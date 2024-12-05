package com.zjry.pms.workload.controller;

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
import com.zjry.pms.workload.domain.PmsWorkloadType;
import com.zjry.pms.workload.service.IPmsWorkloadTypeService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 工作量-类别设置Controller
 *
 * @author zjry
 * @date 2022-09-29
 */
@RestController
@RequestMapping("/workload/type")
@Api(tags={"【工作量-类别设置】"})
public class PmsWorkloadTypeController extends BaseController {
    @Autowired
    private IPmsWorkloadTypeService pmsWorkloadTypeService;

    /**
     * 查询工作量-类别设置列表
     */
    @PreAuthorize("@ss.hasPermi('workload:type:list')")
    @GetMapping("/list")
    @ApiOperation("查询工作量-类别设置列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsWorkloadType pmsWorkloadType) {
        startPage();
        List<PmsWorkloadType> list = pmsWorkloadTypeService.selectPmsWorkloadTypeList(pmsWorkloadType);
        return getDataTable(list);
    }

    /**
     * 导出工作量-类别设置列表
     */
    @PreAuthorize("@ss.hasPermi('workload:type:export')")
    @Log(title = "工作量-类别设置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出工作量-类别设置列表Excel")
    public AjaxResult export(PmsWorkloadType pmsWorkloadType) {
        List<PmsWorkloadType> list = pmsWorkloadTypeService.selectPmsWorkloadTypeList(pmsWorkloadType);
        ExcelUtil<PmsWorkloadType> util = new ExcelUtil<PmsWorkloadType>(PmsWorkloadType.class);
        return util.exportExcel(list, "工作量-类别设置数据");
    }

    /**
     * 获取工作量-类别设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('workload:type:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取工作量-类别设置详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsWorkloadTypeService.selectPmsWorkloadTypeById(id));
    }

    /**
     * 新增工作量-类别设置
     */
    @PreAuthorize("@ss.hasPermi('workload:type:add')")
    @Log(title = "工作量-类别设置", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增工作量-类别设置")
    public AjaxResult add(@RequestBody PmsWorkloadType pmsWorkloadType) {
        return pmsWorkloadTypeService.insertPmsWorkloadType(pmsWorkloadType);
    }

    /**
     * 修改工作量-类别设置
     */
    @PreAuthorize("@ss.hasPermi('workload:type:edit')")
    @Log(title = "工作量-类别设置", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改工作量-类别设置")
    public AjaxResult edit(@RequestBody PmsWorkloadType pmsWorkloadType) {
        return pmsWorkloadTypeService.updatePmsWorkloadType(pmsWorkloadType);
    }

    /**
     * 删除工作量-类别设置
     */
    @PreAuthorize("@ss.hasPermi('workload:type:remove')")
    @Log(title = "工作量-类别设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除工作量-类别设置")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return pmsWorkloadTypeService.deletePmsWorkloadTypeByIds(ids);
    }
}
