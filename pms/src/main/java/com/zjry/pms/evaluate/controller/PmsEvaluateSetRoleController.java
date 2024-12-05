package com.zjry.pms.evaluate.controller;

import java.util.List;

import com.zjry.pms.evaluate.domain.PmsEvaRleList;
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
import com.zjry.pms.evaluate.domain.PmsEvaluateSetRole;
import com.zjry.pms.evaluate.service.IPmsEvaluateSetRoleService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 指标集角色Controller
 *
 * @author mk
 * @date 2022-08-05
 */
@RestController
@RequestMapping("/evaluate/setrole")
@Api(tags={"【指标集角色】"})
public class PmsEvaluateSetRoleController extends BaseController {
    @Autowired
    private IPmsEvaluateSetRoleService pmsEvaluateSetRoleService;

    /**
     * 查询指标集角色列表
     */
    @GetMapping("/list")
    @ApiOperation("查询指标集角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsEvaluateSetRole pmsEvaluateSetRole) {
        startPage();
        List<PmsEvaluateSetRole> list = pmsEvaluateSetRoleService.selectPmsEvaluateSetRoleList(pmsEvaluateSetRole);
        return getDataTable(list);
    }

    /**
     * 导出指标集角色列表
     */
    @Log(title = "指标集角色", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出指标集角色列表Excel")
    public AjaxResult export(PmsEvaluateSetRole pmsEvaluateSetRole) {
        List<PmsEvaluateSetRole> list = pmsEvaluateSetRoleService.selectPmsEvaluateSetRoleList(pmsEvaluateSetRole);
        ExcelUtil<PmsEvaluateSetRole> util = new ExcelUtil<PmsEvaluateSetRole>(PmsEvaluateSetRole.class);
        return util.exportExcel(list, "指标集角色数据");
    }

    /**
     * 获取指标集角色详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取指标集角色详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsEvaluateSetRoleService.selectPmsEvaluateSetRoleById(id));
    }

    /**
     * 新增指标集角色
     */
    @Log(title = "指标集角色", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增指标集角色")
    public AjaxResult add(@RequestBody PmsEvaRleList pmsEvaRleList) {
        pmsEvaluateSetRoleService.insertPmsEvaluateSetRole(pmsEvaRleList);
        return success();
    }

    /**
     * 修改指标集角色
     */
    @Log(title = "指标集角色", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改指标集角色")
    public AjaxResult edit(@RequestBody PmsEvaluateSetRole pmsEvaluateSetRole) {
        return toAjax(pmsEvaluateSetRoleService.updatePmsEvaluateSetRole(pmsEvaluateSetRole));
    }

    /**
     * 删除指标集角色
     */
    @Log(title = "指标集角色", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除指标集角色")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsEvaluateSetRoleService.deletePmsEvaluateSetRoleByIds(ids));
    }
}
