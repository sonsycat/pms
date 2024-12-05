package com.zjry.pms.workload.controller;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjry.pms.workload.domain.WorkloadDto;
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
import com.zjry.pms.workload.domain.PmsWorkload;
import com.zjry.pms.workload.service.IPmsWorkloadService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 工作量Controller
 *
 * @author zjry
 * @date 2022-09-29
 */
@RestController
@RequestMapping("/workload/workload")
@Api(tags = {"【工作量】"})
public class PmsWorkloadController extends BaseController {

    @Autowired
    private IPmsWorkloadService pmsWorkloadService;

    /**
     * 查询工作量列表
     */
    @PreAuthorize("@ss.hasPermi('workload:workload:list')")
    @GetMapping("/list")
    @ApiOperation("查询工作量列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsWorkload pmsWorkload) {
        startPage();
        List<PmsWorkload> list = pmsWorkloadService.selectPmsWorkloadList(pmsWorkload);
        return getDataTable(list);
    }

    /**
     * 导出工作量列表
     */
    @PreAuthorize("@ss.hasPermi('workload:workload:export')")
    @Log(title = "工作量", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出工作量列表Excel")
    public AjaxResult export(PmsWorkload pmsWorkload) {
        List<PmsWorkload> list = pmsWorkloadService.selectPmsWorkloadList(pmsWorkload);
        ExcelUtil<PmsWorkload> util = new ExcelUtil<PmsWorkload>(PmsWorkload.class);
        return util.exportExcel(list, "工作量数据");
    }

    /**
     * 获取工作量详细信息
     */
    @PreAuthorize("@ss.hasPermi('workload:workload:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取工作量详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsWorkloadService.selectPmsWorkloadById(id));
    }

    /**
     * 新增工作量
     */
    @PreAuthorize("@ss.hasPermi('workload:workload:add')")
    @Log(title = "工作量", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增工作量")
    public AjaxResult add(@RequestBody PmsWorkload pmsWorkload) {
        return pmsWorkloadService.insertPmsWorkload(pmsWorkload);
    }

    /**
     * 修改工作量
     */
    @PreAuthorize("@ss.hasPermi('workload:workload:edit')")
    @Log(title = "工作量", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改工作量")
    public AjaxResult edit(@RequestBody PmsWorkload pmsWorkload) {
        return pmsWorkloadService.updatePmsWorkload(pmsWorkload);
    }

    /**
     * 删除工作量
     */
    @PreAuthorize("@ss.hasPermi('workload:workload:remove')")
    @Log(title = "工作量", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除工作量")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsWorkloadService.deletePmsWorkloadByIds(ids));
    }

    /**
     * 工作量-复制
     */
    @PreAuthorize("@ss.hasPermi('workload:workload:edit')")
    @Log(title = "工作量", businessType = BusinessType.UPDATE)
    @PostMapping("copy")
    @ApiOperation("复制工作量")
    public AjaxResult remove(@RequestBody WorkloadDto dto) {
        QueryWrapper<PmsWorkload> qw = new QueryWrapper<>();
        qw.lambda().select(PmsWorkload::getId).eq(PmsWorkload::getWorkloadDate, dto.getNowDate()).last("LIMIT 1");
        PmsWorkload one = pmsWorkloadService.getOne(qw);
        if(null == one){
            return AjaxResult.error("复制的数据不存在");
        }
        return toAjax(pmsWorkloadService.copy(dto));
    }

}
