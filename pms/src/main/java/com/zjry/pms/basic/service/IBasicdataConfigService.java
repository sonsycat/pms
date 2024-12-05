package com.zjry.pms.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.basic.domain.basicData.BasicdataConfig;

/**
 * 参数配置Service接口
 * 
 * @author cyq
 * @date 2021-08-03
 */
public interface IBasicdataConfigService extends IService<BasicdataConfig> {


    /**
     * 通过键名获取参数信息
     * @param configKey
     * @return
     */
    public String selectBasicdataConfigByKey(String configKey);

}
