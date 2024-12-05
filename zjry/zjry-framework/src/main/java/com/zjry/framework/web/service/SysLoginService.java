package com.zjry.framework.web.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.zjry.common.config.ZjRyConfig;
import com.zjry.common.constant.Constants;
import com.zjry.common.core.domain.entity.SysUser;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.core.redis.RedisCache;
import com.zjry.common.exception.CustomException;
import com.zjry.common.exception.user.CaptchaException;
import com.zjry.common.exception.user.CaptchaExpireException;
import com.zjry.common.exception.user.UserPasswordNotMatchException;
import com.zjry.common.utils.DateUtils;
import com.zjry.common.utils.MessageUtils;
import com.zjry.common.utils.ServletUtils;
import com.zjry.common.utils.ip.IpUtils;
import com.zjry.common.utils.sign.RsaUtils;
import com.zjry.framework.manager.AsyncManager;
import com.zjry.framework.manager.factory.AsyncFactory;
import com.zjry.system.service.ISysConfigService;
import com.zjry.system.service.ISysUserService;

/**
 * 登录校验方法
 *
 * @author zjry
 */
@Component
public class SysLoginService {
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;
    
    @Autowired
    private ZjRyConfig zjRyConfig;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService sysConfigService;
    
    @Autowired
    private SysPermissionService permissionService;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid) {
        String verifyKey = zjRyConfig.getName() + ":" + Constants.CAPTCHA_CODE_KEY + uuid;
        // 获取系统参数，判断是否需要验证码进行登录
        String captchaFlag = sysConfigService.selectConfigByKey("login.captchaFlag");
        if ("1".equals(captchaFlag) || captchaFlag == null) {
            String captcha = redisCache.getCacheObject(verifyKey);
            redisCache.deleteObject(verifyKey);
            if (captcha == null) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
                throw new CaptchaExpireException();
            }
            if (code != null && !code.equalsIgnoreCase(captcha)) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
                throw new CaptchaException();
            }
        }
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            //authentication = authenticationManager
            //        .authenticate(new UsernamePasswordAuthenticationToken(username, password));
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, RsaUtils.decryptByPrivateKey(password)));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        recordLoginInfo(loginUser.getUser());
        // 生成token
        return tokenService.createToken(loginUser);
    }
    
    /**
     * 免密登录
     * @param noPwdloginBody
     * @return
     */
    public String nopwdLogin(String username) {
    	// 用户验证
        Authentication authentication = null;
        try {
        	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        	authorities.add(new GrantedAuthority() {
				@Override
				public String getAuthority() {
					return "";
				}
			});
        	
        	authentication = new UsernamePasswordAuthenticationToken(username, "", AuthorityUtils.createAuthorityList());
        	SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
        	e.printStackTrace();
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        SysUser user = userService.selectUserByUserName(username);
        LoginUser loginUser = new LoginUser(user, permissionService.getMenuPermission(user));
        recordLoginInfo(loginUser.getUser());
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 记录登录信息
     */
    public void recordLoginInfo(SysUser user) {
        user.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        user.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(user);
    }
}
