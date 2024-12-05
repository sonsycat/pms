package com.zjry.system.mapper;

import java.util.List;

import com.zjry.system.domain.SysClient;

/**
 * 第三方系统
 * @author yangtao
 */
public interface SysClientMapper {
    
	public List<SysClient> selectClientList(SysClient sysClient);
}
