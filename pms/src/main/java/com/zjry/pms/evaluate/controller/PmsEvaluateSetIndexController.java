package com.zjry.pms.evaluate.controller;

import java.util.List;

import com.zjry.pms.evaluate.domain.PmsEvaSetIndexList;
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
import com.zjry.pms.evaluate.domain.PmsEvaluateSetIndex;
import com.zjry.pms.evaluate.service.IPmsEvaluateSetIndexService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 指标集指标Controller
 *
 * @author mk
 * @date 2022-08-04
 */
@RestController
@RequestMapping("/evaluate/setindex")
@Api(tags={"【指标集指标】"})
public class PmsEvaluateSetIndexController extends BaseController {
    @Autowired
    private IPmsEvaluateSetIndexService pmsEvaluateSetIndexService;

    /**
     * 查询指标集指标列表
     */
    @GetMapping("/list")
    @ApiOperation("查询指标集指标列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsEvaluateSetIndex pmsEvaluateSetIndex) {
        startPage();
        List<PmsEvaluateSetIndex> list = pmsEvaluateSetIndexService.selectPmsEvaluateSetIndexList(pmsEvaluateSetIndex);
        return getDataTable(list);
    }

    /**
     * 导出指标集指标列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:setindex:export')")
    @Log(title = "指标集指标", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出指标集指标列表Excel")
    public AjaxResult export(PmsEvaluateSetIndex pmsEvaluateSetIndex) {
        List<PmsEvaluateSetIndex> list = pmsEvaluateSetIndexService.selectPmsEvaluateSetIndexList(pmsEvaluateSetIndex);
        ExcelUtil<PmsEvaluateSetIndex> util = new ExcelUtil<PmsEvaluateSetIndex>(PmsEvaluateSetIndex.class);
        return util.exportExcel(list, "指标集指标数据");
    }

    /**
     * 获取指标集指标详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取指标集指标详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsEvaluateSetIndexService.selectPmsEvaluateSetIndexById(id));
    }

    /**
     * 新增指标集指标
     */
    @Log(title = "指标集指标", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增指标集指标")
    public AjaxResult add(@RequestBody PmsEvaSetIndexList pmsEvaSetIndexList) {
        pmsEvaluateSetIndexService.insertPmsEvaluateSetIndex(pmsEvaSetIndexList);
        return success();
    }

    /**
     * 修改指标集指标
     */
    @PreAuthorize("@ss.hasPermi('evaluate:setindex:edit')")
    @Log(title = "指标集指标", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改指标集指标")
    public AjaxResult edit(@RequestBody PmsEvaluateSetIndex pmsEvaluateSetIndex) {
        return toAjax(pmsEvaluateSetIndexService.updatePmsEvaluateSetIndex(pmsEvaluateSetIndex));
    }

    /**
     * 删除指标集指标
     */
    @Log(title = "指标集指标", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除指标集指标")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsEvaluateSetIndexService.deletePmsEvaluateSetIndexByIds(ids));
    }
}
