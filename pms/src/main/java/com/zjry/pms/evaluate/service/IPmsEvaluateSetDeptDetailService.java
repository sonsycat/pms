package com.zjry.pms.evaluate.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.evaluate.domain.*;

/**
 * 指标集与科室管理明细Service接口
 *
 * @author mk
 * @date 2022-08-09
 */
public interface IPmsEvaluateSetDeptDetailService extends IService<PmsEvaluateSetDeptDetail> {

    /**
     * 查询指标集与科室管理明细
     *
     * @param id 指标集与科室管理明细ID
     * @return 指标集与科室管理明细
     */
    public PmsEvaluateSetDeptDetail selectPmsEvaluateSetDeptDetailById(Long id);

    /**
     * 查询指标集与科室管理明细列表
     *
     * @param pmsEvaluateSetDeptDetail 指标集与科室管理明细
     * @return 指标集与科室管理明细集合
     */
    public List<PmsEvaluateSetDeptDetail> selectPmsEvaluateSetDeptDetailList(PmsEvaluateSetDeptDetail pmsEvaluateSetDeptDetail);

    /**
     * 新增指标集与科室管理明细
     *
     * @param pmsEvaSetDeptDetailList 指标集与科室管理明细
     * @return 结果
     */
    public void insertPmsEvaluateSetDeptDetail(PmsEvaSetDeptDetailList pmsEvaSetDeptDetailList);

    /**
     * 修改指标集与科室管理明细
     *
     * @param pmsEvaluateSetDeptDetail 指标集与科室管理明细
     * @return 结果
     */
    public int updatePmsEvaluateSetDeptDetail(PmsEvaluateSetDeptDetail pmsEvaluateSetDeptDetail);

    /**
     * 批量删除指标集与科室管理明细
     *
     * @param ids 需要删除的指标集与科室管理明细ID
     * @return 结果
     */
    public int deletePmsEvaluateSetDeptDetailByIds(Long[] ids);

    /**
     * 删除指标集与科室管理明细信息
     *
     * @param id 指标集与科室管理明细ID
     * @return 结果
     */
    public int deletePmsEvaluateSetDeptDetailById(Long id);

    public List<PmsEvaSetDeptDN> selectPmsForDKSN(PmsEvaluateSetDeptDetail pmsEvaluateSetDeptDetail);

    List<PmsEvaluateSetDeptDetail> selectPmsForDKSY(PmsEvaluateSetDeptDetail pmsEvaluateSetDeptDetail);

    void savePmsForDKSY(SetDeptDyList setDeptDyList);

    void calculateScorecardByMonth(Date date);

    void calculateBalanceScore(Date date, List<PmsEvaluateData> evaluateDataList);

    BigDecimal calculationIndexScore(EvaluateSetDeptDetailDto param);

    /**
     * 查询该科室对应BSC分类及总分
     * @param id
     * @return
     */
    List<PmsEvaluateBscSort> getBscScoreById(Long id);
}
