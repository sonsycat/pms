package com.zjry.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.BaseMybatisPlusEntity;
import com.zjry.common.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.system.mapper.SysFlowCodeMapper;
import com.zjry.system.domain.SysFlowCode;
import com.zjry.system.service.ISysFlowCodeService;

/**
 * 流水号Service业务层处理
 *
 * @author zjry
 * @date 2021-06-22
 */
@Service
public class SysFlowCodeServiceImpl extends ServiceImpl<SysFlowCodeMapper, SysFlowCode> implements ISysFlowCodeService {

    @Autowired
    private SysFlowCodeMapper sysFlowCodeMapper;

    @Override
    public String getEntityDateFormatFlowCode(String code, String prefix, long len) {
        String dateStr = DateUtils.dateTime();
        code += dateStr;
        SysFlowCode sysFlowCode = this.selectSysFlowCodeByCode(code);
        if (null == sysFlowCode) {
            sysFlowCode = new SysFlowCode(code, 1L, len, BaseMybatisPlusEntity.DEL_FLAG_NORMAL, dateStr);
            super.save(sysFlowCode);
        } else {
            sysFlowCode.setFlowLen(len);
            sysFlowCode.setPrefix(dateStr);
            sysFlowCode.setFlowCurVal(sysFlowCode.getFlowCurVal() + 1);
            this.updateById(sysFlowCode);
        }
        String newFlowCode = String.format("%0" + len + "d", sysFlowCode.getFlowCurVal());
        if (StringUtils.isNotBlank(code)) {
            newFlowCode = dateStr + newFlowCode;
        }
        return prefix + newFlowCode;
    }

    @Override
    public String getEntityFlowCode(String code, String prefix, long len) {
        if (StringUtils.isNotBlank(prefix)) {
            code = code + prefix;
        }
        SysFlowCode sysFlowCode = this.selectSysFlowCodeByCode(code);
        if (null == sysFlowCode) {
            sysFlowCode = new SysFlowCode(code, 1L, len, BaseMybatisPlusEntity.DEL_FLAG_NORMAL, prefix);
            super.save(sysFlowCode);
        } else {
            sysFlowCode.setFlowLen(len);
            sysFlowCode.setPrefix(prefix);
            sysFlowCode.setFlowCurVal(sysFlowCode.getFlowCurVal() + 1);
            this.updateById(sysFlowCode);
        }
        String newFlowCode = String.format("%0" + len + "d", sysFlowCode.getFlowCurVal());
        if (StringUtils.isNotBlank(code)) {
            newFlowCode = code + newFlowCode;
        }
        return newFlowCode;
    }

    /**
     * 生成流水号，流水号规则为 code1-code2-流水号
     *
     * @param code1 资源库编码
     * @param code2 资源编码
     * @param len
     * @return
     */
    @Override
    public String getEntityFlowCodeSc(String code1, String code2, long len) {
        String code;
        if (code2 != "") {
            code = code1 + "-" + code2;
        } else {
            code = code1;
        }
        SysFlowCode sysFlowCode = this.selectSysFlowCodeByCode(code);
        if (null == sysFlowCode) {
            sysFlowCode = new SysFlowCode(code, 1L, len, BaseMybatisPlusEntity.DEL_FLAG_NORMAL, code);
            super.save(sysFlowCode);
        } else {
            sysFlowCode.setFlowLen(len);
            sysFlowCode.setPrefix(code);
            sysFlowCode.setFlowCurVal(sysFlowCode.getFlowCurVal() + 1);
            this.updateById(sysFlowCode);
        }
        String newFlowCode = String.format("%0" + len + "d", sysFlowCode.getFlowCurVal());
        if (StringUtils.isNotBlank(code)) {
            newFlowCode = code + "-" + newFlowCode;
        }
        return newFlowCode;
    }


    /**
     * 生成流水号，流水号规则为 prefix + 流水号
     * code仅用来查询当前流水号
     *
     * @param code
     * @param prefix
     * @param len
     * @return
     */
    @Override
    public String getEntityFlowCodeNoCode(String code, String prefix, long len) {

        SysFlowCode sysFlowCode = this.selectSysFlowCodeByCode(code);
        if (null == sysFlowCode) {
            sysFlowCode = new SysFlowCode(code, 1L, len, BaseMybatisPlusEntity.DEL_FLAG_NORMAL, prefix);
            super.save(sysFlowCode);
        } else {
            sysFlowCode.setFlowLen(len);
            sysFlowCode.setPrefix(prefix);
            sysFlowCode.setFlowCurVal(sysFlowCode.getFlowCurVal() + 1);
            this.updateById(sysFlowCode);
        }
        String newFlowCode = String.format("%0" + len + "d", sysFlowCode.getFlowCurVal());
        if (StringUtils.isNotBlank(prefix)) {
            newFlowCode = prefix + "-" + newFlowCode;
        }
        return newFlowCode;
    }

    /**
     * 通过编码查询流水号
     * @param code 编码
     * @return SysFlowCode 流水对象
     */
    private SysFlowCode selectSysFlowCodeByCode(String code) {
        LambdaQueryWrapper<SysFlowCode> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SysFlowCode::getFlowCode,code);
       return  this.getOne(queryWrapper);
    }
}
