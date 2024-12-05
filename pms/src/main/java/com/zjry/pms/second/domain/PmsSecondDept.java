package com.zjry.pms.second.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 二次分配科室指标对象 pms_second_dept
 *
 * @author maozt
 * @date 2022-09-27
 */
@Data
@ApiModel(value = "PmsSecondDept", description = "二次分配科室指标对象 pms_second_dept")
public class PmsSecondDept extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
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

    /** 核算科室编码 */
    @Excel(name = "核算科室编码")
    @ApiModelProperty(value = "核算科室编码")
    private String deptCode;

    /** 核算科室编码 */
    @Excel(name = "核算科室编码")
    @ApiModelProperty(value = "核算科室编码")
    private String deptName;

    /** 指标集编码 */
    @Excel(name = "指标集编码")
    @ApiModelProperty(value = "指标集编码")
    private String disCode;

    /** 年月 */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "年月", width = 30, dateFormat = "yyyy-MM")
    @ApiModelProperty(value = "年月")
    private Date nowDate;

    private List<PmsSecondDept> deptList;

}
