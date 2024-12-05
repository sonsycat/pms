package com.zjry.pms.bonus.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.bonus.domain.PmsBonusPersonAudit;

/**
 * 奖金人员审核Mapper接口
 * 
 * @author zjry
 * @date 2022-10-25
 */
public interface PmsBonusPersonAuditMapper extends BaseMapper<PmsBonusPersonAudit> {
    /**
     * 查询奖金人员审核
     * 
     * @param id 奖金人员审核ID
     * @return 奖金人员审核
     */
    public PmsBonusPersonAudit selectPmsBonusPersonAuditById(Long id);

    /**
     * 查询奖金人员审核列表
     * 
     * @param pmsBonusPersonAudit 奖金人员审核
     * @return 奖金人员审核集合
     */
    public List<PmsBonusPersonAudit> selectPmsBonusPersonAuditList(PmsBonusPersonAudit pmsBonusPersonAudit);

    /**
     * 新增奖金人员审核
     * 
     * @param pmsBonusPersonAudit 奖金人员审核
     * @return 结果
     */
    public int insertPmsBonusPersonAudit(PmsBonusPersonAudit pmsBonusPersonAudit);

    /**
     * 修改奖金人员审核
     * 
     * @param pmsBonusPersonAudit 奖金人员审核
     * @return 结果
     */
    public int updatePmsBonusPersonAudit(PmsBonusPersonAudit pmsBonusPersonAudit);

    /**
     * 删除奖金人员审核
     * 
     * @param id 奖金人员审核ID
     * @return 结果
     */
    public int deletePmsBonusPersonAuditById(Long id);

    /**
     * 批量删除奖金人员审核
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsBonusPersonAuditByIds(Long[] ids);
}
