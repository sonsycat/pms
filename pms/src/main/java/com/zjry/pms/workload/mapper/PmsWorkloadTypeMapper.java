package com.zjry.pms.workload.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.workload.domain.PmsWorkloadType;

/**
 * 工作量-类别设置Mapper接口
 * 
 * @author zjry
 * @date 2022-09-29
 */
public interface PmsWorkloadTypeMapper extends BaseMapper<PmsWorkloadType> {
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
    public int insertPmsWorkloadType(PmsWorkloadType pmsWorkloadType);

    /**
     * 修改工作量-类别设置
     * 
     * @param pmsWorkloadType 工作量-类别设置
     * @return 结果
     */
    public int updatePmsWorkloadType(PmsWorkloadType pmsWorkloadType);

    /**
     * 删除工作量-类别设置
     * 
     * @param id 工作量-类别设置ID
     * @return 结果
     */
    public int deletePmsWorkloadTypeById(Long id);

    /**
     * 批量删除工作量-类别设置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsWorkloadTypeByIds(Long[] ids);
}
