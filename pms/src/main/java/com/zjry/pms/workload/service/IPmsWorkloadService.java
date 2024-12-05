package com.zjry.pms.workload.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.pms.workload.domain.PmsWorkload;
import com.zjry.pms.workload.domain.WorkloadDto;

/**
 * 工作量Service接口
 *
 * @author zjry
 * @date 2022-09-29
 */
public interface IPmsWorkloadService extends IService<PmsWorkload> {

    /**
     * 查询工作量
     *
     * @param id 工作量ID
     * @return 工作量
     */
    public PmsWorkload selectPmsWorkloadById(Long id);

    /**
     * 查询工作量列表
     *
     * @param pmsWorkload 工作量
     * @return 工作量集合
     */
    public List<PmsWorkload> selectPmsWorkloadList(PmsWorkload pmsWorkload);

    /**
     * 新增工作量
     *
     * @param pmsWorkload 工作量
     * @return 结果
     */
    AjaxResult insertPmsWorkload(PmsWorkload pmsWorkload);

    /**
     * 修改工作量
     *
     * @param pmsWorkload 工作量
     * @return 结果
     */
    public AjaxResult updatePmsWorkload(PmsWorkload pmsWorkload);

    /**
     * 批量删除工作量
     *
     * @param ids 需要删除的工作量ID
     * @return 结果
     */
    public int deletePmsWorkloadByIds(Long[] ids);

    /**
     * 删除工作量信息
     *
     * @param id 工作量ID
     * @return 结果
     */
    public int deletePmsWorkloadById(Long id);

    int copy(WorkloadDto dto);
}
