package com.zjry.system.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zjry.common.core.domain.BaseMybatisPlusEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 自定义查询对象 sys_user_defined_query
 *
 * @author lf
 * @date 2021-07-01
 */
@Data
@ApiModel(value = "SysUserDefinedQuery", description = "自定义查询对象 sys_user_defined_query")
public class SysUserDefinedQuery extends BaseMybatisPlusEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 查询id */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "${comment}")
    private Long queryId;

    /** 查询名称 */
    @Excel(name = "查询名称")
    @ApiModelProperty(value = "查询名称")
    private String queryName;

    /** 查询索引 */
    @Excel(name = "查询索引")
    @ApiModelProperty(value = "查询索引")
    private String queryIndex;

    /** 过滤条件匹配 */
    @Excel(name = "过滤条件匹配")
    @ApiModelProperty(value = "过滤条件匹配")
    private String filterCondition;

    /** 查询条件json */
    @Excel(name = "查询条件json")
    @ApiModelProperty(value = "查询条件json")
    private String queryRows;

    /** 删除标志（0代表存在 2代表删除） */
    @ApiModelProperty(value = "查询条件json")
    private String delFlag;

    public void setQueryId(Long queryId) {
        this.queryId = queryId;
    }

    public Long getQueryId() {
        return queryId;
    }
    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public String getQueryName() {
        return queryName;
    }
    public void setQueryIndex(String queryIndex) {
        this.queryIndex = queryIndex;
    }

    public String getQueryIndex() {
        return queryIndex;
    }

    public String getFilterCondition() {
        return filterCondition;
    }

    public String getQueryRows() {
        return queryRows;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    public void setQueryRows(String queryRows) {
        this.queryRows = queryRows;
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
            .append("queryId", getQueryId())
            .append("queryName", getQueryName())
            .append("queryIndex", getQueryIndex())
            .append("filterCondition", getFilterCondition())
            .append("queryRows", getQueryRows())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
