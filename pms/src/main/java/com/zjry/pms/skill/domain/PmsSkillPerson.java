package com.zjry.pms.skill.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 技术参与人对象 pms_skill_person
 *
 * @author maozt
 * @date 2022-08-01
 */
@Data
@ApiModel(value = "PmsSkillPerson", description = "技术参与人对象 pms_skill_person")
public class PmsSkillPerson extends BaseEntity implements Serializable {
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

    /** 参与人或者作者 */
    @Excel(name = "参与人或者作者")
    @ApiModelProperty(value = "参与人或者作者")
    private String personCode;

    /** 是否第一作者 */
    @Excel(name = "是否第一作者")
    @ApiModelProperty(value = "是否第一作者")
    private String isOne;

    /** 审批时间 */
    @ApiModelProperty(value = "审批时间")
    private Date approvelTime;

    /** 审批人姓名 */
    @ApiModelProperty(value = "审批人姓名")
    private String approvelName;

    private List<PmsSkillPerson> personList;

}
