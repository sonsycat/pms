package com.zjry.pms.evaluate.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.evaluate.domain.PmsEvaluateSetType;

/**
 * 指标集分类Mapper接口
 * 
 * @author mk
 * @date 2022-08-03
 */
public interface PmsEvaluateSetTypeMapper extends BaseMapper<PmsEvaluateSetType> {
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
    public int insertPmsEvaluateSetType(PmsEvaluateSetType pmsEvaluateSetType);

    /**
     * 修改指标集分类
     * 
     * @param pmsEvaluateSetType 指标集分类
     * @return 结果
     */
    public int updatePmsEvaluateSetType(PmsEvaluateSetType pmsEvaluateSetType);

    /**
     * 删除指标集分类
     * 
     * @param id 指标集分类ID
     * @return 结果
     */
    public int deletePmsEvaluateSetTypeById(Long id);

    /**
     * 批量删除指标集分类
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsEvaluateSetTypeByIds(Long[] ids);

    PmsEvaluateSetType getBecCode(String parentCode);
}
