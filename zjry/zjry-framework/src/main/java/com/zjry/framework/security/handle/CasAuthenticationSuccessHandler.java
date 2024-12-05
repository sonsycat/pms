package com.zjry.framework.security.handle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zjry.common.constant.Constants;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.framework.config.CasConfig;
import com.zjry.framework.web.service.SysLoginService;
import com.zjry.framework.web.service.TokenService;
 
@Service
public class CasAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
 
    protected final Log logger = LogFactory.getLog(this.getClass());
 
    private RequestCache requestCache = new HttpSessionRequestCache();
 
    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private SysLoginService loginService;
 
    @Autowired
    private CasConfig casConfig;
 
    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private int expireTime;
    
    // 令牌cookie存储key
    @Value("${token.tokenKey}")
    private String tokenKey;
 
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        String targetUrlParameter = getTargetUrlParameter();
        if (isAlwaysUseDefaultTargetUrl()
                || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
            requestCache.removeRequest(request, response);
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }
        clearAuthenticationAttributes(request);
        LoginUser userDetails = (LoginUser) authentication.getPrincipal();
        String token = tokenService.createToken(userDetails);
        //往Cookie中设置token
        Cookie casCookie = new Cookie(StringUtils.isEmpty(tokenKey) ? Constants.WEB_TOKEN_KEY : tokenKey, token);
        casCookie.setMaxAge(expireTime * 60);
        response.addCookie(casCookie);
        //设置后端认证成功标识
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(Constants.CAS_TOKEN, token);
        // 更新登录信息
        loginService.recordLoginInfo(userDetails.getUser());
        //登录成功后跳转到前端登录页面
        getRedirectStrategy().sendRedirect(request, response, casConfig.getWebUrl());
    }
}
