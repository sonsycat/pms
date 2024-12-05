package com.zjry.pms.evaluate.controller;

import java.util.List;

import com.zjry.pms.evaluate.domain.*;
import com.zjry.pms.evaluate.service.IPmsEvaluateSetIndexService;
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
import com.zjry.pms.evaluate.service.IPmsEvaluateSetDeptDetailService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 指标集与科室管理明细Controller
 *
 * @author mk
 * @date 2022-08-09
 */
@RestController
@RequestMapping("/evaluate/setdeptdetail")
@Api(tags={"【指标集与科室管理明细】"})
public class PmsEvaluateSetDeptDetailController extends BaseController {
    @Autowired
    private IPmsEvaluateSetDeptDetailService pmsEvaluateSetDeptDetailService;
    @Autowired
    private IPmsEvaluateSetIndexService pmsEvaluateSetIndexService;

    /**
     * 查询指标集与科室管理明细列表
     */
    @GetMapping("/list")
    @ApiOperation("查询指标集与科室管理明细列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsEvaluateSetDeptDetail pmsEvaluateSetDeptDetail) {
        startPage();
        List<PmsEvaluateSetDeptDetail> list = pmsEvaluateSetDeptDetailService.selectPmsEvaluateSetDeptDetailList(pmsEvaluateSetDeptDetail);
        return getDataTable(list);
    }

    @GetMapping("/queryList")
    @ApiOperation("查询指标集与科室管理明细列表")
    public AjaxResult queryList(PmsEvaluateSetDeptDetail pmsEvaluateSetDeptDetail) {
        List<PmsEvaluateSetDeptDetail> list = pmsEvaluateSetDeptDetailService.selectPmsEvaluateSetDeptDetailList(pmsEvaluateSetDeptDetail);
        return AjaxResult.success(list);
    }


    /**
     * 导出指标集与科室管理明细列表
     */
    @Log(title = "指标集与科室管理明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出指标集与科室管理明细列表Excel")
    public AjaxResult export(PmsEvaluateSetDeptDetail pmsEvaluateSetDeptDetail) {
        List<PmsEvaluateSetDeptDetail> list = pmsEvaluateSetDeptDetailService.selectPmsEvaluateSetDeptDetailList(pmsEvaluateSetDeptDetail);
        ExcelUtil<PmsEvaluateSetDeptDetail> util = new ExcelUtil<PmsEvaluateSetDeptDetail>(PmsEvaluateSetDeptDetail.class);
        return util.exportExcel(list, "指标集与科室管理明细数据");
    }

    @GetMapping("/querySetInfo")
    @ApiOperation("查询指标集与科室管理明细列表")
    public AjaxResult querySetInfo(PmsEvaluateSetIndex pmsEvaluateSetIndex) {
        List<PmsEvaluateSetIndex> list = pmsEvaluateSetIndexService.selectPmsEvaluateSetIndexList(pmsEvaluateSetIndex);
        return AjaxResult.success(list);
    }


    /**
     * 获取指标集与科室管理明细详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取指标集与科室管理明细详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsEvaluateSetDeptDetailService.selectPmsEvaluateSetDeptDetailById(id));
    }

    /**
     * 新增指标集与科室管理明细
     */
    @Log(title = "指标集与科室管理明细", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增指标集与科室管理明细")
    public AjaxResult add(@RequestBody PmsEvaSetDeptDetailList pmsEvaSetDeptDetailList) {
        pmsEvaluateSetDeptDetailService.insertPmsEvaluateSetDeptDetail(pmsEvaSetDeptDetailList);
        return success();
    }

    @GetMapping("/selectPmsForDKSN")
    @ApiOperation("单科室多指标年列表")
    public AjaxResult selectPmsForDKSN(PmsEvaluateSetDeptDetail pmsEvaluateSetDeptDetail) {
        List<PmsEvaSetDeptDN> list = pmsEvaluateSetDeptDetailService.selectPmsForDKSN(pmsEvaluateSetDeptDetail);
        return AjaxResult.success(list);
    }

    @GetMapping("/selectPmsForDKSY")
    @ApiOperation("单科室多指标月列表")
    public AjaxResult selectPmsForDKSY(PmsEvaluateSetDeptDetail pmsEvaluateSetDeptDetail) {
        List<PmsEvaluateSetDeptDetail> list = pmsEvaluateSetDeptDetailService.selectPmsForDKSY(pmsEvaluateSetDeptDetail);
        return AjaxResult.success(list);
    }

    @PostMapping("/savePmsForDKSY")
    @ApiOperation("新增指标集与科室月")
    public AjaxResult savePmsForDKSY(@RequestBody SetDeptDyList setDeptDyList) {
        pmsEvaluateSetDeptDetailService.savePmsForDKSY(setDeptDyList);
        return success();
    }



    /**
     * 修改指标集与科室管理明细
     */
    @Log(title = "指标集与科室管理明细", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改指标集与科室管理明细")
    public AjaxResult edit(@RequestBody PmsEvaluateSetDeptDetail pmsEvaluateSetDeptDetail) {
        return toAjax(pmsEvaluateSetDeptDetailService.updatePmsEvaluateSetDeptDetail(pmsEvaluateSetDeptDetail));
    }

    /**
     * 删除指标集与科室管理明细
     */
    @Log(title = "指标集与科室管理明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除指标集与科室管理明细")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsEvaluateSetDeptDetailService.deletePmsEvaluateSetDeptDetailByIds(ids));
    }

    /**
     * 查询该科室对应BSC分类及总分
     */
    @GetMapping(value = "/getBscScoreById/{id}")
    @ApiOperation("查询该科室对应BSC分类及总分")
    public AjaxResult getBscScoreById(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsEvaluateSetDeptDetailService.getBscScoreById(id));
    }
}
