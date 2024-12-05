package com.zjry.pms.evaluate.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.evaluate.domain.PmsEvaluateBscSort;
import com.zjry.pms.evaluate.domain.PmsEvaluateSetDept;

/**
 * 指标集与科室管理Mapper接口
 * 
 * @author mk
 * @date 2022-08-09
 */
public interface PmsEvaluateSetDeptMapper extends BaseMapper<PmsEvaluateSetDept> {
    /**
     * 查询指标集与科室管理
     * 
     * @param id 指标集与科室管理ID
     * @return 指标集与科室管理
     */
    public PmsEvaluateSetDept selectPmsEvaluateSetDeptById(Long id);

    /**
     * 查询指标集与科室管理列表
     * 
     * @param pmsEvaluateSetDept 指标集与科室管理
     * @return 指标集与科室管理集合
     */
    public List<PmsEvaluateSetDept> selectPmsEvaluateSetDeptList(PmsEvaluateSetDept pmsEvaluateSetDept);
    public List<PmsEvaluateSetDept> selectPmsSetDeptList(PmsEvaluateSetDept pmsEvaluateSetDept);


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
     * 删除指标集与科室管理
     * 
     * @param id 指标集与科室管理ID
     * @return 结果
     */
    public int deletePmsEvaluateSetDeptById(Long id);

    /**
     * 批量删除指标集与科室管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsEvaluateSetDeptByIds(Long[] ids);

    /**
     * 获取指标集与科室管理详细信息
     * @param setCode 指标编码
     * @return 结果
     */
    List<PmsEvaluateBscSort> selectBscSort(String setCode);
}
