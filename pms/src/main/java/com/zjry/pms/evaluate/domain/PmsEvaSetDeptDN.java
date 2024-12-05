package com.zjry.pms.evaluate.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zjry.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author mk
 * @title: PmsEvaSetDeptDN
 * @projectName zjry-pms
 * @date 2022/8/1111:31
 */
@Data
public class PmsEvaSetDeptDN {
    /** 单位编码 */
    @Excel(name = "单位编码")
    @ApiModelProperty(value = "单位编码")
    private Long compCode;

    private String deptName;
    private Long deptCode;

    /** 账套编码 */
    @Excel(name = "账套编码")
    @ApiModelProperty(value = "账套编码")
    private String accountCode;

    /** 唯一编码 */
    @Excel(name = "唯一编码")
    @ApiModelProperty(value = "唯一编码")
    private String setDeptCode;

    /** 指标编码 */
    @Excel(name = "指标编码")
    @ApiModelProperty(value = "指标编码")
    private String indexCode;

    /** 降低量 */
    @Excel(name = "降低量")
    @ApiModelProperty(value = "降低量")
    private String reduce;

    /** 扣分 */
    @Excel(name = "扣分")
    @ApiModelProperty(value = "扣分")
    private String deduct;

    /** 增加量 */
    @Excel(name = "增加量")
    @ApiModelProperty(value = "增加量")
    private String increase;

    /** 加分 */
    @Excel(name = "加分")
    @ApiModelProperty(value = "加分")
    private String extra;

    /** 单位(%) */
    @Excel(name = "单位(%)")
    @ApiModelProperty(value = "单位(%)")
    private String unit;



    /** 指标分数 */
    @Excel(name = "指标分数")
    @ApiModelProperty(value = "指标分数")
    private String indexScore;

    /** 是否允许超分 */
    @Excel(name = "是否允许超分")
    @ApiModelProperty(value = "是否允许超分")
    private String isHyper;

    /** 是否允许负分 */
    @Excel(name = "是否允许负分")
    @ApiModelProperty(value = "是否允许负分")
    private String isNegative;

    /** 是否固定分值 */
    @Excel(name = "是否固定分值")
    @ApiModelProperty(value = "是否固定分值")
    private String isFixed;

    /** 阀值 */
    @Excel(name = "阀值")
    @ApiModelProperty(value = "阀值")
    private String threshold;

    private String setCode;

    /** 超分限制 */
    @Excel(name = "超分限制")
    @ApiModelProperty(value = "超分限制")
    private String overLimit;

    /** 减分限制 */
    @Excel(name = "减分限制")
    @ApiModelProperty(value = "减分限制")
    private String deductionLimit;

    private String oneTarget;
    private String twoTarget;
    private String threeTarget;
    private String fourTarget;
    private String fiveTarget;
    private String sixTarget;
    private String sevenTarget;
    private String eightTarget;
    private String nineTarget;
    private String tenTarget;
    private String elevenTarget;
    private String twelveTarget;


    private Long oneID;
    private Long twoID;
    private Long threeID;
    private Long fourID;
    private Long fiveID;
    private Long sixID;
    private Long sevenID;
    private Long eightID;
    private Long nineID;
    private Long tenID;
    private Long elevenID;
    private Long twelveID;

    private Date oneDate;
    private Date twoDate;
    private Date threeDate;
    private Date fourDate;
    private Date fiveDate;
    private Date sixDate;
    private Date sevenDate;
    private Date eightDate;
    private Date nineDate;
    private Date tenDate;
    private Date elevenDate;
    private Date twelveDate;

}
