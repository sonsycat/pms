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
 * 收入核算方案对象 pms_revenue
 *
 * @author mk
 * @date 2022-06-20
 */
@Data
@ApiModel(value = "PmsRevenue", description = "收入核算方案对象 pms_revenue")
public class PmsRevenue extends BaseEntity implements Serializable {
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
    private String revenueCode;

    /** 项目名称 */
    @Excel(name = "项目名称")
    @ApiModelProperty(value = "项目名称")
    private String revenueName;

    /** 项目名称 */
    private String rName;

    /** 拼音码 */
    @Excel(name = "拼音码")
    @ApiModelProperty(value = "拼音码")
    private String spell;

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

    /** 核算类型 */
    @Excel(name = "核算类型")
    @ApiModelProperty(value = "核算类型")
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

    private String hisRevenumTypedeName;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("compCode", getCompCode())
            .append("accountCode", getAccountCode())
            .append("revenueCode", getRevenueCode())
            .append("revenueName", getRevenueName())
            .append("spell", getSpell())
            .append("inpBill", getInpBill())
            .append("inpExec", getInpExec())
            .append("inpCare", getInpCare())
            .append("outpBill", getOutpBill())
            .append("outpExec", getOutpExec())
            .append("outpCare", getOutpCare())
            .append("coopMed", getCoopMed())
            .append("accountType", getAccountType())
            .append("convRatio", getConvRatio())
            .append("fixConv", getFixConv())
            .append("projectCode", getProjectCode())
            .append("hisRevenumTypede", getHisRevenumTypede())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
