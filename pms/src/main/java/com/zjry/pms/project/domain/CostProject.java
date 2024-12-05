package com.zjry.pms.project.domain;

import java.io.Serializable;
import java.util.List;

import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 成本项目管理对象 cost_project
 *
 * @author mk
 * @date 2022-05-06
 */
@Data
@ApiModel(value = "CostProject", description = "成本项目管理对象 cost_project")
public class CostProject extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /** 单位编码 */
    @Excel(name = "单位编码")
    @ApiModelProperty(value = "单位编码")
    private Long compCode;

    /** 账套编码
     @Excel(name = "账套编码") */
    @ApiModelProperty(value = "账套编码")
    private String accountCode;

    /** 项目编码 */
    @Excel(name = "项目编码")
    @ApiModelProperty(value = "项目编码")
    private String projectCode;

    /** 项目名称 */
    @Excel(name = "项目名称")
    @ApiModelProperty(value = "项目名称")
    private String projectName;

    /** 上级项目 */
    @Excel(name = "上级项目")
    @ApiModelProperty(value = "上级项目")
    private String parentProject;

    /** 项目级别 */
    @Excel(name = "项目级别")
    @ApiModelProperty(value = "项目级别")
    private String projectLevel;

    /** 项目类型 */
    @Excel(name = "项目类型")
    @ApiModelProperty(value = "项目类型")
    private String projectType;

    /** 成本属性 */
    @Excel(name = "成本属性")
    @ApiModelProperty(value = "成本属性")
    private String costProperty;
    /** 是否末级*/
    @Excel(name = "是否末级")
    @ApiModelProperty(value = "是否末级")
    private String isLast;

    /** 是否可控 */
    @Excel(name = "是否可控")
    @ApiModelProperty(value = "是否可控")
    private String isControl;

    /** 是否核算 */
    @Excel(name = "是否核算")
    @ApiModelProperty(value = "是否核算")
    private String isAccount;

    /** 是否资本性成本 */
    @Excel(name = "是否资本性成本")
    @ApiModelProperty(value = "是否资本性成本")
    private String isCostCapital;

    /** 是否医疗成本 */
    @Excel(name = "是否医疗成本")
    @ApiModelProperty(value = "是否医疗成本")
    private String isCostHealth;

    /** 是否计算计入成本 */
    @Excel(name = "是否计算计入成本")
    @ApiModelProperty(value = "是否计算计入成本")
    private String isCostCalculate;

    /** 计算计入成本分摊方案 */
    @Excel(name = "计算计入成本分摊方案")
    @ApiModelProperty(value = "计算计入成本分摊方案")
    private String costScheme;

    private String projectFt;



    @Excel(name = "错误原因")
    @ApiModelProperty(value = "错误原因")
    private String reason;

    /** 科室集合 */
    @ApiModelProperty(value = "科室集合")
    private List<String> deptList;


    /** 科室编码 */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private Long deptCode;
    private String pName;


    /** 科室名称 */
    @ApiModelProperty(value = "科室名称")
    private String deptName;

    /** 科室类型 */
    @ApiModelProperty(value = "科室类型")
    private String type;

}
