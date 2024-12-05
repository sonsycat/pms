package com.zjry.pms.common.utils;

import java.util.HashMap;
import java.util.Map;


import com.zjry.common.core.domain.entity.SysUser;
import com.zjry.system.service.ISysConfigService;
/**
 * 
 * @author user
 *
 */
public class ProcessUtils {
	
	private static ISysConfigService configService = SpringContextHolder.getBean(ISysConfigService.class);

	/**
	 * 
	 * @param procDefKey 匹配字段procinsId
	 * @param busiTable	 当前业务表名
	 * @param busiId	当前业务主键ID
	 * @param title		流程名称  XXX合同
	 * @param formUrl	当前业务审核页面路由地址
	 * @return
	 */
	public static Map<String, String> startProcess(String procDefKey,String busiTable,long busiId,String title,String formUrl){
        SysUser user = UserUtils.getSysUser();
		Map<String, String> userInfo = new HashMap<String, String>(16);
		userInfo.put("id", user.getUserId()+"");
		userInfo.put("name", user.getNickName());
		Map<String, String> params = new HashMap<String, String>(16);
		params.put("systemId", "PMS");
		params.put("userInfo", JsonMapper.toJsonString(userInfo));
		params.put("procDefKey", procDefKey);
		params.put("busiTable", busiTable);
		params.put("busiId", busiId==0?"":busiId+"");
		params.put("title", title);
		Map<String, String> vars = new HashMap<String, String>(16);
		vars.put("formUrl", formUrl);
		params.put("varJson", JsonMapper.toJsonString(vars));
		return params;
	}
	
	/**
	 * 
	 * @param procInsId 流程ID
	 * @param busiId	主键ID
	 * @param title	流程标题
	 * @return
	 */
	public static Map<String, String> startProcess(String procInsId,long busiId,String title){
		Map<String, String> params = new HashMap<String, String>(16);
		params.put("procInsId", procInsId);
		params.put("busiTable", "");
		params.put("busiId", busiId==0?"":busiId+"");
		params.put("title", title);
		params.put("varJson", "");
		return params;
	}
	
	/**
	 * 
	 * @param procInsId	当前业务流程ID
	 * @param comment	审核意见
	 * @param assignee	下节点审核人
	 * @return
	 */
	public static Map<String, String> dealProcess(String procInsId,String comment,String assignee){
    	SysUser user = UserUtils.getSysUser();
		Map<String, String> params = new HashMap<String, String>(16);
		params.put("systemId", "PMS");
		params.put("userId", user.getUserId()+"");
		params.put("procInsId", procInsId);
		params.put("comment",comment);
		params.put("assignee", assignee);
		return params;
	}
	
	/**
	 * 获取参数配置
	 * @param key
	 * @return value
	 */
	public static String getConfigValueByKey(String key){
		return configService.selectConfigByKey(key);
	}
}
