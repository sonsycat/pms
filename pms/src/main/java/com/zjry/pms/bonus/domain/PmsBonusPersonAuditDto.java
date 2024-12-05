package com.zjry.pms.bonus.domain;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 奖金人员审核对象 pms_bonus_person_audit
 *
 * @author zjry
 * @date 2022-10-25
 */
@Data
@ApiModel(value = "PmsBonusPersonAudit", description = "奖金人员审核对象 pms_bonus_person_audit")
public class PmsBonusPersonAuditDto extends BaseEntity implements Serializable {

    /**
     * 审核信息
     */
    private PmsBonusPersonAudit audit;

    /**
     * 人员信息
     */
    private JSONArray data;

    /**
     * 是否提交
     */
    private Boolean isCommit = false;

    private String procedureId;

    private Long id;

    private String comment;

    private String assignee;

    private String auditState;

}
