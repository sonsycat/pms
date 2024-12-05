package com.zjry.pms.basic.mapper.basicData;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.basic.domain.basicData.BasicdataConfig;

/**
 * 参数配置Mapper接口
 * 
 * @author cyq
 * @date 2021-08-03
 */
public interface BasicdataConfigMapper extends BaseMapper<BasicdataConfig> {
    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数配置信息
     */
    BasicdataConfig checkConfigKeyUnique(String configKey);
}
