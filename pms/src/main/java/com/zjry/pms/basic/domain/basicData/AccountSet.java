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
 * 账套信息对象 account_set
 *
 * @author 王超
 * @date 2021-07-15
 */
@Data
@ApiModel(value = "AccountSetCopy1", description = "账套信息对象 account_set")
public class AccountSet extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 账套id */
    @ApiModelProperty(value = "${comment}")
    private Long id;

    /** 账套编码 */
    @Excel(name = "账套编码")
    @ApiModelProperty(value = "账套编码")
    private String code;

    /** 账套名称 */
    @Excel(name = "账套名称")
    @ApiModelProperty(value = "账套名称")
    private String name;

    /** 单位id */
    @Excel(name = "单位id")
    @ApiModelProperty(value = "单位id")
    private Long deptId;

    /** 单位名称 */
    @Excel(name = "单位名称")
    @ApiModelProperty(value = "单位名称")
    private String deptName;

    /** 账套开始年度 */
    @Excel(name = "账套开始年度")
    @ApiModelProperty(value = "账套开始年度")
    private String startYear;

    /** 账套开始月份 */
    @Excel(name = "账套开始月份")
    @ApiModelProperty(value = "账套开始月份")
    private String startMonth;

    /** 结束日期 */
    @Excel(name = "结束日期")
    @ApiModelProperty(value = "结束日期")
    private String endDate;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    @ApiModelProperty(value = "状态")
    private String status;

    /** 删除标识(0-正常；1-删除) */
    @ApiModelProperty(value = "状态")
    private String delFlag;

    /** 使用标识*/
    private String useFlag;
    /** 单位编码 */
    private String deptCode;
    /** 备注 */
    private String remark;

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
    public String getDeptCode() {
        return deptCode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptName() {
        return deptName;
    }
    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }
    public String getStartYear() {
        return startYear;
    }
    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }
    public String getStartMonth() {
        return startMonth;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }
    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("code", getCode())
            .append("name", getName())
            .append("deptId", getDeptId())
            .append("deptName", getDeptName())
            .append("startYear", getStartYear())
            .append("startMonth", getStartMonth())
            .append("endDate", getEndDate())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .toString();
	}
}
