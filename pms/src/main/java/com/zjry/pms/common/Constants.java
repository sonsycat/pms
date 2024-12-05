package com.zjry.pms.common;

/**
 * 通用常量信息
 *
 * @author zjry
 */
public class Constants {

    /** 判断重复异常 */
    public static final Integer INDEX_OF_NO= -1;

	/** 判断重复异常 */
    public static final Integer ERROR_NO= -100;

    /** 是否修改 0是 1否 */
    public static final String IS_CHANGE_YES = "0";
    public static final String IS_CHANGE_NO = "1";
    /** 是否在职  1在职2离职 */
    public static final String IS_POST_YES = "1";
    public static final String IS_POST_NO = "2";

    /** 合同状态 1生效2解除3到期 */
    public static final String CONSTANTS_EFFECTIVE = "1";
    public static final String CONSTANTS_INVALID = "2";
    public static final String CONSTANTS_EXPIRE = "3";

    /** 审核状态 0草稿1审核中2通过3审核驳回4终止 */
    public static final String CHECK_DRAFT = "0";
    public static final String CHECK_SUBMIT = "1";
    public static final String CHECK_PASS = "2";
    public static final String CHECK_REFUSE = "3";
    public static final String CHECK_STOP = "4";

    /** 发布状态 1未发布 2已发布 */
    public static final String ISSUE_NO = "1";
    public static final String ISSUE_YES = "2";

    /** 是否查勘 0 是  1 否 */
    public static final String SURVEY_IS = "0";
    public static final String SURVEY_NO = "1";

    /** 维修状态 1 已报修 2查勘 3派工 4领料 5维修费用 6维修完成 7已验收 */
    public static final String REPAIR_CALL = "1";
    public static final String REPAIR_SURVEY = "2";
    public static final String REPAIR_JOB = "3";
    public static final String REPAIR_PICK = "4";
    public static final String REPAIR_COST = "5";
    public static final String REPAIR_RESULT = "6";
    public static final String REPAIR_CHECK = "7";
    /** 日期工具方法 */
    public static final int  FEBRUARY = 2;

    /** 薪资项目 */
    public static final String  SALARY_ITEM = "SALARY_ITEM";

    /** 权重总计  */
    public static final int  PROPORTION_SUM = 100;

    /** 人员变动状态 */
    public static final String  NEW_PEOPLE = "新进";
    public static final String  RENEW_PEOPLE = "复职";
    public static final String  DIMISSION_PEOPLE = "离职";
    public static final String  SHIFT_PEOPLE = "调动";

    /** 简历状态 */
    public static final String  RESUME_DELIVER = "1";
    public static final String  RESUME_READ = "2";
    public static final String  RESUME_INVITA = "3";

    /** 分数 */
    public static final int  FRACTION_NINETY_FIVE = 95;
    public static final int  FRACTION_NINETY = 90;
    public static final int  FRACTION_EIGHTY = 80;
    public static final int  FRACTION_SEVENTY = 70;
    public static final int  FRACTION_SIXTY = 60;

    /** 评级 */
    public static final String  GRADE_A_MORE = "A+";
    public static final String  GRADE_A = "A";
    public static final String  GRADE_B = "B";
    public static final String  GRADE_C = "C";
    public static final String  GRADE_D = "D";
    public static final String  GRADE_E = "E";

    /** 通知方式 1短信，2邮件，3短信+邮件 */
    public static final String  MSYQTZFS_MESSAGE = "1";
    public static final String  MSYQTZFS_EMAIL = "2";
    public static final String  MSYQTZFS_ALL = "3";

    /** 考勤状态：1草稿，2审核中，3生效 */
    public static final String  PUNCH_CARD_DRAFT = "1";
    public static final String  PUNCH_CARD_AUDIT = "2";
    public static final String  PUNCH_CARD_INTO = "3";

    /**
     * 东八区时间戳
     * 8 * 1000 * 60 * 60
     */
    public static final Long TIMESTAMP = 28800000L;

    /** 岗位存放redis的key值 */
    public static final String  POST_REDIS_KEY = "postRedisKey";

    /** 规则类型：1固定时间上下班，2按班次上下班，3自由上下班 */
    public static final String  RULE_TYPE_FIXED = "1";
    public static final String  RULE_TYPE_CLASSES = "2";
    public static final String  RULE_TYPE_FREE = "3";

    /** 数字常量 */
    public static final int  TWO = 2;

    /** 是否 */

    public static final String  YSE = "1";
    public static final String  NO = "0";
    /** 默认父级编码 */
    public static final String  PARENT_CODE = "0";
    public static final String  PARENT_CODES = "0,";

