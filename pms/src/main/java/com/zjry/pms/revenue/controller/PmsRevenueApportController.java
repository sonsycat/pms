package com.zjry.pms.revenue.controller;

import java.util.ArrayList;
import java.util.List;

import com.zjry.common.utils.StringUtils;
import com.zjry.pms.common.Constants;
import com.zjry.pms.revenue.domain.PmsApportInfo;
import com.zjry.pms.revenue.domain.PmsRevenue;
import com.zjry.pms.revenue.domain.PmsRevenueNum;
import com.zjry.pms.revenue.service.IPmsRevenueNumService;
import com.zjry.pms.revenue.service.IPmsRevenueService;
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
import com.zjry.pms.revenue.domain.PmsRevenueApport;
import com.zjry.pms.revenue.service.IPmsRevenueApportService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 收入生成Controller
 *
 * @author mk
 * @date 2022-06-28
 */
@RestController
@RequestMapping("/revenue/apport")
@Api(tags={"【收入生成】"})
public class PmsRevenueApportController extends BaseController {
    @Autowired
    private IPmsRevenueApportService pmsRevenueApportService;
    @Autowired
    private IPmsRevenueService pmsRevenueService;
    @Autowired
    private IPmsRevenueNumService pmsRevenueNumService;

    /**
     * 查询收入生成列表
     */
    @PreAuthorize("@ss.hasPermi('revenue:apport:list')")
    @GetMapping("/list")
    @ApiOperation("查询收入生成列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsRevenueApport pmsRevenueApport) {
        startPage();
        List<PmsRevenueApport> list = pmsRevenueApportService.selectPmsRevenueApportList(pmsRevenueApport);
        return getDataTable(list);
    }




    /**
     * 分解前数据
     */
    @PreAuthorize("@ss.hasPermi('revenue:apport:list')")
    @GetMapping("/beforeBreak")
    @ApiOperation("分解前数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo beforeBreak(PmsRevenueApport pmsRevenueApport) {
        List<PmsApportInfo> pai = pmsRevenueApportService.beforeBreak(pmsRevenueApport);
        return getDataTable(pai);
    }


    /**
     * 分解后数据
     */
    @PreAuthorize("@ss.hasPermi('revenue:apport:list')")
    @GetMapping("/afterBreak")
    @ApiOperation("分解后数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo afterBreak(PmsRevenueApport pmsRevenueApport) {
        List<PmsApportInfo> pai = pmsRevenueApportService.afterBreak(pmsRevenueApport);
        return getDataTable(pai);
    }


