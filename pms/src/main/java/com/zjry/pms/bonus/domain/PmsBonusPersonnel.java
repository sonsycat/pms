package com.zjry.pms.bonus.domain;

import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
@ApiModel(value = "PmsBonusPersonnel", description = "pms_bonus_personnel")
public class PmsBonusPersonnel extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /** 奖金月份 */
    @ApiModelProperty(value = "奖金月份")
    @NotBlank(message = "奖金月份不能为空")
    private String personnelDate;
    private Date personnelTime;

    /** 科室名称 */
    @Excel(name = "科室名称")
    @ApiModelProperty(value = "科室名称")
    @NotBlank(message = "科室名称不能为空")
    private String deptName;
    @Excel(name = "员工ID")
    @ApiModelProperty(value = "员工ID")
    @NotBlank(message = "员工ID不为空")
    private String personnelId;
    @Excel(name = "员工姓名")
    @ApiModelProperty(value = "员工姓名")
    @NotBlank(message = "员工姓名不能为空")
    private String personnelName;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String remark;
}