    /** 账套类型 0自然月 1自定义 */
    public static final String  ACCOUNT_TYPE_FEEL = "1";
    public static final String  ACCOUNT_TYPE_NORMAL = "0";

    /** 仓库存放redis的key值 */
    public static final String  STORE_REDIS_KEY = "storeRedisKey";

    /** 供应商存放redis的key值 */
    public static final String  SUPPLIER_REDIS_KEY = "supplierRedisKey";
    /** 生产商存放redis的key值 */
    public static final String  PRODUCER_REDIS_KEY = "producerRedisKey";

    /** 资产字典redis的key值 */
    public static final String  ASSETS_DICT_REDIS_KEY = "assetsDictRedisKey";

    /**
     * 资产分类redis的key值
     * */
    public static final String  ASSETS_KIND_REDIS_KEY = "assetsKindRedisKey";

    /** 用户信息redis的key值 */
    public static final String  USER_INFO_REDIS_KEY = "userInfoRedisKey";
    /** 盘点类型 */
    public static final String  CHECK_TYPE_DEPT = "2";
    public static final String  CHECK_TYPE_STORE = "1";
    public static final String  CHECK_TYPE_ALL = "3";

    /**审批设置*/
    public static final String  APPROVAL_ALL = "1";
    public static final String  APPROVAL_HOSP = "2";

    /** 折旧方法 */
    public static final String  DISCOUNT_WAY1 = "1";
    public static final String  DISCOUNT_WAY2 = "2";
    public static final String  DISCOUNT_WAY3 = "3";
    public static final String  DISCOUNT_WAY4 = "4";

    /**任务状态*/
    public static final String TASK_TYPE_STAR ="1";
    public static final String TASK_TYPE_END ="2";

    /**存放位置*/
    public static final String SAVE_LOCATION_STORE ="1";
    public static final String SAVE_LOCATION_DEPT ="2";


    /** 请求返回参数 */
    public static final int HTTP_CODE = 200;
    public static final int HTTP_CODE1 = 1;
    public static final int HTTP_CODE2 = 2;

    /** 卡片使用状态 1正常 2领用 3处置 4重估 5已处置 6调出 */
    public static final String CARD_STATE_ONE ="1";
    public static final String CARD_STATE_TWO ="2";
    public static final String CARD_STATE_THREE ="3";
    public static final String CARD_STATE_FOUR ="4";
    public static final String CARD_STATE_FIVE ="5";
    public static final String CARD_STATE_SIX ="6";

    /** 是否停用 */
    public static final String  IS_STOP_YES = "0";
    public static final String  IS_STOP_NO = "1";

    /** 判断id为0 增加空流程*/
    public static final long ID =0;

    /** 启用状态 1 新增 2 启用 3 停用 */
    public static final String  START_NEW = "1";
    public static final String  START_YES= "2";
    public static final String  START_NO= "3";

    /** 主子账户  1主账户2子账户 */
    public static final String IS_TYPE_ONE = "1";
    public static final String IS_TYPE_TWO = "2";

    /** 计划状态  1挂起 2正常 3退单 */
    public static final String PLAN_STATE_UP = "1";
    public static final String PLAN_STATE_NORMAL = "2";
    public static final String PLAN_STATE_DEL = "3";

    /** 引用状态  0未被引用 1被引用 */
    public static final String USE_STATE_DRAFT = "0";
    public static final String USE_STATE_INFO = "1";

    /** 中心库附件类型  1入库2出库 3退库 4退货 */
    public static final String ANNEX_TYPE_IN = "1";
    public static final String ANNEX_TYPE_OUT = "2";
    public static final String ANNEX_TYPE_BACK = "3";
    public static final String ANNEX_TYPE_RETURN = "4";
    /** 执行状态  1进行中 2已完成 */
    public static final String RUN_STATE_ING = "1";
    public static final String RUN_STATE_FINISH = "2";

    /** 拣货状态  1待拣货2进行中3已完成 */
    public static final String PICK_STATE_DRAFT = "1";
    public static final String PICK_STATE_ING = "2";
    public static final String PICK_STATE_FINISH = "3";

    /** 拣货明细状态  1未拣货，2已完成 */
    public static final String DETAIL_PICK_STATE_DRAFT = "1";
    public static final String DETAIL_PICK_STATE_FINISH = "2";

    /** 是否领完  0 否  1 是 */
    public static final String IS_OVER_TRUE = "1";
    public static final String IS_OVER_FALSE = "0";

