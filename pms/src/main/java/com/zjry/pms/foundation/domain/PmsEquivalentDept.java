package com.zjry.pms.foundation.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PmsEquivalentDept implements Serializable {
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    @ApiModelProperty(value = "科室编码")
    private String equivalentDeptCode;

    @ApiModelProperty(value = "科室名称")
    private String equivalentDeptName;

}
