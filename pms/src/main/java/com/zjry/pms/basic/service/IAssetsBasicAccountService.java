package com.zjry.pms.basic.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.basic.domain.AssetsBasicAccount;
import com.zjry.pms.basic.domain.BasicPublicVo;
import com.zjry.pms.basic.domain.SysUserInfo;

/**
 * 账套管理Service接口
 * 
 * @author maozt
 * @date 2021-08-05
 */
public interface IAssetsBasicAccountService extends IService<AssetsBasicAccount> {
    /**
     * 查询账套管理
     * 
     * @param id 账套管理ID
     * @return 账套管理
     */
    public AssetsBasicAccount selectAssetsBasicAccountById(Long id);

    /**
     * 查询账套管理列表
     * 
     * @param assetsBasicAccount 账套管理
     * @return 账套管理集合
     */
    public List<AssetsBasicAccount> selectAssetsBasicAccountList(AssetsBasicAccount assetsBasicAccount);

    /**
     * 查询账套管理列表
     *
     * @param assetsBasicAccount 账套管理
     * @return 账套管理集合
     */
    public List<AssetsBasicAccount> selectAssetsBasicAccountListByUserCode(AssetsBasicAccount assetsBasicAccount);

    /**
     * 新增账套管理
     * 
     * @param assetsBasicAccount 账套管理
     * @return 结果
     */
    public int insertAssetsBasicAccount(AssetsBasicAccount assetsBasicAccount);

    /**
     * 修改账套管理
     * 
     * @param assetsBasicAccount 账套管理
     * @return 结果
     */
    public int updateAssetsBasicAccount(AssetsBasicAccount assetsBasicAccount);

    /**
     * 批量删除账套管理
     * 
     * @param ids 需要删除的账套管理ID
     * @return 结果
     */
    public int deleteAssetsBasicAccountByIds(Long[] ids);

    /**
     * 删除账套管理信息
     * 
     * @param id 账套管理ID
     * @return 结果
     */
    public int deleteAssetsBasicAccountById(Long id);
    /**
     * 根据登录用户获取账套信息
     * @return 结果
     */
    SysUserInfo getAccountInfo();

    /**
     * 根据账套编码查存在的关系数量
     */
    public int selectUserAccountRelationByAccountCode(String accountCode);
    
    List<BasicPublicVo> accountInfoByCompCode(Long compCode);

    Date returnAccountDate ();

    String queryTypeByDept(Long comeCode, Long dept);
}
