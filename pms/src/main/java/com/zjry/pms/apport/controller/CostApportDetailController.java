package com.zjry.pms.apport.controller;

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
import com.zjry.pms.apport.domain.CostApportDetail;
import com.zjry.pms.apport.service.ICostApportDetailService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 分摊明细Controller
 *
 * @author yzh
 * @date 2022-05-17
 */
@RestController
@RequestMapping("/apport/apportDetail")
@Api(tags={"【分摊明细】"})
public class CostApportDetailController extends BaseController {
    @Autowired
    private ICostApportDetailService costApportDetailService;

    /**
     * 查询分摊明细列表
     */
    @PreAuthorize("@ss.hasPermi('apport:apportDetail:list')")
    @GetMapping("/list")
    @ApiOperation("查询分摊明细列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(CostApportDetail costApportDetail) {
        startPage();
        List<CostApportDetail> list = costApportDetailService.selectCostApportDetailList(costApportDetail);
        return getDataTable(list);
    }

    /**
     * 导出分摊明细列表
     */
    @PreAuthorize("@ss.hasPermi('apport:apportDetail:export')")
    @Log(title = "分摊明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出分摊明细列表Excel")
    public AjaxResult export(CostApportDetail costApportDetail) {
        List<CostApportDetail> list = costApportDetailService.selectCostApportDetailList(costApportDetail);
        ExcelUtil<CostApportDetail> util = new ExcelUtil<CostApportDetail>(CostApportDetail.class);
        return util.exportExcel(list, "分摊明细数据");
    }

    /**
     * 获取分摊明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('apport:apportDetail:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取分摊明细详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(costApportDetailService.selectCostApportDetailById(id));
    }

    /**
     * 新增分摊明细
     */
    @PreAuthorize("@ss.hasPermi('apport:apportDetail:add')")
    @Log(title = "分摊明细", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增分摊明细")
    public AjaxResult add(@RequestBody CostApportDetail costApportDetail) {
        return toAjax(costApportDetailService.insertCostApportDetail(costApportDetail));
    }

    /**
     * 修改分摊明细
     */
    @PreAuthorize("@ss.hasPermi('apport:apportDetail:edit')")
    @Log(title = "分摊明细", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改分摊明细")
    public AjaxResult edit(@RequestBody CostApportDetail costApportDetail) {
        return toAjax(costApportDetailService.updateCostApportDetail(costApportDetail));
    }

    /**
     * 删除分摊明细
     */
    @PreAuthorize("@ss.hasPermi('apport:apportDetail:remove')")
    @Log(title = "分摊明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除分摊明细")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(costApportDetailService.deleteCostApportDetailByIds(ids));
    }
}
