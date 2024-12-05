package com.zjry.system.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.system.domain.SysMessage;

/**
 * 消息管理Service接口
 * 
 * @author yangtao
 * @date 2021-11-03
 */
public interface ISysMessageService extends IService<SysMessage> {
    /**
     * 查询消息管理
     * 
     * @param id 消息管理ID
     * @return 消息管理
     */
    public SysMessage selectSysMessageById(Long id);
    
    public SysMessage showDetails(Long id);

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
     * 批量删除消息管理
     * 
     * @param ids 需要删除的消息管理ID
     * @return 结果
     */
    public int deleteSysMessageByIds(Long[] ids);

    /**
     * 删除消息管理信息
     * 
     * @param id 消息管理ID
     * @return 结果
     */
    public int deleteSysMessageById(Long id);
    
    /**
     * 发送websocket消息
     * @param title 标题
     * @param content 内容
     * @param receiveUserIds 接收用户userId
     * @param msgUrl 点击消息打开的url
     */
//    public void sendWSMessage(String title, String content, Long[] receiveUserIds, String msgUrl);
    
    /**
     * 查询未读消息数量
     * @param userId
     * @return
     */
    public Integer queryNotReadMsgNum(Long userId);
}
