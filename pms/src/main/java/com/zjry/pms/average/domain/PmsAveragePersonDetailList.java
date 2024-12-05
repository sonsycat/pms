package com.zjry.pms.average.domain;

import lombok.Data;

import java.util.List;

/**
 * @author user
 */
@Data
public class PmsAveragePersonDetailList {
    private Long avgId;
    private List<PmsAveragePersonDetail> data;
}
