package com.zjry.pms.bonus.domain;

import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 奖金核算对象 pms_bonus_calculation
 *
 * @author zjry
 * @date 2022-10-19
 */
@Data
public class PmsBonusCalculationHis extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @ApiModelProperty(value = "自增主键")
    private Long id;
    private String procedureId;
    private Long calculationId;
    private Long commitId;
    private Date commitDate;
    private Long auditId;
    private Date auditDate;
    private Long archiveId;
    private Date archiveDate;
    private String remark;
    private Date creatDate;
    private String hisState;

}
