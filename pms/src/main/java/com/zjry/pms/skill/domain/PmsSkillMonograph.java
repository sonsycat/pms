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
 * 专著对象 pms_skill_monograph
 *
 * @author maozt
 * @date 2022-08-02
 */
@Data
@ApiModel(value = "PmsSkillMonograph", description = "专著对象 pms_skill_monograph")
public class PmsSkillMonograph extends BaseEntity implements Serializable {
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

    /** 专著编码 */
    @Excel(name = "专著编码")
    @ApiModelProperty(value = "专著编码")
    private String monographCode;

    /** 专著名称 */
    @Excel(name = "专著名称")
    @ApiModelProperty(value = "专著名称")
    private String monographName;

    /** 专著级别（SCI收录期刊，统计源期刊，院内期刊） */
    @Excel(name = "专著级别", readConverterExp = "S=CI收录期刊，统计源期刊，院内期刊")
    @ApiModelProperty(value = "专著级别（SCI收录期刊，统计源期刊，院内期刊）")
    private String monographType;

    /** 科室编码 */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private Long deptCode;

    /** 科室编码 */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private String deptName;

    /** 出版社 */
    @Excel(name = "出版社")
    @ApiModelProperty(value = "出版社")
    private String publish;

    /** 出版时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出版时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "出版时间")
    private Date publisTime;

    /** 期刊号 */
    @Excel(name = "期刊号")
    @ApiModelProperty(value = "期刊号")
    private String issn;

    /** 杂志名称 */
    @Excel(name = "杂志名称")
    @ApiModelProperty(value = "杂志名称")
    private String magazine;

    /** 字数 */
    @Excel(name = "字数")
    @ApiModelProperty(value = "字数")
    private String wordNum;

    /** 作者 */
    @Excel(name = "作者")
    @ApiModelProperty(value = "作者")
    private String author;

    /** 图书编号 */
    @Excel(name = "图书编号")
    @ApiModelProperty(value = "图书编号")
    private String bookNumber;

    /** 开本（大16K，16K，大32K，32K，64K，其他） */
    @Excel(name = "开本", readConverterExp = "大=16K，16K，大32K，32K，64K，其他")
    @ApiModelProperty(value = "开本（大16K，16K，大32K，32K，64K，其他）")
    private String folio;

    /** 印刷数量 */
    @Excel(name = "印刷数量")
    @ApiModelProperty(value = "印刷数量")
    private String priteNum;

    /** 担任职务（主编，副主编，编委） */
    @Excel(name = "担任职务", readConverterExp = "主=编，副主编，编委")
    @ApiModelProperty(value = "担任职务（主编，副主编，编委）")
    private String position;

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
}
