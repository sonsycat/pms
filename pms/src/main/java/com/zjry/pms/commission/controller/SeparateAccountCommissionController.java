package com.zjry.pms.commission.controller;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjry.pms.commission.domain.SeparateAccountCommissionDto;
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
import com.zjry.pms.commission.domain.SeparateAccountCommission;
import com.zjry.pms.commission.service.ISeparateAccountCommissionService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 独立核算提成比Controller
 *
 * @author zjry
 * @date 2022-09-27
 */
@RestController
@RequestMapping("/commission/commission")
@Api(tags = {"【独立核算提成比】"})
public class SeparateAccountCommissionController extends BaseController {

    @Autowired
    private ISeparateAccountCommissionService separateAccountCommissionService;

    /**
     * 查询独立核算提成比列表
     */
    @PreAuthorize("@ss.hasPermi('commission:commission:list')")
    @GetMapping("/list")
    @ApiOperation("查询独立核算提成比列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public AjaxResult list(SeparateAccountCommission separateAccountCommission) {
        return AjaxResult.success(separateAccountCommissionService.selectSeparateAccountCommissionList2(separateAccountCommission));

    }

    /**
     * 导出独立核算提成比列表
     */
    @PreAuthorize("@ss.hasPermi('commission:commission:export')")
    @Log(title = "独立核算提成比", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出独立核算提成比列表Excel")
    public AjaxResult export(SeparateAccountCommission separateAccountCommission) {
        List<SeparateAccountCommission> list = separateAccountCommissionService.selectSeparateAccountCommissionList(separateAccountCommission);
        ExcelUtil<SeparateAccountCommission> util = new ExcelUtil<SeparateAccountCommission>(SeparateAccountCommission.class);
        return util.exportExcel(list, "独立核算提成比数据");
    }

    /**
     * 获取独立核算提成比详细信息
     */
    @PreAuthorize("@ss.hasPermi('commission:commission:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取独立核算提成比详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(separateAccountCommissionService.selectSeparateAccountCommissionById(id));
    }

    /**
     * 新增独立核算提成比
     */
    @PreAuthorize("@ss.hasPermi('commission:commission:add')")
    @Log(title = "独立核算提成比", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增独立核算提成比")
    public AjaxResult add(@RequestBody SeparateAccountCommissionDto data) {
        return toAjax(separateAccountCommissionService.insertSeparateAccountCommission(data));
    }

    /**
     * 复制
     */
    @PreAuthorize("@ss.hasPermi('commission:commission:add')")
    @Log(title = "独立核算提成比复制", businessType = BusinessType.INSERT)
    @PostMapping("copy")
    @ApiOperation("独立核算提成比复制")
    public AjaxResult copy(@RequestBody SeparateAccountCommissionDto dto) {
        return toAjax(separateAccountCommissionService.copy(dto));
    }

    /**
     * 修改独立核算提成比
     */
    @PreAuthorize("@ss.hasPermi('commission:commission:edit')")
    @Log(title = "独立核算提成比", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改独立核算提成比")
    public AjaxResult edit(@RequestBody SeparateAccountCommission separateAccountCommission) {
        return toAjax(separateAccountCommissionService.updateSeparateAccountCommission(separateAccountCommission));
    }

    /**
     * 删除独立核算提成比
     */
    @PreAuthorize("@ss.hasPermi('commission:commission:remove')")
    @Log(title = "独立核算提成比", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除独立核算提成比")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(separateAccountCommissionService.deleteSeparateAccountCommissionByIds(ids));
    }
}
