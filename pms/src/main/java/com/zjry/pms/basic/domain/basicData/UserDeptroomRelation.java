package com.zjry.pms.basic.domain.basicData;

import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

/**
 * 用户科室对应关系对象 user_deptroom_relation
 *
 * @author 王超
 * @date 2021-07-23
 */
@Data
@ApiModel(value = "UserDeptroomRelation", description = "用户科室对应关系对象 user_deptroom_relation")
public class UserDeptroomRelation extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 科室ID */
    @Excel(name = "科室ID")
    @ApiModelProperty(value = "科室ID")
    private Long deptroomId;

    /** 用户ID */
    @Excel(name = "用户ID")
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    /** 是否组织所属（0-是；1-否）*/
    private String fromOrg;

    /** 删除标识(0-正常；1-删除) */
    @ApiModelProperty(value = "用户ID")
    private String delFlag;

    /** 科室名称*/
    private String deptroomName;
    /** 用户名称*/
    private String userName;
    /** 角色id*/
    private Long roleId;
    /** 系统标识*/
    private String systemFlag;

    /** 选中科室*/
    private List<BasicdataDeptRoom> deptRoomCheckedList;
    /** 未选科室*/
    private List<BasicdataDeptRoom> deptRoomNoCheckList;

    public void setDeptroomId(Long deptroomId) {
        this.deptroomId = deptroomId;
    }

    public Long getDeptroomId() {
        return deptroomId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
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
            .append("deptroomId", getDeptroomId())
            .append("userId", getUserId())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
	}
}
