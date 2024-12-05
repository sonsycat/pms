package com.zjry.pms.revenue.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.revenue.domain.PmsRevenueNumSql;

/**
 * 收入sqlService接口
 * 
 * @author mk
 * @date 2022-06-23
 */
public interface IPmsRevenueNumSqlService extends IService<PmsRevenueNumSql> {
    /**
     * 查询收入sql
     * 
     * @param id 收入sqlID
     * @return 收入sql
     */
    public PmsRevenueNumSql selectPmsRevenueNumSqlById(Long id);

    /**
     * 查询收入sql列表
     * 
     * @param pmsRevenueNumSql 收入sql
     * @return 收入sql集合
     */
    public List<PmsRevenueNumSql> selectPmsRevenueNumSqlList(PmsRevenueNumSql pmsRevenueNumSql);

    /**
     * 新增收入sql
     * 
     * @param pmsRevenueNumSql 收入sql
     * @return 结果
     */
    public int insertPmsRevenueNumSql(PmsRevenueNumSql pmsRevenueNumSql);

    /**
     * 修改收入sql
     * 
     * @param pmsRevenueNumSql 收入sql
     * @return 结果
     */
    public int updatePmsRevenueNumSql(PmsRevenueNumSql pmsRevenueNumSql);

    /**
     * 批量删除收入sql
     * 
     * @param ids 需要删除的收入sqlID
     * @return 结果
     */
    public int deletePmsRevenueNumSqlByIds(Long[] ids);

    /**
     * 删除收入sql信息
     * 
     * @param id 收入sqlID
     * @return 结果
     */
    public int deletePmsRevenueNumSqlById(Long id);

    String inertBySql(PmsRevenueNumSql pmsRevenueNumSql) throws Exception;

    void deleteByFlag();

}
