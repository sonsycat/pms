package com.zjry.pms.commission.controller;

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
import com.zjry.pms.commission.domain.SeparateAccountSort;
import com.zjry.pms.commission.service.ISeparateAccountSortService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 独立核算提成比类别Controller
 *
 * @author zjry
 * @date 2022-09-27
 */
@RestController
@RequestMapping("/commission/sort")
@Api(tags = {"【独立核算提成比类别】"})
public class SeparateAccountSortController extends BaseController {

    @Autowired
    private ISeparateAccountSortService separateAccountSortService;

    /**
     * 查询独立核算提成比类别列表
     */
    @PreAuthorize("@ss.hasPermi('commission:sort:list')")
    @GetMapping("/list")
    @ApiOperation("查询独立核算提成比类别列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(SeparateAccountSort separateAccountSort) {
        startPage();
        List<SeparateAccountSort> list = separateAccountSortService.selectSeparateAccountSortList(separateAccountSort);
        return getDataTable(list);
    }

    /**
     * 导出独立核算提成比类别列表
     */
    @PreAuthorize("@ss.hasPermi('commission:sort:export')")
    @Log(title = "独立核算提成比类别", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出独立核算提成比类别列表Excel")
    public AjaxResult export(SeparateAccountSort separateAccountSort) {
        List<SeparateAccountSort> list = separateAccountSortService.selectSeparateAccountSortList(separateAccountSort);
        ExcelUtil<SeparateAccountSort> util = new ExcelUtil<SeparateAccountSort>(SeparateAccountSort.class);
        return util.exportExcel(list, "独立核算提成比类别数据");
    }

    /**
     * 获取独立核算提成比类别详细信息
     */
    @PreAuthorize("@ss.hasPermi('commission:sort:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取独立核算提成比类别详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(separateAccountSortService.selectSeparateAccountSortById(id));
    }

    /**
     * 新增独立核算提成比类别
     */
    @PreAuthorize("@ss.hasPermi('commission:sort:add')")
    @Log(title = "独立核算提成比类别", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增独立核算提成比类别")
    public AjaxResult add(@RequestBody SeparateAccountSort separateAccountSort) {
        return separateAccountSortService.insertSeparateAccountSort(separateAccountSort);
    }

    /**
     * 修改独立核算提成比类别
     */
    @PreAuthorize("@ss.hasPermi('commission:sort:edit')")
    @Log(title = "独立核算提成比类别", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改独立核算提成比类别")
    public AjaxResult edit(@RequestBody SeparateAccountSort separateAccountSort) {
        return separateAccountSortService.updateSeparateAccountSort(separateAccountSort);
    }

    /**
     * 删除独立核算提成比类别
     */
    @PreAuthorize("@ss.hasPermi('commission:sort:remove')")
    @Log(title = "独立核算提成比类别", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除独立核算提成比类别")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return separateAccountSortService.deleteSeparateAccountSortByIds(ids);
    }
}