    /** 是否重新发货  0 是  1 否 */
    public static final String IS_REDELIVER_TRUE = "0";
    public static final String IS_REDELIVER_FALSE = "1";

    /** 采购订单明细是否使用  1 未使用  2 已使用 */
    public static final String IS_USE_FALSE = "1";
    public static final String IS_USE_TRUE = "2";

    /** 配送状态  1 待加工  2 已加工 */
    public static final String FIX_DRAFT = "1";
    public static final String FIX_FINISH = "2";

    /** 配送状态  1 待配送  2 已配送 */
    public static final String DELIVERY_DRAFT = "1";
    public static final String DELIVERY_FINISH = "2";

    public static final String RETURN_SUCCESS ="success";
    public static final String RETURN_ERROR ="error";

    /** 入库类型  1 正常入库  2 专购入库 3 调拨入库 4 代销入库 5 退货入库 */
    public static final String IN_TYPE_NORMAL = "1";
    public static final String IN_TYPE_EXCLUSIVE = "2";
    public static final String IN_TYPE_TRANSFER = "3";
    public static final String IN_TYPE_AGENCY = "4";
    public static final String IN_TYPE_WITHDRAWAL = "5";


    /** 出库类型  1 正常出库  2 专购出库 3 代销出库 4 报废出库 5 调拨出库 6领用出库 */
    public static final String OUT_TYPE_NORMAL = "1";
    public static final String OUT_TYPE_EXCLUSIVE = "2";
    public static final String OUT_TYPE_AGENCY = "3";
    public static final String OUT_TYPE_SCRAP = "4";
    public static final String OUT_TYPE_TRANSFER = "5";
    public static final String OUT_TYPE_DRAW = "6";

    /** 退库类型  1 一般退库 2 专购品退库 3 代销品退库 */
    public static final String WITHDRAWAL_TYPE_NORMAL = "1";
    public static final String WITHDRAWAL_TYPE_EXCLUSIVE = "2";
    public static final String WITHDRAWAL_TYPE_AGENCY = "3";

    public static final int TWELVE = 12;

    public static final String NULL = "null";

    public static final String STORE_TYPE_CENTER = "1";
    public static final String STORE_TYPE_DEPT = "2";

    public static final String TYPE1 = "入库";
    public static final String TYPE2 = "出库";
    public static final String TYPE3 = "退库";
    public static final String TYPE4 = "退货";

    public static final String PMS_ZDLX_ONE = "1";
    public static final String PMS_ZDLX_TWO = "2";
    public static final String PMS_ZDLX_THREE = "3";
    public static final String PMS_ZDLX_FOUR = "4";
    public static final String PMS_ZDLX_FIVE = "5";
    public static final String PMS_ZDLX_SIX = "6";
    public static final String PMS_ZDLX_SEVEN = "7";
    public static final String PMS_ZDLX_EIGHT = "8";
    public static final String PMS_ZDLX_NINE = "9";

    /** 审批类型 */
    public static final String  TECHNO_TYPE_KT = "1";
    public static final String  TECHNO_TYPE_CG = "2";
    public static final String  TECHNO_TYPE_LW = "3";
    public static final String  TECHNO_TYPE_ZZ = "4";
    public static final String  TECHNO_TYPE_JS = "5";

    /** 独立核算提成比类别流水前缀 */
    public static final String HSTCLB = "HSTCLB";

    /** 工作量类别流水前缀 */
    public static final String GZLLB = "GZLLB";


    /** 工作量流水前缀 */
    public static final String GZLXM = "GZLXM";

    /** 通用是否（0是；1否） */
    public static final String COMMON_YES = "0";
    public static final String COMMON_NO = "1";

    /** 日期格式化：年-月 */
    public static final String DATE_MONTH_FORMATTER = "yyyy-MM-01";

    /** 指标计算结果类型（数据类型：1 平衡计分卡；2 奖金；3 二次核算） */
    public static final String EVA_CAL_BSC = "1";
    public static final String EVA_CAL_BONUS = "2";
    public static final String EVA_CAL_SECOND = "3";

    /** 组织类型(Y(院)；K(科)；R(人)) */
    public static final String ORG_TYPE_Y = "Y";
    public static final String ORG_TYPE_K = "K";
    public static final String ORG_TYPE_R = "R";

    /** 组织类型(1(院)；2(科)；3(人)) */
    public static final String ORG_TYPE_1 = "1";
    public static final String ORG_TYPE_2 = "2";
    public static final String ORG_TYPE_3 = "3";

    /** 是否必须指标 0是1否 */
    public static final String IS_NECESSARY_YES = "0";
    public static final String IS_NECESSARY_NO = "1";
}

