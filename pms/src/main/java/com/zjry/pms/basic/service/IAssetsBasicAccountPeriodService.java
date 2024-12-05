package com.zjry.pms.basic.service;

import java.text.ParseException;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.basic.domain.AssetsBasicAccountPeriod;

/**
 * 年度帐期间管理Service接口
 * 
 * @author maozt
 * @date 2021-08-06
 */
public interface IAssetsBasicAccountPeriodService extends IService<AssetsBasicAccountPeriod> {
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
     * 批量删除年度帐期间管理
     * 
     * @param ids 需要删除的年度帐期间管理ID
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

    /**
     * 删除年度帐期间管理信息
     * 
     * @param id 年度帐期间管理ID
     * @return 结果
     */
    public int deleteAssetsBasicAccountPeriodById(Long id);

    /**
     * 自然月新增年度帐期间管理
     *
     * @param assetsBasicAccountPeriod 年度帐期间管理
     * @return 结果
     */
    public int insertAssetsBasicAccountPeriodNum(AssetsBasicAccountPeriod assetsBasicAccountPeriod) throws ParseException;

    /**
     * 自定义新增年度帐期间管理
     *
     * @param assetsBasicAccountPeriod 年度帐期间管理
     * @return 结果
     */
    public int insertAssetsBasicAccountPeriodNumFeel(AssetsBasicAccountPeriod assetsBasicAccountPeriod) throws ParseException;
}
