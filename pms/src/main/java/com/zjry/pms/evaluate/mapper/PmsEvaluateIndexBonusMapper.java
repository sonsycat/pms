package com.zjry.pms.evaluate.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.evaluate.domain.PmsEvaluateIndexBonus;

/**
 * 奖金指标Mapper接口
 * 
 * @author zjry
 * @date 2022-11-08
 */
public interface PmsEvaluateIndexBonusMapper extends BaseMapper<PmsEvaluateIndexBonus> {
    /**
     * 查询奖金指标
     * 
     * @param id 奖金指标ID
     * @return 奖金指标
     */
    public PmsEvaluateIndexBonus selectPmsEvaluateIndexBonusById(Long id);

    /**
     * 查询奖金指标列表
     * 
     * @param pmsEvaluateIndexBonus 奖金指标
     * @return 奖金指标集合
     */
    public List<PmsEvaluateIndexBonus> selectPmsEvaluateIndexBonusList(PmsEvaluateIndexBonus pmsEvaluateIndexBonus);

    /**
     * 新增奖金指标
     * 
     * @param pmsEvaluateIndexBonus 奖金指标
     * @return 结果
     */
    public int insertPmsEvaluateIndexBonus(PmsEvaluateIndexBonus pmsEvaluateIndexBonus);

    /**
     * 修改奖金指标
     * 
     * @param pmsEvaluateIndexBonus 奖金指标
     * @return 结果
     */
    public int updatePmsEvaluateIndexBonus(PmsEvaluateIndexBonus pmsEvaluateIndexBonus);

    /**
     * 删除奖金指标
     * 
     * @param id 奖金指标ID
     * @return 结果
     */
    public int deletePmsEvaluateIndexBonusById(Long id);

    /**
     * 批量删除奖金指标
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsEvaluateIndexBonusByIds(Long[] ids);

    int deleteBatch(PmsEvaluateIndexBonus pmsEvaluateIndexBonus);

    int insertBatch(List<PmsEvaluateIndexBonus> list);
}
