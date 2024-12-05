package com.zjry.pms.skill.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import com.zjry.pms.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.skill.mapper.PmsSkillTopicMapper;
import com.zjry.pms.skill.domain.PmsSkillTopic;
import com.zjry.pms.skill.service.IPmsSkillTopicService;

/**
 * 课题Service业务层处理
 * 
 * @author maozt
 * @date 2022-07-26
 */
@Service
public class PmsSkillTopicServiceImpl extends ServiceImpl<PmsSkillTopicMapper, PmsSkillTopic> implements IPmsSkillTopicService {
    @Autowired
    private PmsSkillTopicMapper pmsSkillTopicMapper;

    /**
     * 查询课题
     * 
     * @param id 课题ID
     * @return 课题
     */
    @Override
    public PmsSkillTopic selectPmsSkillTopicById(Long id) {
        return pmsSkillTopicMapper.selectPmsSkillTopicById(id);
    }

    /**
     * 查询课题列表
     * 
     * @param pmsSkillTopic 课题
     * @return 课题
     */
    @Override
    public List<PmsSkillTopic> selectPmsSkillTopicList(PmsSkillTopic pmsSkillTopic) {
        return pmsSkillTopicMapper.selectPmsSkillTopicList(pmsSkillTopic);
    }

    /**
     * 新增课题
     * 
     * @param pmsSkillTopic 课题
     * @return 结果
     */
    @Override
    public int insertPmsSkillTopic(PmsSkillTopic pmsSkillTopic) throws Exception {
    	pmsSkillTopic.preInsert();
        return pmsSkillTopicMapper.insertPmsSkillTopic(pmsSkillTopic);
    }

    /**
     * 修改课题
     * 
     * @param pmsSkillTopic 课题
     * @return 结果
     */
    @Override
    public int updatePmsSkillTopic(PmsSkillTopic pmsSkillTopic) throws Exception {
    	pmsSkillTopic.preUpdate();
        return pmsSkillTopicMapper.updatePmsSkillTopic(pmsSkillTopic);
    }

    /**
     * 批量删除课题
     * 
     * @param ids 需要删除的课题ID
     * @return 结果
     */
    @Override
    public int deletePmsSkillTopicByIds(Long[] ids) {
        return pmsSkillTopicMapper.deletePmsSkillTopicByIds(ids);
    }

    /**
     * 删除课题信息
     * 
     * @param id 课题ID
     * @return 结果
     */
    @Override
    public int deletePmsSkillTopicById(Long id) {
        return pmsSkillTopicMapper.deletePmsSkillTopicById(id);
    }
}
