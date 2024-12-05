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
import com.zjry.pms.subsidy.domain.PmsSubsidyRewardInput;
import com.zjry.pms.subsidy.service.IPmsSubsidyRewardInputService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 单项奖惩录入Controller
 *
 * @author maozt
 * @date 2022-08-16
 */
@RestController
@RequestMapping("/subsidy/input")
@Api(tags={"【单项奖惩录入】"})
public class PmsSubsidyRewardInputController extends BaseController {
    @Autowired
    private IPmsSubsidyRewardInputService pmsSubsidyRewardInputService;

    /**
     * 查询单项奖惩录入列表
     */
    @PreAuthorize("@ss.hasPermi('subsidy:input:list')")
    @GetMapping("/list")
    @ApiOperation("查询单项奖惩录入列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsSubsidyRewardInput pmsSubsidyRewardInput) {
        startPage();
        List<PmsSubsidyRewardInput> list = pmsSubsidyRewardInputService.selectPmsSubsidyRewardInputList(pmsSubsidyRewardInput);
        return getDataTable(list);
    }

    /**
     * 导出单项奖惩录入列表
     */
    @PreAuthorize("@ss.hasPermi('subsidy:input:export')")
    @Log(title = "单项奖惩录入", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出单项奖惩录入列表Excel")
    public AjaxResult export(PmsSubsidyRewardInput pmsSubsidyRewardInput) {
        List<PmsSubsidyRewardInput> list = pmsSubsidyRewardInputService.selectPmsSubsidyRewardInputList(pmsSubsidyRewardInput);
        ExcelUtil<PmsSubsidyRewardInput> util = new ExcelUtil<PmsSubsidyRewardInput>(PmsSubsidyRewardInput.class);
        return util.exportExcel(list, "单项奖惩录入数据");
    }

    /**
     * 获取单项奖惩录入详细信息
     */
    @PreAuthorize("@ss.hasPermi('subsidy:input:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取单项奖惩录入详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsSubsidyRewardInputService.selectPmsSubsidyRewardInputById(id));
    }

    /**
     * 新增单项奖惩录入
     */
    @PreAuthorize("@ss.hasPermi('subsidy:input:add')")
    @Log(title = "单项奖惩录入", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增单项奖惩录入")
    public AjaxResult add(@RequestBody PmsSubsidyRewardInput pmsSubsidyRewardInput) {
        return toAjax(pmsSubsidyRewardInputService.insertPmsSubsidyRewardInput(pmsSubsidyRewardInput));
    }

    /**
     * 修改单项奖惩录入
     */
    @PreAuthorize("@ss.hasPermi('subsidy:input:edit')")
    @Log(title = "单项奖惩录入", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改单项奖惩录入")
    public AjaxResult edit(@RequestBody PmsSubsidyRewardInput pmsSubsidyRewardInput) {
        return toAjax(pmsSubsidyRewardInputService.updatePmsSubsidyRewardInput(pmsSubsidyRewardInput));
    }

    /**
     * 删除单项奖惩录入
     */
    @PreAuthorize("@ss.hasPermi('subsidy:input:remove')")
    @Log(title = "单项奖惩录入", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除单项奖惩录入")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsSubsidyRewardInputService.deletePmsSubsidyRewardInputByIds(ids));
    }
}
