package com.zjry.pms.commission.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 独立核算提成比对象 pms_separate_account_commission
 *
 * @author zjry
 * @date 2022-09-27
 */
@Data
@ApiModel(value = "SeparateAccountCommission", description = "独立核算提成比对象 pms_separate_account_commission")
@TableName("pms_separate_account_commission")
public class SeparateAccountCommission extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /** 科室编码 */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private String deptCode;

    /** 单位编码 */
    @Excel(name = "单位编码")
    @ApiModelProperty(value = "单位编码")
    private Long compCode;

    /** 科室名称 */
    @Excel(name = "科室名称")
    @ApiModelProperty(value = "科室名称")
    private String deptName;

    /** 账套编码 */
    @Excel(name = "账套编码")
    @ApiModelProperty(value = "账套编码")
    private String accountCode;

    /** 类别 */
    @Excel(name = "类别")
    @ApiModelProperty(value = "类别")
    private String sortCode;

    /** 比例 */
    @Excel(name = "比例")
    @ApiModelProperty(value = "比例")
    private BigDecimal ratio;

    /** 删除标记(0未删除,1已删除) */
    @Excel(name = "删除标记(0未删除,1已删除)")
    @ApiModelProperty(value = "删除标记(0未删除,1已删除)")
    @TableLogic
    private String delFlag;

    /** 核算日期,年月 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "核算日期,年月", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "核算日期,年月")
    private Date commissionDate;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptCode() {
        return deptCode;
    }
    public void setCompCode(Long compCode) {
        this.compCode = compCode;
    }

    public Long getCompCode() {
        return compCode;
    }
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptName() {
        return deptName;
    }
    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountCode() {
        return accountCode;
    }
    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getSortCode() {
        return sortCode;
    }
    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    public BigDecimal getRatio() {
        return ratio;
    }
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }
    public void setCommissionDate(Date commissionDate) {
        this.commissionDate = commissionDate;
    }

    public Date getCommissionDate() {
        return commissionDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("deptCode", getDeptCode())
            .append("compCode", getCompCode())
            .append("deptName", getDeptName())
            .append("accountCode", getAccountCode())
            .append("sortCode", getSortCode())
            .append("ratio", getRatio())
            .append("delFlag", getDelFlag())
            .append("commissionDate", getCommissionDate())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
