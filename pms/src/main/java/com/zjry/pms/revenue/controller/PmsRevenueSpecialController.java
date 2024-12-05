package com.zjry.pms.revenue.controller;

import java.util.List;

import ch.qos.logback.classic.jmx.MBeanUtil;
import com.zjry.pms.encode.service.IHrEncodeRuleService;
import com.zjry.pms.revenue.domain.PmsRevenueSpecialTemp;
import com.zjry.pms.revenue.service.IPmsRevenueSpecialTempService;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
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
import com.zjry.pms.revenue.domain.PmsRevenueSpecial;
import com.zjry.pms.revenue.service.IPmsRevenueSpecialService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 特殊收入项目方案Controller
 *
 * @author maozt
 * @date 2022-06-21
 */
@RestController
@RequestMapping("/revenue/special")
@Api(tags={"【特殊收入项目方案】"})
public class PmsRevenueSpecialController extends BaseController {
    @Autowired
    private IPmsRevenueSpecialService pmsRevenueSpecialService;
    @Autowired
    private IPmsRevenueSpecialTempService pmsRevenueSpecialTempService;
    @Autowired
    private IHrEncodeRuleService hrEncodeRuleService;

    /**
     * 查询特殊收入项目方案列表
     */
    @PreAuthorize("@ss.hasPermi('revenue:special:list')")
    @GetMapping("/list")
    @ApiOperation("查询特殊收入项目方案列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsRevenueSpecial pmsRevenueSpecial) {
        startPage();
        List<PmsRevenueSpecial> list = pmsRevenueSpecialService.selectPmsRevenueSpecialList(pmsRevenueSpecial);
        return getDataTable(list);
    }

    /**
     * 查询特殊收入项目方案列表
     */
    @PreAuthorize("@ss.hasPermi('revenue:special:list')")
    @GetMapping("/refresh")
    @ApiOperation("刷新按钮操作")
    public TableDataInfo refresh(PmsRevenueSpecial pmsRevenueSpecial) {
        List<PmsRevenueSpecial> list = pmsRevenueSpecialService.selectPmsRevenueSpecialList(pmsRevenueSpecial);
        pmsRevenueSpecialTempService.deletePmsRevenueSpecialTempByCompCode(pmsRevenueSpecial.getCompCode());
        for (PmsRevenueSpecial revenueSpecial : list) {
            PmsRevenueSpecialTemp pmsRevenueSpecialTemp = new PmsRevenueSpecialTemp();
            BeanUtils.copyProperties(revenueSpecial,pmsRevenueSpecialTemp);
            pmsRevenueSpecialTemp.setType("0");
            pmsRevenueSpecialTempService.insertPmsRevenueSpecialTemp(pmsRevenueSpecialTemp);
        }
        return getDataTable(list);
    }

    /**
     * 获取编码
     */
    @PreAuthorize("@ss.hasPermi('revenue:special:list')")
    @PostMapping("/getSpecialCode")
    @ApiOperation("获取编码")
    public AjaxResult getSpecialCode(@RequestBody PmsRevenueSpecial pmsRevenueSpecial) {
        PmsRevenueSpecialTemp pmsRevenueSpecialTemp = new PmsRevenueSpecialTemp();
        pmsRevenueSpecialTemp.setCompCode(pmsRevenueSpecial.getCompCode());
        pmsRevenueSpecialTemp.setType("1");
        String specialCode = hrEncodeRuleService.generateEncode("pms_revenue_special", "SPECIAL_CODE");
        pmsRevenueSpecialTemp.setSpecialCode(specialCode);
        pmsRevenueSpecialTempService.insertPmsRevenueSpecialTemp(pmsRevenueSpecialTemp);
        return AjaxResult.success(pmsRevenueSpecialTemp);
    }

