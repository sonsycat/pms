package com.zjry.pms.basic.domain.basicData;

import com.zjry.common.annotation.Excel;
import com.zjry.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户信息对象 sys_user
 *
 * @author cyq
 * @date 2021-07-19
 */
@Data
@ApiModel(value = "BasicdataUser", description = "用户信息对象 sys_user")
public class BasicdataUser extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "$用户ID")
    private Long userId;

    /**
     * 单位ID
     */
    @ApiModelProperty(value = "${单位ID}")
    private Long deptId;

    /**
     * 科室id
     */
    @ApiModelProperty(value = "科室id")
    private Long deptRoomId;

    /**
     * 用户账号
     */
    @Excel(name = "用户账号")
    @ApiModelProperty(value = "用户账号")
    private String userName;

    /**
     * 科室名称
     */
    @Excel(name = "科室名称")
    @ApiModelProperty(value = "科室名称")
    private String deptRoomName;

    /**
     * 单位名称
     */
    @Excel(name = "单位名称")
    @ApiModelProperty(value = "单位名称")
    private String deptName;

    /**
     * 用户昵称
     */
    @Excel(name = "用户昵称")
    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    /**
     * 用户类型（00系统用户）
     */
    @ApiModelProperty(value = "用户类型")
    private String userType;

    /**
     * 用户邮箱
     */
    @ApiModelProperty(value = "用户邮箱")
    private String email;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    @ApiModelProperty(value = "手机号码")
    private String phonenumber;

    /**
     * 用户性别（0男 1女 2未知）
     */
    @ApiModelProperty(value = "用户性别")
    private String sex;

    /**
     * 头像地址
     */
    @ApiModelProperty(value = "头像地址")
    private String avatar;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /** 旧密码*/
    private String oldPwd;

    /**
     * 帐号状态（0正常 1停用）
     */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    @ApiModelProperty(value = "帐号状态")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @ApiModelProperty(value = "删除标志")
    private String delFlag;

    /**
     * 最后登录IP
     */
    @ApiModelProperty(value = "最后登录IP")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @ApiModelProperty(value = "最后登录时间")
    private Date loginDate;

    /**
     * 人员编码
     */
    @ApiModelProperty(value = "人员编码")
    private String docCode;
    private String docName;
    private String empCode;
    private String empName;
    /**
     * 岗位编码
     */
    @ApiModelProperty(value = "岗位编码")
    private String postCode;

    /**
     * 当前账套
     */
    @ApiModelProperty(value = "当前账套")
    private AccountSet accountSet;
    /**
     * 用户账套关系列表
     */
    @ApiModelProperty(value = "用户账套关系列表")
    private List<AccountSet> accountSetList;

    private BasicdataDept dept;
    private BasicdataRole role;
    private List<BasicdataRole> roles;
    private BasicdataDeptRoom deptRoom;
    /**
     * 用户科室关系列表
     */
    @ApiModelProperty(value = "用户科室关系列表")
    private List<BasicdataDeptRoom> deptRoomList;

    /** 账套id和角色id （用于初始化和切换时使用）*/
    private Long accountId;
    private Long roleId;

    private String roleName;
    private String systemFlag;
    /** 用户科室关系页面 下拉树*/
    private Long tempRandom;

    private List<BasicdataUser> children;

    private Long[] roleIds;
    private Long[] postIds;
    private String remark;

    public BasicdataUser() {
    }

    public BasicdataUser(Long userId) {
        this.userId = userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptRoomId(Long deptRoomId) {
        this.deptRoomId = deptRoomId;
    }

    public Long getDeptRoomId() {
        return deptRoomId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    public void setDeptRoomName(String deptRoomName) {
        this.deptRoomName = deptRoomName;
    }

    public String getDeptRoomName() {
        return deptRoomName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getNickName() {
        return nickName;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    public String getUserType() {
        return userType;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
    public String getPhonenumber() {
        return phonenumber;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getSex() {
        return sex;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
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

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }
    public String getDocCode() {
        return docCode;
    }

    public boolean isAdmin() {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    public String getPostCode() {
        return postCode;
    }
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
    @Override
    public String getRemark() {
        return remark;
    }
    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userId", getUserId())
                .append("deptId", getDeptId())
                .append("deptRoomId", getDeptRoomId())
                .append("userName", getUserName())
                .append("deptRoomName", getDeptRoomName())
                .append("deptName", getDeptName())
                .append("nickName", getNickName())
                .append("userType", getUserType())
                .append("email", getEmail())
                .append("phonenumber", getPhonenumber())
                .append("sex", getSex())
                .append("avatar", getAvatar())
                .append("password", getPassword())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("loginIp", getLoginIp())
                .append("loginDate", getLoginDate())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("docCode", getDocCode())
                .toString();
    }
}
