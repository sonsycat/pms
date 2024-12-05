package com.zjry.system.domain;

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
 * 医生信息对象 sys_doctor
 *
 * @author mingc
 * @date 2021-06-18
 */
@Data
@ApiModel(value = "SysDoctor", description = "医生信息对象 sys_doctor")
public class SysDoctor extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "${comment}")
    private Long id;

    /**
     * 医生编码
     */
    @Excel(name = "医生编码")
    @ApiModelProperty(value = "医生编码")
    private String docCode;

    /**
     * 医生姓名
     */
    @Excel(name = "医生姓名")
    @ApiModelProperty(value = "医生姓名")
    private String docName;

    /**
     * 医生级别
     */
    @Excel(name = "医生级别")
    @ApiModelProperty(value = "医生级别")
    private String docLevel;

    /**
     * 机构
     */
    @Excel(name = "机构")
    @ApiModelProperty(value = "机构")
    private Long orgCode;
    private String orgName;

    /**
     * 科室
     */
    @Excel(name = "科室")
    @ApiModelProperty(value = "科室")
    private Long depCode;

    /**
     * 用户性别
     */
    @Excel(name = "用户性别")
    @ApiModelProperty(value = "用户性别")
    private String sex;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "出生日期")
    private Date birthday;

    /**
     * 证件类型
     */
    @Excel(name = "证件类型")
    @ApiModelProperty(value = "证件类型")
    private String cardType;

    /**
     * 身份证号
     */
    @Excel(name = "身份证号")
    @ApiModelProperty(value = "身份证号")
    private String cardNo;

    /**
     * 学历
     */
    @Excel(name = "学历")
    @ApiModelProperty(value = "学历")
    private String education;

    /**
     * 职业类型
     */
    @Excel(name = "职业类型")
    @ApiModelProperty(value = "职业类型")
    private String profType;

    /**
     * 职业级别
     */
    @Excel(name = "职业级别")
    @ApiModelProperty(value = "职业级别")
    private String profLevel;

    /**
     * 职业证号
     */
    @Excel(name = "职业证号")
    @ApiModelProperty(value = "职业证号")
    private String profNum;

    /**
     * 职业机构
     */
    @Excel(name = "职业机构")
    @ApiModelProperty(value = "职业机构")
    private String profOrg;

    /**
     * 职称
     */
    @Excel(name = "职称")
    @ApiModelProperty(value = "职称")
    private String technical;

    /**
     * 电话
     */
    @Excel(name = "电话")
    @ApiModelProperty(value = "电话")
    private String tel;

    /**
     * email
     */
    @Excel(name = "email")
    @ApiModelProperty(value = "email")
    private String email;

    /**
     * 人员状态
     */
    @Excel(name = "人员状态")
    @ApiModelProperty(value = "人员状态")
    private String personStatus;

    /**
     * 入职时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入职时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "入职时间")
    private Date entryTime;

    /**
     * 招聘方式
     */
    @Excel(name = "招聘方式")
    @ApiModelProperty(value = "招聘方式")
    private String recruitmentMethods;

    /**
     * 家庭地址
     */
    @Excel(name = "家庭地址")
    @ApiModelProperty(value = "家庭地址")
    private String homeAddress;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    public String getDocCode() {
        return docCode;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocLevel(String docLevel) {
        this.docLevel = docLevel;
    }

    public String getDocLevel() {
        return docLevel;
    }

    public void setOrgCode(Long orgCode) {
        this.orgCode = orgCode;
    }

    public Long getOrgCode() {
        return orgCode;
    }

    public void setDepCode(Long depCode) {
        this.depCode = depCode;
    }

    public Long getDepCode() {
        return depCode;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEducation() {
        return education;
    }

    public void setProfType(String profType) {
        this.profType = profType;
    }

    public String getProfType() {
        return profType;
    }

    public void setProfLevel(String profLevel) {
        this.profLevel = profLevel;
    }

    public String getProfLevel() {
        return profLevel;
    }

    public void setProfNum(String profNum) {
        this.profNum = profNum;
    }

    public String getProfNum() {
        return profNum;
    }

    public void setProfOrg(String profOrg) {
        this.profOrg = profOrg;
    }

    public String getProfOrg() {
        return profOrg;
    }

    public void setTechnical(String technical) {
        this.technical = technical;
    }

    public String getTechnical() {
        return technical;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTel() {
        return tel;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPersonStatus(String personStatus) {
        this.personStatus = personStatus;
    }

    public String getPersonStatus() {
        return personStatus;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setRecruitmentMethods(String recruitmentMethods) {
        this.recruitmentMethods = recruitmentMethods;
    }

    public String getRecruitmentMethods() {
        return recruitmentMethods;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("docCode", getDocCode())
                .append("docName", getDocName())
                .append("docLevel", getDocLevel())
                .append("orgCode", getOrgCode())
                .append("depCode", getDepCode())
                .append("sex", getSex())
                .append("birthday", getBirthday())
                .append("cardType", getCardType())
                .append("cardNo", getCardNo())
                .append("education", getEducation())
                .append("profType", getProfType())
                .append("profLevel", getProfLevel())
                .append("profNum", getProfNum())
                .append("profOrg", getProfOrg())
                .append("technical", getTechnical())
                .append("tel", getTel())
                .append("email", getEmail())
                .append("personStatus", getPersonStatus())
                .append("entryTime", getEntryTime())
                .append("recruitmentMethods", getRecruitmentMethods())
                .append("homeAddress", getHomeAddress())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("orgName", getOrgName())
                .toString();
    }
}
