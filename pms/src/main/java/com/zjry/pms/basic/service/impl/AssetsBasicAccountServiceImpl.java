package com.zjry.pms.basic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zjry.pms.basic.domain.AssetsBasicAccount;
import com.zjry.pms.basic.domain.AssetsBasicAccountPeriod;
import com.zjry.pms.basic.domain.BasicPublicVo;
import com.zjry.pms.basic.domain.SysUserInfo;
import com.zjry.pms.basic.domain.basicData.BasicdataUser;
import com.zjry.pms.basic.mapper.AssetsBasicAccountMapper;
import com.zjry.pms.basic.service.IAssetsBasicAccountPeriodService;
import com.zjry.pms.basic.service.IAssetsBasicAccountService;
import com.zjry.pms.basic.utils.BasicDataUserUtils;
import com.zjry.pms.common.utils.DateUtils;
import com.zjry.pms.encode.service.IHrEncodeRuleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 账套管理Service业务层处理
 *
 * @author maozt
 * @date 2021-08-05
 */
@Service
public class AssetsBasicAccountServiceImpl extends ServiceImpl<AssetsBasicAccountMapper, AssetsBasicAccount> implements IAssetsBasicAccountService {
    @Autowired
    private AssetsBasicAccountMapper assetsBasicAccountMapper;
    @Autowired
    private IHrEncodeRuleService hrEncodeRuleService;
    @Autowired
    private IAssetsBasicAccountPeriodService assetsBasicAccountPeriodService;
    @Autowired
    private IAssetsBasicAccountService assetsBasicAccountService;
    /**
     * 查询账套管理
     *
     * @param id 账套管理ID
     * @return 账套管理
     */
    @Override
    public AssetsBasicAccount selectAssetsBasicAccountById(Long id) {
        return assetsBasicAccountMapper.selectAssetsBasicAccountById(id);
    }

    /**
     * 查询账套管理列表
     *
     * @param assetsBasicAccount 账套管理
     * @return 账套管理
     */
    @Override
    public List<AssetsBasicAccount> selectAssetsBasicAccountList(AssetsBasicAccount assetsBasicAccount) {
        return assetsBasicAccountMapper.selectAssetsBasicAccountList(assetsBasicAccount);
    }

    /**
     * 查询账套管理列表
     *
     * @param assetsBasicAccount 账套管理
     * @return 账套管理
     */
    @Override
    public List<AssetsBasicAccount> selectAssetsBasicAccountListByUserCode(AssetsBasicAccount assetsBasicAccount) {
        return assetsBasicAccountMapper.selectAssetsBasicAccountListByUserCode(assetsBasicAccount);
    }

    /**
     * 新增账套管理
     *
     * @param assetsBasicAccount 账套管理
     * @return 结果
     */
    @Override
    public int insertAssetsBasicAccount(AssetsBasicAccount assetsBasicAccount) {
        assetsBasicAccount.preInsert();
        String accountCode = hrEncodeRuleService.generateEncode("account_set", "account_code");
        if(StringUtils.isNotBlank(accountCode)){
            accountCode = accountCode.replace("-", "");
        }
        assetsBasicAccount.setAccountCode(accountCode);
        return assetsBasicAccountMapper.insertAssetsBasicAccount(assetsBasicAccount);
    }

    /**
     * 修改账套管理
     *
     * @param assetsBasicAccount 账套管理
     * @return 结果
     */
    @Override
    public int updateAssetsBasicAccount(AssetsBasicAccount assetsBasicAccount) {
        assetsBasicAccount.preUpdate();
        return assetsBasicAccountMapper.updateAssetsBasicAccount(assetsBasicAccount);
    }

    /**
     * 批量删除账套管理
     *
     * @param ids 需要删除的账套管理ID
     * @return 结果
     */
    @Override
    public int deleteAssetsBasicAccountByIds(Long[] ids) {
        return assetsBasicAccountMapper.deleteAssetsBasicAccountByIds(ids);
    }

    /**
     * 删除账套管理信息
     *
     * @param id 账套管理ID
     * @return 结果
     */
    @Override
    public int deleteAssetsBasicAccountById(Long id) {
        return assetsBasicAccountMapper.deleteAssetsBasicAccountById(id);
    }

    @Override
    public SysUserInfo getAccountInfo() {
        BasicdataUser user = BasicDataUserUtils.getUserWithRelations();
        SysUserInfo sysUserInfo = new SysUserInfo();
        sysUserInfo.setUserId(user.getUserId());
        sysUserInfo.setNickName(user.getNickName());
        sysUserInfo.setUserName(user.getUserName());
        if(user.getAccountSet()!=null){
            sysUserInfo.setAccountCode(user.getAccountSet().getCode());
            sysUserInfo.setAccountName(user.getAccountSet().getName());
            AssetsBasicAccountPeriod assetsBasicAccountPeriod = new AssetsBasicAccountPeriod();
            assetsBasicAccountPeriod.setAccountCode(user.getAccountSet().getCode());
            assetsBasicAccountPeriod.setIsPeriod("0");
            List<AssetsBasicAccountPeriod> list = assetsBasicAccountPeriodService.selectAssetsBasicAccountPeriodList(assetsBasicAccountPeriod);
            if (list.size() > 0) {
                assetsBasicAccountPeriod = list.get(0);
                sysUserInfo.setStartDate(assetsBasicAccountPeriod.getStartDate());
                sysUserInfo.setEndDate(assetsBasicAccountPeriod.getEndDate());
                sysUserInfo.setPeriodName(assetsBasicAccountPeriod.getPeriodName());
            }
        }

        sysUserInfo.setDeptId(user.getDeptId());
        sysUserInfo.setDeptName(user.getDeptName());
        return sysUserInfo;
    }

    /**
     * 根据账套编码查存在的关系数量
     */
    @Override
    public int selectUserAccountRelationByAccountCode(String accountCode) {
        return assetsBasicAccountMapper.selectUserAccountRelationByAccountCode(accountCode);
    }

    @Override
    public List<BasicPublicVo> accountInfoByCompCode(Long compCode) {
        List<AssetsBasicAccount> list = assetsBasicAccountMapper.selectAccountSetListByCompCode(compCode);
        List<BasicPublicVo>  data = new ArrayList<BasicPublicVo>();
        if(list.size()>0){
            list.stream().forEach(item->{
                BasicPublicVo bpv = new BasicPublicVo();
                bpv.setCode(item.getAccountCode());
                bpv.setName(item.getAccountName());
                data.add(bpv);
            });
        }
        return data;
    }

    @Override
    public Date returnAccountDate() {
        SysUserInfo sysUserInfo =  assetsBasicAccountService.getAccountInfo();
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        try {
            String s0 = sdf.format(new Date());
            String s1 = sdf.format(sysUserInfo.getStartDate()); //2015-02-09 format()才是格式化
            String s2 = sdf.format(sysUserInfo.getEndDate()); //2015-02-09 format()才是格式化
            Boolean flag =DateUtils.isEffectiveDate(sdf.parse(s0),sdf.parse(s1),sdf.parse(s2));
            if(flag){
                return new Date();
            }else{
                return sysUserInfo.getEndDate();
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String queryTypeByDept(Long comeCode, Long dept) {
        return assetsBasicAccountMapper.queryTypeByDept(comeCode,dept);
    }
}
