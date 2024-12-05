package com.zjry.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.system.domain.SysPersistentToken;
import com.zjry.system.mapper.SysPersistentTokenMapper;
import com.zjry.system.service.ISysPersistentTokenService;
import org.springframework.stereotype.Service;

/**
 * @Author: mingc
 * @DateTime: 2021/7/15
 */
@Service
public class SysPersistentTokenServiceImpl extends ServiceImpl<SysPersistentTokenMapper, SysPersistentToken> implements ISysPersistentTokenService {
}
