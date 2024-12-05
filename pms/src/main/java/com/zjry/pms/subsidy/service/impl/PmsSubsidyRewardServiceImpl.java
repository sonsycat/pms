package com.zjry.pms.subsidy.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import com.zjry.pms.encode.service.IHrEncodeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.subsidy.mapper.PmsSubsidyRewardMapper;
import com.zjry.pms.subsidy.domain.PmsSubsidyReward;
import com.zjry.pms.subsidy.service.IPmsSubsidyRewardService;

/**
 * 奖惩项目维护Service业务层处理
 * 
 * @author maozt
 * @date 2022-08-15
 */
@Service
public class PmsSubsidyRewardServiceImpl extends ServiceImpl<PmsSubsidyRewardMapper, PmsSubsidyReward> implements IPmsSubsidyRewardService {
    @Autowired
    private PmsSubsidyRewardMapper pmsSubsidyRewardMapper;
    @Autowired
    private IHrEncodeRuleService hrEncodeRuleService;

    /**
     * 查询奖惩项目维护
     * 
     * @param id 奖惩项目维护ID
     * @return 奖惩项目维护
     */
    @Override
    public PmsSubsidyReward selectPmsSubsidyRewardById(Long id) {
        return pmsSubsidyRewardMapper.selectPmsSubsidyRewardById(id);
    }

    /**
     * 查询奖惩项目维护列表
     * 
     * @param pmsSubsidyReward 奖惩项目维护
     * @return 奖惩项目维护
     */
    @Override
    public List<PmsSubsidyReward> selectPmsSubsidyRewardList(PmsSubsidyReward pmsSubsidyReward) {
        return pmsSubsidyRewardMapper.selectPmsSubsidyRewardList(pmsSubsidyReward);
    }

    /**
     * 新增奖惩项目维护
     * 
     * @param pmsSubsidyReward 奖惩项目维护
     * @return 结果
     */
    @Override
    public int insertPmsSubsidyReward(PmsSubsidyReward pmsSubsidyReward) throws Exception {
        if (pmsSubsidyRewardMapper.selectPmsSubsidyRewardByName(pmsSubsidyReward).size() > 0 ) {
            throw new Exception("项目名称重复");
        }
        pmsSubsidyReward.setRewardCode(hrEncodeRuleService.generateEncode("pms_subsidy_reward", "REWARD_CODE"));
    	pmsSubsidyReward.preInsert();
        return pmsSubsidyRewardMapper.insertPmsSubsidyReward(pmsSubsidyReward);
    }

    /**
     * 修改奖惩项目维护
     * 
     * @param pmsSubsidyReward 奖惩项目维护
     * @return 结果
     */
    @Override
    public int updatePmsSubsidyReward(PmsSubsidyReward pmsSubsidyReward) throws Exception {
        if (pmsSubsidyRewardMapper.selectPmsSubsidyRewardByName(pmsSubsidyReward).size() > 0 ) {
            throw new Exception("项目名称重复");
        }
    	pmsSubsidyReward.preUpdate();
        return pmsSubsidyRewardMapper.updatePmsSubsidyReward(pmsSubsidyReward);
    }

    /**
     * 批量删除奖惩项目维护
     * 
     * @param ids 需要删除的奖惩项目维护ID
     * @return 结果
     */
    @Override
    public int deletePmsSubsidyRewardByIds(Long[] ids) {
        return pmsSubsidyRewardMapper.deletePmsSubsidyRewardByIds(ids);
    }

    /**
     * 删除奖惩项目维护信息
     * 
     * @param id 奖惩项目维护ID
     * @return 结果
     */
    @Override
    public int deletePmsSubsidyRewardById(Long id) {
        return pmsSubsidyRewardMapper.deletePmsSubsidyRewardById(id);
    }
}
