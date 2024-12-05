package com.zjry.pms.bonus.controller;

import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.StringUtils;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.pms.bonus.domain.*;
import com.zjry.pms.bonus.domain.equivalent.*;
import com.zjry.pms.bonus.service.IPmsBonusStatementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 基础当量Controller
 *
 * @author yzh
 * @date 2022-05-17
 */
@RestController
@RequestMapping("/bonus/statement")
@Api(tags={"【超劳补贴】"})
public class PmsBonusStatementController extends BaseController {
    @Autowired
    private IPmsBonusStatementService pmsBonusStatementService;


    @GetMapping("/equivalentType")
    @ApiOperation("查询基础当量部门列表")
    public AjaxResult equivalentTypelist() {
        List<PmsStatementEquivalentType> data = pmsBonusStatementService.selectEquivalentTypelist();
        if (StringUtils.isNull(data)) {
            data = new ArrayList<PmsStatementEquivalentType>();
        }
        return AjaxResult.success(data);
    }
    @GetMapping("/achievementType")
    @ApiOperation("查询基础当量部门列表")
    public AjaxResult achievementTypelist() {
        List<PmsStatementAchievementType> data = pmsBonusStatementService.selectAchievementTypeList();
        if (StringUtils.isNull(data)) {
            data = new ArrayList<PmsStatementAchievementType>();
        }
        return AjaxResult.success(data);
    }
    @GetMapping("/labourType")
    @ApiOperation("查询基础当量部门列表")
    public AjaxResult labourTypelist() {
        List<PmsStatementLabourType> data = pmsBonusStatementService.selectlabourTypelistList();
        if (StringUtils.isNull(data)) {
            data = new ArrayList<PmsStatementLabourType>();
        }
        return AjaxResult.success(data);
    }
    @PreAuthorize("@ss.hasPermi('bonus:sEquivalent:list')")
    @GetMapping("/equivalentList")
    @ApiOperation("查询当量列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo equivalentList(PmsStatementEquivalent pmsStatementEquivalent) {
        startPage();
        List<PmsStatementEquivalent> list = pmsBonusStatementService.selectEquivalentList(pmsStatementEquivalent);
        return getDataTable(list);
    }
    @PreAuthorize("@ss.hasPermi('bonus:sEquivalent:exportDetail')")
    @Log(title = "当量查询明细", businessType = BusinessType.EXPORT)
    @GetMapping("/exportDetail")
    @ApiOperation("导出当量明细列表Excel")
    public AjaxResult exportDetail(PmsStatementEquivalent pmsStatementEquivalent) {
        if("1".equals(pmsStatementEquivalent.getEquivalentTypeCode())){
            List<PmsStatementEquivalentOne> list = pmsBonusStatementService.selectEquivalentListByThread(pmsStatementEquivalent);
            ExcelUtil<PmsStatementEquivalentOne> util = new ExcelUtil<PmsStatementEquivalentOne>(PmsStatementEquivalentOne.class);
            return util.exportExcel(list, "门诊临床当量查询明细数据");
        }else if("2".equals(pmsStatementEquivalent.getEquivalentTypeCode())){
            List<PmsStatementEquivalentTwo> list = pmsBonusStatementService.selectEquivalentListByThread(pmsStatementEquivalent);
            ExcelUtil<PmsStatementEquivalentTwo> util = new ExcelUtil<PmsStatementEquivalentTwo>(PmsStatementEquivalentTwo.class);
            return util.exportExcel(list, "门诊医技当量查询明细数据");
        }else if("3".equals(pmsStatementEquivalent.getEquivalentTypeCode())){
            List<PmsStatementEquivalentThree> list = pmsBonusStatementService.selectEquivalentListByThread(pmsStatementEquivalent);
            ExcelUtil<PmsStatementEquivalentThree> util = new ExcelUtil<PmsStatementEquivalentThree>(PmsStatementEquivalentThree.class);
            return util.exportExcel(list, "日间病房当量明细数据");
        }else if("4".equals(pmsStatementEquivalent.getEquivalentTypeCode())){
            List<PmsStatementEquivalentFour> list = pmsBonusStatementService.selectEquivalentListByThread(pmsStatementEquivalent);
            ExcelUtil<PmsStatementEquivalentFour> util = new ExcelUtil<PmsStatementEquivalentFour>(PmsStatementEquivalentFour.class);
            return util.exportExcel(list, "住院临床当量明细数据");
        }else if("5".equals(pmsStatementEquivalent.getEquivalentTypeCode())){
            List<PmsStatementEquivalentFive> list = pmsBonusStatementService.selectEquivalentListByThread(pmsStatementEquivalent);
            ExcelUtil<PmsStatementEquivalentFive> util = new ExcelUtil<PmsStatementEquivalentFive>(PmsStatementEquivalentFive.class);
            return util.exportExcel(list, "住院医技当量明细数据");
        }else if("6".equals(pmsStatementEquivalent.getEquivalentTypeCode())){
            List<PmsStatementEquivalentSix> list = pmsBonusStatementService.selectEquivalentListByThread(pmsStatementEquivalent);
            ExcelUtil<PmsStatementEquivalentSix> util = new ExcelUtil<PmsStatementEquivalentSix>(PmsStatementEquivalentSix.class);
            return util.exportExcel(list, "住院护理明细数据");
        }else if("7".equals(pmsStatementEquivalent.getEquivalentTypeCode())){
            List<PmsStatementEquivalentSeven> list = pmsBonusStatementService.selectEquivalentListByThread(pmsStatementEquivalent);
            ExcelUtil<PmsStatementEquivalentSeven> util = new ExcelUtil<PmsStatementEquivalentSeven>(PmsStatementEquivalentSeven.class);
            return util.exportExcel(list, "R-病房当量明细数据");
        }else{
            return AjaxResult.error("选择类别不存在！");
        }

    }
//    @GetMapping("/exportDetail")
//    @ApiOperation("导出当量明细列表Excel")
//    public AjaxResult exportDetail(PmsStatementEquivalent pmsStatementEquivalent) {
//        List<PmsStatementEquivalent> list = pmsBonusStatementService.selectEquivalentList(pmsStatementEquivalent);
//
////            List<PmsStatementEquivalentOne> mesList = new ArrayList<>();
////            for (PmsStatementEquivalent source : list) {
////                PmsStatementEquivalentOne target = new PmsStatementEquivalentOne();
////                copyProperties(source, target);
////                mesList.add(target);
////            }
//            ExcelUtil<PmsStatementEquivalent> util = new ExcelUtil<PmsStatementEquivalent>(PmsStatementEquivalent.class);
//            return util.exportExcel(list, "门诊临床当量查询明细数据");
//
//
//    }
    public static <T,U> void copyProperties(T source, U target) {

        try {
            BeanUtils.copyProperties(target, source);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }
}
