package com.zjry.common.core.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.utils.DateUtils;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.common.utils.StringUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Entity基类
 *
 * @author zjry
 */
@ApiModel(value = "BaseEntity", description = "Entity基类 BaseEntity")
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 删除标记（0：正常；1：删除；2：审核；）
	 */
	public static final String DEL_FLAG_NORMAL = "0";
	public static final String DEL_FLAG_DELETE = "1";
	public static final String DEL_FLAG_AUDIT = "2";

	/** 搜索值 */
	@ApiModelProperty(value = "搜索值")
	private String searchValue;

	/** 创建者 */
	@ApiModelProperty(value = "创建者")
	private String createBy;

	/** 创建时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	/** 更新者 */
	@ApiModelProperty(value = "更新者")
	private String updateBy;

	/** 更新时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;

	/** 备注 */
	@ApiModelProperty(value = "备注")
	private String remark;

	/** 请求参数 */
	@ApiModelProperty(value = "请求参数")
	private Map<String, Object> params;
	/** 删除状态，默认为非删除状态*/
	@ApiModelProperty(value = "删除状态")
	private String delFlag=DEL_FLAG_NORMAL;

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Map<String, Object> getParams() {
		if (params == null) {
			params = new HashMap<>();
		}
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	/**
	 * 插入前调用，创建时间，修改时间，创建人，修改人赋值
	 */
	public void preInsert(String username) {
		Date now = DateUtils.getNowDate();
		setCreateTime(now);
		setUpdateTime(now);
		LoginUser loginUser = SecurityUtils.getLoginUser();
		if (loginUser != null && StringUtils.equals(username, loginUser.getUsername())) {
			setCreateBy(loginUser.getUsername());
			setUpdateBy(loginUser.getUsername());
		}
	}

	/**
	 * 更新前调用，创建时间，修改时间，创建人，修改人赋值，不推荐，因为会造成非法篡改问题，推荐 preInsert(String username)
	 */
	public void preInsert() {
		Date now = DateUtils.getNowDate();
		setCreateTime(now);
		setUpdateTime(now);
		LoginUser loginUser = SecurityUtils.getLoginUser();
		if (loginUser != null) {
			setCreateBy(loginUser.getUser().getUserId().toString());
			setUpdateBy(loginUser.getUser().getUserId().toString());
		}
	}

	/**
	 * 更新前调用，修改时间，修改人赋值
	 */
	public void preUpdate(String username) {
		setUpdateTime(DateUtils.getNowDate());
		LoginUser loginUser = SecurityUtils.getLoginUser();
		if (loginUser != null && StringUtils.equals(username, loginUser.getUsername())) {
			setUpdateBy(loginUser.getUsername());
		}
	}

	/**
	 * 更新前调用，修改时间，修改人赋值，不推荐，因为会造成非法篡改问题，推荐preUpdate(String username)
	 */
	public void preUpdate() {
		setUpdateTime(DateUtils.getNowDate());
		LoginUser loginUser = SecurityUtils.getLoginUser();
		if (loginUser != null) {
			setUpdateBy(loginUser.getUser().getUserId().toString());
		}
	}
}
