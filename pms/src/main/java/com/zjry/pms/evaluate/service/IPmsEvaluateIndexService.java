package com.zjry.pms.evaluate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.evaluate.domain.PmsEvaluateData;
import com.zjry.pms.evaluate.domain.PmsEvaluateIndex;

import java.util.List;
import java.util.Map;

/**
 * 指标管理Service接口
 * 
 * @author mk
 * @date 2022-07-26
 */
public interface IPmsEvaluateIndexService extends IService<PmsEvaluateIndex> {
    /**
     * 查询指标管理
     * 
     * @param id 指标管理ID
     * @return 指标管理
     */
    PmsEvaluateIndex selectPmsEvaluateIndexById(Long id);

    /**
     * 查询指标管理列表
     * 
     * @param pmsEvaluateIndex 指标管理
     * @return 指标管理集合
     */
    List<PmsEvaluateIndex> selectPmsEvaluateIndexList(PmsEvaluateIndex pmsEvaluateIndex);

    /**
     * 新增指标管理
     * 
     * @param pmsEvaluateIndex 指标管理
     * @return 结果
     */
    int insertPmsEvaluateIndex(PmsEvaluateIndex pmsEvaluateIndex);

    /**
     * 修改指标管理
     * 
     * @param pmsEvaluateIndex 指标管理
     * @return 结果
     */
    int updatePmsEvaluateIndex(PmsEvaluateIndex pmsEvaluateIndex);

    /**
     * 批量删除指标管理
     * 
     * @param ids 需要删除的指标管理ID
     * @return 结果
     */
    int deletePmsEvaluateIndexByIds(Long[] ids);

    /**
     * 删除指标管理信息
     * 
     * @param id 指标管理ID
     * @return 结果
     */
    int deletePmsEvaluateIndexById(Long id);

    List<PmsEvaluateIndex> createDataInfo(PmsEvaluateIndex pmsEvaluateIndex) throws Exception;

    /**
     * 从数据源获取数据
     */
    List<PmsEvaluateData> generateDataFromSql(PmsEvaluateIndex pmsEvaluateIndex) throws Exception;

    /**
     * 通过表达式生成数据
     */
    List<PmsEvaluateData> generateDataFromExpr(PmsEvaluateIndex pmsEvaluateIndex, Map<String, PmsEvaluateIndex> indexMap, Map<String, List<PmsEvaluateData>> dataListMap);

    /**
     * 指标计算
     * @param actDate
     */
    List<PmsEvaluateData> calculateEvaluateIndex(String actDate);
    /**
     * 指标计算-科级
     * @param actDate
     */
    void calculateEvaluateIndexOfBSC(String actDate);
    /**
     * 指标计算-奖金
     * @param actDate
     */
    void calculateEvaluateIndexOfBonus(String actDate);
    /**
     * 指标计算-二次核算（人员分配）
     * @param actDate
     */
    List<PmsEvaluateData> calculateEvaluateIndexOfDoctor(String actDate);

    /**
     * 指标是否被指标集引用
     * @param id
     * @return
     */
    boolean checkIsUsedInSet(String indexCode);
}
