package com.zjry.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.system.domain.SysUserFavourite;

/**
 * 收藏夹Mapper接口
 * 
 * @author yangtao
 * @date 2021-12-07
 */
public interface SysUserFavouriteMapper extends BaseMapper<SysUserFavourite> {
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
     * 删除收藏夹
     * 
     * @param id 收藏夹ID
     * @return 结果
     */
    public int deleteSysUserFavouriteById(Long id);

    /**
     * 批量删除收藏夹
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysUserFavouriteByIds(Long[] ids);
}
