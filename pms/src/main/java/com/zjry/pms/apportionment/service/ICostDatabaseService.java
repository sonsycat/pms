package com.zjry.pms.apportionment.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.apportionment.domain.CostDatabase;

/**
 * 多源数据库设置Service接口
 * 
 * @author yzh
 * @date 2022-05-12
 */
public interface ICostDatabaseService extends IService<CostDatabase> {
    /**
     * 查询多源数据库设置
     * 
     * @param id 多源数据库设置ID
     * @return 多源数据库设置
     */
    public CostDatabase selectCostDatabaseById(Long id);

    /**
     * 查询多源数据库设置列表
     * 
     * @param costDatabase 多源数据库设置
     * @return 多源数据库设置集合
     */
    public List<CostDatabase> selectCostDatabaseList(CostDatabase costDatabase);

    /**
     * 新增多源数据库设置
     * 
     * @param costDatabase 多源数据库设置
     * @return 结果
     */
    public int insertCostDatabase(CostDatabase costDatabase);

    /**
     * 修改多源数据库设置
     * 
     * @param costDatabase 多源数据库设置
     * @return 结果
     */
    public int updateCostDatabase(CostDatabase costDatabase);

    /**
     * 批量删除多源数据库设置
     * 
     * @param ids 需要删除的多源数据库设置ID
     * @return 结果
     */
    public int deleteCostDatabaseByIds(Long[] ids);

    /**
     * 删除多源数据库设置信息
     * 
     * @param id 多源数据库设置ID
     * @return 结果
     */
    public int deleteCostDatabaseById(Long id);
}
