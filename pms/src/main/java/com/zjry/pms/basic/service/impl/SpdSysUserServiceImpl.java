package com.zjry.pms.basic.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.common.core.domain.entity.SysUser;
import com.zjry.common.utils.DateUtils;
import com.zjry.common.utils.StringUtils;
import com.zjry.pms.basic.domain.UserRole;
import com.zjry.pms.common.Constants;
import com.zjry.system.domain.SysUserPost;
import com.zjry.system.mapper.SysUserMapper;
import com.zjry.system.mapper.SysUserPostMapper;
import com.zjry.system.mapper.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.basic.mapper.SpdSysUserMapper;
import com.zjry.pms.basic.domain.SpdSysUser;
import com.zjry.pms.basic.service.ISpdSysUserService;
import com.zjry.pms.basic.domain.AssetsBasicPublicVo;
import com.zjry.pms.common.utils.UserUtils;
import com.zjry.pms.basic.domain.EmpInfoVo;

/**
 * 用户信息Service业务层处理
 * 
 * @author yzh
 * @date 2021-11-15
 */
@Service
public class SpdSysUserServiceImpl extends ServiceImpl<SpdSysUserMapper, SpdSysUser> implements ISpdSysUserService {
    @Autowired
    private SpdSysUserMapper spdSysUserMapper;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysUserRoleMapper userRoleMapper;
    @Autowired
    private SysUserPostMapper userPostMapper;

    /**
     * 查询用户信息
     * 
     * @param userId 用户信息ID
     * @return 用户信息
     */
    @Override
    public SpdSysUser selectSpdSysUserById(Long userId) {
        return spdSysUserMapper.selectSpdSysUserById(userId);
    }

    /**
     * 查询用户信息列表
     * 
     * @param spdSysUser 用户信息
     * @return 用户信息
     */
    @Override
    public List<SpdSysUser> selectSpdSysUserList(SpdSysUser spdSysUser) {
        return spdSysUserMapper.selectSpdSysUserList(spdSysUser);
    }
    
    /**
     * 查询所有用户(前台字典)
     *
     * @return 用户集合
     */
    @Override
    public List<AssetsBasicPublicVo> queryAllUserInfo() {
        SysUser sysUser = UserUtils.getSysUser();
            List<AssetsBasicPublicVo> assetsBasicPublicVos = spdSysUserMapper.queryUserInfoByDept(sysUser.getDeptId());
            if (assetsBasicPublicVos.size()==0){
                return null;
            }
        return assetsBasicPublicVos;
    }
    
    /**
     * 
     */
    @Override
    public List<EmpInfoVo> empInfo() {
        return spdSysUserMapper.empInfo();
    }
    
    /**
     * 
     */
    @Override
    public List<EmpInfoVo> queryEmpInfo(String compCode, String deptCode, String postCode ,String searchName) {
        return spdSysUserMapper.queryEmpInfo(compCode,deptCode,postCode,searchName);
    }

    /**
     * 新增用户信息
     * 
     * @param spdSysUser 用户信息
     * @return 结果
     */
    @Override
    public int insertSpdSysUser(SpdSysUser spdSysUser) {
        spdSysUser.setCreateTime(DateUtils.getNowDate());
        return spdSysUserMapper.insertSpdSysUser(spdSysUser);
    }

    /**
     * 修改用户信息
     * 
     * @param spdSysUser 用户信息
     * @return 结果
     */
    @Override
    public int updateSpdSysUser(SpdSysUser spdSysUser) {
        spdSysUser.setUpdateTime(DateUtils.getNowDate());
        return spdSysUserMapper.updateSpdSysUser(spdSysUser);
    }
    
    /**
     * 修改用户信息
     * 
     * @param spdSysUser 用户信息
     * @return 结果
     */
    @Override
    public int UpdateSpdSysUserByCode(SpdSysUser spdSysUser) {
        spdSysUser.setUpdateTime(DateUtils.getNowDate());
        return spdSysUserMapper.UpdateSpdSysUserByCode(spdSysUser);
    }
    /**
     * 批量删除用户信息
     * 
     * @param userIds 需要删除的用户信息ID
     * @return 结果
     */
    @Override
    public int deleteSpdSysUserByIds(Long[] userIds) {
        return spdSysUserMapper.deleteSpdSysUserByIds(userIds);
    }

    /**
     * 删除用户信息信息
     * 
     * @param userId 用户信息ID
     * @return 结果
     */
    @Override
    public int deleteSpdSysUserById(Long userId) {
        return spdSysUserMapper.deleteSpdSysUserById(userId);
    }
    /**
     * 
     * @param str
     * @return
     */
    @Override
    public String querySystemValue(String str) {
        return spdSysUserMapper.querySystemValue(str);
    }

    @Override
    public int insertUser(SysUser sysUser) {
        int rows = userMapper.insertUser(sysUser);
        this.insertUserPost(sysUser);
        this.insertUserRole(sysUser);
        return rows;
    }

    @Override
    public int updateUser(SysUser user) {
        Long userId = user.getUserId();
        this.userRoleMapper.deleteUserRoleByUserId(userId);
        this.insertUserRole(user);
        this.userPostMapper.deleteUserPostByUserId(userId);
        this.insertUserPost(user);
        return this.userMapper.updateUser(user);
    }

    public void insertUserRole(SysUser user) {
        Long[] roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles)) {
            List<UserRole> list = new ArrayList();
            Long[] var4 = roles;
            int var5 = roles.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Long roleId = var4[var6];
                UserRole ur = new UserRole();
                ur.setUserId(user.getUserId());
                if(var6 == 0){
                    ur.setUserFlag(Constants.CARD_STATE_ONE);
                }else{
                    ur.setUserFlag(Constants.IS_CHANGE_YES);
                }
                ur.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
                ur.setRoleId(roleId);
                list.add(ur);
            }

            if (list.size() > 0) {
                this.spdSysUserMapper.insertRoleList(list);
            }
        }

    }



    public void insertUserPost(SysUser user) {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotNull(posts)) {
            List<SysUserPost> list = new ArrayList();
            Long[] var4 = posts;
            int var5 = posts.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Long postId = var4[var6];
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }

            if (list.size() > 0) {
                this.userPostMapper.batchUserPost(list);
            }
        }

    }



}
