package com.zjry.pms.foundation.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PmsEquivalentItem implements Serializable {
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    @ApiModelProperty(value = "项目编码")
    private String equivalentItemCode;

    @ApiModelProperty(value = "项目名称")
    private String equivalentItemName;

}
