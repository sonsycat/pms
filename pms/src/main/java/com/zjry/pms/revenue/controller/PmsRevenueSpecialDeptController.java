package com.zjry.pms.revenue.controller;

import java.util.List;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
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
import com.zjry.pms.revenue.domain.PmsRevenueSpecialDept;
import com.zjry.pms.revenue.service.IPmsRevenueSpecialDeptService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 特殊收入项目科室设置Controller
 *
 * @author maozt
 * @date 2022-06-23
 */
@RestController
@RequestMapping("/revenue/specialDept")
@Api(tags={"【特殊收入项目科室设置】"})
public class PmsRevenueSpecialDeptController extends BaseController {
    @Autowired
    private IPmsRevenueSpecialDeptService pmsRevenueSpecialDeptService;

    /**
     * 查询特殊收入项目科室设置列表
     */
    @PreAuthorize("@ss.hasPermi('revenue:special:list')")
    @GetMapping("/list")
    @ApiOperation("查询特殊收入项目科室设置列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsRevenueSpecialDept pmsRevenueSpecialDept) {
        startPage();
        List<PmsRevenueSpecialDept> list = pmsRevenueSpecialDeptService.selectPmsRevenueSpecialDeptList(pmsRevenueSpecialDept);
        return getDataTable(list);
    }

    /**
     * 导出特殊收入项目科室设置列表
     */
    @PreAuthorize("@ss.hasPermi('revenue:special:export')")
    @Log(title = "特殊收入项目科室设置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出特殊收入项目科室设置列表Excel")
    public AjaxResult export(PmsRevenueSpecialDept pmsRevenueSpecialDept) {
        List<PmsRevenueSpecialDept> list = pmsRevenueSpecialDeptService.selectPmsRevenueSpecialDeptList(pmsRevenueSpecialDept);
        ExcelUtil<PmsRevenueSpecialDept> util = new ExcelUtil<PmsRevenueSpecialDept>(PmsRevenueSpecialDept.class);
        return util.exportExcel(list, "特殊收入项目科室设置数据");
    }

    /**
     * 获取特殊收入项目科室设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('revenue:special:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取特殊收入项目科室设置详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsRevenueSpecialDeptService.selectPmsRevenueSpecialDeptById(id));
    }

    /**
     * 新增特殊收入项目科室设置
     */
    @PreAuthorize("@ss.hasPermi('revenue:special:add')")
    @Log(title = "特殊收入项目科室设置", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增特殊收入项目科室设置")
    public AjaxResult add(@RequestBody PmsRevenueSpecialDept pmsRevenueSpecialDept) {
        return toAjax(pmsRevenueSpecialDeptService.insertPmsRevenueSpecialDept(pmsRevenueSpecialDept));
    }

    /**
     * 修改特殊收入项目科室设置
     */
    @PreAuthorize("@ss.hasPermi('revenue:special:edit')")
    @Log(title = "特殊收入项目科室设置", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改特殊收入项目科室设置")
    public AjaxResult edit(@RequestBody PmsRevenueSpecialDept pmsRevenueSpecialDept) {
        return toAjax(pmsRevenueSpecialDeptService.updatePmsRevenueSpecialDept(pmsRevenueSpecialDept));
    }

    /**
     * 删除特殊收入项目科室设置
     */
    @PreAuthorize("@ss.hasPermi('revenue:special:remove')")
    @Log(title = "特殊收入项目科室设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除特殊收入项目科室设置")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsRevenueSpecialDeptService.deletePmsRevenueSpecialDeptByIds(ids));
    }

    /**
     * 新增科室设置
     */
    @PreAuthorize("@ss.hasPermi('revenue:special:add')")
    @Log(title = "新增科室设置", businessType = BusinessType.UPDATE)
    @PostMapping("/addSpecialDept")
    @ApiOperation("新增科室设置")
    public AjaxResult addSpecialDept(@RequestBody PmsRevenueSpecialDept pmsRevenueSpecialDept) {
        String specialCode = pmsRevenueSpecialDept.getSpecialCode();
        if (StringUtils.isNotEmpty(specialCode)){
            pmsRevenueSpecialDeptService.deletePmsRevenueSpecialDeptByProjectCode(specialCode);
        }
        pmsRevenueSpecialDeptService.insertPmsRevenueSpecialDeptPl(pmsRevenueSpecialDept);
        return success();
    }
    /**
     * 查询科室设置列表
     * isSProject 1 已选科室 2 待选科室
     * storeCode 关联仓库编码
     */
    @PreAuthorize("@ss.hasPermi('revenue:special:list')")
    @GetMapping("/listSpecialDept")
    @ApiOperation("根据用户查询关联仓库管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo listSpecialDept(PmsRevenueSpecialDept pmsRevenueSpecialDept) {
        startPage();
        List<PmsRevenueSpecialDept> list = pmsRevenueSpecialDeptService.getDeptProject(pmsRevenueSpecialDept);
        return getDataTable(list);
    }
}
