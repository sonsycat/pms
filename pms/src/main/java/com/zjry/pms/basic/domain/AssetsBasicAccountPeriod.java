package com.zjry.pms.basic.domain;

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
 * 年度帐期间管理对象 assets_basic_account_period
 *
 * @author maozt
 * @date 2021-08-06
 */
@Data
@ApiModel(value = "AssetsBasicAccountPeriod", description = "年度帐期间管理对象 assets_basic_account_period")
public class AssetsBasicAccountPeriod extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** id */
    @ApiModelProperty(value = "id")
    private Long id;

    /** 账套编码 */
    @Excel(name = "账套编码")
    @ApiModelProperty(value = "账套编码")
    private String accountCode;

    /** 年度 */
    @Excel(name = "年度", width = 30)
    @ApiModelProperty(value = "年度")
    private String accountYear;

    /** 开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始日期")
    private Date startDate;

    /** 结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "结束日期")
    private Date endDate;

    /** 期间 */
    @Excel(name = "期间")
    @ApiModelProperty(value = "期间")
    private String periodNum;
    
    /** 是否当前期间 */
    @Excel(name = "是否当前期间")
    @ApiModelProperty(value = "是否当前期间")
    private String isPeriod;

    /** 期间名称 */
    @Excel(name = "期间名称")
    @ApiModelProperty(value = "期间名称")
    private String periodName;

    /** 账套名称 */
    @Excel(name = "账套名称")
    @ApiModelProperty(value = "账套名称")
    private String accountName;

    /** 类型名称 */
    @ApiModelProperty(value = "类型名称 0=自然月 1=自定义")
    private String type;

    /** 当前期间 */
    @ApiModelProperty(value = "当前期间")
    private String nowPeriod;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("accountCode", getAccountCode())
            .append("accountYear", getAccountYear())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("periodNum", getPeriodNum())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
