package com.zjry.pms.evaluate.domain;

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
 * 指标集与科室管理对象 pms_evaluate_set_dept
 *
 * @author mk
 * @date 2022-08-09
 */
@Data
@ApiModel(value = "PmsEvaluateSetDept", description = "指标集与科室管理对象 pms_evaluate_set_dept")
public class PmsEvaluateSetDept extends BaseEntity implements Serializable {
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

    /** 唯一编码 */
    @Excel(name = "唯一编码")
    @ApiModelProperty(value = "唯一编码")
    private String setDeptCode;

    /** 指标集编码 */
    @Excel(name = "指标集编码")
    @ApiModelProperty(value = "指标集编码")
    private String setCode;

    /** 科室编码 */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private String deptCode;

    private String deptName;

    private String deptType;
    /** 内部管理分值 */
    @Excel(name = "内部管理分值")
    @ApiModelProperty(value = "内部管理分值")
    private Object internalScore;

    /** 财务管理分值 */
    @Excel(name = "财务管理分值")
    @ApiModelProperty(value = "财务管理分值")
    private String financeScore;

    /** 服务分值 */
    @Excel(name = "服务分值")
    @ApiModelProperty(value = "服务分值")
    private String serviceScore;

    /** 效率分值 */
    @Excel(name = "效率分值")
    @ApiModelProperty(value = "效率分值")
    private String efficientScore;

    /** 年月 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "年月", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "年月")
    private Date nowDate;

    private Date copyDate;

    private String indexCode;

    private String setName;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("compCode", getCompCode())
            .append("accountCode", getAccountCode())
            .append("setDeptCode", getSetDeptCode())
            .append("setCode", getSetCode())
            .append("deptCode", getDeptCode())
            .append("internalScore", getInternalScore())
            .append("financeScore", getFinanceScore())
            .append("serviceScore", getServiceScore())
            .append("efficientScore", getEfficientScore())
            .append("nowDate", getNowDate())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
