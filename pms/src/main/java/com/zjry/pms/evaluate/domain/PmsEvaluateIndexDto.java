package com.zjry.pms.evaluate.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 二次核算考核人员对应指标结果集
 */
@Data
public class PmsEvaluateIndexDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 医生编码 */
    private String docCode;
    /** 科室编码 */
    private String deptCode;
    /** 指标集编码 */
    private String setCode;
    /** 指标编码 */
    private String indexCode;
    /** 指标权重 */
    private String weight;
    /** 指标权重 */
    private BigDecimal weightRatio;
}
