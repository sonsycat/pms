package com.zjry.pms.evaluate.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.evaluate.domain.PmsEvaluateIndexBonus;
import com.zjry.pms.evaluate.domain.PmsEvaluateIndexBonusDto;

/**
 * 奖金指标Service接口
 * 
 * @author zjry
 * @date 2022-11-08
 */
public interface IPmsEvaluateIndexBonusService extends IService<PmsEvaluateIndexBonus> {
    /**
     * 查询奖金指标
     * 
     * @param id 奖金指标ID
     * @return 奖金指标
     */
    PmsEvaluateIndexBonus selectPmsEvaluateIndexBonusById(Long id);

    /**
     * 查询奖金指标列表
     * 
     * @param pmsEvaluateIndexBonus 奖金指标
     * @return 奖金指标集合
     */
    List<PmsEvaluateIndexBonus> selectPmsEvaluateIndexBonusList(PmsEvaluateIndexBonus pmsEvaluateIndexBonus);

    /**
     * 新增奖金指标
     * 
     * @param pmsEvaluateIndexBonus 奖金指标
     * @return 结果
     */
    int insertPmsEvaluateIndexBonus(PmsEvaluateIndexBonus pmsEvaluateIndexBonus);

    /**
     * 修改奖金指标
     * 
     * @param pmsEvaluateIndexBonus 奖金指标
     * @return 结果
     */
    int updatePmsEvaluateIndexBonus(PmsEvaluateIndexBonus pmsEvaluateIndexBonus);

    /**
     * 批量删除奖金指标
     * 
     * @param ids 需要删除的奖金指标ID
     * @return 结果
     */
    int deletePmsEvaluateIndexBonusByIds(Long[] ids);

    /**
     * 删除奖金指标信息
     * 
     * @param id 奖金指标ID
     * @return 结果
     */
    int deletePmsEvaluateIndexBonusById(Long id);

    /**
     * 批量保存
     * @param list
     * @return
     */
    int saveBatch(PmsEvaluateIndexBonusDto pmsEvaluateIndexBonusDto);
}
