package com.zjry.pms.table.domain;

import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 分摊计算对象 cost_apport
 *
 * @author yzh
 * @date 2022-05-17
 */
@Data
public class PmsTableCostQuery extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "开始时间")
    private String startTime;
    @ApiModelProperty(value = "结束时间")
    private String endTime;
    @ApiModelProperty(value = "收费类型")
    private String feeType;
    @ApiModelProperty(value = "方式类型")
    private String balanceTag;
    @ApiModelProperty(value = "科室类别")
    private String deptType;

}
