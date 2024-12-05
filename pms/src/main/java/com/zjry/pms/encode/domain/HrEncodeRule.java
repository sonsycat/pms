package com.zjry.pms.encode.domain;

import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;

/**
 * 编码规则对象 hr_encode_rule
 *
 * @author syw
 * @date 2021-06-25
 */
@Data
@ApiModel(value = "HrEncodeRule", description = "编码规则对象 hr_encode_rule")
public class HrEncodeRule extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "${comment}")
    private Long id;

    /**
     * 规则名称
     */
    @Excel(name = "规则名称")
    @NotBlank(message = "规则名称不为空")
    @ApiModelProperty(value = "规则名称")
    private String ruleName;

    /**
     * 规则数据库表
     */
    @Excel(name = "规则数据库表")
    @NotBlank(message = "规则数据库表不为空")
    @ApiModelProperty(value = "规则数据库表")
    private String ruleDb;

    /**
     * 规则数据库对应字段
     */
    @Excel(name = "规则数据库对应字段")
    @NotBlank(message = "规则数据库对应字段不为空")
    @ApiModelProperty(value = "规则数据库对应字段")
    private String ruleColumn;

    /**
     * 规则内容
     */
    @Excel(name = "规则内容")
    @ApiModelProperty(value = "规则内容")
    private String ruleContent;

    /**
     * 规则内容第一部分
     */
    @NotBlank(message = "规则内容第一部分不为空")
    @ApiModelProperty(value = "规则内容第一部分")
    private String ruleFirst;

    /**
     * 规则内容第二部分
     */
    @NotBlank(message = "规则内容第二部分不为空")
    @ApiModelProperty(value = "规则内容第二部分")
    private String ruleSecond;

    /**
     * 规则内容第三部分
     */
    @NotBlank(message = "规则内容第三部分不为空")
    @ApiModelProperty(value = "规则内容第三部分")
    private String ruleThird;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleDb(String ruleDb) {
        this.ruleDb = ruleDb;
    }

    public String getRuleDb() {
        return ruleDb;
    }

    public void setRuleColumn(String ruleColumn) {
        this.ruleColumn = ruleColumn;
    }

    public String getRuleColumn() {
        return ruleColumn;
    }

    public void setRuleContent(String ruleContent) {
        this.ruleContent = ruleContent;
    }

    public String getRuleContent() {
        return ruleContent;
    }

    public void setRuleFirst(String ruleFirst) {
        this.ruleFirst = ruleFirst;
    }

    public String getRuleFirst() {
        return ruleFirst;
    }

    public void setRuleSecond(String ruleSecond) {
        this.ruleSecond = ruleSecond;
    }

    public String getRuleSecond() {
        return ruleSecond;
    }

    public void setRuleThird(String ruleThird) {
        this.ruleThird = ruleThird;
    }

    public String getRuleThird() {
        return ruleThird;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("ruleName", getRuleName())
                .append("ruleDb", getRuleDb())
                .append("ruleColumn", getRuleColumn())
                .append("ruleContent", getRuleContent())
                .append("ruleFirst", getRuleFirst())
                .append("ruleSecond", getRuleSecond())
                .append("ruleThird", getRuleThird())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
