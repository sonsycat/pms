package com.zjry.pms.project.domain;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 成本项目管理_科室对象 cost_project_dept
 *
 * @author mk
 * @date 2022-05-11
 */
@Data
@ApiModel(value = "CostProjectDept", description = "成本项目管理_科室对象 cost_project_dept")
public class CostProjectDept extends BaseEntity implements Serializable {
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
    private String projectCode;

    /** 科室编码 */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private Long deptCode;


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

    /** 计算计入成本分摊方案
     @Excel(name = "计算计入成本分摊方案") */
    @ApiModelProperty(value = "计算计入成本分摊方案")
    private String costScheme;

    private String queryString;



    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("compCode", getCompCode())
            .append("accountCode", getAccountCode())
            .append("projectCode", getProjectCode())
            .append("deptCode", getDeptCode())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
