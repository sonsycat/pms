package com.zjry.pms.skill.domain;

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
 * 成果对象 pms_skill_achieve
 *
 * @author maozt
 * @date 2022-08-01
 */
@Data
@ApiModel(value = "PmsSkillAchieve", description = "成果对象 pms_skill_achieve")
public class PmsSkillAchieve extends BaseEntity implements Serializable {
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

    /** 成果编码 */
    @Excel(name = "成果编码")
    @ApiModelProperty(value = "成果编码")
    private String achieveCode;

    /** 成果名称 */
    @Excel(name = "成果名称")
    @ApiModelProperty(value = "成果名称")
    private String achieveName;

    /** 科室编码 */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private Long deptCode;

    /** 科室编码 */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private String deptName;

    /** 协作单位 */
    @Excel(name = "协作单位")
    @ApiModelProperty(value = "协作单位")
    private String completeUnit;

    /** 完成人 */
    @Excel(name = "完成人")
    @ApiModelProperty(value = "完成人")
    private String personCode;

    /** 完成人学历 */
    @Excel(name = "完成人学历")
    @ApiModelProperty(value = "完成人学历")
    private String education;

    /** 完成人专业 */
    @Excel(name = "完成人专业")
    @ApiModelProperty(value = "完成人专业")
    private String special;

    /** 职称 */
    @Excel(name = "职称")
    @ApiModelProperty(value = "职称")
    private String titleCode;

    /** 获奖类别 */
    @Excel(name = "获奖类别")
    @ApiModelProperty(value = "获奖类别")
    private String awardCategory;

    /** 获奖级别 */
    @Excel(name = "获奖级别")
    @ApiModelProperty(value = "获奖级别")
    private String awardLevel;

    /** 是否推广（0是，1否） */
    @Excel(name = "是否推广", readConverterExp = "0=是，1否")
    @ApiModelProperty(value = "是否推广（0是，1否）")
    private String isExtension;

    /** 获奖时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "获奖时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "获奖时间")
    private Date awardTime;

    /** 获奖收益 */
    @Excel(name = "获奖收益")
    @ApiModelProperty(value = "获奖收益")
    private String extensionIncome;

    /** 批准单位（国家，军队，院内） */
    @Excel(name = "批准单位", readConverterExp = "国=家，军队，院内")
    @ApiModelProperty(value = "批准单位（国家，军队，院内）")
    private String approve;

    /** 任务来源（国家，全军，军区，单位，自选，其他） */
    @Excel(name = "任务来源", readConverterExp = "国=家，全军，军区，单位，自选，其他")
    @ApiModelProperty(value = "任务来源（国家，全军，军区，单位，自选，其他）")
    private String taskSource;

    /** 推广范围（军用，民用，军民通用） */
    @Excel(name = "推广范围", readConverterExp = "军=用，民用，军民通用")
    @ApiModelProperty(value = "推广范围（军用，民用，军民通用）")
    private String extensionScope;

    /** 成果性质（基础研究，应用研究，试验研究，涉及试制，软科学） */
    @Excel(name = "成果性质", readConverterExp = "基=础研究，应用研究，试验研究，涉及试制，软科学")
    @ApiModelProperty(value = "成果性质（基础研究，应用研究，试验研究，涉及试制，软科学）")
    private String achieveNature;

    /** 药批号 */
    @Excel(name = "药批号")
    @ApiModelProperty(value = "药批号")
    private String drugApproval;

    /** 专利收益 */
    @Excel(name = "专利收益")
    @ApiModelProperty(value = "专利收益")
    private String patentIncome;

    /** 专利号 */
    @Excel(name = "专利号")
    @ApiModelProperty(value = "专利号")
    private String patentNo;

    /** 专利类别（发明专利，实用新型专利，外观设计专利） */
    @Excel(name = "专利类别", readConverterExp = "发=明专利，实用新型专利，外观设计专利")
    @ApiModelProperty(value = "专利类别（发明专利，实用新型专利，外观设计专利）")
    private String patentType;

    /** 主题词 */
    @Excel(name = "主题词")
    @ApiModelProperty(value = "主题词")
    private String subjectWords;

    /** 成果摘要 */
    @Excel(name = "成果摘要")
    @ApiModelProperty(value = "成果摘要")
    private String achieveAbstract;

    /** 附件 */
    @Excel(name = "附件")
    @ApiModelProperty(value = "附件")
    private String annex;

    /** 审核状态 */
    @Excel(name = "审核状态")
    @ApiModelProperty(value = "审核状态")
    private String auditState;

    /** 流程ID */
    @Excel(name = "流程ID")
    @ApiModelProperty(value = "流程ID")
    private String procedureId;

    /** 审核意见 */
    @ApiModelProperty(value = "审核意见")
    private String comment;

    /** 下一节点审核人 */
    @ApiModelProperty(value = "下一节点审核人")
    private String assignee;
}
