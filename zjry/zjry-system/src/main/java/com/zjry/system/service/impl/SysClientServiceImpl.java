/**
 * 
 */
package com.zjry.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjry.system.domain.SysClient;
import com.zjry.system.mapper.SysClientMapper;
import com.zjry.system.service.ISysClientService;

/**
 * @author yangtao
 *
 */
@Service
public class SysClientServiceImpl implements ISysClientService {

	@Autowired
	private SysClientMapper sysClientMapper;
	
	@Override
	public List<SysClient> selectClientList(SysClient sysClient) {
		return sysClientMapper.selectClientList(sysClient);
	}

}
