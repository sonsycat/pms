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
import com.zjry.pms.project.domain.CostProjectNumSql;
import com.zjry.pms.project.service.ICostProjectNumSqlService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 成本sqlController
 *
 * @author mk
 * @date 2022-05-18
 */
@RestController
@RequestMapping("/project/sql")
@Api(tags={"【成本sql】"})
public class CostProjectNumSqlController extends BaseController {
    @Autowired
    private ICostProjectNumSqlService costProjectNumSqlService;

    /**
     * 查询成本sql列表
     */
    @GetMapping("/list")
    @ApiOperation("查询成本sql列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(CostProjectNumSql costProjectNumSql) {
        startPage();
        List<CostProjectNumSql> list = costProjectNumSqlService.selectCostProjectNumSqlList(costProjectNumSql);
        return getDataTable(list);
    }

    /**
     * 导出成本sql列表
     */
    @PreAuthorize("@ss.hasPermi('project:sql:export')")
    @Log(title = "成本sql", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出成本sql列表Excel")
    public AjaxResult export(CostProjectNumSql costProjectNumSql) {
        List<CostProjectNumSql> list = costProjectNumSqlService.selectCostProjectNumSqlList(costProjectNumSql);
        ExcelUtil<CostProjectNumSql> util = new ExcelUtil<CostProjectNumSql>(CostProjectNumSql.class);
        return util.exportExcel(list, "成本sql数据");
    }

    /**
     * 获取成本sql详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:sql:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取成本sql详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(costProjectNumSqlService.selectCostProjectNumSqlById(id));
    }




    /**
     * 新增成本sql
     */
    @PreAuthorize("@ss.hasPermi('project:sql:add')")
    @Log(title = "成本sql", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增成本sql")
    public AjaxResult add(@RequestBody CostProjectNumSql costProjectNumSql) {
        return toAjax(costProjectNumSqlService.insertCostProjectNumSql(costProjectNumSql));
    }

    /**
     * 修改成本sql
     */
    @PreAuthorize("@ss.hasPermi('project:sql:edit')")
    @Log(title = "成本sql", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改成本sql")
    public AjaxResult edit(@RequestBody CostProjectNumSql costProjectNumSql) {
        return toAjax(costProjectNumSqlService.updateCostProjectNumSql(costProjectNumSql));
    }

    /**
     * 删除成本sql
     */
    @PreAuthorize("@ss.hasPermi('project:sql:remove')")
    @Log(title = "成本sql", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除成本sql")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(costProjectNumSqlService.deleteCostProjectNumSqlByIds(ids));
    }
}
