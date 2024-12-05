package com.zjry.pms.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.pms.basic.domain.basicData.BasicdataFlowCode;
import com.zjry.pms.basic.mapper.basicData.BasicdataFlowCodeMapper;
import com.zjry.pms.basic.service.IBasicdataFlowCodeService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 流水Service业务层处理
 * 
 * @author cyq
 * @date 2021-07-14
 */
@Service
public class BasicdataFlowCodeServiceImpl extends ServiceImpl<BasicdataFlowCodeMapper, BasicdataFlowCode> implements IBasicdataFlowCodeService {
    @Autowired
    private BasicdataFlowCodeMapper sysFlowCodeMapper;

    /**
     * 生成流水号，结构化
     *
     * @param sysFlowCode
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public String getStructCode(BasicdataFlowCode sysFlowCode) {
        BasicdataFlowCode flowcode = sysFlowCodeMapper.getStructCode(sysFlowCode);
        if (null == flowcode) {
            flowcode = new BasicdataFlowCode();
            flowcode.setFlowCode(sysFlowCode.getFlowCode());
            flowcode.setFlowLen(sysFlowCode.getFlowLen());
            flowcode.setRuleId(sysFlowCode.getRuleId());
            flowcode.setFlowCurVal(1L);
            flowcode.setDelFlag("0");
            sysFlowCodeMapper.insertSysFlowCode(flowcode);
        } else {
            flowcode.setFlowCurVal(flowcode.getFlowCurVal() + 1);
            sysFlowCodeMapper.updateSysFlowCode(flowcode);
        }
        String newFlowcode = String.format("%0" + flowcode.getFlowLen() + "d", flowcode.getFlowCurVal());
        if (StringUtils.isNotBlank(flowcode.getFlowCode())) {
            newFlowcode = flowcode.getFlowCode() + newFlowcode;
        }
        return newFlowcode;
    }

    /**
     * 生成流水号，非结构化无日期
     *
     * @param sysFlowCode
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public String getEntityFlowCode(BasicdataFlowCode sysFlowCode) {
        String  code =sysFlowCode.getFlowCode();
        if (StringUtils.isNotBlank(sysFlowCode.getPrefix())) {
            code = sysFlowCode.getPrefix();
        }
        BasicdataFlowCode flowcode = sysFlowCodeMapper.getFlowCodeByCode(sysFlowCode);
        if (null == flowcode) {
            flowcode = new BasicdataFlowCode();
            flowcode.setFlowCode(code);
            flowcode.setRuleId(sysFlowCode.getRuleId());
            flowcode.setFlowLen(sysFlowCode.getFlowLen());
            flowcode.setFlowCurVal(1L);
            flowcode.setPrefix(sysFlowCode.getPrefix());
            flowcode.setDelFlag("0");
            sysFlowCodeMapper.insertSysFlowCode(flowcode);
        } else {
            flowcode.setPrefix(sysFlowCode.getPrefix());
            flowcode.setFlowLen(sysFlowCode.getFlowLen());
            flowcode.setFlowCurVal(flowcode.getFlowCurVal() + 1);
            sysFlowCodeMapper.updateSysFlowCode(flowcode);
        }
        String newFlowcode = String.format("%0" + flowcode.getFlowLen() + "d", flowcode.getFlowCurVal());
        if (StringUtils.isNotBlank(flowcode.getFlowCode())) {
            newFlowcode = code + newFlowcode;
        }
        return newFlowcode;
    }

    /**
     * 生成流水号，非结构化带日期
     *
     * @param sysFlowCode
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public String getTimeFlowCode(BasicdataFlowCode sysFlowCode) {
        String  code =sysFlowCode.getFlowCode();
        Date now = new Date();
        String dateStr =  DateFormatUtils.format(now, sysFlowCode.getDateFormat());
        if (StringUtils.isNotBlank(sysFlowCode.getPrefix())) {
            code = sysFlowCode.getPrefix() +dateStr ;
        }else{
            code = dateStr;
        }
        sysFlowCode.setFlowCode(code);
        BasicdataFlowCode flowcode = sysFlowCodeMapper.getStructCode(sysFlowCode);
        if (null == flowcode) {
            flowcode = new BasicdataFlowCode();
            flowcode.setFlowCode(code);
            flowcode.setRuleId(sysFlowCode.getRuleId());
            flowcode.setFlowLen(sysFlowCode.getFlowLen());
            flowcode.setFlowCurVal(1L);
            flowcode.setPrefix(sysFlowCode.getPrefix());
            flowcode.setDelFlag("0");
            sysFlowCodeMapper.insertSysFlowCode(flowcode);
        } else {
            flowcode.setPrefix(sysFlowCode.getPrefix());
            flowcode.setFlowLen(sysFlowCode.getFlowLen());
            if(flowcode.getFlowCode().equals(code)){
                flowcode.setFlowCurVal(flowcode.getFlowCurVal() + 1);
            }else {
                flowcode.setFlowCurVal(1L);
                flowcode.setFlowCode(code);
            }
            sysFlowCodeMapper.updateSysFlowCode(flowcode);
        }
        String newFlowcode = String.format("%0" + flowcode.getFlowLen() + "d", flowcode.getFlowCurVal());
        if (StringUtils.isNotBlank(flowcode.getFlowCode())) {
            newFlowcode = code + newFlowcode;
        }
        return newFlowcode;
    }
}
