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
 * 人员账套对应关系对象 user_account_relation
 *
 * @author cyq
 * @date 2021-07-22
 */
@Data
@ApiModel(value = "UserAccountRelation", description = "人员账套对应关系对象 user_account_relation")
public class UserAccountRelation extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 账套ID */
    @Excel(name = "账套ID")
    @ApiModelProperty(value = "账套ID")
    private Long accountId;

    /** 用户ID */
    @Excel(name = "用户ID")
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    /** 删除标识(0-正常；1-删除) */
    @ApiModelProperty(value = "用户ID")
    private String delFlag;
    private Long[] userIds;
    private String useFlag;

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
    @Override
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("accountId", getAccountId())
            .append("userId", getUserId())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
	}
}
