/**
 * 
 */
package com.zjry.system.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.common.utils.spring.SpringUtils;
import com.zjry.system.domain.SysMessage;
import com.zjry.system.service.ISysMessageService;

/**
 * @author yangtao
 * 系统消息websocket处理类
 */
@ServerEndpoint(value="/websocket/sysMessage/{userId}")
@Component
public class SysMessageWS {

	/** 消息类型-获取未读消息数量 */
	private final static String TYPE_GET_COUNT = "getCount";
	/** 消息类型-获取消息内容 */
	private final static String TYPE_GET_CONTENT = "getContent";
	/** 用户websocket连接存储对象 */
	private static Map<String, Object> sessionPool = new HashMap<>();
	
	private ISysMessageService iSysMessageService;
	
	@OnOpen
	public void onOpen(Session session, @PathParam(value="userId") String userId) {
		sessionPool.put(userId, session);
	}
	
	@OnMessage
	public void onMessage(String message, Session session) {
		if(StringUtils.isNotEmpty(message)) {
			if(message.startsWith(TYPE_GET_COUNT)) {
				String userId = message.split(":")[1];
				iSysMessageService = SpringUtils.getBean(SysMessageServiceImpl.class);
				Integer messageCount = iSysMessageService.queryNotReadMsgNum(Long.parseLong(userId));
				SysMessage msg = new SysMessage();
				msg.setNotReadMsgNum(messageCount);
				this.sendMsgCount(Long.parseLong(userId));
			}
		}
	}
	
	/**
	 * 推送消息
	 * @param msg
	 * @param userId
	 */
	private void sendMsg(String msg, String userId) {
		Session session = (Session) sessionPool.get(userId);
		if(session != null) {
			try {
				synchronized(session) {
					session.getBasicRemote().sendText(msg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 推送消息内容-多用户
	 * @param title 标题
     * @param content 内容
     * @param receiveUserIds 接收用户userId
     * @param msgUrl 点击消息打开的url
     * @param busiId 业务id
	 */
	public void sendMsgContent(String title, String content, Long sendUserId, Long[] receiveUserIds, String msgUrl, Long busiId) {
		iSysMessageService = SpringUtils.getBean(SysMessageServiceImpl.class);
		SysMessage msg = new SysMessage();
		msg.setTitle(title);
		msg.setContent(content);
		msg.setSendUserId(sendUserId);
		msg.setSendTime(new Date());
		msg.setIsRead("0");
		msg.setMsgUrl(msgUrl);
		msg.setBusiId(busiId);
		for(Long receiveUserId : receiveUserIds) {
			msg.setReceiveUserId(receiveUserId);
			iSysMessageService.insertSysMessage(msg);
			// 给前端推送消息内容
			this.sendMsgContent(msg, receiveUserId);
		}
	}
	
	/**
	 * 推送消息内容-单用户
	 * @param msg
	 * @param userId
	 */
	private void sendMsgContent(SysMessage msg, Long userId) {
		msg.setMsgType(TYPE_GET_CONTENT);
		iSysMessageService = SpringUtils.getBean(SysMessageServiceImpl.class);
		Integer messageCount = iSysMessageService.queryNotReadMsgNum(userId);
		msg.setNotReadMsgNum(messageCount);
		Session session = (Session) sessionPool.get(userId+"");
		if(session != null) {
			sendMsg(JSONObject.toJSONString(msg), userId+"");
		}
	}
	
	/**
	 * 推送消息数量
	 * @param userId
	 */
	public void sendMsgCount(Long userId) {
		SysMessage msg = new SysMessage();
		msg.setMsgType(TYPE_GET_COUNT);
		iSysMessageService = SpringUtils.getBean(SysMessageServiceImpl.class);
		Integer messageCount = iSysMessageService.queryNotReadMsgNum(userId);
		msg.setNotReadMsgNum(messageCount);
		Session session = (Session) sessionPool.get(userId+"");
		if(session != null) {
			sendMsg(JSONObject.toJSONString(msg), userId+"");
		}
	}
	
}
