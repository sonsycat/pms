package com.zjry.pms.workload.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 工作量对象 pms_workload
 *
 * @author zjry
 * @date 2022-09-29
 */
@Data
@ApiModel(value = "PmsWorkload", description = "工作量对象 pms_workload")
public class PmsWorkload extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /**
     * 单位编码
     */
    @Excel(name = "单位编码")
    @ApiModelProperty(value = "单位编码")
    private Long compCode;

    /**
     * 账套编码
     */
    @Excel(name = "账套编码")
    @ApiModelProperty(value = "账套编码")
    private String accountCode;

    /**
     * 项目编码
     */
    @Excel(name = "项目编码")
    @ApiModelProperty(value = "项目编码")
    private String projectCode;

    /**
     * 项目名称
     */
    @Excel(name = "项目名称")
    @ApiModelProperty(value = "项目名称")
    private String projectName;

    /**
     * 类别
     */
    @Excel(name = "类别")
    @ApiModelProperty(value = "类别")
    private String type;

    /**
     * 拼音码
     */
    @Excel(name = "拼音码")
    @ApiModelProperty(value = "拼音码")
    private String spell;

    /**
     * 住院积分
     */
    @Excel(name = "住院积分")
    @ApiModelProperty(value = "住院积分")
    private BigDecimal hospitalPoints;

    /**
     * 门诊积分
     */
    @Excel(name = "门诊积分")
    @ApiModelProperty(value = "门诊积分")
    private BigDecimal outpatientPoints;

    /**
     * 删除标记(0未删除,1已删除)
     */
    @Excel(name = "删除标记(0未删除,1已删除)")
    @ApiModelProperty(value = "删除标记(0未删除,1已删除)")
    @TableLogic
    private String delFlag;

    /**
     * 年月
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "年月", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "年月")
    private Date workloadDate;

    @ApiModelProperty("类型名称")
    @TableField(exist = false)
    private String typeName;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCompCode(Long compCode) {
        this.compCode = compCode;
    }

    public Long getCompCode() {
        return compCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String getSpell() {
        return spell;
    }

    public void setHospitalPoints(BigDecimal hospitalPoints) {
        this.hospitalPoints = hospitalPoints;
    }

    public BigDecimal getHospitalPoints() {
        return hospitalPoints;
    }

    public void setOutpatientPoints(BigDecimal outpatientPoints) {
        this.outpatientPoints = outpatientPoints;
    }

    public BigDecimal getOutpatientPoints() {
        return outpatientPoints;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setWorkloadDate(Date workloadDate) {
        this.workloadDate = workloadDate;
    }

    public Date getWorkloadDate() {
        return workloadDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("compCode", getCompCode())
                .append("accountCode", getAccountCode())
                .append("projectCode", getProjectCode())
                .append("projectName", getProjectName())
                .append("type", getType())
                .append("spell", getSpell())
                .append("hospitalPoints", getHospitalPoints())
                .append("outpatientPoints", getOutpatientPoints())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("workloadDate", getWorkloadDate())
                .toString();
    }
}
