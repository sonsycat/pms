package com.zjry.pms.common.service.impl;

import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.utils.DateUtils;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.pms.common.domain.SysFlowHrcode;
import com.zjry.pms.common.mapper.SysFlowcodeMapper;
import com.zjry.pms.common.service.ISysFlowcodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 流水号Service业务层处理
 * 
 * @author zjrj
 * @date 2021-06-22
 */
@Service
public class SysFlowcodeServiceImpl implements ISysFlowcodeService
{
    @Autowired
    private SysFlowcodeMapper sysFlowcodeMapper;

    /**
     * 查询流水号
     * 
     * @param id 流水号ID
     * @return 流水号
     */
    @Override
    public SysFlowHrcode selectSysFlowcodeById(String id)
    {
        return sysFlowcodeMapper.selectSysFlowcodeById(id);
    }

    /**
     * 查询流水号列表
     * 
     * @param sysFlowcode 流水号
     * @return 流水号
     */
    @Override
    public List<SysFlowHrcode> selectSysFlowcodeList(SysFlowHrcode sysFlowcode)
    {
        return sysFlowcodeMapper.selectSysFlowcodeList(sysFlowcode);
    }

    /**
     * 新增流水号
     * 
     * @param sysFlowcode 流水号
     * @return 结果
     */
    @Override
    public int insertSysFlowcode(SysFlowHrcode sysFlowcode)
    {
        Date now = DateUtils.getNowDate();
        sysFlowcode.setCreateTime(now);
        sysFlowcode.setUpdateTime(now);
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if(loginUser != null){
            sysFlowcode.setCreateBy(loginUser.getUsername());
            sysFlowcode.setUpdateBy(loginUser.getUsername());
        }
        return sysFlowcodeMapper.insertSysFlowcode(sysFlowcode);
    }

    /**
     * 修改流水号
     * 
     * @param sysFlowcode 流水号
     * @return 结果
     */
    @Override
    public int updateSysFlowcode(SysFlowHrcode sysFlowcode)
    {
        sysFlowcode.setUpdateTime( DateUtils.getNowDate());
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if(loginUser != null){
            sysFlowcode.setUpdateBy(loginUser.getUsername());
        }
        return sysFlowcodeMapper.updateSysFlowcode(sysFlowcode);
    }

    /**
     * 批量删除流水号
     * 
     * @param ids 需要删除的流水号ID
     * @return 结果
     */
    @Override
    public int deleteSysFlowcodeByIds(String[] ids)
    {
        return sysFlowcodeMapper.deleteSysFlowcodeByIds(ids);
    }

    /**
     * 删除流水号信息
     * 
     * @param id 流水号ID
     * @return 结果
     */
    @Override
    public int deleteSysFlowcodeById(String id)
    {
        return sysFlowcodeMapper.deleteSysFlowcodeById(id);
    }

    @Override
    public String getEntityDateFormatFlowcode(String code, String prefix,long len) {

        String dateStr = DateUtils.dateTime();

        code += dateStr;

        SysFlowHrcode sysFlowcode = sysFlowcodeMapper.selectSysFlowcodeByCode(code);
        if (null == sysFlowcode) {
            sysFlowcode = new SysFlowHrcode();
            sysFlowcode.setCode(code);
            sysFlowcode.setCodeLen(len);
            sysFlowcode.setCurVal(0L);
            sysFlowcode.setPrefix(dateStr);
            Date now = DateUtils.getNowDate();
            sysFlowcode.setCreateTime(now);
            sysFlowcode.setUpdateTime(now);
            LoginUser loginUser = SecurityUtils.getLoginUser();
            if(loginUser != null){
                sysFlowcode.setCreateBy(loginUser.getUsername());
                sysFlowcode.setUpdateBy(loginUser.getUsername());
            }
            sysFlowcode.setDelFlag("0");
            sysFlowcode.setCurVal(sysFlowcode.getCurVal() + 1);
            sysFlowcodeMapper.insertSysFlowcode(sysFlowcode);
        } else {
            sysFlowcode.setCodeLen(len);
            sysFlowcode.setPrefix(dateStr);
            sysFlowcode.setUpdateTime(DateUtils.getNowDate());
            LoginUser loginUser = SecurityUtils.getLoginUser();
            if(loginUser != null){
                sysFlowcode.setUpdateBy(loginUser.getUsername());
            }
            sysFlowcode.setCurVal(sysFlowcode.getCurVal() + 1);
            sysFlowcodeMapper.updateSysFlowcode(sysFlowcode);
        }
        String newFlowcode = String.format("%0" + len + "d", sysFlowcode.getCurVal());
        if (StringUtils.isNotBlank(code)) {
            newFlowcode = dateStr + newFlowcode;
        }
        return prefix+newFlowcode;
    }

