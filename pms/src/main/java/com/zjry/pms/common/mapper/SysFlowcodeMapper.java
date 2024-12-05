package com.zjry.pms.common.mapper;



import java.util.List;

import com.zjry.pms.common.domain.SysFlowHrcode;

/**
 * 流水号Mapper接口
 * 
 * @author zjrj
 * @date 2021-06-22
 */
public interface SysFlowcodeMapper 
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
     * 删除流水号
     * 
     * @param id 流水号ID
     * @return 结果
     */
    public int deleteSysFlowcodeById(String id);

    /**
     * 批量删除流水号
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysFlowcodeByIds(String[] ids);

    /**
     * 查询
     *
     * @param code
     * @return 结果
     */
    public  SysFlowHrcode selectSysFlowcodeByCode(String code);

    /**
     * 根据code查询当前编号
     * @param code
     * @return
     */
    SysFlowHrcode getJxFlowcodeByCode(String code);


}
