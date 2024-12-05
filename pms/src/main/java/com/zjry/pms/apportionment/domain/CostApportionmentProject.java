package com.zjry.pms.apportionment.domain;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 分摊方案管理对象 cost_apportionment_project
 *
 * @author mk
 * @date 2022-05-12
 */
@Data
@ApiModel(value = "CostApportionmentProject", description = "分摊方案管理对象 cost_apportionment_project")
public class CostApportionmentProject extends BaseEntity implements Serializable {
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

    /** 分摊方案名称 */
    @Excel(name = "分摊方案名称")
    @ApiModelProperty(value = "分摊方案名称")
    private String projectName;

    /** 当前年月 */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "当前年月", width = 30, dateFormat = "yyyy-MM")
    @ApiModelProperty(value = "当前年月")
    private Date nowDate;

    /** 拼音码 */
    @Excel(name = "拼音码")
    @ApiModelProperty(value = "拼音码")
    private String spell;

    /** 方案类型 */
    @Excel(name = "方案类型")
    @ApiModelProperty(value = "方案类型")
    private String projectType;

    /** 设置比例中文 */
    @Excel(name = "设置比例中文")
    @ApiModelProperty(value = "设置比例中文")
    private String rationShow;

    /** 设置比例编码 */
    @Excel(name = "设置比例编码")
    @ApiModelProperty(value = "设置比例编码")
    private String rationCount;

    private String projectInCode;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("compCode", getCompCode())
            .append("accountCode", getAccountCode())
            .append("projectCode", getProjectCode())
            .append("projectName", getProjectName())
            .append("nowDate", getNowDate())
            .append("spell", getSpell())
            .append("projectType", getProjectType())
            .append("rationShow", getRationShow())
            .append("rationCount", getRationCount())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
