package com.zjry.pms.evaluate.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.evaluate.domain.PmsEvaluateSetType;

/**
 * 指标集分类Service接口
 * 
 * @author mk
 * @date 2022-08-03
 */
public interface IPmsEvaluateSetTypeService extends IService<PmsEvaluateSetType> {
    /**
     * 查询指标集分类
     * 
     * @param id 指标集分类ID
     * @return 指标集分类
     */
    public PmsEvaluateSetType selectPmsEvaluateSetTypeById(Long id);

    /**
     * 查询指标集分类列表
     * 
     * @param pmsEvaluateSetType 指标集分类
     * @return 指标集分类集合
     */
    public List<PmsEvaluateSetType> selectPmsEvaluateSetTypeList(PmsEvaluateSetType pmsEvaluateSetType);

    /**
     * 新增指标集分类
     * 
     * @param pmsEvaluateSetType 指标集分类
     * @return 结果
     */
    public int insertPmsEvaluateSetType(PmsEvaluateSetType pmsEvaluateSetType)throws Exception;

    /**
     * 修改指标集分类
     * 
     * @param pmsEvaluateSetType 指标集分类
     * @return 结果
     */
    public int updatePmsEvaluateSetType(PmsEvaluateSetType pmsEvaluateSetType);

    /**
     * 批量删除指标集分类
     * 
     * @param ids 需要删除的指标集分类ID
     * @return 结果
     */
    public String deletePmsEvaluateSetTypeByIds(Long[] ids);

    /**
     * 删除指标集分类信息
     * 
     * @param id 指标集分类ID
     * @return 结果
     */
    public int deletePmsEvaluateSetTypeById(Long id);
    String updateIsStop(Long id, String isStop);
}
