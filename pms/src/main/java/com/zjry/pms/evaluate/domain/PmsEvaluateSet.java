package com.zjry.pms.evaluate.domain;

import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
/**
 * 指标集管理对象 pms_evaluate_set
 *
 * @author mk
 * @date 2022-08-04
 */
@Data
@ApiModel(value = "PmsEvaluateSet", description = "指标集管理对象 pms_evaluate_set")
public class PmsEvaluateSet extends BaseEntity implements Serializable {
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

    /** 指标集编码 */
    @Excel(name = "指标集编码")
    @ApiModelProperty(value = "指标集编码")
    private String setCode;

    /** 指标集名称 */
    @Excel(name = "指标集名称")
    @ApiModelProperty(value = "指标集名称")
    private String setName;
    private String sName;

    /** 一级分类 */
    @Excel(name = "一级分类")
    @ApiModelProperty(value = "一级分类")
    private String oneCode;

    /** 二级分类 */
    @Excel(name = "二级分类")
    @ApiModelProperty(value = "二级分类")
    private String secondCode;

    /** 部门分类 */
    @Excel(name = "部门分类")
    @ApiModelProperty(value = "部门分类")
    private String deptType;

    /** 组织类别(适用对象) */
    @Excel(name = "组织类别(适用对象)")
    @ApiModelProperty(value = "组织类别(适用对象)")
    private String orgType;

    /** 适用年份（下拉框） */
    @Excel(name = "适用年份", readConverterExp = "下=拉框")
    @ApiModelProperty(value = "适用年份（下拉框）")
    private String partYear;

    private String isBonus;



    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("compCode", getCompCode())
            .append("accountCode", getAccountCode())
            .append("setCode", getSetCode())
            .append("setName", getSetName())
            .append("oneCode", getOneCode())
            .append("secondCode", getSecondCode())
            .append("deptType", getDeptType())
            .append("orgType", getOrgType())
            .append("partYear", getPartYear())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
