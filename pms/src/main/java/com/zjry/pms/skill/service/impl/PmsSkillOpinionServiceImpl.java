package com.zjry.pms.skill.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.skill.mapper.PmsSkillOpinionMapper;
import com.zjry.pms.skill.domain.PmsSkillOpinion;
import com.zjry.pms.skill.service.IPmsSkillOpinionService;

/**
 * 审批意见Service业务层处理
 * 
 * @author maozt
 * @date 2022-07-27
 */
@Service
public class PmsSkillOpinionServiceImpl extends ServiceImpl<PmsSkillOpinionMapper, PmsSkillOpinion> implements IPmsSkillOpinionService {
    @Autowired
    private PmsSkillOpinionMapper pmsSkillOpinionMapper;

    /**
     * 查询审批意见
     * 
     * @param id 审批意见ID
     * @return 审批意见
     */
    @Override
    public PmsSkillOpinion selectPmsSkillOpinionById(Long id) {
        return pmsSkillOpinionMapper.selectPmsSkillOpinionById(id);
    }

    /**
     * 查询审批意见列表
     * 
     * @param pmsSkillOpinion 审批意见
     * @return 审批意见
     */
    @Override
    public List<PmsSkillOpinion> selectPmsSkillOpinionList(PmsSkillOpinion pmsSkillOpinion) {
        return pmsSkillOpinionMapper.selectPmsSkillOpinionList(pmsSkillOpinion);
    }

    /**
     * 新增审批意见
     * 
     * @param pmsSkillOpinion 审批意见
     * @return 结果
     */
    @Override
    public int insertPmsSkillOpinion(PmsSkillOpinion pmsSkillOpinion) {
    	pmsSkillOpinion.preInsert();
        return pmsSkillOpinionMapper.insertPmsSkillOpinion(pmsSkillOpinion);
    }

    /**
     * 修改审批意见
     * 
     * @param pmsSkillOpinion 审批意见
     * @return 结果
     */
    @Override
    public int updatePmsSkillOpinion(PmsSkillOpinion pmsSkillOpinion) {
    	pmsSkillOpinion.preUpdate();
        return pmsSkillOpinionMapper.updatePmsSkillOpinion(pmsSkillOpinion);
    }

    /**
     * 批量删除审批意见
     * 
     * @param ids 需要删除的审批意见ID
     * @return 结果
     */
    @Override
    public int deletePmsSkillOpinionByIds(Long[] ids) {
        return pmsSkillOpinionMapper.deletePmsSkillOpinionByIds(ids);
    }

    /**
     * 删除审批意见信息
     * 
     * @param id 审批意见ID
     * @return 结果
     */
    @Override
    public int deletePmsSkillOpinionById(Long id) {
        return pmsSkillOpinionMapper.deletePmsSkillOpinionById(id);
    }
}
