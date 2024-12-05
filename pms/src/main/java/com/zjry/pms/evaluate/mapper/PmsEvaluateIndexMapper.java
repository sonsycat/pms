package com.zjry.pms.evaluate.mapper;

import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.evaluate.domain.PmsEvaluateIndex;
import com.zjry.pms.evaluate.domain.PmsEvaluateIndexDto;
import org.apache.ibatis.annotations.Param;

/**
 * 指标管理Mapper接口
 * 
 * @author mk
 * @date 2022-07-26
 */
public interface PmsEvaluateIndexMapper extends BaseMapper<PmsEvaluateIndex> {
    /**
     * 查询指标管理
     * 
     * @param id 指标管理ID
     * @return 指标管理
     */
    public PmsEvaluateIndex selectPmsEvaluateIndexById(Long id);

    /**
     * 查询指标管理列表
     * 
     * @param pmsEvaluateIndex 指标管理
     * @return 指标管理集合
     */
    public List<PmsEvaluateIndex> selectPmsEvaluateIndexList(PmsEvaluateIndex pmsEvaluateIndex);

    /**
     * 新增指标管理
     * 
     * @param pmsEvaluateIndex 指标管理
     * @return 结果
     */
    public int insertPmsEvaluateIndex(PmsEvaluateIndex pmsEvaluateIndex);

    /**
     * 修改指标管理
     * 
     * @param pmsEvaluateIndex 指标管理
     * @return 结果
     */
    public int updatePmsEvaluateIndex(PmsEvaluateIndex pmsEvaluateIndex);

    /**
     * 删除指标管理
     * 
     * @param id 指标管理ID
     * @return 结果
     */
    public int deletePmsEvaluateIndexById(Long id);

    /**
     * 批量删除指标管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsEvaluateIndexByIds(Long[] ids);

    /**
     * 获取对应考核指标结果集
     * @param actDate
     * @return
     */
    List<PmsEvaluateIndexDto> selectEvaluateSetIndexDtoOfBSC(@Param("compCode") Long compCode, @Param("actDate") String actDate, @Param("isBonus")String isBonus);

    /**
     * 获取对应考核指标结果集
     * @param actDate
     * @return
     */
    List<PmsEvaluateIndexDto> selectEvaluateSetIndexDtoOfBonus(@Param("compCode") Long compCode, @Param("actDate") String actDate, @Param("isBonus")String isBonus);

    /**
     * 指标是否被指标集引用
     * @param id
     * @return
     */
    int checkIsUsedInSet(String indexCode);
}
