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
import java.util.List;


@Data
@ApiModel(value = "PmsSubsidyFundBalance", description = "科基金 pms_subsidy_fund_balance")
public class PmsSubsidyFundBalance extends BaseEntity implements Serializable {
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

    @Excel(name = "科基金余额")
    @ApiModelProperty(value = "科基金余额")
    private BigDecimal fundBalance;

}
