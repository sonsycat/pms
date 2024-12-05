package com.zjry.advanceQuery.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.zjry.advanceQuery.constant.ConstantsAdvanceQuery;
import com.zjry.advanceQuery.vo.AdvanceQueryCondition;
import com.zjry.advanceQuery.vo.AdvanceQueryConditionGroup;
import com.zjry.advanceQuery.vo.AdvanceQueryVo;

/**
 * 高级查询-工具类
 * 
 * @author yangtao
 */
public class AdvanceQueryUtil {
	
	/**
	 * 获取高级查询sql
	 * @param originalSql 业务原始查询sql
	 * @param advanceQueryVo 高级查询条件
	 * @return
	 */
	public static String getAdvanceQuerySql(String originalSql, AdvanceQueryVo advanceQueryVo) {
		StringBuilder sql = new StringBuilder();
		sql.append(getSelect(originalSql));
//		sql.append(getWhere(advanceQueryVo));
		sql.append(getWhereGroup(advanceQueryVo));
		System.out.println("sql---" + sql);
		return sql.toString();
	}
	
	public static String getSelect(String originalSql) {
		StringBuilder select = new StringBuilder();
		select.append("select "+ConstantsAdvanceQuery.QUERY_ALIAS+".* from ");
		select.append("(");
		select.append(originalSql);
		select.append(")");
		select.append(" "+ConstantsAdvanceQuery.QUERY_ALIAS+" ");
		
		return select.toString();
	}
	
	public static String getWhereGroup(AdvanceQueryVo advanceQueryVo) {
		StringBuilder whereGroup = new StringBuilder();
		List<AdvanceQueryConditionGroup> queryConditionGroups = advanceQueryVo.getQueryConditionGroups();
		if(queryConditionGroups != null && queryConditionGroups.size() > 0) {
			whereGroup.append(" where ");
			for(int i = 0;i < queryConditionGroups.size();i++) {
				AdvanceQueryConditionGroup queryGroup = queryConditionGroups.get(i);
				String params = getWhere(queryGroup);
				if(StringUtils.isNotEmpty(params)) {
					whereGroup.append("(");
					whereGroup.append(params);
					whereGroup.append(")");
					if(i != queryConditionGroups.size() - 1) {
						whereGroup.append(ConstantsAdvanceQuery.SPACE + advanceQueryVo.getJoinWord() + ConstantsAdvanceQuery.SPACE);
					}
				}
			}
		}
		return whereGroup.toString();
	}
	
//	public static String getWhere(AdvanceQueryVo advanceQueryVo) {
//		StringBuilder where = new StringBuilder();
//		List<AdvanceQueryCondition> queryConditions = advanceQueryVo.getQueryConditions();
//		String andor = advanceQueryVo.getJoinWord();
//		if(queryConditions != null && queryConditions.size() > 0) {
//			StringBuilder param = new StringBuilder();
//			int paramNum = queryConditions.size();
//			for(int i = 0;i < paramNum;i++) {
//				AdvanceQueryCondition queryCondition = queryConditions.get(i);
//				if(StringUtils.isEmpty(queryCondition.getValue())) {
//					continue;
//				}
//				String key = ConstantsAdvanceQuery.QUERY_ALIAS + "." + queryCondition.getKey();
//				String op = queryCondition.getOp();
//				String value = "'" + queryCondition.getValue() + "'";
//				switch (op) {
//				case ConstantsAdvanceQuery.EQ:
//					if(i == paramNum - 1) {
//						param.append(key + " = " + value);
//					} else {
//						param.append(key + " = " + value + ConstantsAdvanceQuery.SPACE + andor + ConstantsAdvanceQuery.SPACE);
//					}
//					break;
//				case ConstantsAdvanceQuery.NE:
//					if(i == paramNum - 1) {
//						param.append(key + " != " + value);
//					} else {
//						param.append(key + " != " + value + ConstantsAdvanceQuery.SPACE + andor + ConstantsAdvanceQuery.SPACE);
//					}
//					break;
//				case ConstantsAdvanceQuery.GT:
//					if(i == paramNum - 1) {
//						param.append(key + " > " + value);
//					} else {
//						param.append(key + " > " + value + ConstantsAdvanceQuery.SPACE + andor + ConstantsAdvanceQuery.SPACE);
//					}
//					break;
//				case ConstantsAdvanceQuery.GE:
//					if(i == paramNum - 1) {
//						param.append(key + " >= " + value);
//					} else {
//						param.append(key + " >= " + value + ConstantsAdvanceQuery.SPACE + andor + ConstantsAdvanceQuery.SPACE);
//					}
//					break;
//				case ConstantsAdvanceQuery.LT:
//					if(i == paramNum - 1) {
//						param.append(key + " < " + value);
//					} else {
//						param.append(key + " < " + value + ConstantsAdvanceQuery.SPACE + andor + ConstantsAdvanceQuery.SPACE);
//					}
//					break;
//				case ConstantsAdvanceQuery.LE:
//					if(i == paramNum - 1) {
//						param.append(key + " <= " + value);
//					} else {
//						param.append(key + " <= " + value + ConstantsAdvanceQuery.SPACE + andor + ConstantsAdvanceQuery.SPACE);
//					}
//					break;
//				case ConstantsAdvanceQuery.LIKE:
//					if(i == paramNum - 1) {
//						param.append(key + " like concat('%',  " + value + ", '%') ");
//					} else {
//						param.append(key + " like concat('%',  " + value + ", '%') " + andor + ConstantsAdvanceQuery.SPACE);
//					}
//					break;
//				case ConstantsAdvanceQuery.BETWEEN:
//					if(queryCondition.getValue().indexOf("`") != -1) {
//						String[] values = queryCondition.getValue().split("`");
//						if(i == paramNum - 1) {
//							param.append(key + " between " + values[0] + " and " + values[1]);
//						} else {
//							param.append(key + " between " + values[0] + " and " + values[1] + ConstantsAdvanceQuery.SPACE + andor + ConstantsAdvanceQuery.SPACE);
//						}
//					}
//					break;
//				default:
//					break;
//				}
//			}
////			if(StringUtils.isNotEmpty(param.toString())) {
////				where.append(" where ".concat(param.toString()));
////			}
//		}
//		return where.toString();
//	}
	
