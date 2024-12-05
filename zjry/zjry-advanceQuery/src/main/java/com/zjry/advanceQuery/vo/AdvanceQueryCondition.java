package com.zjry.advanceQuery.vo;

/**
 * 高级查询条件
 * @author yangtao
 * @date 2020-10-13
 */
public class AdvanceQueryCondition {
	
	private String key; // 查询参数
	private String op; // 查询运算符：eq like gt lt...
	private String value; // 查询值
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
