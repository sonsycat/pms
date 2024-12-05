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
 * 编码规则树形对象 hr_encode_rule_tree
 *
 * @author syw
 * @date 2021-06-25
 */
@Data
@ApiModel(value = "HrEncodeRuleTree", description = "编码规则树形对象 hr_encode_rule_tree")
public class HrEncodeRuleTree extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "Id")
    private Long  id;

    /**
     * 规则名称
     */
    @NotBlank(message = "规则名称不为空")
    @Excel(name = "规则名称")
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
    @NotBlank(message = "规则内容不为空")
    @ApiModelProperty(value = "规则内容")
    private String ruleContent;



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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("ruleName", getRuleName())
                .append("ruleDb", getRuleDb())
                .append("ruleColumn", getRuleColumn())
                .append("ruleContent", getRuleContent())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
