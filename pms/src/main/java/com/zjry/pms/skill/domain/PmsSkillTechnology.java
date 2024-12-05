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
 * 新业务新技术对象 pms_skill_technology
 *
 * @author maozt
 * @date 2022-08-02
 */
@Data
@ApiModel(value = "PmsSkillTechnology", description = "新业务新技术对象 pms_skill_technology")
public class PmsSkillTechnology extends BaseEntity implements Serializable {
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

    /** 编码 */
    @Excel(name = "编码")
    @ApiModelProperty(value = "编码")
    private String technoCode;

    /** 名称 */
    @Excel(name = "名称")
    @ApiModelProperty(value = "名称")
    private String technoName;

    /** 出版时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开展时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "开展时间")
    private Date developTime;

    /** 水平（国内领先，军内领先，省内空白，医院空白） */
    @Excel(name = "水平", readConverterExp = "国=内领先，军内领先，省内空白，医院空白")
    @ApiModelProperty(value = "水平（国内领先，军内领先，省内空白，医院空白）")
    private String technoType;

    /** 科室编码 */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private Long deptCode;

    /** 科室编码 */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private String deptName;

    /** 完成例数 */
    @Excel(name = "完成例数")
    @ApiModelProperty(value = "完成例数")
    private String completeNum;

    /** 负责人 */
    @Excel(name = "负责人")
    @ApiModelProperty(value = "负责人")
    private String personCode;

    /** 负责人昵称 */
    @Excel(name = "负责人昵称")
    @ApiModelProperty(value = "负责人昵称")
    private String personName;

    /** 负责人学历 */
    @Excel(name = "负责人学历")
    @ApiModelProperty(value = "负责人学历")
    private String education;

    /** 负责人专业 */
    @Excel(name = "负责人专业")
    @ApiModelProperty(value = "负责人专业")
    private String special;

    /** 职称 */
    @Excel(name = "职称")
    @ApiModelProperty(value = "职称")
    private String titleCode;

    /** 效果（很好，好，一般，不明显，待观察） */
    @Excel(name = "效果", readConverterExp = "很=好，好，一般，不明显，待观察")
    @ApiModelProperty(value = "效果（很好，好，一般，不明显，待观察）")
    private String effect;

    /** 新业务新技术（引进，自主创新，消化吸收，其他） */
    @Excel(name = "新业务新技术", readConverterExp = "引=进，自主创新，消化吸收，其他")
    @ApiModelProperty(value = "新业务新技术（引进，自主创新，消化吸收，其他）")
    private String techno;

    /** 进展情况 */
    @Excel(name = "进展情况")
    @ApiModelProperty(value = "进展情况")
    private String progress;

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
