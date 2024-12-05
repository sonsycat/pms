package com.zjry.system.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.system.domain.SysUserFavourite;

/**
 * 收藏夹Service接口
 * 
 * @author yangtao
 * @date 2021-12-07
 */
public interface ISysUserFavouriteService extends IService<SysUserFavourite> {
    /**
     * 查询收藏夹
     * 
     * @param id 收藏夹ID
     * @return 收藏夹
     */
    public SysUserFavourite selectSysUserFavouriteById(Long id);

    /**
     * 查询收藏夹列表
     * 
     * @param sysUserFavourite 收藏夹
     * @return 收藏夹集合
     */
    public List<SysUserFavourite> selectSysUserFavouriteList(SysUserFavourite sysUserFavourite);

    /**
     * 新增收藏夹
     * 
     * @param sysUserFavourite 收藏夹
     * @return 结果
     */
    public int insertSysUserFavourite(SysUserFavourite sysUserFavourite);

    /**
     * 修改收藏夹
     * 
     * @param sysUserFavourite 收藏夹
     * @return 结果
     */
    public int updateSysUserFavourite(SysUserFavourite sysUserFavourite);

    /**
     * 批量删除收藏夹
     * 
     * @param ids 需要删除的收藏夹ID
     * @return 结果
     */
    public int deleteSysUserFavouriteByIds(Long[] ids);

    /**
     * 删除收藏夹信息
     * 
     * @param id 收藏夹ID
     * @return 结果
     */
    public int deleteSysUserFavouriteById(Long id);
}
