package com.zjry.pms.process.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.process.mapper.HrProcessModelMapper;
import com.zjry.pms.process.domain.HrProcessModel;
import com.zjry.pms.process.service.IHrProcessModelService;

/**
 * 流程模块Service业务层处理
 * 
 * @author yzh
 * @date 2021-07-26
 */
@Service
public class HrProcessModelServiceImpl extends ServiceImpl<HrProcessModelMapper, HrProcessModel> implements IHrProcessModelService {
    @Autowired
    private HrProcessModelMapper hrProcessModelMapper;

    /**
     * 查询流程模块
     * 
     * @param id 流程模块ID
     * @return 流程模块
     */
    @Override
    public HrProcessModel selectHrProcessModelById(Long id) {
        return hrProcessModelMapper.selectHrProcessModelById(id);
    }

    /**
     * 查询流程模块列表
     * 
     * @param hrProcessModel 流程模块
     * @return 流程模块
     */
    @Override
    public List<HrProcessModel> selectHrProcessModelList(HrProcessModel hrProcessModel) {
        return hrProcessModelMapper.selectHrProcessModelList(hrProcessModel);
    }

    /**
     * 新增流程模块
     * 
     * @param hrProcessModel 流程模块
     * @return 结果
     */
    @Override
    public int insertHrProcessModel(HrProcessModel hrProcessModel) {
        hrProcessModel.setCreateTime(DateUtils.getNowDate());
        return hrProcessModelMapper.insertHrProcessModel(hrProcessModel);
    }

    /**
     * 修改流程模块
     * 
     * @param hrProcessModel 流程模块
     * @return 结果
     */
    @Override
    public int updateHrProcessModel(HrProcessModel hrProcessModel) {
        hrProcessModel.setUpdateTime(DateUtils.getNowDate());
        return hrProcessModelMapper.updateHrProcessModel(hrProcessModel);
    }

    /**
     * 批量删除流程模块
     * 
     * @param ids 需要删除的流程模块ID
     * @return 结果
     */
    @Override
    public int deleteHrProcessModelByIds(Long[] ids) {
        return hrProcessModelMapper.deleteHrProcessModelByIds(ids);
    }

    /**
     * 删除流程模块信息
     * 
     * @param id 流程模块ID
     * @return 结果
     */
    @Override
    public int deleteHrProcessModelById(Long id) {
        return hrProcessModelMapper.deleteHrProcessModelById(id);
    }
}
