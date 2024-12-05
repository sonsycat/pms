package com.zjry.pms.skill.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.skill.domain.PmsSkillThesis;

/**
 * 论文Mapper接口
 * 
 * @author maozt
 * @date 2022-07-28
 */
public interface PmsSkillThesisMapper extends BaseMapper<PmsSkillThesis> {
    /**
     * 查询论文
     * 
     * @param id 论文ID
     * @return 论文
     */
    public PmsSkillThesis selectPmsSkillThesisById(Long id);

    /**
     * 查询论文列表
     * 
     * @param pmsSkillThesis 论文
     * @return 论文集合
     */
    public List<PmsSkillThesis> selectPmsSkillThesisList(PmsSkillThesis pmsSkillThesis);

    /**
     * 查询论文列表
     *
     * @param pmsSkillThesis 论文
     * @return 论文集合
     */
    public List<PmsSkillThesis> selectPmsSkillThesisListByName(PmsSkillThesis pmsSkillThesis);

    /**
     * 新增论文
     * 
     * @param pmsSkillThesis 论文
     * @return 结果
     */
    public int insertPmsSkillThesis(PmsSkillThesis pmsSkillThesis);

    /**
     * 修改论文
     * 
     * @param pmsSkillThesis 论文
     * @return 结果
     */
    public int updatePmsSkillThesis(PmsSkillThesis pmsSkillThesis);

    /**
     * 删除论文
     * 
     * @param id 论文ID
     * @return 结果
     */
    public int deletePmsSkillThesisById(Long id);

    /**
     * 批量删除论文
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsSkillThesisByIds(Long[] ids);
}
