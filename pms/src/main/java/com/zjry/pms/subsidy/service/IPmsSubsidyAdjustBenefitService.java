package com.zjry.pms.subsidy.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.subsidy.domain.PmsSubsidyAdjustBenefit;

/**
 * 科室效益调整Service接口
 * 
 * @author maozt
 * @date 2022-08-17
 */
public interface IPmsSubsidyAdjustBenefitService extends IService<PmsSubsidyAdjustBenefit> {
    /**
     * 查询科室效益调整
     * 
     * @param id 科室效益调整ID
     * @return 科室效益调整
     */
    public PmsSubsidyAdjustBenefit selectPmsSubsidyAdjustBenefitById(Long id);

    /**
     * 查询科室效益调整列表
     * 
     * @param pmsSubsidyAdjustBenefit 科室效益调整
     * @return 科室效益调整集合
     */
    public List<PmsSubsidyAdjustBenefit> selectPmsSubsidyAdjustBenefitList(PmsSubsidyAdjustBenefit pmsSubsidyAdjustBenefit);

    /**
     * 新增科室效益调整
     * 
     * @param pmsSubsidyAdjustBenefit 科室效益调整
     * @return 结果
     */
    public int insertPmsSubsidyAdjustBenefit(PmsSubsidyAdjustBenefit pmsSubsidyAdjustBenefit);

    /**
     * 修改科室效益调整
     * 
     * @param pmsSubsidyAdjustBenefit 科室效益调整
     * @return 结果
     */
    public int updatePmsSubsidyAdjustBenefit(PmsSubsidyAdjustBenefit pmsSubsidyAdjustBenefit);

    /**
     * 批量删除科室效益调整
     * 
     * @param ids 需要删除的科室效益调整ID
     * @return 结果
     */
    public int deletePmsSubsidyAdjustBenefitByIds(Long[] ids);

    /**
     * 删除科室效益调整信息
     * 
     * @param id 科室效益调整ID
     * @return 结果
     */
    public int deletePmsSubsidyAdjustBenefitById(Long id);
}
