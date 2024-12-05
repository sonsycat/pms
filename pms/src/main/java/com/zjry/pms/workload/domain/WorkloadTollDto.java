package com.zjry.pms.workload.domain;

import lombok.Data;

import java.util.List;

@Data
public class WorkloadTollDto {

    /**
     * 工作量编码
     */
    private String projectCode;

    /**
     * 类型,1已选,2未选
     */
    private String type;

    /**
     * 收费项目名称
     */
    private String hisName;

    /**
     * 名称
     */
    private List<String> existCode;

    private List<PmsWorkloadToll> list;

}
