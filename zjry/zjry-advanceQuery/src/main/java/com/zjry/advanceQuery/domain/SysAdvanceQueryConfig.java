package com.zjry.advanceQuery.domain;

import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 高级查询配置对象 sys_advance_query_config
 *
 * @author yangtao
 * @date 2021-10-15
 */
@Data
@ApiModel(value = "SysAdvanceQueryConfig", description = "高级查询配置对象 sys_advance_query_config")
public class SysAdvanceQueryConfig extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @ApiModelProperty(value = "主键")
    private Long id;

    /** 配置名称 */
    @Excel(name = "配置名称")
    @ApiModelProperty(value = "配置名称")
    private String name;

    /** 功能路由 */
    @Excel(name = "功能路由")
    @ApiModelProperty(value = "功能路由")
    private String menuId;

    /** 功能名称 */
    @Excel(name = "功能名称")
    @ApiModelProperty(value = "功能名称")
    private String menuName;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private String delFlag;
    
    // 例：com.zjry.demo.mapper.DemoInviteMapper.selectDemoInviteList
    private String sqlId;

	/** $table.subTable.functionName信息 */
	private List<SysAdvanceQueryConfigDetail> sysAdvanceQueryConfigDetailList;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuId() {
        return menuId;
    }
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuName() {
        return menuName;
    }
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public List<SysAdvanceQueryConfigDetail> getSysAdvanceQueryConfigDetailList() {
        return sysAdvanceQueryConfigDetailList;
    }

    public void setSysAdvanceQueryConfigDetailList(List<SysAdvanceQueryConfigDetail> sysAdvanceQueryConfigDetailList) {
        this.sysAdvanceQueryConfigDetailList = sysAdvanceQueryConfigDetailList;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("menuId", getMenuId())
            .append("menuName", getMenuName())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .append("sysAdvanceQueryConfigDetailList", getSysAdvanceQueryConfigDetailList())
            .toString();
	}
}
