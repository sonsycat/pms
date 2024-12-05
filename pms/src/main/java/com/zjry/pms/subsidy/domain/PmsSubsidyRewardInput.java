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
 * 单项奖惩录入对象 pms_subsidy_reward_input
 *
 * @author maozt
 * @date 2022-08-16
 */
@Data
@ApiModel(value = "PmsSubsidyRewardInput", description = "单项奖惩录入对象 pms_subsidy_reward_input")
public class PmsSubsidyRewardInput extends BaseEntity implements Serializable {
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

    /** 项目编码 */
    @Excel(name = "项目编码")
    @ApiModelProperty(value = "项目编码")
    private String rewardCode;

    /** 科室编码 */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private String deptCode;

    /** 项目金额 */
    @Excel(name = "项目金额")
    @ApiModelProperty(value = "项目金额")
    private BigDecimal amount;

    /** 项目金额 */
    @Excel(name = "项目金额")
    @ApiModelProperty(value = "项目金额")
    private BigDecimal amountMax;

    /** 项目金额 */
    @Excel(name = "项目金额")
    @ApiModelProperty(value = "项目金额")
    private BigDecimal amountMin;

    /** 奖励时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "奖励时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "奖励时间")
    private Date rewardTime;

    /** 奖励年月 */
    @JsonFormat(pattern = "yyyy-MM")
    @ApiModelProperty(value = "奖励年月")
    private Date rewardMonth;

    /** 项目类型(1单项奖惩2其他奖励) */
    @Excel(name = "项目类型(1单项奖惩2其他奖励)")
    @ApiModelProperty(value = "项目类型(1单项奖惩2其他奖励)")
    private String rewardType;

    /** 奖惩标准/原因 */
    @Excel(name = "奖惩标准/原因")
    @ApiModelProperty(value = "奖惩标准/原因")
    private String rewardReason;

    /** 是否启用（0是，1否） */
    @Excel(name = "是否启用", readConverterExp = "0=是，1否")
    @ApiModelProperty(value = "是否启用（0是，1否）")
    private String isEnable;
}
