package com.zjry.pms.evaluate.domain;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 指标集分类对象 pms_evaluate_set_type
 *
 * @author mk
 * @date 2022-08-03
 */
@Data
@ApiModel(value = "PmsEvaluateSetType", description = "指标集分类对象 pms_evaluate_set_type")
public class PmsEvaluateSetType extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /** 指标集类别编码 */
    @Excel(name = "指标集类别编码")
    @ApiModelProperty(value = "指标集类别编码")
    private String bscCode;

    /** 指标集类别名称 */
    @Excel(name = "指标集类别名称")
    @ApiModelProperty(value = "指标集类别名称")
    private String bscName;
    private String bName;

    /** 上级编码 */
    @Excel(name = "上级编码")
    @ApiModelProperty(value = "上级编码")
    private String parentCode;

    /** 祖级编码 */
    @Excel(name = "祖级编码")
    @ApiModelProperty(value = "祖级编码")
    private String ancestors;

    /** 是否停用（0是，1否） */
    @Excel(name = "是否停用", readConverterExp = "0=是，1否")
    @ApiModelProperty(value = "是否停用（0是，1否）")
    private String isStop;

    /** 是否末级（0是，1否） */
    @Excel(name = "是否末级", readConverterExp = "0=是，1否")
    @ApiModelProperty(value = "是否末级（0是，1否）")
    private String isLast;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("bscCode", getBscCode())
            .append("bscName", getBscName())
            .append("parentCode", getParentCode())
            .append("ancestors", getAncestors())
            .append("isStop", getIsStop())
            .append("isLast", getIsLast())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
