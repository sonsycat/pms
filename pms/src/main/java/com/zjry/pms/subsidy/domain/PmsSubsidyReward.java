package com.zjry.pms.subsidy.domain;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 奖惩项目维护对象 pms_subsidy_reward
 *
 * @author maozt
 * @date 2022-08-15
 */
@Data
@ApiModel(value = "PmsSubsidyReward", description = "奖惩项目维护对象 pms_subsidy_reward")
public class PmsSubsidyReward extends BaseEntity implements Serializable {
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

    /** 项目名称 */
    @Excel(name = "项目名称")
    @ApiModelProperty(value = "项目名称")
    private String rewardName;

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
