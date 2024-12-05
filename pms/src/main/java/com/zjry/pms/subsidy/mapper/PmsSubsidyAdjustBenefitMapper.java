package com.zjry.pms.subsidy.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.subsidy.domain.PmsSubsidyAdjustBenefit;

/**
 * 科室效益调整Mapper接口
 * 
 * @author maozt
 * @date 2022-08-17
 */
public interface PmsSubsidyAdjustBenefitMapper extends BaseMapper<PmsSubsidyAdjustBenefit> {
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
     * 删除科室效益调整
     * 
     * @param id 科室效益调整ID
     * @return 结果
     */
    public int deletePmsSubsidyAdjustBenefitById(Long id);

    /**
     * 批量删除科室效益调整
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsSubsidyAdjustBenefitByIds(Long[] ids);
}