	public static String getWhere(AdvanceQueryConditionGroup queryGroup) {
//		StringBuilder where = new StringBuilder();
		StringBuilder param = new StringBuilder();
		List<AdvanceQueryCondition> queryConditions = queryGroup.getQueryConditions();
		String andor = queryGroup.getJoinWord();
		if(queryConditions != null && queryConditions.size() > 0) {
			int paramNum = queryConditions.size();
			for(int i = 0;i < paramNum;i++) {
				AdvanceQueryCondition queryCondition = queryConditions.get(i);
				if(StringUtils.isEmpty(queryCondition.getValue())) {
					continue;
				}
				String key = ConstantsAdvanceQuery.QUERY_ALIAS + "." + queryCondition.getKey();
				String op = queryCondition.getOp();
				String value = "'" + queryCondition.getValue() + "'";
				switch (op) {
				case ConstantsAdvanceQuery.EQ:
					if(i == paramNum - 1) {
						param.append(key + " = " + value);
					} else {
						param.append(key + " = " + value + ConstantsAdvanceQuery.SPACE + andor + ConstantsAdvanceQuery.SPACE);
					}
					break;
				case ConstantsAdvanceQuery.NE:
					if(i == paramNum - 1) {
						param.append(key + " != " + value);
					} else {
						param.append(key + " != " + value + ConstantsAdvanceQuery.SPACE + andor + ConstantsAdvanceQuery.SPACE);
					}
					break;
				case ConstantsAdvanceQuery.GT:
					if(i == paramNum - 1) {
						param.append(key + " > " + value);
					} else {
						param.append(key + " > " + value + ConstantsAdvanceQuery.SPACE + andor + ConstantsAdvanceQuery.SPACE);
					}
					break;
				case ConstantsAdvanceQuery.GE:
					if(i == paramNum - 1) {
						param.append(key + " >= " + value);
					} else {
						param.append(key + " >= " + value + ConstantsAdvanceQuery.SPACE + andor + ConstantsAdvanceQuery.SPACE);
					}
					break;
				case ConstantsAdvanceQuery.LT:
					if(i == paramNum - 1) {
						param.append(key + " < " + value);
					} else {
						param.append(key + " < " + value + ConstantsAdvanceQuery.SPACE + andor + ConstantsAdvanceQuery.SPACE);
					}
					break;
				case ConstantsAdvanceQuery.LE:
					if(i == paramNum - 1) {
						param.append(key + " <= " + value);
					} else {
						param.append(key + " <= " + value + ConstantsAdvanceQuery.SPACE + andor + ConstantsAdvanceQuery.SPACE);
					}
					break;
				case ConstantsAdvanceQuery.LIKE:
					if(i == paramNum - 1) {
						param.append(key + " like concat('%',  " + value + ", '%') ");
					} else {
						param.append(key + " like concat('%',  " + value + ", '%') " + andor + ConstantsAdvanceQuery.SPACE);
					}
					break;
				case ConstantsAdvanceQuery.BETWEEN:
					if(queryCondition.getValue().indexOf("`") != -1) {
						String[] values = queryCondition.getValue().split("`");
						if(i == paramNum - 1) {
							param.append(key + " between " + values[0] + " and " + values[1]);
						} else {
							param.append(key + " between " + values[0] + " and " + values[1] + ConstantsAdvanceQuery.SPACE + andor + ConstantsAdvanceQuery.SPACE);
						}
					}
					break;
				default:
					break;
				}
			}
//			if(StringUtils.isNotEmpty(param.toString())) {
//				where.append(" where ".concat(param.toString()));
//			}
		}
		return param.toString();
	}
	
}
