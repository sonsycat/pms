package com.zjry.system.domain;

import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.TreeEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 区域信息对象 sys_area
 * 
 * @author mingc
 * @date 2021-06-17
 */
public class SysArea extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 区域编码 */
    @Excel(name = "区域编码")
    private String areaId;

    /** 区域名称 */
    @Excel(name = "区域名称")
    private String areaName;

    /** 区域信息 */
    @Excel(name = "区域信息")
    private String areaInfo;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setAreaId(String areaId) 
    {
        this.areaId = areaId;
    }

    public String getAreaId() 
    {
        return areaId;
    }
    public void setAreaName(String areaName) 
    {
        this.areaName = areaName;
    }

    public String getAreaName() 
    {
        return areaName;
    }
    public void setAreaInfo(String areaInfo) 
    {
        this.areaInfo = areaInfo;
    }

    public String getAreaInfo() 
    {
        return areaInfo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("areaId", getAreaId())
            .append("parentId", getParentId())
            .append("areaName", getAreaName())
            .append("areaInfo", getAreaInfo())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
