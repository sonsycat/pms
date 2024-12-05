package com.zjry.pms.evaluate.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.evaluate.domain.PmsEvaluateBscSort;
import com.zjry.pms.evaluate.domain.PmsEvaluateSetDept;
import com.zjry.pms.evaluate.domain.SetDeptDNList;

/**
 * 指标集与科室管理Service接口
 * 
 * @author mk
 * @date 2022-08-09
 */
public interface IPmsEvaluateSetDeptService extends IService<PmsEvaluateSetDept> {
    /**
     * 查询指标集与科室管理
     * 
     * @param id 指标集与科室管理ID
     * @return 指标集与科室管理
     */
    public PmsEvaluateSetDept selectPmsEvaluateSetDeptById(Long id);

    public List<PmsEvaluateSetDept> selectPmsSetDeptList(PmsEvaluateSetDept pmsEvaluateSetDept);

    /**
     * 查询指标集与科室管理列表
     * 
     * @param pmsEvaluateSetDept 指标集与科室管理
     * @return 指标集与科室管理集合
     */
    public List<PmsEvaluateSetDept> selectPmsEvaluateSetDeptList(PmsEvaluateSetDept pmsEvaluateSetDept);

    /**
     * 新增指标集与科室管理
     * 
     * @param pmsEvaluateSetDept 指标集与科室管理
     * @return 结果
     */
    public int insertPmsEvaluateSetDept(PmsEvaluateSetDept pmsEvaluateSetDept);

    /**
     * 修改指标集与科室管理
     * 
     * @param pmsEvaluateSetDept 指标集与科室管理
     * @return 结果
     */
    public int updatePmsEvaluateSetDept(PmsEvaluateSetDept pmsEvaluateSetDept);

    /**
     * 批量删除指标集与科室管理
     * 
     * @param ids 需要删除的指标集与科室管理ID
     * @return 结果
     */
    public int deletePmsEvaluateSetDeptByIds(Long[] ids);

    /**
     * 删除指标集与科室管理信息
     * 
     * @param id 指标集与科室管理ID
     * @return 结果
     */
    public int deletePmsEvaluateSetDeptById(Long id);

    void copy(List<PmsEvaluateSetDept> listN, Date copyDate) throws Exception;

    void savePmsEvaSetDeptDN(SetDeptDNList setDeptDNList) throws Exception;

    /**
     * 获取指标集与科室管理详细信息
     * @param setCode 指标编码
     * @return 结果
     */
    List<PmsEvaluateBscSort> selectBscSort(String setCode);
}
