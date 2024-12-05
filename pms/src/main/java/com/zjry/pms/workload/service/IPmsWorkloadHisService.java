package com.zjry.pms.workload.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.workload.domain.PmsWorkloadHis;

/**
 * 工作量-HIS收费项目Service接口
 *
 * @author zjry
 * @date 2022-09-29
 */
public interface IPmsWorkloadHisService extends IService<PmsWorkloadHis> {
    /**
     * 查询工作量-HIS收费项目
     *
     * @param id 工作量-HIS收费项目ID
     * @return 工作量-HIS收费项目
     */
    public PmsWorkloadHis selectPmsWorkloadHisById(Long id);

    /**
     * 查询工作量-HIS收费项目列表
     *
     * @param pmsWorkloadHis 工作量-HIS收费项目
     * @return 工作量-HIS收费项目集合
     */
    public List<PmsWorkloadHis> selectPmsWorkloadHisList(PmsWorkloadHis pmsWorkloadHis);

    /**
     * 新增工作量-HIS收费项目
     *
     * @param pmsWorkloadHis 工作量-HIS收费项目
     * @return 结果
     */
    public int insertPmsWorkloadHis(PmsWorkloadHis pmsWorkloadHis);

    /**
     * 修改工作量-HIS收费项目
     *
     * @param pmsWorkloadHis 工作量-HIS收费项目
     * @return 结果
     */
    public int updatePmsWorkloadHis(PmsWorkloadHis pmsWorkloadHis);

    /**
     * 批量删除工作量-HIS收费项目
     *
     * @param ids 需要删除的工作量-HIS收费项目ID
     * @return 结果
     */
    public int deletePmsWorkloadHisByIds(Long[] ids);

    /**
     * 删除工作量-HIS收费项目信息
     *
     * @param id 工作量-HIS收费项目ID
     * @return 结果
     */
    public int deletePmsWorkloadHisById(Long id);
}
