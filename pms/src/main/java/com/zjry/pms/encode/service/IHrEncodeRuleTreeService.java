package com.zjry.pms.encode.service;

import java.util.List;

import com.zjry.pms.encode.domain.HrEncodeRuleTree;

/**
 * 编码规则树形Service接口
 * 
 * @author syw
 * @date 2021-06-25
 */
public interface IHrEncodeRuleTreeService 
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
     * 批量删除编码规则树形
     * 
     * @param ids 需要删除的编码规则树形ID
     * @return 结果
     */
    public int deleteHrEncodeRuleTreeByIds(Long[] ids);

    /**
     * 删除编码规则树形信息
     * 
     * @param id 编码规则树形ID
     * @return 结果
     */
    public int deleteHrEncodeRuleTreeById(Long id);


    /**
     *按规则生成树编码(只适合于纯数字的数编码带字母的不行)
     * @param databaseName 数据库表名
     * @param  fieldName 字段名
     * @param parentCode 父编码
     * @return String 按数据库规则生成的流水号
     * @throws Exception
     * */
    public String generateTreeEncode(String databaseName, String fieldName,String parentCode)throws Exception;
}
