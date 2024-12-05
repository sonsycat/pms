package com.zjry.advanceQuery.vo;

import java.util.List;

/**
 * 高级查询条件组
 * @author yangtao
 * @date 2020-11-01
 */
public class AdvanceQueryConditionGroup {
	
	private List<AdvanceQueryCondition> queryConditions; // 查询条件集合
	private String joinWord; // 连接字符 and/or
	
	public List<AdvanceQueryCondition> getQueryConditions() {
		return queryConditions;
	}
	public void setQueryConditions(List<AdvanceQueryCondition> queryConditions) {
		this.queryConditions = queryConditions;
	}
	public String getJoinWord() {
		return joinWord;
	}
	public void setJoinWord(String joinWord) {
		this.joinWord = joinWord;
	}
	
}