    /**
     * 导出特殊收入项目方案列表
     */
    @PreAuthorize("@ss.hasPermi('revenue:special:export')")
    @Log(title = "特殊收入项目方案", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出特殊收入项目方案列表Excel")
    public AjaxResult export(PmsRevenueSpecial pmsRevenueSpecial) {
        List<PmsRevenueSpecial> list = pmsRevenueSpecialService.selectPmsRevenueSpecialList(pmsRevenueSpecial);
        ExcelUtil<PmsRevenueSpecial> util = new ExcelUtil<PmsRevenueSpecial>(PmsRevenueSpecial.class);
        return util.exportExcel(list, "特殊收入项目方案数据");
    }

    /**
     * 获取特殊收入项目方案详细信息
     */
    @PreAuthorize("@ss.hasPermi('revenue:special:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取特殊收入项目方案详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsRevenueSpecialService.selectPmsRevenueSpecialById(id));
    }

    /**
     * 新增特殊收入项目方案
     */
    @PreAuthorize("@ss.hasPermi('revenue:special:add')")
    @Log(title = "特殊收入项目方案", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增特殊收入项目方案")
    public AjaxResult add(@RequestBody PmsRevenueSpecial pmsRevenueSpecial) throws Exception {
        return toAjax(pmsRevenueSpecialService.insertPmsRevenueSpecial(pmsRevenueSpecial));
    }

    /**
     * 修改特殊收入项目方案
     */
    @PreAuthorize("@ss.hasPermi('revenue:special:edit')")
    @Log(title = "特殊收入项目方案", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改特殊收入项目方案")
    public AjaxResult edit(@RequestBody PmsRevenueSpecial pmsRevenueSpecial) throws Exception {
        return toAjax(pmsRevenueSpecialService.updatePmsRevenueSpecial(pmsRevenueSpecial));
    }

    /**
     * 删除特殊收入项目方案
     */
    @PreAuthorize("@ss.hasPermi('revenue:special:remove')")
    @Log(title = "特殊收入项目方案", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除特殊收入项目方案")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsRevenueSpecialService.deletePmsRevenueSpecialByIds(ids));
    }

    /**
     * 获取特殊收入项目方案详细信息
     */
    @PreAuthorize("@ss.hasPermi('revenue:special:edit')")
    @PostMapping(value = "/save")
    @ApiOperation("获取特殊收入项目方案详细信息")
    public AjaxResult save(@RequestBody PmsRevenueSpecial pmsRevenueSpecial) {
        try {
            PmsRevenueSpecialTemp pmsRevenueSpecialTemp = new PmsRevenueSpecialTemp();
            pmsRevenueSpecialTemp.setCompCode(pmsRevenueSpecial.getCompCode());
            pmsRevenueSpecialTemp.setType("3");
            List<PmsRevenueSpecialTemp> pmsRevenueSpecialTemps = pmsRevenueSpecialTempService.selectPmsRevenueSpecialTempList(pmsRevenueSpecialTemp);
            for (PmsRevenueSpecialTemp revenueSpecialTemp : pmsRevenueSpecialTemps) {
                PmsRevenueSpecial revenueSpecial = new PmsRevenueSpecial();
                BeanUtils.copyProperties(revenueSpecialTemp,revenueSpecial);
                pmsRevenueSpecialService.deletePmsRevenueSpecialBySpecialCode(revenueSpecial);
            }
            pmsRevenueSpecialTemp.setType("1");
            pmsRevenueSpecialTemps = pmsRevenueSpecialTempService.selectPmsRevenueSpecialTempList(pmsRevenueSpecialTemp);
            for (PmsRevenueSpecialTemp revenueSpecialTemp : pmsRevenueSpecialTemps) {
                PmsRevenueSpecial revenueSpecial = new PmsRevenueSpecial();
                BeanUtils.copyProperties(revenueSpecialTemp,revenueSpecial);
                pmsRevenueSpecialService.insertPmsRevenueSpecial(revenueSpecial);
            }
            pmsRevenueSpecialTemp.setType("2");
            pmsRevenueSpecialTemps = pmsRevenueSpecialTempService.selectPmsRevenueSpecialTempList(pmsRevenueSpecialTemp);
            for (PmsRevenueSpecialTemp revenueSpecialTemp : pmsRevenueSpecialTemps) {
                PmsRevenueSpecial revenueSpecial = new PmsRevenueSpecial();
                BeanUtils.copyProperties(revenueSpecialTemp,revenueSpecial);
                pmsRevenueSpecialService.updatePmsRevenueSpecialBySpecialCode(revenueSpecial);
            }
            return AjaxResult.success();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }
}
