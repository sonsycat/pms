package com.zjry.pms.skill.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.skill.domain.PmsSkillTopic;

/**
 * 课题Mapper接口
 * 
 * @author maozt
 * @date 2022-07-26
 */
public interface PmsSkillTopicMapper extends BaseMapper<PmsSkillTopic> {
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
    public int insertPmsSkillTopic(PmsSkillTopic pmsSkillTopic);

    /**
     * 修改课题
     * 
     * @param pmsSkillTopic 课题
     * @return 结果
     */
    public int updatePmsSkillTopic(PmsSkillTopic pmsSkillTopic);

    /**
     * 删除课题
     * 
     * @param id 课题ID
     * @return 结果
     */
    public int deletePmsSkillTopicById(Long id);

    /**
     * 批量删除课题
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsSkillTopicByIds(Long[] ids);
}
