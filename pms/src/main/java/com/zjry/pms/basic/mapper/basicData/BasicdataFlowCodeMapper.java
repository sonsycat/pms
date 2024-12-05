package com.zjry.pms.basic.mapper.basicData;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.basic.domain.basicData.BasicdataFlowCode;


/**
 * 流水Mapper接口
 * 
 * @author cyq
 * @date 2021-07-14
 */
public interface BasicdataFlowCodeMapper extends BaseMapper<BasicdataFlowCode> {

    /**
     * 新增流水
     * 
     * @param sysFlowCode 流水
     * @return 结果
     */
    public int insertSysFlowCode(BasicdataFlowCode sysFlowCode);

    /**
     * 修改流水
     * 
     * @param sysFlowCode 流水
     * @return 结果
     */
    public int updateSysFlowCode(BasicdataFlowCode sysFlowCode);


    /**
     * 查询流水信息
     *
     * @param sysFlowCode 流水信息
     * @return 结果
     */
    BasicdataFlowCode getFlowCodeByCode(BasicdataFlowCode sysFlowCode);
    /**
     * 查询流水信息
     *
     * @param sysFlowCode 流水信息
     * @return 结果
     */
    BasicdataFlowCode getStructCode(BasicdataFlowCode sysFlowCode);

}
