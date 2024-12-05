package com.zjry.pms.basic.domain.basicData;

import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 参数配置对象 sys_config
 *
 * @author cyq
 * @date 2021-08-03
 */
@Data
@ApiModel(value = "BasicdataConfig", description = "参数配置对象 sys_config")
public class BasicdataConfig extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** config_id */
    @ApiModelProperty(value = "${comment}")
    private Long configId;

    /** 参数名称 */
    @Excel(name = "参数名称")
    @ApiModelProperty(value = "参数名称")
    private String configName;

    /** 参数键名 */
    @Excel(name = "参数键名")
    @ApiModelProperty(value = "参数键名")
    private String configKey;

    /** 参数键值 */
    @Excel(name = "参数键值")
    @ApiModelProperty(value = "参数键值")
    private String configValue;

    /** 系统内置（0是 1否） */
    @Excel(name = "系统内置", readConverterExp = "Y=是,N=否")
    @ApiModelProperty(value = "系统内置")
    private String configType;

    /** 删除标识(0-正常；1-删除) */
    @ApiModelProperty(value = "系统内置")
    private String delFlag;

    /** 系统标识（用于区别不同业务系统的数据） */
    @ApiModelProperty(value = "系统标识")
    private String systemFlag;

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public Long getConfigId() {
        return configId;
    }
    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigName() {
        return configName;
    }
    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigKey() {
        return configKey;
    }
    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public String getConfigValue() {
        return configValue;
    }
    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public String getConfigType() {
        return configType;
    }
    @Override
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }
    public void setSystemFlag(String systemFlag) {
        this.systemFlag = systemFlag;
    }

    public String getSystemFlag() {
        return systemFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("configId", getConfigId())
            .append("configName", getConfigName())
            .append("configKey", getConfigKey())
            .append("configValue", getConfigValue())
            .append("configType", getConfigType())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("systemFlag", getSystemFlag())
            .append("remark", getRemark())
            .toString();
	}
}
