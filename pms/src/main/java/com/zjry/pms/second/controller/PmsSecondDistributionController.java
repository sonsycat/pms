package com.zjry.pms.second.controller;

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
import com.zjry.pms.second.domain.PmsSecondDistribution;
import com.zjry.pms.second.service.IPmsSecondDistributionService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 二次分配指标集设置Controller
 *
 * @author maozt
 * @date 2022-09-26
 */
@RestController
@RequestMapping("/second/distribution")
@Api(tags={"【二次分配指标集设置】"})
public class PmsSecondDistributionController extends BaseController {
    @Autowired
    private IPmsSecondDistributionService pmsSecondDistributionService;

    /**
     * 查询二次分配指标集设置列表
     */
    @PreAuthorize("@ss.hasPermi('second:distribution:list')")
    @GetMapping("/list")
    @ApiOperation("查询二次分配指标集设置列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsSecondDistribution pmsSecondDistribution) {
        startPage();
        List<PmsSecondDistribution> list = pmsSecondDistributionService.selectPmsSecondDistributionList(pmsSecondDistribution);
        return getDataTable(list);
    }

    /**
     * 导出二次分配指标集设置列表
     */
    @PreAuthorize("@ss.hasPermi('second:distribution:export')")
    @Log(title = "二次分配指标集设置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出二次分配指标集设置列表Excel")
    public AjaxResult export(PmsSecondDistribution pmsSecondDistribution) {
        List<PmsSecondDistribution> list = pmsSecondDistributionService.selectPmsSecondDistributionList(pmsSecondDistribution);
        ExcelUtil<PmsSecondDistribution> util = new ExcelUtil<PmsSecondDistribution>(PmsSecondDistribution.class);
        return util.exportExcel(list, "二次分配指标集设置数据");
    }

    /**
     * 获取二次分配指标集设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('second:distribution:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取二次分配指标集设置详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsSecondDistributionService.selectPmsSecondDistributionById(id));
    }

    /**
     * 新增二次分配指标集设置
     */
    @PreAuthorize("@ss.hasPermi('second:distribution:add')")
    @Log(title = "二次分配指标集设置", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增二次分配指标集设置")
    public AjaxResult add(@RequestBody PmsSecondDistribution pmsSecondDistribution) {
        try {
            return toAjax(pmsSecondDistributionService.insertPmsSecondDistribution(pmsSecondDistribution));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 修改二次分配指标集设置
     */
    @PreAuthorize("@ss.hasPermi('second:distribution:edit')")
    @Log(title = "二次分配指标集设置", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改二次分配指标集设置")
    public AjaxResult edit(@RequestBody PmsSecondDistribution pmsSecondDistribution) {
        try {
            return toAjax(pmsSecondDistributionService.updatePmsSecondDistribution(pmsSecondDistribution));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 删除二次分配指标集设置
     */
    @PreAuthorize("@ss.hasPermi('second:distribution:remove')")
    @Log(title = "二次分配指标集设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除二次分配指标集设置")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsSecondDistributionService.deletePmsSecondDistributionByIds(ids));
    }
}
