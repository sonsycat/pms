package com.zjry.pms.second.domain;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 二次分配指标集设置对象 pms_second_distribution
 *
 * @author maozt
 * @date 2022-09-26
 */
@Data
@ApiModel(value = "PmsSecondDistribution", description = "二次分配指标集设置对象 pms_second_distribution")
public class PmsSecondDistribution extends BaseEntity implements Serializable {
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
    private String disCode;

    /** 指标集名称 */
    @Excel(name = "指标集名称")
    @ApiModelProperty(value = "指标集名称")
    private String disName;

    @ApiModelProperty(value = "指标集合")
    private List<PmsSecondDistributionDetail> pmsSecondDistributionDetails;

}
