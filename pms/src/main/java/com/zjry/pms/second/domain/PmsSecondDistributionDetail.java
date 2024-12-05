package com.zjry.pms.second.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 二次分配指标集明细对象 pms_second_distribution_detail
 *
 * @author maozt
 * @date 2022-09-26
 */
@Data
@ApiModel(value = "PmsSecondDistributionDetail", description = "二次分配指标集明细对象 pms_second_distribution_detail")
public class PmsSecondDistributionDetail extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /** 单位编码 */
    @Excel(name = "单位编码")
    @ApiModelProperty(value = "单位编码")
    private Long compCode;

    /** 账套编码 */
    @ApiModelProperty(value = "账套编码")
    private String accountCode;

    /** 指标集编码 */
    @Excel(name = "指标集编码")
    @ApiModelProperty(value = "指标集编码")
    private String disCode;

    /** 指标编码 */
    @Excel(name = "指标编码")
    @ApiModelProperty(value = "指标编码")
    private String indexCode;

    /** 指标名称 */
    @Excel(name = "指标名称")
    @ApiModelProperty(value = "指标名称")
    private String indexName;

    /** 权重 */
    @Excel(name = "权重")
    @ApiModelProperty(value = "权重")
    private String weights;

    /** 1已选科室 2待选科室 */
    @ApiModelProperty(value = "1已选科室 2待选科室")
    private String isProject;

    /** 搜索字段 */
    @ApiModelProperty(value = "搜索字段")
    private String queryCode;
    /** 权重比值 */
    private BigDecimal weightRatio;
}
