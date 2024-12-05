package com.zjry.pms.workload.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.pms.workload.domain.PmsWorkloadType;

/**
 * 工作量-类别设置Service接口
 *
 * @author zjry
 * @date 2022-09-29
 */
public interface IPmsWorkloadTypeService extends IService<PmsWorkloadType> {
    /**
     * 查询工作量-类别设置
     *
     * @param id 工作量-类别设置ID
     * @return 工作量-类别设置
     */
    public PmsWorkloadType selectPmsWorkloadTypeById(Long id);

    /**
     * 查询工作量-类别设置列表
     *
     * @param pmsWorkloadType 工作量-类别设置
     * @return 工作量-类别设置集合
     */
    public List<PmsWorkloadType> selectPmsWorkloadTypeList(PmsWorkloadType pmsWorkloadType);

    /**
     * 新增工作量-类别设置
     *
     * @param pmsWorkloadType 工作量-类别设置
     * @return 结果
     */
    public AjaxResult insertPmsWorkloadType(PmsWorkloadType pmsWorkloadType);

    /**
     * 修改工作量-类别设置
     *
     * @param pmsWorkloadType 工作量-类别设置
     * @return 结果
     */
    public AjaxResult updatePmsWorkloadType(PmsWorkloadType pmsWorkloadType);

    /**
     * 批量删除工作量-类别设置
     *
     * @param ids 需要删除的工作量-类别设置ID
     * @return 结果
     */
    public AjaxResult deletePmsWorkloadTypeByIds(Long[] ids);

    /**
     * 删除工作量-类别设置信息
     *
     * @param id 工作量-类别设置ID
     * @return 结果
     */
    public int deletePmsWorkloadTypeById(Long id);
}
