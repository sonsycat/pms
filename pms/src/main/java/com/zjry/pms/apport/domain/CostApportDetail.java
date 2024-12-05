package com.zjry.pms.apport.domain;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 分摊明细对象 cost_apport_detail
 *
 * @author yzh
 * @date 2022-05-17
 */
@Data
@ApiModel(value = "CostApportDetail", description = "分摊明细对象 cost_apport_detail")
public class CostApportDetail extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /** 分摊编码 */
    @Excel(name = "分摊编码")
    @ApiModelProperty(value = "分摊编码")
    private String apportCode;

    /** 科室编码 */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private Long deptCode;
    
    /** 科室名称 */
    @Excel(name = "科室名称")
    @ApiModelProperty(value = "科室名称")
    private String deptName;
    
    /** 科室类型 */
    @Excel(name = "科室类型")
    @ApiModelProperty(value = "科室类型")
    private String deptType;

    /** 项目编码 */
    @Excel(name = "项目编码")
    @ApiModelProperty(value = "项目编码")
    private String projectCode;
    
    /** 比例编码 */
    @Excel(name = "比例编码")
    @ApiModelProperty(value = "比例编码")
    private String ratioCode;

    /** 成本类型 */
    @Excel(name = "成本类型")
    @ApiModelProperty(value = "成本类型")
    private String costType;
    
    /** 分摊级别 */
    @Excel(name = "分摊级别")
    @ApiModelProperty(value = "分摊级别")
    private String apportType;

    /** 分摊金额 */
    @Excel(name = "分摊金额")
    @ApiModelProperty(value = "分摊金额")
    private Double amount;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setApportCode(String apportCode) {
        this.apportCode = apportCode;
    }

    public String getApportCode() {
        return apportCode;
    }
    public void setDeptCode(Long deptCode) {
        this.deptCode = deptCode;
    }

    public Long getDeptCode() {
        return deptCode;
    }
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectCode() {
        return projectCode;
    }
    public void setApportType(String apportType) {
        this.apportType = apportType;
    }

    public String getApportType() {
        return apportType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("apportCode", getApportCode())
            .append("deptCode", getDeptCode())
            .append("projectCode", getProjectCode())
            .append("apportType", getApportType())
            .append("amount", getAmount())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
