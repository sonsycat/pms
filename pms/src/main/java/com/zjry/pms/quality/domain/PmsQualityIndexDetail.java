package com.zjry.pms.quality.domain;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 质量考评指标明细对象 pms_quality_index_detail
 *
 * @author yzh
 * @date 2022-07-05
 */
@Data
@ApiModel(value = "PmsQualityIndexDetail", description = "质量考评指标明细对象 pms_quality_index_detail")
public class PmsQualityIndexDetail extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /** 指标编码 */
    @Excel(name = "指标编码")
    @ApiModelProperty(value = "指标编码")
    private String indexCode;
    
    /** 主管部门 */
    @Excel(name = "主管部门")
    @ApiModelProperty(value = "主管部门")
    private Long deptCode;
    
    /** 主管部门名称 */
    @Excel(name = "主管部门名称")
    @ApiModelProperty(value = "主管部门名称")
    private String deptName;

    /** 指标名称 */
    @Excel(name = "指标名称")
    @ApiModelProperty(value = "指标名称")
    private String indexName;

    /** 指标分值 */
    @Excel(name = "指标分值")
    @ApiModelProperty(value = "指标分值")
    private String indexScore;

    /** 考评内容 */
    @Excel(name = "考评内容")
    @ApiModelProperty(value = "考评内容")
    private String evaCont;

    /** 考评标准 */
    @Excel(name = "考评标准")
    @ApiModelProperty(value = "考评标准")
    private String evaCriteria;

    /** 考评办法 */
    @Excel(name = "考评办法")
    @ApiModelProperty(value = "考评办法")
    private String evaMethod;

    /** 是否KPI */
    @Excel(name = "是否KPI")
    @ApiModelProperty(value = "是否KPI")
    private String isKpi;

    /** 删除标记(0未删除,1已删除) */
    @ApiModelProperty(value = "删除标记(0未删除,1已删除)")
    private String delFlag;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public String getIndexCode() {
        return indexCode;
    }
    public void setEvaCont(String evaCont) {
        this.evaCont = evaCont;
    }

    public String getEvaCont() {
        return evaCont;
    }
    public void setEvaCriteria(String evaCriteria) {
        this.evaCriteria = evaCriteria;
    }

    public String getEvaCriteria() {
        return evaCriteria;
    }
    public void setEvaMethod(String evaMethod) {
        this.evaMethod = evaMethod;
    }

    public String getEvaMethod() {
        return evaMethod;
    }
    public void setIsKpi(String isKpi) {
        this.isKpi = isKpi;
    }

    public String getIsKpi() {
        return isKpi;
    }
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("indexCode", getIndexCode())
            .append("evaCont", getEvaCont())
            .append("evaCriteria", getEvaCriteria())
            .append("evaMethod", getEvaMethod())
            .append("isKpi", getIsKpi())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
