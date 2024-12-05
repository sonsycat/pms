package com.zjry.pms.common.domain;

import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 流水号对象 sys_flowcode
 *
 * @author zjrj
 * @date 2021-06-22
 */
@Data
@ApiModel(value = "SysFlowcode", description = "流水号对象 sys_flowcode")
public class SysFlowHrcode extends BaseEntity {
private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "${comment}")
    private Long id;

    /** $column.columnComment */
    @ApiModelProperty(value = "${comment}")
    private String name;

    /** $column.columnComment */
    @ApiModelProperty(value = "${comment}")
    private String code;

    /** $column.columnComment */
    @ApiModelProperty(value = "${comment}")
    private String status;

    /** $column.columnComment */
    @ApiModelProperty(value = "${comment}")
    private Long curVal;

    /** $column.columnComment */
    @ApiModelProperty(value = "${comment}")
    private Long codeLen;



    /** $column.columnComment */
    @ApiModelProperty(value = "${comment}")
    private String prefix;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCurVal() {
        return curVal;
    }

    public void setCurVal(Long curVal) {
        this.curVal = curVal;
    }

    public Long getCodeLen() {
        return codeLen;
    }

    public void setCodeLen(Long codeLen) {
        this.codeLen = codeLen;
    }


    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                                .append("id", getId())
                                .append("name", getName())
                                .append("code", getCode())
                                .append("status", getStatus())
                                .append("curVal", getCurVal())
                                .append("codeLen", getCodeLen())
                                .append("createBy", getCreateBy())
                                .append("updateBy", getUpdateBy())
                                .append("createDate", getCreateTime())
                                .append("updateDate", getUpdateTime())
                                .append("delFlag", getDelFlag())
                                .append("prefix", getPrefix())
                                .toString();
        }
}
