package com.zjry.pms.skill.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.skill.domain.PmsSkillAchieve;

/**
 * 成果Mapper接口
 * 
 * @author maozt
 * @date 2022-08-01
 */
public interface PmsSkillAchieveMapper extends BaseMapper<PmsSkillAchieve> {
    /**
     * 查询成果
     * 
     * @param id 成果ID
     * @return 成果
     */
    public PmsSkillAchieve selectPmsSkillAchieveById(Long id);

    /**
     * 查询成果列表
     * 
     * @param pmsSkillAchieve 成果
     * @return 成果集合
     */
    public List<PmsSkillAchieve> selectPmsSkillAchieveList(PmsSkillAchieve pmsSkillAchieve);

    /**
     * 查询成果列表
     *
     * @param pmsSkillAchieve 成果
     * @return 成果集合
     */
    public List<PmsSkillAchieve> selectPmsSkillAchieveListByName(PmsSkillAchieve pmsSkillAchieve);

    /**
     * 新增成果
     * 
     * @param pmsSkillAchieve 成果
     * @return 结果
     */
    public int insertPmsSkillAchieve(PmsSkillAchieve pmsSkillAchieve);

    /**
     * 修改成果
     * 
     * @param pmsSkillAchieve 成果
     * @return 结果
     */
    public int updatePmsSkillAchieve(PmsSkillAchieve pmsSkillAchieve);

    /**
     * 删除成果
     * 
     * @param id 成果ID
     * @return 结果
     */
    public int deletePmsSkillAchieveById(Long id);

    /**
     * 批量删除成果
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsSkillAchieveByIds(Long[] ids);
}
