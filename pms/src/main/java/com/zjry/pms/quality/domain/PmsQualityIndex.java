package com.zjry.pms.quality.domain;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 质量考评指标对象 pms_quality_index
 *
 * @author yzh
 * @date 2022-07-05
 */
@Data
@ApiModel(value = "PmsQualityIndex", description = "质量考评指标对象 pms_quality_index")
public class PmsQualityIndex extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /** 单位编码 */
    @ApiModelProperty(value = "单位编码")
    private Long compCode;

    /** 账套编码 */
    @ApiModelProperty(value = "账套编码")
    private String accountCode;

    /** 主管部门 */
    @Excel(name = "主管部门")
    @ApiModelProperty(value = "主管部门")
    private String deptCode;
    
    /** 主管部门名称 */
    @Excel(name = "主管部门名称")
    @ApiModelProperty(value = "主管部门名称")
    private String deptName;

    /** 指标编码 */
    @Excel(name = "指标编码")
    @ApiModelProperty(value = "指标编码")
    private String indexCode;

    /** 指标名称 */
    @Excel(name = "指标名称")
    @ApiModelProperty(value = "指标名称")
    private String indexName;

    /** 指标分值 */
    @Excel(name = "指标分值")
    @ApiModelProperty(value = "指标分值")
    private String indexScore;

    /** 模板编码 */
    @ApiModelProperty(value = "模板编码")
    private String tempCode;

    /** 时间列 */
    @ApiModelProperty(value = "时间列")
    private String dateCol;

    private String dateColName;

    /** 部门列 */
    @ApiModelProperty(value = "部门列")
    private String deptCol;

    private String deptColName;
    /** 数值列 */
    @ApiModelProperty(value = "数值列")
    private String numCol;

    private String numColName;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("compCode", getCompCode())
            .append("accountCode", getAccountCode())
            .append("deptCode", getDeptCode())
            .append("indexCode", getIndexCode())
            .append("indexName", getIndexName())
            .append("indexScore", getIndexScore())
            .append("tempCode", getTempCode())
            .append("dateCol", getDateCol())
            .append("deptCol", getDeptCol())
            .append("numCol", getNumCol())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
