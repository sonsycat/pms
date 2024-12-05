package com.zjry.pms.evaluate.domain;

import lombok.Data;

import java.util.List;

/**
 * @author mk
 * @title: PmsEvaRleList
 * @projectName zjry-pms
 * @date 2022/8/514:25
 */
@Data
public class PmsEvaRleList {
    private Long compCode;
    private String setCode;
    private List<PmsEvaluateSetRole> listPms;
}
