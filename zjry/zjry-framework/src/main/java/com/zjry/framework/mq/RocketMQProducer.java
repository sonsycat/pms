/**
 * 
 */
package com.zjry.framework.mq;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;

import com.zjry.common.utils.StringUtils;
import com.zjry.common.utils.spring.SpringUtils;
import com.zjry.system.service.ISysConfigService;
import com.zjry.system.service.impl.SysConfigServiceImpl;

/**
 * @author yangtao
 * rocketmq生产者
 * 支持同步、异步、顺序
 */
//@Component
//@Lazy
public class RocketMQProducer {

	private static RocketMQTemplate rocketMQTemplate;
	private static ISysConfigService sysConfigService;
	// 是否开启mq
	private static String rocketmqEnabled;
	
	static {
		// 无rocketmq环境，增加以下配置
		// spring:
		// autoconfigure:
	    // exclude: org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration
		if(SpringUtils.containsBean(RocketMQAutoConfiguration.ROCKETMQ_TEMPLATE_DEFAULT_GLOBAL_NAME)) {
			rocketMQTemplate = SpringUtils.getBean(RocketMQAutoConfiguration.ROCKETMQ_TEMPLATE_DEFAULT_GLOBAL_NAME);
		}
		sysConfigService = SpringUtils.getBean(SysConfigServiceImpl.class);
		rocketmqEnabled = StringUtils.EMPTY.equals(sysConfigService.selectConfigByKey("rocketmq.enabled")) ? "0" : "1";
	}
	
	/**
	 * 同步发送模式
	 * @param msg 消息对象
	 * @param topic 发送主题topic
	 * @return
	 */
	public static boolean sendSync(Object msg, String topic) {
		// MQ未开启
		if(!checkMQEnabled()) {
			return false;
		}
		SendResult sendResult = rocketMQTemplate.syncSend(topic, msg);
		return SendStatus.SEND_OK.equals(sendResult.getSendStatus());
	}
	
	public static SendResult sendSync(String destination, String message, String keys, long timeout, int delayLevel) {
		// MQ未开启
		if(!checkMQEnabled()) {
			return null;
		}
		return rocketMQTemplate.syncSend(destination, MessageBuilder.withPayload(message).setHeader(MessageConst.PROPERTY_KEYS, keys).build(), timeout, delayLevel);
	}
	
	/**
	 * 同步发送模式（顺序发送）
	 * @param msg 消息对象
	 * @param topic 发送主题topic
	 * @param queueId 发送队列id
	 * @return
	 */
	public static boolean sendSyncOrderly(Object msg, String topic, String queueId) {
		// MQ未开启
		if(!checkMQEnabled()) {
			return false;
		}
		SendResult sendResult = rocketMQTemplate.syncSendOrderly(topic, msg, queueId);
		return SendStatus.SEND_OK.equals(sendResult.getSendStatus());
	}
	
	/**
	 * 同步发送模式，可指定tags
	 * @param msg 消息对象
	 * @param topic 发送主题topic
	 * @param tags 发送tag，格式：tagA || tagB || tagC
	 * @return
	 */
	public static boolean sendSync(Object msg, String topic, String tags) {
		return sendSync(msg, getDestination(topic, tags));
	}
	
	/**
	 * 同步发送模式（顺序发送），可指定tags
	 * @param msg 消息对象
	 * @param topic 发送主题topic
	 * @param tags 发送tag，格式：tagA || tagB || tagC
	 * @param queueId 发送队列id
	 * @return
	 */
	public static boolean sendSyncOrderly(Object msg, String topic, String tags, String queueId) {
		return sendSyncOrderly(msg, getDestination(topic, tags), queueId);
	}
	
	/**
	 * 异步发送模式
	 * @param msg 消息对象
	 * @param topic 发送主题topic
	 * @param sendCallBack 回调方法
	 */
	public static void sendAsync(Object msg, String topic, SendCallback sendCallBack) {
		// MQ未开启
		if(!checkMQEnabled()) {
			return;
		}
		rocketMQTemplate.asyncSend(topic, msg, sendCallBack);
	}
	
	/**
	 * 异步发送模式
	 * @param msg 消息对象
	 * @param topic 发送主题topic
	 * @param tags 发送tag，格式：tagA || tagB || tagC
	 * @param sendCallBack 回调方法
	 */
	public static void sendAsync(Object msg, String topic, String tags, SendCallback sendCallBack) {
		sendAsync(msg, getDestination(topic, tags), sendCallBack);
	}
	
	/**
	 * 异步发送模式（顺序发送）
	 * @param msg 消息对象
	 * @param topic 发送主题topic
	 * @param queueId 发送队列id
	 */
	public static void sendAsyncOrderly(Object msg, String topic, String queueId, SendCallback sendCallBack) {
		// MQ未开启
		if(!checkMQEnabled()) {
			return;
		}
		rocketMQTemplate.asyncSendOrderly(topic, msg, queueId, sendCallBack);
	}
	
	/**
	 * 异步发送模式（顺序发送），可指定tags
	 * @param msg 消息对象
	 * @param topic 发送主题topic
	 * @param tags 发送tag，格式：tagA || tagB || tagC
	 * @param queueId 发送队列id
	 */
	public static void sendAsyncOrderly(Object msg, String topic, String tags, String queueId, SendCallback sendCallBack) {
		sendAsyncOrderly(msg, getDestination(topic, tags), queueId, sendCallBack);
	}
	
	/**
	 * 发送完消息立即返回，不等待发送结果
	 * 存在丢失消息的风险，适用于不重要的消息发送
	 * @param msg 消息对象
	 * @param topic 发送主题topic
	 */
	public static void sendOneWay(Object msg, String topic) {
		// MQ未开启
		if(!checkMQEnabled()) {
			return;
		}
		rocketMQTemplate.sendOneWay(topic, msg);
	}
	
	/**
	 * 发送完消息立即返回，不等待发送结果
	 * 存在丢失消息的风险，适用于不重要的消息发送
	 * @param msg 消息对象
	 * @param topic 发送主题topic
	 * @param tags 发送tag，格式：tagA || tagB || tagC
	 */
	public static void sendOneWay(Object msg, String topic, String tags) {
		sendOneWay(msg, getDestination(topic, tags));
	}
	
	private static String getDestination(String topic, String tags) {
		return topic + ":" + tags;
	}
	
	public static RocketMQTemplate getRocketMQTemplate() {
		return rocketMQTemplate;
	}
	
	/**
	 * 检测rocketmq是否可用
	 * @return
	 */
	private static boolean checkMQEnabled() {
		if("0".equals(rocketmqEnabled) || rocketMQTemplate == null) {
			return false;
		}
		return true;
	}
}
