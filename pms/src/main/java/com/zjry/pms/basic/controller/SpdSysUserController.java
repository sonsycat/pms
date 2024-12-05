package com.zjry.pms.basic.controller;

import java.util.List;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.enums.BusinessType;
import com.zjry.pms.basic.domain.SpdSysUser;
import com.zjry.pms.basic.service.ISpdSysUserService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 用户信息Controller
 *
 * @author yzh
 * @date 2021-11-15
 */
@RestController
@RequestMapping("/basicUser/user")
@Api(tags={"【用户信息】"})
public class SpdSysUserController extends BaseController {
    @Autowired
    private ISpdSysUserService spdSysUserService;

    /**
     * 查询用户信息列表
     */
    @PreAuthorize("@ss.hasPermi('basicUser:user:list')")
    @GetMapping("/list")
    @ApiOperation("查询用户信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(SpdSysUser spdSysUser) {
        startPage();
        List<SpdSysUser> list = spdSysUserService.selectSpdSysUserList(spdSysUser);
        return getDataTable(list);
    }

    /**
     * 导出用户信息列表
     */
    @PreAuthorize("@ss.hasPermi('basicUser:user:export')")
    @Log(title = "用户信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出用户信息列表Excel")
    public AjaxResult export(SpdSysUser spdSysUser) {
        List<SpdSysUser> list = spdSysUserService.selectSpdSysUserList(spdSysUser);
        ExcelUtil<SpdSysUser> util = new ExcelUtil<SpdSysUser>(SpdSysUser.class);
        return util.exportExcel(list, "用户信息数据");
    }

    /**
     * 获取用户信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('basicUser:user:query')")
    @GetMapping(value = "/{userId}")
    @ApiOperation("获取用户信息详细信息")
    public AjaxResult getInfo(@PathVariable("userId") Long userId) {
        return AjaxResult.success(spdSysUserService.selectSpdSysUserById(userId));
    }

    /**
     * 新增用户信息
     */
    @PreAuthorize("@ss.hasPermi('basicUser:user:add')")
    @Log(title = "用户信息", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增用户信息")
    public AjaxResult add(@RequestBody SpdSysUser spdSysUser) {
        return toAjax(spdSysUserService.insertSpdSysUser(spdSysUser));
    }

    /**
     * 修改用户信息
     */
    @PreAuthorize("@ss.hasPermi('basicUser:user:edit')")
    @Log(title = "用户信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改用户信息")
    public AjaxResult edit(@RequestBody SpdSysUser spdSysUser) {
        return toAjax(spdSysUserService.updateSpdSysUser(spdSysUser));
    }

    /**
     * 删除用户信息
     */
    @PreAuthorize("@ss.hasPermi('basicUser:user:remove')")
    @Log(title = "用户信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    @ApiOperation("删除用户信息")
    public AjaxResult remove(@PathVariable Long[] userIds) {
        return toAjax(spdSysUserService.deleteSpdSysUserByIds(userIds));
    }
}
