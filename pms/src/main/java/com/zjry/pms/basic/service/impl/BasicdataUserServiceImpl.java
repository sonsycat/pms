package com.zjry.pms.basic.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.common.utils.ServletUtils;
import com.zjry.common.utils.ip.IpUtils;

import com.zjry.pms.basic.constant.Constants;
import com.zjry.pms.basic.domain.basicData.*;
import com.zjry.pms.basic.mapper.basicData.*;
import com.zjry.pms.basic.service.IBasicdataConfigService;
import com.zjry.pms.basic.service.IBasicdataUserService;
import com.zjry.pms.basic.utils.ValidUtil;
import com.zjry.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 用户信息Service业务层处理
 *
 * @author cyq
 * @date 2021-07-19
 */
@Service
public class BasicdataUserServiceImpl extends ServiceImpl<BasicdataUserMapper, BasicdataUser> implements IBasicdataUserService {
    @Autowired
    private BasicdataUserMapper basicdataUserMapper;
    @Autowired
    private IBasicdataConfigService basicdataConfigService;
    @Autowired
    private BasicdataDeptRoomMapper basicdataDeptRoomMapper;
    @Autowired
    private AccountSetMapper accountSetMapper;
    @Autowired
    private UserAccountRelationMapper userAccountRelationMapper;
    @Autowired
    private UserDeptroomRelationMapper userDeptroomRelationMapper;
    @Autowired
    private BasicdataUserRoleMapper basicdataUserRoleMapper;

    @Autowired
    private BasicdataRoleMapper basicdataRoleMapper;

    @Autowired
    private ISysUserService userService;


