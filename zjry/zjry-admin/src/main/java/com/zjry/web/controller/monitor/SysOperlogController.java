package com.zjry.web.controller.monitor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.system.domain.SysOperLog;
import com.zjry.system.service.ISysOperLogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 操作日志记录
 *
 * @author zjry
 */
@RestController
@RequestMapping("/monitor/operlog" )
@Api(tags = {"【操作日志】" })
public class SysOperlogController extends BaseController {
    @Autowired
    private ISysOperLogService operLogService;

    @PreAuthorize("@ss.hasPermi('monitor:operlog:list')" )
    @GetMapping("/list" )
    @ApiOperation("获取操作日志" )
    public TableDataInfo list(SysOperLog operLog) {
        startPage();
        List<SysOperLog> list = operLogService.selectOperLogList(operLog);
        return getDataTable(list);
    }

    @Log(title = "操作日志" , businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')" )
    @GetMapping("/export" )
    @ApiOperation("导出操作日志" )
    public AjaxResult export(SysOperLog operLog) {
        List<SysOperLog> list = operLogService.selectOperLogList(operLog);
        ExcelUtil<SysOperLog> util = new ExcelUtil<SysOperLog>(SysOperLog.class);
        return util.exportExcel(list, "操作日志" );
    }

    @Log(title = "操作日志" , businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')" )
    @DeleteMapping("/{operIds}" )
    @ApiOperation("移除操作日志" )
    public AjaxResult remove(@PathVariable Long[] operIds) {
        return toAjax(operLogService.deleteOperLogByIds(operIds));
    }

    @Log(title = "操作日志" , businessType = BusinessType.CLEAN)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')" )
    @DeleteMapping("/clean" )
    @ApiOperation("清除操作日志" )
    public AjaxResult clean() {
        operLogService.cleanOperLog();
        return AjaxResult.success();
    }
    
    @GetMapping("/queryOperStatistics" )
    @ApiOperation("操作日志统计" )
    public AjaxResult queryOperStatistics(@RequestParam("operName") String operName) {
    	return AjaxResult.success(operLogService.selectOperLogStatistics(operName));
    }
}
