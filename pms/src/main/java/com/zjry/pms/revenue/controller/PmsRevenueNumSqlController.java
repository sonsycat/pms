package com.zjry.pms.revenue.controller;

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
import com.zjry.pms.revenue.domain.PmsRevenueNumSql;
import com.zjry.pms.revenue.service.IPmsRevenueNumSqlService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 收入sqlController
 *
 * @author mk
 * @date 2022-06-23
 */
@RestController
@RequestMapping("/revenue/revenuesql")
@Api(tags={"【收入sql】"})
public class PmsRevenueNumSqlController extends BaseController {
    @Autowired
    private IPmsRevenueNumSqlService pmsRevenueNumSqlService;

    /**
     * 查询收入sql列表
     */
    @GetMapping("/list")
    @ApiOperation("查询收入sql列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsRevenueNumSql pmsRevenueNumSql) {
        startPage();
        List<PmsRevenueNumSql> list = pmsRevenueNumSqlService.selectPmsRevenueNumSqlList(pmsRevenueNumSql);
        return getDataTable(list);
    }

    /**
     * 导出收入sql列表
     */
    @PreAuthorize("@ss.hasPermi('revenue:revenuesql:export')")
    @Log(title = "收入sql", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出收入sql列表Excel")
    public AjaxResult export(PmsRevenueNumSql pmsRevenueNumSql) {
        List<PmsRevenueNumSql> list = pmsRevenueNumSqlService.selectPmsRevenueNumSqlList(pmsRevenueNumSql);
        ExcelUtil<PmsRevenueNumSql> util = new ExcelUtil<PmsRevenueNumSql>(PmsRevenueNumSql.class);
        return util.exportExcel(list, "收入sql数据");
    }

    /**
     * 获取收入sql详细信息
     */
    @PreAuthorize("@ss.hasPermi('revenue:revenuesql:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取收入sql详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsRevenueNumSqlService.selectPmsRevenueNumSqlById(id));
    }

    /**
     * 新增收入sql
     */
    @PreAuthorize("@ss.hasPermi('revenue:revenuesql:add')")
    @Log(title = "收入sql", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增收入sql")
    public AjaxResult add(@RequestBody PmsRevenueNumSql pmsRevenueNumSql) {
        return toAjax(pmsRevenueNumSqlService.insertPmsRevenueNumSql(pmsRevenueNumSql));
    }

    /**
     * 修改收入sql
     */
    @PreAuthorize("@ss.hasPermi('revenue:revenuesql:edit')")
    @Log(title = "收入sql", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改收入sql")
    public AjaxResult edit(@RequestBody PmsRevenueNumSql pmsRevenueNumSql) {
        return toAjax(pmsRevenueNumSqlService.updatePmsRevenueNumSql(pmsRevenueNumSql));
    }

    /**
     * 删除收入sql
     */
    @PreAuthorize("@ss.hasPermi('revenue:revenuesql:remove')")
    @Log(title = "收入sql", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除收入sql")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsRevenueNumSqlService.deletePmsRevenueNumSqlByIds(ids));
    }
}