    /**
     * 通过用户id获取登录用户的详细信息
     * @param accountId
     * @return
     */
    @Override
    public BasicdataUser initLoginMsgById(Long accountId, Long roleId) {
        //1、通过userId获取用户信息，对比黑白名单（白名单：对比绑定IP，黑名单：禁止登陆）
        //2、该用户是管理员时，返回用户用户信息中的科室信息（BasicdataDeptRoom）
        // 该用户是业务员时：
        //3、通过userId查询用户账套关系表：（1、通过用户信息中的账套id获取账套信息；2、在用户账套关系表彰获取默认账套；3、前两种情况都没有，就获取用户账套关系List中第一个）
        //4、通过userId查询用户科室关系表：获取用户当前机构及下级机构的科室信息List；
        //5、账套中单位和科室List对比，获取最后科室信息List。
        LoginUser user = SecurityUtils.getLoginUser();
        BasicdataUser basicdataUser = basicdataUserMapper.selectBasicdataUserByIdForOne(user.getUser().getUserId());

        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        if(!ip.equals(basicdataUser.getLoginIp())){
            basicdataUser.setLoginIp(ip);
        }

        //一：判断accountId是否有值（-1代表无值，无值时查询账套列表；有值时查询科室信息）
        if(accountId == null || accountId.toString().isEmpty()){
            //2、该用户是管理员时，返回用户用户信息中的科室信息（BasicdataDeptRoom）
            if(Constants.ADMINISTRATORS.equals(basicdataUser.getUserType())){
                basicdataUser.setDeptRoom(basicdataDeptRoomMapper.selectBasicdataDeptRoomById(basicdataUser.getDeptRoomId()));
            }else{
                //3、通过userId查询用户账套关系表：（1、获取用户账套关系表中正在使用的账套）
                UserAccountRelation userAccountRelation = new UserAccountRelation();
                userAccountRelation.setUserId(basicdataUser.getUserId());
                userAccountRelation.setUseFlag(Constants.IN_USE);
                List<UserAccountRelation> userAccountRelationList = userAccountRelationMapper.selectUserAccountRelationList(userAccountRelation);

                //获取正在使用或选择的角色信息
                basicdataUser.setRole(getBasicdataRoleByRoleId(basicdataUser.getUserId(), roleId));

                if(ValidUtil.isNotEmpty(userAccountRelationList)){
                    AccountSet accountSet = accountSetMapper.selectAccountSetById(userAccountRelationList.get(0).getAccountId());
                    basicdataUser.setAccountSet(accountSet);

                    basicdataUser.setDeptRoomList(getDeptRoomListByAccountId(accountSet.getDeptId(),basicdataUser.getUserId(), basicdataUser.getRole().getRoleId()));
                }
            }
        }else {
            //二：accountId有值

            //更新用户账套关系表中的使用账套
            UserAccountRelation relation = new UserAccountRelation();
            relation.setUserId(basicdataUser.getUserId());
            relation.setUseFlag("0");
            userAccountRelationMapper.updateUserAccountRelationUseFlag(relation);
            relation.setUseFlag("1");
            relation.setAccountId(accountId);
            userAccountRelationMapper.updateUserAccountRelationUseFlag(relation);

            AccountSet accountSet = accountSetMapper.selectAccountSetById(accountId);
            basicdataUser.setAccountSet(accountSet);

            basicdataUser.setRole(getBasicdataRoleByRoleId(basicdataUser.getUserId(), roleId));

            basicdataUser.setDeptRoomList(getDeptRoomListByAccountId(accountSet.getDeptId(),basicdataUser.getUserId(), basicdataUser.getRole().getRoleId()));
        }

        //业务员用户从当前账套获取单位id nema,  //切换账套和角色之后，刷新页面
        if(Constants.SALESMAN.equals(basicdataUser.getUserType())) {
            if(ValidUtil.isNotEmpty(basicdataUser.getAccountSet())){
                basicdataUser.setDeptId(basicdataUser.getAccountSet().getDeptId());
                basicdataUser.setDeptName(basicdataUser.getAccountSet().getDeptName());
                basicdataUser.getAccountSet().setUseFlag("1");
                basicdataUser.getAccountSetList().forEach(item -> {
                    if (basicdataUser.getAccountSet().getId().equals(item.getId())) {
                        item.setUseFlag("1");
                    } else {
                        item.setUseFlag("0");
                    }
                });
            }
            //切换账套和角色之后，刷新页面
            // 切换角色
            userService.changeRole(basicdataUser.getRole().getRoleId());
            // 切换机构
            userService.changeDept(basicdataUser.getDeptId());
        }

        return basicdataUser;
    }
    public BasicdataRole getBasicdataRoleByRoleId(Long userId, Long roleId){

        BasicdataRole basicdataRole = new BasicdataRole();
        //判断角色是不是单个使用
        String configValue = basicdataConfigService.selectBasicdataConfigByKey(Constants.CONFIG_KEY_ROLE);
        if(configValue.equals(Constants.SINGLE_USE)){
            if(roleId == null || roleId.toString().isEmpty()){
                BasicdataUserRole userRole = new BasicdataUserRole();
                userRole.setUserId(userId);
                userRole.setUseFlag(Constants.IN_USE);
                List<BasicdataUserRole> userRoleList = basicdataUserRoleMapper.selectBasicdataUserRoleList(userRole);
                if(ValidUtil.isNotEmpty(userRoleList)){
                    basicdataRole = basicdataRoleMapper.selectBasicdataRoleById(userRoleList.get(0).getRoleId());
                }
            }else {
                //更新用户角色关系表中的使用角色
                BasicdataUserRole userRole = new BasicdataUserRole();
                userRole.setUserId(userId);
                userRole.setUseFlag("0");
                basicdataUserRoleMapper.updateBasicdataUserRoleUseFlag(userRole);
                userRole.setUseFlag("1");
                userRole.setRoleId(roleId);
                basicdataUserRoleMapper.updateBasicdataUserRoleUseFlag(userRole);

                basicdataRole = basicdataRoleMapper.selectBasicdataRoleById(roleId);
            }
        }
        return basicdataRole;
    }
    /**
     * 获取最终科室科室列表
     * @param deptId
     * @param userId
     * @return
     */
    public List<BasicdataDeptRoom> getDeptRoomListByAccountId(Long deptId, Long userId, Long roleId){

        UserDeptroomRelation relation = new UserDeptroomRelation();
        relation.setUserId(userId);
        relation.setRoleId(roleId);

        List<UserDeptroomRelation> list = userDeptroomRelationMapper.selectUserDeptroomRelationByUserIdAndRoleId(relation);

        //获取用户科室关系表中对应用户的科室id
        List<Long> deptRoomIds = list.stream().map(UserDeptroomRelation::getDeptroomId).collect(Collectors.toList());
        //返回科室列表
        return basicdataDeptRoomMapper.selectDeptRoomListByIds(deptId, deptRoomIds);
    }
    /**
     * 初始化账套信息List
     * @return
     */
    @Override
    public BasicdataUser initAccountMsg() {

        LoginUser user = SecurityUtils.getLoginUser();
        BasicdataUser basicdataUser = basicdataUserMapper.selectBasicdataUserById(user.getUser().getUserId());
        //2、该用户是管理员时，返回用户用户信息中的科室信息（BasicdataDeptRoom）
        if(Constants.ADMINISTRATORS.equals(basicdataUser.getUserType())){
            basicdataUser.setDeptRoom(basicdataDeptRoomMapper.selectBasicdataDeptRoomById(basicdataUser.getDeptRoomId()));
        }else{
            //3、通过userId查询用户账套关系表：（1、获取用户账套关系表中正在使用的账套）
            UserAccountRelation userAccountRelation = new UserAccountRelation();
            userAccountRelation.setUserId(basicdataUser.getUserId());
            List<UserAccountRelation> userAccountRelationList = userAccountRelationMapper.selectUserAccountRelationList(userAccountRelation);
            if (!userAccountRelationList.isEmpty()) {
                Long[] ids = new Long[userAccountRelationList.size()];
                Long accountId = null;
                for(int i = 0; i < ids.length; i++){
                    ids[i] = userAccountRelationList.get(i).getAccountId();
                    if(Constants.IN_USE.equals(userAccountRelationList.get(i).getUseFlag())){
                        accountId = userAccountRelationList.get(i).getAccountId();
                    }
                }
                List<AccountSet> accountSetList = accountSetMapper.selectAccountSetByIds(ids);
                for(AccountSet set : accountSetList){
                    if(set.getId().equals(accountId)){
                        set.setUseFlag(Constants.IN_USE);
                    }else{
                        set.setUseFlag(Constants.NOT_USE);
                    }
                }
                basicdataUser.setAccountSetList(accountSetList);
            }
        }

        return basicdataUser;
    }
    /**
     * 初始化角色信息List
     * @return
     */
    @Override
    public BasicdataUser initRoleMsg() {

        LoginUser user = SecurityUtils.getLoginUser();
        BasicdataUser basicdataUser = basicdataUserMapper.selectBasicdataUserById(user.getUser().getUserId());

        String configValue = basicdataConfigService.selectBasicdataConfigByKey(Constants.CONFIG_KEY_ROLE);
        //判断角色是不是单个使用
        if(configValue.equals(Constants.SINGLE_USE)){
            //通过userID查询用户角色表：
            BasicdataUserRole userRole = new BasicdataUserRole();
            userRole.setUserId(basicdataUser.getUserId());
            List<BasicdataUserRole> userRoleList = basicdataUserRoleMapper.selectBasicdataUserRoleList(userRole);
            if (!userRoleList.isEmpty()) {
                Long[] ids = new Long[userRoleList.size()];
                Long roleId = null;
                for (int i = 0; i < ids.length; i++) {
                    ids[i] = userRoleList.get(i).getRoleId();
                    if (Constants.IN_USE.equals(userRoleList.get(i).getUseFlag())) {
                        roleId = userRoleList.get(i).getRoleId();
                    }
                }
                List<BasicdataRole> roleList = basicdataRoleMapper.selectBasicdataRoleByIds(ids);
                for (BasicdataRole role : roleList) {
                    if (role.getRoleId().equals(roleId)) {
                        role.setUseFlag(Constants.IN_USE);
                    } else {
                        role.setUseFlag(Constants.NOT_USE);
                    }
                }
                basicdataUser.setRoles(roleList);
            }
        }

        return basicdataUser;
    }
}
