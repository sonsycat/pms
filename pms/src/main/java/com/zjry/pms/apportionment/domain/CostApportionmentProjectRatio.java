package com.zjry.pms.apportionment.domain;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 分摊方案管理比例设置对象 cost_apportionment_project_ratio
 *
 * @author mk
 * @date 2022-05-13
 */
@Data
@ApiModel(value = "CostApportionmentProjectRatio", description = "分摊方案管理比例设置对象 cost_apportionment_project_ratio")
public class CostApportionmentProjectRatio extends BaseEntity implements Serializable {
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

    /** 分摊方案编码 */
    @Excel(name = "分摊方案编码")
    @ApiModelProperty(value = "分摊方案编码")
    private String projectCode;

    /** 分摊比例字典编码 */
    @Excel(name = "分摊比例字典编码")
    @ApiModelProperty(value = "分摊比例字典编码")
    private String ratioCode;

    /** 比例名称 */
    @Excel(name = "比例名称")
    @ApiModelProperty(value = "比例名称")
    private String ratioName;

    /** 计算计入设置比例 */
    @Excel(name = "计算计入设置比例")
    @ApiModelProperty(value = "计算计入设置比例")
    private String ratio;

    /** 一级设置比例 */
    @Excel(name = "一级设置比例")
    @ApiModelProperty(value = "一级设置比例")
    private String firstRatio;

    /** 二级设置比例 */
    @Excel(name = "二级设置比例")
    @ApiModelProperty(value = "二级设置比例")
    private String secondRatio;

    /** 三级设置比例 */
    @Excel(name = "三级设置比例")
    @ApiModelProperty(value = "三级设置比例")
    private String thirdRatio;

    /** 比例类型 */
    @Excel(name = "比例类型")
    @ApiModelProperty(value = "比例类型")
    private String ratioType;

    /** 明细 */
    @ApiModelProperty(value = "明细")
    List<CostApportionmentProjectRatio> costDeptInfos;



    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("compCode", getCompCode())
            .append("accountCode", getAccountCode())
            .append("projectCode", getProjectCode())
            .append("ratioCode", getRatioCode())
            .append("ratio", getRatio())
            .append("firstRatio", getFirstRatio())
            .append("secondRatio", getSecondRatio())
            .append("thirdRatio", getThirdRatio())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
