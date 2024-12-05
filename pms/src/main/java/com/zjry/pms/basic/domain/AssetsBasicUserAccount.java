package com.zjry.pms.basic.domain;

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
 * 人员账套对应关系对象 user_account_relation
 *
 * @author maozt
 * @date 2022-03-18
 */
@Data
@ApiModel(value = "AssetsBasicUserAccount", description = "人员账套对应关系对象 user_account_relation")
public class AssetsBasicUserAccount extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 璐﹀ID */
    @Excel(name = "璐﹀ID")
    @ApiModelProperty(value = "璐﹀ID")
    private Long accountId;

    /** 璐﹀浣跨敤鏍囪瘑(0-鏈娇鐢?1-姝ｅ湪浣跨敤) */
    @Excel(name = "璐﹀浣跨敤鏍囪瘑(0-鏈娇鐢?1-姝ｅ湪浣跨敤)")
    @ApiModelProperty(value = "璐﹀浣跨敤鏍囪瘑(0-鏈娇鐢?1-姝ｅ湪浣跨敤)")
    private String useFlag;

    /** 鐢ㄦ埛ID */
    @Excel(name = "鐢ㄦ埛ID")
    @ApiModelProperty(value = "鐢ㄦ埛ID")
    private Long userId;

    /** 账套列表 */
    @ApiModelProperty(value = "账套列表")
    private List<AssetsBasicAccount> assetsBasicAccounts;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("accountId", getAccountId())
            .append("useFlag", getUseFlag())
            .append("userId", getUserId())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
	}
}
