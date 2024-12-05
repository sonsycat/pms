package com.zjry.pms.subsidy.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.subsidy.mapper.PmsSubsidyRewardInputMapper;
import com.zjry.pms.subsidy.domain.PmsSubsidyRewardInput;
import com.zjry.pms.subsidy.service.IPmsSubsidyRewardInputService;

/**
 * 单项奖惩录入Service业务层处理
 * 
 * @author maozt
 * @date 2022-08-16
 */
@Service
public class PmsSubsidyRewardInputServiceImpl extends ServiceImpl<PmsSubsidyRewardInputMapper, PmsSubsidyRewardInput> implements IPmsSubsidyRewardInputService {
    @Autowired
    private PmsSubsidyRewardInputMapper pmsSubsidyRewardInputMapper;

    /**
     * 查询单项奖惩录入
     * 
     * @param id 单项奖惩录入ID
     * @return 单项奖惩录入
     */
    @Override
    public PmsSubsidyRewardInput selectPmsSubsidyRewardInputById(Long id) {
        return pmsSubsidyRewardInputMapper.selectPmsSubsidyRewardInputById(id);
    }

    /**
     * 查询单项奖惩录入列表
     * 
     * @param pmsSubsidyRewardInput 单项奖惩录入
     * @return 单项奖惩录入
     */
    @Override
    public List<PmsSubsidyRewardInput> selectPmsSubsidyRewardInputList(PmsSubsidyRewardInput pmsSubsidyRewardInput) {
        return pmsSubsidyRewardInputMapper.selectPmsSubsidyRewardInputList(pmsSubsidyRewardInput);
    }

    /**
     * 新增单项奖惩录入
     * 
     * @param pmsSubsidyRewardInput 单项奖惩录入
     * @return 结果
     */
    @Override
    public int insertPmsSubsidyRewardInput(PmsSubsidyRewardInput pmsSubsidyRewardInput) {
    	pmsSubsidyRewardInput.preInsert();
        return pmsSubsidyRewardInputMapper.insertPmsSubsidyRewardInput(pmsSubsidyRewardInput);
    }

    /**
     * 修改单项奖惩录入
     * 
     * @param pmsSubsidyRewardInput 单项奖惩录入
     * @return 结果
     */
    @Override
    public int updatePmsSubsidyRewardInput(PmsSubsidyRewardInput pmsSubsidyRewardInput) {
    	pmsSubsidyRewardInput.preUpdate();
        return pmsSubsidyRewardInputMapper.updatePmsSubsidyRewardInput(pmsSubsidyRewardInput);
    }

    /**
     * 批量删除单项奖惩录入
     * 
     * @param ids 需要删除的单项奖惩录入ID
     * @return 结果
     */
    @Override
    public int deletePmsSubsidyRewardInputByIds(Long[] ids) {
        return pmsSubsidyRewardInputMapper.deletePmsSubsidyRewardInputByIds(ids);
    }

    /**
     * 删除单项奖惩录入信息
     * 
     * @param id 单项奖惩录入ID
     * @return 结果
     */
    @Override
    public int deletePmsSubsidyRewardInputById(Long id) {
        return pmsSubsidyRewardInputMapper.deletePmsSubsidyRewardInputById(id);
    }
}
