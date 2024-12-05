package com.zjry.web.controller.system;

import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.system.domain.SysArea;
import com.zjry.system.service.ISysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 区域信息Controller
 * 
 * @author mingc
 * @date 2021-06-17
 */
@RestController
@RequestMapping("/system/area")
public class SysAreaController extends BaseController
{
    @Autowired
    private ISysAreaService sysAreaService;

    /**
     * 查询区域信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:area:list')")
    @GetMapping("/list")
    public AjaxResult list(SysArea sysArea)
    {
        List<SysArea> list = sysAreaService.selectSysAreaList(sysArea);
        return AjaxResult.success(list);
    }

    /**
     * 导出区域信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:area:export')")
    @Log(title = "区域信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysArea sysArea)
    {
        List<SysArea> list = sysAreaService.selectSysAreaList(sysArea);
        ExcelUtil<SysArea> util = new ExcelUtil<SysArea>(SysArea.class);
        return util.exportExcel(list, "区域信息数据");
    }

    /**
     * 获取区域信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:area:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysAreaService.selectSysAreaById(id));
    }

    /**
     * 新增区域信息
     */
    @PreAuthorize("@ss.hasPermi('system:area:add')")
    @Log(title = "区域信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysArea sysArea)
    {
        return toAjax(sysAreaService.insertSysArea(sysArea));
    }

    /**
     * 修改区域信息
     */
    @PreAuthorize("@ss.hasPermi('system:area:edit')")
    @Log(title = "区域信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysArea sysArea)
    {
        return toAjax(sysAreaService.updateSysArea(sysArea));
    }

    /**
     * 删除区域信息
     */
    @PreAuthorize("@ss.hasPermi('system:area:remove')")
    @Log(title = "区域信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysAreaService.deleteSysAreaByIds(ids));
    }
}
