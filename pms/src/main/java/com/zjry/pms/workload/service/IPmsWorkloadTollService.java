package com.zjry.pms.workload.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.pms.workload.domain.PmsWorkloadToll;
import com.zjry.pms.workload.domain.WorkloadTollDto;

/**
 * 工作量-收费项设置Service接口
 *
 * @author zjry
 * @date 2022-09-29
 */
public interface IPmsWorkloadTollService extends IService<PmsWorkloadToll> {

    /**
     * 查询工作量-收费项设置
     *
     * @param id 工作量-收费项设置ID
     * @return 工作量-收费项设置
     */
    public PmsWorkloadToll selectPmsWorkloadTollById(Long id);

    /**
     * 查询工作量-收费项设置列表
     *
     * @param pmsWorkloadToll 工作量-收费项设置
     * @return 工作量-收费项设置集合
     */
    public List<PmsWorkloadToll> selectPmsWorkloadTollList(PmsWorkloadToll pmsWorkloadToll);

    /**
     * 新增工作量-收费项设置
     *
     * @param pmsWorkloadToll 工作量-收费项设置
     * @return 结果
     */
    public int insertPmsWorkloadToll(PmsWorkloadToll pmsWorkloadToll);

    /**
     * 修改工作量-收费项设置
     *
     * @param pmsWorkloadToll 工作量-收费项设置
     * @return 结果
     */
    public int updatePmsWorkloadToll(PmsWorkloadToll pmsWorkloadToll);

    /**
     * 批量删除工作量-收费项设置
     *
     * @param ids 需要删除的工作量-收费项设置ID
     * @return 结果
     */
    public int deletePmsWorkloadTollByIds(Long[] ids);

    /**
     * 删除工作量-收费项设置信息
     *
     * @param id 工作量-收费项设置ID
     * @return 结果
     */
    public int deletePmsWorkloadTollById(Long id);

    /**
     * @param projectCode
     * @param type
     * @param hisName
     * @param existCode
     * @return
     */
    List<PmsWorkloadToll> selectAll(String projectCode, String type, String hisName, List<String> existCode);

    AjaxResult saveToll(WorkloadTollDto dto);
}
