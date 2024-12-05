package com.zjry.pms.project.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.project.domain.CostProjectNumSql;

/**
 * 成本sqlService接口
 * 
 * @author mk
 * @date 2022-05-18
 */
public interface ICostProjectNumSqlService extends IService<CostProjectNumSql> {
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
     * 批量删除成本sql
     * 
     * @param ids 需要删除的成本sqlID
     * @return 结果
     */
    public int deleteCostProjectNumSqlByIds(Long[] ids);

    /**
     * 删除成本sql信息
     * 
     * @param id 成本sqlID
     * @return 结果
     */
    public int deleteCostProjectNumSqlById(Long id);

    String inertBySql(CostProjectNumSql costProjectNumSql) throws Exception;
}
