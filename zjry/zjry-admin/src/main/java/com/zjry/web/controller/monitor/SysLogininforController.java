package com.zjry.web.controller.monitor;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.system.domain.SysLogininfor;
import com.zjry.system.service.ISysLogininforService;

/**
 * 系统访问记录
 *
 * @author zjry
 */
@RestController
@RequestMapping("/monitor/logininfor" )
@Api(tags = {"【登录日志】" })
public class SysLogininforController extends BaseController {
    @Autowired
    private ISysLogininforService logininforService;

    @PreAuthorize("@ss.hasPermi('monitor:logininfor:list')" )
    @GetMapping("/list" )
    @ApiOperation("获取登录日志" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum" , value = "当前页码" , dataType = "int" , paramType = "query" , required = false),
            @ApiImplicitParam(name = "pageSize" , value = "每页数据量" , dataType = "int" , paramType = "query" , required = false),
    })
    public TableDataInfo list(SysLogininfor logininfor) {
        startPage();
        List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
        return getDataTable(list);
    }

    @Log(title = "登录日志" , businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('monitor:logininfor:export')" )
    @GetMapping("/export" )
    @ApiOperation("导出登录日志" )
    public AjaxResult export(SysLogininfor logininfor) {
        List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
        ExcelUtil<SysLogininfor> util = new ExcelUtil<SysLogininfor>(SysLogininfor.class);
        return util.exportExcel(list, "登录日志" );
    }

    @PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')" )
    @Log(title = "登录日志" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{infoIds}" )
    @ApiOperation("删除登录日志" )
    public AjaxResult remove(@PathVariable Long[] infoIds) {
        return toAjax(logininforService.deleteLogininforByIds(infoIds));
    }

    @PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')" )
    @Log(title = "登录日志" , businessType = BusinessType.CLEAN)
    @DeleteMapping("/clean" )
    @ApiOperation("清除登录日志" )
    public AjaxResult clean() {
        logininforService.cleanLogininfor();
        return AjaxResult.success();
    }
}
