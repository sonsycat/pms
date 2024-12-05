package com.zjry.pms.apportionment.domain;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 多源数据库设置对象 cost_database
 *
 * @author yzh
 * @date 2022-05-12
 */
@Data
@ApiModel(value = "CostDatabase", description = "多源数据库设置对象 cost_database")
public class CostDatabase extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /** 编码 */
    @Excel(name = "编码")
    @ApiModelProperty(value = "编码")
    private String code;

    /** 名称 */
    @Excel(name = "名称")
    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不为空")
    private String name;

    /** 类型 */
    @Excel(name = "类型")
    @ApiModelProperty(value = "类型")
    @NotBlank(message = "类型不为空")
    private String type;

    /** url */
    @Excel(name = "url")
    @ApiModelProperty(value = "url")
    @NotBlank(message = "url不为空")
    private String url;

    /** 用户名 */
    @Excel(name = "用户名")
    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不为空")
    private String userName;

    /** 密码 */
    @Excel(name = "密码")
    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不为空")
    private String passWord;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPassWord() {
        return passWord;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("code", getCode())
            .append("name", getName())
            .append("type", getType())
            .append("url", getUrl())
            .append("userName", getUserName())
            .append("passWord", getPassWord())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
