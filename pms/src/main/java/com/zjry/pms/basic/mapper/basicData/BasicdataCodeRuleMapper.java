package com.zjry.pms.basic.mapper.basicData;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.basic.domain.basicData.BasicdataCodeRule;


import java.util.List;

/**
 * 编码规则Mapper接口
 * 
 * @author cyq
 * @date 2021-07-14
 */
public interface BasicdataCodeRuleMapper extends BaseMapper<BasicdataCodeRule> {

    /**
     * 查询编码规则列表
     * 
     * @param basicdataCodeRule 编码规则
     * @return 编码规则集合
     */
    public List<BasicdataCodeRule> selectBasicdataCodeRuleList(BasicdataCodeRule basicdataCodeRule);

}
