package com.zjry.pms.bonus.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 院级奖金核算结果对象 pms_bonus_y
 *
 * @author zjry
 * @date 2022-10-19
 */
@Data
@ApiModel(value = "PmsBonusY", description = "院级奖金核算结果对象 pms_bonus_y")
public class PmsBonusDto extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    @ApiModelProperty(value = "自增主键")
    private Long id;

    /** 奖金生成ID */
    @Excel(name = "奖金生成ID")
    @ApiModelProperty(value = "奖金生成ID")
    private Long bonusCalculationId;

    /** 考核周期（月份） */
    @JsonFormat(pattern = "yyyy-MM")
    @JSONField(format="yyyy-MM")
    @Excel(name = "考核周期", readConverterExp = "月=份")
    @ApiModelProperty(value = "考核周期（月份）")
    private Date period;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间")
    private Date dateBegin;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间")
    private Date dateEnd;

    /** 独立核算科室奖金 */
    @Excel(name = "独立核算科室奖金")
    @ApiModelProperty(value = "独立核算科室奖金")
    private BigDecimal bonusAlone;

    /** 平均奖科室奖金 */
    @Excel(name = "平均奖科室奖金")
    @ApiModelProperty(value = "平均奖科室奖金")
    private BigDecimal bonusAverage;

    /** 总奖金 */
    @Excel(name = "总奖金")
    @ApiModelProperty(value = "总奖金")
    private BigDecimal bonusTotal;

    /** 单位编码 */
    @Excel(name = "单位编码")
    @ApiModelProperty(value = "单位编码")
    private Long compCode;

    /** 账套编码 */
    @Excel(name = "账套编码")
    @ApiModelProperty(value = "账套编码")
    private String accountCode;

    /** 删除标记(0未删除,1已删除) */
    @Excel(name = "删除标记(0未删除,1已删除)")
    @ApiModelProperty(value = "删除标记(0未删除,1已删除)")
    private String delFlag;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
