package com.zjry.pms.evaluate.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author mk
 * @title: PmsEvaSetDeptDetailList
 * @projectName zjry-pms
 * @date 2022/8/916:53
 */
@Data
public class PmsEvaSetDeptDetailList {
    private Long compCode;
    private String setDeptCode;
    private Date nowDate;
    private List<PmsEvaluateSetDeptDetail> listPms;
}
