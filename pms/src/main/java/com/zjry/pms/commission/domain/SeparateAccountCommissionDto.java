package com.zjry.pms.commission.domain;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.util.Date;

@Data
public class SeparateAccountCommissionDto {

    private Date commissionDate;

    private JSONArray data;

    private Date nowDate;

    private Date copyDate;

}
