package com.zjry.pms.workload.controller;

import java.util.List;

import com.zjry.pms.workload.domain.WorkloadTollDto;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.enums.BusinessType;
import com.zjry.pms.workload.domain.PmsWorkloadToll;
import com.zjry.pms.workload.service.IPmsWorkloadTollService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 工作量-收费项设置Controller
 *
 * @author zjry
 * @date 2022-09-29
 */
@RestController
@RequestMapping("/workload/toll")
@Api(tags = {"【工作量-收费项设置】"})
public class PmsWorkloadTollController extends BaseController {

    @Autowired
    private IPmsWorkloadTollService pmsWorkloadTollService;

    /**
     * 查询工作量-收费项设置列表
     */
    @PreAuthorize("@ss.hasPermi('commission:toll:list')")
    @GetMapping("/list")
    @ApiOperation("查询工作量-收费项设置列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsWorkloadToll pmsWorkloadToll) {
        startPage();
        List<PmsWorkloadToll> list = pmsWorkloadTollService.selectPmsWorkloadTollList(pmsWorkloadToll);
        return getDataTable(list);
    }

    /**
     * 导出工作量-收费项设置列表
     */
    @PreAuthorize("@ss.hasPermi('commission:toll:export')")
    @Log(title = "工作量-收费项设置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出工作量-收费项设置列表Excel")
    public AjaxResult export(PmsWorkloadToll pmsWorkloadToll) {
        List<PmsWorkloadToll> list = pmsWorkloadTollService.selectPmsWorkloadTollList(pmsWorkloadToll);
        ExcelUtil<PmsWorkloadToll> util = new ExcelUtil<PmsWorkloadToll>(PmsWorkloadToll.class);
        return util.exportExcel(list, "工作量-收费项设置数据");
    }

    /**
     * 获取工作量-收费项设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('commission:toll:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取工作量-收费项设置详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsWorkloadTollService.selectPmsWorkloadTollById(id));
    }

    /**
     * 新增工作量-收费项设置
     */
    @PreAuthorize("@ss.hasPermi('commission:toll:add')")
    @Log(title = "工作量-收费项设置", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增工作量-收费项设置")
    public AjaxResult add(@RequestBody PmsWorkloadToll pmsWorkloadToll) {
        return toAjax(pmsWorkloadTollService.insertPmsWorkloadToll(pmsWorkloadToll));
    }

    /**
     * 修改工作量-收费项设置
     */
    @PreAuthorize("@ss.hasPermi('commission:toll:edit')")
    @Log(title = "工作量-收费项设置", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改工作量-收费项设置")
    public AjaxResult edit(@RequestBody PmsWorkloadToll pmsWorkloadToll) {
        return toAjax(pmsWorkloadTollService.updatePmsWorkloadToll(pmsWorkloadToll));
    }

    /**
     * 删除工作量-收费项设置
     */
    @PreAuthorize("@ss.hasPermi('commission:toll:remove')")
    @Log(title = "工作量-收费项设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除工作量-收费项设置")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsWorkloadTollService.deletePmsWorkloadTollByIds(ids));
    }

    /**
     * 查询工作量-收费项设置列表
     */
    @PreAuthorize("@ss.hasPermi('commission:toll:list')")
    @PostMapping("/listAll")
    @ApiOperation("查询收费项目-收费项设置列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectCode", value = "工作量编码", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "type", value = "类型,1已选,2未选", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "hisName", value = "名称", dataType = "string", paramType = "query", required = false),
    })
    public AjaxResult listAll(@RequestBody WorkloadTollDto dto) {
        List<PmsWorkloadToll> list = pmsWorkloadTollService.selectAll(dto.getProjectCode(), dto.getType(), dto.getHisName(), dto.getExistCode());
        return AjaxResult.success(list);
    }

    /**
     * 查询工作量-收费项设置列表
     */
    @PreAuthorize("@ss.hasPermi('commission:toll:edit')")
    @PostMapping("/save")
    @ApiOperation("查询收费项目-收费项设置保存")
    public AjaxResult save(@RequestBody WorkloadTollDto dto) {
        return pmsWorkloadTollService.saveToll(dto);
    }

}
