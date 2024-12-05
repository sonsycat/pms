/**
 * 
 */
package com.zjry.formDesign.vo;

import java.util.List;

/**
 * @author Yangtao
 * 表单columns对象
 * 如果存在此节点，则说明是多列的表单
 */
public class Column {

	private String span;
	private List<FieldConfig> list;
	
	public String getSpan() {
		return span;
	}
	public void setSpan(String span) {
		this.span = span;
	}
	public List<FieldConfig> getList() {
		return list;
	}
	public void setList(List<FieldConfig> list) {
		this.list = list;
	}
	
}
