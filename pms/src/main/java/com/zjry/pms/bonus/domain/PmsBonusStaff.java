package com.zjry.pms.bonus.domain;

import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 核算科室设置对象 pms_account_dept
 *
 * @author zjry
 * @date 2022-09-29
 */
@Data
public class PmsBonusStaff extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    @Excel(name = "员工编码")
    private String staffCode;
    @Excel(name = "员工姓名")
    private String staffName;
    @Excel(name = "科室编码")
    private String deptCode;
    @Excel(name = "科室名称")
    private String deptName;
    @Excel(name = "奖金金额")
    private BigDecimal bonusAmount;
    @Excel(name = "奖金日期")
    private String actDate;
}
