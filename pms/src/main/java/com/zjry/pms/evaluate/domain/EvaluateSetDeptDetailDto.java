package com.zjry.pms.evaluate.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author llllwgd
 */
@Data
public class EvaluateSetDeptDetailDto {

    /**
     * 科室编码
     */
    private String deptRoomCode;

    /**
     * 指标集编码
     */
    private String setCode;

    /**
     * 指标编码
     */
    private String indexCode;

    /**
     * 降低量
     */
    private BigDecimal reduce;

    /**
     * 扣分
     */
    private BigDecimal deduct;

    /**
     * 增加量
     */
    private BigDecimal increase;

    /**
     * 加分
     */
    private BigDecimal extra;

    /**
     * 单位(%) 0:百分比,1:分值
     */
    private String unit;

    /**
     * 目标值
     */
    private BigDecimal target;

    /**
     * 指标分数
     */
    private BigDecimal indexScore;

    /**
     * 是否允许超分
     */
    private Boolean isHyper;

    /**
     * 是否允许负分
     */
    private Boolean isNegative;

    /**
     * 是否固定分值
     */
    private Boolean isFixed;

    /**
     * 阀值
     */
    private BigDecimal threshold;

    /**
     * 超分限制
     */
    private BigDecimal overLimit;

    /**
     * 减分限制
     */
    private BigDecimal deductionLimit;

    /**
     * 实际值
     */
    private BigDecimal realValue;

}
