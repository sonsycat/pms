package com.zjry.formDesign.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 自定义表单对象 form_design
 *
 * @author yangtao
 * @date 2021-07-27
 */
@Data
@ApiModel(value = "FormDesign", description = "自定义表单对象 form_design")
public class FormDesign extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "${comment}")
    private String id;
    private String fName;

    /** 表单名称 */
    @Excel(name = "表单名称")
    @ApiModelProperty(value = "表单名称")
    private String formName;

    /** $column.columnComment */
    @Excel(name = "表单名称")
    @ApiModelProperty(value = "表单名称")
    private String formJson;
    
    /** $column.columnComment */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "表单名称", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "表单名称")
    private Date createDate;

    /** $column.columnComment */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "表单名称", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "表单名称")
    private Date updateDate;

    /** 备注 */
    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String remarks;

    /** $column.columnComment */
    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String delFlag;

    /** 表单编码 */
    @Excel(name = "表单编码")
    @ApiModelProperty(value = "表单编码")
    private String formCode;

    /** $column.columnComment */
    @Excel(name = "表单编码")
    @ApiModelProperty(value = "表单编码")
    private String genTableName;

    /** $column.columnComment */
    @ApiModelProperty(value = "表单编码")
    private String formUrl;

    private String formHtml;
    private String formData;
    private String formType;
    @ApiModelProperty(value = "单位编码")
    private Long compCode;
    @ApiModelProperty(value = "排序")
    private String sortNo;
    @ApiModelProperty(value = "是否汇总")
    private String isSum;
    @ApiModelProperty(value = "每页条数")
    private String pageNum;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "是否科室跟踪")
    private String isTrack;
     /**选择的模板Code*/
    private String originalCode;

    private List<FormDesignField> fdcList;




    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("formName", getFormName())
            .append("formJson", getFormJson())
            .append("createBy", getCreateBy())
            .append("createDate", getCreateDate())
            .append("updateBy", getUpdateBy())
            .append("updateDate", getUpdateDate())
            .append("remarks", getRemarks())
            .append("delFlag", getDelFlag())
            .append("formCode", getFormCode())
            .append("genTableName", getGenTableName())
            .append("formUrl", getFormUrl())
            .toString();
	}
}
