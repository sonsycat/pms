package com.zjry.pms.subsidy.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Data
@ApiModel(value = "PmsSubsidyFund", description = "科基金 pms_subsidy_fund")
public class PmsSubsidyFund extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /** 账套编码 */
    @Excel(name = "科室名称")
    @ApiModelProperty(value = "科室名称")
    private String deptName;

    /** 科室编码 */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private String deptCode;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "基金时间")
    private Date fundTime;

    @Excel(name = "科基金金额")
    @ApiModelProperty(value = "科基金金额")
    private BigDecimal fundMoney;

    @Excel(name = "科基金余额")
    @ApiModelProperty(value = "科基金余额")
    private BigDecimal fundBalance;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String remark;
    private String fundState;
    private String submitDate;

}
