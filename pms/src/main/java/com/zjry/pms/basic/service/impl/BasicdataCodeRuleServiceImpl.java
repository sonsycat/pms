package com.zjry.pms.basic.service.impl;

import com.zjry.pms.basic.constant.Constants;
import com.zjry.pms.basic.domain.basicData.BasicdataCodeRule;
import com.zjry.pms.basic.domain.basicData.BasicdataFlowCode;
import com.zjry.pms.basic.mapper.basicData.BasicdataCodeRuleMapper;
import com.zjry.pms.basic.service.IBasicdataCodeRuleService;
import com.zjry.pms.basic.service.IBasicdataFlowCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 编码规则Service业务层处理
 * 
 * @author cyq
 * @date 2021-07-14
 */
@Service
public class BasicdataCodeRuleServiceImpl  implements IBasicdataCodeRuleService {
    @Autowired
    private BasicdataCodeRuleMapper basicdataCodeRuleMapper;
    @Autowired
    private IBasicdataFlowCodeService sysFlowCodeService;







    /**
     * 获取编码
     *@param  fieldName　字段名
     * @param  databaseName　表名
     * @return 结果
     */
    @Override
    public String generateCode(String databaseName, String fieldName,String pcode) {
        //获取编码规则
        BasicdataCodeRule basicdataCodeRule = new BasicdataCodeRule();
        basicdataCodeRule.setRuleTable(databaseName);
        basicdataCodeRule.setRuleColumns(fieldName);
        basicdataCodeRule.setUseFlag("0");
        List<BasicdataCodeRule> encodeRules = basicdataCodeRuleMapper.selectBasicdataCodeRuleList(basicdataCodeRule);
        if(encodeRules.size()==0){
            return null;
        }
        String encode =null;
        BasicdataCodeRule rule =encodeRules.get(0);
        //查询系统流水表
        BasicdataFlowCode sysFlowCode=new BasicdataFlowCode();
        sysFlowCode.setRuleId(rule.getId());
        sysFlowCode.setFlowLen(Long.valueOf(rule.getFlowcodeLength()));
        sysFlowCode.setPrefix(rule.getRulePrefix());
        sysFlowCode.setFlowCode(pcode);
        //编码生成方法，0代表结构化编码规则，1代表非结构化编码
        if(rule.getIsStructs()==null){
            return null;
        }else if(Constants.STATE_0.equals(rule.getIsStructs()) ){
            encode = sysFlowCodeService.getStructCode(sysFlowCode);
        }else if(Constants.STATE_1.equals(rule.getIsStructs()) && Constants.STATE_1.equals(rule.getIsDate()) ){
            encode = sysFlowCodeService.getEntityFlowCode(sysFlowCode);
        } else if(Constants.STATE_1.equals(rule.getIsStructs()) && Constants.STATE_0.equals(rule.getIsDate()) ){
            sysFlowCode.setDateFormat(rule.getDateFormat());
            encode = sysFlowCodeService.getTimeFlowCode(sysFlowCode);
        }
        return encode;
    }

}
