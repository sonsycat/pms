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
import com.zjry.pms.workload.domain.PmsWorkloadHis;
import com.zjry.pms.workload.service.IPmsWorkloadHisService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 工作量-HIS收费项目Controller
 *
 * @author zjry
 * @date 2022-09-29
 */
@RestController
@RequestMapping("/workload/his")
@Api(tags={"【工作量-HIS收费项目】"})
public class PmsWorkloadHisController extends BaseController {
    @Autowired
    private IPmsWorkloadHisService pmsWorkloadHisService;

    /**
     * 查询工作量-HIS收费项目列表
     */
    @PreAuthorize("@ss.hasPermi('commission:his:list')")
    @GetMapping("/list")
    @ApiOperation("查询工作量-HIS收费项目列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsWorkloadHis pmsWorkloadHis) {
        startPage();
        List<PmsWorkloadHis> list = pmsWorkloadHisService.selectPmsWorkloadHisList(pmsWorkloadHis);
        return getDataTable(list);
    }

    /**
     * 导出工作量-HIS收费项目列表
     */
    @PreAuthorize("@ss.hasPermi('commission:his:export')")
    @Log(title = "工作量-HIS收费项目", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出工作量-HIS收费项目列表Excel")
    public AjaxResult export(PmsWorkloadHis pmsWorkloadHis) {
        List<PmsWorkloadHis> list = pmsWorkloadHisService.selectPmsWorkloadHisList(pmsWorkloadHis);
        ExcelUtil<PmsWorkloadHis> util = new ExcelUtil<PmsWorkloadHis>(PmsWorkloadHis.class);
        return util.exportExcel(list, "工作量-HIS收费项目数据");
    }

    /**
     * 获取工作量-HIS收费项目详细信息
     */
    @PreAuthorize("@ss.hasPermi('commission:his:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取工作量-HIS收费项目详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsWorkloadHisService.selectPmsWorkloadHisById(id));
    }

    /**
     * 新增工作量-HIS收费项目
     */
    @PreAuthorize("@ss.hasPermi('commission:his:add')")
    @Log(title = "工作量-HIS收费项目", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增工作量-HIS收费项目")
    public AjaxResult add(@RequestBody PmsWorkloadHis pmsWorkloadHis) {
        return toAjax(pmsWorkloadHisService.insertPmsWorkloadHis(pmsWorkloadHis));
    }

    /**
     * 修改工作量-HIS收费项目
     */
    @PreAuthorize("@ss.hasPermi('commission:his:edit')")
    @Log(title = "工作量-HIS收费项目", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改工作量-HIS收费项目")
    public AjaxResult edit(@RequestBody PmsWorkloadHis pmsWorkloadHis) {
        return toAjax(pmsWorkloadHisService.updatePmsWorkloadHis(pmsWorkloadHis));
    }

    /**
     * 删除工作量-HIS收费项目
     */
    @PreAuthorize("@ss.hasPermi('commission:his:remove')")
    @Log(title = "工作量-HIS收费项目", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除工作量-HIS收费项目")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsWorkloadHisService.deletePmsWorkloadHisByIds(ids));
    }
}
