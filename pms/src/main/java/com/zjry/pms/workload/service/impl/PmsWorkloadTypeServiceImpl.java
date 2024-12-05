package com.zjry.pms.workload.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.pms.common.Constants;
import com.zjry.pms.common.service.ISysFlowcodeService;
import com.zjry.pms.workload.domain.PmsWorkload;
import com.zjry.pms.workload.domain.PmsWorkloadType;
import com.zjry.pms.workload.mapper.PmsWorkloadMapper;
import com.zjry.pms.workload.mapper.PmsWorkloadTypeMapper;
import com.zjry.pms.workload.service.IPmsWorkloadTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 工作量-类别设置Service业务层处理
 *
 * @author zjry
 * @date 2022-09-29
 */
@Service
public class PmsWorkloadTypeServiceImpl extends ServiceImpl<PmsWorkloadTypeMapper, PmsWorkloadType> implements IPmsWorkloadTypeService {

    @Autowired
    private PmsWorkloadTypeMapper pmsWorkloadTypeMapper;

    @Autowired
    private ISysFlowcodeService flowcodeService;

    @Autowired
    private PmsWorkloadMapper workloadMapper;

    /**
     * 查询工作量-类别设置
     *
     * @param id 工作量-类别设置ID
     * @return 工作量-类别设置
     */
    @Override
    public PmsWorkloadType selectPmsWorkloadTypeById(Long id) {
        return pmsWorkloadTypeMapper.selectPmsWorkloadTypeById(id);
    }

    /**
     * 查询工作量-类别设置列表
     *
     * @param pmsWorkloadType 工作量-类别设置
     * @return 工作量-类别设置
     */
    @Override
    public List<PmsWorkloadType> selectPmsWorkloadTypeList(PmsWorkloadType pmsWorkloadType) {
        return pmsWorkloadTypeMapper.selectPmsWorkloadTypeList(pmsWorkloadType);
    }

    /**
     * 新增工作量-类别设置
     *
     * @param pmsWorkloadType 工作量-类别设置
     * @return 结果
     */
    @Override
    public AjaxResult insertPmsWorkloadType(PmsWorkloadType pmsWorkloadType) {
        pmsWorkloadType.preInsert();
        Integer count = new LambdaQueryChainWrapper<>(pmsWorkloadTypeMapper)
                .select(PmsWorkloadType::getTypeName)
                .eq(PmsWorkloadType::getTypeName, pmsWorkloadType.getTypeName())
                .count();
        if (count > 0) {
            return AjaxResult.error("类别名称重复");
        }
        pmsWorkloadType.setTypeCode(Constants.GZLLB + flowcodeService.getEntityFlowcodeNoCode(Constants.GZLLB, 6));
        pmsWorkloadTypeMapper.insertPmsWorkloadType(pmsWorkloadType);
        return AjaxResult.success();
    }

    /**
     * 修改工作量-类别设置
     *
     * @param pmsWorkloadType 工作量-类别设置
     * @return 结果
     */
    @Override
    public AjaxResult updatePmsWorkloadType(PmsWorkloadType pmsWorkloadType) {
        pmsWorkloadType.preUpdate();

        Integer count = new LambdaQueryChainWrapper<>(pmsWorkloadTypeMapper)
                .select(PmsWorkloadType::getTypeName)
                .eq(PmsWorkloadType::getTypeName, pmsWorkloadType.getTypeName())
                .ne(PmsWorkloadType::getId, pmsWorkloadType.getId())
                .count();
        if (count > 0) {
            return AjaxResult.error("类别名称重复件");
        }
        pmsWorkloadTypeMapper.updatePmsWorkloadType(pmsWorkloadType);
        return AjaxResult.success();
    }

    /**
     * 批量删除工作量-类别设置
     *
     * @param ids 需要删除的工作量-类别设置ID
     * @return 结果
     */
    @Override
    public AjaxResult deletePmsWorkloadTypeByIds(Long[] ids) {

        List<String> typeCodes = new LambdaQueryChainWrapper<>(pmsWorkloadTypeMapper)
                .select(PmsWorkloadType::getTypeCode)
                .in(PmsWorkloadType::getId, ids)
                .list()
                .stream()
                .map(PmsWorkloadType::getTypeCode)
                .collect(Collectors.toList());

        Integer count = new LambdaQueryChainWrapper<>(workloadMapper)
                .select(PmsWorkload::getId)
                .in(PmsWorkload::getType, typeCodes)
                .count();
        if (count > 0) {
            return AjaxResult.error("类别被引用,无法删除");
        }
        pmsWorkloadTypeMapper.deletePmsWorkloadTypeByIds(ids);
        return AjaxResult.success();
    }

    /**
     * 删除工作量-类别设置信息
     *
     * @param id 工作量-类别设置ID
     * @return 结果
     */
    @Override
    public int deletePmsWorkloadTypeById(Long id) {
        return pmsWorkloadTypeMapper.deletePmsWorkloadTypeById(id);
    }
}
