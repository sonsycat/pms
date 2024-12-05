package com.zjry.pms.second.controller;

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
import com.zjry.pms.second.domain.PmsSecondDept;
import com.zjry.pms.second.service.IPmsSecondDeptService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 二次分配科室指标Controller
 *
 * @author maozt
 * @date 2022-09-27
 */
@RestController
@RequestMapping("/second/dept")
@Api(tags={"【二次分配科室指标】"})
public class PmsSecondDeptController extends BaseController {
    @Autowired
    private IPmsSecondDeptService pmsSecondDeptService;

    /**
     * 查询二次分配科室指标列表
     */
    @PreAuthorize("@ss.hasPermi('second:dept:list')")
    @GetMapping("/list")
    @ApiOperation("查询二次分配科室指标列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsSecondDept pmsSecondDept) {
        startPage();
        List<PmsSecondDept> list = pmsSecondDeptService.selectPmsSecondDeptList(pmsSecondDept);
        return getDataTable(list);
    }

    /**
     * 查询二次分配科室指标列表
     */
    @PreAuthorize("@ss.hasPermi('second:dept:list')")
    @GetMapping("/listDeptByCompCode")
    @ApiOperation("查询二次分配科室指标列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo listDeptByCompCode(PmsSecondDept pmsSecondDept) {
        startPage();
        return getDataTable(pmsSecondDeptService.selectPmsSecondDeptListByCompCode(pmsSecondDept));
    }

    /**
     * 导出二次分配科室指标列表
     */
    @PreAuthorize("@ss.hasPermi('second:dept:export')")
    @Log(title = "二次分配科室指标", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出二次分配科室指标列表Excel")
    public AjaxResult export(PmsSecondDept pmsSecondDept) {
        List<PmsSecondDept> list = pmsSecondDeptService.selectPmsSecondDeptList(pmsSecondDept);
        ExcelUtil<PmsSecondDept> util = new ExcelUtil<PmsSecondDept>(PmsSecondDept.class);
        return util.exportExcel(list, "二次分配科室指标数据");
    }

    /**
     * 获取二次分配科室指标详细信息
     */
    @PreAuthorize("@ss.hasPermi('second:dept:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取二次分配科室指标详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsSecondDeptService.selectPmsSecondDeptById(id));
    }

    /**
     * 新增二次分配科室指标
     */
    @PreAuthorize("@ss.hasPermi('second:dept:add')")
    @Log(title = "二次分配科室指标", businessType = BusinessType.INSERT)
    @PostMapping("/saveDept")
    @ApiOperation("新增二次分配科室指标")
    public AjaxResult saveDept(@RequestBody PmsSecondDept pmsSecondDept) {
        pmsSecondDeptService.deletePmsSecondDeptByNowDate(pmsSecondDept);

        int row = 0;
        for (PmsSecondDept secondDept : pmsSecondDept.getDeptList()) {
            secondDept.setNowDate(pmsSecondDept.getNowDate());
            secondDept.setCompCode(pmsSecondDept.getCompCode());
            row += pmsSecondDeptService.insertPmsSecondDept(secondDept);
        }

        return toAjax(row);
    }

    /**
     * 新增二次分配科室指标
     */
    @PreAuthorize("@ss.hasPermi('second:dept:add')")
    @Log(title = "二次分配科室指标", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增二次分配科室指标")
    public AjaxResult add(@RequestBody PmsSecondDept pmsSecondDept) {
        return toAjax(pmsSecondDeptService.insertPmsSecondDept(pmsSecondDept));
    }

    /**
     * 修改二次分配科室指标
     */
    @PreAuthorize("@ss.hasPermi('second:dept:edit')")
    @Log(title = "二次分配科室指标", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改二次分配科室指标")
    public AjaxResult edit(@RequestBody PmsSecondDept pmsSecondDept) {
        return toAjax(pmsSecondDeptService.updatePmsSecondDept(pmsSecondDept));
    }

    /**
     * 删除二次分配科室指标
     */
    @PreAuthorize("@ss.hasPermi('second:dept:remove')")
    @Log(title = "二次分配科室指标", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除二次分配科室指标")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsSecondDeptService.deletePmsSecondDeptByIds(ids));
    }
}
