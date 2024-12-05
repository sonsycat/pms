package com.zjry.pms.bonus.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.pms.bonus.domain.PmsBonusPersonAudit;
import com.zjry.pms.bonus.domain.PmsBonusPersonAuditDto;

/**
 * 奖金人员审核Service接口
 *
 * @author zjry
 * @date 2022-10-25
 */
public interface IPmsBonusPersonAuditService extends IService<PmsBonusPersonAudit> {
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
     * @param param 奖金人员审核
     * @return 结果
     */
     AjaxResult insertPmsBonusPersonAudit(PmsBonusPersonAuditDto param);

    /**
     * 修改奖金人员审核
     *
     * @param pmsBonusPersonAudit 奖金人员审核
     * @return 结果
     */
    public int updatePmsBonusPersonAudit(PmsBonusPersonAudit pmsBonusPersonAudit);

    /**
     * 批量删除奖金人员审核
     *
     * @param ids 需要删除的奖金人员审核ID
     * @return 结果
     */
    public int deletePmsBonusPersonAuditByIds(Long[] ids);

    /**
     * 删除奖金人员审核信息
     *
     * @param id 奖金人员审核ID
     * @return 结果
     */
    public int deletePmsBonusPersonAuditById(Long id);

    /**
     * 审核
     * @param param
     * @return
     */
    AjaxResult audit(PmsBonusPersonAuditDto param);

    void updateData(PmsBonusPersonAudit exit);
}
