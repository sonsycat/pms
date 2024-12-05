package com.zjry.pms.subsidy.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.subsidy.domain.PmsSubsidyRewardInput;

/**
 * 单项奖惩录入Mapper接口
 * 
 * @author maozt
 * @date 2022-08-16
 */
public interface PmsSubsidyRewardInputMapper extends BaseMapper<PmsSubsidyRewardInput> {
    /**
     * 查询单项奖惩录入
     * 
     * @param id 单项奖惩录入ID
     * @return 单项奖惩录入
     */
    public PmsSubsidyRewardInput selectPmsSubsidyRewardInputById(Long id);

    /**
     * 查询单项奖惩录入列表
     * 
     * @param pmsSubsidyRewardInput 单项奖惩录入
     * @return 单项奖惩录入集合
     */
    public List<PmsSubsidyRewardInput> selectPmsSubsidyRewardInputList(PmsSubsidyRewardInput pmsSubsidyRewardInput);

    /**
     * 新增单项奖惩录入
     * 
     * @param pmsSubsidyRewardInput 单项奖惩录入
     * @return 结果
     */
    public int insertPmsSubsidyRewardInput(PmsSubsidyRewardInput pmsSubsidyRewardInput);

    /**
     * 修改单项奖惩录入
     * 
     * @param pmsSubsidyRewardInput 单项奖惩录入
     * @return 结果
     */
    public int updatePmsSubsidyRewardInput(PmsSubsidyRewardInput pmsSubsidyRewardInput);

    /**
     * 删除单项奖惩录入
     * 
     * @param id 单项奖惩录入ID
     * @return 结果
     */
    public int deletePmsSubsidyRewardInputById(Long id);

    /**
     * 批量删除单项奖惩录入
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsSubsidyRewardInputByIds(Long[] ids);
}
