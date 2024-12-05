package com.zjry.pms.evaluate.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.evaluate.domain.PmsEvaluateSetIndex;
import org.apache.ibatis.annotations.Param;

/**
 * 指标集指标Mapper接口
 * 
 * @author mk
 * @date 2022-08-04
 */
public interface PmsEvaluateSetIndexMapper extends BaseMapper<PmsEvaluateSetIndex> {
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
    public int insertPmsEvaluateSetIndex(PmsEvaluateSetIndex pmsEvaluateSetIndex);

    /**
     * 修改指标集指标
     * 
     * @param pmsEvaluateSetIndex 指标集指标
     * @return 结果
     */
    public int updatePmsEvaluateSetIndex(PmsEvaluateSetIndex pmsEvaluateSetIndex);

    /**
     * 删除指标集指标
     * 
     * @param id 指标集指标ID
     * @return 结果
     */
    public int deletePmsEvaluateSetIndexById(Long id);

    /**
     * 批量删除指标集指标
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsEvaluateSetIndexByIds(Long[] ids);

    void deleteByCompCodeAndsetCode(@Param("compCode") Long compCode,@Param("setCode")  String setCode);
}
