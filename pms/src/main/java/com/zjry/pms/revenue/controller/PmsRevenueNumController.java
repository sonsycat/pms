package com.zjry.pms.revenue.controller;

import java.util.List;

import com.zjry.common.utils.StringUtils;
import com.zjry.pms.revenue.domain.PmsRevenueNumSql;
import com.zjry.pms.revenue.service.IPmsRevenueNumSqlService;
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
import com.zjry.pms.revenue.domain.PmsRevenueNum;
import com.zjry.pms.revenue.service.IPmsRevenueNumService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 收入录入Controller
 *
 * @author mk
 * @date 2022-06-22
 */
@RestController
@RequestMapping("/revenue/revenuenum")
@Api(tags={"【收入录入】"})
public class PmsRevenueNumController extends BaseController {
    @Autowired
    private IPmsRevenueNumService pmsRevenueNumService;
    @Autowired
    private IPmsRevenueNumSqlService pmsRevenueNumSqlService;


    /**
     * 查询收入录入列表
     */
    @PreAuthorize("@ss.hasPermi('revenue:revenuenum:list')")
    @GetMapping("/list")
    @ApiOperation("查询收入录入列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsRevenueNum pmsRevenueNum) {
        startPage();
        List<PmsRevenueNum> list = pmsRevenueNumService.selectPmsRevenueNumList(pmsRevenueNum);
        return getDataTable(list);
    }

    /**
     * 导出收入录入列表
     */
    @PreAuthorize("@ss.hasPermi('revenue:revenuenum:export')")
    @Log(title = "收入录入", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出收入录入列表Excel")
    public AjaxResult export(PmsRevenueNum pmsRevenueNum) {
        List<PmsRevenueNum> list = pmsRevenueNumService.selectPmsRevenueNumList(pmsRevenueNum);
        ExcelUtil<PmsRevenueNum> util = new ExcelUtil<PmsRevenueNum>(PmsRevenueNum.class);
        return util.exportExcel(list, "收入录入数据");
    }

    /**
     * 获取收入录入详细信息
     */
    @PreAuthorize("@ss.hasPermi('revenue:revenuenum:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取收入录入详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsRevenueNumService.selectPmsRevenueNumById(id));
    }

    /**
     * 新增收入录入
     */
    @PreAuthorize("@ss.hasPermi('revenue:revenuenum:add')")
    @Log(title = "收入录入", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增收入录入")
    public AjaxResult add(@RequestBody PmsRevenueNum pmsRevenueNum) {
        return toAjax(pmsRevenueNumService.insertPmsRevenueNum(pmsRevenueNum));
    }

    /**
     * 修改收入录入
     */
    @PreAuthorize("@ss.hasPermi('revenue:revenuenum:edit')")
    @Log(title = "收入录入", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改收入录入")
    public AjaxResult edit(@RequestBody PmsRevenueNum pmsRevenueNum) {
        return toAjax(pmsRevenueNumService.updatePmsRevenueNum(pmsRevenueNum));
    }

    /**
     * 新增成本sql
     */
    @Log(title = "收入sql", businessType = BusinessType.INSERT)
    @PostMapping("/inertBySql")
    @ApiOperation("新增成本sql")
    public AjaxResult inertBySql(@RequestBody PmsRevenueNumSql pmsRevenueNumSql) {
        try {
            String msg = pmsRevenueNumSqlService.inertBySql(pmsRevenueNumSql);
            if(StringUtils.isNotEmpty(msg)){
                pmsRevenueNumSqlService.deleteByFlag();
                return error(msg);
            }
        } catch (Exception e) {
            pmsRevenueNumSqlService.deleteByFlag();
            return error(e.getMessage());
        }
        return success();
    }

    /**
     * 删除收入录入
     */
    @PreAuthorize("@ss.hasPermi('revenue:revenuenum:remove')")
    @Log(title = "收入录入", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除收入录入")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsRevenueNumService.deletePmsRevenueNumByIds(ids));
    }
}
