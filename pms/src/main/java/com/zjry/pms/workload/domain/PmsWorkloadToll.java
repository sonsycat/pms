package com.zjry.pms.workload.domain;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 工作量-收费项设置对象 pms_workload_toll
 *
 * @author zjry
 * @date 2022-09-29
 */
@Data
@ApiModel(value = "PmsWorkloadToll", description = "工作量-收费项设置对象 pms_workload_toll")
public class PmsWorkloadToll extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /** 项目编码 */
    @Excel(name = "项目编码")
    @ApiModelProperty(value = "项目编码")
    private String projectCode;

    /** 收费项目名称 */
    @Excel(name = "收费项目名称")
    @ApiModelProperty(value = "收费项目名称")
    private String hisName;

    /** 收费项目编码 */
    @Excel(name = "收费项目编码")
    @ApiModelProperty(value = "收费项目编码")
    private String hisCode;

    /** 删除标记(0未删除,1已删除) */
    @Excel(name = "删除标记(0未删除,1已删除)")
    @ApiModelProperty(value = "删除标记(0未删除,1已删除)")
    private String delFlag;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectCode() {
        return projectCode;
    }
    public void setHisName(String hisName) {
        this.hisName = hisName;
    }

    public String getHisName() {
        return hisName;
    }
    public void setHisCode(String hisCode) {
        this.hisCode = hisCode;
    }

    public String getHisCode() {
        return hisCode;
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
            .append("projectCode", getProjectCode())
            .append("hisName", getHisName())
            .append("hisCode", getHisCode())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
