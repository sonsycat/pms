/**
 * Copyright &copy; 2008-2019 <a href="http://www.sxzjrj.com">ZhiJie</a> All rights reserved.
 */
package com.zjry.formDesign.vo;

import java.util.List;

/**
 * 表单设计元素Entity
 * @author Yangtao
 * @version 2021-04-27
 */
public class FormJson {

	private List<FieldConfig> list;
	private FormConfig config;

	public FormConfig getConfig() {
		return config;
	}

	public void setConfig(FormConfig config) {
		this.config = config;
	}

	public List<FieldConfig> getList() {
		return list;
	}

	public void setList(List<FieldConfig> list) {
		this.list = list;
	}
	
}