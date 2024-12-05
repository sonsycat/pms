package com.zjry.pms.basic.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.basic.domain.AssetsBasicAccount;
import org.apache.ibatis.annotations.Param;

/**
 * 账套管理Mapper接口
 * 
 * @author maozt
 * @date 2021-08-05
 */
public interface AssetsBasicAccountMapper extends BaseMapper<AssetsBasicAccount> {
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
     * 删除账套管理
     * 
     * @param id 账套管理ID
     * @return 结果
     */
    public int deleteAssetsBasicAccountById(Long id);

    /**
     * 批量删除账套管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAssetsBasicAccountByIds(Long[] ids);

    /**
     * 根据账套编码查存在的关系数量
     */
    public int selectUserAccountRelationByAccountCode(String accountCode);
    
    List<AssetsBasicAccount> selectAccountSetListByCompCode(Long compCode);

    String queryTypeByDept(@Param("comeCode") Long comeCode,@Param("dept") Long dept);
}
