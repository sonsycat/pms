package com.zjry.pms.basic.domain;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * HIS收入类别对象 pms_his_income
 *
 * @author maozt
 * @date 2022-06-16
 */
@Data
@ApiModel(value = "PmsHisIncome", description = "HIS收入类别对象 pms_his_income")
public class PmsHisIncome extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /** 单位编码 */
    @ApiModelProperty(value = "单位编码")
    private Long compCode;

    /** 账套编码 */
    @ApiModelProperty(value = "账套编码")
    private String accountCode;

    /** 收入类别编码 */
    @ApiModelProperty(value = "收入类别编码")
    private String revenueCode;

    /** 收入类别名称 */
    @Excel(name = "收入类别名称")
    @ApiModelProperty(value = "收入类别名称")
    private String revenueName;

    /** 拼音码 */
    @ApiModelProperty(value = "拼音码")
    private String spell;

}
