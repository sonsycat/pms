package com.zjry.pms.workload.mapper;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.workload.domain.PmsWorkload;
import org.apache.ibatis.annotations.Param;

/**
 * 工作量Mapper接口
 *
 * @author zjry
 * @date 2022-09-29
 */
public interface PmsWorkloadMapper extends BaseMapper<PmsWorkload> {
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
    public int insertPmsWorkload(PmsWorkload pmsWorkload);

    /**
     * 修改工作量
     *
     * @param pmsWorkload 工作量
     * @return 结果
     */
    public int updatePmsWorkload(PmsWorkload pmsWorkload);

    /**
     * 删除工作量
     *
     * @param id 工作量ID
     * @return 结果
     */
    public int deletePmsWorkloadById(Long id);

    /**
     * 批量删除工作量
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsWorkloadByIds(Long[] ids);

    void realDelete(@Param("copyDate") Date copyDate);

    void saveBatch(List<PmsWorkload> list);
}
