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
 * 课题对象 pms_skill_topic
 *
 * @author maozt
 * @date 2022-07-26
 */
@Data
@ApiModel(value = "PmsSkillTopic", description = "课题对象 pms_skill_topic")
public class PmsSkillTopic extends BaseEntity implements Serializable {
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

    /** 课题编码 */
    @Excel(name = "课题编码")
    @ApiModelProperty(value = "课题编码")
    private String topicCode;

    /** 课题名称 */
    @Excel(name = "课题名称")
    @ApiModelProperty(value = "课题名称")
    private String topicName;

    /** 科室编码 */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private Long deptCode;

    /** 科室编码 */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private String deptName;

    /** 课题类别 */
    @Excel(name = "课题类别")
    @ApiModelProperty(value = "课题类别")
    private String topicType;

    /** 课题类别 */
    @Excel(name = "年度")
    @ApiModelProperty(value = "年度")
    private String topicYear;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    /** 经费投入类别 */
    @Excel(name = "经费投入类别")
    @ApiModelProperty(value = "经费投入类别")
    private String fundType;

    /** 经费投入金额 */
    @Excel(name = "经费投入金额")
    @ApiModelProperty(value = "经费投入金额")
    private String funMoney;

    /** 负责人 */
    @Excel(name = "负责人")
    @ApiModelProperty(value = "负责人")
    private String personCode;

    /** 负责人 */
    @Excel(name = "负责人")
    @ApiModelProperty(value = "负责人")
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

    /** 批准单位（国家，军队，院内） */
    @Excel(name = "批准单位", readConverterExp = "国=家，军队，院内")
    @ApiModelProperty(value = "批准单位（国家，军队，院内）")
    private String approve;

    /** 等级（重点，杰出人才，青年学者） */
    @Excel(name = "等级", readConverterExp = "重=点，杰出人才，青年学者")
    @ApiModelProperty(value = "等级（重点，杰出人才，青年学者）")
    private String grade;

    /** 协作单位 */
    @Excel(name = "协作单位")
    @ApiModelProperty(value = "协作单位")
    private String cooperateUnit;

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
