package com.zjry.pms.basic.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.common.core.domain.entity.SysUser;
import com.zjry.pms.basic.domain.SpdSysUser;
import com.zjry.pms.basic.domain.EmpInfoVo;
import com.zjry.pms.basic.domain.AssetsBasicPublicVo;

/**
 * 用户信息Service接口
 * 
 * @author yzh
 * @date 2021-11-15
 */
public interface ISpdSysUserService extends IService<SpdSysUser> {
    /**
     * 查询用户信息
     * 
     * @param userId 用户信息ID
     * @return 用户信息
     */
    public SpdSysUser selectSpdSysUserById(Long userId);

    /**
     * 查询用户信息列表
     * 
     * @param spdSysUser 用户信息
     * @return 用户信息集合
     */
    public List<SpdSysUser> selectSpdSysUserList(SpdSysUser spdSysUser);

    /**
     * 新增用户信息
     * 
     * @param spdSysUser 用户信息
     * @return 结果
     */
    public int insertSpdSysUser(SpdSysUser spdSysUser);

    /**
     * 修改用户信息
     * 
     * @param spdSysUser 用户信息
     * @return 结果
     */
    public int updateSpdSysUser(SpdSysUser spdSysUser);
    
    /**
     * 修改用户信息
     * 
     * @param spdSysUser 用户信息
     * @return 结果
     */
    public int UpdateSpdSysUserByCode(SpdSysUser spdSysUser);

    /**
     * 批量删除用户信息
     * 
     * @param userIds 需要删除的用户信息ID
     * @return 结果
     */
    public int deleteSpdSysUserByIds(Long[] userIds);

    /**
     * 删除用户信息信息
     * 
     * @param userId 用户信息ID
     * @return 结果
     */
    public int deleteSpdSysUserById(Long userId);
    
    /**
     * 查询所有用户(前台字典)
     *
     * @return 用户集合
     */
    public List<AssetsBasicPublicVo> queryAllUserInfo();
    /**
     * 
     * @return
     */
    public List<EmpInfoVo> empInfo();
    
    /**
     * 
     * @param compCode
     * @param deptCode
     * @param postCode
     * @param searchName
     * @return
     */
    public List<EmpInfoVo> queryEmpInfo(String compCode, String deptCode, String postCode,String searchName);
    
    /**
     * 
     * @param str
     * @return
     */
    public String querySystemValue(String str);

    int insertUser(SysUser sysUser);

    int updateUser(SysUser user);
}