    /**
     * 导出收入生成列表
     */
    @PreAuthorize("@ss.hasPermi('revenue:apport:export')")
    @Log(title = "收入生成", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出收入生成列表Excel")
    public AjaxResult export(PmsRevenueApport pmsRevenueApport) {
        List<PmsRevenueApport> list = pmsRevenueApportService.selectPmsRevenueApportList(pmsRevenueApport);
        ExcelUtil<PmsRevenueApport> util = new ExcelUtil<PmsRevenueApport>(PmsRevenueApport.class);
        return util.exportExcel(list, "收入生成数据");
    }

    /**
     * 获取收入生成详细信息
     */
    @PreAuthorize("@ss.hasPermi('revenue:apport:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取收入生成详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsRevenueApportService.selectPmsRevenueApportById(id));
    }

    /**
     * 新增收入生成
     */
    @PreAuthorize("@ss.hasPermi('revenue:apport:add')")
    @Log(title = "收入生成", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增收入生成")
    public AjaxResult add(@RequestBody PmsRevenueApport pmsRevenueApport) {
        return toAjax(pmsRevenueApportService.insertPmsRevenueApport(pmsRevenueApport));
    }

    /**
     * 修改收入生成
     */
    @PreAuthorize("@ss.hasPermi('revenue:apport:edit')")
    @Log(title = "收入生成", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改收入生成")
    public AjaxResult edit(@RequestBody PmsRevenueApport pmsRevenueApport) {
        return toAjax(pmsRevenueApportService.updatePmsRevenueApport(pmsRevenueApport));
    }

    /**
     * 查询收入生成列表
     */
    @PreAuthorize("@ss.hasPermi('revenue:apport:list')")
    @GetMapping("/verification1")
    @ApiOperation("验证是否有当前月的数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public AjaxResult verification1(PmsRevenueApport pmsRevenueApport) {
        PmsRevenueApport pra = new PmsRevenueApport();
        pra.setCompCode(pmsRevenueApport.getCompCode());
        pra.setOTime(pmsRevenueApport.getOccureTime());
        List<PmsRevenueApport> list = pmsRevenueApportService.selectPmsRevenueApportList(pra);
        if(list.size()>0){
            return error();
        }
        return success();
    }



    /**
     * 查询收入生成列表
     */
    @PreAuthorize("@ss.hasPermi('revenue:apport:list')")
    @GetMapping("/verification2")
    @ApiOperation("验证当前是否有对应方案")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public AjaxResult verification2(PmsRevenueApport pmsRevenueApport) {
        PmsRevenueNum prn = new PmsRevenueNum();
        prn.setCompCode(pmsRevenueApport.getCompCode());
        prn.setOccureT(pmsRevenueApport.getOccureTime());
        List<PmsRevenueNum> list = pmsRevenueNumService.selectPmsForApp(prn);
        for(PmsRevenueNum item :list){
            PmsRevenue pra = new PmsRevenue();
            pra.setCompCode(pmsRevenueApport.getCompCode());
            pra.setRevenueCode(item.getRevenueCode());
            List<PmsRevenue> list1 = pmsRevenueService.selectPmsRevenueList(pra);
            if(list1 == null || list1.size()==0){
                return error();
            }
        }
       return success();
    }


    /**
     * 门诊正常收入分解
     */
    @PreAuthorize("@ss.hasPermi('revenue:apport:list')")
    @GetMapping("/generate1")
    @ApiOperation("门诊正常收入分解")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public AjaxResult generate1(PmsRevenueApport pmsRevenueApport) {
        PmsRevenueNum prn = new PmsRevenueNum();
        prn.setCompCode(pmsRevenueApport.getCompCode());
        prn.setOccureT(pmsRevenueApport.getOccureTime());
        //正常收入1 附加收入2
        prn.setRevenueKind(Constants.CARD_STATE_ONE);
        prn.setRevenueType(Constants.CARD_STATE_ONE);
        List<PmsRevenueNum> list = pmsRevenueNumService.selectPmsRevenueNumList(prn);
        try {
            pmsRevenueApportService.generate1(list,Constants.CARD_STATE_ONE,Constants.CARD_STATE_ONE);
        } catch (Exception e) {
            //将错误数据删除
            pmsRevenueApportService.deleteByState();
            return error();
        }
        return success();
    }


    /**
     * 门诊附加收入分解
     */
    @PreAuthorize("@ss.hasPermi('revenue:apport:list')")
    @GetMapping("/generate2")
    @ApiOperation("门诊附加收入分解")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public AjaxResult generate2(PmsRevenueApport pmsRevenueApport) {
        PmsRevenueNum prn = new PmsRevenueNum();
        prn.setCompCode(pmsRevenueApport.getCompCode());
        prn.setOccureT(pmsRevenueApport.getOccureTime());
        //正常收入1 附加收入2
        prn.setRevenueKind(Constants.CARD_STATE_TWO);
        prn.setRevenueType(Constants.CARD_STATE_ONE);
        List<PmsRevenueNum> list = pmsRevenueNumService.selectPmsRevenueNumList(prn);
        try {
            pmsRevenueApportService.generate1(list,Constants.CARD_STATE_ONE,Constants.CARD_STATE_TWO);
        } catch (Exception e) {
            //将错误数据删除
            pmsRevenueApportService.deleteByState();
            return error();
        }
        return success();
    }

    /**
     * 住院正常收入分解
     */
    @PreAuthorize("@ss.hasPermi('revenue:apport:list')")
    @GetMapping("/generate3")
    @ApiOperation("住院正常收入分解")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public AjaxResult generate3(PmsRevenueApport pmsRevenueApport) {
        PmsRevenueNum prn = new PmsRevenueNum();
        prn.setCompCode(pmsRevenueApport.getCompCode());
        prn.setOccureT(pmsRevenueApport.getOccureTime());
        //正常收入1 附加收入2
        prn.setRevenueKind(Constants.CARD_STATE_ONE);
        prn.setRevenueType(Constants.CARD_STATE_TWO);
        List<PmsRevenueNum> list = pmsRevenueNumService.selectPmsRevenueNumList(prn);
        try {
            pmsRevenueApportService.generate1(list,Constants.CARD_STATE_TWO,Constants.CARD_STATE_ONE);
        } catch (Exception e) {
            //将错误数据删除
            pmsRevenueApportService.deleteByState();
            return error();
        }
        return success();
    }


    /**
     * 住院附加收入分解
     */
    @PreAuthorize("@ss.hasPermi('revenue:apport:list')")
    @GetMapping("/generate4")
    @ApiOperation("住院附加收入分解")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public AjaxResult generate4(PmsRevenueApport pmsRevenueApport) {
        PmsRevenueNum prn = new PmsRevenueNum();
        prn.setCompCode(pmsRevenueApport.getCompCode());
        prn.setOccureT(pmsRevenueApport.getOccureTime());
        //正常收入1 附加收入2
        prn.setRevenueKind(Constants.CARD_STATE_TWO);
        prn.setRevenueType(Constants.CARD_STATE_TWO);
        List<PmsRevenueNum> list = pmsRevenueNumService.selectPmsRevenueNumList(prn);
        try {
            pmsRevenueApportService.generate1(list,Constants.CARD_STATE_TWO,Constants.CARD_STATE_TWO);
            pmsRevenueApportService.updateByState();
        } catch (Exception e) {
            //将错误数据删除
            pmsRevenueApportService.deleteByState();
            return error();
        }
        return success();
    }






    /**
     * 删除收入生成
     */
    @PreAuthorize("@ss.hasPermi('revenue:apport:remove')")
    @Log(title = "收入生成", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除收入生成")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsRevenueApportService.deletePmsRevenueApportByIds(ids));
    }
}
