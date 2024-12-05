package com.zjry.pms.encode.service.impl;

import com.zjry.common.utils.DateUtils;
import com.zjry.pms.common.service.ISysFlowcodeService;
import com.zjry.pms.common.service.impl.SysFlowcodeServiceImpl;
import com.zjry.pms.encode.domain.HrEncodeRule;
import com.zjry.pms.encode.mapper.HrEncodeRuleMapper;
import com.zjry.pms.encode.service.IHrEncodeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 编码规则Service业务层处理
 * 
 * @author syw
 * @date 2021-06-25
 */
@Service
public class HrEncodeRuleServiceImpl implements IHrEncodeRuleService 
{
    @Autowired
    private HrEncodeRuleMapper hrEncodeRuleMapper;
    @Autowired
    private ISysFlowcodeService hrEncodeRuleService;
    @Autowired
    private SysFlowcodeServiceImpl sysFlowcodeService;

    /**
     * 查询编码规则
     * 
     * @param id 编码规则ID
     * @return 编码规则
     */
    @Override
    public HrEncodeRule selectHrEncodeRuleById(Long id)
    {
        return hrEncodeRuleMapper.selectHrEncodeRuleById(id);
    }

    /**
     * 查询编码规则列表
     * 
     * @param hrEncodeRule 编码规则
     * @return 编码规则
     */
    @Override
    public List<HrEncodeRule> selectHrEncodeRuleList(HrEncodeRule hrEncodeRule)
    {
        return hrEncodeRuleMapper.selectHrEncodeRuleList(hrEncodeRule);
    }

    /**
     * 新增编码规则
     * 
     * @param hrEncodeRule 编码规则
     * @return 结果
     */
    @Override
    public int insertHrEncodeRule(HrEncodeRule hrEncodeRule)
    {
        hrEncodeRule.setCreateTime(DateUtils.getNowDate());
        return hrEncodeRuleMapper.insertHrEncodeRule(hrEncodeRule);
    }

    /**
     * 修改编码规则
     * 
     * @param hrEncodeRule 编码规则
     * @return 结果
     */
    @Override
    public int updateHrEncodeRule(HrEncodeRule hrEncodeRule)
    {
        hrEncodeRule.setUpdateTime(DateUtils.getNowDate());
        return hrEncodeRuleMapper.updateHrEncodeRule(hrEncodeRule);
    }

    /**
     * 按规则生成流水号
     * databaseName 数据库表名
     * fieldName 字段名
     * */
    @Override
    public String generateEncode(String databaseName, String fieldName) {
        HrEncodeRule her = new HrEncodeRule();
        her.setRuleDb(databaseName);
        her.setRuleColumn(fieldName);
        List<HrEncodeRule> hrEncodeRules = hrEncodeRuleMapper.selectHrEncodeRuleList(her);
        if(hrEncodeRules.size()>0){
            HrEncodeRule heRule =  hrEncodeRules.get(0);
            String date = null;
            if (ObjectUtils.isEmpty(heRule.getRuleSecond())) {
                date = "";
            } else {
                date = com.zjry.pms.common.utils.DateUtils.getDate(heRule.getRuleSecond());
            }
            String encode = hrEncodeRuleService.getEntityFlowcodeSc(heRule.getRuleFirst(),date,Long.parseLong(heRule.getRuleThird()));
            return encode;
        }
        return null;
    }

    @Override
    public String generateBarCode(String databaseName, String fieldName) {
        HrEncodeRule her = new HrEncodeRule();
        her.setRuleDb(databaseName);
        her.setRuleColumn(fieldName);
        List<HrEncodeRule> hrEncodeRules = hrEncodeRuleMapper.selectHrEncodeRuleList(her);
        if(hrEncodeRules.size()>0){
            HrEncodeRule heRule =  hrEncodeRules.get(0);
            String date = null;
            if (ObjectUtils.isEmpty(heRule.getRuleSecond())) {
                date = "";
            } else {
                date = com.zjry.pms.common.utils.DateUtils.getDate(heRule.getRuleSecond());
            }
            String encode = hrEncodeRuleService.getEntityFlowcode(heRule.getId().toString(),date,Long.parseLong(heRule.getRuleThird()));
            return encode;
        }
        return null;
    }

    @Override
    public String generateMaterialBarCode(String barCode, int len) {
        return  barCode+sysFlowcodeService.getEntityFlowcodeNoCode(barCode,len);
    }

    /**
     * 批量删除编码规则
     * 
     * @param ids 需要删除的编码规则ID
     * @return 结果
     */
    @Override
    public int deleteHrEncodeRuleByIds(Long[] ids)
    {
        return hrEncodeRuleMapper.deleteHrEncodeRuleByIds(ids);
    }

    /**
     * 删除编码规则信息
     * 
     * @param id 编码规则ID
     * @return 结果
     */
    @Override
    public int deleteHrEncodeRuleById(Long id)
    {
        return hrEncodeRuleMapper.deleteHrEncodeRuleById(id);
    }
}
