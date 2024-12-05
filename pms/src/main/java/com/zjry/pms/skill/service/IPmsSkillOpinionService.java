package com.zjry.pms.skill.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.skill.domain.PmsSkillOpinion;

/**
 * 审批意见Service接口
 * 
 * @author maozt
 * @date 2022-07-27
 */
public interface IPmsSkillOpinionService extends IService<PmsSkillOpinion> {
    /**
     * 查询审批意见
     * 
     * @param id 审批意见ID
     * @return 审批意见
     */
    public PmsSkillOpinion selectPmsSkillOpinionById(Long id);

    /**
     * 查询审批意见列表
     * 
     * @param pmsSkillOpinion 审批意见
     * @return 审批意见集合
     */
    public List<PmsSkillOpinion> selectPmsSkillOpinionList(PmsSkillOpinion pmsSkillOpinion);

    /**
     * 新增审批意见
     * 
     * @param pmsSkillOpinion 审批意见
     * @return 结果
     */
    public int insertPmsSkillOpinion(PmsSkillOpinion pmsSkillOpinion);

    /**
     * 修改审批意见
     * 
     * @param pmsSkillOpinion 审批意见
     * @return 结果
     */
    public int updatePmsSkillOpinion(PmsSkillOpinion pmsSkillOpinion);

    /**
     * 批量删除审批意见
     * 
     * @param ids 需要删除的审批意见ID
     * @return 结果
     */
    public int deletePmsSkillOpinionByIds(Long[] ids);

    /**
     * 删除审批意见信息
     * 
     * @param id 审批意见ID
     * @return 结果
     */
    public int deletePmsSkillOpinionById(Long id);
}
