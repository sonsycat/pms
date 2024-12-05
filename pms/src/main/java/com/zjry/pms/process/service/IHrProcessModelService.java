package com.zjry.pms.process.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.process.domain.HrProcessModel;

/**
 * 流程模块Service接口
 * 
 * @author yzh
 * @date 2021-07-26
 */
public interface IHrProcessModelService extends IService<HrProcessModel> {
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
     * 批量删除流程模块
     * 
     * @param ids 需要删除的流程模块ID
     * @return 结果
     */
    public int deleteHrProcessModelByIds(Long[] ids);

    /**
     * 删除流程模块信息
     * 
     * @param id 流程模块ID
     * @return 结果
     */
    public int deleteHrProcessModelById(Long id);
}
