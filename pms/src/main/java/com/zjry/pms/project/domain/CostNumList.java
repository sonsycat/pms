package com.zjry.pms.project.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zjry.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author mk
 * @title: CostNumList
 * @projectName zjry-cost
 * @date 2022/5/1816:53
 */
@Data
@ApiModel(value = "CostNumList", description = "成本录入多条")
public class CostNumList implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 科室编码 */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private Long deptCode;
    /** 年月 */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "年月", width = 30, dateFormat = "yyyy-MM")
    @ApiModelProperty(value = "年月")
    private Date nowDate;

    private List<CostProjectNum> costNumList;
}
