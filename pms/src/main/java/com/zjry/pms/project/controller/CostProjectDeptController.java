package com.zjry.pms.project.controller;

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
import com.zjry.pms.project.domain.CostProjectDept;
import com.zjry.pms.project.service.ICostProjectDeptService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 成本项目管理_科室Controller
 *
 * @author mk
 * @date 2022-05-11
 */
@RestController
@RequestMapping("/project/dept")
@Api(tags={"【成本项目管理_科室】"})
public class CostProjectDeptController extends BaseController {
    @Autowired
    private ICostProjectDeptService costProjectDeptService;

    /**
     * 查询成本项目管理_科室列表
     */
    @PreAuthorize("@ss.hasPermi('project:dept:list')")
    @GetMapping("/list")
    @ApiOperation("查询成本项目管理_科室列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(CostProjectDept costProjectDept) {
        startPage();
        List<CostProjectDept> list = costProjectDeptService.selectCostProjectDeptList(costProjectDept);
        return getDataTable(list);
    }


    @GetMapping("/selectForDept")
    @ApiOperation("根据科室查询项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo selectForDept(CostProjectDept costProjectDept) {
        startPage();
        List<CostProjectDept> list = costProjectDeptService.selectForDept( costProjectDept);
        return getDataTable(list);
    }



    /**
     * 导出成本项目管理_科室列表
     */
    @PreAuthorize("@ss.hasPermi('project:dept:export')")
    @Log(title = "成本项目管理_科室", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出成本项目管理_科室列表Excel")
    public AjaxResult export(CostProjectDept costProjectDept) {
        List<CostProjectDept> list = costProjectDeptService.selectCostProjectDeptList(costProjectDept);
        ExcelUtil<CostProjectDept> util = new ExcelUtil<CostProjectDept>(CostProjectDept.class);
        return util.exportExcel(list, "成本项目管理_科室数据");
    }

    /**
     * 获取成本项目管理_科室详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:dept:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取成本项目管理_科室详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(costProjectDeptService.selectCostProjectDeptById(id));
    }

    /**
     * 新增成本项目管理_科室
     */
    @PreAuthorize("@ss.hasPermi('project:dept:add')")
    @Log(title = "成本项目管理_科室", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增成本项目管理_科室")
    public AjaxResult add(@RequestBody CostProjectDept costProjectDept) {
        return toAjax(costProjectDeptService.insertCostProjectDept(costProjectDept));
    }

    /**
     * 修改成本项目管理_科室
     */
    @PreAuthorize("@ss.hasPermi('project:dept:edit')")
    @Log(title = "成本项目管理_科室", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改成本项目管理_科室")
    public AjaxResult edit(@RequestBody CostProjectDept costProjectDept) {
        return toAjax(costProjectDeptService.updateCostProjectDept(costProjectDept));
    }

    /**
     * 删除成本项目管理_科室
     */
    @PreAuthorize("@ss.hasPermi('project:dept:remove')")
    @Log(title = "成本项目管理_科室", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除成本项目管理_科室")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(costProjectDeptService.deleteCostProjectDeptByIds(ids));
    }
}
