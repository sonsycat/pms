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
 * 审批意见对象 pms_skill_opinion
 *
 * @author maozt
 * @date 2022-07-27
 */
@Data
@ApiModel(value = "PmsSkillOpinion", description = "审批意见对象 pms_skill_opinion")
public class PmsSkillOpinion extends BaseEntity implements Serializable {
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

    /** 编码（各种刊物编码） */
    @Excel(name = "编码", readConverterExp = "各=种刊物编码")
    @ApiModelProperty(value = "编码（各种刊物编码）")
    private String technoCode;

    /** 类别（1课题，2成果，3论文，4专著，5技术） */
    @Excel(name = "类别", readConverterExp = "1=课题，2成果，3论文，4专著，5技术")
    @ApiModelProperty(value = "类别（1课题，2成果，3论文，4专著，5技术）")
    private String technoType;

    /** 审批状态 */
    @Excel(name = "审批状态")
    @ApiModelProperty(value = "审批状态")
    private String auditState;

    /** 意见 */
    @Excel(name = "意见")
    @ApiModelProperty(value = "意见")
    private String opinion;

    /** 审批时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "审批时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "审批时间")
    private Date approvelTime;

    /** 审批人姓名 */
    @Excel(name = "审批人姓名")
    @ApiModelProperty(value = "审批人姓名")
    private String approvelName;
}
