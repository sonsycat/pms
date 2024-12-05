package com.zjry.pms.basic.domain.basicData;

import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 单位对象 sys_dept
 *
 * @author cyq
 * @date 2021-07-15
 */
@Data
@ApiModel(value = "BasicdataDept", description = "单位对象 sys_dept")
public class BasicdataDept extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 单位id */
    @ApiModelProperty(value = "${comment}")
    private Long deptId;

    /** 父单位id */
    @ApiModelProperty(value = "${comment}")
    private Long parentId;

    /** 祖级列表 */
    @ApiModelProperty(value = "${comment}")
    private String ancestors;

    /** 单位名称 */
    @Excel(name = "单位名称")
    @ApiModelProperty(value = "单位名称")
    private String deptName;

    /** 单位编码 */
    @Excel(name = "单位编码")
    @ApiModelProperty(value = "单位编码")
    private String deptCode;

    /** 显示顺序 */
    @ApiModelProperty(value = "单位编码")
    private Integer orderNum;

    /** 负责人 */
    @Excel(name = "负责人")
    @ApiModelProperty(value = "负责人")
    private String leader;

    /** 联系电话 */
    @Excel(name = "联系电话")
    @ApiModelProperty(value = "联系电话")
    private String phone;

    /** 邮箱 */
    @Excel(name = "邮箱")
    @ApiModelProperty(value = "邮箱")
    private String email;

    /** 单位状态（0正常 1停用） */
    @Excel(name = "单位状态", readConverterExp = "0=正常,1=停用")
    @ApiModelProperty(value = "单位状态")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    @ApiModelProperty(value = "删除标志")
    private String delFlag;

    /** 拼音码 */
    @Excel(name = "拼音码")
    @ApiModelProperty(value = "拼音码")
    private String pym;

    /** 所在省 */
    @Excel(name = "所在省")
    @ApiModelProperty(value = "所在省")
    private Long province;

    /** 所在省 */
    @Excel(name = "所在市")
    @ApiModelProperty(value = "所在市")
    private Long city;

    /** 详细地址 */
    @Excel(name = "详细地址")
    @ApiModelProperty(value = "详细地址")
    private String address;

    /** 联系人 */
    @Excel(name = "联系人")
    @ApiModelProperty(value = "联系人")
    private String contactPerson;

    /** 税务证号 */
    @Excel(name = "税务证号")
    @ApiModelProperty(value = "税务证号")
    private String taxNumber;

    /** 主管会计 */
    @Excel(name = "主管会计")
    @ApiModelProperty(value = "主管会计")
    private String chiefAccountant;

    /** 是否末级(0-否；1-是) */
    @Excel(name = "是否末级(0-否；1-是)")
    @ApiModelProperty(value = "是否末级(0-否；1-是)")
    private String isLeaf;

    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String remark;

    private List<BasicdataDept> children = new ArrayList();
    private boolean hasChildren;

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    public Long getParentId() {
        return parentId;
    }
    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }

    public String getAncestors() {
        return ancestors;
    }
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    public String getDeptName() {
        return deptName;
    }
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
    public String getDeptCode() {
        return deptCode;
    }
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getOrderNum() {
        return orderNum;
    }
    public void setLeader(String leader) {
        this.leader = leader;
    }
    public String getLeader() {
        return leader;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return phone;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    @Override
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String getDelFlag() {
        return delFlag;
    }
    public void setPym(String pym) {
        this.pym = pym;
    }

    public String getPym() {
        return pym;
    }
    public void setProvince(Long province) {
        this.province = province;
    }
    public Long getProvince() {
        return province;
    }
    public void setCity(Long city) {
        this.city = city;
    }
    public Long getCity() {
        return city;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return address;
    }
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
    public String getContactPerson() {
        return contactPerson;
    }
    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }
    public String getTaxNumber() {
        return taxNumber;
    }
    public void setChiefAccountant(String chiefAccountant) {
        this.chiefAccountant = chiefAccountant;
    }
    public String getChiefAccountant() {
        return chiefAccountant;
    }
    public void setIsLeaf(String isLeaf) {
        this.isLeaf = isLeaf;
    }
    public String getIsLeaf() {
        return isLeaf;
    }

    @Override
    public String getRemark() {
        return remark;
    }
    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<BasicdataDept> getChildren() {
        return children;
    }

    public void setChildren(List<BasicdataDept> children) {
        this.children = children;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("deptId", getDeptId())
            .append("parentId", getParentId())
            .append("ancestors", getAncestors())
            .append("deptName", getDeptName())
            .append("deptCode", getDeptCode())
            .append("orderNum", getOrderNum())
            .append("leader", getLeader())
            .append("phone", getPhone())
            .append("email", getEmail())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("pym", getPym())
            .append("province", getProvince())
            .append("city", getCity())
            .append("address", getAddress())
            .append("contactPerson", getContactPerson())
            .append("taxNumber", getTaxNumber())
            .append("chiefAccountant", getChiefAccountant())
            .append("isLeaf", getIsLeaf())
            .append("remark", getRemark())
            .toString();
	}
}
