package com.zjry.advanceQuery.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zjry.advanceQuery.domain.SysAdvanceQueryConfig;
import com.zjry.advanceQuery.mapper.SysAdvanceQueryConfigMapper;
import com.zjry.advanceQuery.service.ISysAdvanceQueryConfigService;
import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.poi.ExcelUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 高级查询配置Controller
 *
 * @author yangtao
 * @date 2021-10-15
 */
@RestController
@RequestMapping("/advanceQueryConfig")
@Api(tags={"【高级查询配置】"})
public class SysAdvanceQueryConfigController extends BaseController {
    @Autowired
    private ISysAdvanceQueryConfigService sysAdvanceQueryConfigService;
    @Autowired
    private SysAdvanceQueryConfigMapper sysAdvanceQueryConfigMapper;

    /**
     * 查询高级查询配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:advanceQuery:list')")
    @GetMapping("/list")
    @ApiOperation("查询高级查询配置列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(SysAdvanceQueryConfig sysAdvanceQueryConfig) {
        startPage();
        List<SysAdvanceQueryConfig> list = sysAdvanceQueryConfigService.selectSysAdvanceQueryConfigList(sysAdvanceQueryConfig);
        return getDataTable(list);
    }

    /**
     * 导出高级查询配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:advanceQuery:export')")
    @Log(title = "高级查询配置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出高级查询配置列表Excel")
    public AjaxResult export(SysAdvanceQueryConfig sysAdvanceQueryConfig) {
        List<SysAdvanceQueryConfig> list = sysAdvanceQueryConfigService.selectSysAdvanceQueryConfigList(sysAdvanceQueryConfig);
        ExcelUtil<SysAdvanceQueryConfig> util = new ExcelUtil<SysAdvanceQueryConfig>(SysAdvanceQueryConfig.class);
        return util.exportExcel(list, "高级查询配置数据");
    }

    /**
     * 获取高级查询配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:advanceQuery:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取高级查询配置详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sysAdvanceQueryConfigService.selectSysAdvanceQueryConfigById(id));
    }
    
    /**
     * 获取高级查询配置详细信息（按照路由id)
     */
    @PreAuthorize("@ss.hasPermi('system:advanceQuery:query')")
    @PostMapping("getDetailByRoute")
    @ApiOperation("获取高级查询配置详细信息（按照路由id)")
    public AjaxResult getDetailByRoute(@RequestParam("routeId") String routeId) {
//    	routeId = "system";
        return AjaxResult.success(sysAdvanceQueryConfigService.selectDetailByRouteId(routeId));
    }
    
    /**
     * 新增高级查询配置
     */
    @PreAuthorize("@ss.hasPermi('system:advanceQuery:add')")
    @Log(title = "高级查询配置", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增高级查询配置")
    public AjaxResult add(@RequestBody SysAdvanceQueryConfig sysAdvanceQueryConfig) {
        return toAjax(sysAdvanceQueryConfigService.insertSysAdvanceQueryConfig(sysAdvanceQueryConfig));
    }

    /**
     * 修改高级查询配置
     */
    @PreAuthorize("@ss.hasPermi('system:advanceQuery:edit')")
    @Log(title = "高级查询配置", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改高级查询配置")
    public AjaxResult edit(@RequestBody SysAdvanceQueryConfig sysAdvanceQueryConfig) {
        return toAjax(sysAdvanceQueryConfigService.updateSysAdvanceQueryConfig(sysAdvanceQueryConfig));
    }
    
    /**
     * 保存明细
     */
    @PreAuthorize("@ss.hasPermi('system:advanceQuery:edit')")
    @Log(title = "保存明细", businessType = BusinessType.UPDATE)
    @PostMapping("saveDetail")
    @ApiOperation("保存明细")
    public AjaxResult saveDetail(@RequestBody SysAdvanceQueryConfig sysAdvanceQueryConfig) {
        return toAjax(sysAdvanceQueryConfigService.saveDetail(sysAdvanceQueryConfig));
    }

    /**
     * 删除高级查询配置
     */
    @PreAuthorize("@ss.hasPermi('system:advanceQuery:remove')")
    @Log(title = "高级查询配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除高级查询配置")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sysAdvanceQueryConfigService.deleteSysAdvanceQueryConfigByIds(ids));
    }
    
    @PostMapping("/getTableList")
    @ApiOperation("获取可配置数据库表名")
    public AjaxResult getTableList() {
    	List<Map> list = sysAdvanceQueryConfigMapper.selectDbTableList();
    	for(Map map : list) {
    		map.put("tableLabel", map.get("tableComment") + "(" + map.get("tableName") + ")");
    	}
    	return AjaxResult.success(list);
    }
    
    @PostMapping("/getTableByName")
    @ApiOperation("按照表名查找表")
    public AjaxResult selectTableByName(@RequestParam("tableName") String tableName) {
    	return AjaxResult.success(sysAdvanceQueryConfigMapper.selectTableByName(tableName));
    }
    
    @PostMapping("/getTableFields")
    @ApiOperation("获取数据库表字段信息")
    public AjaxResult getTableFields(@RequestParam("tableName") String tableName) {
    	return AjaxResult.success(sysAdvanceQueryConfigMapper.selectTableFields(tableName));
    }
    
    @PreAuthorize("@ss.hasPermi('system:advanceQuery:query')")
    @PostMapping("getMenuPath")
    @ApiOperation("获取可配置的功能名称")
    public AjaxResult getMenuPath() {
        return AjaxResult.success(sysAdvanceQueryConfigService.selectMenuPath());
    }
    
    @PreAuthorize("@ss.hasPermi('system:advanceQuery:query')")
    @PostMapping("checkData")
    @ApiOperation("校验数据正确性")
    public AjaxResult checkData(@RequestBody SysAdvanceQueryConfig sysAdvanceQueryConfig) {
    	if(!sysAdvanceQueryConfigService.checkSqlIdExists(sysAdvanceQueryConfig.getSqlId())) {
    		return AjaxResult.error("查询语句id不存在！");
    	} else {
    		SysAdvanceQueryConfig config = new SysAdvanceQueryConfig();
    		config.setMenuId(sysAdvanceQueryConfig.getMenuId());
    		List list = sysAdvanceQueryConfigService.selectSysAdvanceQueryConfigList(config);
    		if(list != null && list.size() > 0) {
    			return AjaxResult.error("此功能已配置，请勿重复操作！");
    		}
    	}
        return AjaxResult.success();
    }
    
}
