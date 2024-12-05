package com.zjry.web.controller.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zjry.common.config.ZjRyConfig;
import com.zjry.common.constant.Constants;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.domain.entity.SysMenu;
import com.zjry.common.core.domain.entity.SysUser;
import com.zjry.common.core.domain.model.LoginBody;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.core.domain.model.NoPwdLoginBody;
import com.zjry.common.core.redis.RedisCache;
import com.zjry.common.utils.ServletUtils;
import com.zjry.common.utils.uuid.UUID;
import com.zjry.framework.web.service.PersistentTokenRememberMeService;
import com.zjry.framework.web.service.SysLoginService;
import com.zjry.framework.web.service.SysPermissionService;
import com.zjry.framework.web.service.TokenService;
import com.zjry.system.domain.SysClient;
import com.zjry.system.service.ISysClientService;
import com.zjry.system.service.ISysConfigService;
import com.zjry.system.service.ISysMenuService;
import com.zjry.system.service.ISysUserService;

import cn.hutool.core.util.BooleanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 登录验证
 *
 * @author zjry
 */
@RestController
@Api(tags = {"【登录验证】" })
public class SysLoginController {
	
	private static final String SYS_CODE = "BASE-FRAME";
    
	@Autowired
    private SysLoginService loginService;
    @Autowired
    private ISysMenuService menuService;
    @Autowired
    private SysPermissionService permissionService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private PersistentTokenRememberMeService persistentTokenRememberMeService;
    @Autowired
    private ISysConfigService sysConfigService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ISysClientService sysClientService;
    @Autowired
    private ZjRyConfig zjRyConfig;
    @Autowired
    private ISysUserService userService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login" )
    @ApiOperation("登录方法" )
    public AjaxResult login(@RequestBody LoginBody loginBody,HttpServletRequest request,HttpServletResponse response) {
    	AjaxResult ajax = AjaxResult.success();
    	// 验证系统是否激活
//        if(!LicenseUtils.authorizationVerification(SYS_CODE)) {
//        	ajax = AjaxResult.error("系统未授权激活，请激活后使用！");
//        	return ajax;
//        }
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        if(BooleanUtil.isTrue(loginBody.getRememberMe())) {
        	persistentTokenRememberMeService.onLoginSuccess(request, response,loginBody.getUsername(),token);
        }
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }
    
    @GetMapping("/nopwdLogin")
    @ApiOperation("免密登录（通过用户名）")
    public AjaxResult nopwdLogin(@RequestParam(value="userName") String userName) {
    	AjaxResult ajax = AjaxResult.success();
    	// 0-需要首先获取loginCode
    	String simpleMode = sysConfigService.selectConfigByKey("nopwdLogin.simpleMode");
    	if(!"1".equals(simpleMode)) {
    		return ajax.error("禁止访问！");
    	}
    	if(StringUtils.isEmpty(userName)) {
    		return ajax.error("参数userName不可为空！");
    	}
    	SysUser user = userService.selectUserByUserName(userName);
    	if(user == null) {
    		return ajax.error("用户不存在！");
    	}
    	// 生成令牌
        String token = loginService.nopwdLogin(userName);
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }
    
    @GetMapping("/nopwdLoginByCode")
    @ApiOperation("免密登录（通过登录码）")
    public AjaxResult nopwdLoginByCode(@RequestParam(value="loginCode") String loginCode) {
    	AjaxResult ajax = AjaxResult.success();
    	String simpleMode = sysConfigService.selectConfigByKey("nopwdLogin.simpleMode");
    	// 0-需要首先获取loginCode
    	if(StringUtils.isNotEmpty(simpleMode) && !"0".equals(simpleMode)) {
    		return ajax.error("禁止访问！");
    	}
    	if(StringUtils.isEmpty(loginCode)) {
    		return ajax.error("参数loginCode不可为空！");
    	}
		NoPwdLoginBody noPwdLoginBody = redisCache.getCacheObject(zjRyConfig.getName()+":"+Constants.NOPWD_LOGIN_CODE_KEY+loginCode);
		if(noPwdLoginBody == null) {
			return ajax.error("登录码已过期！");
		}
		String userName = noPwdLoginBody.getUsername();
		SysUser user = userService.selectUserByUserName(userName);
    	if(user == null) {
    		return ajax.error("用户不存在！");
    	}
		// 生成令牌
		String token = loginService.nopwdLogin(userName);
		ajax.put(Constants.TOKEN, token);
        return ajax;
    }
    
    @PostMapping("/getLoginCode")
    @ApiOperation("免密登录-获取登录码")
    public AjaxResult getLoginCode(@RequestBody String paramBody) {
    	AjaxResult ajax = AjaxResult.success();
    	// 解密参数
    	NoPwdLoginBody noPwdLoginBody = new NoPwdLoginBody(paramBody);
    	if(noPwdLoginBody == null) {
    		return ajax.error("参数错误！");
    	}
    	// 客户端验证
    	SysClient sysClient = new SysClient();
    	sysClient.setClientId(noPwdLoginBody.getClientId());
    	sysClient.setClientSecret(noPwdLoginBody.getSecret());
    	List<SysClient> sysClientList = sysClientService.selectClientList(sysClient);
    	if(sysClientList == null || sysClientList.size() == 0) {
    		return ajax.error("非法接入！");
    	}
    	// 返回登录码
    	String loginCode = UUID.fastUUID().toString();
    	redisCache.setCacheObject(zjRyConfig.getName()+":"+Constants.NOPWD_LOGIN_CODE_KEY+loginCode, noPwdLoginBody, 30, TimeUnit.MINUTES);
        ajax.put("loginCode", loginCode);
        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo" )
    @ApiOperation("获取用户信息" )
    public AjaxResult getInfo() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = null;
        if(loginUser.getCurrRoleId() != null) {
        	permissions = permissionService.getMenuPermission(user, loginUser.getCurrRoleId());
        } else {
        	permissions = permissionService.getMenuPermission(user);
        }
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user" , user);
        ajax.put("roles" , roles);
        ajax.put("permissions" , permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters" )
    @ApiOperation("获取路由信息" )
    public AjaxResult getRouters() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        // 用户信息
        SysUser user = loginUser.getUser();
        Long currRoleId = loginUser.getCurrRoleId();
        List<SysMenu> menus = new ArrayList<>();
        if(currRoleId != null) {
        	menus = menuService.selectMenuTreeByUserRole(user.getUserId(), currRoleId);
        } else {
        	menus = menuService.selectMenuTreeByUserId(user.getUserId());
        }
        return AjaxResult.success(menuService.buildMenus(menus));
    }
    
}
