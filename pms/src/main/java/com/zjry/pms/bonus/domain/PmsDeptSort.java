package com.zjry.pms.bonus.domain;

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
 * 核算单位类别设置对象 pms_dept_sort
 *
 * @author zjry
 * @date 2022-09-27
 */
@Data
@ApiModel(value = "PmsDeptSort", description = "核算单位类别设置对象 pms_dept_sort")
public class PmsDeptSort extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /** 核算科室编码 */
    @Excel(name = "核算科室编码")
    @ApiModelProperty(value = "核算科室编码")
    private String deptRoomCode;

    /** 核算科室编码 */
    @Excel(name = "核算科室名称")
    @ApiModelProperty(value = "核算科室名称")
    private String deptRoomName;

    /** 类别编码 */
    @Excel(name = "类别编码")
    @ApiModelProperty(value = "类别编码")
    private String sortCode;

    /** 单位编码 */
    @Excel(name = "单位编码")
    @ApiModelProperty(value = "单位编码")
    private Long compCode;

    /** 账套编码 */
    @Excel(name = "账套编码")
    @ApiModelProperty(value = "账套编码")
    private String accountCode;

    /** 年月 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "年月", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "年月")
    private Date nowDate;

    /** 删除标记(0未删除,1已删除) */
    @Excel(name = "删除标记(0未删除,1已删除)")
    @ApiModelProperty(value = "删除标记(0未删除,1已删除)")
    private String delFlag;

    private Date copyDate;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setDeptRoomCode(String deptRoomCode) {
        this.deptRoomCode = deptRoomCode;
    }

    public String getDeptRoomCode() {
        return deptRoomCode;
    }

    public String getDeptRoomName() {
        return deptRoomName;
    }

    public void setDeptRoomName(String deptRoomName) {
        this.deptRoomName = deptRoomName;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getSortCode() {
        return sortCode;
    }
    public void setCompCode(Long compCode) {
        this.compCode = compCode;
    }

    public Long getCompCode() {
        return compCode;
    }
    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountCode() {
        return accountCode;
    }
    public void setNowDate(Date nowDate) {
        this.nowDate = nowDate;
    }

    public Date getNowDate() {
        return nowDate;
    }
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public Date getCopyDate() {
        return copyDate;
    }

    public void setCopyDate(Date copyDate) {
        this.copyDate = copyDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("deptRoomCode", getDeptRoomCode())
            .append("deptRoomName", getDeptRoomName())
            .append("sortCode", getSortCode())
            .append("compCode", getCompCode())
            .append("accountCode", getAccountCode())
            .append("nowDate", getNowDate())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
