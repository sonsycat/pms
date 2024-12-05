package com.zjry.pms.quality.domain;

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
 * 质量数据生成对象 pms_quality_generate
 *
 * @author mk
 * @date 2022-07-26
 */
@Data
@ApiModel(value = "PmsQualityGenerate", description = "质量数据生成对象 pms_quality_generate")
public class PmsQualityGenerate extends BaseEntity implements Serializable {
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

    /** 科室编码 */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private Long deptCode;

    private String deptName;

    /** 指标编码 */
    @Excel(name = "指标编码")
    @ApiModelProperty(value = "指标编码")
    private String indexCode;


    /** 模板编码 */
    @Excel(name = "模板编码")
    @ApiModelProperty(value = "模板编码")
    private String tempCode;

    private String tempName;


    /** 指标分值 */
    @Excel(name = "指标分值")
    @ApiModelProperty(value = "指标分值")
    private String indexScore;

    /** 发生时间(YYYYMM) */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发生时间(YYYYMM)", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "发生时间(YYYYMM)")
    private Date occureTime;

    private String occTime;

    /** 实际得分 */
    @Excel(name = "实际得分")
    @ApiModelProperty(value = "实际得分")
    private String actualScore;

    /** 发生时间(YYYYMM) */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发生时间(YYYYMM)", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "发生时间(YYYYMM)")
    private Date qTime;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("compCode", getCompCode())
            .append("accountCode", getAccountCode())
            .append("deptCode", getDeptCode())
            .append("indexCode", getIndexCode())
            .append("tempCode", getTempCode())
            .append("indexScore", getIndexScore())
            .append("occureTime", getOccureTime())
            .append("actualScore", getActualScore())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
