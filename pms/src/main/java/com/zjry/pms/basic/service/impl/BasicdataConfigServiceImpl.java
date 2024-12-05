package com.zjry.pms.basic.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.constant.Constants;
import com.zjry.common.core.redis.RedisCache;
import com.zjry.common.core.text.Convert;
import com.zjry.common.utils.StringUtils;

import com.zjry.pms.basic.domain.basicData.BasicdataConfig;
import com.zjry.pms.basic.mapper.basicData.BasicdataConfigMapper;
import com.zjry.pms.basic.service.IBasicdataConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 参数配置Service业务层处理
 * 
 * @author cyq
 * @date 2021-08-03
 */
@Service
public class BasicdataConfigServiceImpl extends ServiceImpl<BasicdataConfigMapper, BasicdataConfig> implements IBasicdataConfigService {
    @Autowired
    private BasicdataConfigMapper basicdataConfigMapper;
    @Autowired
    private RedisCache redisCache;




    /**
     * 通过键名获取参数信息
     * @param configKey
     * @return
     */
    @Override
    public String selectBasicdataConfigByKey(String configKey) {
        //获取缓存中的参数信息
        String configValue = Convert.toStr(redisCache.getCacheObject(this.getCacheKey(configKey)));
        if (StringUtils.isNotEmpty(configValue)) {
            return configValue;
        } else {
            BasicdataConfig retConfig = basicdataConfigMapper.checkConfigKeyUnique(configKey);
            if (StringUtils.isNotNull(retConfig)) {
                redisCache.setCacheObject(this.getCacheKey(configKey), retConfig.getConfigValue());
                return retConfig.getConfigValue();
            } else {
                return "";
            }
        }
    }


    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey) {
        return Constants.SYS_CONFIG_KEY + configKey;
    }

}
