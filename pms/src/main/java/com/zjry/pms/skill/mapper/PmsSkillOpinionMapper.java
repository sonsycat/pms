package com.zjry.pms.skill.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.skill.domain.PmsSkillOpinion;

/**
 * 审批意见Mapper接口
 * 
 * @author maozt
 * @date 2022-07-27
 */
public interface PmsSkillOpinionMapper extends BaseMapper<PmsSkillOpinion> {
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
     * 删除审批意见
     * 
     * @param id 审批意见ID
     * @return 结果
     */
    public int deletePmsSkillOpinionById(Long id);

    /**
     * 批量删除审批意见
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsSkillOpinionByIds(Long[] ids);
}
