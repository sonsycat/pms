package com.zjry.pms.bonus.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PmsStatementLabourType implements Serializable {
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    @ApiModelProperty(value = "编码")
    private String labourTypeCode;

    @ApiModelProperty(value = "名称")
    private String labourTypeName;

}
