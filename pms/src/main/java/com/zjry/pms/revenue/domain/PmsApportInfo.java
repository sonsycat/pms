package com.zjry.pms.revenue.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author mk
 * @title: PmsApportInfo
 * @projectName zjry-pms
 * @date 2022/6/3014:31
 */
@Data
public class PmsApportInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private BigDecimal value;
}
