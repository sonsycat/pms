package com.zjry.pms.basic.controller;

import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.domain.entity.SysUser;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.utils.ServletUtils;
import com.zjry.framework.web.service.SysPermissionService;
import com.zjry.framework.web.service.TokenService;
import com.zjry.pms.basic.domain.basicData.BasicdataUser;
import com.zjry.pms.basic.service.IBasicdataUserService;
import com.zjry.pms.basic.utils.BasicDataUserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * 自定义表单Controller
 *
 * @author yangtao
 * @date 2021-07-27
 */
@RestController
@RequestMapping("/basic/user")
@Api(tags = {"【用户信息】"})
public class BasicUserController extends BaseController {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SysPermissionService permissionService;
    @Autowired
    private IBasicdataUserService basicdataUserService;
    @GetMapping("getInfo")
    @ApiOperation("获取用户信息")
    public AjaxResult getInfo() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        BasicdataUser basicdataUser = BasicDataUserUtils.getUserWithRelations();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("basicdataUser", basicdataUser);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }
    @GetMapping("/initAccountMsg")
    @ApiOperation("获取用户的账套信息")
    public AjaxResult initAccountMsg() {

        BasicdataUser basicdataUser = basicdataUserService.initAccountMsg();
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", basicdataUser);
        return ajax;
    }
    @GetMapping("/initRoleMsg")
    @ApiOperation("获取用户的角色信息")
    public AjaxResult initRoleMsg() {

        BasicdataUser basicdataUser = basicdataUserService.initRoleMsg();
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", basicdataUser);
        return ajax;
    }
}
