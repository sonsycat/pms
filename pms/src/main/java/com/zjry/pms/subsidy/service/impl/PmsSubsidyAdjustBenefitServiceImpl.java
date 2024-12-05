package com.zjry.pms.subsidy.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.subsidy.mapper.PmsSubsidyAdjustBenefitMapper;
import com.zjry.pms.subsidy.domain.PmsSubsidyAdjustBenefit;
import com.zjry.pms.subsidy.service.IPmsSubsidyAdjustBenefitService;

/**
 * 科室效益调整Service业务层处理
 * 
 * @author maozt
 * @date 2022-08-17
 */
@Service
public class PmsSubsidyAdjustBenefitServiceImpl extends ServiceImpl<PmsSubsidyAdjustBenefitMapper, PmsSubsidyAdjustBenefit> implements IPmsSubsidyAdjustBenefitService {
    @Autowired
    private PmsSubsidyAdjustBenefitMapper pmsSubsidyAdjustBenefitMapper;

    /**
     * 查询科室效益调整
     * 
     * @param id 科室效益调整ID
     * @return 科室效益调整
     */
    @Override
    public PmsSubsidyAdjustBenefit selectPmsSubsidyAdjustBenefitById(Long id) {
        return pmsSubsidyAdjustBenefitMapper.selectPmsSubsidyAdjustBenefitById(id);
    }

    /**
     * 查询科室效益调整列表
     * 
     * @param pmsSubsidyAdjustBenefit 科室效益调整
     * @return 科室效益调整
     */
    @Override
    public List<PmsSubsidyAdjustBenefit> selectPmsSubsidyAdjustBenefitList(PmsSubsidyAdjustBenefit pmsSubsidyAdjustBenefit) {
        return pmsSubsidyAdjustBenefitMapper.selectPmsSubsidyAdjustBenefitList(pmsSubsidyAdjustBenefit);
    }

    /**
     * 新增科室效益调整
     * 
     * @param pmsSubsidyAdjustBenefit 科室效益调整
     * @return 结果
     */
    @Override
    public int insertPmsSubsidyAdjustBenefit(PmsSubsidyAdjustBenefit pmsSubsidyAdjustBenefit) {
    	pmsSubsidyAdjustBenefit.preInsert();
        return pmsSubsidyAdjustBenefitMapper.insertPmsSubsidyAdjustBenefit(pmsSubsidyAdjustBenefit);
    }

    /**
     * 修改科室效益调整
     * 
     * @param pmsSubsidyAdjustBenefit 科室效益调整
     * @return 结果
     */
    @Override
    public int updatePmsSubsidyAdjustBenefit(PmsSubsidyAdjustBenefit pmsSubsidyAdjustBenefit) {
    	pmsSubsidyAdjustBenefit.preUpdate();
        return pmsSubsidyAdjustBenefitMapper.updatePmsSubsidyAdjustBenefit(pmsSubsidyAdjustBenefit);
    }

    /**
     * 批量删除科室效益调整
     * 
     * @param ids 需要删除的科室效益调整ID
     * @return 结果
     */
    @Override
    public int deletePmsSubsidyAdjustBenefitByIds(Long[] ids) {
        return pmsSubsidyAdjustBenefitMapper.deletePmsSubsidyAdjustBenefitByIds(ids);
    }

    /**
     * 删除科室效益调整信息
     * 
     * @param id 科室效益调整ID
     * @return 结果
     */
    @Override
    public int deletePmsSubsidyAdjustBenefitById(Long id) {
        return pmsSubsidyAdjustBenefitMapper.deletePmsSubsidyAdjustBenefitById(id);
    }
}
