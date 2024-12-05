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
 * 流水对象 sys_flow_code
 *
 * @author cyq
 * @date 2021-07-14
 */
@Data
@ApiModel(value = "SysFlowCode", description = "流水对象 sys_flow_code")
public class BasicdataFlowCode extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @Excel(name = "ID")
    @ApiModelProperty(value = "ID")
    private Long flowId;

    /** 编码规则id */
    @Excel(name = "编码规则id")
    @ApiModelProperty(value = "编码规则id")
    private Long ruleId;

    /** 流水号名称 */
    @Excel(name = "流水号名称")
    @ApiModelProperty(value = "流水号名称")
    private String flowName;

    /** 启用标识(0-启用；1-禁用) */
    @Excel(name = "启用标识(0-启用；1-禁用)")
    @ApiModelProperty(value = "启用标识(0-启用；1-禁用)")
    private String status;

    /** 编码 */
    @Excel(name = "编码")
    @ApiModelProperty(value = "编码")
    private String flowCode;

    /** 当前值 */
    @Excel(name = "当前值")
    @ApiModelProperty(value = "当前值")
    private Long flowCurVal;

    /** 流水号长度 */
    @Excel(name = "流水号长度")
    @ApiModelProperty(value = "流水号长度")
    private Long flowLen;

    /** 编码前缀 */
    @Excel(name = "编码前缀")
    @ApiModelProperty(value = "编码前缀")
    private String prefix;

    /** 删除标识(0-正常；1-删除) */
    @ApiModelProperty(value = "编码前缀")
    private String delFlag;

    /** 日期格式 */
    @ApiModelProperty(value = "规则内容")
    private String dateFormat;

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }

    public Long getFlowId() {
        return flowId;
    }
    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Long getRuleId() {
        return ruleId;
    }
    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getFlowName() {
        return flowName;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    public void setFlowCode(String flowCode) {
        this.flowCode = flowCode;
    }

    public String getFlowCode() {
        return flowCode;
    }
    public void setFlowCurVal(Long flowCurVal) {
        this.flowCurVal = flowCurVal;
    }

    public Long getFlowCurVal() {
        return flowCurVal;
    }
    public void setFlowLen(Long flowLen) {
        this.flowLen = flowLen;
    }

    public Long getFlowLen() {
        return flowLen;
    }
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
    @Override
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("flowId", getFlowId())
            .append("ruleId", getRuleId())
            .append("flowName", getFlowName())
            .append("status", getStatus())
            .append("flowCode", getFlowCode())
            .append("flowCurVal", getFlowCurVal())
            .append("flowLen", getFlowLen())
            .append("prefix", getPrefix())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .toString();
	}
}
