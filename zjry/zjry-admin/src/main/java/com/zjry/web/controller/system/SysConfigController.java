package com.zjry.web.controller.system;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
import com.zjry.common.annotation.RepeatSubmit;
import com.zjry.common.constant.UserConstants;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.system.domain.SysConfig;
import com.zjry.system.service.ISysConfigService;

/**
 * 参数配置 信息操作处理
 *
 * @author zjry
 */
@RestController
@RequestMapping("/system/config" )
@Api(tags = {"【参数配置】" })
public class SysConfigController extends BaseController {
    @Autowired
    private ISysConfigService configService;

    /**
     * 获取参数配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:config:list')" )
    @GetMapping("/list" )
    @ApiOperation("获取参数配置列表" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum" , value = "当前页码" , dataType = "int" , paramType = "query" , required = false),
            @ApiImplicitParam(name = "pageSize" , value = "每页数据量" , dataType = "int" , paramType = "query" , required = false),
    })
    public TableDataInfo list(SysConfig config) {
        startPage();
        List<SysConfig> list = configService.selectConfigList(config);
        return getDataTable(list);
    }

    @Log(title = "导出参数信息列表Excel" , businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:config:export')" )
    @GetMapping("/export" )
    @ApiOperation("导出参数信息列表Excel" )
    public AjaxResult export(SysConfig config) {
        List<SysConfig> list = configService.selectConfigList(config);
        ExcelUtil<SysConfig> util = new ExcelUtil<SysConfig>(SysConfig.class);
        return util.exportExcel(list, "参数数据" );
    }

    /**
     * 根据参数编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:config:query')" )
    @GetMapping(value = "/{configId}" )
    @ApiOperation("根据参数编号获取详细信息" )
    public AjaxResult getInfo(@PathVariable Long configId) {
        return AjaxResult.success(configService.selectConfigById(configId));
    }

    /**
     * 根据参数键名查询参数值
     */
    @GetMapping(value = "/configKey/{configKey}" )
    @ApiOperation("根据参数键名查询参数值" )
    public AjaxResult getConfigKey(@PathVariable("configKey") String configKey) {
        return AjaxResult.success(configService.selectConfigByKey(configKey));
    }
    
    @GetMapping(value = "/configKey/test" )
    @ApiOperation("根据参数键名查询参数值" )
    public AjaxResult getConfigKey2() {
        return AjaxResult.success("32223");
    }

    /**
     * 新增参数配置
     */
    @PreAuthorize("@ss.hasPermi('system:config:add')" )
    @Log(title = "参数管理" , businessType = BusinessType.INSERT)
    @PostMapping
    @RepeatSubmit
    @ApiOperation("新增参数配置" )
    public AjaxResult add(@Validated @RequestBody SysConfig config) {
        if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
            return AjaxResult.error("新增参数'" + config.getConfigName() + "'失败，参数键名已存在" );
        }
        config.setCreateBy(SecurityUtils.getUsername());
        return toAjax(configService.insertConfig(config));
    }

    /**
     * 修改参数配置
     */
    @PreAuthorize("@ss.hasPermi('system:config:edit')" )
    @Log(title = "参数管理" , businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改参数配置" )
    public AjaxResult edit(@Validated @RequestBody SysConfig config) {
        if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
            return AjaxResult.error("修改参数'" + config.getConfigName() + "'失败，参数键名已存在" );
        }
        config.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(configService.updateConfig(config));
    }

    /**
     * 删除参数配置
     */
    @PreAuthorize("@ss.hasPermi('system:config:remove')" )
    @Log(title = "参数管理" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{configIds}" )
    @ApiOperation("删除参数配置" )
    public AjaxResult remove(@PathVariable Long[] configIds) {
        configService.deleteConfigByIds(configIds);
        return success();
    }

    /**
     * 刷新参数缓存
     */
    @PreAuthorize("@ss.hasPermi('system:config:remove')" )
    @Log(title = "参数管理" , businessType = BusinessType.CLEAN)
    @DeleteMapping("/refreshCache" )
    @ApiOperation("刷新参数缓存" )
    public AjaxResult refreshCache() {
        configService.resetConfigCache();
        return AjaxResult.success();
    }
}
