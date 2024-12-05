package com.zjry.system.mapper;

import com.zjry.system.domain.SysArea;

import java.util.List;

/**
 * 区域信息Mapper接口
 * 
 * @author mingc
 * @date 2021-06-17
 */
public interface SysAreaMapper 
{
    /**
     * 查询区域信息
     * 
     * @param id 区域信息ID
     * @return 区域信息
     */
    public SysArea selectSysAreaById(Long id);

    /**
     * 查询区域信息列表
     * 
     * @param sysArea 区域信息
     * @return 区域信息集合
     */
    public List<SysArea> selectSysAreaList(SysArea sysArea);

    /**
     * 新增区域信息
     * 
     * @param sysArea 区域信息
     * @return 结果
     */
    public int insertSysArea(SysArea sysArea);

    /**
     * 修改区域信息
     * 
     * @param sysArea 区域信息
     * @return 结果
     */
    public int updateSysArea(SysArea sysArea);

    /**
     * 删除区域信息
     * 
     * @param id 区域信息ID
     * @return 结果
     */
    public int deleteSysAreaById(Long id);

    /**
     * 批量删除区域信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysAreaByIds(Long[] ids);
}
