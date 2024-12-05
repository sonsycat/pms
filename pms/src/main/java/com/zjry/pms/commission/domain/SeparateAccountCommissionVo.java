package com.zjry.pms.commission.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SeparateAccountCommissionVo {

    private String deptRoomCode;

    private String deptRoomName;

    private String sortCode;

    private BigDecimal ratio;

    private String sortName;

}
