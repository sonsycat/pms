package com.zjry.web.controller.system;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zjry.common.annotation.Log;
import com.zjry.common.constant.Constants;
import com.zjry.common.constant.UserConstants;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.domain.entity.SysMenu;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.common.utils.ServletUtils;
import com.zjry.common.utils.StringUtils;
import com.zjry.framework.web.service.TokenService;
import com.zjry.system.service.ISysMenuService;

/**
 * 菜单信息
 *
 * @author zjry
 */
@RestController
@RequestMapping("/system/menu" )
@Api(tags = {"【菜单信息】" })
public class SysMenuController extends BaseController {
    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private TokenService tokenService;

    /**
     * 获取菜单列表
     */
    @PreAuthorize("@ss.hasPermi('system:menu:list')" )
    @GetMapping("/list" )
    @ApiOperation("获取菜单列表" )
    public AjaxResult list(SysMenu menu) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();
        if(loginUser.getCurrRoleId() != null) {
        	menu.getParams().put("roleId", loginUser.getCurrRoleId());
        }
        List<SysMenu> menus = menuService.selectMenuList(menu, userId);
        return AjaxResult.success(menus);
    }

    /**
     * 根据菜单编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:menu:query')" )
    @GetMapping(value = "/{menuId}" )
    @ApiOperation("根据菜单编号获取详细信息" )
    public AjaxResult getInfo(@PathVariable Long menuId) {
        return AjaxResult.success(menuService.selectMenuById(menuId));
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect" )
    @ApiOperation("获取菜单下拉树列表" )
    public AjaxResult treeselect(SysMenu menu) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();
        if(loginUser.getCurrRoleId() != null) {
        	menu.getParams().put("roleId", loginUser.getCurrRoleId());
        }
        List<SysMenu> menus = menuService.selectMenuList(menu, userId);
        return AjaxResult.success(menuService.buildMenuTreeSelect(menus));
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}" )
    @ApiOperation("加载对应角色菜单列表树" )
    public AjaxResult roleMenuTreeselect(@PathVariable("roleId" ) Long roleId) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        List<SysMenu> menus = menuService.selectMenuList(loginUser.getUser().getUserId());
        AjaxResult ajax = AjaxResult.success();
        ajax.put("checkedKeys" , menuService.selectMenuListByRoleId(roleId));
        ajax.put("menus" , menuService.buildMenuTreeSelect(menus));
        return ajax;
    }

    /**
     * 新增菜单
     */
    @PreAuthorize("@ss.hasPermi('system:menu:add')" )
    @Log(title = "菜单管理" , businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增菜单" )
    public AjaxResult add(@Validated @RequestBody SysMenu menu) {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return AjaxResult.error("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在" );
        } 
//        else if (UserConstants.YES_FRAME.equals(menu.getIsFrame())
//                && !StringUtils.startsWithAny(menu.getPath(), Constants.HTTP, Constants.HTTPS)) {
        else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
            return AjaxResult.error("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头" );
        }
        menu.setCreateBy(SecurityUtils.getUsername());
        return toAjax(menuService.insertMenu(menu));
    }

    /**
     * 修改菜单
     */
    @PreAuthorize("@ss.hasPermi('system:menu:edit')" )
    @Log(title = "菜单管理" , businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改菜单" )
    public AjaxResult edit(@Validated @RequestBody SysMenu menu) {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return AjaxResult.error("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在" );
        } 
//        else if (UserConstants.YES_FRAME.equals(menu.getIsFrame())
//                && !StringUtils.startsWithAny(menu.getPath(), Constants.HTTP, Constants.HTTPS)) {
        else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
            return AjaxResult.error("修改菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头" );
        } else if (menu.getMenuId().equals(menu.getParentId())) {
            return AjaxResult.error("修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己" );
        }
        menu.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(menuService.updateMenu(menu));
    }

    /**
     * 删除菜单
     */
    @PreAuthorize("@ss.hasPermi('system:menu:remove')" )
    @Log(title = "菜单管理" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{menuId}" )
    @ApiOperation("删除菜单" )
    public AjaxResult remove(@PathVariable("menuId" ) Long menuId) {
        if (menuService.hasChildByMenuId(menuId)) {
            return AjaxResult.error("存在子菜单,不允许删除" );
        }
        if (menuService.checkMenuExistRole(menuId)) {
            return AjaxResult.error("菜单已分配,不允许删除" );
        }
        return toAjax(menuService.deleteMenuById(menuId));
    }
}