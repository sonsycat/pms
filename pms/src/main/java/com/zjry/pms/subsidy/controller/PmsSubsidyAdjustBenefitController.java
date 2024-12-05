package com.zjry.pms.subsidy.controller;

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
import com.zjry.pms.subsidy.domain.PmsSubsidyAdjustBenefit;
import com.zjry.pms.subsidy.service.IPmsSubsidyAdjustBenefitService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 科室效益调整Controller
 *
 * @author maozt
 * @date 2022-08-17
 */
@RestController
@RequestMapping("/subsidy/benefit")
@Api(tags={"【科室效益调整】"})
public class PmsSubsidyAdjustBenefitController extends BaseController {
    @Autowired
    private IPmsSubsidyAdjustBenefitService pmsSubsidyAdjustBenefitService;

    /**
     * 查询科室效益调整列表
     */
    @PreAuthorize("@ss.hasPermi('subsidy:benefit:list')")
    @GetMapping("/list")
    @ApiOperation("查询科室效益调整列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsSubsidyAdjustBenefit pmsSubsidyAdjustBenefit) {
        startPage();
        List<PmsSubsidyAdjustBenefit> list = pmsSubsidyAdjustBenefitService.selectPmsSubsidyAdjustBenefitList(pmsSubsidyAdjustBenefit);
        return getDataTable(list);
    }

    /**
     * 导出科室效益调整列表
     */
    @PreAuthorize("@ss.hasPermi('subsidy:benefit:export')")
    @Log(title = "科室效益调整", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出科室效益调整列表Excel")
    public AjaxResult export(PmsSubsidyAdjustBenefit pmsSubsidyAdjustBenefit) {
        List<PmsSubsidyAdjustBenefit> list = pmsSubsidyAdjustBenefitService.selectPmsSubsidyAdjustBenefitList(pmsSubsidyAdjustBenefit);
        ExcelUtil<PmsSubsidyAdjustBenefit> util = new ExcelUtil<PmsSubsidyAdjustBenefit>(PmsSubsidyAdjustBenefit.class);
        return util.exportExcel(list, "科室效益调整数据");
    }

    /**
     * 获取科室效益调整详细信息
     */
    @PreAuthorize("@ss.hasPermi('subsidy:benefit:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取科室效益调整详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsSubsidyAdjustBenefitService.selectPmsSubsidyAdjustBenefitById(id));
    }

    /**
     * 新增科室效益调整
     */
    @PreAuthorize("@ss.hasPermi('subsidy:benefit:add')")
    @Log(title = "科室效益调整", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增科室效益调整")
    public AjaxResult add(@RequestBody PmsSubsidyAdjustBenefit pmsSubsidyAdjustBenefit) {
        return toAjax(pmsSubsidyAdjustBenefitService.insertPmsSubsidyAdjustBenefit(pmsSubsidyAdjustBenefit));
    }

    /**
     * 修改科室效益调整
     */
    @PreAuthorize("@ss.hasPermi('subsidy:benefit:edit')")
    @Log(title = "科室效益调整", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改科室效益调整")
    public AjaxResult edit(@RequestBody PmsSubsidyAdjustBenefit pmsSubsidyAdjustBenefit) {
        return toAjax(pmsSubsidyAdjustBenefitService.updatePmsSubsidyAdjustBenefit(pmsSubsidyAdjustBenefit));
    }

    /**
     * 删除科室效益调整
     */
    @PreAuthorize("@ss.hasPermi('subsidy:benefit:remove')")
    @Log(title = "科室效益调整", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除科室效益调整")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsSubsidyAdjustBenefitService.deletePmsSubsidyAdjustBenefitByIds(ids));
    }
}
