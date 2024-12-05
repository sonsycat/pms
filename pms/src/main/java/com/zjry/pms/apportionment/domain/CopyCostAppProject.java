package com.zjry.pms.apportionment.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zjry.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author mk
 * @title: CopyCostAppProject
 * @projectName zjry-cost
 * @date 2022/5/1711:12
 */
@Data
public class CopyCostAppProject implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 当前年月 */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "当前年月", width = 30, dateFormat = "yyyy-MM")
    @ApiModelProperty(value = "当前年月")
    private Date nowDate;

    private List<CostApportionmentProject> costApportionmentProjectList;

}
