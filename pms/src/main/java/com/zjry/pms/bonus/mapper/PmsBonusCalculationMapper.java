package com.zjry.pms.bonus.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.bonus.domain.PmsBonusCalculation;

/**
 * 奖金核算Mapper接口
 * 
 * @author zjry
 * @date 2022-10-19
 */
public interface PmsBonusCalculationMapper extends BaseMapper<PmsBonusCalculation> {
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
     * 删除奖金核算
     * 
     * @param id 奖金核算ID
     * @return 结果
     */
    public int deletePmsBonusCalculationById(Long id);

    /**
     * 批量删除奖金核算
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsBonusCalculationByIds(Long[] ids);
}
