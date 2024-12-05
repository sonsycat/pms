package com.zjry.system.service;

import java.util.List;

import com.zjry.system.domain.SysClient;

/**
 * 第三方系统业务层
 * @author yangtao
 */
public interface ISysClientService {
    public List<SysClient> selectClientList(SysClient sysClient);
}
