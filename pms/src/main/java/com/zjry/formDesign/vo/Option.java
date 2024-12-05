/**
 * 
 */
package com.zjry.formDesign.vo;

import java.util.List;

/**
 * @author Yangtao
 * 表单字段属性option对象
 */
public class Option {

	private String width;
//	private String[] defaultValue;
	private String required;
	private String type;
	private String pattern;
	private String placeholder;
	private String disabled;
	private String remoteFunc;
	private List<OptionsObj> options;
	
	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

//	public String[] getDefaultValue() {
//		return defaultValue;
//	}
//
//	public void setDefaultValue(String[] defaultValue) {
//		this.defaultValue = defaultValue;
//	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getRemoteFunc() {
		return remoteFunc;
	}

	public void setRemoteFunc(String remoteFunc) {
		this.remoteFunc = remoteFunc;
	}

	public List<OptionsObj> getOptions() {
		return options;
	}

	public void setOptions(List<OptionsObj> options) {
		this.options = options;
	}
	
}
