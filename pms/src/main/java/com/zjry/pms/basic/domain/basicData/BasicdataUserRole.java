package com.zjry.pms.basic.domain.basicData;

import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 用户和角色关联对象 sys_user_role
 *
 * @author 王超
 * @date 2021-07-22
 */
@Data
@ApiModel(value = "BasicdataUserRole", description = "用户和角色关联对象 sys_user_role")
public class BasicdataUserRole extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    @ApiModelProperty(value = "${comment}")
    private Long userId;

    /** 角色ID */
    @ApiModelProperty(value = "${comment}")
    private Long roleId;

    /** 系统标识（用于区别不同业务系统的数据） */
    @ApiModelProperty(value = "系统标识（用于区别不同业务系统的数据）")
    private String systemFlag;

    private String useFlag;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getRoleId() {
        return roleId;
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
            .append("userId", getUserId())
            .append("roleId", getRoleId())
            .append("delFlag", getDelFlag())
            .append("systemFlag", getSystemFlag())
             .toString();
	}
}
