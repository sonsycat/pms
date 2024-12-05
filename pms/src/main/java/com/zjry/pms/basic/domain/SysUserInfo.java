package com.zjry.pms.basic.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author mk
 * @title: SysUserInfo
 * @projectName zjry-asset
 * @date 2021/8/1815:13
 */
@Data
public class SysUserInfo {
    private static final long serialVersionUID = 1L;
    /**用户id*/
    private Long  userId;
    /**单位id*/
    private Long  deptId;
    /**用户账号*/
    private String userName;
    /**用户姓名*/
    private String nickName;
    /**用户类型*/
    private String userType;
    /**单位名*/
    private String deptName;
    /** 账套编码 */
    private String accountCode;
    /** 账套名称 */
    private String accountName;
    /** 开始日期 */
    private Date startDate;
    /** 结束日期 */
    private Date endDate;
    /** 期间名称 */
    private String periodName;

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

}
