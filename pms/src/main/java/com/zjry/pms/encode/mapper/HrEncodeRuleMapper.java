package com.zjry.pms.encode.mapper;

import java.util.List;

import com.zjry.pms.encode.domain.HrEncodeRule;

/**
 * 编码规则Mapper接口
 * 
 * @author syw
 * @date 2021-06-25
 */
public interface HrEncodeRuleMapper 
{
    /**
     * 查询编码规则
     * 
     * @param id 编码规则ID
     * @return 编码规则
     */
    public HrEncodeRule selectHrEncodeRuleById(Long id);

    /**
     * 查询编码规则列表
     * 
     * @param hrEncodeRule 编码规则
     * @return 编码规则集合
     */
    public List<HrEncodeRule> selectHrEncodeRuleList(HrEncodeRule hrEncodeRule);

    /**
     * 新增编码规则
     * 
     * @param hrEncodeRule 编码规则
     * @return 结果
     */
    public int insertHrEncodeRule(HrEncodeRule hrEncodeRule);

    /**
     * 修改编码规则
     * 
     * @param hrEncodeRule 编码规则
     * @return 结果
     */
    public int updateHrEncodeRule(HrEncodeRule hrEncodeRule);

    /**
     * 删除编码规则
     * 
     * @param id 编码规则ID
     * @return 结果
     */
    public int deleteHrEncodeRuleById(Long id);

    /**
     * 批量删除编码规则
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteHrEncodeRuleByIds(Long[] ids);
}
