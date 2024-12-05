package com.zjry.pms.project.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.project.domain.CostProjectNumSql;

/**
 * 成本sqlMapper接口
 * 
 * @author mk
 * @date 2022-05-18
 */
public interface CostProjectNumSqlMapper extends BaseMapper<CostProjectNumSql> {
    /**
     * 查询成本sql
     * 
     * @param id 成本sqlID
     * @return 成本sql
     */
    public CostProjectNumSql selectCostProjectNumSqlById(Long id);

    /**
     * 查询成本sql列表
     * 
     * @param costProjectNumSql 成本sql
     * @return 成本sql集合
     */
    public List<CostProjectNumSql> selectCostProjectNumSqlList(CostProjectNumSql costProjectNumSql);

    /**
     * 新增成本sql
     * 
     * @param costProjectNumSql 成本sql
     * @return 结果
     */
    public int insertCostProjectNumSql(CostProjectNumSql costProjectNumSql);

    /**
     * 修改成本sql
     * 
     * @param costProjectNumSql 成本sql
     * @return 结果
     */
    public int updateCostProjectNumSql(CostProjectNumSql costProjectNumSql);

    /**
     * 删除成本sql
     * 
     * @param id 成本sqlID
     * @return 结果
     */
    public int deleteCostProjectNumSqlById(Long id);

    /**
     * 批量删除成本sql
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCostProjectNumSqlByIds(Long[] ids);
}
