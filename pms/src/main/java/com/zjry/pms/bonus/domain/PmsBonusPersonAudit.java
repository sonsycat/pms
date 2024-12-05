package com.zjry.pms.bonus.domain;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 奖金人员审核对象 pms_bonus_person_audit
 *
 * @author zjry
 * @date 2022-10-25
 */
@Data
@ApiModel(value = "PmsBonusPersonAudit", description = "奖金人员审核对象 pms_bonus_person_audit")
public class PmsBonusPersonAudit extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /** 单位编码 */
    @Excel(name = "单位编码")
    @ApiModelProperty(value = "单位编码")
    private Long compCode;

    /** 账套编码 */
    @Excel(name = "账套编码")
    @ApiModelProperty(value = "账套编码")
    private String accountCode;

    /** 科室编码 */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private String deptCode;

    /** 年月 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "年月", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "年月")
    private Date nowDate;

    /** 审核状态 */
    @Excel(name = "审核状态")
    @ApiModelProperty(value = "审核状态")
    private String auditState;

    /** 流程id */
    @Excel(name = "流程id")
    @ApiModelProperty(value = "流程id")
    private String procedureId;

    /** 删除标记(0未删除,1已删除) */
    @Excel(name = "删除标记(0未删除,1已删除)")
    @ApiModelProperty(value = "删除标记(0未删除,1已删除)")
    private String delFlag;

    private String comment;

    private String assignee;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setCompCode(Long compCode) {
        this.compCode = compCode;
    }

    public Long getCompCode() {
        return compCode;
    }
    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountCode() {
        return accountCode;
    }
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptCode() {
        return deptCode;
    }
    public void setNowDate(Date nowDate) {
        this.nowDate = nowDate;
    }

    public Date getNowDate() {
        return nowDate;
    }
    public void setAuditState(String auditState) {
        this.auditState = auditState;
    }

    public String getAuditState() {
        return auditState;
    }
    public void setProcedureId(String procedureId) {
        this.procedureId = procedureId;
    }

    public String getProcedureId() {
        return procedureId;
    }
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("compCode", getCompCode())
            .append("accountCode", getAccountCode())
            .append("deptCode", getDeptCode())
            .append("nowDate", getNowDate())
            .append("auditState", getAuditState())
            .append("procedureId", getProcedureId())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
