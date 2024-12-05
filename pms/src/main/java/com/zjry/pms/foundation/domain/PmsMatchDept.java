package com.zjry.pms.foundation.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PmsMatchDept implements Serializable {
    @ApiModelProperty(value = "对应名称")
    private String matchDeptName;

}
