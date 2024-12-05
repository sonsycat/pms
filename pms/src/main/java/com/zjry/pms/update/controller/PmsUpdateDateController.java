package com.zjry.pms.update.controller;

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
import com.zjry.pms.update.domain.PmsUpdateDate;
import com.zjry.pms.update.service.IPmsUpdateDateService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 更新日期Controller
 *
 * @author maozt
 * @date 2022-07-06
 */
@RestController
@RequestMapping("/update/date")
@Api(tags={"【更新日期】"})
public class PmsUpdateDateController extends BaseController {
    @Autowired
    private IPmsUpdateDateService pmsUpdateDateService;

    /**
     * 查询更新日期列表
     */
    @PreAuthorize("@ss.hasPermi('update:date:list')")
    @GetMapping("/list")
    @ApiOperation("查询更新日期列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsUpdateDate pmsUpdateDate) {
        startPage();
        List<PmsUpdateDate> list = pmsUpdateDateService.selectPmsUpdateDateList(pmsUpdateDate);
        return getDataTable(list);
    }

    /**
     * 导出更新日期列表
     */
    @PreAuthorize("@ss.hasPermi('update:date:export')")
    @Log(title = "更新日期", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出更新日期列表Excel")
    public AjaxResult export(PmsUpdateDate pmsUpdateDate) {
        List<PmsUpdateDate> list = pmsUpdateDateService.selectPmsUpdateDateList(pmsUpdateDate);
        ExcelUtil<PmsUpdateDate> util = new ExcelUtil<PmsUpdateDate>(PmsUpdateDate.class);
        return util.exportExcel(list, "更新日期数据");
    }

    /**
     * 获取更新日期详细信息
     */
    @PreAuthorize("@ss.hasPermi('update:date:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取更新日期详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsUpdateDateService.selectPmsUpdateDateById(id));
    }

    /**
     * 新增更新日期
     */
    @PreAuthorize("@ss.hasPermi('update:date:add')")
    @Log(title = "更新日期", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增更新日期")
    public AjaxResult add(@RequestBody PmsUpdateDate pmsUpdateDate) {
        try {
            return toAjax(pmsUpdateDateService.insertPmsUpdateDate(pmsUpdateDate));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 修改更新日期
     */
    @PreAuthorize("@ss.hasPermi('update:date:edit')")
    @Log(title = "更新日期", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改更新日期")
    public AjaxResult edit(@RequestBody PmsUpdateDate pmsUpdateDate) {
        return toAjax(pmsUpdateDateService.updatePmsUpdateDate(pmsUpdateDate));
    }

    /**
     * 删除更新日期
     */
    @PreAuthorize("@ss.hasPermi('update:date:remove')")
    @Log(title = "更新日期", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除更新日期")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsUpdateDateService.deletePmsUpdateDateByIds(ids));
    }
}
