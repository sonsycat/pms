package com.zjry.pms.workload.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.pms.workload.domain.WorkloadTollDto;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.workload.mapper.PmsWorkloadTollMapper;
import com.zjry.pms.workload.domain.PmsWorkloadToll;
import com.zjry.pms.workload.service.IPmsWorkloadTollService;

/**
 * 工作量-收费项设置Service业务层处理
 *
 * @author zjry
 * @date 2022-09-29
 */
@Service
public class PmsWorkloadTollServiceImpl extends ServiceImpl<PmsWorkloadTollMapper, PmsWorkloadToll> implements IPmsWorkloadTollService {

    @Autowired
    private PmsWorkloadTollMapper pmsWorkloadTollMapper;

    /**
     * 查询工作量-收费项设置
     *
     * @param id 工作量-收费项设置ID
     * @return 工作量-收费项设置
     */
    @Override
    public PmsWorkloadToll selectPmsWorkloadTollById(Long id) {
        return pmsWorkloadTollMapper.selectPmsWorkloadTollById(id);
    }

    /**
     * 查询工作量-收费项设置列表
     *
     * @param pmsWorkloadToll 工作量-收费项设置
     * @return 工作量-收费项设置
     */
    @Override
    public List<PmsWorkloadToll> selectPmsWorkloadTollList(PmsWorkloadToll pmsWorkloadToll) {
        return pmsWorkloadTollMapper.selectPmsWorkloadTollList(pmsWorkloadToll);
    }

    /**
     * 新增工作量-收费项设置
     *
     * @param pmsWorkloadToll 工作量-收费项设置
     * @return 结果
     */
    @Override
    public int insertPmsWorkloadToll(PmsWorkloadToll pmsWorkloadToll) {
        pmsWorkloadToll.preInsert();
        return pmsWorkloadTollMapper.insertPmsWorkloadToll(pmsWorkloadToll);
    }

    /**
     * 修改工作量-收费项设置
     *
     * @param pmsWorkloadToll 工作量-收费项设置
     * @return 结果
     */
    @Override
    public int updatePmsWorkloadToll(PmsWorkloadToll pmsWorkloadToll) {
        pmsWorkloadToll.preUpdate();
        return pmsWorkloadTollMapper.updatePmsWorkloadToll(pmsWorkloadToll);
    }

    /**
     * 批量删除工作量-收费项设置
     *
     * @param ids 需要删除的工作量-收费项设置ID
     * @return 结果
     */
    @Override
    public int deletePmsWorkloadTollByIds(Long[] ids) {
        return pmsWorkloadTollMapper.deletePmsWorkloadTollByIds(ids);
    }

    /**
     * 删除工作量-收费项设置信息
     *
     * @param id 工作量-收费项设置ID
     * @return 结果
     */
    @Override
    public int deletePmsWorkloadTollById(Long id) {
        return pmsWorkloadTollMapper.deletePmsWorkloadTollById(id);
    }

    @Override
    public List<PmsWorkloadToll> selectAll(String projectCode, String type, String hisName, List<String> existCode) {
        return pmsWorkloadTollMapper.selectAll(projectCode, type, hisName, existCode);
    }

    @Override
    public AjaxResult saveToll(WorkloadTollDto dto) {
        String projectCode = dto.getProjectCode();
        List<PmsWorkloadToll> list = dto.getList();
        //删除旧数据
        pmsWorkloadTollMapper.realDelete(projectCode);
        //插入新数据
        list.forEach(item -> {
            item.setProjectCode(projectCode);
            item.setDelFlag("0");
            item.preInsert();
        });
        if (CollectionUtils.isNotEmpty(list)) {
            pmsWorkloadTollMapper.saveBatch(list);
        }
        return AjaxResult.success();
    }
}
