package com.zjry.pms.bonus.domain;

import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PmsAccountDeptNew extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /** 科室编码 */
    @ApiModelProperty(value = "科室编码")
    private String deptRoomCode;

    /** 科室名称 */
    @ApiModelProperty(value = "科室名称")
    private String deptRoomName;
    @ApiModelProperty(value = "核算类别")
    private String accountType;
    /** 年月 */
    @ApiModelProperty(value = "年月")
    private Date nowDate;
    /** 单位编码 */
    @ApiModelProperty(value = "单位编码")
    private Long compCode;
    /** 删除标记(0未删除,1已删除) */
    @ApiModelProperty(value = "删除标记(0未删除,1已删除)")
    private String delFlag;

}
