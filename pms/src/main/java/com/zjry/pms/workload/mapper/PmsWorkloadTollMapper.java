package com.zjry.pms.workload.mapper;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.workload.domain.PmsWorkloadToll;
import org.apache.ibatis.annotations.Param;

/**
 * 工作量-收费项设置Mapper接口
 *
 * @author zjry
 * @date 2022-09-29
 */
public interface PmsWorkloadTollMapper extends BaseMapper<PmsWorkloadToll> {
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
     * 删除工作量-收费项设置
     *
     * @param id 工作量-收费项设置ID
     * @return 结果
     */
    public int deletePmsWorkloadTollById(Long id);

    /**
     * 批量删除工作量-收费项设置
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsWorkloadTollByIds(Long[] ids);

    List<PmsWorkloadToll> selectAll(@Param("projectCode") String projectCode, @Param("type") String type, @Param("histName") String histName, @Param("existCode") List<String> existCode);

    void realDelete(@Param("projectCode") String projectCode);

    void saveBatch(@Param("list") List<PmsWorkloadToll> list);

    void realDeleteByDate(@Param("copyDate") Date copyDate);
}
