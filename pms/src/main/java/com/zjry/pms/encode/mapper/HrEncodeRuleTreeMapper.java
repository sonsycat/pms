package com.zjry.pms.encode.mapper;

import java.util.List;

import com.zjry.pms.encode.domain.HrEncodeRuleTree;

/**
 * 编码规则树形Mapper接口
 * 
 * @author syw
 * @date 2021-06-25
 */
public interface HrEncodeRuleTreeMapper 
{
    /**
     * 查询编码规则树形
     * 
     * @param id 编码规则树形ID
     * @return 编码规则树形
     */
    public HrEncodeRuleTree selectHrEncodeRuleTreeById(Long id);

    /**
     * 查询编码规则树形列表
     * 
     * @param hrEncodeRuleTree 编码规则树形
     * @return 编码规则树形集合
     */
    public List<HrEncodeRuleTree> selectHrEncodeRuleTreeList(HrEncodeRuleTree hrEncodeRuleTree);

    /**
     * 新增编码规则树形
     * 
     * @param hrEncodeRuleTree 编码规则树形
     * @return 结果
     */
    public int insertHrEncodeRuleTree(HrEncodeRuleTree hrEncodeRuleTree);

    /**
     * 修改编码规则树形
     * 
     * @param hrEncodeRuleTree 编码规则树形
     * @return 结果
     */
    public int updateHrEncodeRuleTree(HrEncodeRuleTree hrEncodeRuleTree);

    /**
     * 删除编码规则树形
     * 
     * @param id 编码规则树形ID
     * @return 结果
     */
    public int deleteHrEncodeRuleTreeById(Long id);

    /**
     * 批量删除编码规则树形
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteHrEncodeRuleTreeByIds(Long[] ids);

}
