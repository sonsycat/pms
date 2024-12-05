package com.zjry.pms.average.controller;

import java.util.List;
import java.util.Map;

import com.zjry.pms.average.domain.PmsAveragePersonDetailList;
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
import com.zjry.pms.average.domain.PmsAveragePersonDetail;
import com.zjry.pms.average.service.IPmsAveragePersonDetailService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 平均奖科人数明细Controller
 *
 * @author zwf
 * @date 2022-09-27
 */
@RestController
@RequestMapping("/average/averageDetail")
@Api(tags={"【平均奖科人数明细】"})
public class PmsAveragePersonDetailController extends BaseController {
    @Autowired
    private IPmsAveragePersonDetailService pmsAveragePersonDetailService;

    /**
     * 查询平均奖科人数明细列表
     */
    @PreAuthorize("@ss.hasPermi('average:averageDetail:list')")
    @GetMapping("/list")
    @ApiOperation("查询平均奖科人数明细列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsAveragePersonDetail pmsAveragePersonDetail) {
        startPage();
        List<PmsAveragePersonDetail> list = pmsAveragePersonDetailService.selectPmsAveragePersonDetailList(pmsAveragePersonDetail);
        return getDataTable(list);
    }

    /**
     * 导出平均奖科人数明细列表
     */
    @PreAuthorize("@ss.hasPermi('average:averageDetail:export')")
    @Log(title = "平均奖科人数明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出平均奖科人数明细列表Excel")
    public AjaxResult export(PmsAveragePersonDetail pmsAveragePersonDetail) {
        List<PmsAveragePersonDetail> list = pmsAveragePersonDetailService.selectPmsAveragePersonDetailList(pmsAveragePersonDetail);
        ExcelUtil<PmsAveragePersonDetail> util = new ExcelUtil<PmsAveragePersonDetail>(PmsAveragePersonDetail.class);
        return util.exportExcel(list, "平均奖科人数明细数据");
    }

    /**
     * 获取平均奖科人数明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('average:averageDetail:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取平均奖科人数明细详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsAveragePersonDetailService.selectPmsAveragePersonDetailById(id));
    }

    /**
     * 新增平均奖科人数明细
     */
    @PreAuthorize("@ss.hasPermi('average:averageDetail:add')")
    @Log(title = "平均奖科人数明细", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增平均奖科人数明细")
    public AjaxResult add(@RequestBody PmsAveragePersonDetail pmsAveragePersonDetail) {
        return toAjax(pmsAveragePersonDetailService.insertPmsAveragePersonDetail(pmsAveragePersonDetail));
    }

    /**
     * 修改平均奖科人数明细
     */
    @PreAuthorize("@ss.hasPermi('average:averageDetail:edit')")
    @Log(title = "平均奖科人数明细", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改平均奖科人数明细")
    public AjaxResult edit(@RequestBody PmsAveragePersonDetail pmsAveragePersonDetail) {
        return toAjax(pmsAveragePersonDetailService.updatePmsAveragePersonDetail(pmsAveragePersonDetail));
    }

    /**
     * 删除平均奖科人数明细
     */
    @PreAuthorize("@ss.hasPermi('average:averageDetail:remove')")
    @Log(title = "平均奖科人数明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除平均奖科人数明细")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsAveragePersonDetailService.deletePmsAveragePersonDetailByIds(ids));
    }

    /**
     * 保存平均奖科人数明细
     */
    @Log(title = "平均奖科人数明细", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    @ApiOperation("保存平均奖科人数明细")
    public AjaxResult save(@RequestBody PmsAveragePersonDetailList pmsAveragePersonDetailList) {
        return toAjax(pmsAveragePersonDetailService.savePmsAveragePersonDetail(pmsAveragePersonDetailList));
    }
}
