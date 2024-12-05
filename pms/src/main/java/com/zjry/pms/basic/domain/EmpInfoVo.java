package com.zjry.pms.basic.domain;

import lombok.Data;

/**
 * @author mk
 * @title: EmpInfoVo
 * @projectName zjry-asset
 * @date 2021/10/816:02
 */
@Data
public class EmpInfoVo {
    private String userId;
    /**工号*/
    private String empCode;
    /**姓名*/
    private String empName;
    /**机构*/
    private String compCode;
    /**科室*/
    private String deptCode;
    /**性别*/
    private String gender;
    /**登录名*/
    private String userName;
    /**科室*/
    private String deptName;
    /**部门*/
    private String compName;
    /**岗位*/
    private String postName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getCompCode() {
        return compCode;
    }

    public void setCompCode(String compCode) {
        this.compCode = compCode;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
