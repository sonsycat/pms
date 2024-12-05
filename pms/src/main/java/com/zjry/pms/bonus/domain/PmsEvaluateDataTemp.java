package com.zjry.pms.bonus.domain;

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
 * 指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)对象 pms_evaluate_data_temp
 *
 * @author zjry
 * @date 2022-10-25
 */
@Data
@ApiModel(value = "PmsEvaluateDataTemp", description = "指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)对象 pms_evaluate_data_temp")
public class PmsEvaluateDataTemp extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 单位编码
     */
    private Long compCode;

    /**
     * 账套编码
     */
    private String accountCode;

    /**
     * 自增主键
     */
    @ApiModelProperty(value = "自增主键")
    private Long id;

    /**
     * 院，科，人编码
     */
    @Excel(name = "院，科，人编码")
    @ApiModelProperty(value = "院，科，人编码")
    private String unitCode;

    /**
     * 固定与指标代码相同
     */
    @Excel(name = "固定与指标代码相同")
    @ApiModelProperty(value = "固定与指标代码相同")
    private String guideCode;

    /**
     * 指标值
     */
    @Excel(name = "指标值")
    @ApiModelProperty(value = "指标值")
    private BigDecimal guideValue;

    /**
     * 指标值计算结果
     */
    @Excel(name = "指标值计算结果")
    @ApiModelProperty(value = "指标值计算结果")
    private BigDecimal guideValueResult;

    /**
     * Y，K，R
     */
    @Excel(name = "Y，K，R")
    @ApiModelProperty(value = "Y，K，R")
    private String guideType;

    /**
     * 数据作用日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "数据作用日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "数据作用日期")
    private Date actDate;

    /**
     * 数据作用开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "数据作用开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "数据作用开始日期")
    private Date actDateEnd;

    /**
     * 数据作用结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "数据作用结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "数据作用结束日期")
    private Date actDateBegin;

    /**
     * 删除标记(0未删除,1已删除)
     */
    @Excel(name = "删除标记(0未删除,1已删除)")
    @ApiModelProperty(value = "删除标记(0未删除,1已删除)")
    private String delFlag;

    /**
     * 科室编码
     */
    private String deptCode;

    /**
     * 数据类型(1平衡计分卡；2奖金；3二次核算)
     */
    private String dataType;

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setGuideCode(String guideCode) {
        this.guideCode = guideCode;
    }

    public String getGuideCode() {
        return guideCode;
    }

    public void setGuideValue(BigDecimal guideValue) {
        this.guideValue = guideValue;
    }

    public BigDecimal getGuideValue() {
        return guideValue;
    }

    public void setGuideValueResult(BigDecimal guideValueResult) {
        this.guideValueResult = guideValueResult;
    }

    public BigDecimal getGuideValueResult() {
        return guideValueResult;
    }

    public void setGuideType(String guideType) {
        this.guideType = guideType;
    }

    public String getGuideType() {
        return guideType;
    }

    public void setActDate(Date actDate) {
        this.actDate = actDate;
    }

    public Date getActDate() {
        return actDate;
    }

    public void setActDateEnd(Date actDateEnd) {
        this.actDateEnd = actDateEnd;
    }

    public Date getActDateEnd() {
        return actDateEnd;
    }

    public void setActDateBegin(Date actDateBegin) {
        this.actDateBegin = actDateBegin;
    }

    public Date getActDateBegin() {
        return actDateBegin;
    }

    @Override
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
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
