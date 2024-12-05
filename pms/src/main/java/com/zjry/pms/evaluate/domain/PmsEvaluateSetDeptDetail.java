package com.zjry.pms.evaluate.domain;

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
 * 指标集与科室管理明细对象 pms_evaluate_set_dept_detail
 *
 * @author mk
 * @date 2022-08-09
 */
@Data
@ApiModel(value = "PmsEvaluateSetDeptDetail", description = "指标集与科室管理明细对象 pms_evaluate_set_dept_detail")
public class PmsEvaluateSetDeptDetail extends BaseEntity implements Serializable {
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

    /** 唯一编码 */
    @Excel(name = "唯一编码")
    @ApiModelProperty(value = "唯一编码")
    private String setDeptCode;

    /** 指标编码 */
    @Excel(name = "指标编码")
    @ApiModelProperty(value = "指标编码")
    private String indexCode;

    /** 降低量 */
    @Excel(name = "降低量")
    @ApiModelProperty(value = "降低量")
    private String reduce;

    /** 扣分 */
    @Excel(name = "扣分")
    @ApiModelProperty(value = "扣分")
    private String deduct;

    /** 增加量 */
    @Excel(name = "增加量")
    @ApiModelProperty(value = "增加量")
    private String increase;

    /** 加分 */
    @Excel(name = "加分")
    @ApiModelProperty(value = "加分")
    private String extra;

    /** 单位(%) */
    @Excel(name = "单位(%)")
    @ApiModelProperty(value = "单位(%)")
    private String unit;

    /** 目标值 */
    @Excel(name = "目标值")
    @ApiModelProperty(value = "目标值")
    private String target;

    /** 指标分数 */
    @Excel(name = "指标分数")
    @ApiModelProperty(value = "指标分数")
    private String indexScore;

    /** 是否允许超分 */
    @Excel(name = "是否允许超分")
    @ApiModelProperty(value = "是否允许超分")
    private String isHyper;

    /** 是否允许负分 */
    @Excel(name = "是否允许负分")
    @ApiModelProperty(value = "是否允许负分")
    private String isNegative;

    /** 是否固定分值 */
    @Excel(name = "是否固定分值")
    @ApiModelProperty(value = "是否固定分值")
    private String isFixed;

    /** 阀值 */
    @Excel(name = "阀值")
    @ApiModelProperty(value = "阀值")
    private String threshold;

    /** 超分限制 */
    @Excel(name = "超分限制")
    @ApiModelProperty(value = "超分限制")
    private String overLimit;

    /** 减分限制 */
    @Excel(name = "减分限制")
    @ApiModelProperty(value = "减分限制")
    private String deductionLimit;

    /** 年月 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "年月", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "年月")
    private Date nowDate;

    private String bscOneCode;

    private String deptName;

    private Long deptCode;

    private String setCode;

    private String indexName;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("compCode", getCompCode())
            .append("accountCode", getAccountCode())
            .append("setDeptCode", getSetDeptCode())
            .append("indexCode", getIndexCode())
            .append("reduce", getReduce())
            .append("deduct", getDeduct())
            .append("increase", getIncrease())
            .append("extra", getExtra())
            .append("unit", getUnit())
            .append("target", getTarget())
            .append("indexScore", getIndexScore())
            .append("isHyper", getIsHyper())
            .append("isNegative", getIsNegative())
            .append("isFixed", getIsFixed())
            .append("threshold", getThreshold())
            .append("overLimit", getOverLimit())
            .append("deductionLimit", getDeductionLimit())
            .append("nowDate", getNowDate())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
