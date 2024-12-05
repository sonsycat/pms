package com.zjry.pms.evaluate.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zwf
 */
@Data
public class PmsEvaluateDataAccountDept {
    private String deptRoomId;
    private String deptRoomCode;
    private String deptRoomName;
    private String guideCode;
    private BigDecimal guideValue;
    private BigDecimal guideValueResult;
    private String indexName;
    private String docName;
    private String docCode;
    private String remark;
    private String actDate;
}
