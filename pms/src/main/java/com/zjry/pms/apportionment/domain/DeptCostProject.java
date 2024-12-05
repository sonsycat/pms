package com.zjry.pms.apportionment.domain;

import com.zjry.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author mk
 * @title: DeptCostProject
 * @projectName zjry-cost
 * @date 2022/5/1310:47
 */
@Data
public class DeptCostProject implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /** 单位编码 */
    @Excel(name = "单位编码")
    @ApiModelProperty(value = "单位编码")
    private Long compCode;

    /** 账套编码 */
    @Excel(name = "账套编码")
    @ApiModelProperty(value = "账套编码")
    private String accountCode;
    /** 分摊方案编码 */
    @Excel(name = "分摊方案编码")
    @ApiModelProperty(value = "分摊方案编码")
    private String projectCode;

    private List<CostApportionmentProjectDept> deptList;
}
