package com.zjry.pms.subsidy.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.subsidy.domain.PmsSubsidyReward;

/**
 * 奖惩项目维护Mapper接口
 * 
 * @author maozt
 * @date 2022-08-15
 */
public interface PmsSubsidyRewardMapper extends BaseMapper<PmsSubsidyReward> {
    /**
     * 查询奖惩项目维护
     * 
     * @param id 奖惩项目维护ID
     * @return 奖惩项目维护
     */
    public PmsSubsidyReward selectPmsSubsidyRewardById(Long id);

    /**
     * 查询奖惩项目维护列表
     * 
     * @param pmsSubsidyReward 奖惩项目维护
     * @return 奖惩项目维护集合
     */
    public List<PmsSubsidyReward> selectPmsSubsidyRewardList(PmsSubsidyReward pmsSubsidyReward);

    /**
     * 查询奖惩项目维护列表
     *
     * @param pmsSubsidyReward 奖惩项目维护
     * @return 奖惩项目维护集合
     */
    public List<PmsSubsidyReward> selectPmsSubsidyRewardByName(PmsSubsidyReward pmsSubsidyReward);

    /**
     * 新增奖惩项目维护
     * 
     * @param pmsSubsidyReward 奖惩项目维护
     * @return 结果
     */
    public int insertPmsSubsidyReward(PmsSubsidyReward pmsSubsidyReward);

    /**
     * 修改奖惩项目维护
     * 
     * @param pmsSubsidyReward 奖惩项目维护
     * @return 结果
     */
    public int updatePmsSubsidyReward(PmsSubsidyReward pmsSubsidyReward);

    /**
     * 删除奖惩项目维护
     * 
     * @param id 奖惩项目维护ID
     * @return 结果
     */
    public int deletePmsSubsidyRewardById(Long id);

    /**
     * 批量删除奖惩项目维护
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsSubsidyRewardByIds(Long[] ids);
}
