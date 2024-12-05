package com.zjry.pms.basic.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 账套管理对象 assets_basic_account
 *
 * @author maozt
 * @date 2021-08-05
 */
@Data
@ApiModel(value = "AssetsBasicAccount", description = "账套管理对象 assets_basic_account")
@TableName(value = "account_set")
public class AssetsBasicAccount extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 账套id */
    @ApiModelProperty(value = "${comment}")
    private Long id;

    /** 账套编码 */
    @Excel(name = "账套编码")
    @ApiModelProperty(value = "账套编码")
    private String accountCode;

    /** 账套名称 */
    @Excel(name = "账套名称")
    @ApiModelProperty(value = "账套名称")
    @TableField("name")
    @NotBlank(message = "账套名称不能为空")
    private String accountName;

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
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "结束日期")
    private Date endDate;

    /** 状态（0正常 1停用） */
    @ApiModelProperty(value = "结束日期")
    private String status;

    /** 是否关联用户 */
    @Excel(name = "是否关联用户")
    @ApiModelProperty(value = "是否关联用户")
    private String isUser;
    /** 用户编码 */
    @Excel(name = "用户编码")
    @ApiModelProperty(value = "用户编码")
    private Long userId;
    /** 查询条件 */
    @ApiModelProperty(value = "查询条件")
    private String queryCode;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("accountCode", getAccountCode())
            .append("accountName", getAccountName())
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
