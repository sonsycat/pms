package com.zjry.pms.subsidy.controller;

import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.pms.subsidy.domain.PmsSubsidyAdjustBenefit;
import com.zjry.pms.subsidy.domain.PmsSubsidyFund;
import com.zjry.pms.subsidy.service.IPmsSubsidyAdjustBenefitService;
import com.zjry.pms.subsidy.service.IPmsSubsidyFundService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 科基金Controller
 *
 * @author maozt
 * @date 2022-08-17
 */
@RestController
@RequestMapping("/subsidy/fund")
@Api(tags={"【科基金】"})
public class PmsSubsidyFundController extends BaseController {
    @Autowired
    private IPmsSubsidyFundService pmsSubsidyFundService;
    /**
     * 获取科基金详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取科基金详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsSubsidyFundService.selectPmsSubsidyFundById(id));
    }
    /**
     * 查询科基金列表
     */
    @PreAuthorize("@ss.hasPermi('subsidy:fund:list')")
    @GetMapping("/list")
    @ApiOperation("查询科基金列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsSubsidyFund pmsSubsidyFund) {
        startPage();
        List<PmsSubsidyFund> list = pmsSubsidyFundService.selectPmsSubsidyFundList(pmsSubsidyFund);
        return getDataTable(list);
    }


    /**
     * 新增科基金
     */
    @PreAuthorize("@ss.hasPermi('subsidy:fund:add')")
    @Log(title = "科基金", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增科基金")
    public AjaxResult add(@RequestBody PmsSubsidyFund pmsSubsidyFund) {
        return toAjax(pmsSubsidyFundService.insertPmsSubsidyFund(pmsSubsidyFund));
    }

    /**
     * 修改科基金
     */
    @PreAuthorize("@ss.hasPermi('subsidy:fund:edit')")
    @Log(title = "科基金", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改科基金")
    public AjaxResult edit(@RequestBody PmsSubsidyFund pmsSubsidyFund) {
        return toAjax(pmsSubsidyFundService.updatePmsSubsidyFund(pmsSubsidyFund));
    }
    /**
     * 删除科基金
     */
    @PreAuthorize("@ss.hasPermi('subsidy:fund:remove')")
    @Log(title = "科基金", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除科基金")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsSubsidyFundService.deletePmsSubsidyFund(ids));
    }
    @PostMapping("/submitBalance")
    @ApiOperation("提交科基金列表")
    public AjaxResult submitBalance(@RequestBody PmsSubsidyFund pmsSubsidyFund) {
        pmsSubsidyFundService.submitBalance(pmsSubsidyFund);
        return AjaxResult.success();
    }

}
