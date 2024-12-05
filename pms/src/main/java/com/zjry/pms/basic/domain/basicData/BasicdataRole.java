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
 * 角色信息对象 sys_role
 *
 * @author 王超
 * @date 2021-07-20
 */
@Data
@ApiModel(value = "BasicdataRole", description = "角色信息对象 sys_role")
public class BasicdataRole extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 角色ID */
    @ApiModelProperty(value = "${comment}")
    private Long roleId;

    /** 角色名称 */
    @Excel(name = "角色名称")
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /** 角色权限字符串 */
    @Excel(name = "角色权限字符串")
    @ApiModelProperty(value = "角色权限字符串")
    private String roleKey;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    @ApiModelProperty(value = "显示顺序")
    private Integer roleSort;

    /** 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限 5： 仅本人数据权限）' */
    @ApiModelProperty(value = "数据范围")
    private String dataScope;

    /** 菜单树选择项是否关联显示 */
    @ApiModelProperty(value = "菜单树选择项是否关联显示")
    private boolean menuCheckStrictly;

    /** 部门树选择项是否关联显示 */
    @ApiModelProperty(value = "部门树选择项是否关联显示")
    private boolean deptCheckStrictly;

    /** 角色状态(0-启用；1-停用) */
    @Excel(name = "角色状态", readConverterExp = "0=启用,1=停用")
    @ApiModelProperty(value = "角色状态(0-启用；1-停用)")
    private String status;

    /** 删除标识(0-正常；1-删除) */
    @ApiModelProperty(value = "角色状态(0-启用；1-停用)")
    private String delFlag;

    /** 菜单id数组*/
    private Long[] menuIds;
    private Long[] deptIds;
    /** 使用标识*/
    private String useFlag;
    /** 系统标识*/
    private String systemFlag;

    public BasicdataRole() {
    }

    public BasicdataRole(Long roleId) {
        this.roleId = roleId;
    }

    public boolean isAdmin() {
        return isAdmin(this.roleId);
    }

    public static boolean isAdmin(Long roleId) {
        return roleId != null && 1L == roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getRoleId() {
        return roleId;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    public String getRoleKey() {
        return roleKey;
    }
    public void setRoleSort(Integer roleSort) {
        this.roleSort = roleSort;
    }

    public Integer getRoleSort() {
        return roleSort;
    }
    public void setDataScope(String dataScope) {
        this.dataScope = dataScope;
    }

    public String getDataScope() {
        return dataScope;
    }
    public void setMenuCheckStrictly(boolean menuCheckStrictly) {
        this.menuCheckStrictly = menuCheckStrictly;
    }

    public boolean getMenuCheckStrictly() {
        return menuCheckStrictly;
    }
    public void setDeptCheckStrictly(boolean deptCheckStrictly) {
        this.deptCheckStrictly = deptCheckStrictly;
    }

    public boolean getDeptCheckStrictly() {
        return deptCheckStrictly;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public String getSystemFlag() {
        return systemFlag;
    }

    public void setSystemFlag(String systemFlag) {
        this.systemFlag = systemFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("roleId", getRoleId())
            .append("roleName", getRoleName())
            .append("roleKey", getRoleKey())
            .append("roleSort", getRoleSort())
            .append("dataScope", getDataScope())
            .append("menuCheckStrictly", getMenuCheckStrictly())
            .append("deptCheckStrictly", getDeptCheckStrictly())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("systemFlag", getSystemFlag())
            .toString();
	}
}
