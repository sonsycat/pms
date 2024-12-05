package com.zjry.pms.basic.domain.basicData;

import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 编码规则对象 basicdata_code_rule
 *
 * @author cyq
 * @date 2021-07-14
 */
@Data
@ApiModel(value = "BasicdataCodeRule", description = "编码规则对象 basicdata_code_rule")
public class BasicdataCodeRule extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** id */
    @ApiModelProperty(value = "${comment}")
    private Long id;

    /** 规则名称 */
    @Excel(name = "规则名称")
    @ApiModelProperty(value = "规则名称")
    private String ruleName;

    /** 规则适用表 */
    @Excel(name = "规则适用表")
    @ApiModelProperty(value = "规则适用表")
    private String ruleTable;

    /** 规则对应字段 */
    @Excel(name = "规则对应字段")
    @ApiModelProperty(value = "规则对应字段")
    private String ruleColumns;

    /** 规则内容 */
    @Excel(name = "规则内容")
    @ApiModelProperty(value = "规则内容")
    private String ruleContent;

    /** 是否结构化 */
    @ApiModelProperty(value = "规则内容")
    private String isStructs;

    /** 编码前缀 */
    @ApiModelProperty(value = "规则内容")
    private String rulePrefix;

    /** 是否带日期 */
    @ApiModelProperty(value = "规则内容")
    private String isDate;

    /** 日期格式 */
    @ApiModelProperty(value = "规则内容")
    private String dateFormat;

    /** 流水号长度 */
    @ApiModelProperty(value = "规则内容")
    private Integer flowcodeLength;

    /** 系统标识（用于区别不同业务系统的数据） */
    @Excel(name = "系统标识")
    @ApiModelProperty(value = "系统标识")
    private String systemFlag;

    /** 删除标识(0-正常；1-删除) */
    @ApiModelProperty(value = "系统标识")
    private String delFlag;

    /** 启用标识(0-启用；1-禁用) */
    @ApiModelProperty(value = "系统标识")
    private String useFlag;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleName() {
        return ruleName;
    }
    public void setRuleTable(String ruleTable) {
        this.ruleTable = ruleTable;
    }

    public String getRuleTable() {
        return ruleTable;
    }
    public void setRuleColumns(String ruleColumns) {
        this.ruleColumns = ruleColumns;
    }

    public String getRuleColumns() {
        return ruleColumns;
    }
    public void setRuleContent(String ruleContent) {
        this.ruleContent = ruleContent;
    }

    public String getRuleContent() {
        return ruleContent;
    }
    public void setIsStructs(String isStructs) {
        this.isStructs = isStructs;
    }

    public String getIsStructs() {
        return isStructs;
    }
    public void setRulePrefix(String rulePrefix) {
        this.rulePrefix = rulePrefix;
    }

    public String getRulePrefix() {
        return rulePrefix;
    }
    public void setIsDate(String isDate) {
        this.isDate = isDate;
    }

    public String getIsDate() {
        return isDate;
    }
    public void setFlowcodeLength(Integer flowcodeLength) {
        this.flowcodeLength = flowcodeLength;
    }

    public Integer getFlowcodeLength() {
        return flowcodeLength;
    }
    public void setSystemFlag(String systemFlag) {
        this.systemFlag = systemFlag;
    }

    public String getSystemFlag() {
        return systemFlag;
    }
    @Override
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String getDelFlag() {
        return delFlag;
    }
    public void setUseFlag(String useFlag) {
        this.useFlag = useFlag;
    }

    public String getUseFlag() {
        return useFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("ruleName", getRuleName())
            .append("ruleTable", getRuleTable())
            .append("ruleColumns", getRuleColumns())
            .append("ruleContent", getRuleContent())
            .append("createBy", getCreateBy())
            .append("isStructs", getIsStructs())
            .append("createTime", getCreateTime())
            .append("rulePrefix", getRulePrefix())
            .append("updateBy", getUpdateBy())
            .append("isDate", getIsDate())
            .append("dateFormat", getDateFormat())
            .append("updateTime", getUpdateTime())
            .append("flowcodeLength", getFlowcodeLength())
            .append("remark", getRemark())
            .append("systemFlag", getSystemFlag())
            .append("delFlag", getDelFlag())
            .append("useFlag", getUseFlag())
            .toString();
	}
}
