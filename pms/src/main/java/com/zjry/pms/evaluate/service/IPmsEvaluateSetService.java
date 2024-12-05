package com.zjry.pms.evaluate.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.evaluate.domain.PmsEvaluateSet;

/**
 * 指标集管理Service接口
 * 
 * @author mk
 * @date 2022-08-04
 */
public interface IPmsEvaluateSetService extends IService<PmsEvaluateSet> {
    /**
     * 查询指标集管理
     * 
     * @param id 指标集管理ID
     * @return 指标集管理
     */
    public PmsEvaluateSet selectPmsEvaluateSetById(Long id);

    /**
     * 查询指标集管理列表
     * 
     * @param pmsEvaluateSet 指标集管理
     * @return 指标集管理集合
     */
    public List<PmsEvaluateSet> selectPmsEvaluateSetList(PmsEvaluateSet pmsEvaluateSet);

    /**
     * 新增指标集管理
     * 
     * @param pmsEvaluateSet 指标集管理
     * @return 结果
     */
    public int insertPmsEvaluateSet(PmsEvaluateSet pmsEvaluateSet);

    /**
     * 修改指标集管理
     * 
     * @param pmsEvaluateSet 指标集管理
     * @return 结果
     */
    public int updatePmsEvaluateSet(PmsEvaluateSet pmsEvaluateSet);

    /**
     * 批量删除指标集管理
     * 
     * @param ids 需要删除的指标集管理ID
     * @return 结果
     */
    public int deletePmsEvaluateSetByIds(Long[] ids);

    /**
     * 删除指标集管理信息
     * 
     * @param id 指标集管理ID
     * @return 结果
     */
    public int deletePmsEvaluateSetById(Long id);

    void copy(PmsEvaluateSet pmsEvaluateSet) throws Exception;
}
