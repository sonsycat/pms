package com.zjry.pms.basic.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.basic.domain.AssetsBasicAccountPeriod;

/**
 * 年度帐期间管理Mapper接口
 * 
 * @author maozt
 * @date 2021-08-06
 */
public interface AssetsBasicAccountPeriodMapper extends BaseMapper<AssetsBasicAccountPeriod> {
    /**
     * 查询年度帐期间管理
     * 
     * @param id 年度帐期间管理ID
     * @return 年度帐期间管理
     */
    public AssetsBasicAccountPeriod selectAssetsBasicAccountPeriodById(Long id);

    /**
     * 查询年度帐期间管理列表
     * 
     * @param assetsBasicAccountPeriod 年度帐期间管理
     * @return 年度帐期间管理集合
     */
    public List<AssetsBasicAccountPeriod> selectAssetsBasicAccountPeriodList(AssetsBasicAccountPeriod assetsBasicAccountPeriod);

    /**
     * 新增年度帐期间管理
     * 
     * @param assetsBasicAccountPeriod 年度帐期间管理
     * @return 结果
     */
    public int insertAssetsBasicAccountPeriod(AssetsBasicAccountPeriod assetsBasicAccountPeriod);

    /**
     * 修改年度帐期间管理
     * 
     * @param assetsBasicAccountPeriod 年度帐期间管理
     * @return 结果
     */
    public int updateAssetsBasicAccountPeriod(AssetsBasicAccountPeriod assetsBasicAccountPeriod);

    /**
     * 删除年度帐期间管理
     * 
     * @param id 年度帐期间管理ID
     * @return 结果
     */
    public int deleteAssetsBasicAccountPeriodById(Long id);

    /**
     * 批量删除年度帐期间管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAssetsBasicAccountPeriodByIds(Long[] ids);

    /**
     * 根据名称年度批量删除年度帐期间管理
     *
     * @param assetsBasicAccountPeriod 需要删除的年度帐期间管理ID
     * @return 结果
     */
    public int deleteAssetsBasicAccountPeriodByNameYear(AssetsBasicAccountPeriod assetsBasicAccountPeriod);
}
