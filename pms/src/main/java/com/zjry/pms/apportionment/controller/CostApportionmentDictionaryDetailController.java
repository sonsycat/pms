package com.zjry.pms.apportionment.controller;


import io.swagger.annotations.*;

import java.util.List;

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
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.pms.apportionment.domain.CostApportionmentDictionaryDetail;
import com.zjry.pms.apportionment.service.ICostApportionmentDictionaryDetailService;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 比例分摊字典明细Controller
 *
 * @author yzh
 * @date 2022-05-12
 */
@RestController
@RequestMapping("/dictionary/dictionaryDetail")
@Api(tags={"【比例分摊字典明细】"})
public class CostApportionmentDictionaryDetailController extends BaseController {
    @Autowired
    private ICostApportionmentDictionaryDetailService costApportionmentDictionaryDetailService;

    /**
     * 查询比例分摊字典明细列表
     */
    @GetMapping("/list")
    @ApiOperation("查询比例分摊字典明细列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(CostApportionmentDictionaryDetail costApportionmentDictionaryDetail) {
        startPage();
        List<CostApportionmentDictionaryDetail> list = costApportionmentDictionaryDetailService.selectCostApportionmentDictionaryDetailList(costApportionmentDictionaryDetail);
        return getDataTable(list);
    }

    /**
     * 导出比例分摊字典明细列表
     */
    @Log(title = "比例分摊字典明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出比例分摊字典明细列表Excel")
    public AjaxResult export(CostApportionmentDictionaryDetail costApportionmentDictionaryDetail) {
        List<CostApportionmentDictionaryDetail> list = costApportionmentDictionaryDetailService.selectCostApportionmentDictionaryDetailList(costApportionmentDictionaryDetail);
        ExcelUtil<CostApportionmentDictionaryDetail> util = new ExcelUtil<CostApportionmentDictionaryDetail>(CostApportionmentDictionaryDetail.class);
        return util.exportExcel(list, "比例分摊字典明细数据");
    }

    /**
     * 获取比例分摊字典明细详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取比例分摊字典明细详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(costApportionmentDictionaryDetailService.selectCostApportionmentDictionaryDetailById(id));
    }

    /**
     * 新增比例分摊字典明细
     */
    @Log(title = "比例分摊字典明细", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增比例分摊字典明细")
    public AjaxResult add(@RequestBody CostApportionmentDictionaryDetail costApportionmentDictionaryDetail) {
        return toAjax(costApportionmentDictionaryDetailService.insertCostApportionmentDictionaryDetail(costApportionmentDictionaryDetail));
    }

    /**
     * 修改比例分摊字典明细
     */
    @Log(title = "比例分摊字典明细", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改比例分摊字典明细")
    public AjaxResult edit(@RequestBody CostApportionmentDictionaryDetail costApportionmentDictionaryDetail) {
        return toAjax(costApportionmentDictionaryDetailService.updateCostApportionmentDictionaryDetail(costApportionmentDictionaryDetail));
    }

    /**
     * 删除比例分摊字典明细
     */
    @Log(title = "比例分摊字典明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除比例分摊字典明细")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(costApportionmentDictionaryDetailService.deleteCostApportionmentDictionaryDetailByIds(ids));
    }
}
