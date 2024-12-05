package com.zjry.pms.revenue.domain;

import com.zjry.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author mk
 * @title: PmRevDept
 * @projectName zjry-pms
 * 2022/6/2114:54
 */
@Data
public class PmRevDept  implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 单位编码 */
    @Excel(name = "单位编码")
    @ApiModelProperty(value = "单位编码")
    private Long compCode;
    /** 项目编码 */
    @Excel(name = "项目编码")
    @ApiModelProperty(value = "项目编码")
    private String revenueCode;

    private List<PmsRevenueDept> pmsRevenueDepts;

}
