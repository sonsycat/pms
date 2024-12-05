package com.zjry.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.system.domain.SysMessage;
import com.zjry.system.mapper.SysMessageMapper;
import com.zjry.system.service.ISysMessageService;

/**
 * 消息管理Service业务层处理
 * 
 * @author yangtao
 * @date 2021-11-03
 */
@Service
public class SysMessageServiceImpl extends ServiceImpl<SysMessageMapper, SysMessage> implements ISysMessageService {
    @Autowired
    private SysMessageMapper sysMessageMapper;
    @Autowired
    private SysMessageWS sysMessageWS;

    /**
     * 查询消息管理
     * 
     * @param id 消息管理ID
     * @return 消息管理
     */
    @Override
    public SysMessage selectSysMessageById(Long id) {
        return sysMessageMapper.selectSysMessageById(id);
    }
    
    @Override
    public SysMessage showDetails(Long id) {
    	SysMessage message = sysMessageMapper.selectSysMessageById(id);
    	if(message != null && "0".equals(message.getIsRead())) {
    		message.setIsRead("1");
    		sysMessageMapper.updateSysMessage(message);
    	}
    	sysMessageWS.sendMsgCount(message.getReceiveUserId());
        return message;
    }

    /**
     * 查询消息管理列表
     * 
     * @param sysMessage 消息管理
     * @return 消息管理
     */
    @Override
    public List<SysMessage> selectSysMessageList(SysMessage sysMessage) {
        return sysMessageMapper.selectSysMessageList(sysMessage);
    }

    /**
     * 新增消息管理
     * 
     * @param sysMessage 消息管理
     * @return 结果
     */
    @Override
    public int insertSysMessage(SysMessage sysMessage) {
        sysMessage.setCreateTime(DateUtils.getNowDate());
        return sysMessageMapper.insertSysMessage(sysMessage);
    }

    /**
     * 修改消息管理
     * 
     * @param sysMessage 消息管理
     * @return 结果
     */
    @Override
    public int updateSysMessage(SysMessage sysMessage) {
        sysMessage.setUpdateTime(DateUtils.getNowDate());
        return sysMessageMapper.updateSysMessage(sysMessage);
    }

    /**
     * 批量删除消息管理
     * 
     * @param ids 需要删除的消息管理ID
     * @return 结果
     */
    @Override
    public int deleteSysMessageByIds(Long[] ids) {
    	int flag = sysMessageMapper.deleteSysMessageByIds(ids);
    	sysMessageWS.sendMsgCount(SecurityUtils.getLoginUser().getUser().getUserId());
        return flag;
    }

    /**
     * 删除消息管理信息
     * 
     * @param id 消息管理ID
     * @return 结果
     */
    @Override
    public int deleteSysMessageById(Long id) {
    	int flag = sysMessageMapper.deleteSysMessageById(id);
    	sysMessageWS.sendMsgCount(SecurityUtils.getLoginUser().getUser().getUserId());
        return flag;
    }

	@Override
	public Integer queryNotReadMsgNum(Long userId) {
		Integer num = 0;
		SysMessage sysMessage = new SysMessage();
		sysMessage.setReceiveUserId(userId);
		sysMessage.setIsRead("0");
		List list = sysMessageMapper.selectSysMessageList(sysMessage);
		if(list != null) {
			num = list.size();
		}
		return num;
	}
}
