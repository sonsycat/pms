package com.zjry.pms.basic.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.basic.domain.AssetsBasicUserAccount;

/**
 * 人员账套对应关系Mapper接口
 * 
 * @author maozt
 * @date 2022-03-18
 */
public interface AssetsBasicUserAccountMapper extends BaseMapper<AssetsBasicUserAccount> {
    /**
     * 查询人员账套对应关系
     * 
     * @param accountId 人员账套对应关系ID
     * @return 人员账套对应关系
     */
    public AssetsBasicUserAccount selectAssetsBasicUserAccountById(Long accountId);

    /**
     * 查询人员账套对应关系列表
     * 
     * @param assetsBasicUserAccount 人员账套对应关系
     * @return 人员账套对应关系集合
     */
    public List<AssetsBasicUserAccount> selectAssetsBasicUserAccountList(AssetsBasicUserAccount assetsBasicUserAccount);

    /**
     * 新增人员账套对应关系
     * 
     * @param assetsBasicUserAccount 人员账套对应关系
     * @return 结果
     */
    public int insertAssetsBasicUserAccount(AssetsBasicUserAccount assetsBasicUserAccount);

    /**
     * 修改人员账套对应关系
     * 
     * @param assetsBasicUserAccount 人员账套对应关系
     * @return 结果
     */
    public int updateAssetsBasicUserAccount(AssetsBasicUserAccount assetsBasicUserAccount);

    /**
     * 删除人员账套对应关系
     * 
     * @param accountId 人员账套对应关系ID
     * @return 结果
     */
    public int deleteAssetsBasicUserAccountById(Long accountId);

    /**
     * 批量删除人员账套对应关系
     * 
     * @param accountIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAssetsBasicUserAccountByIds(Long[] accountIds);

    /**
     * 删除人员账套对应关系信息
     *
     * @param userId 人员ID
     * @return 结果
     */
    public int deleteAssetsBasicUserAccountByUserId(Long userId);
}
