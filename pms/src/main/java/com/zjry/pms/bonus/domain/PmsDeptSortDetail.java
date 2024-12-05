package com.zjry.pms.bonus.domain;

import java.io.Serializable;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.pms.common.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 核算单位类别对象 pms_dept_sort_detail
 *
 * @author zjry
 * @date 2022-09-27
 */
@Data
@ApiModel(value = "PmsDeptSortDetail", description = "核算单位类别对象 pms_dept_sort_detail")
public class PmsDeptSortDetail extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String EDIT_ALLOW_NO = Constants.COMMON_NO;
    public static final String EDIT_ALLOW_YES = Constants.COMMON_YES;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /** 类别编码 */
    @Excel(name = "类别编码")
    @ApiModelProperty(value = "类别编码")
    private String sortCode;

    /** 类别名称 */
    @Excel(name = "类别名称")
    @ApiModelProperty(value = "类别名称")
    private String sortName;

    /** 删除标记(0未删除,1已删除) */
    @Excel(name = "删除标记(0未删除,1已删除)")
    @ApiModelProperty(value = "删除标记(0未删除,1已删除)")
    private String delFlag;

    /** 是否允许编辑(0否,1是) */
    private String editAllow;

    /** 单位编码 */
    @Excel(name = "单位编码")
    @ApiModelProperty(value = "单位编码")
    private Long compCode;

    /** 账套编码 */
    @Excel(name = "账套编码")
    @ApiModelProperty(value = "账套编码")
    private String accountCode;
}
