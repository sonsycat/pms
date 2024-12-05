package com.zjry.pms.common.service;

import java.util.List;

import com.zjry.pms.common.domain.SysFlowHrcode;

/**
 * 流水号Service接口
 * 
 * @author zjrj
 * @date 2021-06-22
 */
public interface ISysFlowcodeService 
{
    /**
     * 查询流水号
     * 
     * @param id 流水号ID
     * @return 流水号
     */
    public SysFlowHrcode selectSysFlowcodeById(String id);

    /**
     * 查询流水号列表
     * 
     * @param sysFlowcode 流水号
     * @return 流水号集合
     */
    public List<SysFlowHrcode> selectSysFlowcodeList(SysFlowHrcode sysFlowcode);

    /**
     * 新增流水号
     * 
     * @param sysFlowcode 流水号
     * @return 结果
     */
    public int insertSysFlowcode(SysFlowHrcode sysFlowcode);

    /**
     * 修改流水号
     * 
     * @param sysFlowcode 流水号
     * @return 结果
     */
    public int updateSysFlowcode(SysFlowHrcode sysFlowcode);

    /**
     * 批量删除流水号
     * 
     * @param ids 需要删除的流水号ID
     * @return 结果
     */
    public int deleteSysFlowcodeByIds(String[] ids);

    /**
     * 删除流水号信息
     * 
     * @param id 流水号ID
     * @return 结果
     */
    public int deleteSysFlowcodeById(String id);

    /**
     * 删除流水号信息
     *
     * @param  code
     * @param  prefix
     * @param  len
     * @return 结果
     */
    public String getEntityDateFormatFlowcode(String code, String prefix, long len);

    /**
     * 删除流水号信息
     *
     * @param code
     * @param prefix
     * @param len
     * @return 结果
     */
    public String getEntityFlowcode(String code, String prefix, long len);

    /**
     * 删除流水号信息
     *
     * @param code1
     * @param code2
     * @param len
     * @return 结果
     */
    public String getEntityFlowcodeSc(String code1, String code2, long len);

    /**
     * 删除流水号信息
     *
     * @param code
     * @param prefix
     * @param len
     * @return 结果
     */
    public String getEntityFlowcodeNoCode(String code, String prefix, long len);

    /**
     * 生成流水号，流水号规则为 流水号
     *
     * @param code
     * @param len
     * @return 结果
     */
    public String getEntityFlowcodeNoCode(String code, long len);



}
