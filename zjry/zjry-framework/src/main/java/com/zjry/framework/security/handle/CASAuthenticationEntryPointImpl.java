package com.zjry.framework.security.handle;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zjry.common.constant.HttpStatus;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.utils.ServletUtils;
import com.zjry.common.utils.StringUtils;
import com.zjry.framework.config.CasConfig;

 
@Service
public class CASAuthenticationEntryPointImpl  implements AuthenticationEntryPoint, Serializable {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = -7106462533803060645L;

	protected final Log logger = LogFactory.getLog(this.getClass());
 
    @Autowired
    private CasConfig casConfig;
 
    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private int expireTime;
 
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException
    {
        StringBuffer requestURL = request.getRequestURL();
        System.out.println("requestURL=>"+requestURL);
        int code = HttpStatus.UNAUTHORIZED;
        String msg = StringUtils.format("请求访问：{}，认证失败，无法访问系统资源", request.getRequestURI());
        AjaxResult error = AjaxResult.error(code, msg);
        error.put("loginUrl",casConfig.getCasServerLoginUrl()+"?service="+casConfig.getAppServerUrl() + casConfig.getAppLoginUrl());
        ServletUtils.renderString(response, JSON.toJSONString(error));
    }
}