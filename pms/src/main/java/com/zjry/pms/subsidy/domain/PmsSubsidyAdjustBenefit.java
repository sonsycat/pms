package com.zjry.pms.subsidy.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * 科室效益调整对象 pms_subsidy_adjust_benefit
 *
 * @author maozt
 * @date 2022-08-17
 */
@Data
@ApiModel(value = "PmsSubsidyAdjustBenefit", description = "科室效益调整对象 pms_subsidy_adjust_benefit")
public class PmsSubsidyAdjustBenefit extends BaseEntity implements Serializable {
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

    /** 调整时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "调整时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "调整时间")
    private Date adjustTime;

    /** 调整时间 */
    @JsonFormat(pattern = "yyyy-MM")
    @ApiModelProperty(value = "调整时间")
    private Date adjustMonth;

    /** 调整类型(1成本2收入) */
    @Excel(name = "调整类型(1成本2收入)")
    @ApiModelProperty(value = "调整类型(1成本2收入)")
    private String adjustType;

    /** 调整金额 */
    @Excel(name = "调整金额")
    @ApiModelProperty(value = "调整金额")
    private BigDecimal adjustAmount;

    /** 调整金额 */
    @Excel(name = "调整金额")
    @ApiModelProperty(value = "调整金额")
    private BigDecimal adjustAmountMax;

    /** 调整金额 */
    @Excel(name = "调整金额")
    @ApiModelProperty(value = "调整金额")
    private BigDecimal adjustAmountMin;

    /** 调整说明 */
    @Excel(name = "调整说明")
    @ApiModelProperty(value = "调整说明")
    private String adjustReason;

    /** 调整方向(1地方2军队) */
    @Excel(name = "调整方向(1地方2军队)")
    @ApiModelProperty(value = "调整方向(1地方2军队)")
    private String adjustDirection;

    /** 调整人 */
    @Excel(name = "调整人")
    @ApiModelProperty(value = "调整人")
    private String adjustName;
}
