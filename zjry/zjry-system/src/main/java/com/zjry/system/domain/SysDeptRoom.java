package com.zjry.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjry.common.core.domain.BaseMybatisPlusEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 科室对象 sys_dept_room
 *
 * @author lf
 * @date 2021-06-18
 */
@Data
@TableName("comm.sys_dept_room_ry")
@ApiModel(value = "SysDeptRoom", description = "科室对象 sys_dept_room")
public class SysDeptRoom extends BaseMybatisPlusEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 科室id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "科室id")
    private Long deptRoomId;

    /**
     * 父科室id
     */
    @Excel(name = "父科室id")
    @ApiModelProperty(value = "父科室id")
    private Long parentId;

    /**
     * 科室名称
     */
    @Excel(name = "科室名称")
    @ApiModelProperty(value = "科室名称")
    private String deptRoomName;

    /**
     * 科室编码
     */
    @Excel(name = "科室编码")
    @ApiModelProperty(value = "科室编码")
    private String deptRoomCode;

    /**
     * 机构ID
     */
    @Excel(name = "机构ID")
    @ApiModelProperty(value = "机构ID")
    private Long deptId;

    /**
     * 机构名称
     */
    @Excel(name = "机构名称")
    @ApiModelProperty(value = "机构名称")
    private String deptName;

    /**
     * 科室类型
     */
    @Excel(name = "科室类型")
    @ApiModelProperty(value = "科室类型")
    private String type;

    /**
     * 科室排序
     */
    @Excel(name = "科室排序")
    @ApiModelProperty(value = "科室排序")
    private Long sort;

    /**
     * 科室级别
     */
    @Excel(name = "科室级别")
    @ApiModelProperty(value = "科室级别")
    private String grade;

    /**
     * 负责人
     */
    @Excel(name = "负责人")
    @ApiModelProperty(value = "负责人")
    private String dutyOfficer;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    @ApiModelProperty(value = "联系电话")
    private String phone;

    /**
     * 科室地址
     */
    @Excel(name = "科室地址")
    @ApiModelProperty(value = "科室地址")
    private String depAddress;

    /**
     * 部门状态（0正常 1停用）
     */
    @Excel(name = "部门状态", readConverterExp = "0=正常,1=停用")
    @ApiModelProperty(value = "部门状态")
    private String status;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @ApiModelProperty(value = "删除标志（0代表存在 1代表删除）")
    private String delFlag;

    /**
     * 版本
     */
    @Excel(name = "版本")
    @ApiModelProperty(value = "版本")
    private String version;

    /**
     * 拼音码
     */
    @Excel(name = "拼音码")
    @ApiModelProperty(value = "拼音码")
    private String pym;

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

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setPym(String pym) {
        this.pym = pym;
    }

    public String getPym() {
        return pym;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
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
                .append("status", getStatus())
                .append("remark", getRemark())
                .append("delFlag", getDelFlag())
                .append("verson", getVersion())
                .append("pym", getPym())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
