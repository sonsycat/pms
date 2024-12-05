package com.zjry.pms.basic.constant;

/**
 * @author
 */
public final class Constants {

	/**
	 * 消息标识destination（单位、区域、科室、账套、员工、字典）
	 */
	public final static String DESTINATION_DEPT = "basicdata-topic:tagDept";
	public final static String DESTINATION_AREA = "basicdata-topic:tagArea";
	public final static String DESTINATION_DEPT_ROOM = "basicdata-topic:tagDeptRoom";
	public final static String DESTINATION_ACCOUNT = "basicdata-topic:tagAccount";
	public final static String DESTINATION_DOCTOR = "basicdata-topic:tagDoctor";
	public final static String DESTINATION_DICT_TYPE = "basicdata-topic:tagDictType";
	public final static String DESTINATION_DICT_DATA = "basicdata-topic:tagDictData";
	public final static String DESTINATION_USER = "basicdata-topic:tagUser";
	public final static String DESTINATION_USER_ROLE = "basicdata-topic:tagUserRole";
	public final static String DESTINATION_USER_POST = "basicdata-topic:tagUserPost";
	public final static String DESTINATION_USER_DEPTROOM = "basicdata-topic:tagUserDeptRoom";
	public final static String DESTINATION_USER_ACCOUNT = "basicdata-topic:tagUserAccount";
	public final static String DESTINATION_MENU = "basicdata-topic:tagMenu";
	public final static String DESTINATION_ROLE = "basicdata-topic:tagRole";
	public final static String DESTINATION_ROLE_MENU = "basicdata-topic:tagRoleMenu";
	public final static String DESTINATION_ROLE_DEPT = "basicdata-topic:tagRoleDept";
	public final static String DESTINATION_BLACK_WHITE = "basicdata-topic:tagBlackWhite";
	public final static String DESTINATION_POST = "basicdata-topic:tagPost";
	public final static String DESTINATION_SYSTEM_INFO = "basicdata-topic:tagSystemInfo";
	public final static String DESTINATION_CONFIG = "basicdata-topic:tagConfig";
	public final static String DESTINATION_SYS_EMP = "basicdata-topic:tagSysEmp";
	public final static String DESTINATION_POST_MANAGE = "basicdata-topic:tagPostManage";
	public final static String DESTINATION_NOTICE = "basicdata-topic:tagNotice";

	/**
	 * RocketMQ-生产者-消息的返回状态
	 */
	/** 消息发送成功 */
	public final static String SEND_OK = "SEND_OK";
	/** 消息发送成功，但是此时 slave 不可用，消息已经进入服务器队列，只有此时服务器宕机，消息才会丢*/
	public final static String SLAVE_NOT_AVAILABLE = "SLAVE_NOT_AVAILABLE";
	/** 消息发送成功，但是服务器同步到 Slave 时超时，消息已经进入服务器队列，只有此时服务器宕机，消息才会丢失 */
	public final static String FLUSH_SLAVE_TIMEOUT = "FLUSH_SLAVE_TIMEOUT";
	/** 消息发送成功，但是服务器刷盘超时，消息已经进入服务器队列，只有此时服务器宕机，消息才会丢失 */
	public final static String FLUSH_DISK_TIMEOUT = "FLUSH_DISK_TIMEOUT";
	/** 服务器宕机 */
	public final static Integer DOWNTIME = -1;

	/**
	 * 基础数据
	 */
	public final static String BASICDATA = "BASICDATA";

	/**
	 * 人力资源-待办标识   人力资源系统在待办红能中的标识为HR
	 */
	public final static String HR = "HR";
	/**
	 * 资产管理-待办标识   资产管理系统在待办功能中的标识为ASSET
	 */
	public final static String ASSET = "ASSET";

