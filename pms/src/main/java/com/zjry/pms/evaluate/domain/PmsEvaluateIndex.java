package com.zjry.pms.evaluate.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 指标管理对象 pms_evaluate_index
 *
 * @author mk
 * @date 2022-07-26
 */
@Data
@ApiModel(value = "PmsEvaluateIndex", description = "指标管理对象 pms_evaluate_index")
public class PmsEvaluateIndex extends BaseEntity implements Serializable {
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

    /** BSC一级分类 */
    @Excel(name = "BSC一级分类")
    @ApiModelProperty(value = "BSC一级分类")
    private String bscOneCode;
    private String bscOneName;
    /** BSC二级分类 */
    @Excel(name = "BSC二级分类")
    @ApiModelProperty(value = "BSC二级分类")
    private String bscSecondCode;
    private String bscSecondName;
    /** 部门分类 */
    @Excel(name = "部门分类")
    @ApiModelProperty(value = "部门分类")
    private String deptType;

    /** 组织类别 */
    @Excel(name = "组织类别")
    @ApiModelProperty(value = "组织类别")
    private String orgType;

    /** 指标编码 */
    @Excel(name = "指标编码")
    @ApiModelProperty(value = "指标编码")
    private String indexCode;

    /** 指标名称 */
    @Excel(name = "指标名称")
    @ApiModelProperty(value = "指标名称")
    private String indexName;

    private String iName;

    /** 是否表达式（0是，1否） */
    @Excel(name = "是否表达式", readConverterExp = "0=是，1否")
    @ApiModelProperty(value = "是否表达式（0是，1否）")
    private String isExpress;

    /** 公式编码 */
    @Excel(name = "公式编码")
    @ApiModelProperty(value = "公式编码")
    private String formulaCode;

    /** 公式名称 */
    @Excel(name = "公式名称")
    @ApiModelProperty(value = "公式名称")
    private String formulaName;

    /** 是否区间（0是，1否） */
    @Excel(name = "是否区间", readConverterExp = "0=是，1否")
    @ApiModelProperty(value = "是否区间（0是，1否）")
    private String isSection;

    /** 指标算法sql */
    @Excel(name = "指标算法sql")
    @ApiModelProperty(value = "指标算法sql")
    private String indexSql;

    /** 区间开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "区间开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "区间开始时间")
    private Date sectionStart;

    /** 区间结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "区间结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "区间结束时间")
    private Date sectionEnd;

    /** 是否高优指标（0是，1否） */
    @Excel(name = "是否高优指标", readConverterExp = "0=是，1否")
    @ApiModelProperty(value = "是否高优指标（0是，1否）")
    private String isCouns;

    /** 是否停用（0是，1否，2不显示） */
    @Excel(name = "是否停用", readConverterExp = "0=是，1否，2不显示")
    @ApiModelProperty(value = "是否停用（0是，1否，2不显示）")
    private String isStop;

    /** 是否综合评价指标（0是，1否） */
    @Excel(name = "是否综合评价指标", readConverterExp = "0=是，1否")
    @ApiModelProperty(value = "是否综合评价指标（0是，1否）")
    private String isComp;

    /** 是否绝对值（0是，1否） */
    @Excel(name = "是否绝对值", readConverterExp = "0=是，1否")
    @ApiModelProperty(value = "是否绝对值（0是，1否）")
    private String isAbsolute;

    /** 指标类型（1时间有关指标，2时间无关指标） */
    @Excel(name = "指标类型", readConverterExp = "1=时间有关指标，2时间无关指标")
    @ApiModelProperty(value = "指标类型（1时间有关指标，2时间无关指标）")
    private String indexType;

    /** 是否固定分值（0是，1否） */
    @Excel(name = "是否固定分值", readConverterExp = "0=是，1否")
    @ApiModelProperty(value = "是否固定分值（0是，1否）")
    private String isRegular;

    /** 指标阈值比 */
    @Excel(name = "指标阈值比")
    @ApiModelProperty(value = "指标阈值比")
    private String indexRatio;

    /** 指标说明 */
    @Excel(name = "指标说明")
    @ApiModelProperty(value = "指标说明")
    private String indexContent;

    /** 顺序号 */
    @Excel(name = "顺序号")
    @ApiModelProperty(value = "顺序号")
    private String indexSort;

    /** 数据源CODE **/
    private String dbCode;

    /** 获取收入sql */
    @Excel(name = "获取收入sql")
    @ApiModelProperty(value = "获取收入sql")
    private String numSql;

    private String isBonus;

    private String isNecessary;

    private String deptTypeForBonus;
    /** 指标集编码 */
    private String setCode;
    /** 指标权重比值 */
    private BigDecimal weightRatio;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("compCode", getCompCode())
            .append("accountCode", getAccountCode())
            .append("bscOneCode", getBscOneCode())
            .append("bscSecondCode", getBscSecondCode())
            .append("deptType", getDeptType())
            .append("orgType", getOrgType())
            .append("indexCode", getIndexCode())
            .append("indexName", getIndexName())
            .append("isExpress", getIsExpress())
            .append("formulaCode", getFormulaCode())
            .append("formulaName", getFormulaName())
            .append("isSection", getIsSection())
            .append("indexSql", getIndexSql())
            .append("sectionStart", getSectionStart())
            .append("sectionEnd", getSectionEnd())
            .append("isCouns", getIsCouns())
            .append("isStop", getIsStop())
            .append("isComp", getIsComp())
            .append("isAbsolute", getIsAbsolute())
            .append("indexType", getIndexType())
            .append("isRegular", getIsRegular())
            .append("indexRatio", getIndexRatio())
            .append("indexContent", getIndexContent())
            .append("indexSort", getIndexSort())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
