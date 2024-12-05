package com.zjry.pms.revenue.domain;

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
 * 收入生成对象 pms_revenue_apport
 *
 * @author mk
 * @date 2022-06-28
 */
@Data
@ApiModel(value = "PmsRevenueApport", description = "收入生成对象 pms_revenue_apport")
public class PmsRevenueApport extends BaseEntity implements Serializable {
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
    private String revenueName;

    /** 应收分解金额 */
    @Excel(name = "应收分解金额")
    @ApiModelProperty(value = "应收分解金额")
    private BigDecimal receiveNum;

    /** 实际分解金额 */
    @Excel(name = "实际分解金额")
    @ApiModelProperty(value = "实际分解金额")
    private BigDecimal actualNum;

    /** 科室编码 */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private Long dpetCode;
    private String dpetName;

    /** 发生时间(YYYYMM) */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "发生时间(YYYYMM)", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "发生时间(YYYYMM)")
    private Date occureTime;

    /** 发生时间(YYYYMM) */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "发生时间(YYYYMM)", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "发生时间(YYYYMM)")
    private Date oTime;

    /** 收入类别(1门诊，2住院) */
    @Excel(name = "收入类别(1门诊，2住院)")
    @ApiModelProperty(value = "收入类别(1门诊，2住院)")
    private String revenueType;

    /** 收入类型（1正常收入，2附加收入） */
    @Excel(name = "收入类型", readConverterExp = "1=正常收入，2附加收入")
    @ApiModelProperty(value = "收入类型（1正常收入，2附加收入）")
    private String revenueKind;


    /** 结算标识(1,正常,2附加收入) */
    @Excel(name = "结算标识(1,日结,权责发生)")
    @ApiModelProperty(value = "结算标识(1,日结,权责发生)")
    private String settleType;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("compCode", getCompCode())
            .append("accountCode", getAccountCode())
            .append("revenueCode", getRevenueCode())
            .append("receiveNum", getReceiveNum())
            .append("actualNum", getActualNum())
            .append("dpetCode", getDpetCode())
            .append("occureTime", getOccureTime())
            .append("revenueType", getRevenueType())
            .append("revenueKind", getRevenueKind())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("settleType", getSettleType())
            .toString();
	}
}
