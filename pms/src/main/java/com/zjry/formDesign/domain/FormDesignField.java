package com.zjry.formDesign.domain;

import java.util.List;

import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 表单设计配置
 * @author Yangtao
 */
@Data
public class FormDesignField extends BaseEntity {

	private static final long serialVersionUID = -6417747740175225402L;

	@ApiModelProperty(value = "主键")
	private String id;
	@ApiModelProperty(value = "主表标识")
	private String formDesignId;
	@ApiModelProperty(value = "表字段名")
	private String fieldName; //
	@ApiModelProperty(value = "字段描述")
	private String fieldDesc;
	@ApiModelProperty(value = "是否必填")
	private String isNotNull;
	@ApiModelProperty(value = "字段长度")
	private Integer fieldLength;
	@ApiModelProperty(value = "是否在列表显示")
	private String isList;
	@ApiModelProperty(value = "是否在编辑页面显示")
	private String isEdit;
	@ApiModelProperty(value = "是否查询列")
	private String isQuery;
	@ApiModelProperty(value = "查询条件（1大于2等于3小于4包含")
	private String queryType;
	@ApiModelProperty(value = "显示排序")
	private Integer sortNo;
	@ApiModelProperty(value = "VALID_TYPE")
	private String validType;
	@ApiModelProperty(value = "数据库类型")
	private String dbType;
	@ApiModelProperty(value = "字段类型")
	private String fieldType;
	@ApiModelProperty(value = "调取的字典")
	private String dictType;

	@ApiModelProperty(value = "radio的数据  单选、多选的选项")
	private String options;
	@ApiModelProperty(value = "宽度")
	private Integer span;



//===============================================
	@ApiModelProperty(value = "查询默认值")
	private String queryDefault;
	@ApiModelProperty(value = "绩效字段类型")
	private String pmsType;
	@ApiModelProperty(value = "文本行数")
	private String textLines;
	@ApiModelProperty(value = "最大值")
	private String maxValue;
	@ApiModelProperty(value = "最小值")
	private String minValue;
	@ApiModelProperty(value = "小数位数")
	private String decimlDigit;
	@ApiModelProperty(value = "是否百分比")
	private String isPercente;
	@ApiModelProperty(value = "默认值")
	private String defaultValue;
	@ApiModelProperty(value = "说明")
	private String explain;
	@ApiModelProperty(value = "考核指标")
	private String indexCode;
	@ApiModelProperty(value = "是否排序")
	private String isSort;
	@ApiModelProperty(value = "排序类型1正序2倒序")
	private String soortType;
	@ApiModelProperty(value = "是否汇总")
	private String isSum;
	@ApiModelProperty(value = "汇总方式")
	private String sumType;
	@ApiModelProperty(value = "日期默认")
	private String  dateDefaultValue;
	@ApiModelProperty(value = "小页面查询标志")
	private String isListQuery;
	@ApiModelProperty(value = "每页条数")
	private String pageNum;

	@ApiModelProperty(value = "显示类型")
	private String displayType;





	private String nullVal;
	private Object fieldValue;
	private List<FormDesignField> fdcList;


}