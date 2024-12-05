package com.zjry.pms.bonus.domain;

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
 * 奖金核算对象 pms_bonus_calculation
 *
 * @author zjry
 * @date 2022-10-19
 */
@Data
@ApiModel(value = "PmsBonusCalculation", description = "奖金核算对象 pms_bonus_calculation")
public class PmsBonusCalculation extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @ApiModelProperty(value = "自增主键")
    private Long id;

    /**
     * 奖金名称
     */
    @Excel(name = "奖金名称")
    @ApiModelProperty(value = "奖金名称")
    private String name;

    /**
     * 奖金年月
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "奖金年月", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "奖金年月")
    private Date period;

    /**
     * 审核状态
     */
    @Excel(name = "审核状态")
    @ApiModelProperty(value = "审核状态")
    private String auditState;

    /**
     * 归档状态(是否归档：0是；1否)
     */
    @Excel(name = "归档状态")
    @ApiModelProperty(value = "归档状态")
    private String archiveState;

    /** 单位编码 */
    @ApiModelProperty(value = "单位编码")
    private Long compCode;

    /** 账套编码 */
    @ApiModelProperty(value = "账套编码")
    private String accountCode;

    /**
     * 删除标记(0未删除,1已删除)
     */
    @ApiModelProperty(value = "删除标记(0未删除,1已删除)")
    private String delFlag;

    /**
     * 审核流程id
     */
    private String procedureId;

    /**
     * 审核意见
     */
    private String comment;

    /**
     * 下级审核人
     */
    private String assignee;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("period", getPeriod())
                .append("auditState", getAuditState())
                .append("archiveState", getArchiveState())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
