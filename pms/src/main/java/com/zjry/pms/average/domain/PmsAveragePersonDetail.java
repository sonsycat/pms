package com.zjry.pms.average.domain;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 平均奖科人数明细对象 pms_average_person_detail
 *
 * @author zwf
 * @date 2022-09-27
 */
@Data
@ApiModel(value = "PmsAveragePersonDetail", description = "平均奖科人数明细对象 pms_average_person_detail")
public class PmsAveragePersonDetail extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /** 平均奖科人数主表编码 */
    @ApiModelProperty(value = "平均奖科人数主表编码")
    private String avgCode;

    /** 科室编码 */
    @ApiModelProperty(value = "科室编码")
    private String deptCode;

    /** 科室名称 */
    @Excel(name = "科室名称")
    @ApiModelProperty(value = "科室名称")
    private String deptName;

    /** 医生编码 */
    @ApiModelProperty(value = "医生编码")
    private String docCode;

    /** 医生名称 */
    @Excel(name = "医生名称")
    @ApiModelProperty(value = "医生名称")
    private String docName;

    /** 是否发奖金（0：否1：是） */
    @Excel(name = "是否发奖金", readConverterExp = "0=：否1：是")
    @ApiModelProperty(value = "是否发奖金（0：否1：是）")
    private String isReward;

    /** 工作日数 */
    @Excel(name = "工作日数")
    @ApiModelProperty(value = "工作日数")
    private String workDays;

    /** 岗位系数 */
    @Excel(name = "岗位系数")
    @ApiModelProperty(value = "岗位系数")
    private String postRatio;

    /** 责任奖系数 */
    @Excel(name = "责任奖系数")
    @ApiModelProperty(value = "责任奖系数")
    private String dutyRewardRatio;

    /** 删除标记(0未删除,1已删除) */
    @ApiModelProperty(value = "删除标记(0未删除,1已删除)")
    private String delFlag;

    private Long avgId;
    private String depCode;
    private String depName;
    private String copyCode;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setAvgCode(String avgCode) {
        this.avgCode = avgCode;
    }

    public String getAvgCode() {
        return avgCode;
    }
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptCode() {
        return deptCode;
    }
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptName() {
        return deptName;
    }
    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    public String getDocCode() {
        return docCode;
    }
    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocName() {
        return docName;
    }
    public void setIsReward(String isReward) {
        this.isReward = isReward;
    }

    public String getIsReward() {
        return isReward;
    }
    public void setWorkDays(String workDays) {
        this.workDays = workDays;
    }

    public String getWorkDays() {
        return workDays;
    }
    public void setPostRatio(String postRatio) {
        this.postRatio = postRatio;
    }

    public String getPostRatio() {
        return postRatio;
    }
    public void setDutyRewardRatio(String dutyRewardRatio) {
        this.dutyRewardRatio = dutyRewardRatio;
    }

    public String getDutyRewardRatio() {
        return dutyRewardRatio;
    }
    @Override
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("avgCode", getAvgCode())
            .append("deptCode", getDeptCode())
            .append("deptName", getDeptName())
            .append("docCode", getDocCode())
            .append("docName", getDocName())
            .append("isReward", getIsReward())
            .append("workDays", getWorkDays())
            .append("postRatio", getPostRatio())
            .append("dutyRewardRatio", getDutyRewardRatio())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
                .append("depCode", getDepCode())
                .append("depName", getDepName())
                .append("avgId", getAvgId())
            .toString();
	}
}
