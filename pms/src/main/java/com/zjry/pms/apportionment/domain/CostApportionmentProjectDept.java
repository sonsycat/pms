package com.zjry.pms.apportionment.domain;

import java.io.Serializable;
import java.util.List;

import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 分摊方案管理科室设置对象 cost_apportionment_project_dept
 *
 * @author mk
 * @date 2022-05-13
 */
@Data
@ApiModel(value = "CostApportionmentProjectDept", description = "分摊方案管理科室设置对象 cost_apportionment_project_dept")
public class CostApportionmentProjectDept extends BaseEntity implements Serializable {
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

    /** 分摊方案编码 */
    @Excel(name = "分摊方案编码")
    @ApiModelProperty(value = "分摊方案编码")
    private String projectCode;

    /** 科室ID */
    @Excel(name = "科室ID")
    @ApiModelProperty(value = "科室ID")
    private Long deptId;

    /** 科室ID */
    @Excel(name = "科室ID")
    @ApiModelProperty(value = "科室ID")
    private Long deptCode;

    /** 科室名称 */
    @ApiModelProperty(value = "科室名称")
    private String deptName;

    /** 计算计入设置比例 */
    @Excel(name = "计算计入设置比例")
    @ApiModelProperty(value = "计算计入设置比例")
    private String ratio;

    /** 一级设置比例 */
    @Excel(name = "一级设置比例")
    @ApiModelProperty(value = "一级设置比例")
    private String firstRatio;

    /** 二级设置比例 */
    @Excel(name = "二级设置比例")
    @ApiModelProperty(value = "二级设置比例")
    private String secondRatio;

    /** 三级设置比例 */
    @Excel(name = "三级设置比例")
    @ApiModelProperty(value = "三级设置比例")
    private String thirdRatio;

    /** 1已选科室 2待选科室 */
    @ApiModelProperty(value = "1已选科室 2待选科室")
    private String isProject;

    /** 查询参数 */
    @ApiModelProperty(value = "查询参数")
    private String queryCode;

    /** 科室类型 */
    @ApiModelProperty(value = "科室类型")
    private String type;

    private List<CostApportionmentProjectDept> deptList;

}
