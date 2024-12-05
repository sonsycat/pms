package com.zjry.pms.revenue.domain;

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
 * 特殊收入项目科室设置对象 pms_revenue_special_dept
 *
 * @author maozt
 * @date 2022-06-23
 */
@Data
@ApiModel(value = "PmsRevenueSpecialDept", description = "特殊收入项目科室设置对象 pms_revenue_special_dept")
public class PmsRevenueSpecialDept extends BaseEntity implements Serializable {
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
    private String specialCode;

    /** 科室编码 */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private Long deptCode;

    /** 科室编码 */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private String deptName;

    /** 比例这只 */
    @Excel(name = "比例这只")
    @ApiModelProperty(value = "比例这只")
    private String ratio;

    private List<PmsRevenueSpecialDept> deptList;

    /** 1已选科室 2待选科室 */
    @ApiModelProperty(value = "1已选科室 2待选科室")
    private String isProject;

    /** 查询参数 */
    @ApiModelProperty(value = "查询参数")
    private String queryCode;

    /** 科室类型 */
    @ApiModelProperty(value = "科室类型")
    private String type;

    /** 科室类型 */
    @ApiModelProperty(value = "科室类型 门诊住院")
    private String clinicType;
}
