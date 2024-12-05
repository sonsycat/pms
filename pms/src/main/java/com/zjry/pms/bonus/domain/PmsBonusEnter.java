package com.zjry.pms.bonus.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 *
 *
 * @author zjry
 * @date 2022-09-29
 */
@Data
@ApiModel(value = "PmsBonusEnter", description = "pms_bonus_enter")
public class PmsBonusEnter extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /** 奖金月份 */
    @ApiModelProperty(value = "奖金月份")
    @NotBlank(message = "奖金月份不能为空")
    private String enterDate;
    private Date enterTime;

    /** 科室名称 */
    @Excel(name = "科室名称")
    @ApiModelProperty(value = "科室名称")
    @NotBlank(message = "科室名称不能为空")
    private String deptName;
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    @NotBlank(message = "科室编码不能为空")
    private String deptCode;
    @Excel(name = "员工姓名")
    @ApiModelProperty(value = "员工姓名")
    @NotBlank(message = "员工姓名不能为空")
    private String userName;
    /** 奖金绩效 */
    @Excel(name = "奖金绩效")
    @ApiModelProperty(value = "奖金绩效")
    @NotBlank(message = "奖金绩效不为空")
    private String achievement;
    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String remark;
}
