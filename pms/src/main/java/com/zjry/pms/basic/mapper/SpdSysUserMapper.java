package com.zjry.pms.basic.mapper;

import java.util.List;

import com.zjry.pms.basic.domain.UserRole;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.basic.domain.AssetsBasicPublicVo;
import com.zjry.pms.basic.domain.SpdSysUser;
import com.zjry.pms.basic.domain.EmpInfoVo;

/**
 * 用户信息Mapper接口
 * 
 * @author yzh
 * @date 2021-11-15
 */
public interface SpdSysUserMapper extends BaseMapper<SpdSysUser> {
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
     * 删除用户信息
     * 
     * @param userId 用户信息ID
     * @return 结果
     */
    public int deleteSpdSysUserById(Long userId);

    /**
     * 批量删除用户信息
     * 
     * @param userIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSpdSysUserByIds(Long[] userIds);
    
    /**
     * 查询用户根据单位(前台字典)
     * @param deptId 单位id
     * @return 用户集合
     */
    public List<AssetsBasicPublicVo> queryUserInfoByDept(Long deptId);
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
    public List<EmpInfoVo> queryEmpInfo(@Param("compCode") String compCode, @Param("deptCode")String deptCode,@Param("postCode") String postCode,@Param("searchName") String searchName);
    
    /**
     * 
     * @param str
     * @return
     */
    public String querySystemValue(@Param("str")String str);

    void insertRoleList(List<UserRole> list);
}
