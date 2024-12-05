CREATE TABLE `account_set` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(100) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '编码',
  `name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '姓名',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  `dept_name` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '部门名称',
  `start_year` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '开始年份',
  `start_month` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '开始月份',
  `end_date` datetime DEFAULT NULL COMMENT '结束时间',
  `status` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态,0:正常,1:停用',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `del_flag` varchar(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='账套信息';

CREATE TABLE `assets_basic_account_period` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACCOUNT_CODE` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '璐﹀缂栫爜',
  `PERIOD_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '鏈熼棿鍚嶇О',
  `ACCOUNT_YEAR` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '骞村害',
  `START_DATE` date NOT NULL COMMENT '寮€濮嬫棩鏈?',
  `END_DATE` date NOT NULL COMMENT '缁撴潫鏃ユ湡',
  `PERIOD_NUM` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '鏈熼棿',
  `IS_PERIOD` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '鏄惁褰撳墠鏈熼棿',
  `DEL_FLAG` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '鍒犻櫎鏍囪(0鏈垹闄?1宸插垹闄?',
  `CREATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '鍒涘缓鑰?',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '鍒涘缓鏃堕棿',
  `UPDATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '淇敼鑰?',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '淇敼鏃堕棿',
  `REMARK` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '澶囨敞',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=526 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='年度帐期间管理';

CREATE TABLE `basicdata_code_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `rule_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规则名称',
  `rule_table` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规则适用表',
  `rule_columns` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规则对应字段',
  `rule_content` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规则内容',
  `is_structs` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否结构化',
  `rule_prefix` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '编码前缀',
  `is_date` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否带日期',
  `flowcode_length` int(10) DEFAULT NULL COMMENT '流水号长度',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `system_flag` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '系统标识（用于区别不同业务系统的数据）',
  `del_flag` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标识(0-正常；1-删除)',
  `use_flag` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '启用标识(0-启用；1-禁用)',
  `date_format` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '日期格式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='编码规则信息';

CREATE TABLE `cost_apport` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '账套编码',
  `APPORT_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分摊编码',
  `NOW_DATE` date DEFAULT NULL COMMENT '年月',
  `APPORT_TYPE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分摊级别(保存最后的级别)',
  `FORWARD` double(50,2) DEFAULT NULL COMMENT '分摊前金额',
  `BACk` double(50,2) DEFAULT NULL COMMENT '分摊后金额',
  `APPORT_TIME` datetime DEFAULT NULL COMMENT '分摊时间',
  `DEL_FLAG` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='分摊计算';

CREATE TABLE `cost_apport_detail` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `APPORT_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分摊编码',
  `DEPT_CODE` bigint(20) DEFAULT NULL COMMENT '科室编码',
  `DEPT_TYPE` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '科室类别',
  `PROJECT_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目编码',
  `RATIO_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '比例编码',
  `COST_TYPE` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '成本类型(1直接成本，间接成本)',
  `APPORT_TYPE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分摊级别',
  `AMOUNT` double(50,2) DEFAULT NULL COMMENT '分摊金额',
  `DEL_FLAG` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12080 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='分摊明细';

CREATE TABLE `cost_apportionment_dictionary` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '账套编码',
  `RATIO_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '比例编码',
  `RATIO_NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '比例名称',
  `RATIO_TYPE` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '比例类型',
  `COONECT_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '数据连接编码',
  `COUNT_SQl` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '计算计入成本分摊SQL',
  `FIRST_LEVEL_SQl` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '一级分摊SQL',
  `SECOND_LEVEL_SQl` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '二级分摊SQL',
  `THIRD_LEVEL_SQl` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '三级分摊SQL',
  `DEL_FLAG` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='比例分摊字典管理';

CREATE TABLE `cost_apportionment_dictionary_detail` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `RATIO_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '比例编码',
  `RATIO_NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '比例名称',
  `LEVEL_TYPE` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分摊或计算类型(1.一级分摊2.二级分摊3.三级分摊4.计算计入分摊)',
  `DEPT_CODE` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '科室编码',
  `DEPT_NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '科室名称',
  `USE_RATIO` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '比例值（计算出来的比例值）',
  `NUM` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '比例数（实际获取过来的数字）',
  `NY` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '年月',
  `DEL_FLAG` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1518 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='比例分摊字典明细';

CREATE TABLE `cost_apportionment_project` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '账套编码',
  `PROJECT_IN_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '方案唯一编码',
  `PROJECT_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分摊方案编码',
  `PROJECT_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分摊方案名称',
  `NOW_DATE` date DEFAULT NULL COMMENT '当前年月',
  `SPELL` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拼音码',
  `PROJECT_TYPE` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '方案类型',
  `RATION_SHOW` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '设置比例中文',
  `RATION_COUNT` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '设置比例编码',
  `DEL_FLAG` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='分摊方案管理';

CREATE TABLE `cost_apportionment_project_dept` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '账套编码',
  `PROJECT_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分摊方案编码',
  `DEPT_ID` bigint(20) DEFAULT NULL COMMENT '科室ID',
  `RATIO` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '计算计入设置比例',
  `FIRST_RATIO` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '一级设置比例',
  `SECOND_RATIO` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '二级设置比例',
  `THIRD_RATIO` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '三级设置比例',
  `DEL_FLAG` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=217 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='分摊方案管理科室设置';

CREATE TABLE `cost_apportionment_project_ratio` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '账套编码',
  `PROJECT_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分摊方案编码',
  `RATIO_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分摊比例字典编码',
  `RATIO` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '计算计入设置比例',
  `FIRST_RATIO` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '一级设置比例',
  `SECOND_RATIO` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '二级设置比例',
  `THIRD_RATIO` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '三级设置比例',
  `DEL_FLAG` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=174 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='分摊方案管理比例设置';

CREATE TABLE `cost_cs_detail` (
  `LEVEL_TYPE` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分摊或计算类型(1.一级分摊2.二级分摊3.三级分摊4.计算计入分摊)',
  `DEPT_CODE` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '科室编码',
  `DEPT_NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '科室名称',
  `USE_RATIO` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '使用比例',
  `NY` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '年月',
  `TYPE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '类型 1人员，2面积',
  `DEPT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位',
  `DEPT_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='比例分摊字典明细';

CREATE TABLE `cost_database` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '编码',
  `NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '名称',
  `TYPE` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '类型',
  `URL` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'url',
  `USER_NAME` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `PASS_WORD` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `DEL_FLAG` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='多源数据库';

CREATE TABLE `cost_project` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '账套编码',
  `PROJECT_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目编码',
  `PROJECT_NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目名称',
  `PARENT_PROJECT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '上级项目',
  `PROJECT_LEVEL` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目级别',
  `PROJECT_TYPE` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类型',
  `COST_PROPERTY` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '成本属性',
  `IS_CONTROL` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否可控',
  `IS_ACCOUNT` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否核算',
  `IS_COST_CAPITAL` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否资本性成本',
  `IS_COST_HEALTH` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否医疗成本',
  `IS_COST_CALCULATE` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否计算计入成本',
  `COST_SCHEME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '计算计入成本分摊方案',
  `FIRST_LEVEL_SQl` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '一级分摊方案',
  `SECOND_LEVEL_SQl` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '二级分摊方案',
  `THIRD_LEVEL_SQl` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '三级分摊方案',
  `DEL_FLAG` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '备注',
  `IS_LAST` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否末级',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='成本项目管理';

CREATE TABLE `cost_project_dept` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '账套编码',
  `PROJECT_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目编码',
  `DEPT_CODE` bigint(20) DEFAULT NULL COMMENT '科室编码',
  `DEL_FLAG` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=324 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='成本项目科室设置';

CREATE TABLE `cost_project_num` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '账套编码',
  `PROJECT_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目编码',
  `DEPT_CODE` bigint(20) DEFAULT NULL COMMENT '科室编码',
  `NOW_DATE` date DEFAULT NULL COMMENT '年月',
  `MONEY` decimal(20,2) DEFAULT NULL COMMENT '成本金额',
  `DEL_FLAG` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=575 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='成本金额管理';

CREATE TABLE `cost_project_num_sql` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `NUM_SQL` text COMMENT '获取成本sql',
  `SQL_TYPE` varchar(2) DEFAULT NULL COMMENT 'sql类型 1sql，2过程',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='成本sql';

