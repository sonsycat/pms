package com.zjry.web.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.system.domain.SysUserFavourite;
import com.zjry.system.service.ISysUserFavouriteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 收藏夹Controller
 *
 * @author yangtao
 * @date 2021-12-07
 */
@RestController
@RequestMapping("/system/favourite")
@Api(tags={"【收藏夹】"})
public class SysUserFavouriteController extends BaseController {
    @Autowired
    private ISysUserFavouriteService sysUserFavouriteService;
    
    /**
     * 查询收藏夹列表
     */
    @PreAuthorize("@ss.hasPermi('system:favourite:list')")
    @GetMapping("/list")
    @ApiOperation("查询收藏夹列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(SysUserFavourite sysUserFavourite) {
        startPage();
        List<SysUserFavourite> list = sysUserFavouriteService.selectSysUserFavouriteList(sysUserFavourite);
        return getDataTable(list);
    }

    /**
     * 获取收藏夹详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:favourite:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取收藏夹详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sysUserFavouriteService.selectSysUserFavouriteById(id));
    }

    /**
     * 新增收藏夹
     */
    @Log(title = "收藏夹", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增收藏夹")
    public AjaxResult add(@RequestBody SysUserFavourite sysUserFavourite) {
        return toAjax(sysUserFavouriteService.insertSysUserFavourite(sysUserFavourite));
    }

    /**
     * 修改收藏夹
     */
    @PreAuthorize("@ss.hasPermi('system:favourite:edit')")
    @Log(title = "收藏夹", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改收藏夹")
    public AjaxResult edit(@RequestBody SysUserFavourite sysUserFavourite) {
        return toAjax(sysUserFavouriteService.updateSysUserFavourite(sysUserFavourite));
    }

    /**
     * 删除收藏夹
     */
    @PreAuthorize("@ss.hasPermi('system:favourite:remove')")
    @Log(title = "收藏夹", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除收藏夹")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sysUserFavouriteService.deleteSysUserFavouriteByIds(ids));
    }
}
