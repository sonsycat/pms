package com.zjry.pms.apportionment.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.apportionment.mapper.CostDatabaseMapper;
import com.zjry.pms.apportionment.domain.CostDatabase;
import com.zjry.pms.apportionment.service.ICostDatabaseService;

/**
 * 多源数据库设置Service业务层处理
 * 
 * @author yzh
 * @date 2022-05-12
 */
@Service
public class CostDatabaseServiceImpl extends ServiceImpl<CostDatabaseMapper, CostDatabase> implements ICostDatabaseService {
    @Autowired
    private CostDatabaseMapper costDatabaseMapper;

    /**
     * 查询多源数据库设置
     * 
     * @param id 多源数据库设置ID
     * @return 多源数据库设置
     */
    @Override
    public CostDatabase selectCostDatabaseById(Long id) {
        return costDatabaseMapper.selectCostDatabaseById(id);
    }

    /**
     * 查询多源数据库设置列表
     * 
     * @param costDatabase 多源数据库设置
     * @return 多源数据库设置
     */
    @Override
    public List<CostDatabase> selectCostDatabaseList(CostDatabase costDatabase) {
        return costDatabaseMapper.selectCostDatabaseList(costDatabase);
    }

    /**
     * 新增多源数据库设置
     * 
     * @param costDatabase 多源数据库设置
     * @return 结果
     */
    @Override
    public int insertCostDatabase(CostDatabase costDatabase) {
    	costDatabase.preInsert();
        return costDatabaseMapper.insertCostDatabase(costDatabase);
    }

    /**
     * 修改多源数据库设置
     * 
     * @param costDatabase 多源数据库设置
     * @return 结果
     */
    @Override
    public int updateCostDatabase(CostDatabase costDatabase) {
    	costDatabase.preUpdate();
        return costDatabaseMapper.updateCostDatabase(costDatabase);
    }

    /**
     * 批量删除多源数据库设置
     * 
     * @param ids 需要删除的多源数据库设置ID
     * @return 结果
     */
    @Override
    public int deleteCostDatabaseByIds(Long[] ids) {
        return costDatabaseMapper.deleteCostDatabaseByIds(ids);
    }

    /**
     * 删除多源数据库设置信息
     * 
     * @param id 多源数据库设置ID
     * @return 结果
     */
    @Override
    public int deleteCostDatabaseById(Long id) {
        return costDatabaseMapper.deleteCostDatabaseById(id);
    }
}
