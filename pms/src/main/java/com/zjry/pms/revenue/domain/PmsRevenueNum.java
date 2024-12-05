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
 * 收入录入对象 pms_revenue_num
 *
 * @author mk
 * @date 2022-06-22
 */
@Data
@ApiModel(value = "PmsRevenueNum", description = "收入录入对象 pms_revenue_num")
public class PmsRevenueNum extends BaseEntity implements Serializable {
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

    /** 应收金额 */
    @Excel(name = "应收金额")
    @ApiModelProperty(value = "应收金额")
    private BigDecimal receiveNum;


    /** 应收金额 */
    @ApiModelProperty(value = "应收开始")
    private String receiveNumB;


    /** 应收金额 */
    @ApiModelProperty(value = "应收结束")
    private String receiveNumE;

    /** 实际金额 */
    @Excel(name = "实际金额")
    @ApiModelProperty(value = "实际金额")
    private BigDecimal actualNum;

    /** 实际金额 */
    @ApiModelProperty(value = "实际金额开始")
    private String actualNumB;

    /** 实际金额 */
    @ApiModelProperty(value = "实际金额结束")
    private String actualNumE;

    /** 开单科室 */
    @Excel(name = "开单科室")
    @ApiModelProperty(value = "开单科室")
    private Long billDept;

    private String billDeptName;

    /** 执行科室 */
    @Excel(name = "执行科室")
    @ApiModelProperty(value = "执行科室")
    private Long execDept;

    private String execDeptName;
    /** 护理单元 */
    @Excel(name = "护理单元")
    @ApiModelProperty(value = "护理单元")
    private Long careDept;

    private String careDeptName;
    /** 开单医生 */
    @Excel(name = "开单医生")
    @ApiModelProperty(value = "开单医生")
    private String billDoc;

    /** 发生时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发生时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "发生时间")
    private Date occureTime;

    /** 收入类别(1门诊，2住院) */
    @Excel(name = "收入类别(1门诊，2住院)")
    @ApiModelProperty(value = "收入类别(1门诊，2住院)")
    private String revenueType;

    /** 结算标识(1收付实现，2责权发生) */
    @Excel(name = "结算标识(1收付实现，2责权发生)")
    @ApiModelProperty(value = "结算标识(1收付实现，2责权发生)")
    private String settleMark;

    /** 收入类型（1正常收入，2附加收入） */
    @Excel(name = "收入类型", readConverterExp = "1=正常收入，2附加收入")
    @ApiModelProperty(value = "收入类型（1正常收入，2附加收入）")
    private String revenueKind;

    /** 发生时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "发生时间")
    private Date occureT;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("compCode", getCompCode())
            .append("accountCode", getAccountCode())
            .append("revenueCode", getRevenueCode())
            .append("receiveNum", getReceiveNum())
            .append("actualNum", getActualNum())
            .append("billDept", getBillDept())
            .append("execDept", getExecDept())
            .append("careDept", getCareDept())
            .append("billDoc", getBillDoc())
            .append("occureTime", getOccureTime())
            .append("revenueType", getRevenueType())
            .append("settleMark", getSettleMark())
            .append("revenueKind", getRevenueKind())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}


}