	/**
	 * 状态代码
	 */
	public final static String STATE_0 = "0";
	public final static String STATE_1 = "1";
	public final static String STATE_2 = "2";
	public final static String STATE_3 = "3";
	public final static String STATE_4 = "4";
	public final static String STATE_5 = "5";
	public final static String STATE_6 = "6";
	public final static String STATE_10 = "10";

	/**
	 * int型代码
	 */
	public final static Integer INT_0 = 0;
	public final static Integer INT_1 = 1;
	public final static Integer INT_2 = 2;
	public final static Integer INT_3 = 3;
	public final static Integer INT_4 = 4;
	public final static Integer INT_5 = 5;

	/**
	 * 黑白名单
	 */
	public final static String BLICK_TYPE = "1";
	public final static String WHITE_TYPE = "2";
	public final static String BLICK_LIST = "1";
	public final static String WHITE_LIST = "0";
	/**
	 * 用户类型
	 */
	public final static String ADMINISTRATORS = "00";
	public final static String SALESMAN = "01";
	/**
	 * 账套使用状态
	 */
	public final static String IN_USE = "1";
	public final static String NOT_USE = "0";
	/**
	 * 组织所属(用户科室关系表)
	 */
	public final static String BELONG_TO = "0";
	public final static String NOT_BELONG = "1";

	/**
	 * 参数信息
	 */
	public final static String CONFIG_KEY_ROLE = "roleFlag";
	public final static String BACKLOG_URL = "backlog_url";

	public final static String COMBINED_USE = "combinedUse";
	public final static String SINGLE_USE = "singleUse";

	/**
	 * 检查重复性
	 */
	public final static String BASIC_UNIQUE = "0";
	public final static String BASIC_NOT_UNIQUE = "1";
	/**
	 * 菜单是否为外键
	 */
	public final static Integer MENU_IS_FRAME = 0;
	public final static Integer MENU_NOT_FRAME = 1;

	/**
	 * 判断是不是员工修改还是用户修改
	 */
	public final static String UPDATE_USER = "userUpdate";
	public final static String UPDATE_DOCTOR = "DoctorUpdate";

	/**模板名称校验-单位信息*/
	public static final String 	VALID_DEPT = "单位信息";
	/**模板名称校验-科室信息*/
	public static final String 	VALID_DEPTROOM = "科室信息";
	/**模板名称校验-账套信息*/
	public static final String 	VALID_ACCOUNTSET = "账套信息";
	/**模板名称校验-岗位信息*/
	public static final String 	VALID_POST = "岗位信息";
	/**模板名称校验-员工信息*/
	public static final String 	VALID_DOCTOR = "员工信息";
	/**模板名称校验-用户信息*/
	public static final String 	VALID_USER = "用户信息";
	/**模板名称校验-岗位管理*/
	public static final String 	VALID_POSTMANAGE = "岗位管理";
	/**模板名称校验-人员信息*/
	public static final String 	VALID_EMP = "人员信息";
	/**模板名称校验-角色信息*/
	public static final String 	VALID_RULE = "角色信息";
	/**模板名称校验-菜单信息*/
	public static final String 	VALID_MENU = "菜单信息";
	/**模板名称校验-角色菜单信息*/
	public static final String 	VALID_RULE_MENU = "角色菜单";
	/**
	 * 是否修改 0是 1否
	 */
	public static final String IS_CHANGE_YES = "0";
	public static final String IS_CHANGE_NO = "1";

	/**
	 * 审核状态 0草稿1审核中2通过3审核驳回4终止
	 * */
	public static final String CHECK_DRAFT = "0";
	public static final String CHECK_SUBMIT = "1";
	public static final String CHECK_PASS = "2";
	public static final String CHECK_REFUSE = "3";
	public static final String CHECK_STOP = "4";

	/**
	 * 岗位存放redis的key值
	 * */
	public static final String  POST_REDIS_KEY = "postRedisKey";

	/**
	 * POST 请求返回状态码
	 */
	public static final String POST_RETURN_CODE = "200";

}
