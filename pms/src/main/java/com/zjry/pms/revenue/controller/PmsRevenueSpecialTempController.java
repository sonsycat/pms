package com.zjry.pms.revenue.controller;

import java.util.List;

import com.zjry.common.utils.StringUtils;
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
import com.zjry.pms.revenue.domain.PmsRevenueSpecialTemp;
import com.zjry.pms.revenue.service.IPmsRevenueSpecialTempService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 特殊收入项目方案临时Controller
 *
 * @author maozt
 * @date 2022-06-21
 */
@RestController
@RequestMapping("/revenue/specialTemp")
@Api(tags={"【特殊收入项目方案临时】"})
public class PmsRevenueSpecialTempController extends BaseController {
    @Autowired
    private IPmsRevenueSpecialTempService pmsRevenueSpecialTempService;

    /**
     * 查询特殊收入项目方案临时列表
     */
    @PreAuthorize("@ss.hasPermi('revenue:special:list')")
    @GetMapping("/list")
    @ApiOperation("查询特殊收入项目方案临时列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsRevenueSpecialTemp pmsRevenueSpecialTemp) {
        startPage();
        List<PmsRevenueSpecialTemp> list = pmsRevenueSpecialTempService.selectPmsRevenueSpecialTempListNoDel(pmsRevenueSpecialTemp);
        return getDataTable(list);
    }

    /**
     * 导出特殊收入项目方案临时列表
     */
    @PreAuthorize("@ss.hasPermi('revenue:special:export')")
    @Log(title = "特殊收入项目方案临时", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出特殊收入项目方案临时列表Excel")
    public AjaxResult export(PmsRevenueSpecialTemp pmsRevenueSpecialTemp) {
        List<PmsRevenueSpecialTemp> list = pmsRevenueSpecialTempService.selectPmsRevenueSpecialTempList(pmsRevenueSpecialTemp);
        ExcelUtil<PmsRevenueSpecialTemp> util = new ExcelUtil<PmsRevenueSpecialTemp>(PmsRevenueSpecialTemp.class);
        return util.exportExcel(list, "特殊收入项目方案临时数据");
    }

    /**
     * 获取特殊收入项目方案临时详细信息
     */
    @PreAuthorize("@ss.hasPermi('revenue:special:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取特殊收入项目方案临时详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsRevenueSpecialTempService.selectPmsRevenueSpecialTempById(id));
    }

    /**
     * 新增特殊收入项目方案临时
     */
    @PreAuthorize("@ss.hasPermi('revenue:special:add')")
    @Log(title = "特殊收入项目方案临时", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增特殊收入项目方案临时")
    public AjaxResult add(@RequestBody PmsRevenueSpecialTemp pmsRevenueSpecialTemp) {
        return toAjax(pmsRevenueSpecialTempService.insertPmsRevenueSpecialTemp(pmsRevenueSpecialTemp));
    }

    /**
     * 修改特殊收入项目方案临时
     */
    @PreAuthorize("@ss.hasPermi('revenue:special:edit')")
    @Log(title = "特殊收入项目方案临时", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改特殊收入项目方案临时")
    public AjaxResult edit(@RequestBody PmsRevenueSpecialTemp pmsRevenueSpecialTemp) {
        if ("0".equals(pmsRevenueSpecialTemp.getType())) {
            pmsRevenueSpecialTemp.setType("2");
        }
        return toAjax(pmsRevenueSpecialTempService.updatePmsRevenueSpecialTemp(pmsRevenueSpecialTemp));
    }

    /**
     * 删除特殊收入项目方案临时
     */
    @PreAuthorize("@ss.hasPermi('revenue:special:remove')")
    @Log(title = "特殊收入项目方案临时", businessType = BusinessType.DELETE)
    @DeleteMapping
    @ApiOperation("删除特殊收入项目方案临时")
    public AjaxResult remove(@RequestBody PmsRevenueSpecialTemp pmsRevenueSpecialTemp) {
        pmsRevenueSpecialTemp.setType("3");
        return toAjax(pmsRevenueSpecialTempService.updatePmsRevenueSpecialTemp(pmsRevenueSpecialTemp));
    }
}
