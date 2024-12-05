package com.zjry.pms.bonus.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 核算单位配置列表数据
 */
@Data
public class PmsDeptSortDto {

    private Long compCode;
    private String accountCode;
    private Date nowDate;
    private List<PmsDeptSort> data;
}
