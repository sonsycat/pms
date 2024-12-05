package com.zjry.system.domain.vo;

import com.zjry.common.core.domain.BaseEntity;

/**
 * 操作日志统计
 * @author yangtao
 */
public class SysOperLogStatistics extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 操作人员登录名 */
    private String operName;
    /** 模块标题 */
    private String title;
    /** 操作次数 */
    private Integer operCount;
    /** 操作路径 */
    private String operPath;
    /** 菜单id */
    private Long menuId;
    /** 系统标识 */
    private String systemId;
    
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getOperCount() {
		return operCount;
	}
	public void setOperCount(Integer operCount) {
		this.operCount = operCount;
	}
	public String getOperPath() {
		return operPath;
	}
	public void setOperPath(String operPath) {
		this.operPath = operPath;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
    
}
