package com.zjry.pms.skill.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.skill.domain.PmsSkillPerson;

/**
 * 技术参与人Mapper接口
 * 
 * @author maozt
 * @date 2022-08-01
 */
public interface PmsSkillPersonMapper extends BaseMapper<PmsSkillPerson> {
    /**
     * 查询技术参与人
     * 
     * @param id 技术参与人ID
     * @return 技术参与人
     */
    public PmsSkillPerson selectPmsSkillPersonById(Long id);

    /**
     * 查询技术参与人列表
     * 
     * @param pmsSkillPerson 技术参与人
     * @return 技术参与人集合
     */
    public List<PmsSkillPerson> selectPmsSkillPersonList(PmsSkillPerson pmsSkillPerson);

    /**
     * 新增技术参与人
     * 
     * @param pmsSkillPerson 技术参与人
     * @return 结果
     */
    public int insertPmsSkillPerson(PmsSkillPerson pmsSkillPerson);

    /**
     * 修改技术参与人
     * 
     * @param pmsSkillPerson 技术参与人
     * @return 结果
     */
    public int updatePmsSkillPerson(PmsSkillPerson pmsSkillPerson);

    /**
     * 删除技术参与人
     * 
     * @param id 技术参与人ID
     * @return 结果
     */
    public int deletePmsSkillPersonById(Long id);

    /**
     * 批量删除技术参与人
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsSkillPersonByIds(Long[] ids);

    /**
     * 删除技术参与人信息
     *
     * @param pmsSkillPerson 技术参与人
     * @return 结果
     */
    public int deletePmsSkillPerson(PmsSkillPerson pmsSkillPerson);
}
