package com.zjry.pms.basic.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.pms.basic.domain.AssetsBasicAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.basic.mapper.AssetsBasicUserAccountMapper;
import com.zjry.pms.basic.domain.AssetsBasicUserAccount;
import com.zjry.pms.basic.service.IAssetsBasicUserAccountService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 人员账套对应关系Service业务层处理
 * 
 * @author maozt
 * @date 2022-03-18
 */
@Service
public class AssetsBasicUserAccountServiceImpl extends ServiceImpl<AssetsBasicUserAccountMapper, AssetsBasicUserAccount> implements IAssetsBasicUserAccountService {
    @Autowired
    private AssetsBasicUserAccountMapper assetsBasicUserAccountMapper;

    /**
     * 查询人员账套对应关系
     * 
     * @param accountId 人员账套对应关系ID
     * @return 人员账套对应关系
     */
    @Override
    public AssetsBasicUserAccount selectAssetsBasicUserAccountById(Long accountId) {
        return assetsBasicUserAccountMapper.selectAssetsBasicUserAccountById(accountId);
    }

    /**
     * 查询人员账套对应关系列表
     * 
     * @param assetsBasicUserAccount 人员账套对应关系
     * @return 人员账套对应关系
     */
    @Override
    public List<AssetsBasicUserAccount> selectAssetsBasicUserAccountList(AssetsBasicUserAccount assetsBasicUserAccount) {
        return assetsBasicUserAccountMapper.selectAssetsBasicUserAccountList(assetsBasicUserAccount);
    }

    /**
     * 新增人员账套对应关系
     * 
     * @param assetsBasicUserAccount 人员账套对应关系
     * @return 结果
     */
    @Override
    public int insertAssetsBasicUserAccount(AssetsBasicUserAccount assetsBasicUserAccount) {
    	assetsBasicUserAccount.preInsert();
        return assetsBasicUserAccountMapper.insertAssetsBasicUserAccount(assetsBasicUserAccount);
    }

    /**
     * 新增人员账套对应关系
     *
     * @param assetsBasicUserAccount 人员账套对应关系
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertAssetsBasicUserAccountPl(AssetsBasicUserAccount assetsBasicUserAccount) throws Exception {
        int row = 0;
        //只能有一个使用的标识
        Boolean flag = true;
        for (AssetsBasicAccount assetsBasicAccount : assetsBasicUserAccount.getAssetsBasicAccounts()) {
            AssetsBasicUserAccount basicUserAccount = new AssetsBasicUserAccount();
            basicUserAccount.setUserId(assetsBasicUserAccount.getUserId());
            basicUserAccount.setAccountId(assetsBasicAccount.getId());
            if (flag) {
                basicUserAccount.setUseFlag("1");
                flag = false;
            } else {
                basicUserAccount.setUseFlag("0");
            }
            basicUserAccount.preInsert();
            row += assetsBasicUserAccountMapper.insertAssetsBasicUserAccount(basicUserAccount);
        }
        return row;
    }

    /**
     * 修改人员账套对应关系
     * 
     * @param assetsBasicUserAccount 人员账套对应关系
     * @return 结果
     */
    @Override
    public int updateAssetsBasicUserAccount(AssetsBasicUserAccount assetsBasicUserAccount) {
    	assetsBasicUserAccount.preUpdate();
        return assetsBasicUserAccountMapper.updateAssetsBasicUserAccount(assetsBasicUserAccount);
    }

    /**
     * 批量删除人员账套对应关系
     * 
     * @param accountIds 需要删除的人员账套对应关系ID
     * @return 结果
     */
    @Override
    public int deleteAssetsBasicUserAccountByIds(Long[] accountIds) {
        return assetsBasicUserAccountMapper.deleteAssetsBasicUserAccountByIds(accountIds);
    }

    /**
     * 删除人员账套对应关系信息
     * 
     * @param accountId 人员账套对应关系ID
     * @return 结果
     */
    @Override
    public int deleteAssetsBasicUserAccountById(Long accountId) {
        return assetsBasicUserAccountMapper.deleteAssetsBasicUserAccountById(accountId);
    }

    /**
     * 删除人员账套对应关系信息
     *
     * @param userId 人员ID
     * @return 结果
     */
    @Override
    public int deleteAssetsBasicUserAccountByUserId(Long userId) {
        return assetsBasicUserAccountMapper.deleteAssetsBasicUserAccountByUserId(userId);
    }
}
