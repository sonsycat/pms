package com.zjry.pms.revenue.domain;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 特殊收入项目方案临时对象 pms_revenue_special_temp
 *
 * @author maozt
 * @date 2022-06-21
 */
@Data
@ApiModel(value = "PmsRevenueSpecialTemp", description = "特殊收入项目方案临时对象 pms_revenue_special_temp")
public class PmsRevenueSpecialTemp extends BaseEntity implements Serializable {
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

    /** 特殊项目编码 */
    @Excel(name = "特殊项目编码")
    @ApiModelProperty(value = "特殊项目编码")
    private String specialCode;

    /** 项目编码 */
    @Excel(name = "项目编码")
    @ApiModelProperty(value = "项目编码")
    private String revenueCode;

    /** 项目名称 */
    @Excel(name = "项目名称")
    @ApiModelProperty(value = "项目名称")
    private String revenueName;

    /** 开单科室 */
    @Excel(name = "开单科室")
    @ApiModelProperty(value = "开单科室")
    private Long billDept;

    /** 执行科室 */
    @Excel(name = "执行科室")
    @ApiModelProperty(value = "执行科室")
    private Long execDept;

    /** 住院开单(%) */
    @Excel(name = "住院开单(%)")
    @ApiModelProperty(value = "住院开单(%)")
    private String inpBill;

    /** 住院执行(%) */
    @Excel(name = "住院执行(%)")
    @ApiModelProperty(value = "住院执行(%)")
    private String inpExec;

    /** 住院护理(%) */
    @Excel(name = "住院护理(%)")
    @ApiModelProperty(value = "住院护理(%)")
    private String inpCare;

    /** 门诊开单(%) */
    @Excel(name = "门诊开单(%)")
    @ApiModelProperty(value = "门诊开单(%)")
    private String outpBill;

    /** 门诊执行(%) */
    @Excel(name = "门诊执行(%)")
    @ApiModelProperty(value = "门诊执行(%)")
    private String outpExec;

    /** 门诊护理(%) */
    @Excel(name = "门诊护理(%)")
    @ApiModelProperty(value = "门诊护理(%)")
    private String outpCare;

    /** 合作医疗(%) */
    @Excel(name = "合作医疗(%)")
    @ApiModelProperty(value = "合作医疗(%)")
    private String coopMed;

    /** 核算类型(1参与核算，2不参与核算) */
    @Excel(name = "核算类型(1参与核算，2不参与核算)")
    @ApiModelProperty(value = "核算类型(1参与核算，2不参与核算)")
    private String accountType;

    /** 折算比(%) */
    @Excel(name = "折算比(%)")
    @ApiModelProperty(value = "折算比(%)")
    private String convRatio;

    /** 固定折算(%) */
    @Excel(name = "固定折算(%)")
    @ApiModelProperty(value = "固定折算(%)")
    private String fixConv;

    /** 成本对照(成本项目) */
    @Excel(name = "成本对照(成本项目)")
    @ApiModelProperty(value = "成本对照(成本项目)")
    private String projectCode;

    /** 收入类别(HIS) */
    @Excel(name = "收入类别(HIS)")
    @ApiModelProperty(value = "收入类别(HIS)")
    private String hisRevenumTypede;

    /** 临时表标识 */
    @Excel(name = "临时表标识")
    @ApiModelProperty(value = "临时表标识")
    private String type;
}
