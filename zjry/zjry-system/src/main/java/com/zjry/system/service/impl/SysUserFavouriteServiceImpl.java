package com.zjry.system.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.system.mapper.SysUserFavouriteMapper;
import com.zjry.system.domain.SysUserFavourite;
import com.zjry.system.service.ISysUserFavouriteService;

/**
 * 收藏夹Service业务层处理
 * 
 * @author yangtao
 * @date 2021-12-07
 */
@Service
public class SysUserFavouriteServiceImpl extends ServiceImpl<SysUserFavouriteMapper, SysUserFavourite> implements ISysUserFavouriteService {
    @Autowired
    private SysUserFavouriteMapper sysUserFavouriteMapper;

    /**
     * 查询收藏夹
     * 
     * @param id 收藏夹ID
     * @return 收藏夹
     */
    @Override
    public SysUserFavourite selectSysUserFavouriteById(Long id) {
        return sysUserFavouriteMapper.selectSysUserFavouriteById(id);
    }

    /**
     * 查询收藏夹列表
     * 
     * @param sysUserFavourite 收藏夹
     * @return 收藏夹
     */
    @Override
    public List<SysUserFavourite> selectSysUserFavouriteList(SysUserFavourite sysUserFavourite) {
        return sysUserFavouriteMapper.selectSysUserFavouriteList(sysUserFavourite);
    }

    /**
     * 新增收藏夹
     * 
     * @param sysUserFavourite 收藏夹
     * @return 结果
     */
    @Override
    public int insertSysUserFavourite(SysUserFavourite sysUserFavourite) {
    	sysUserFavourite.setCreateTime(DateUtils.getNowDate());
    	List<SysUserFavourite> list = this.selectSysUserFavouriteList(sysUserFavourite);
    	if(list == null || list.size() == 0) {
    		return sysUserFavouriteMapper.insertSysUserFavourite(sysUserFavourite);
    	}
    	return 0;
    }

    /**
     * 修改收藏夹
     * 
     * @param sysUserFavourite 收藏夹
     * @return 结果
     */
    @Override
    public int updateSysUserFavourite(SysUserFavourite sysUserFavourite) {
        return sysUserFavouriteMapper.updateSysUserFavourite(sysUserFavourite);
    }

    /**
     * 批量删除收藏夹
     * 
     * @param ids 需要删除的收藏夹ID
     * @return 结果
     */
    @Override
    public int deleteSysUserFavouriteByIds(Long[] ids) {
        return sysUserFavouriteMapper.deleteSysUserFavouriteByIds(ids);
    }

    /**
     * 删除收藏夹信息
     * 
     * @param id 收藏夹ID
     * @return 结果
     */
    @Override
    public int deleteSysUserFavouriteById(Long id) {
        return sysUserFavouriteMapper.deleteSysUserFavouriteById(id);
    }
}
