package com.zjry.formDesign.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.formDesign.vo.FormJson;

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
    
    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormName() {
        return formName;
    }
    public void setFormJson(String formJson) {
        this.formJson = formJson;
    }

    public String getFormJson() {
        return formJson;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return createDate;
    }
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }
    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFormCode() {
        return formCode;
    }
    public void setGenTableName(String genTableName) {
        this.genTableName = genTableName;
    }

    public String getGenTableName() {
        return genTableName;
    }
    public void setFormUrl(String formUrl) {
        this.formUrl = formUrl;
    }

    public String getFormUrl() {
        return formUrl;
    }
    
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
