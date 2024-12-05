package com.zjry.pms.commission.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 独立核算提成比类别对象 pms_separate_account_sort
 *
 * @author zjry
 * @date 2022-09-27
 */
@Data
@ApiModel(value = "SeparateAccountSort", description = "独立核算提成比类别对象 pms_separate_account_sort")
@TableName("pms_separate_account_sort")
public class SeparateAccountSort extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /** 类别编码 */
    @Excel(name = "类别编码")
    @ApiModelProperty(value = "类别编码")
    private String sortCode;

    /** 类别名称 */
    @Excel(name = "类别名称")
    @ApiModelProperty(value = "类别名称")
    private String sortName;

    /** 删除标记(0未删除,1已删除) */
    @Excel(name = "删除标记(0未删除,1已删除)")
    @ApiModelProperty(value = "删除标记(0未删除,1已删除)")
    @TableLogic
    private String delFlag;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getSortCode() {
        return sortCode;
    }
    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortName() {
        return sortName;
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
            .append("sortCode", getSortCode())
            .append("sortName", getSortName())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
