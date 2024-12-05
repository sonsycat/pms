package com.zjry.pms.apportionment.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.apportionment.domain.CostDatabase;

/**
 * 多源数据库设置Mapper接口
 * 
 * @author yzh
 * @date 2022-05-12
 */
public interface CostDatabaseMapper extends BaseMapper<CostDatabase> {
    /**
     * 查询多源数据库设置
     * 
     * @param id 多源数据库设置ID
     * @return 多源数据库设置
     */
    public CostDatabase selectCostDatabaseById(Long id);
    
    /**
     * 查询多源数据库设置
     * 
     * @param code 多源数据库设置code
     * @return 多源数据库设置
     */
    public CostDatabase selectCostDatabaseByCode(String code);

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
     * 删除多源数据库设置
     * 
     * @param id 多源数据库设置ID
     * @return 结果
     */
    public int deleteCostDatabaseById(Long id);

    /**
     * 批量删除多源数据库设置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCostDatabaseByIds(Long[] ids);
}
