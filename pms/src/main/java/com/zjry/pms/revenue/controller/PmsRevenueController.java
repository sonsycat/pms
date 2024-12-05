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
import com.zjry.pms.revenue.domain.PmsRevenue;
import com.zjry.pms.revenue.service.IPmsRevenueService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 收入核算方案Controller
 *
 * @author mk
 * @date 2022-06-20
 */
@RestController
@RequestMapping("/revenue/revenue")
@Api(tags={"【收入核算方案】"})
public class PmsRevenueController extends BaseController {
    @Autowired
    private IPmsRevenueService pmsRevenueService;

    /**
     * 查询收入核算方案列表
     */
    @PreAuthorize("@ss.hasPermi('revenue:revenue:list')")
    @GetMapping("/list")
    @ApiOperation("查询收入核算方案列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsRevenue pmsRevenue) {
        startPage();
        List<PmsRevenue> list = pmsRevenueService.selectPmsRevenueList(pmsRevenue);
        return getDataTable(list);
    }

    /**
     * 导出收入核算方案列表
     */
    @PreAuthorize("@ss.hasPermi('revenue:revenue:export')")
    @Log(title = "收入核算方案", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出收入核算方案列表Excel")
    public AjaxResult export(PmsRevenue pmsRevenue) {
        List<PmsRevenue> list = pmsRevenueService.selectPmsRevenueList(pmsRevenue);
        ExcelUtil<PmsRevenue> util = new ExcelUtil<PmsRevenue>(PmsRevenue.class);
        return util.exportExcel(list, "收入核算方案数据");
    }

    /**
     * 获取收入核算方案详细信息
     */
    @PreAuthorize("@ss.hasPermi('revenue:revenue:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取收入核算方案详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsRevenueService.selectPmsRevenueById(id));
    }

    /**
     * 新增收入核算方案
     */
    @PreAuthorize("@ss.hasPermi('revenue:revenue:add')")
    @Log(title = "收入核算方案", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增收入核算方案")
    public AjaxResult add(@RequestBody PmsRevenue pmsRevenue) {
        PmsRevenue pr = new PmsRevenue();
        pr.setCompCode(pmsRevenue.getCompCode());
        pr.setRName(pmsRevenue.getRevenueName());
        List<PmsRevenue> list = pmsRevenueService.selectPmsRevenueList(pr);
        if(list.size()>0){
            return error("收入项目名称不能重复");
        }
        // 2023.2.7 新增判断：被收入核算方案选择过的HIS收入类别无法被其他方案选择
        PmsRevenue pmsRevenueHis = new PmsRevenue();
        pmsRevenueHis.setCompCode(pmsRevenue.getCompCode());
        pmsRevenueHis.setHisRevenumTypede(pmsRevenue.getHisRevenumTypede());
        List<PmsRevenue> listHis = pmsRevenueService.selectPmsRevenueList(pmsRevenueHis);
        if (listHis.size() > 0){
            return error("名称为：" + listHis.get(0).getHisRevenumTypedeName() + " 的HIS收入类别已被其他方案使用！");
        }

        return toAjax(pmsRevenueService.insertPmsRevenue(pmsRevenue));
    }

    /**
     * 修改收入核算方案
     */
    @PreAuthorize("@ss.hasPermi('revenue:revenue:edit')")
    @Log(title = "收入核算方案", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改收入核算方案")
    public AjaxResult edit(@RequestBody PmsRevenue pmsRevenue) {
        PmsRevenue pr = new PmsRevenue();
        pr.setId(pmsRevenue.getId());
        pr.setCompCode(pmsRevenue.getCompCode());
        pr.setRName(pmsRevenue.getRevenueName());
        List<PmsRevenue> list = pmsRevenueService.selectPmsRevenueList(pr);
        if(list.size()>0){
            return error("收入项目名称不能重复");
        }

        return toAjax(pmsRevenueService.updatePmsRevenue(pmsRevenue));
    }

    /**
     * 删除收入核算方案
     */
    @PreAuthorize("@ss.hasPermi('revenue:revenue:remove')")
    @Log(title = "收入核算方案", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除收入核算方案")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsRevenueService.deletePmsRevenueByIds(ids));
    }
}
