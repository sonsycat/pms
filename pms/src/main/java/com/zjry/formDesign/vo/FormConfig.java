/**
 * 
 */
package com.zjry.formDesign.vo;

/**
 * @author Yangtao
 * 表单config对象
 */
public class FormConfig {

	private Integer labelWidth;
	private String labelPosition;
	private String size;
	
	public Integer getLabelWidth() {
		return labelWidth;
	}
	public void setLabelWidth(Integer labelWidth) {
		this.labelWidth = labelWidth;
	}
	public String getLabelPosition() {
		return labelPosition;
	}
	public void setLabelPosition(String labelPosition) {
		this.labelPosition = labelPosition;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	@Override
	public String toString() {
		return "labelWidth：" + labelWidth + ",labelPosition:" + labelPosition + ",size:" + size; 
	}
}
