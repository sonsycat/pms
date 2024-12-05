package com.zjry.pms.bonus.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.pms.bonus.domain.PmsBonusCalculation;

/**
 * 奖金核算Service接口
 *
 * @author zjry
 * @date 2022-10-19
 */
public interface IPmsBonusCalculationService extends IService<PmsBonusCalculation> {

    /**
     * 查询奖金核算
     *
     * @param id 奖金核算ID
     * @return 奖金核算
     */
    public PmsBonusCalculation selectPmsBonusCalculationById(Long id);

    /**
     * 查询奖金核算列表
     *
     * @param pmsBonusCalculation 奖金核算
     * @return 奖金核算集合
     */
    public List<PmsBonusCalculation> selectPmsBonusCalculationList(PmsBonusCalculation pmsBonusCalculation);

    /**
     * 新增奖金核算
     *
     * @param pmsBonusCalculation 奖金核算
     * @return 结果
     */
    public int insertPmsBonusCalculation(PmsBonusCalculation pmsBonusCalculation);

    /**
     * 修改奖金核算
     *
     * @param pmsBonusCalculation 奖金核算
     * @return 结果
     */
    public int updatePmsBonusCalculation(PmsBonusCalculation pmsBonusCalculation);

    /**
     * 批量删除奖金核算
     *
     * @param ids 需要删除的奖金核算ID
     * @return 结果
     */
    public int deletePmsBonusCalculationByIds(Long[] ids);

    /**
     * 删除奖金核算信息
     *
     * @param id 奖金核算ID
     * @return 结果
     */
    public int deletePmsBonusCalculationById(Long id);

    /**
     * 提交审核
     *
     * @param pmsBonusCalculation
     * @return
     */
    AjaxResult commitAudit(PmsBonusCalculation pmsBonusCalculation);

    /**
     * 获取流程id
     *
     * @param param
     * @return
     */
    AjaxResult getProId(PmsBonusCalculation param);

    /**
     * 审核
     * @param pmsBonusCalculation
     * @return
     */
    AjaxResult audit(PmsBonusCalculation pmsBonusCalculation);

}
