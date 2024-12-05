package com.zjry.formDesign.domain;

import java.util.List;

import com.zjry.common.core.domain.BaseEntity;

/**
 * 表单设计配置
 * @author Yangtao
 */
public class FormDesignField extends BaseEntity {

	private static final long serialVersionUID = -6417747740175225402L;
	
	private String id;
	private String formDesignId;
	private String fieldName; //
	private String fieldDesc;
	private String isQuery;
	private String isList;
	private String isEdit;
	private String queryType;
	private Integer sortNo;
	private String isNotNull;
	private Integer fieldLength;
	private String validType;
	private String fieldType;
	private String dictType;
	
	private String dbType;
	private String nullVal;
	
	private Object fieldValue;
	private String options; // 单选、多选的选项
	
	private List<FormDesignField> fdcList;

	public List<FormDesignField> getFdcList() {
		return fdcList;
	}
	public void setFdcList(List<FormDesignField> fdcList) {
		this.fdcList = fdcList;
	}
	public String getFormDesignId() {
		return formDesignId;
	}
	public void setFormDesignId(String formDesignId) {
		this.formDesignId = formDesignId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getIsQuery() {
		return isQuery;
	}
	public void setIsQuery(String isQuery) {
		this.isQuery = isQuery;
	}
	public String getIsList() {
		return isList;
	}
	public void setIsList(String isList) {
		this.isList = isList;
	}
	public String getIsEdit() {
		return isEdit;
	}
	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public Integer getSortNo() {
		return sortNo;
	}
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}
	public String getFieldDesc() {
		return fieldDesc;
	}
	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}
	public String getIsNotNull() {
		return isNotNull;
	}
	public void setIsNotNull(String isNotNull) {
		this.isNotNull = isNotNull;
	}
	public Integer getFieldLength() {
		return fieldLength;
	}
	public void setFieldLength(Integer fieldLength) {
		this.fieldLength = fieldLength;
	}
	public String getValidType() {
		return validType;
	}
	public void setValidType(String validType) {
		this.validType = validType;
	}
	public String getDbType() {
		return dbType;
	}
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	public String getNullVal() {
		return nullVal;
	}
	public void setNullVal(String nullVal) {
		this.nullVal = nullVal;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Object getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getDictType() {
		return dictType;
	}
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	
}