package com.zjry.advanceQuery.vo;

import java.util.List;

/**
 * 高级查询vo
 * @author yangtao
 * @date 2020-10-13
 */
public class AdvanceQueryVo {
	
//	private List<AdvanceQueryCondition> queryConditions; // 查询条件集合
	private String routeId; // 功能路由id
	private String joinWord; // 连接字符 and/or
	private List<AdvanceQueryConditionGroup> queryConditionGroups;
	
//	public List<AdvanceQueryCondition> getQueryConditions() {
//		return queryConditions;
//	}
//	public void setQueryConditions(List<AdvanceQueryCondition> queryConditions) {
//		this.queryConditions = queryConditions;
//	}
	public String getJoinWord() {
		return joinWord;
	}
	public void setJoinWord(String joinWord) {
		this.joinWord = joinWord;
	}
	public String getRouteId() {
		return routeId;
	}
	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
	public List<AdvanceQueryConditionGroup> getQueryConditionGroups() {
		return queryConditionGroups;
	}
	public void setQueryConditionGroups(List<AdvanceQueryConditionGroup> queryConditionGroups) {
		this.queryConditionGroups = queryConditionGroups;
	}
	
}
