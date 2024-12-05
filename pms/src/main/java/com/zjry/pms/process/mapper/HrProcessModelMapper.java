package com.zjry.pms.process.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.process.domain.HrProcessModel;

/**
 * 流程模块Mapper接口
 * 
 * @author yzh
 * @date 2021-07-26
 */
public interface HrProcessModelMapper extends BaseMapper<HrProcessModel> {
    /**
     * 查询流程模块
     * 
     * @param id 流程模块ID
     * @return 流程模块
     */
    public HrProcessModel selectHrProcessModelById(Long id);

    /**
     * 查询流程模块列表
     * 
     * @param hrProcessModel 流程模块
     * @return 流程模块集合
     */
    public List<HrProcessModel> selectHrProcessModelList(HrProcessModel hrProcessModel);

    /**
     * 新增流程模块
     * 
     * @param hrProcessModel 流程模块
     * @return 结果
     */
    public int insertHrProcessModel(HrProcessModel hrProcessModel);

    /**
     * 修改流程模块
     * 
     * @param hrProcessModel 流程模块
     * @return 结果
     */
    public int updateHrProcessModel(HrProcessModel hrProcessModel);

    /**
     * 删除流程模块
     * 
     * @param id 流程模块ID
     * @return 结果
     */
    public int deleteHrProcessModelById(Long id);

    /**
     * 批量删除流程模块
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteHrProcessModelByIds(Long[] ids);

    HrProcessModel selectByProcInsId(String procInsId);
}
