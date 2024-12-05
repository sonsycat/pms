package com.zjry.pms.skill.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.skill.domain.PmsSkillMonograph;

/**
 * 专著Mapper接口
 * 
 * @author maozt
 * @date 2022-08-02
 */
public interface PmsSkillMonographMapper extends BaseMapper<PmsSkillMonograph> {
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
     * 删除专著
     * 
     * @param id 专著ID
     * @return 结果
     */
    public int deletePmsSkillMonographById(Long id);

    /**
     * 批量删除专著
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsSkillMonographByIds(Long[] ids);
}
