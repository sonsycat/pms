package com.zjry.framework.config;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.filter.CorsFilter;

import com.zjry.framework.security.filter.JwtAuthenticationTokenFilter;
import com.zjry.framework.security.handle.AuthenticationEntryPointImpl;
import com.zjry.framework.security.handle.CasAuthenticationSuccessHandler;
import com.zjry.framework.security.handle.LogoutSuccessHandlerImpl;
import com.zjry.framework.web.service.CasUserDetailsService;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MultiHttpSecurityConfig {
	
	/**
	 * 单点认证
	 * @author sunzw
	 *
	 */
	@Configuration
	@Order(2)
	public static class CasSecurityConfig extends WebSecurityConfigurerAdapter{
	    @Autowired
	    private CasConfig casConfig;
	    @Autowired
	    private CasUserDetailsService casUserDetailsService;
	    @Autowired
	    private CasAuthenticationSuccessHandler casAuthenticationSuccessHandler;
	    /**
	     * 退出处理类
	     */
	    @Autowired
	    private LogoutSuccessHandlerImpl logoutSuccessHandler;
	 
	    /**
	     * 跨域过滤器
	     */
	    @Autowired
	    private CorsFilter corsFilter;
	    @Override
	    	protected void configure(HttpSecurity httpSecurity) throws Exception {
	    	httpSecurity.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 过滤请求
                .authorizeRequests()
                // 对于登录login 验证码captchaImage 允许匿名访问
                //.antMatchers("/login", "/captchaImage").anonymous()
                .antMatchers("/ssologin","/ssologout").authenticated()
                .and()
                .headers().frameOptions().disable();
	    		 //单点登录登出
	            httpSecurity.logout().permitAll().logoutSuccessHandler(logoutSuccessHandler);
	            httpSecurity.addFilter(casAuthenticationFilter())
                //.addFilterBefore(casLogoutFilter(), LogoutFilter.class)
                .addFilterBefore(singleSignOutFilter(), CasAuthenticationFilter.class).exceptionHandling()
                .authenticationEntryPoint(casAuthenticationEntryPoint());
	            httpSecurity.addFilterBefore(corsFilter, LogoutFilter.class);
	            // disable page caching
	            httpSecurity.headers().cacheControl();
	    	}
	 
	    /**
	     * 定义认证用户信息获取来源，密码校验规则等
	     */
	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.authenticationProvider(casAuthenticationProvider());
	    }
	 
	    /**
	     * 认证的入口
	     */
	    @Bean
	    public CasAuthenticationEntryPoint casAuthenticationEntryPoint() {
	        CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();
	        casAuthenticationEntryPoint.setLoginUrl(casConfig.getCasServerLoginUrl());
	        casAuthenticationEntryPoint.setServiceProperties(serviceProperties());
	        return casAuthenticationEntryPoint;
	    }
	 
	    /**
	     * 指定service相关信息
	     */
	    @Bean
	    public ServiceProperties serviceProperties() {
	        ServiceProperties serviceProperties = new ServiceProperties();
	        serviceProperties.setService(casConfig.getAppServerUrl() + casConfig.getAppLoginUrl());
	        serviceProperties.setAuthenticateAllArtifacts(true);
	        return serviceProperties;
	    }
	 
	    /**
	     * CAS认证过滤器
	     */
	    @Bean
	    public CasAuthenticationFilter casAuthenticationFilter() throws Exception {
	        CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
	        casAuthenticationFilter.setAuthenticationManager(authenticationManager());
	        casAuthenticationFilter.setFilterProcessesUrl(casConfig.getAppLoginUrl());
	        casAuthenticationFilter.setAuthenticationSuccessHandler(casAuthenticationSuccessHandler);
	        return casAuthenticationFilter;
	    }
	 
	    /**
	     * cas 认证 Provider
	     */
	    @Bean
	    public CasAuthenticationProvider casAuthenticationProvider() {
	        CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
	        casAuthenticationProvider.setAuthenticationUserDetailsService(casUserDetailsService);
	        casAuthenticationProvider.setServiceProperties(serviceProperties());
	        casAuthenticationProvider.setTicketValidator(cas20ServiceTicketValidator());
	        casAuthenticationProvider.setKey("casAuthenticationProviderKey");
	        return casAuthenticationProvider;
	    }
	 
	    @Bean
	    public Cas20ServiceTicketValidator cas20ServiceTicketValidator() {
	        return new Cas20ServiceTicketValidator(casConfig.getCasServerUrl());
	    }
	 
	    /**
	     * 单点登出过滤器
	     */
	    @Bean
	    public SingleSignOutFilter singleSignOutFilter() {
	        SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
	        singleSignOutFilter.setCasServerUrlPrefix(casConfig.getCasServerUrl());
	        singleSignOutFilter.setIgnoreInitConfiguration(true);
	        return singleSignOutFilter;
	    }
	 
	    /**
	     * 请求单点退出过滤器
	     */
	    @Bean
	    public LogoutFilter casLogoutFilter() {
	        LogoutFilter logoutFilter = new LogoutFilter(casConfig.getCasServerLogoutUrl(),
	                new SecurityContextLogoutHandler());
	        logoutFilter.setFilterProcessesUrl(casConfig.getAppLogoutUrl());
	        return logoutFilter;
	    }
	    
	}
	/**
	 * 用户名，密码验证
	 * @author sunzw
	 *
	 */
	@Configuration
	@Order(1)
	public static class LoginSecurityConfig extends WebSecurityConfigurerAdapter{
		/**
	     * 自定义用户认证逻辑
	     */
	    @Autowired
	    private UserDetailsService userDetailsService;
	    
	    /**
	     * 认证失败处理类
	     */
	    @Autowired
	    private AuthenticationEntryPointImpl unauthorizedHandler;

	    /**
	     * 退出处理类
	     */
	    @Autowired
	    private LogoutSuccessHandlerImpl logoutSuccessHandler;

	    /**
	     * token认证过滤器
	     */
	    @Autowired
	    private JwtAuthenticationTokenFilter authenticationTokenFilter;
	    /**
	     * 跨域过滤器
	     */
	    @Autowired
	    private CorsFilter corsFilter;
	    /**
	     * 只能单个一过滤器
	     */
//	    @Autowired
//	    private SystemCustomizeFilter systemCustomizeFilter;
	    
	    /**
	     * 解决 无法直接注入 AuthenticationManager
	     *
	     * @return
	     * @throws Exception
	     */
	    @Bean
	    @Override
	    public AuthenticationManager authenticationManagerBean() throws Exception
	    {
	        return super.authenticationManagerBean();
	    }

	    /**
	     * anyRequest          |   匹配所有请求路径
	     * access              |   SpringEl表达式结果为true时可以访问
	     * anonymous           |   匿名可以访问
	     * denyAll             |   用户不能访问
	     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
	     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
	     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
	     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
	     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
	     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
	     * permitAll           |   用户可以任意访问
	     * rememberMe          |   允许通过remember-me登录的用户访问
	     * authenticated       |   用户登录后可访问
	     */
	    @Override
	    protected void configure(HttpSecurity httpSecurity) throws Exception
	    {
	        httpSecurity
	                // CSRF禁用，因为不使用session
	                .csrf().disable()
	                // 认证失败处理类
	                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
	                // 基于token，所以不需要session
	                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	                // 过滤请求
	                .authorizeRequests()
	                // 对于登录login 验证码captchaImage 允许匿名访问
	                .antMatchers("/login", "/captchaImage","/ssologin","/ssologout").anonymous()
	                .antMatchers(
	                        HttpMethod.GET,
	                        "/*.html",
	                        "/**/*.html",
	                        "/**/*.css",
	                        "/**/*.js"
	                ).permitAll()
	                .antMatchers("/profile/**").anonymous()
	                .antMatchers("/common/download**").anonymous()
	                .antMatchers("/common/download/resource**").anonymous()
	                .antMatchers("/swagger-ui.html").anonymous()
	                .antMatchers("/swagger-resources/**").anonymous()
	                .antMatchers("/webjars/**").anonymous()
	                .antMatchers("/*/api-docs").anonymous()
	                .antMatchers("/druid/**").anonymous()
	                .antMatchers("/api/**").anonymous()
	                .antMatchers("/ureport/**").anonymous()
	                .antMatchers(HttpMethod.GET, "/system/config/configKey/login.captchaFlag").anonymous()
	                .antMatchers("/basic/config/api/**").anonymous()
					// 除上面外的所有请求全部需要鉴权认证
					.antMatchers("/actuator/prometheus").anonymous()
					.antMatchers("/auth/license/**").anonymous()
					.antMatchers("/websocket/**").anonymous()
					.antMatchers("/monitor/operlog/queryOperStatistics").anonymous()
					.antMatchers("/nopwdLogin", "/nopwdLoginByCode", "/getLoginCode").anonymous()
					// 除上面外的所有请求全部需要鉴权认证
	                .anyRequest().authenticated()
	                .and()
	                .headers().frameOptions().disable();
	        httpSecurity.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
	        // 添加JWT filter
	        httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
	        // 添加CORS filter
	        httpSecurity.addFilterBefore(corsFilter, JwtAuthenticationTokenFilter.class);
	        httpSecurity.addFilterBefore(corsFilter, LogoutFilter.class);
	    }

	    /**
	     * 强散列哈希加密实现
	     */
	    @Bean
	    public BCryptPasswordEncoder bCryptPasswordEncoder()
	    {
	        return new BCryptPasswordEncoder();
	    }

	    /**
	     * 身份认证接口
	     */
	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception
	    {
	        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	    }
	}
	
}
