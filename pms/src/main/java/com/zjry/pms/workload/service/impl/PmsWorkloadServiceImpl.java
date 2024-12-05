package com.zjry.pms.workload.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.utils.DateUtils;
import com.zjry.common.utils.StringUtils;
import com.zjry.pms.common.Constants;
import com.zjry.pms.common.service.ISysFlowcodeService;
import com.zjry.pms.workload.domain.PmsWorkloadToll;
import com.zjry.pms.workload.domain.WorkloadDto;
import com.zjry.pms.workload.mapper.PmsWorkloadTollMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.workload.mapper.PmsWorkloadMapper;
import com.zjry.pms.workload.domain.PmsWorkload;
import com.zjry.pms.workload.service.IPmsWorkloadService;

/**
 * 工作量Service业务层处理
 *
 * @author zjry
 * @date 2022-09-29
 */
@Service
public class PmsWorkloadServiceImpl extends ServiceImpl<PmsWorkloadMapper, PmsWorkload> implements IPmsWorkloadService {

    @Autowired
    private PmsWorkloadMapper pmsWorkloadMapper;

    @Autowired
    private ISysFlowcodeService flowcodeService;

    @Autowired
    private PmsWorkloadTollMapper workloadTollMapper;

    /**
     * 查询工作量
     *
     * @param id 工作量ID
     * @return 工作量
     */
    @Override
    public PmsWorkload selectPmsWorkloadById(Long id) {
        return pmsWorkloadMapper.selectPmsWorkloadById(id);
    }

    /**
     * 查询工作量列表
     *
     * @param pmsWorkload 工作量
     * @return 工作量
     */
    @Override
    public List<PmsWorkload> selectPmsWorkloadList(PmsWorkload pmsWorkload) {
        return pmsWorkloadMapper.selectPmsWorkloadList(pmsWorkload);
    }

    /**
     * 新增工作量
     *
     * @param pmsWorkload 工作量
     * @return 结果
     */
    @Override
    public AjaxResult insertPmsWorkload(PmsWorkload pmsWorkload) {
        Integer count = new LambdaQueryChainWrapper<>(pmsWorkloadMapper)
                .select(PmsWorkload::getProjectName)
                .eq(PmsWorkload::getProjectName, pmsWorkload.getProjectName())
                .eq(PmsWorkload::getWorkloadDate, pmsWorkload.getWorkloadDate())
                .count();
        if (count > 0) {
            return AjaxResult.error("项目名称重复");
        }
//        pmsWorkload.setProjectCode(Constants.GZLXM + flowcodeService.getEntityFlowcodeNoCode(Constants.GZLXM, 6));
        pmsWorkload.preInsert();
        pmsWorkloadMapper.insertPmsWorkload(pmsWorkload);
        return AjaxResult.success();
    }

    /**
     * 修改工作量
     *
     * @param pmsWorkload 工作量
     * @return 结果
     */
    @Override
    public AjaxResult updatePmsWorkload(PmsWorkload pmsWorkload) {

        Integer count = new LambdaQueryChainWrapper<>(pmsWorkloadMapper)
                .select(PmsWorkload::getProjectName)
                .eq(PmsWorkload::getProjectName, pmsWorkload.getProjectName())
                .eq(PmsWorkload::getWorkloadDate, pmsWorkload.getWorkloadDate())
                .ne(PmsWorkload::getId, pmsWorkload.getId())
                .count();
        if (count > 0) {
            return AjaxResult.error("项目名称重复");
        }
        pmsWorkload.preUpdate();
        pmsWorkloadMapper.updatePmsWorkload(pmsWorkload);
        return AjaxResult.success();
    }

    /**
     * 批量删除工作量
     *
     * @param ids 需要删除的工作量ID
     * @return 结果
     */
    @Override
    public int deletePmsWorkloadByIds(Long[] ids) {
        return pmsWorkloadMapper.deletePmsWorkloadByIds(ids);
    }

    /**
     * 删除工作量信息
     *
     * @param id 工作量ID
     * @return 结果
     */
    @Override
    public int deletePmsWorkloadById(Long id) {
        return pmsWorkloadMapper.deletePmsWorkloadById(id);
    }

    @Override
    public int copy(WorkloadDto dto) {
        Date copyDate = dto.getCopyDate();
        Date nowDate = dto.getNowDate();
        //删除旧收费项目
        workloadTollMapper.realDeleteByDate(copyDate);
        //删除旧工作量
        pmsWorkloadMapper.realDelete(copyDate);
        //新增数据
        PmsWorkload query = new PmsWorkload();
        query.setDelFlag("0");
        query.setWorkloadDate(nowDate);
        List<PmsWorkload> list = pmsWorkloadMapper.selectPmsWorkloadList(query);
        List<String> projectCodeList = list.stream().map(PmsWorkload::getProjectCode).collect(Collectors.toList());
        List<PmsWorkloadToll> newToll = new ArrayList<>();
        List<PmsWorkloadToll> oldToll = new LambdaQueryChainWrapper<>(workloadTollMapper)
                .select(PmsWorkloadToll::getProjectCode, PmsWorkloadToll::getHisName, PmsWorkloadToll::getHisCode)
                .eq(PmsWorkloadToll::getDelFlag, "0")
                .in(PmsWorkloadToll::getProjectCode, projectCodeList).list();

        list.forEach(item -> {
            item.preInsert();
            String oldProjectCode = item.getProjectCode();
            List<PmsWorkloadToll> oldTollFilter = oldToll.stream()
                    .filter(f -> StringUtils.equals(oldProjectCode, f.getProjectCode()))
                    .collect(Collectors.toList());

            String newProjectCode = Constants.GZLXM + flowcodeService.getEntityFlowcodeNoCode(Constants.GZLXM, 6);
            oldTollFilter.forEach(tollItem -> {
                tollItem.preInsert();
                tollItem.setProjectCode(newProjectCode);
            });
            newToll.addAll(oldTollFilter);
            item.setProjectCode(newProjectCode);
            item.setWorkloadDate(copyDate);
        });
        pmsWorkloadMapper.saveBatch(list);
        workloadTollMapper.saveBatch(newToll);

        return 1;
    }
}
