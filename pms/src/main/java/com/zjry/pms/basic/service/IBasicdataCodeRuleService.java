package com.zjry.pms.basic.service;

/**
 * 编码规则Service接口
 * 
 * @author cyq
 * @date 2021-07-14
 */
public interface IBasicdataCodeRuleService  {


    /**
     * 获取编码
     *@param  fieldName　字段名
     * @param  databaseName　表名
     *  @param  pcode　父编码
     * @return 结果
     */
    String generateCode(String databaseName, String fieldName,String pcode);


}