CREATE TABLE `form_design` (
  `ID` varchar(64) NOT NULL,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `FORM_NAME` varchar(200) DEFAULT NULL COMMENT '表单名称',
  `FORM_JSON` varchar(4000) DEFAULT NULL COMMENT '表单JSON',
  `CREATE_BY` varchar(64) DEFAULT NULL COMMENT '创建人',
  `CREATE_DATE` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(64) DEFAULT NULL COMMENT '修改人',
  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARKS` varchar(255) DEFAULT NULL COMMENT '备注',
  `DEL_FLAG` char(1) NOT NULL COMMENT '删除标识',
  `FORM_CODE` varchar(64) NOT NULL COMMENT '表单编码',
  `GEN_TABLE_NAME` varchar(100) DEFAULT NULL COMMENT '表名',
  `PAGE_NUM` varchar(5) DEFAULT NULL COMMENT '每页条数',
  `FORM_URL` varchar(256) DEFAULT NULL COMMENT '访问链接',
  `FORM_HTML` blob COMMENT '表单html',
  `FORM_DATA` blob COMMENT '表单数据',
  `FORM_TYPE` varchar(4000) DEFAULT NULL COMMENT '自定义模板',
  `SORT_NO` bigint(10) DEFAULT NULL COMMENT '排序',
  `IS_SUM` varchar(2) DEFAULT NULL COMMENT '是否汇总',
  `title` varchar(50) DEFAULT NULL COMMENT '标题',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '说明',
  `is_track` varchar(2) DEFAULT NULL COMMENT '是否跟踪',
  `originalCode` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '选择的模板Code',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='模板管理主表';

CREATE TABLE `form_design_field` (
  `ID` varchar(64) NOT NULL,
  `FORM_DESIGN_ID` varchar(64) DEFAULT NULL COMMENT '主表标识',
  `FIELD_NAME` varchar(64) NOT NULL COMMENT '表字段名',
  `FIELD_DESC` varchar(64) DEFAULT NULL COMMENT '字段描述',
  `IS_NOT_NULL` char(1) DEFAULT NULL COMMENT '是否必填',
  `FIELD_LENGTH` int(11) DEFAULT NULL COMMENT '字段长度',
  `IS_LIST` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '是否在列表显示(0否1是)',
  `IS_EDIT` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '是否在编辑页面显示(0否1是)',
  `IS_QUERY` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '是否查询列(0否1是)',
  `QUERY_TYPE` varchar(32) DEFAULT NULL COMMENT '查询条件（1大于2等于3小于4包含）',
  `QUERY_DEFAULT` varchar(255) DEFAULT NULL COMMENT '查询默认值',
  `SORT_NO` int(11) DEFAULT NULL COMMENT '显示排序',
  `VALID_TYPE` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `DB_TYPE` varchar(64) DEFAULT NULL COMMENT '数据库类型',
  `PMS_TYPE` varchar(10) DEFAULT NULL COMMENT '绩效字段类型',
  `FIELD_TYPE` varchar(64) DEFAULT NULL COMMENT '字段类型',
  `DICT_TYPE` varchar(128) DEFAULT NULL COMMENT '调取的字典',
  `TEXT_LINES` varchar(10) DEFAULT NULL COMMENT '文本行数',
  `MAX_VALUE` varchar(10) DEFAULT NULL COMMENT '最大值',
  `MIN_VALUE` varchar(10) DEFAULT NULL COMMENT '最小值',
  `DECIML_DIGIT` varchar(10) DEFAULT NULL COMMENT '小数位数',
  `IS_PERCENTE` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否百分比(0否1是)',
  `DEFAULT_VALUE` varchar(64) DEFAULT NULL COMMENT '默认值',
  `PMS_EXPLAIN` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '说明',
  `INDEX_CODE` varchar(64) DEFAULT NULL COMMENT '考核指标',
  `IS_SORT` varchar(1) DEFAULT NULL COMMENT '是否排序',
  `SOORT_TYPE` varchar(1) DEFAULT NULL COMMENT '排序类型1正序2倒序',
  `OPTIONS` varchar(512) DEFAULT NULL COMMENT 'radio的数据',
  `SPAN` int(2) DEFAULT NULL COMMENT '宽度',
  `IS_SUM` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否汇总(0否1是)',
  `SUM_TYPE` varchar(2) DEFAULT NULL COMMENT '汇总方式',
  `DATE_DEFAULT_VALUE` varchar(2) DEFAULT NULL COMMENT '日期默认',
  `IS_LIST_QUERY` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '小页面查询标志(0否1是)',
  `DISPLAY_TYPE` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '显示类型',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='模板管理子表';

CREATE TABLE `gen_table` (
  `table_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='代码生成业务表';

CREATE TABLE `gen_table_column` (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典类型',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1141 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='代码生成业务表字段';

CREATE TABLE `hr_encode_rule` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `RULE_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规则名称',
  `RULE_DB` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规则数据库表',
  `RULE_COLUMN` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规则数据库对应字段',
  `RULE_CONTENT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规则内容',
  `RULE_FIRST` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规则内容第一部分',
  `RULE_SECOND` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规则内容第二部分',
  `RULE_THIRD` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规则内容第三部分',
  `DEL_FLAG` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '删除标记（0未删除，1已删除）',
  `CREATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='编码规则';

CREATE TABLE `hr_encode_rule_tree` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `RULE_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规则名称',
  `RULE_DB` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规则数据库表',
  `RULE_COLUMN` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规则数据库对应字段',
  `RULE_CONTENT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '规则内容',
  `DEL_FLAG` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '删除标记（0未删除，1已删除）',
  `CREATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='编码规则树形';

CREATE TABLE `hr_post_manage` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `POST_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '岗位编码',
  `POST_NAME` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '岗位名称',
  `POST_EXPLAIN` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任职说明',
  `DUTY` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '职责说明',
  `INTRODUCE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '岗位简介',
  `SPARE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备用字段',
  `SPARE_ONE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备用字段',
  `DEL_FLAG` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '删除标记（0未删除，1已删除）',
  `CREATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '备注',
  `POST_LEVEL` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '岗位级别 1院级，2科级',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='岗位管理';

CREATE TABLE `hr_process_model` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PROCINS_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '流程key',
  `PROCINS_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '流程名称',
  `TABEL_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表名',
  `FORM_URL` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '表单url',
  `TYPE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '类型',
  `DEL_FLAG` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '删除标记（0未删除，1已删除）',
  `CREATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='流程模块表';

CREATE TABLE `hr_sys_emp` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位ID',
  `DEPT_CODE` bigint(20) DEFAULT NULL COMMENT '科室ID',
  `EMP_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '人员工号',
  `EMP_NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '人员姓名',
  `SPELL` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拼音码',
  `CARD_TYPE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '证件类型',
  `ID_NUMBER` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '证件号码',
  `GENDER` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '性别',
  `BIRTHDAY` datetime DEFAULT NULL COMMENT '出生日期',
  `MARITAL_STATUS` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '婚姻状况',
  `NATION_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '民族',
  `POLITICAL_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '政治面貌',
  `TELEPHONE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系方式',
  `EMAIL` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '电子邮箱',
  `HOUSEHOLD_TYPE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '户口性质',
  `HOME_ADD` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '家庭住址',
  `HOME_TOWN` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '籍贯',
  `IS_POST` decimal(9,0) DEFAULT NULL COMMENT '是否在岗（1在职2离职）',
  `KIND_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '人员分类',
  `POST_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '岗位编码',
  `INHOSPITAL_DATE` datetime DEFAULT NULL COMMENT '来院时间',
  `TITLE_CODE` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '职称',
  `TITLE_LEVEL` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '职称级别',
  `TITLE_KIND_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '职称系列',
  `EDU_CODE` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '学历',
  `DEGREE_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '学位',
  `GRADUATE_ACADEMY` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '毕业院校',
  `STUDY_SPECSORT` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '所学专业',
  `DUTY` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '行政职务',
  `DUTYDATE` datetime DEFAULT NULL COMMENT '行政职务时间',
  `DUTY_LEVEL` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '行政职级',
  `AUDIT_STATE` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核状态',
  `DEL_FLAG` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '删除标记（0未删除，1已删除）',
  `CREATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '备注',
  `IS_CHANGE` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否修改',
  `PROCLNS_ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '流程ID',
  `ANNEX` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '附件',
  `STAFFSORT` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '人员类别',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='人员基本信息';

CREATE TABLE `pms_account_dept` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PARENT_CODE` varchar(50) DEFAULT NULL COMMENT '上级科室编码',
  `DEPT_ROOM_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '科室编码',
  `DEPT_ROOM_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '科室名称',
  `NOW_DATE` date DEFAULT NULL COMMENT '年月',
  `IS_ACCOUNT` varchar(5) DEFAULT NULL COMMENT '是否核算',
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '账套编码',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=189 DEFAULT CHARSET=utf8 COMMENT='核算科室信息';

CREATE TABLE `pms_average_person` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SET_DATE` date DEFAULT NULL COMMENT '设置年月',
  `SET_TIME` date DEFAULT NULL COMMENT '录入时间',
  `SET_PERSON` varchar(50) DEFAULT NULL COMMENT '录入人',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  `CODE` varchar(50) DEFAULT NULL COMMENT '平均奖科人数编码',
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '账套编码',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='平均奖科人数';

CREATE TABLE `pms_average_person_detail` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `AVG_CODE` varchar(50) DEFAULT NULL COMMENT '平均奖科人数主表编码',
  `DEPT_CODE` varchar(50) DEFAULT NULL COMMENT '科室编码',
  `DEPT_NAME` varchar(50) DEFAULT NULL COMMENT '科室名称',
  `DOC_CODE` varchar(30) DEFAULT NULL COMMENT '医生编码',
  `DOC_NAME` varchar(30) DEFAULT NULL COMMENT '医生名称',
  `IS_REWARD` char(1) DEFAULT NULL COMMENT '是否发奖金（0：否1：是）',
  `WORK_DAYS` varchar(10) DEFAULT NULL COMMENT '工作日数',
  `POST_RATIO` varchar(10) DEFAULT NULL COMMENT '岗位系数',
  `DUTY_REWARD_RATIO` varchar(10) DEFAULT NULL COMMENT '责任奖系数',
  `DEL_FLAG` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='平均奖科人数明细';

CREATE TABLE `pms_balanced_scorecard` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `DEPT_CODE` varchar(100) DEFAULT NULL COMMENT '科室编码',
  `NOW_DATE` date DEFAULT NULL COMMENT '年月',
  `INDEX_CODE` varchar(20) DEFAULT NULL COMMENT '指标编码',
  `SCORECARD_VALUE` decimal(10,2) DEFAULT NULL COMMENT '分值',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=406 DEFAULT CHARSET=utf8 COMMENT='平衡记分卡';

CREATE TABLE `pms_bonus_calculation` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `NAME` varchar(200) COLLATE utf8mb4_general_ci NOT NULL COMMENT '奖金名称',
  `PERIOD` date NOT NULL COMMENT '奖金年月',
  `AUDIT_STATE` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '审核状态',
  `ARCHIVE_STATE` char(1) COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否归档(0是；1否)',
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '账套编码',
  `DEL_FLAG` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `UPDATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '备注',
  `PROCEDURE_ID` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '流程id',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='奖金核算';

CREATE TABLE `pms_bonus_person_audit` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `DEPT_CODE` varchar(100) DEFAULT NULL COMMENT '科室编码',
  `NOW_DATE` date DEFAULT NULL COMMENT '年月',
  `AUDIT_STATE` char(1) NOT NULL COMMENT '审核状态',
  `PROCEDURE_ID` varchar(64) DEFAULT NULL COMMENT '流程id',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='奖金人员审核';

CREATE TABLE `pms_dept_sort` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `DEPT_ROOM_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '核算科室编码',
  `SORT_CODE` varchar(50) DEFAULT NULL COMMENT '类别编码',
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '账套编码',
  `NOW_DATE` date DEFAULT NULL COMMENT '年月',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1394 DEFAULT CHARSET=utf8 COMMENT='核算单位类别';

CREATE TABLE `pms_dept_sort_detail` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SORT_CODE` varchar(50) DEFAULT NULL COMMENT '类别编码',
  `SORT_NAME` varchar(50) DEFAULT NULL COMMENT '类别名称',
  `EDIT_ALLOW` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '是否允许编辑(0是,1否)',
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '账套编码',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='核算单位类别设置';

CREATE TABLE `pms_evaluate_bsc` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `BSC_CODE` varchar(50) DEFAULT NULL COMMENT 'BSC类别编码',
  `BSC_NAME` varchar(100) DEFAULT NULL COMMENT 'BSC类别名称',
  `PARENT_CODE` varchar(50) DEFAULT NULL COMMENT '上级编码',
  `ANCESTORS` varchar(1000) DEFAULT NULL COMMENT '祖级编码',
  `IS_STOP` varchar(1) DEFAULT NULL COMMENT '是否停用（0是，1否）',
  `IS_LAST` varchar(1) DEFAULT NULL COMMENT '是否末级（0是，1否）',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8 COMMENT='BSC分类';

CREATE TABLE `pms_evaluate_data` (
  `ID` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '账套编码',
  `DEPT_CODE` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '科室编码',
  `UNIT_CODE` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '院，科，人编码',
  `SET_CODE` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '指标集编码',
  `GUIDE_CODE` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '固定与指标代码相同',
  `GUIDE_VALUE` decimal(10,4) DEFAULT NULL COMMENT '指标值',
  `GUIDE_VALUE_RESULT` decimal(10,4) DEFAULT NULL COMMENT '指标值计算结果',
  `GUIDE_TYPE` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织类型(Y(院)；K(科)；R(人))',
  `DATA_TYPE` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据类型(1平衡计分卡；2奖金；3二次核算)',
  `ACT_DATE` date DEFAULT NULL COMMENT '数据作用日期',
  `ACT_DATE_END` date DEFAULT NULL COMMENT '数据作用开始日期',
  `ACT_DATE_BEGIN` date DEFAULT NULL COMMENT '数据作用结束日期',
  `DEL_FLAG` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=505 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='指标数据值(通过sql和表达式计算获取)';

CREATE TABLE `pms_evaluate_data_temp` (
  `ID` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `COMP_CODE` int(11) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '账套编码',
  `UNIT_CODE` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '院，科，人编码',
  `GUIDE_CODE` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '固定与指标代码相同',
  `GUIDE_VALUE` decimal(10,4) DEFAULT NULL COMMENT '指标值',
  `GUIDE_VALUE_RESULT` decimal(10,4) DEFAULT NULL COMMENT '指标值计算结果',
  `GUIDE_TYPE` char(2) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Y，K，R',
  `ACT_DATE` date DEFAULT NULL COMMENT '数据作用日期',
  `ACT_DATE_END` date DEFAULT NULL COMMENT '数据作用开始日期',
  `ACT_DATE_BEGIN` date DEFAULT NULL COMMENT '数据作用结束日期',
  `DEL_FLAG` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '备注',
  `DEPT_CODE` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '科室编码',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=421 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='指标数据值零时表(pms_bonus_person_audit通过后,写入pms_evaluate_data表)';

CREATE TABLE `pms_evaluate_index` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `BSC_ONE_CODE` varchar(20) DEFAULT NULL COMMENT 'BSC一级分类',
  `BSC_SECOND_CODE` varchar(20) DEFAULT NULL COMMENT 'BSC二级分类',
  `DEPT_TYPE` varchar(20) DEFAULT NULL COMMENT '部门分类',
  `ORG_TYPE` varchar(20) DEFAULT NULL COMMENT '组织类别',
  `INDEX_CODE` varchar(20) DEFAULT NULL COMMENT '指标编码',
  `INDEX_NAME` varchar(100) DEFAULT NULL COMMENT '指标名称',
  `IS_EXPRESS` varchar(1) DEFAULT NULL COMMENT '是否表达式（0是，1否）',
  `FORMULA_CODE` varchar(300) DEFAULT NULL COMMENT '公式编码',
  `FORMULA_NAME` varchar(300) DEFAULT NULL COMMENT '公式名称',
  `FORMULA_VARIABLE` varchar(255) DEFAULT NULL COMMENT '公式变量，多个逗号隔开',
  `IS_SECTION` varchar(1) DEFAULT NULL COMMENT '是否区间（0是，1否）',
  `DB_CODE` varchar(64) DEFAULT NULL COMMENT '数据源CODE',
  `INDEX_SQL` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '指标算法sql',
  `SECTION_START` datetime DEFAULT NULL COMMENT '区间开始时间',
  `SECTION_END` datetime DEFAULT NULL COMMENT '区间结束时间',
  `IS_COUNS` varchar(1) DEFAULT NULL COMMENT '是否高优指标（0是，1否）',
  `IS_STOP` varchar(1) DEFAULT NULL COMMENT '是否停用（0是，1否，2不显示）',
  `IS_COMP` varchar(1) DEFAULT NULL COMMENT '是否综合评价指标（0是，1否）',
  `IS_ABSOLUTE` varchar(1) DEFAULT NULL COMMENT '是否绝对值（0是，1否）',
  `INDEX_TYPE` varchar(20) DEFAULT NULL COMMENT '指标类型（1时间有关指标，2时间无关指标）',
  `IS_REGULAR` varchar(1) DEFAULT NULL COMMENT '是否固定分值（0是，1否）',
  `INDEX_RATIO` varchar(20) DEFAULT NULL COMMENT '指标阈值比',
  `INDEX_CONTENT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '指标说明',
  `INDEX_SORT` varchar(20) DEFAULT NULL COMMENT '顺序号',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  `IS_BONUS` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否奖金(0是1否)',
  `IS_NECESSARY` varchar(1) DEFAULT NULL COMMENT '是否必备指标(0是1否)',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=173 DEFAULT CHARSET=utf8 COMMENT='指标管理';

CREATE TABLE `pms_evaluate_index_bonus` (
                                            `ID` int(11) NOT NULL AUTO_INCREMENT,
                                            `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
                                            `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
                                            `DEPT_ROOM_TYPE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '科室类别(字典JXGL_JJ_KSLB)',
                                            `INDEX_CODE` varchar(64) DEFAULT NULL COMMENT '指标编码',
                                            `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
                                            `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
                                            `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
                                            `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
                                            `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
                                            `REMARK` text COMMENT '备注',
                                            PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='奖金指标';

CREATE TABLE `pms_evaluate_set` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `SET_CODE` varchar(20) DEFAULT NULL COMMENT '指标集编码',
  `SET_NAME` varchar(100) DEFAULT NULL COMMENT '指标集名称',
  `ONE_CODE` varchar(20) DEFAULT NULL COMMENT '一级分类',
  `SECOND_CODE` varchar(20) DEFAULT NULL COMMENT '二级分类',
  `DEPT_TYPE` varchar(20) DEFAULT NULL COMMENT '部门分类',
  `ORG_TYPE` varchar(20) DEFAULT NULL COMMENT '组织类别(适用对象)',
  `PART_YEAR` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '适用年份（下拉框）',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  `IS_BONUS` varchar(1) DEFAULT NULL COMMENT '是否金额(0是1否)',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8 COMMENT='指标集管理';

CREATE TABLE `pms_evaluate_set_dept` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `SET_DEPT_CODE` varchar(20) DEFAULT NULL COMMENT '唯一编码',
  `SET_CODE` varchar(20) DEFAULT NULL COMMENT '指标集编码',
  `DEPT_CODE` varchar(100) DEFAULT NULL COMMENT '科室编码',
  `INTERNAL_SCORE` json DEFAULT NULL COMMENT '内部管理分值',
  `FINANCE_SCORE` varchar(20) DEFAULT NULL COMMENT '财务管理分值',
  `SERVICE_SCORE` varchar(20) DEFAULT NULL COMMENT '服务分值',
  `EFFICIENT_SCORE` varchar(20) DEFAULT NULL COMMENT '效率分值',
  `NOW_DATE` date DEFAULT NULL COMMENT '年月',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=237 DEFAULT CHARSET=utf8 COMMENT='指标集与科室管理';

CREATE TABLE `pms_evaluate_set_dept_detail` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `SET_DEPT_CODE` varchar(20) DEFAULT NULL COMMENT '唯一编码',
  `INDEX_CODE` varchar(20) DEFAULT NULL COMMENT '指标编码',
  `REDUCE` varchar(20) DEFAULT NULL COMMENT '降低量',
  `DEDUCT` varchar(20) DEFAULT NULL COMMENT '扣分',
  `INCREASE` varchar(20) DEFAULT NULL COMMENT '增加量',
  `EXTRA` varchar(20) DEFAULT NULL COMMENT '加分',
  `UNIT` varchar(20) DEFAULT NULL COMMENT '单位(%)',
  `TARGET` varchar(20) DEFAULT NULL COMMENT '目标值',
  `INDEX_SCORE` varchar(20) DEFAULT NULL COMMENT '指标分数',
  `IS_HYPER` varchar(5) DEFAULT NULL COMMENT '是否允许超分',
  `IS_NEGATIVE` varchar(5) DEFAULT NULL COMMENT '是否允许负分',
  `IS_FIXED` varchar(5) DEFAULT NULL COMMENT '是否固定分值',
  `THRESHOLD` varchar(20) DEFAULT NULL COMMENT '阀值',
  `OVER_LIMIT` varchar(20) DEFAULT NULL COMMENT '超分限制',
  `DEDUCTION_LIMIT` varchar(20) DEFAULT NULL COMMENT '减分限制',
  `NOW_DATE` date DEFAULT NULL COMMENT '年月',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1326 DEFAULT CHARSET=utf8 COMMENT='指标集与科室管理明细';

CREATE TABLE `pms_evaluate_set_index` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `SET_CODE` varchar(20) DEFAULT NULL COMMENT '指标集编码',
  `INDEX_CODE` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '指标编码',
  `WEIGHT` varchar(20) DEFAULT NULL COMMENT '权重',
  `WEIGHT_RATIO` decimal(10,4) DEFAULT NULL COMMENT '权重比值',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=365 DEFAULT CHARSET=utf8 COMMENT='指标集指标';

CREATE TABLE `pms_evaluate_set_role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `SET_CODE` varchar(20) DEFAULT NULL COMMENT '指标集编码',
  `INDEX_NAME` varchar(100) DEFAULT NULL COMMENT '角色编码',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=149 DEFAULT CHARSET=utf8 COMMENT='指标集角色';

CREATE TABLE `pms_evaluate_set_type` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `BSC_CODE` varchar(50) DEFAULT NULL COMMENT '指标集类别编码',
  `BSC_NAME` varchar(100) DEFAULT NULL COMMENT '指标集类别名称',
  `PARENT_CODE` varchar(50) DEFAULT NULL COMMENT '上级编码',
  `ANCESTORS` varchar(1000) DEFAULT NULL COMMENT '祖级编码',
  `IS_STOP` varchar(1) DEFAULT NULL COMMENT '是否停用（0是，1否）',
  `IS_LAST` varchar(1) DEFAULT NULL COMMENT '是否末级（0是，1否）',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8 COMMENT='指标集分类';

CREATE TABLE `pms_form_design` (
  `ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `FORM_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `FORM_JSON` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `CREATE_BY` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  `UPDATE_BY` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  `REMARKS` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `DEL_FLAG` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `FORM_CODE` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `GEN_TABLE_NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `FORM_URL` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `FORM_HTML` blob,
  `FORM_DATA` blob,
  `FORM_TYPE` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `pms_form_design_field` (
  `ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `FIELD_NAME` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `IS_QUERY` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `IS_LIST` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `IS_EDIT` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `QUERY_TYPE` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `SORT_NO` int(11) DEFAULT NULL,
  `FORM_DESIGN_ID` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `FIELD_DESC` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `IS_NOT_NULL` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `FIELD_LENGTH` int(11) DEFAULT NULL,
  `VALID_TYPE` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `DB_TYPE` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `FIELD_TYPE` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `DICT_TYPE` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `OPTIONS` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `pms_his_income` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `REVENUE_CODE` varchar(50) DEFAULT NULL COMMENT '收入类别编码',
  `REVENUE_NAME` varchar(100) DEFAULT NULL COMMENT '收入类别名称',
  `SPELL` varchar(30) DEFAULT NULL COMMENT '拼音码',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='HIS收入类别';

CREATE TABLE `pms_index` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `INDEX_NAME` varchar(50) DEFAULT NULL COMMENT '指标名称',
  `INDEX_VALUE` varchar(50) DEFAULT NULL COMMENT '指标值',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='指标配置维护';

CREATE TABLE `pms_quality_generate` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `DEPT_CODE` bigint(20) DEFAULT NULL COMMENT '科室编码',
  `INDEX_CODE` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '指标编码',
  `TEMP_CODE` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模板编码',
  `INDEX_SCORE` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '指标分值',
  `OCCURE_TIME` datetime DEFAULT NULL COMMENT '发生时间(YYYYMM)',
  `ACTUAL_SCORE` varchar(20) DEFAULT NULL COMMENT '实际得分',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=159 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='质量数据生成';

CREATE TABLE `pms_quality_index` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `DEPT_CODE` bigint(20) DEFAULT NULL COMMENT '主管部门',
  `INDEX_CODE` varchar(20) DEFAULT NULL COMMENT '指标编码',
  `INDEX_NAME` varchar(100) DEFAULT NULL COMMENT '指标名称',
  `INDEX_SCORE` varchar(20) DEFAULT NULL COMMENT '指标分值',
  `TEMP_CODE` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '模板编码',
  `DATE_COL` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '时间列',
  `DEPT_COL` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门列',
  `NUM_COL` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '数值列',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='质量考评指标';

CREATE TABLE `pms_quality_index_detail` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `INDEX_CODE` varchar(20) DEFAULT NULL COMMENT '指标编码',
  `EVA_CONT` varchar(200) DEFAULT NULL COMMENT '考评内容',
  `EVA_CRITERIA` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '考评标准',
  `EVA_METHOD` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '考评办法',
  `IS_KPI` varchar(20) DEFAULT NULL COMMENT '是否KPI',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=576 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='质量考评指标明细';

CREATE TABLE `pms_revenue` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `REVENUE_CODE` varchar(50) DEFAULT NULL COMMENT '项目编码',
  `REVENUE_NAME` varchar(100) DEFAULT NULL COMMENT '项目名称',
  `SPELL` varchar(30) DEFAULT NULL COMMENT '拼音码',
  `INP_BILL` varchar(20) DEFAULT NULL COMMENT '住院开单(%)',
  `INP_EXEC` varchar(20) DEFAULT NULL COMMENT '住院执行(%)',
  `INP_CARE` varchar(20) DEFAULT NULL COMMENT '住院护理(%)',
  `OUTP_BILL` varchar(20) DEFAULT NULL COMMENT '门诊开单(%)',
  `OUTP_EXEC` varchar(20) DEFAULT NULL COMMENT '门诊执行(%)',
  `OUTP_CARE` varchar(20) DEFAULT NULL COMMENT '门诊护理(%)',
  `COOP_MED` varchar(20) DEFAULT NULL COMMENT '合作医疗(%)',
  `ACCOUNT_TYPE` varchar(20) DEFAULT NULL COMMENT '核算类型(1参与核算，2不参与核算)',
  `CONV_RATIO` varchar(20) DEFAULT NULL COMMENT '折算比(%)',
  `FIX_CONV` varchar(20) DEFAULT NULL COMMENT '固定折算(%)',
  `PROJECT_CODE` varchar(20) DEFAULT NULL COMMENT '成本对照(成本项目)',
  `HIS_REVENUM_TYPEDE` varchar(20) DEFAULT NULL COMMENT '收入类别(HIS)',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=153 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='收入核算方案';

CREATE TABLE `pms_revenue_apport` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `REVENUE_CODE` varchar(50) DEFAULT NULL COMMENT '项目编码',
  `RECEIVE_NUM` decimal(20,2) DEFAULT NULL COMMENT '应收分解金额',
  `ACTUAL_NUM` decimal(20,2) DEFAULT NULL COMMENT '实际分解金额',
  `DPET_CODE` bigint(20) DEFAULT NULL COMMENT '科室编码',
  `OCCURE_TIME` datetime DEFAULT NULL COMMENT '发生时间(YYYYMM)',
  `REVENUE_TYPE` varchar(5) DEFAULT NULL COMMENT '收入类别(1门诊，2住院)',
  `REVENUE_KIND` varchar(5) DEFAULT NULL COMMENT '收入类型（1正常收入，2附加收入）',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  `SETTLE_TYPE` varchar(2) DEFAULT NULL COMMENT '结算标识(1,正常,2附加收入)',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=145 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='收入生成';

CREATE TABLE `pms_revenue_dept` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `REVENUE_CODE` varchar(50) DEFAULT NULL COMMENT '项目编码',
  `DEPT_CODE` bigint(20) DEFAULT NULL COMMENT '科室编码',
  `RATIO` varchar(20) DEFAULT NULL COMMENT '比例这只',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='收入核算科室设置';

CREATE TABLE `pms_revenue_num` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `REVENUE_CODE` varchar(50) DEFAULT NULL COMMENT '项目编码',
  `RECEIVE_NUM` decimal(20,2) DEFAULT NULL COMMENT '应收金额',
  `ACTUAL_NUM` decimal(20,2) DEFAULT NULL COMMENT '实际金额',
  `BILL_DEPT` bigint(20) DEFAULT NULL COMMENT '开单科室',
  `EXEC_DEPT` bigint(20) DEFAULT NULL COMMENT '执行科室',
  `CARE_DEPT` bigint(20) DEFAULT NULL COMMENT '护理单元',
  `BILL_DOC` varchar(20) DEFAULT NULL COMMENT '开单医生',
  `OCCURE_TIME` datetime DEFAULT NULL COMMENT '发生时间',
  `REVENUE_TYPE` varchar(5) DEFAULT NULL COMMENT '收入类别(1门诊，2住院)',
  `SETTLE_MARK` varchar(5) DEFAULT NULL COMMENT '结算标识(1收付实现，2责权发生)',
  `REVENUE_KIND` varchar(5) DEFAULT NULL COMMENT '收入类型（1正常收入，2附加收入）',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6696 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='收入金额管理';

CREATE TABLE `pms_revenue_num_sql` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `NUM_SQL` text COMMENT '获取收入sql',
  `SQL_TYPE` varchar(2) DEFAULT NULL COMMENT 'sql类型 1sql，2过程',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='收入sql';

CREATE TABLE `pms_revenue_special` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `SPECIAL_CODE` varchar(50) DEFAULT NULL COMMENT '特殊项目编码',
  `REVENUE_CODE` varchar(50) DEFAULT NULL COMMENT '项目编码',
  `REVENUE_NAME` varchar(100) DEFAULT NULL COMMENT '项目名称',
  `BILL_DEPT` bigint(20) DEFAULT NULL COMMENT '开单科室',
  `EXEC_DEPT` bigint(20) DEFAULT NULL COMMENT '执行科室',
  `INP_BILL` varchar(20) DEFAULT NULL COMMENT '住院开单(%)',
  `INP_EXEC` varchar(20) DEFAULT NULL COMMENT '住院执行(%)',
  `INP_CARE` varchar(20) DEFAULT NULL COMMENT '住院护理(%)',
  `OUTP_BILL` varchar(20) DEFAULT NULL COMMENT '门诊开单(%)',
  `OUTP_EXEC` varchar(20) DEFAULT NULL COMMENT '门诊执行(%)',
  `OUTP_CARE` varchar(20) DEFAULT NULL COMMENT '门诊护理(%)',
  `COOP_MED` varchar(20) DEFAULT NULL COMMENT '合作医疗(%)',
  `ACCOUNT_TYPE` varchar(20) DEFAULT NULL COMMENT '核算类型(1参与核算，2不参与核算)',
  `CONV_RATIO` varchar(20) DEFAULT NULL COMMENT '折算比(%)',
  `FIX_CONV` varchar(20) DEFAULT NULL COMMENT '固定折算(%)',
  `PROJECT_CODE` varchar(20) DEFAULT NULL COMMENT '成本对照(成本项目)',
  `HIS_REVENUM_TYPEDE` varchar(20) DEFAULT NULL COMMENT '收入类别(HIS)',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='特殊收入项目方案';

CREATE TABLE `pms_revenue_special_dept` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `SPECIAL_CODE` varchar(50) DEFAULT NULL COMMENT '项目编码',
  `DEPT_CODE` bigint(20) DEFAULT NULL COMMENT '科室编码',
  `RATIO` varchar(20) DEFAULT NULL COMMENT '比例这只',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='特殊收入项目科室设置';

CREATE TABLE `pms_revenue_special_temp` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `SPECIAL_CODE` varchar(50) DEFAULT NULL COMMENT '特殊项目编码',
  `REVENUE_CODE` varchar(50) DEFAULT NULL COMMENT '项目编码',
  `REVENUE_NAME` varchar(100) DEFAULT NULL COMMENT '项目名称',
  `BILL_DEPT` bigint(20) DEFAULT NULL COMMENT '开单科室',
  `EXEC_DEPT` bigint(20) DEFAULT NULL COMMENT '执行科室',
  `INP_BILL` varchar(20) DEFAULT NULL COMMENT '住院开单(%)',
  `INP_EXEC` varchar(20) DEFAULT NULL COMMENT '住院执行(%)',
  `INP_CARE` varchar(20) DEFAULT NULL COMMENT '住院护理(%)',
  `OUTP_BILL` varchar(20) DEFAULT NULL COMMENT '门诊开单(%)',
  `OUTP_EXEC` varchar(20) DEFAULT NULL COMMENT '门诊执行(%)',
  `OUTP_CARE` varchar(20) DEFAULT NULL COMMENT '门诊护理(%)',
  `COOP_MED` varchar(20) DEFAULT NULL COMMENT '合作医疗(%)',
  `ACCOUNT_TYPE` varchar(20) DEFAULT NULL COMMENT '核算类型(1参与核算，2不参与核算)',
  `CONV_RATIO` varchar(20) DEFAULT NULL COMMENT '折算比(%)',
  `FIX_CONV` varchar(20) DEFAULT NULL COMMENT '固定折算(%)',
  `PROJECT_CODE` varchar(20) DEFAULT NULL COMMENT '成本对照(成本项目)',
  `HIS_REVENUM_TYPEDE` varchar(20) DEFAULT NULL COMMENT '收入类别(HIS)',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  `TYPE` varchar(1) DEFAULT NULL COMMENT '临时表标识 0不变化 1新增 2修改 3删除',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=866 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='特殊收入项目方案临时表';

CREATE TABLE `pms_second_dept` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `DEPT_CODE` varchar(50) DEFAULT NULL COMMENT '核算科室编码',
  `DIS_CODE` varchar(50) DEFAULT NULL COMMENT '指标集编码',
  `NOW_DATE` date DEFAULT NULL COMMENT '年月',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8 COMMENT='二次分配科室指标';

CREATE TABLE `pms_second_distribution` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `DIS_CODE` varchar(50) DEFAULT NULL COMMENT '指标集编码',
  `DIS_NAME` varchar(50) DEFAULT NULL COMMENT '指标集名称',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='二次分配指标集';

CREATE TABLE `pms_second_distribution_detail` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `DIS_CODE` varchar(50) DEFAULT NULL COMMENT '指标集编码',
  `INDEX_CODE` varchar(50) DEFAULT NULL COMMENT '指标编码',
  `INDEX_NAME` varchar(50) DEFAULT NULL COMMENT '指标名称',
  `WEIGHTS` varchar(50) DEFAULT NULL COMMENT '权重',
  `WEIGHT_RATIO` decimal(10,4) DEFAULT NULL COMMENT '权重比值',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='二次分配指标集明细';

CREATE TABLE `pms_separate_account_commission` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `DEPT_CODE` varchar(50) DEFAULT NULL COMMENT '科室编码',
  `DEPT_NAME` varchar(50) DEFAULT NULL COMMENT '科室名称',
  `COMMISSION_DATE` date NOT NULL COMMENT '核算日期,年月',
  `SORT_CODE` varchar(50) DEFAULT NULL COMMENT '类别',
  `RATIO` decimal(20,2) DEFAULT NULL COMMENT '比例',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=787 DEFAULT CHARSET=utf8 COMMENT='独立核算提成比';

CREATE TABLE `pms_separate_account_sort` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SORT_CODE` varchar(50) DEFAULT NULL COMMENT '类别编码',
  `SORT_NAME` varchar(50) DEFAULT NULL COMMENT '类别名称',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='独立核算提成比类别';

CREATE TABLE `pms_skill_achieve` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `ACHIEVE_CODE` varchar(20) DEFAULT NULL COMMENT '成果编码',
  `ACHIEVE_NAME` varchar(100) DEFAULT NULL COMMENT '成果名称',
  `DEPT_CODE` bigint(20) DEFAULT NULL COMMENT '科室编码',
  `COMPLETE_UNIT` varchar(100) DEFAULT NULL COMMENT '协作单位',
  `PERSON_CODE` varchar(20) DEFAULT NULL COMMENT '完成人',
  `EDUCATION` varchar(20) DEFAULT NULL COMMENT '完成人学历',
  `SPECIAL` varchar(20) DEFAULT NULL COMMENT '完成人专业',
  `TITLE_CODE` varchar(36) DEFAULT NULL COMMENT '职称',
  `AWARD_CATEGORY` varchar(36) DEFAULT NULL COMMENT '获奖类别',
  `AWARD_LEVEL` varchar(36) DEFAULT NULL COMMENT '获奖级别',
  `IS_EXTENSION` varchar(20) DEFAULT NULL COMMENT '是否推广（0是，1否）',
  `AWARD_TIME` datetime DEFAULT NULL COMMENT '获奖时间',
  `EXTENSION_INCOME` varchar(20) DEFAULT NULL COMMENT '获奖收益',
  `APPROVE` varchar(20) DEFAULT NULL COMMENT '批准单位（国家，军队，院内）',
  `TASK_SOURCE` varchar(20) DEFAULT NULL COMMENT '任务来源（国家，全军，军区，单位，自选，其他）',
  `EXTENSION_SCOPE` varchar(20) DEFAULT NULL COMMENT '推广范围（军用，民用，军民通用）',
  `ACHIEVE_NATURE` varchar(20) DEFAULT NULL COMMENT '成果性质（基础研究，应用研究，试验研究，涉及试制，软科学）',
  `DRUG_APPROVAL` varchar(50) DEFAULT NULL COMMENT '药批号',
  `PATENT_INCOME` varchar(50) DEFAULT NULL COMMENT '专利收益',
  `PATENT_NO` varchar(50) DEFAULT NULL COMMENT '专利号',
  `PATENT_TYPE` varchar(50) DEFAULT NULL COMMENT '专利类别（发明专利，实用新型专利，外观设计专利）',
  `SUBJECT_WORDS` varchar(100) DEFAULT NULL COMMENT '主题词',
  `ACHIEVE_ABSTRACT` varchar(200) DEFAULT NULL COMMENT '成果摘要',
  `ANNEX` varchar(200) DEFAULT NULL COMMENT '附件',
  `AUDIT_STATE` varchar(5) DEFAULT NULL COMMENT '审核状态',
  `PROCEDURE_ID` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='成果';

CREATE TABLE `pms_skill_monograph` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `MONOGRAPH_CODE` varchar(20) DEFAULT NULL COMMENT '专著编码',
  `MONOGRAPH_NAME` varchar(100) DEFAULT NULL COMMENT '专著名称',
  `MONOGRAPH_TYPE` varchar(10) DEFAULT NULL COMMENT '专著级别（SCI收录期刊，统计源期刊，院内期刊）',
  `DEPT_CODE` bigint(20) DEFAULT NULL COMMENT '科室编码',
  `PUBLISH` varchar(50) DEFAULT NULL COMMENT '出版社',
  `PUBLIS_TIME` datetime DEFAULT NULL COMMENT '出版时间',
  `ISSN` varchar(20) DEFAULT NULL COMMENT '期刊号',
  `MAGAZINE` varchar(120) DEFAULT NULL COMMENT '杂志名称',
  `WORD_NUM` varchar(10) DEFAULT NULL COMMENT '字数',
  `AUTHOR` varchar(50) DEFAULT NULL COMMENT '作者',
  `BOOK_NUMBER` varchar(100) DEFAULT NULL COMMENT '图书编号',
  `FOLIO` varchar(10) DEFAULT NULL COMMENT '开本（大16K，16K，大32K，32K，64K，其他）',
  `PRITE_NUM` varchar(10) DEFAULT NULL COMMENT '印刷数量',
  `POSITION` varchar(10) DEFAULT NULL COMMENT '担任职务（主编，副主编，编委）',
  `MEET_NAME` varchar(100) DEFAULT NULL COMMENT '会议名称',
  `MEET_TIME` datetime DEFAULT NULL COMMENT '会议时间',
  `ANNEX` varchar(200) DEFAULT NULL COMMENT '附件',
  `AUDIT_STATE` varchar(5) DEFAULT NULL COMMENT '审核状态',
  `PROCEDURE_ID` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='专著';

CREATE TABLE `pms_skill_opinion` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `TECHNO_CODE` varchar(20) DEFAULT NULL COMMENT '编码（各种刊物编码）',
  `TECHNO_TYPE` varchar(10) DEFAULT NULL COMMENT '类别（1课题，2成果，3论文，4专著，5技术）',
  `AUDIT_STATE` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审批状态',
  `OPINION` varchar(500) DEFAULT NULL COMMENT '意见',
  `APPROVEL_TIME` datetime DEFAULT NULL COMMENT '审批时间',
  `APPROVEL_NAME` varchar(50) DEFAULT NULL COMMENT '审批人姓名',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='审批意见';

CREATE TABLE `pms_skill_person` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `TECHNO_CODE` varchar(20) DEFAULT NULL COMMENT '编码（各种刊物编码）',
  `TECHNO_TYPE` varchar(10) DEFAULT NULL COMMENT '类别（1课题，2成果，3论文，4专著，5技术）',
  `PERSON_CODE` varchar(100) DEFAULT NULL COMMENT '参与人或者作者',
  `IS_ONE` varchar(5) DEFAULT NULL COMMENT '是否第一作者',
  `APPROVEL_TIME` datetime DEFAULT NULL COMMENT '审批时间',
  `APPROVEL_NAME` varchar(50) DEFAULT NULL COMMENT '审批人姓名',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='技术参与人';

CREATE TABLE `pms_skill_technology` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `TECHNO_CODE` varchar(20) DEFAULT NULL COMMENT '编码',
  `TECHNO_NAME` varchar(100) DEFAULT NULL COMMENT '名称',
  `DEVELOP_TIME` datetime DEFAULT NULL COMMENT '开展时间',
  `TECHNO_TYPE` varchar(10) DEFAULT NULL COMMENT '水平（国内领先，军内领先，省内空白，医院空白）',
  `DEPT_CODE` bigint(20) DEFAULT NULL COMMENT '科室编码',
  `COMPLETE_NUM` varchar(5) DEFAULT NULL COMMENT '完成例数',
  `PERSON_CODE` varchar(20) DEFAULT NULL COMMENT '负责人',
  `EDUCATION` varchar(10) DEFAULT NULL COMMENT '负责人学历',
  `SPECIAL` varchar(10) DEFAULT NULL COMMENT '负责人专业',
  `TITLE_CODE` varchar(10) DEFAULT NULL COMMENT '职称',
  `EFFECT` varchar(10) DEFAULT NULL COMMENT '效果（很好，好，一般，不明显，待观察）',
  `TECHNO` varchar(10) DEFAULT NULL COMMENT '新业务新技术（引进，自主创新，消化吸收，其他）',
  `PROGRESS` varchar(240) DEFAULT NULL COMMENT '进展情况',
  `ANNEX` varchar(200) DEFAULT NULL COMMENT '附件',
  `AUDIT_STATE` varchar(5) DEFAULT NULL COMMENT '审核状态',
  `PROCEDURE_ID` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='新技术';

CREATE TABLE `pms_skill_thesis` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `THESIS_CODE` varchar(20) DEFAULT NULL COMMENT '刊物编码',
  `THESIS_NAME` varchar(100) DEFAULT NULL COMMENT '刊物名称',
  `DEPT_CODE` bigint(20) DEFAULT NULL COMMENT '科室编码',
  `YEAR` varchar(20) DEFAULT NULL COMMENT '年度',
  `THESIS_TYPE` varchar(10) DEFAULT NULL COMMENT '论文级别（SCI收录期刊，统计源期刊，院内期刊）',
  `PERSON_CODE` varchar(20) DEFAULT NULL COMMENT '负责人',
  `THESIS_SUBJECT` varchar(20) DEFAULT NULL COMMENT '论文题目',
  `ISSN` varchar(20) DEFAULT NULL COMMENT '期刊号',
  `THESIS_COLUMN` varchar(100) DEFAULT NULL COMMENT '论文栏目',
  `THESIS_GRADE` varchar(36) DEFAULT NULL COMMENT '刊物等级（国际，中华，中央，省级，内部，其他）',
  `THESIS_YEAR` varchar(36) DEFAULT NULL COMMENT '刊物年',
  `THESIS_STAGE` varchar(36) DEFAULT NULL COMMENT '刊物期',
  `THESIS_VOLUME` varchar(36) DEFAULT NULL COMMENT '刊物卷',
  `PUBLISH` varchar(50) DEFAULT NULL COMMENT '出版社',
  `POSITION` varchar(10) DEFAULT NULL COMMENT '担任职务（主编，副主编，编委）',
  `IS_EXTENSION` varchar(10) DEFAULT NULL COMMENT '是否统计源期刊（0是，1否）',
  `PUBLIS_TIME` datetime DEFAULT NULL COMMENT '出版时间',
  `MEET_NAME` varchar(100) DEFAULT NULL COMMENT '会议名称',
  `MEET_TIME` datetime DEFAULT NULL COMMENT '会议时间',
  `ANNEX` varchar(200) DEFAULT NULL COMMENT '附件',
  `AUDIT_STATE` varchar(5) DEFAULT NULL COMMENT '审核状态',
  `PROCEDURE_ID` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='论文';

CREATE TABLE `pms_skill_topic` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `TOPIC_CODE` varchar(20) DEFAULT NULL COMMENT '课题编码',
  `TOPIC_NAME` varchar(100) DEFAULT NULL COMMENT '课题名称',
  `DEPT_CODE` bigint(20) DEFAULT NULL COMMENT '科室编码',
  `TOPIC_TYPE` varchar(20) DEFAULT NULL COMMENT '课题类别',
  `TOPIC_YEAR` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '年度',
  `START_TIME` datetime DEFAULT NULL COMMENT '开始时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '结束时间',
  `FUND_TYPE` varchar(20) DEFAULT NULL COMMENT '经费投入类别',
  `FUN_MONEY` varchar(20) DEFAULT NULL COMMENT '经费投入金额',
  `PERSON_CODE` varchar(20) DEFAULT NULL COMMENT '负责人',
  `EDUCATION` varchar(20) DEFAULT NULL COMMENT '负责人学历',
  `SPECIAL` varchar(20) DEFAULT NULL COMMENT '负责人专业',
  `TITLE_CODE` varchar(36) DEFAULT NULL COMMENT '职称',
  `APPROVE` varchar(36) DEFAULT NULL COMMENT '批准单位（国家，军队，院内）',
  `GRADE` varchar(36) DEFAULT NULL COMMENT '等级（重点，杰出人才，青年学者）',
  `COOPERATE_UNIT` varchar(100) DEFAULT NULL COMMENT '协作单位',
  `ANNEX` varchar(200) DEFAULT NULL COMMENT '附件',
  `AUDIT_STATE` varchar(5) DEFAULT NULL COMMENT '审核状态',
  `PROCEDURE_ID` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='课题';

CREATE TABLE `pms_subsidy_adjust_benefit` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `DEPT_CODE` varchar(50) DEFAULT NULL COMMENT '科室编码',
  `ADJUST_TIME` datetime DEFAULT NULL COMMENT '调整时间',
  `ADJUST_TYPE` varchar(5) DEFAULT NULL COMMENT '调整类型(1成本2收入)',
  `ADJUST_AMOUNT` decimal(10,2) DEFAULT NULL COMMENT '调整金额',
  `ADJUST_REASON` varchar(512) DEFAULT NULL COMMENT '调整说明',
  `ADJUST_DIRECTION` varchar(5) DEFAULT NULL COMMENT '调整方向(1地方2军队)',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='科室效益调整';

CREATE TABLE `pms_subsidy_reward` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `REWARD_CODE` varchar(50) DEFAULT NULL COMMENT '项目编码',
  `REWARD_NAME` varchar(100) DEFAULT NULL COMMENT '项目名称',
  `REWARD_TYPE` varchar(50) DEFAULT NULL COMMENT '项目类型(1单项奖惩2其他奖励)',
  `REWARD_REASON` varchar(512) DEFAULT NULL COMMENT '奖惩标准/原因',
  `IS_ENABLE` varchar(1) DEFAULT NULL COMMENT '是否启用（0是，1否）',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='奖惩项目';

CREATE TABLE `pms_subsidy_reward_input` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `REWARD_CODE` varchar(50) DEFAULT NULL COMMENT '项目编码',
  `DEPT_CODE` varchar(100) DEFAULT NULL COMMENT '科室编码',
  `AMOUNT` decimal(10,2) DEFAULT NULL COMMENT '项目金额',
  `REWARD_TIME` datetime DEFAULT NULL COMMENT '奖励时间',
  `REWARD_TYPE` varchar(50) DEFAULT NULL COMMENT '项目类型(1单项奖惩2其他奖励)',
  `REWARD_REASON` varchar(512) DEFAULT NULL COMMENT '奖惩标准/原因',
  `IS_ENABLE` varchar(1) DEFAULT NULL COMMENT '是否启用（0是，1否）',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='奖惩录入';

CREATE TABLE `pms_update_date` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `REVENUE_CODE` varchar(50) DEFAULT NULL COMMENT '编码',
  `REVENUE_NAME` varchar(100) DEFAULT NULL COMMENT '调整类型',
  `START_TIME` datetime DEFAULT NULL COMMENT '开始时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '结束时间',
  `TARGET_MONTH` datetime DEFAULT NULL COMMENT '目标年月',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='更新日期';

CREATE TABLE `pms_workload` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMP_CODE` bigint(20) DEFAULT NULL COMMENT '单位编码',
  `ACCOUNT_CODE` varchar(50) DEFAULT NULL COMMENT '账套编码',
  `PROJECT_CODE` varchar(50) DEFAULT NULL COMMENT '项目编码',
  `PROJECT_NAME` varchar(50) DEFAULT NULL COMMENT '项目名称',
  `TYPE` varchar(50) DEFAULT NULL COMMENT '类别',
  `SPELL` varchar(50) DEFAULT NULL COMMENT '拼音码',
  `HOSPITAL_POINTS` decimal(20,2) DEFAULT NULL COMMENT '住院积分',
  `OUTPATIENT_POINTS` decimal(20,2) DEFAULT NULL COMMENT '门诊积分',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  `WORKLOAD_DATE` date DEFAULT NULL COMMENT '年月',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='工作量';

CREATE TABLE `pms_workload_his` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `HIS_NAME` varchar(50) DEFAULT NULL COMMENT '收费项目名称',
  `HIS_CODE` varchar(50) DEFAULT NULL COMMENT '收费项目编码',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='工作量-HIS收费项目';

CREATE TABLE `pms_workload_toll` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PROJECT_CODE` varchar(50) DEFAULT NULL COMMENT '项目编码',
  `HIS_NAME` varchar(50) DEFAULT NULL COMMENT '收费项目名称',
  `HIS_CODE` varchar(50) DEFAULT NULL COMMENT '收费项目编码',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 COMMENT='工作量-收费项设置';

CREATE TABLE `pms_workload_type` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PROJECT_CODE` varchar(50) DEFAULT NULL COMMENT '项目编码',
  `TYPE_NAME` varchar(50) DEFAULT NULL COMMENT '类别名称',
  `TYPE_CODE` varchar(50) DEFAULT NULL COMMENT '类别编码',
  `DEL_FLAG` varchar(1) DEFAULT '0' COMMENT '删除标记(0未删除,1已删除)',
  `CREATE_BY` varchar(36) DEFAULT NULL COMMENT '创建者',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(36) DEFAULT NULL COMMENT '修改者',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `REMARK` text COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='工作量-类别设置';

CREATE TABLE `qrtz_blob_triggers` (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `blob_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `qrtz_calendars` (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `calendar_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `calendar` blob NOT NULL,
  PRIMARY KEY (`sched_name`,`calendar_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `qrtz_cron_triggers` (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `cron_expression` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time_zone_id` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `qrtz_fired_triggers` (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `entry_id` varchar(95) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `instance_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `fired_time` bigint(13) NOT NULL,
  `sched_time` bigint(13) NOT NULL,
  `priority` int(11) NOT NULL,
  `state` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `job_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `requests_recovery` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`entry_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `qrtz_job_details` (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `job_class_name` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_durable` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_update_data` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `requests_recovery` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`job_name`,`job_group`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `qrtz_locks` (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `lock_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`sched_name`,`lock_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `qrtz_paused_trigger_grps` (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_group`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `qrtz_scheduler_state` (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `instance_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `last_checkin_time` bigint(13) NOT NULL,
  `checkin_interval` bigint(13) NOT NULL,
  PRIMARY KEY (`sched_name`,`instance_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `qrtz_simple_triggers` (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `repeat_count` bigint(7) NOT NULL,
  `repeat_interval` bigint(12) NOT NULL,
  `times_triggered` bigint(10) NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `qrtz_simprop_triggers` (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `str_prop_1` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `str_prop_2` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `str_prop_3` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `int_prop_1` int(11) DEFAULT NULL,
  `int_prop_2` int(11) DEFAULT NULL,
  `long_prop_1` bigint(20) DEFAULT NULL,
  `long_prop_2` bigint(20) DEFAULT NULL,
  `dec_prop_1` decimal(13,4) DEFAULT NULL,
  `dec_prop_2` decimal(13,4) DEFAULT NULL,
  `bool_prop_1` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `bool_prop_2` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `qrtz_triggers` (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `next_fire_time` bigint(13) DEFAULT NULL,
  `prev_fire_time` bigint(13) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `trigger_state` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_type` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `start_time` bigint(13) NOT NULL,
  `end_time` bigint(13) DEFAULT NULL,
  `calendar_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `misfire_instr` smallint(2) DEFAULT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  KEY `sched_name` (`sched_name`,`job_name`,`job_group`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `sys_advance_query_condition` (
  `id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `route_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '功能路由id',
  `query_name` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '查询名称',
  `condition_json` varchar(10000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '查询条件json'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `sys_advance_query_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '配置名称',
  `menu_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '功能id',
  `menu_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '功能名称',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0',
  `sql_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `menu_id` (`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `sys_advance_query_config_detail` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT,
  `config_id` bigint(22) DEFAULT NULL,
  `field_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字段英文名',
  `field_desc` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字段中文名',
  `field_op_tyoe` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字段操作类型',
  `field_label_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字段显示控件类型',
  `field_dict_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字段下拉显示字典值',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `sys_area` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `area_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '区域编码',
  `parent_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '父编码',
  `area_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '区域名称',
  `area_info` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '区域信息',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `del_flag` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标记（0：正常；1：删除；）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=60845 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='区域信息表';

CREATE TABLE `sys_client_info` (
  `client_id` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '接入方标识',
  `client_secret` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '接入方秘钥',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `sys_config` (
  `config_id` int(5) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `del_flag` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标记（0：正常；1：删除；）',
  `system_flag` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '系统标识（用于区别不同业务系统的数据）',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='参数配置表';

CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父部门id',
  `ancestors` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `pym` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拼音码',
  `province` bigint(20) DEFAULT NULL COMMENT '所在省',
  `city` bigint(20) DEFAULT NULL COMMENT '所在市',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '详细地址',
  `contact_person` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人',
  `tax_number` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '税务证号',
  `chief_accountant` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '主管会计',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `is_leaf` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否末级(0-否；1-是)',
  `dept_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位编码',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=123123123123220 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='部门表';

CREATE TABLE `sys_dept_room` (
  `dept_room_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '科室id',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父科室id',
  `dept_room_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '科室名称',
  `dept_room_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '科室编码',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '机构ID',
  `dept_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '机构名称',
  `type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '科室类型',
  `sort` bigint(20) DEFAULT NULL COMMENT '科室排序',
  `grade` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '科室级别',
  `duty_officer` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '负责人',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '联系电话',
  `dep_address` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '科室地址',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `version` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '版本',
  `pym` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '拼音码',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_leaf` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否末级（0-是；1-否）',
  `clinic_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '临床科室类别（1-门诊；2-急诊）',
  `is_accounting` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否核算（0-是；1-否）',
  `is_independent_accounting` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否独立核算（0-是；1-否）',
  `inline_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '内线电话',
  `is_service` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否服务（0-是；1-否）',
  `is_purchase` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否采购（0-是；1-否）',
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  `parent_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`dept_room_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1032 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='科室表';

CREATE TABLE `sys_dict_data` (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `del_flag` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标记（0：正常；1：删除；）',
  `parent_dic_code` bigint(20) DEFAULT NULL COMMENT '父级字典',
  `system_flag` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '系统标识（用于区别不同业务系统的数据）',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=70547 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='字典数据表';

CREATE TABLE `sys_dict_type` (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `del_flag` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标记（0：正常；1：删除；）',
  `system_flag` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '系统标识（用于区别不同业务系统的数据）',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE KEY `dict_type` (`dict_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=131087 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='字典类型表';

CREATE TABLE `sys_doctor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `doc_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '医生编码',
  `doc_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '医生姓名',
  `doc_level` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '医生级别',
  `org_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机构',
  `dep_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '科室',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `birthday` datetime DEFAULT NULL COMMENT '出生日期',
  `card_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '证件类型',
  `card_no` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '身份证号',
  `education` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '学历',
  `prof_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '职业类型',
  `prof_level` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '职业级别',
  `prof_num` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '职业证号',
  `prof_org` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '职业机构',
  `technical` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '职称',
  `tel` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '电话',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'email',
  `person_status` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '人员状态',
  `entry_time` datetime DEFAULT NULL COMMENT '入职时间',
  `recruitment_methods` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '招聘方式',
  `home_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '家庭地址',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `del_flag` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标记（0：正常；1：删除；）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='医生信息表';

CREATE TABLE `sys_flow_code` (
  `flow_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `rule_id` bigint(20) DEFAULT NULL COMMENT '编码规则id',
  `prefix` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '编码前缀',
  `flow_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '流水号名称',
  `flow_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '编码',
  `flow_cur_val` int(11) DEFAULT NULL COMMENT '当前值',
  `flow_len` int(11) DEFAULT NULL COMMENT '流水号长度',
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '启用标识(0-启用；1-禁用)',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `del_flag` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '删除标识(0-正常；1-删除)',
  PRIMARY KEY (`flow_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='编码流水号信息';

CREATE TABLE `sys_flowcode` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `cur_val` int(11) DEFAULT NULL,
  `code_len` int(11) DEFAULT NULL,
  `create_by` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `update_by` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` timestamp(6) NULL DEFAULT NULL,
  `update_time` timestamp(6) NULL DEFAULT NULL,
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `prefix` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2333 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `sys_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`,`job_name`,`job_group`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=601 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='定时任务调度表';

CREATE TABLE `sys_job_log` (
  `job_log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日志信息',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '异常信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19569 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='定时任务调度日志表';

CREATE TABLE `sys_logininfor` (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '操作系统',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7687 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统访问记录';

CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组件路径',
  `is_frame` int(1) DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `is_cache` int(1) DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '#' COMMENT '菜单图标',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注',
  `system_flag` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '系统标识（用于区别不同业务系统的数据）',
  `query` varchar(255) DEFAULT NULL COMMENT '路由参数',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3875 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='菜单权限表';

CREATE TABLE `sys_notice` (
  `notice_id` int(4) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告标题',
  `notice_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob COMMENT '公告内容',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='通知公告表';

CREATE TABLE `sys_oper_log` (
  `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '模块标题',
  `business_type` int(2) DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '请求方式',
  `operator_type` int(1) DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '返回参数',
  `status` int(1) DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25688 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='操作日志记录';

CREATE TABLE `sys_post` (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位名称',
  `post_sort` int(4) NOT NULL COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='岗位信息表';

CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) DEFAULT '1' COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) DEFAULT '1' COMMENT '部门树选择项是否关联显示',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色信息表';

CREATE TABLE `sys_role_dept` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  `del_flag` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标记（0：正常；1：删除；）',
  PRIMARY KEY (`role_id`,`dept_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色和部门关联表';

CREATE TABLE `sys_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0',
  PRIMARY KEY (`role_id`,`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色和菜单关联表';

CREATE TABLE `sys_ureport_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `file_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名称',
  `file_content` blob COMMENT '文件内容',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表正常 1代表删除）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `name` varchar(50) DEFAULT NULL COMMENT '防报错字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1544480607671189506 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='报表存储表';

CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `doc_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `dept_room_id` bigint(20) DEFAULT NULL COMMENT '科室id ',
  `dept_room_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '科室名称',
  `dept_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10084 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户信息表';

CREATE TABLE `sys_user_post` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL COMMENT '岗位ID',
  `del_flag` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标记（0：正常；1：删除；）',
  PRIMARY KEY (`user_id`,`post_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户与岗位关联表';

CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `use_flag` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `del_flag` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户和角色关联表';

CREATE TABLE `system_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '系统信息ID',
  `system_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '系统编码',
  `system_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '系统名称',
  `pym` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '拼音码',
  `short_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '系统简称',
  `system_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '系统路径',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '角色状态(0-启用；1-停用)',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标识(0-正常；1-删除)',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_current` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否当前系统（0-是；1-否）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统信息';

CREATE TABLE `user_account_relation` (
  `account_id` bigint(20) NOT NULL COMMENT '账套ID',
  `use_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '账套使用标识(0-未使用 1-正在使用)',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标识(0-正常；1-删除)',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='人员账套对应关系';

CREATE TABLE `user_deptroom_relation` (
  `deptroom_id` bigint(20) NOT NULL COMMENT '科室ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `from_org` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '是否组织所属（0-是；1-否）',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标识(0-正常；1-删除)',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户科室对应关系';

