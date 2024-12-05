package com.zjry.system.domain;


import com.baomidou.mybatisplus.annotation.TableLogic;
import com.zjry.common.core.domain.BaseMybatisPlusEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 流水号对象 sys_flow_code
 *
 * @author zjry
 * @date 2021-06-22
 */
@Data
@ApiModel(value = "SysFlowCode", description = "流水号对象 sys_flow_code")
public class SysFlowCode extends BaseMybatisPlusEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "flowId")
    private Long flowId;

    /**
     * 名称
     */
    @ApiModelProperty(value = "flowName")
    private String flowName;

    /**
     * 编码
     */
    @ApiModelProperty(value = "flowCode")
    private String flowCode;

    /**
     * 当前值
     */
    @ApiModelProperty(value = "flowCurVal")
    private Long flowCurVal;

    /**
     * 流水码长度
     */
    @ApiModelProperty(value = "flowLen")
    private Long flowLen;

    /**
     * 状态
     */
    @ApiModelProperty(value = "status")
    private String status;

    /**
     * 删除标志
     */
    @TableLogic
    @ApiModelProperty(value = "delFlag")
    private String delFlag;

    /**
     * 前缀
     */
    @ApiModelProperty(value = "prefix")
    private String prefix;

    public Long getFlowId() {
        return flowId;
    }

    public String getFlowName() {
        return flowName;
    }

    public String getFlowCode() {
        return flowCode;
    }

    public Long getFlowCurVal() {
        return flowCurVal;
    }

    public Long getFlowLen() {
        return flowLen;
    }

    public String getStatus() {
        return status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public void setFlowCode(String flowCode) {
        this.flowCode = flowCode;
    }

    public void setFlowCurVal(Long flowCurVal) {
        this.flowCurVal = flowCurVal;
    }

    public void setFlowLen(Long flowLen) {
        this.flowLen = flowLen;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public SysFlowCode(String flowCode, Long flowCurVal, Long flowLen, String delFlag, String prefix) {
        this.flowCode = flowCode;
        this.flowCurVal = flowCurVal;
        this.flowLen = flowLen;
        this.delFlag = delFlag;
        this.prefix = prefix;
    }
}
