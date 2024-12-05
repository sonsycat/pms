package com.zjry.pms.process.domain;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 流程模块对象 hr_process_model
 *
 * @author yzh
 * @date 2021-07-26
 */
@Data
@ApiModel(value = "HrProcessModel", description = "流程模块对象 hr_process_model")
public class HrProcessModel extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "${comment}")
    private Long id;

    /** 流程key */
    @Excel(name = "流程key")
    @ApiModelProperty(value = "流程key")
    private String procinsId;

    /** 流程名称 */
    @Excel(name = "流程名称")
    @ApiModelProperty(value = "流程名称")
    private String procinsName;

    /** 表名 */
    @Excel(name = "表名")
    @ApiModelProperty(value = "表名")
    private String tabelName;

    /** 表单url */
    @Excel(name = "表单url")
    @ApiModelProperty(value = "表单url")
    private String formUrl;
    
    /** 类型 */
    @Excel(name = "类型")
    @ApiModelProperty(value = "类型")
    private String type;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setProcinsId(String procinsId) {
        this.procinsId = procinsId;
    }

    public String getProcinsId() {
        return procinsId;
    }
    public void setProcinsName(String procinsName) {
        this.procinsName = procinsName;
    }

    public String getProcinsName() {
        return procinsName;
    }
    public void setTabelName(String tabelName) {
        this.tabelName = tabelName;
    }

    public String getTabelName() {
        return tabelName;
    }
    public void setFormUrl(String formUrl) {
        this.formUrl = formUrl;
    }

    public String getFormUrl() {
        return formUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("procinsId", getProcinsId())
            .append("procinsName", getProcinsName())
            .append("tabelName", getTabelName())
            .append("formUrl", getFormUrl())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
