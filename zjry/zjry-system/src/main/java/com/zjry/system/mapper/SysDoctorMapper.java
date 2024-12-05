package com.zjry.system.mapper;

import java.util.List;
import com.zjry.system.domain.SysDoctor;

/**
 * 医生信息Mapper接口
 * 
 * @author mingc
 * @date 2021-06-18
 */
public interface SysDoctorMapper 
{
    /**
     * 查询医生信息
     * 
     * @param id 医生信息ID
     * @return 医生信息
     */
    public SysDoctor selectSysDoctorById(Long id);

    /**
     * 查询医生信息列表
     * 
     * @param sysDoctor 医生信息
     * @return 医生信息集合
     */
    public List<SysDoctor> selectSysDoctorList(SysDoctor sysDoctor);

    /**
     * 新增医生信息
     * 
     * @param sysDoctor 医生信息
     * @return 结果
     */
    public int insertSysDoctor(SysDoctor sysDoctor);

    /**
     * 修改医生信息
     * 
     * @param sysDoctor 医生信息
     * @return 结果
     */
    public int updateSysDoctor(SysDoctor sysDoctor);

    /**
     * 删除医生信息
     * 
     * @param id 医生信息ID
     * @return 结果
     */
    public int deleteSysDoctorById(Long id);

    /**
     * 批量删除医生信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysDoctorByIds(Long[] ids);

    /**
     * 获取所有医生编码
     *
     * @return
     */
    List<String> getAllCode();
}
