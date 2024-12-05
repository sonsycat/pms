package com.zjry.pms.common.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 测试用
 *
 * @author mk
 * @date 2021-06-24
 */
@Data
public class CheckInfo {
    private Long id;
    /**审核状态*/
    private String state;
    /**审核日期*/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date checkDate;
     /**审核意见*/
    private String stateOpinion;

    //流程ID
    private String proclnsId;
    //审核意见
    private String comment;

}
