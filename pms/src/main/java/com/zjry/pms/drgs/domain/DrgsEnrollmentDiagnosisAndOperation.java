package com.zjry.pms.drgs.domain;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * ADRG入组诊断及操作对象 drgs_enrollment_diagnosis_and_operation
 *
 * @author zjry
 * @date 2023-03-20
 */
@Data
@ApiModel(value = "DrgsEnrollmentDiagnosisAndOperation", description = "ADRG入组诊断及操作对象 drgs_enrollment_diagnosis_and_operation")
public class DrgsEnrollmentDiagnosisAndOperation extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    @ApiModelProperty(value = "自增主键")
    private Long id;

    /** MDC编码 */
    @Excel(name = "MDC编码")
    @ApiModelProperty(value = "MDC编码")
    private String mdcCode;

    /** MDC名称 */
    @Excel(name = "MDC名称")
    @ApiModelProperty(value = "MDC名称")
    private String mdcName;

    /** ADRG编码 */
    @Excel(name = "ADRG编码")
    @ApiModelProperty(value = "ADRG编码")
    private String adrgCode;

    /** ADRG名称 */
    @Excel(name = "ADRG名称")
    @ApiModelProperty(value = "ADRG名称")
    private String adrgName;

    /** ICD编码 */
    @Excel(name = "ICD编码")
    @ApiModelProperty(value = "ICD编码")
    private String icdCode;

    /** ICD名称 */
    @Excel(name = "ICD名称")
    @ApiModelProperty(value = "ICD名称")
    private String icdName;

    /** ICD类型 */
    @Excel(name = "ICD类型")
    @ApiModelProperty(value = "ICD类型")
    private String icdType;

    /** 组合号 */
    @Excel(name = "组合号")
    @ApiModelProperty(value = "组合号")
    private String combinationNo;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志")
    private String delFlag;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setMdcCode(String mdcCode) {
        this.mdcCode = mdcCode;
    }

    public String getMdcCode() {
        return mdcCode;
    }
    public void setMdcName(String mdcName) {
        this.mdcName = mdcName;
    }

    public String getMdcName() {
        return mdcName;
    }
    public void setAdrgCode(String adrgCode) {
        this.adrgCode = adrgCode;
    }

    public String getAdrgCode() {
        return adrgCode;
    }
    public void setAdrgName(String adrgName) {
        this.adrgName = adrgName;
    }

    public String getAdrgName() {
        return adrgName;
    }
    public void setIcdCode(String icdCode) {
        this.icdCode = icdCode;
    }

    public String getIcdCode() {
        return icdCode;
    }
    public void setIcdName(String icdName) {
        this.icdName = icdName;
    }

    public String getIcdName() {
        return icdName;
    }
    public void setIcdType(String icdType) {
        this.icdType = icdType;
    }

    public String getIcdType() {
        return icdType;
    }
    public void setCombinationNo(String combinationNo) {
        this.combinationNo = combinationNo;
    }

    public String getCombinationNo() {
        return combinationNo;
    }
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("mdcCode", getMdcCode())
            .append("mdcName", getMdcName())
            .append("adrgCode", getAdrgCode())
            .append("adrgName", getAdrgName())
            .append("icdCode", getIcdCode())
            .append("icdName", getIcdName())
            .append("icdType", getIcdType())
            .append("combinationNo", getCombinationNo())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .toString();
	}
}
