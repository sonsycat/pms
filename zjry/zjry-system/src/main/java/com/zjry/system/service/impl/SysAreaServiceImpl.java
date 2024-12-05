package com.zjry.system.service.impl;

import com.zjry.common.utils.DateUtils;
import com.zjry.system.domain.SysArea;
import com.zjry.system.mapper.SysAreaMapper;
import com.zjry.system.service.ISysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 区域信息Service业务层处理
 * 
 * @author mingc
 * @date 2021-06-17
 */
@Service
public class SysAreaServiceImpl implements ISysAreaService 
{
    @Autowired
    private SysAreaMapper sysAreaMapper;

    /**
     * 查询区域信息
     * 
     * @param id 区域信息ID
     * @return 区域信息
     */
    @Override
    public SysArea selectSysAreaById(Long id)
    {
        return sysAreaMapper.selectSysAreaById(id);
    }

    /**
     * 查询区域信息列表
     * 
     * @param sysArea 区域信息
     * @return 区域信息
     */
    @Override
    public List<SysArea> selectSysAreaList(SysArea sysArea)
    {
        return sysAreaMapper.selectSysAreaList(sysArea);
    }

    /**
     * 新增区域信息
     * 
     * @param sysArea 区域信息
     * @return 结果
     */
    @Override
    public int insertSysArea(SysArea sysArea)
    {
        sysArea.setCreateTime(DateUtils.getNowDate());
        return sysAreaMapper.insertSysArea(sysArea);
    }

    /**
     * 修改区域信息
     * 
     * @param sysArea 区域信息
     * @return 结果
     */
    @Override
    public int updateSysArea(SysArea sysArea)
    {
        sysArea.setUpdateTime(DateUtils.getNowDate());
        return sysAreaMapper.updateSysArea(sysArea);
    }

    /**
     * 批量删除区域信息
     * 
     * @param ids 需要删除的区域信息ID
     * @return 结果
     */
    @Override
    public int deleteSysAreaByIds(Long[] ids)
    {
        return sysAreaMapper.deleteSysAreaByIds(ids);
    }

    /**
     * 删除区域信息信息
     * 
     * @param id 区域信息ID
     * @return 结果
     */
    @Override
    public int deleteSysAreaById(Long id)
    {
        return sysAreaMapper.deleteSysAreaById(id);
    }
}
