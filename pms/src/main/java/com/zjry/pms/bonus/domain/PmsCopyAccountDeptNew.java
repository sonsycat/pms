package com.zjry.pms.bonus.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PmsCopyAccountDeptNew implements Serializable {
    private static final long serialVersionUID = 1L;


    /** 年月 */
    @ApiModelProperty(value = "年月")
    private Date nowDate;
    @ApiModelProperty(value = "年月")
    private Date copyDate;
    private Long compCode;

}
