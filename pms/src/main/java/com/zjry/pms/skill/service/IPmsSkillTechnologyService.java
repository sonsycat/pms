package com.zjry.pms.skill.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.skill.domain.PmsSkillTechnology;

/**
 * 新业务新技术Service接口
 * 
 * @author maozt
 * @date 2022-08-02
 */
public interface IPmsSkillTechnologyService extends IService<PmsSkillTechnology> {
    /**
     * 查询新业务新技术
     * 
     * @param id 新业务新技术ID
     * @return 新业务新技术
     */
    public PmsSkillTechnology selectPmsSkillTechnologyById(Long id);

    /**
     * 查询新业务新技术列表
     * 
     * @param pmsSkillTechnology 新业务新技术
     * @return 新业务新技术集合
     */
    public List<PmsSkillTechnology> selectPmsSkillTechnologyList(PmsSkillTechnology pmsSkillTechnology);

    /**
     * 查询新业务新技术列表
     *
     * @param pmsSkillTechnology 新业务新技术
     * @return 新业务新技术集合
     */
    public List<PmsSkillTechnology> selectPmsSkillTechnologyListByName(PmsSkillTechnology pmsSkillTechnology);

    /**
     * 新增新业务新技术
     * 
     * @param pmsSkillTechnology 新业务新技术
     * @return 结果
     */
    public int insertPmsSkillTechnology(PmsSkillTechnology pmsSkillTechnology);

    /**
     * 修改新业务新技术
     * 
     * @param pmsSkillTechnology 新业务新技术
     * @return 结果
     */
    public int updatePmsSkillTechnology(PmsSkillTechnology pmsSkillTechnology);

    /**
     * 批量删除新业务新技术
     * 
     * @param ids 需要删除的新业务新技术ID
     * @return 结果
     */
    public int deletePmsSkillTechnologyByIds(Long[] ids);

    /**
     * 删除新业务新技术信息
     * 
     * @param id 新业务新技术ID
     * @return 结果
     */
    public int deletePmsSkillTechnologyById(Long id);
}
