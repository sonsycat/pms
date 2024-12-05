package com.zjry.pms.skill.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.skill.domain.PmsSkillMonograph;

/**
 * 专著Service接口
 * 
 * @author maozt
 * @date 2022-08-02
 */
public interface IPmsSkillMonographService extends IService<PmsSkillMonograph> {
    /**
     * 查询专著
     * 
     * @param id 专著ID
     * @return 专著
     */
    public PmsSkillMonograph selectPmsSkillMonographById(Long id);

    /**
     * 查询专著列表
     * 
     * @param pmsSkillMonograph 专著
     * @return 专著集合
     */
    public List<PmsSkillMonograph> selectPmsSkillMonographList(PmsSkillMonograph pmsSkillMonograph);

    /**
     * 查询专著列表
     *
     * @param pmsSkillMonograph 专著
     * @return 专著集合
     */
    public List<PmsSkillMonograph> selectPmsSkillMonographListByName(PmsSkillMonograph pmsSkillMonograph);

    /**
     * 新增专著
     * 
     * @param pmsSkillMonograph 专著
     * @return 结果
     */
    public int insertPmsSkillMonograph(PmsSkillMonograph pmsSkillMonograph);

    /**
     * 修改专著
     * 
     * @param pmsSkillMonograph 专著
     * @return 结果
     */
    public int updatePmsSkillMonograph(PmsSkillMonograph pmsSkillMonograph);

    /**
     * 批量删除专著
     * 
     * @param ids 需要删除的专著ID
     * @return 结果
     */
    public int deletePmsSkillMonographByIds(Long[] ids);

    /**
     * 删除专著信息
     * 
     * @param id 专著ID
     * @return 结果
     */
    public int deletePmsSkillMonographById(Long id);
}
