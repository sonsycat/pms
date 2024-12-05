package com.zjry.pms.post.domain;

import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 岗位管理对象 hr_post_manage
 *
 * @author mk
 * @date 2021-07-23
 */
@Data
@ApiModel(value = "HrPostManage", description = "岗位管理对象 hr_post_manage")
public class HrPostManage extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "${comment}")
    private Long id;

    /** 岗位编码 */
    @Excel(name = "岗位编码")
    @ApiModelProperty(value = "岗位编码")
    private String postCode;

    /** 岗位名称 */
    @Excel(name = "岗位名称")
    @ApiModelProperty(value = "岗位名称")
    private String postName;

    /** 任职说明 */
    @Excel(name = "任职说明")
    @ApiModelProperty(value = "任职说明")
    private String postExplain;

    /** 职责说明 */
    @Excel(name = "职责说明")
    @ApiModelProperty(value = "职责说明")
    private String duty;

    /** 岗位简介 */
    @Excel(name = "岗位简介")
    @ApiModelProperty(value = "岗位简介")
    private String introduce;

    /** 备用字段 */
    @ApiModelProperty(value = "岗位简介")
    private String spare;

    /** 备用字段 */
    @ApiModelProperty(value = "岗位简介")
    private String spareOne;

    /** 岗位级别 1院级，2科级 */
    @ApiModelProperty(value = "岗位级别 1院级，2科级")
    private String postLevel;



    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostCode() {
        return postCode;
    }
    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostName() {
        return postName;
    }

    public String getPostExplain() {
        return postExplain;
    }

    public void setPostExplain(String postExplain) {
        this.postExplain = postExplain;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getDuty() {
        return duty;
    }
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getIntroduce() {
        return introduce;
    }
    public void setSpare(String spare) {
        this.spare = spare;
    }

    public String getSpare() {
        return spare;
    }
    public void setSpareOne(String spareOne) {
        this.spareOne = spareOne;
    }

    public String getSpareOne() {
        return spareOne;
    }



    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("postCode", getPostCode())
            .append("postName", getPostName())
            .append("duty", getDuty())
            .append("introduce", getIntroduce())
            .append("spare", getSpare())
            .append("spareOne", getSpareOne())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