    @Override
    public String getEntityFlowcode(String code, String prefix, long len) {

        if (StringUtils.isNotBlank(prefix)) {
            code = code + prefix ;
        }
        SysFlowHrcode sysFlowcode = sysFlowcodeMapper.selectSysFlowcodeByCode(code);
        if (null == sysFlowcode) {
            sysFlowcode = new SysFlowHrcode();
            sysFlowcode.setCode(code);
            sysFlowcode.setCodeLen(len);
            sysFlowcode.setCurVal(0L);
            sysFlowcode.setPrefix(prefix);
            Date now = DateUtils.getNowDate();
            sysFlowcode.setCreateTime(now);
            sysFlowcode.setUpdateTime(now);
            LoginUser loginUser = SecurityUtils.getLoginUser();
            if(loginUser != null){
                sysFlowcode.setCreateBy(loginUser.getUsername());
                sysFlowcode.setUpdateBy(loginUser.getUsername());
            }
            sysFlowcode.setDelFlag("0");
            sysFlowcode.setCurVal(sysFlowcode.getCurVal() + 1);
            sysFlowcodeMapper.insertSysFlowcode(sysFlowcode);
        } else {
            sysFlowcode.setPrefix(prefix);
            sysFlowcode.setCodeLen(len);
            sysFlowcode.setUpdateTime(DateUtils.getNowDate());
            LoginUser loginUser = SecurityUtils.getLoginUser();
            if(loginUser != null){
                sysFlowcode.setUpdateBy(loginUser.getUsername());
            }
            sysFlowcode.setCurVal(sysFlowcode.getCurVal() + 1);
            sysFlowcodeMapper.updateSysFlowcode(sysFlowcode);
        }
        String newFlowcode = String.format("%0" + len + "d", sysFlowcode.getCurVal());

        if (StringUtils.isNotBlank(code)) {
            newFlowcode = code + newFlowcode;
        }
        return newFlowcode;
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
    public String getEntityFlowcodeSc(String code1, String code2, long len) {
        String code = null;
        if(code2 != "" ){
            code = code1+"-"+code2;
        }else{
            code = code1;
        }
        SysFlowHrcode sysFlowcode = sysFlowcodeMapper.selectSysFlowcodeByCode(code);
        if (null == sysFlowcode) {
            sysFlowcode = new SysFlowHrcode();
            sysFlowcode.setCode(code);
            sysFlowcode.setCodeLen(len);
            sysFlowcode.setCurVal(0L);
            sysFlowcode.setPrefix(code);
            Date now = DateUtils.getNowDate();
            sysFlowcode.setCreateTime(now);
            sysFlowcode.setUpdateTime(now);
//            LoginUser loginUser = SecurityUtils.getLoginUser();
//            if(loginUser != null){
//                sysFlowcode.setCreateBy(loginUser.getUsername());
//                sysFlowcode.setUpdateBy(loginUser.getUsername());
//            }
            sysFlowcode.setDelFlag("0");
            sysFlowcode.setCurVal(sysFlowcode.getCurVal() + 1);
            sysFlowcodeMapper.insertSysFlowcode(sysFlowcode);
        } else {
            sysFlowcode.setPrefix(code);
            sysFlowcode.setCodeLen(len);
            sysFlowcode.setUpdateTime(DateUtils.getNowDate());
//            LoginUser loginUser = SecurityUtils.getLoginUser();
//            if(loginUser != null){
//                sysFlowcode.setUpdateBy(loginUser.getUsername());
//            }
            sysFlowcode.setCurVal(sysFlowcode.getCurVal() + 1);
            sysFlowcodeMapper.updateSysFlowcode(sysFlowcode);
        }
        String newFlowcode = String.format("%0" + len + "d", sysFlowcode.getCurVal());
        if (StringUtils.isNotBlank(code)) {
            newFlowcode = code +"-"+ newFlowcode;
        }
        return newFlowcode;
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
    public String getEntityFlowcodeNoCode(String code, String prefix, long len) {

        SysFlowHrcode sysFlowcode = sysFlowcodeMapper.selectSysFlowcodeByCode(code);
        if (null == sysFlowcode) {
            sysFlowcode = new SysFlowHrcode();
            sysFlowcode.setCode(code);
            sysFlowcode.setCodeLen(len);
            sysFlowcode.setCurVal(0L);
            sysFlowcode.setPrefix(prefix);
            Date now = DateUtils.getNowDate();
            sysFlowcode.setCreateTime(now);
            sysFlowcode.setUpdateTime(now);
            LoginUser loginUser = SecurityUtils.getLoginUser();
            if(loginUser != null){
                sysFlowcode.setCreateBy(loginUser.getUsername());
                sysFlowcode.setUpdateBy(loginUser.getUsername());
            }
            sysFlowcode.setDelFlag("0");
            sysFlowcode.setCurVal(sysFlowcode.getCurVal() + 1);
            sysFlowcodeMapper.insertSysFlowcode(sysFlowcode);
        } else {
            sysFlowcode.setPrefix(prefix);
            sysFlowcode.setCodeLen(len);
            sysFlowcode.setUpdateTime(DateUtils.getNowDate());
            LoginUser loginUser = SecurityUtils.getLoginUser();
            if(loginUser != null){
                sysFlowcode.setUpdateBy(loginUser.getUsername());
            }
            sysFlowcode.setCurVal(sysFlowcode.getCurVal() + 1);
            sysFlowcodeMapper.updateSysFlowcode(sysFlowcode);
        }
        String newFlowcode = String.format("%0" + len + "d", sysFlowcode.getCurVal());
        if (StringUtils.isNotBlank(prefix)) {
            newFlowcode = prefix + newFlowcode;
        }
        return newFlowcode;
    }

    /**
     * 生成流水号，流水号规则为 流水号
     *
     * @param code
     * @param len
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public String getEntityFlowcodeNoCode(String code, long len) {

        SysFlowHrcode flowcode = sysFlowcodeMapper.getJxFlowcodeByCode(code);
        if (null == flowcode) {
            flowcode = new SysFlowHrcode();
            flowcode.setCode(code);
            flowcode.setCodeLen(len);
            flowcode.setCurVal(0L);

        } else {
            flowcode.setCodeLen(len);
        }

        flowcode.setCurVal(flowcode.getCurVal() + 1);

        String newFlowcode = String.format("%0" + len + "d", flowcode.getCurVal());

        if(null == flowcode.getId()){
            sysFlowcodeMapper.insertSysFlowcode(flowcode);
        }else{
            sysFlowcodeMapper.updateSysFlowcode(flowcode);
        }
        return newFlowcode;
    }
}
