package com.zjry.pms.table.domain;

import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class PmsTableHsdeptRes extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "核算科目")
    @Excel(name = "核算科目")
    private String className;
    @ApiModelProperty(value = "项目名称")
    @Excel(name = "项目名称")
    private String itemName;
    @ApiModelProperty(value = "总收入")
    @Excel(name = "总收入")
    private String accountincomes;
    @ApiModelProperty(value = "实际收入小计")
    @Excel(name = "实际收入小计")
    private String facaccountincomes;
    @ApiModelProperty(value = "实际收入门诊")
    @Excel(name = "实际收入门诊")
    private String armaccountincomes;
    @ApiModelProperty(value = "实际收入住院")
    @Excel(name = "实际收入住院")
    private String hrealincomes;
    @ApiModelProperty(value = "计价收入小计")
    @Excel(name = "计价收入小计")
    private String hvalationincomes;
    @ApiModelProperty(value = "计价收入门诊")
    @Excel(name = "计价收入门诊")
    private String crealincomes;
    @ApiModelProperty(value = "计价收入住院")
    @Excel(name = "计价收入住院")
    private String cvalationincomes;





}
