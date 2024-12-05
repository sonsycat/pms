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
 * 科室对象 sys_dept_room
 *
 * @author cyq
 * @date 2021-07-16
 */
@Data
@ApiModel(value = "BasicdataDeptRoom", description = "科室对象 sys_dept_room")
public class BasicdataDeptRoom extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 科室id */
    @ApiModelProperty(value = "${comment}")
    private Long deptRoomId;

    /** 父科室id */
    @ApiModelProperty(value = "${comment}")
    private Long parentId;
    private String parentName;

    /** 是否组织所属（0-是；1-否）*/
    private String fromOrg;

    /** 科室名称 */
    @Excel(name = "科室名称")
    @ApiModelProperty(value = "科室名称")
    private String deptRoomName;

    /** 科室编码 */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private String deptRoomCode;

    /** 机构ID */
    @ApiModelProperty(value = "机构ID")
    private Long deptId;

    /** 机构名称 */
    @Excel(name = "机构名称")
    @ApiModelProperty(value = "机构名称")
    private String deptName;

    /** 科室类型 */
    @Excel(name = "科室类型", readConverterExp = "1=机关,2=医技,3=医辅,4=临床")
    @ApiModelProperty(value = "科室类型")
    private String type;

    /** 科室排序 */
    @ApiModelProperty(value = "科室排序")
    private Long sort;

    /** 科室级别 */
    @ApiModelProperty(value = "科室级别")
    private String grade;

    /** 负责人 */
    @ApiModelProperty(value = "负责人")
    private String dutyOfficer;

    /** 联系电话 */
    @Excel(name = "联系电话")
    @ApiModelProperty(value = "联系电话")
    private String phone;

    /** 科室地址 */
    @ApiModelProperty(value = "科室地址")
    private String depAddress;

    /** 版本 */
    @ApiModelProperty(value = "版本")
    private String version;

    /** 部门状态（0正常 1停用） */
    @ApiModelProperty(value = "部门状态")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    @ApiModelProperty(value = "删除标志")
    private String delFlag;

    /** 拼音码 */
    @ApiModelProperty(value = "拼音码")
    private String pym;

    /** 临床科室类别（1-门诊；2-急诊） */
    @Excel(name = "临床科室类别", readConverterExp = "1=门诊,2=急诊")
    @ApiModelProperty(value = "临床科室类别")
    private String clinicType;

    /** 是否核算（0-是；1-否） */
    @Excel(name = "是否核算", readConverterExp = "0=是,1=否")
    @ApiModelProperty(value = "是否核算")
    private String isAccounting;

    /** 是否独立核算（0=是,1=否） */
    @Excel(name = "是否独立核算", readConverterExp = "0=是,1=否")
    @ApiModelProperty(value = "是否独立核算")
    private String isIndependentAccounting;

    /** 内线电话 */
    @Excel(name = "内线电话")
    @ApiModelProperty(value = "内线电话")
    private String inlinePhone;

    /** 是否服务（0-是；1-否） */
    @Excel(name = "是否服务", readConverterExp = "0=是,1=否")
    @ApiModelProperty(value = "是否服务")
    private String isService;

    /** 是否末级（0-是；1-否） */
    @Excel(name = "是否末级", readConverterExp = "0=是,1=否")
    @ApiModelProperty(value = "是否末级")
    private String isLeaf;

    /** 是否采购（0-是；1-否） */
    @Excel(name = "是否采购", readConverterExp = "0=是,1=否")
    @ApiModelProperty(value = "是否采购")
    private String isPurchase;

    /** 描述 */
    @Excel(name = "描述")
    @ApiModelProperty(value = "描述")
    private String description;

    private List<BasicdataDeptRoom> children = new ArrayList();
    private boolean hasChildren;
    /** 单位编码 */
    private String deptCode;
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptRoomId(Long deptRoomId) {
        this.deptRoomId = deptRoomId;
    }

    public Long getDeptRoomId() {
        return deptRoomId;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getParentId() {
        return parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    public void setDeptRoomName(String deptRoomName) {
        this.deptRoomName = deptRoomName;
    }
    public String getDeptRoomName() {
        return deptRoomName;
    }
    public void setDeptRoomCode(String deptRoomCode) {
        this.deptRoomCode = deptRoomCode;
    }
    public String getDeptRoomCode() {
        return deptRoomCode;
    }
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptName() {
        return deptName;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Long getSort() {
        return sort;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }
    public void setDutyOfficer(String dutyOfficer) {
        this.dutyOfficer = dutyOfficer;
    }

    public String getDutyOfficer() {
        return dutyOfficer;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return phone;
    }
    public void setDepAddress(String depAddress) {
        this.depAddress = depAddress;
    }
    public String getDepAddress() {
        return depAddress;
    }
    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
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
    public void setClinicType(String clinicType) {
        this.clinicType = clinicType;
    }

    public String getClinicType() {
        return clinicType;
    }
    public void setIsAccounting(String isAccounting) {
        this.isAccounting = isAccounting;
    }
    public String getIsAccounting() {
        return isAccounting;
    }
    public void setIsIndependentAccounting(String isIndependentAccounting) {
        this.isIndependentAccounting = isIndependentAccounting;
    }
    public String getIsIndependentAccounting() {
        return isIndependentAccounting;
    }
    public void setInlinePhone(String inlinePhone) {
        this.inlinePhone = inlinePhone;
    }
    public String getInlinePhone() {
        return inlinePhone;
    }
    public void setIsService(String isService) {
        this.isService = isService;
    }
    public String getIsService() {
        return isService;
    }
    public void setIsLeaf(String isLeaf) {
        this.isLeaf = isLeaf;
    }
    public String getIsLeaf() {
        return isLeaf;
    }
    public void setIsPurchase(String isPurchase) {
        this.isPurchase = isPurchase;
    }
    public String getIsPurchase() {
        return isPurchase;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public List<BasicdataDeptRoom> getChildren() {
        return children;
    }

    public void setChildren(List<BasicdataDeptRoom> children) {
        this.children = children;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("deptRoomId", getDeptRoomId())
            .append("parentId", getParentId())
            .append("deptRoomName", getDeptRoomName())
            .append("deptRoomCode", getDeptRoomCode())
            .append("deptId", getDeptId())
            .append("deptName", getDeptName())
            .append("type", getType())
            .append("sort", getSort())
            .append("grade", getGrade())
            .append("dutyOfficer", getDutyOfficer())
            .append("phone", getPhone())
            .append("depAddress", getDepAddress())
            .append("version", getVersion())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("pym", getPym())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("clinicType", getClinicType())
            .append("isAccounting", getIsAccounting())
            .append("isIndependentAccounting", getIsIndependentAccounting())
            .append("inlinePhone", getInlinePhone())
            .append("isService", getIsService())
            .append("isLeaf", getIsLeaf())
            .append("isPurchase", getIsPurchase())
            .append("description", getDescription())
            .toString();
	}


    public boolean isHasChildren() {
        return this.hasChildren;
    }


}
