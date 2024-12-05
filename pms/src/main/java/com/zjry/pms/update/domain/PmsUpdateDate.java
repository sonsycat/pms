package com.zjry.pms.update.domain;

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
 * 更新日期对象 pms_update_date
 *
 * @author maozt
 * @date 2022-07-06
 */
@Data
@ApiModel(value = "PmsUpdateDate", description = "更新日期对象 pms_update_date")
public class PmsUpdateDate extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /** 单位编码 */
    @Excel(name = "单位编码")
    @ApiModelProperty(value = "单位编码")
    private Long compCode;

    /** 账套编码 */
    @ApiModelProperty(value = "账套编码")
    private String accountCode;

    /** 编码 */
    @Excel(name = "编码")
    @ApiModelProperty(value = "编码")
    private String revenueCode;

    /** 调整类型 */
    @Excel(name = "调整类型")
    @ApiModelProperty(value = "调整类型")
    private String revenueName;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd hh:mm:ss")
    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd hh:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    /** 目标年月 */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "目标年月", width = 30, dateFormat = "yyyy-MM")
    @ApiModelProperty(value = "目标年月")
    private Date targetMonth;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("compCode", getCompCode())
            .append("accountCode", getAccountCode())
            .append("revenueCode", getRevenueCode())
            .append("revenueName", getRevenueName())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("targetMonth", getTargetMonth())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
