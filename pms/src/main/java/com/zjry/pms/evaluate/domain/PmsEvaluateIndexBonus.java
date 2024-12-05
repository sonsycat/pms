package com.zjry.pms.evaluate.domain;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 奖金指标对象 pms_evaluate_index_bonus
 *
 * @author zjry
 * @date 2022-11-08
 */
@Data
@ApiModel(value = "PmsEvaluateIndexBonus", description = "奖金指标对象 pms_evaluate_index_bonus")
public class PmsEvaluateIndexBonus extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /** 单位编码 */
    @Excel(name = "单位编码")
    @ApiModelProperty(value = "单位编码")
    private Long compCode;

    /** 账套编码 */
    @Excel(name = "账套编码")
    @ApiModelProperty(value = "账套编码")
    private String accountCode;

    /** 科室类别(字典JXGL_JJ_KSLB) */
    @Excel(name = "科室类别")
    @ApiModelProperty(value = "科室类别")
    private String deptRoomType;

    /** 指标编码 */
    @Excel(name = "指标编码")
    @ApiModelProperty(value = "指标编码")
    private String indexCode;

    /** 删除标记(0未删除,1已删除) */
    @Excel(name = "删除标记(0未删除,1已删除)")
    @ApiModelProperty(value = "删除标记(0未删除,1已删除)")
    private String delFlag;

    /** 适用对象(1-院；1-科；3-人) */
    private String orgType;
    /** 指标名称 */
    private String indexName;
    /** 部门分类 */
    private String deptType;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("compCode", getCompCode())
            .append("accountCode", getAccountCode())
            .append("deptRoomType", getDeptRoomType())
            .append("orgType", getOrgType())
            .append("indexCode", getIndexCode())
            .append("indexName", getIndexName())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
