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
 * 指标集角色对象 pms_evaluate_set_role
 *
 * @author mk
 * @date 2022-08-05
 */
@Data
@ApiModel(value = "PmsEvaluateSetRole", description = "指标集角色对象 pms_evaluate_set_role")
public class PmsEvaluateSetRole extends BaseEntity implements Serializable {
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

    /** 指标集编码 */
    @Excel(name = "指标集编码")
    @ApiModelProperty(value = "指标集编码")
    private String setCode;

    /** 角色编码 */
    @Excel(name = "角色编码")
    @ApiModelProperty(value = "角色编码")
    private String indexName;
    private String indexN;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("compCode", getCompCode())
            .append("accountCode", getAccountCode())
            .append("setCode", getSetCode())
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
