package com.zjry.advanceQuery.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;

/**
 * ${subTable.functionName}对象 sys_advance_query_config_detail
 * 
 * @author yangtao
 * @date 2021-10-15
 */
public class SysAdvanceQueryConfigDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "字段下拉显示字典值")
    private Long configId;

    /** 字段英文名 */
    @Excel(name = "字段英文名")
    private String fieldName;

    /** 字段中文名 */
    @Excel(name = "字段中文名")
    private String fieldDesc;

    /** 字段操作类型 */
    @Excel(name = "字段操作类型")
    private String fieldOpTyoe;

    /** 字段显示控件类型 */
    @Excel(name = "字段显示控件类型")
    private String fieldLabelType;

    /** 字段下拉显示字典值 */
    @Excel(name = "字段下拉显示字典值")
    private String fieldDictType;

    /** $column.columnComment */
    private String delFlag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setConfigId(Long configId) 
    {
        this.configId = configId;
    }

    public Long getConfigId() 
    {
        return configId;
    }
    public void setFieldName(String fieldName) 
    {
        this.fieldName = fieldName;
    }

    public String getFieldName() 
    {
        return fieldName;
    }
    public void setFieldDesc(String fieldDesc) 
    {
        this.fieldDesc = fieldDesc;
    }

    public String getFieldDesc() 
    {
        return fieldDesc;
    }
    public void setFieldOpTyoe(String fieldOpTyoe) 
    {
        this.fieldOpTyoe = fieldOpTyoe;
    }

    public String getFieldOpTyoe() 
    {
        return fieldOpTyoe;
    }
    public void setFieldLabelType(String fieldLabelType) 
    {
        this.fieldLabelType = fieldLabelType;
    }

    public String getFieldLabelType() 
    {
        return fieldLabelType;
    }
    public void setFieldDictType(String fieldDictType) 
    {
        this.fieldDictType = fieldDictType;
    }

    public String getFieldDictType() 
    {
        return fieldDictType;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("configId", getConfigId())
            .append("fieldName", getFieldName())
            .append("fieldDesc", getFieldDesc())
            .append("fieldOpTyoe", getFieldOpTyoe())
            .append("fieldLabelType", getFieldLabelType())
            .append("fieldDictType", getFieldDictType())
            .append("delFlag", getDelFlag())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
