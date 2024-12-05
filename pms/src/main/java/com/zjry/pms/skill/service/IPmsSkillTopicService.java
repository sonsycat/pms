package com.zjry.pms.skill.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.skill.domain.PmsSkillTopic;

/**
 * 课题Service接口
 * 
 * @author maozt
 * @date 2022-07-26
 */
public interface IPmsSkillTopicService extends IService<PmsSkillTopic> {
    /**
     * 查询课题
     * 
     * @param id 课题ID
     * @return 课题
     */
    public PmsSkillTopic selectPmsSkillTopicById(Long id);

    /**
     * 查询课题列表
     * 
     * @param pmsSkillTopic 课题
     * @return 课题集合
     */
    public List<PmsSkillTopic> selectPmsSkillTopicList(PmsSkillTopic pmsSkillTopic);

    /**
     * 新增课题
     * 
     * @param pmsSkillTopic 课题
     * @return 结果
     */
    public int insertPmsSkillTopic(PmsSkillTopic pmsSkillTopic) throws Exception;

    /**
     * 修改课题
     * 
     * @param pmsSkillTopic 课题
     * @return 结果
     */
    public int updatePmsSkillTopic(PmsSkillTopic pmsSkillTopic) throws Exception;

    /**
     * 批量删除课题
     * 
     * @param ids 需要删除的课题ID
     * @return 结果
     */
    public int deletePmsSkillTopicByIds(Long[] ids);

    /**
     * 删除课题信息
     * 
     * @param id 课题ID
     * @return 结果
     */
    public int deletePmsSkillTopicById(Long id);
}
