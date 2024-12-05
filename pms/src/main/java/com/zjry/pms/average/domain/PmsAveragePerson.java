package com.zjry.pms.average.domain;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 平均奖科人数对象 pms_average_person
 *
 * @author zwf
 * @date 2022-09-27
 */
@Data
@ApiModel(value = "PmsAveragePerson", description = "平均奖科人数对象 pms_average_person")
public class PmsAveragePerson extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /** 设置年月 */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "设置年月", width = 30, dateFormat = "yyyy-MM")
    @ApiModelProperty(value = "设置年月")
    private Date setDate;

    /** 录入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "录入时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "录入时间")
    private Date setTime;

    /** 录入人 */
    @Excel(name = "录入人")
    @ApiModelProperty(value = "录入人")
    private String setPerson;

    /** 删除标记(0未删除,1已删除) */
    @ApiModelProperty(value = "删除标记(0未删除,1已删除)")
    private String delFlag;

    /** 平均奖科人数编码 */
    @ApiModelProperty(value = "平均奖科人数编码")
    private String code;

    /** 单位编码 */
    @ApiModelProperty(value = "单位编码")
    private Long compCode;

    /** 账套编码 */
    @ApiModelProperty(value = "账套编码")
    private String accountCode;

    private Date copyDate;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setSetDate(Date setDate) {
        this.setDate = setDate;
    }

    public Date getSetDate() {
        return setDate;
    }
    public void setSetTime(Date setTime) {
        this.setTime = setTime;
    }

    public Date getSetTime() {
        return setTime;
    }
    public void setSetPerson(String setPerson) {
        this.setPerson = setPerson;
    }

    public String getSetPerson() {
        return setPerson;
    }
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
    public void setCompCode(Long compCode) {
        this.compCode = compCode;
    }

    public Long getCompCode() {
        return compCode;
    }
    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountCode() {
        return accountCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("setDate", getSetDate())
            .append("setTime", getSetTime())
            .append("setPerson", getSetPerson())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("code", getCode())
            .append("compCode", getCompCode())
            .append("accountCode", getAccountCode())
            .toString();
	}
}
