package com.zjry.pms.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.basic.domain.basicData.BasicdataUser;
import org.springframework.stereotype.Service;

/**
 * 用户信息Service接口
 * 
 * @author cyq
 * @date 2021-07-19
 */
@Service
public interface IBasicdataUserService extends IService<BasicdataUser> {
    /**
     * 通过用户id获取登录用户的详细信息
     * @param accountId
     *   @param roleId
     * @return
     */
    public BasicdataUser initLoginMsgById(Long accountId, Long roleId);
    /**
     * 初始化账套信息List
     * @return
     */
    public BasicdataUser initAccountMsg();
    /**
     * 初始化角色信息List
     * @return
     */
    public BasicdataUser initRoleMsg();


}
