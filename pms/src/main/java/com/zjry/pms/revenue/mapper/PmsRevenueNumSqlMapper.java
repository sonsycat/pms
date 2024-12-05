package com.zjry.pms.revenue.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.revenue.domain.PmsRevenueNumSql;

/**
 * 收入sqlMapper接口
 * 
 * @author mk
 * @date 2022-06-23
 */
public interface PmsRevenueNumSqlMapper extends BaseMapper<PmsRevenueNumSql> {
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
     * 删除收入sql
     * 
     * @param id 收入sqlID
     * @return 结果
     */
    public int deletePmsRevenueNumSqlById(Long id);

    /**
     * 批量删除收入sql
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsRevenueNumSqlByIds(Long[] ids);
}
