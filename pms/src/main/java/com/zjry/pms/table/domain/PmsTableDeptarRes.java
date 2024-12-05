package com.zjry.pms.table.domain;

import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class PmsTableDeptarRes extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "科室名称")
    @Excel(name = "科室名称")
    private String deptName;
    @ApiModelProperty(value = "科室编码")
    @Excel(name = "科室编码")
    private String deptCode;
    @ApiModelProperty(value = "总收入")
    @Excel(name = "总收入")
    private String hj;
    @ApiModelProperty(value = "住院小计")
    @Excel(name = "住院小计")
    private String zyhj;
    @ApiModelProperty(value = "住院实际收入")
    @Excel(name = "住院实际收入")
    private String zyss;
    @ApiModelProperty(value = "住院计价收入")
    @Excel(name = "住院计件收入")
    private String zyjd;
    @ApiModelProperty(value = "门诊小计")
    @Excel(name = "门诊小计")
    private String mzhj;
    @ApiModelProperty(value = "门诊实际收入")
    @Excel(name = "门诊实际收入")
    private String mzss;
    @ApiModelProperty(value = "门诊计价收入")
    @Excel(name = "门诊计件收入")
    private String mzjd;





}
