package com.zjry.pms.revenue.controller;

import java.util.List;

import com.zjry.pms.revenue.domain.PmRevDept;
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
import com.zjry.pms.revenue.domain.PmsRevenueDept;
import com.zjry.pms.revenue.service.IPmsRevenueDeptService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 收入核算科室设置Controller
 *
 * @author mk
 * @date 2022-06-21
 */
@RestController
@RequestMapping("/revenue/revenuedept")
@Api(tags={"【收入核算科室设置】"})
public class PmsRevenueDeptController extends BaseController {
    @Autowired
    private IPmsRevenueDeptService pmsRevenueDeptService;

    /**
     * 查询收入核算科室设置列表
     */
    @GetMapping("/list")
    @ApiOperation("查询收入核算科室设置列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsRevenueDept pmsRevenueDept) {
        startPage();
        List<PmsRevenueDept> list = pmsRevenueDeptService.selectPmsRevenueDeptList(pmsRevenueDept);
        return getDataTable(list);
    }

    /**
     * 导出收入核算科室设置列表
     */
    @PreAuthorize("@ss.hasPermi('revenue:revenuedept:export')")
    @Log(title = "收入核算科室设置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出收入核算科室设置列表Excel")
    public AjaxResult export(PmsRevenueDept pmsRevenueDept) {
        List<PmsRevenueDept> list = pmsRevenueDeptService.selectPmsRevenueDeptList(pmsRevenueDept);
        ExcelUtil<PmsRevenueDept> util = new ExcelUtil<PmsRevenueDept>(PmsRevenueDept.class);
        return util.exportExcel(list, "收入核算科室设置数据");
    }

    /**
     * 获取收入核算科室设置详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取收入核算科室设置详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsRevenueDeptService.selectPmsRevenueDeptById(id));
    }

    /**
     * 新增收入核算科室设置
     */
    @Log(title = "收入核算科室设置", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增收入核算科室设置")
    public AjaxResult add(@RequestBody PmRevDept pmRevDept) {
        try {
            pmsRevenueDeptService.insertPmsRevenueDept(pmRevDept);
        } catch (Exception e) {
            return error();
        }
        return success();
    }

    /**
     * 修改收入核算科室设置
     */
    @Log(title = "收入核算科室设置", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改收入核算科室设置")
    public AjaxResult edit(@RequestBody PmRevDept pmRevDept) {
        try {
            pmsRevenueDeptService.updatePmsRevenueDept(pmRevDept);
        } catch (Exception e) {
            return error();
        }
        return success();
    }

    /**
     * 删除收入核算科室设置
     */
    @PreAuthorize("@ss.hasPermi('revenue:revenuedept:remove')")
    @Log(title = "收入核算科室设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除收入核算科室设置")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsRevenueDeptService.deletePmsRevenueDeptByIds(ids));
    }
}
