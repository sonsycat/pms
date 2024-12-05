package com.zjry.pms.encode.service;

import java.util.List;

import com.zjry.pms.encode.domain.HrEncodeRule;

/**
 * 编码规则Service接口
 * 
 * @author syw
 * @date 2021-06-25
 */
public interface IHrEncodeRuleService 
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
     * 批量删除编码规则
     * 
     * @param ids 需要删除的编码规则ID
     * @return 结果
     */
    public int deleteHrEncodeRuleByIds(Long[] ids);

    /**
     * 删除编码规则信息
     * 
     * @param id 编码规则ID
     * @return 结果
     */
    public int deleteHrEncodeRuleById(Long id);

    /**
     * 按规则生成流水号
     * @param databaseName 数据库表名
     * @param  fieldName 字段名
     * @return String 按数据库规则生成的流水号
     *
     * */
    public  String generateEncode(String databaseName, String fieldName);

    /**
     *按规则生成条码
     * @param databaseName 数据库表名
     * @param  fieldName 字段名
     * */
    public  String generateBarCode(String databaseName, String fieldName);

    String generateMaterialBarCode(String barCode, int len);
}
