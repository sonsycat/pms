package com.zjry.pms.bonus.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 核算科室设置对象 pms_account_dept
 *
 * @author zjry
 * @date 2022-09-29
 */
@Data
@ApiModel(value = "PmsAccountDept", description = "核算科室设置对象 pms_account_dept")
public class PmsAccountDept extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty(value = "$column.columnComment")
    private Long id;

    /** 上级科室编码 */
    @Excel(name = "上级科室编码")
    @ApiModelProperty(value = "上级科室编码")
    @NotBlank(message = "上级科室不能为空")
    private String parentCode;

    /** 上级科室名称 */
    @Excel(name = "上级科室名称")
    @ApiModelProperty(value = "上级科室名称")
    @TableField(exist = false)
    private String parentName;

    /** 科室编码 */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    @NotBlank(message = "科室编码不为空")
    private String deptRoomCode;

    /** 科室名称 */
    @Excel(name = "科室名称")
    @ApiModelProperty(value = "科室名称")
    private String deptRoomName;

    /** 是否核算 */
    @Excel(name = "是否核算")
    @ApiModelProperty(value = "是否核算")
    private String isAccount;

    /** 年月 */
    @ApiModelProperty(value = "年月")
    private Date nowDate;

    /** 拼音码 */
    @ApiModelProperty(value = "拼音码")
    @TableField(exist = false)
    private String pym;

    /** 单位编码 */
    @ApiModelProperty(value = "单位编码")
    private Long compCode;

    /** 账套编码 */
    @ApiModelProperty(value = "账套编码")
    private String accountCode;

    /** 删除标记(0未删除,1已删除) */
    @ApiModelProperty(value = "删除标记(0未删除,1已删除)")
    private String delFlag;

    @TableField(exist = false)
    List<PmsAccountDept> accountDeptList = new ArrayList<>();

    /** 子类数量 */
    @TableField(exist = false)
    private int childNum = 0;

    @TableField(exist = false)
    private Date copyDate;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getParentCode() {
        return parentCode;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public void setDeptRoomCode(String deptRoomCode) {
        this.deptRoomCode = deptRoomCode;
    }

    public String getDeptRoomCode() {
        return deptRoomCode;
    }
    public void setDeptRoomName(String deptRoomName) {
        this.deptRoomName = deptRoomName;
    }

    public String getDeptRoomName() {
        return deptRoomName;
    }
    public void setIsAccount(String isAccount) {
        this.isAccount = isAccount;
    }

    public String getIsAccount() {
        return isAccount;
    }
    public void setNowDate(Date nowDate) {
        this.nowDate = nowDate;
    }

    public Date getNowDate() {
        return nowDate;
    }

    public String getPym() {
        return pym;
    }

    public void setPym(String pym) {
        this.pym = pym;
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
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public List<PmsAccountDept> getAccountDeptList() {
        return accountDeptList;
    }

    public void setAccountDeptList(List<PmsAccountDept> accountDeptList) {
        this.accountDeptList = accountDeptList;
    }

    public int getChildNum() {
        return childNum;
    }

    public void setChildNum(int childNum) {
        this.childNum = childNum;
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
            .append("parentCode", getParentCode())
            .append("deptRoomCode", getDeptRoomCode())
            .append("deptRoomName", getDeptRoomName())
            .append("isAccount", getIsAccount())
            .append("nowDate", getNowDate())
            .append("compCode", getCompCode())
            .append("accountCode", getAccountCode())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
	}
}
