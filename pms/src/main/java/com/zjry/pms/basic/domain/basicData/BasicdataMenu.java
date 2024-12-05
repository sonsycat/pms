package com.zjry.pms.basic.domain.basicData;

import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单管理对象 sys_menu
 *
 * @author 王超
 * @date 2021-07-19
 */
@Data
@ApiModel(value = "BasicdataMenu", description = "菜单管理对象 sys_menu")
public class BasicdataMenu extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 菜单ID */
    @ApiModelProperty(value = "${comment}")
    private Long menuId;

    /** 菜单名称 */
    @Excel(name = "菜单名称")
    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    /** 父菜单ID */
    @Excel(name = "父菜单ID")
    @ApiModelProperty(value = "父菜单ID")
    private Long parentId;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    @ApiModelProperty(value = "显示顺序")
    private Integer orderNum;

    /** 路由地址 */
    @Excel(name = "路由地址")
    @ApiModelProperty(value = "路由地址")
    private String path;

    /** 组件路径 */
    @Excel(name = "组件路径")
    @ApiModelProperty(value = "组件路径")
    private String component;

    /** 是否为外链（0是 1否） */
    @Excel(name = "是否为外链", readConverterExp = "0=是,1=否")
    @ApiModelProperty(value = "是否为外链")
    private Integer isFrame;

    /** 是否缓存（0缓存 1不缓存） */
    @Excel(name = "是否缓存", readConverterExp = "0=缓存,1=不缓存")
    @ApiModelProperty(value = "是否缓存")
    private Integer isCache;

    /** 菜单类型（M目录 C菜单 F按钮） */
    @Excel(name = "菜单类型", readConverterExp = "M=目录,C=菜单,F=按钮")
    @ApiModelProperty(value = "菜单类型")
    private String menuType;

    /** 菜单状态（0显示 1隐藏） */
    @Excel(name = "菜单状态", readConverterExp = "0=显示,1=隐藏")
    @ApiModelProperty(value = "菜单状态")
    private String visible;

    /** 菜单状态（0正常 1停用） */
    @Excel(name = "菜单状态", readConverterExp = "0=正常,1=停用")
    @ApiModelProperty(value = "菜单状态")
    private String status;

    /** 权限标识 */
    @Excel(name = "权限标识")
    @ApiModelProperty(value = "权限标识")
    private String perms;

    /** 菜单图标 */
    @Excel(name = "菜单图标")
    @ApiModelProperty(value = "菜单图标")
    private String icon;

    /** 系统标识（用于区别不同业务系统的数据） */
    @Excel(name = "系统标识", readConverterExp = "用=于区别不同业务系统的数据")
    @ApiModelProperty(value = "系统标识")
    private String systemFlag;

    /** 孩子集合*/
    private List<BasicdataMenu> children = new ArrayList();

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getMenuId() {
        return menuId;
    }
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuName() {
        return menuName;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getParentId() {
        return parentId;
    }
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getOrderNum() {
        return orderNum;
    }
    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
    public void setComponent(String component) {
        this.component = component;
    }

    public String getComponent() {
        return component;
    }
    public void setIsFrame(Integer isFrame) {
        this.isFrame = isFrame;
    }

    public Integer getIsFrame() {
        return isFrame;
    }
    public void setIsCache(Integer isCache) {
        this.isCache = isCache;
    }

    public Integer getIsCache() {
        return isCache;
    }
    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getMenuType() {
        return menuType;
    }
    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getVisible() {
        return visible;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    public void setPerms(String perms) {
        this.perms = perms;
    }

    public String getPerms() {
        return perms;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }
    public void setSystemFlag(String systemFlag) {
        this.systemFlag = systemFlag;
    }

    public String getSystemFlag() {
        return systemFlag;
    }

    public List<BasicdataMenu> getChildren() {
        return children;
    }

    public void setChildren(List<BasicdataMenu> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("menuId", getMenuId())
            .append("menuName", getMenuName())
            .append("parentId", getParentId())
            .append("orderNum", getOrderNum())
            .append("path", getPath())
            .append("component", getComponent())
            .append("isFrame", getIsFrame())
            .append("isCache", getIsCache())
            .append("menuType", getMenuType())
            .append("visible", getVisible())
            .append("status", getStatus())
            .append("perms", getPerms())
            .append("icon", getIcon())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("systemFlag", getSystemFlag())
            .append("delFlag", getDelFlag())
             .toString();
	}
}
