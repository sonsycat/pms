package com.zjry.pms.system.domain;

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
@ApiModel(value = "SystemStaff", description = "system_staff")
public class PmsSystemStaff extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;
    @Excel(name = "员工编码")
    @ApiModelProperty(value = "员工编码")
    private String staffCode;
    @Excel(name = "员工姓名")
    @ApiModelProperty(value = "员工姓名")
    private String staffName;
    private String orgCode;
    private String orgName;
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private String deptCode;
    @Excel(name = "科室名称")
    @ApiModelProperty(value = "科室名称")
    private String deptName;
    @Excel(name = "性别")
    @ApiModelProperty(value = "性别")
    private String sex;
    @Excel(name = "证件类别")
    @ApiModelProperty(value = "证件类别")
    private String cardType;
    @Excel(name = "证件号码")
    @ApiModelProperty(value = "证件号码")
    private String cardNo;
    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String remark;
}
