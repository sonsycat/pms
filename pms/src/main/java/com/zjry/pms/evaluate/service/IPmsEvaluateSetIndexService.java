package com.zjry.pms.evaluate.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.evaluate.domain.PmsEvaSetIndexList;
import com.zjry.pms.evaluate.domain.PmsEvaluateSetIndex;

/**
 * 指标集指标Service接口
 * 
 * @author mk
 * @date 2022-08-04
 */
public interface IPmsEvaluateSetIndexService extends IService<PmsEvaluateSetIndex> {
    /**
     * 查询指标集指标
     * 
     * @param id 指标集指标ID
     * @return 指标集指标
     */
    public PmsEvaluateSetIndex selectPmsEvaluateSetIndexById(Long id);

    /**
     * 查询指标集指标列表
     * 
     * @param pmsEvaluateSetIndex 指标集指标
     * @return 指标集指标集合
     */
    public List<PmsEvaluateSetIndex> selectPmsEvaluateSetIndexList(PmsEvaluateSetIndex pmsEvaluateSetIndex);

    /**
     * 新增指标集指标
     * 
     * @param pmsEvaluateSetIndex 指标集指标
     * @return 结果
     */
    public void insertPmsEvaluateSetIndex(PmsEvaSetIndexList pmsEvaluateSetIndex);

    /**
     * 修改指标集指标
     * 
     * @param pmsEvaluateSetIndex 指标集指标
     * @return 结果
     */
    public int updatePmsEvaluateSetIndex(PmsEvaluateSetIndex pmsEvaluateSetIndex);

    /**
     * 批量删除指标集指标
     * 
     * @param ids 需要删除的指标集指标ID
     * @return 结果
     */
    public int deletePmsEvaluateSetIndexByIds(Long[] ids);

    /**
     * 删除指标集指标信息
     * 
     * @param id 指标集指标ID
     * @return 结果
     */
    public int deletePmsEvaluateSetIndexById(Long id);
}
