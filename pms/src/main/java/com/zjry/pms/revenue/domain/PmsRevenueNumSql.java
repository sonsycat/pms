package com.zjry.pms.revenue.domain;

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
 * 收入sql对象 pms_revenue_num_sql
 *
 * @author mk
 * @date 2022-06-23
 */
@Data
@ApiModel(value = "PmsRevenueNumSql", description = "收入sql对象 pms_revenue_num_sql")
public class PmsRevenueNumSql extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /** 单位编码 */
    @Excel(name = "单位编码")
    @ApiModelProperty(value = "单位编码")
    private Long compCode;

    /** 账套编码 */
    @Excel(name = "账套编码")
    @ApiModelProperty(value = "账套编码")
    private String accountCode;

    /** 获取收入sql */
    @Excel(name = "获取收入sql")
    @ApiModelProperty(value = "获取收入sql")
    private String numSql;

    /** sql类型 1sql，2过程 */
    @Excel(name = "sql类型 1sql，2过程")
    @ApiModelProperty(value = "sql类型 1sql，2过程")
    private String sqlType;


    private String coonectCode;

    /** 年月 */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "年月", width = 30, dateFormat = "yyyy-MM")
    @ApiModelProperty(value = "年月")
    private Date nowDate;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("compCode", getCompCode())
            .append("accountCode", getAccountCode())
            .append("numSql", getNumSql())
            .append("sqlType", getSqlType())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
