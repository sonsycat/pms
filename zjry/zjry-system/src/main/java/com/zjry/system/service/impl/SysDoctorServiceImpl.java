package com.zjry.system.service.impl;

import java.util.List;

import cn.hutool.core.util.StrUtil;
import com.zjry.common.utils.CodeUtils;
import com.zjry.common.utils.DateUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.system.mapper.SysDoctorMapper;
import com.zjry.system.domain.SysDoctor;
import com.zjry.system.service.ISysDoctorService;

/**
 * 医生信息Service业务层处理
 * 
 * @author mingc
 * @date 2021-06-18
 */
@Service
public class SysDoctorServiceImpl implements ISysDoctorService 
{
    @Autowired
    private SysDoctorMapper sysDoctorMapper;

    /**
     * 查询医生信息
     * 
     * @param id 医生信息ID
     * @return 医生信息
     */
    @Override
    public SysDoctor selectSysDoctorById(Long id)
    {
        return sysDoctorMapper.selectSysDoctorById(id);
    }

    /**
     * 查询医生信息列表
     * 
     * @param sysDoctor 医生信息
     * @return 医生信息
     */
    @Override
    public List<SysDoctor> selectSysDoctorList(SysDoctor sysDoctor)
    {
        return sysDoctorMapper.selectSysDoctorList(sysDoctor);
    }

    /**
     * 新增医生信息
     * 
     * @param sysDoctor 医生信息
     * @return 结果
     */
    @Override
    public int insertSysDoctor(SysDoctor sysDoctor)
    {
        //处理医生编码
        if (StrUtil.isEmpty(sysDoctor.getDocCode())) {
            List<String> codeList = sysDoctorMapper.getAllCode();
            sysDoctor.setDocCode(CodeUtils.generateBusiCode("DOC", 5, codeList));
        }
        sysDoctor.setCreateTime(DateUtils.getNowDate());
        return sysDoctorMapper.insertSysDoctor(sysDoctor);
    }

    /**
     * 修改医生信息
     * 
     * @param sysDoctor 医生信息
     * @return 结果
     */
    @Override
    public int updateSysDoctor(SysDoctor sysDoctor)
    {
        sysDoctor.setUpdateTime(DateUtils.getNowDate());
        return sysDoctorMapper.updateSysDoctor(sysDoctor);
    }

    /**
     * 批量删除医生信息
     * 
     * @param ids 需要删除的医生信息ID
     * @return 结果
     */
    @Override
    public int deleteSysDoctorByIds(Long[] ids)
    {
        return sysDoctorMapper.deleteSysDoctorByIds(ids);
    }

    /**
     * 删除医生信息信息
     * 
     * @param id 医生信息ID
     * @return 结果
     */
    @Override
    public int deleteSysDoctorById(Long id)
    {
        return sysDoctorMapper.deleteSysDoctorById(id);
    }
}
