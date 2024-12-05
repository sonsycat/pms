package com.zjry.pms.evaluate.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * 指标数据值（外部获取）对象 pms_evaluate_data
 *
 * @author zjry
 * @date 2022-10-17
 */
@Data
@ApiModel(value = "PmsEvaluateData", description = "指标数据值（外部获取）对象 pms_evaluate_data")
public class PmsEvaluateData extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    @ApiModelProperty(value = "自增主键")
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
    private String deptCode;

    /** 院，科，人编码 */
    @Excel(name = "院，科，人编码")
    @ApiModelProperty(value = "院，科，人编码")
    private String unitCode;

    /** 固定与指标代码相同 */
    @Excel(name = "固定与指标代码相同")
    @ApiModelProperty(value = "固定与指标代码相同")
    private String guideCode;

    /** 指标值 */
    @Excel(name = "指标值")
    @ApiModelProperty(value = "指标值")
    private BigDecimal guideValue;

    /** 指标值计算结构 */
    private BigDecimal guideValueResult;

    /** Y，K，R */
    @Excel(name = "Y，K，R")
    @ApiModelProperty(value = "Y，K，R")
    private String guideType;

    /** 数据类型(1平衡计分卡；2奖金；3二次核算) */
    private String dataType;

    /** 数据作用日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "数据作用日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "数据作用日期")
    private Date actDate;

    /** 数据作用开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "数据作用开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "数据作用开始日期")
    private Date actDateEnd;

    /** 数据作用结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "数据作用结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "数据作用结束日期")
    private Date actDateBegin;

    /** 删除标记(0未删除,1已删除) */
    @Excel(name = "删除标记(0未删除,1已删除)")
    @ApiModelProperty(value = "删除标记(0未删除,1已删除)")
    private String delFlag;

    /** 指标名称 */
    private String indexName;
    /** 科室类型：0 独立核算科室 1 平均奖科室 */
    private String deptType;
    private String deptRoomCode;

    /** 指标集编码 */
    private String setCode;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("unitCode", getUnitCode())
            .append("guideCode", getGuideCode())
            .append("guideValue", getGuideValue())
            .append("guideValueResult", getGuideValueResult())
            .append("guideType", getGuideType())
            .append("actDate", getActDate())
            .append("actDateEnd", getActDateEnd())
            .append("actDateBegin", getActDateBegin())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}