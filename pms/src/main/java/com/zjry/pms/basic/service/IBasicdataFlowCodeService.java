package com.zjry.pms.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.basic.domain.basicData.BasicdataFlowCode;

/**
 * 流水Service接口
 * 
 * @author cyq
 * @date 2021-07-14
 */
public interface IBasicdataFlowCodeService extends IService<BasicdataFlowCode> {


    /**
     * 查询结构化编码
     *
     * @param sysFlowCode 流水信息
     * @return 结果
     */
    public String getEntityFlowCode(BasicdataFlowCode sysFlowCode);

    /**
     * 查询非结构化带时间编码
     *
     * @param sysFlowCode 流水信息
     * @return 结果
     */
    public String getTimeFlowCode(BasicdataFlowCode sysFlowCode);

    /**
     * 查询非结构化编码
     *
     * @param sysFlowCode 流水信息
     * @return 结果
     */
    String getStructCode(BasicdataFlowCode sysFlowCode);
}

