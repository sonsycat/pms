package com.zjry.framework.web.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.zjry.common.utils.ServletUtils;
import com.zjry.common.utils.ip.AddressUtils;
import com.zjry.common.utils.ip.IpUtils;
import com.zjry.system.domain.SysPersistentToken;
import com.zjry.system.service.ISysPersistentTokenService;
import com.zjry.system.service.ISysUserService;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author sunzw
 *目前只在登录的时候进行了保存操作，后期可以定义过期时间。同时登录状态能够永久保留
 */
@Component
@Slf4j
public class PersistentTokenRememberMeService{

	@Autowired
	private ISysUserService userService;

	@Autowired
	private ISysPersistentTokenService sysPersistentTokenService;

	

	public void onLoginSuccess(HttpServletRequest request, HttpServletResponse response,String username,String token) {

		log.debug("Creating new persistent login for user {}", username);
		SysPersistentToken persistentToken = Optional.of(userService.selectUserByUserName(username)).map(u -> {
			String ip = IpUtils.getIpAddr(ServletUtils.getRequest());

			SysPersistentToken t = new SysPersistentToken();
			t.setSeries(RandomUtil.randomString(32));
			t.setUserAgent(u.getUserId().toString());
			t.setUserId(u.getUserId().toString());
			t.setUsername(u.getUserName());
			t.setTokenValue(token);
			t.setTokenDate(LocalDateTime.now());
			t.setIpAddress(ip);
			t.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
			t.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
			UserAgent userAgent = UserAgentUtil.parse(t.getUserAgent());
			t.setBrowser(userAgent.getBrowser().getName());
			t.setOs(userAgent.getOs().getName());
			return t;
		}).orElseThrow(() -> new UsernameNotFoundException("User " + username + " was not found in the database"));
		try {
			sysPersistentTokenService.save(persistentToken);
		} catch (DataAccessException e) {
			log.error("Failed to save persistent token ", e);
		}
	}
}
