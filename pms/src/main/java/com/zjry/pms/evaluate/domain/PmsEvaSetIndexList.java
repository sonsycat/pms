package com.zjry.pms.evaluate.domain;

import lombok.Data;

import java.util.List;

/**
 * @author mk
 * @title: PmsEvaSetIn
 * @projectName zjry-pms
 * @date 2022/8/416:20
 */
@Data
public class PmsEvaSetIndexList {
    private Long compCode;
    private String setCode;
    private String deptType;
    private List<PmsEvaluateSetIndex> pmsEvaluateSetIndexList;
}
