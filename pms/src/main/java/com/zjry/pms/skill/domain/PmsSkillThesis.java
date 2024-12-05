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
 * 论文对象 pms_skill_thesis
 *
 * @author maozt
 * @date 2022-07-28
 */
@Data
@ApiModel(value = "PmsSkillThesis", description = "论文对象 pms_skill_thesis")
public class PmsSkillThesis extends BaseEntity implements Serializable {
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

    /** 刊物编码 */
    @Excel(name = "刊物编码")
    @ApiModelProperty(value = "刊物编码")
    private String thesisCode;

    /** 刊物名称 */
    @Excel(name = "刊物名称")
    @ApiModelProperty(value = "刊物名称")
    private String thesisName;

    /** 科室编码 */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private Long deptCode;

    /** 科室编码 */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private String deptName;

    /** 年度 */
    @Excel(name = "年度")
    @ApiModelProperty(value = "年度")
    private String year;

    /** 论文级别（SCI收录期刊，统计源期刊，院内期刊） */
    @Excel(name = "论文级别", readConverterExp = "S=CI收录期刊，统计源期刊，院内期刊")
    @ApiModelProperty(value = "论文级别（SCI收录期刊，统计源期刊，院内期刊）")
    private String thesisType;

    /** 负责人 */
    @Excel(name = "负责人")
    @ApiModelProperty(value = "负责人")
    private String personCode;

    /** 论文题目 */
    @Excel(name = "论文题目")
    @ApiModelProperty(value = "论文题目")
    private String thesisSubject;

    /** 期刊号 */
    @Excel(name = "期刊号")
    @ApiModelProperty(value = "期刊号")
    private String issn;

    /** 论文栏目 */
    @Excel(name = "论文栏目")
    @ApiModelProperty(value = "论文栏目")
    private String thesisColumn;

    /** 刊物等级（国际，中华，中央，省级，内部，其他） */
    @Excel(name = "刊物等级", readConverterExp = "国=际，中华，中央，省级，内部，其他")
    @ApiModelProperty(value = "刊物等级（国际，中华，中央，省级，内部，其他）")
    private String thesisGrade;

    /** 刊物年 */
    @Excel(name = "刊物年")
    @ApiModelProperty(value = "刊物年")
    private String thesisYear;

    /** 刊物期 */
    @Excel(name = "刊物期")
    @ApiModelProperty(value = "刊物期")
    private String thesisStage;

    /** 刊物卷 */
    @Excel(name = "刊物卷")
    @ApiModelProperty(value = "刊物卷")
    private String thesisVolume;

    /** 出版社 */
    @Excel(name = "出版社")
    @ApiModelProperty(value = "出版社")
    private String publish;

    /** 担任职务（主编，副主编，编委） */
    @Excel(name = "担任职务", readConverterExp = "主=编，副主编，编委")
    @ApiModelProperty(value = "担任职务（主编，副主编，编委）")
    private String position;

    /** 是否统计源期刊（0是，1否） */
    @Excel(name = "是否统计源期刊", readConverterExp = "0=是，1否")
    @ApiModelProperty(value = "是否统计源期刊（0是，1否）")
    private String isExtension;

    /** 出版时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出版时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "出版时间")
    private Date publisTime;

    /** 会议名称 */
    @Excel(name = "会议名称")
    @ApiModelProperty(value = "会议名称")
    private String meetName;

    /** 会议时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "会议时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "会议时间")
    private Date meetTime;

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

    /** 负责人 */
    @Excel(name = "负责人昵称")
    @ApiModelProperty(value = "负责人昵称")
    private String personName;
}
