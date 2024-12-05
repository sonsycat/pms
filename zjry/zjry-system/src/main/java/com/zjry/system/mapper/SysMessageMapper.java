package com.zjry.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.system.domain.SysMessage;

/**
 * 消息管理Mapper接口
 * 
 * @author yangtao
 * @date 2021-11-03
 */
public interface SysMessageMapper extends BaseMapper<SysMessage> {
    /**
     * 查询消息管理
     * 
     * @param id 消息管理ID
     * @return 消息管理
     */
    public SysMessage selectSysMessageById(Long id);

    /**
     * 查询消息管理列表
     * 
     * @param sysMessage 消息管理
     * @return 消息管理集合
     */
    public List<SysMessage> selectSysMessageList(SysMessage sysMessage);

    /**
     * 新增消息管理
     * 
     * @param sysMessage 消息管理
     * @return 结果
     */
    public int insertSysMessage(SysMessage sysMessage);

    /**
     * 修改消息管理
     * 
     * @param sysMessage 消息管理
     * @return 结果
     */
    public int updateSysMessage(SysMessage sysMessage);

    /**
     * 删除消息管理
     * 
     * @param id 消息管理ID
     * @return 结果
     */
    public int deleteSysMessageById(Long id);

    /**
     * 批量删除消息管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysMessageByIds(Long[] ids);
}
