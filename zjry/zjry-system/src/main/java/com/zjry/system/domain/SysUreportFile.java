package com.zjry.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.zjry.common.core.domain.BaseMybatisPlusEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 报表存储对象 sys_ureport_file
 *
 * @author mingc
 * @date 2021-07-5
 */
@Data
@ApiModel(value = "SysUreportFile", description = "报表存储对象 sys_ureport_file")
public class SysUreportFile extends BaseMybatisPlusEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@ApiModelProperty(value = "ID")
	private Long id;

	/**
	 * 医生编码
	 */
	@ApiModelProperty(value = "文件名称")
	private String fileName;

	/**
	 * 医生姓名
	 */
	@ApiModelProperty(value = "文件内容")
	private byte[] fileContent;

	/** 删除标志（0代表存在 2代表删除） */
	@ApiModelProperty(value = "状态")
	private String delFlag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
				.append("fileName", getFileName()).append("fileContent", getFileContent())
				.append("createBy", getCreateBy()).append("createTime", getCreateTime())
				.append("updateBy", getUpdateBy()).append("updateTime", getUpdateTime()).append("delFlag", getDelFlag())
				.append("remark", getRemark()).toString();
	}
}
