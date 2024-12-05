package com.zjry.pms.common.utils;
import org.springframework.beans.factory.annotation.Autowired;

import com.zjry.common.core.domain.entity.SysUser;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.utils.ServletUtils;
import com.zjry.framework.web.service.TokenService;



/**
 * @author mk
 * @title: UserUtils
 * @projectName zjrj-hrm
 * @date 2021/7/2209:52
 */
public class UserUtils {
    @Autowired
    private static TokenService tokenService = SpringContextHolder.getBean(TokenService.class);;

    /**
     * 获取当前
     */
    public static SysUser getSysUser() {
    	LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        return loginUser.getUser();
    }
}
