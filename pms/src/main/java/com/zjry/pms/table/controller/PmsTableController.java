package com.zjry.pms.table.controller;

import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.pms.table.domain.*;
import com.zjry.pms.table.service.IPmsTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 收入Controller
 *
 * @author yzh
 * @date 2022-05-17
 */
@RestController
@RequestMapping("/table")
@Api(tags={"【收入】"})
public class PmsTableController extends BaseController {
    @Autowired
    private IPmsTableService pmsTableService;

    @GetMapping("/listOpcList")
    @ApiOperation("查询门诊收入列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo listOpcList(PmsTableOpcQuery pmsTableOpcQuery) {
        startPage();
        List<PmsTableOpcRes> list = pmsTableService.selectListOpcList(pmsTableOpcQuery);
        return getDataTable(list);
    }
    @Log(title = "导出门诊收入明细列表", businessType = BusinessType.EXPORT)
    @GetMapping("/exportDetailOpc")
    @ApiOperation("导出门诊收入明细列表Excel")
    public AjaxResult exportDetailOpc(PmsTableOpcQuery pmsTableOpcQuery) {

        List<PmsTableOpcRes> list = pmsTableService.selectListOpcList(pmsTableOpcQuery);
        ExcelUtil<PmsTableOpcRes> util = new ExcelUtil<PmsTableOpcRes>(PmsTableOpcRes.class);
        return util.exportExcel(list, "门诊收入明细数据");


    }
    @GetMapping("/listBihList")
    @ApiOperation("查询住院收入列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo listBihList(PmsTableBihQuery pmsTableBihQuery) {
        startPage();
        List<PmsTableBihRes> list = pmsTableService.selectListBihList(pmsTableBihQuery);
        return getDataTable(list);
    }
    @Log(title = "导出住院收入明细列表", businessType = BusinessType.EXPORT)
    @GetMapping("/exportDetailBih")
    @ApiOperation("导出住院收入明细列表Excel")
    public AjaxResult exportDetailBih(PmsTableBihQuery pmsTableBihQuery) {

        List<PmsTableBihRes> list = pmsTableService.selectListBihList(pmsTableBihQuery);
        ExcelUtil<PmsTableBihRes> util = new ExcelUtil<PmsTableBihRes>(PmsTableBihRes.class);
        return util.exportExcel(list, "住院收入明细数据");


    }
    @GetMapping("/listDeptbeList")
    @ApiOperation("查询科室核算前收入列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo listDeptbeList(PmsTableDeptbeQuery pmsTableDeptbeQuery) {
        startPage();
        List<PmsTableDeptbeRes> list = pmsTableService.listDeptbeList(pmsTableDeptbeQuery);
        return getDataTable(list);
    }
    @Log(title = "导出科室核算前收入明细列表", businessType = BusinessType.EXPORT)
    @GetMapping("/exportDetailDeptbe")
    @ApiOperation("导出科室核算前明细列表Excel")
    public AjaxResult exportDetailDeptbe(PmsTableDeptbeQuery pmsTableDeptbeQuery) {

        return pmsTableService.exportDetailDeptbe(pmsTableDeptbeQuery, "科室核算前收入明细数据");


    }
    @GetMapping("/listHsdeptList")
    @ApiOperation("查询核算科目医疗收入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo listHsdeptList(PmsTableHsdeptQuery pmsTableHsdeptQuery) {
        startPage();
        List<PmsTableHsdeptRes> list = pmsTableService.listHsdeptList(pmsTableHsdeptQuery);
        return getDataTable(list);
    }
    @Log(title = "导出核算科目医疗收入明细列表", businessType = BusinessType.EXPORT)
    @GetMapping("/exportDetailHsdept")
    @ApiOperation("导出核算科目医疗收入列表Excel")
    public AjaxResult exportDetailHsdept(PmsTableHsdeptQuery pmsTableHsdeptQuery) {

        return pmsTableService.exportDetailHsdept(pmsTableHsdeptQuery, "核算科目医疗收入明细数据");


    }
    @GetMapping("/listDeptarList")
    @ApiOperation("查询科室核算后收入列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo listDeptarList(PmsTableDeptarQuery pmsTableDeptarQuery) {
        startPage();
        List<PmsTableDeptarRes> list = pmsTableService.listDeptarList(pmsTableDeptarQuery);
        return getDataTable(list);
    }
    @Log(title = "导出科室核算后收入明细列表", businessType = BusinessType.EXPORT)
    @GetMapping("/exportDetailDeptar")
    @ApiOperation("导出科室核算后明细列表Excel")
    public AjaxResult exportDetailDeptar(PmsTableDeptarQuery pmsTableDeptarQuery) {

        return pmsTableService.exportDetailDeptar(pmsTableDeptarQuery, "科室核算后收入明细数据");


    }
    @GetMapping("/listIncsumList")
    @ApiOperation("查询收入汇总列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo listIncsumList(PmsTableIncsumQuery pmsTableIncsumQuery) {
        startPage();
        List<PmsTableIncsumRes> list = pmsTableService.listIncsumList(pmsTableIncsumQuery);
        return getDataTable(list);
    }
    @Log(title = "导出收入汇总明细列表", businessType = BusinessType.EXPORT)
    @GetMapping("/exportDetailIncsum")
    @ApiOperation("导出收入汇总明细列表Excel")
    public AjaxResult exportDetailIncsum(PmsTableIncsumQuery pmsTableIncsumQuery) {

        List<PmsTableIncsumRes> list = pmsTableService.listIncsumList(pmsTableIncsumQuery);
        ExcelUtil<PmsTableIncsumRes> util = new ExcelUtil<PmsTableIncsumRes>(PmsTableIncsumRes.class);
        return util.exportExcel(list, "收入汇总明细数据");


    }
    @GetMapping("/listDocList")
    @ApiOperation("查询医生明细列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo listDocList(PmsTableDocQuery pmsTableDocQuery) {
        startPage();
        List<PmsTableDocRes> list = pmsTableService.listDocList(pmsTableDocQuery);
        return getDataTable(list);
    }
    @Log(title = "导出医生明细列表", businessType = BusinessType.EXPORT)
    @GetMapping("/exportDetailDoc")
    @ApiOperation("导出医生明细列表Excel")
    public AjaxResult exportDetailDoc(PmsTableDocQuery pmsTableDocQuery) {

        List<PmsTableDocRes> list = pmsTableService.listDocList(pmsTableDocQuery);
        ExcelUtil<PmsTableDocRes> util = new ExcelUtil<PmsTableDocRes>(PmsTableDocRes.class);
        return util.exportExcel(list, "医生明细数据");


    }
    @GetMapping("/listCostList")
    @ApiOperation("查询成本汇总明细列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo listCostList(PmsTableCostQuery pmsTableCostQuery) {
        startPage();
        List<PmsTableCostRes> list = pmsTableService.listCostList(pmsTableCostQuery);
        return getDataTable(list);
    }
    @Log(title = "导出成本汇总明细列表", businessType = BusinessType.EXPORT)
    @GetMapping("/exportDetailCost")
    @ApiOperation("导出成本汇总明细列表Excel")
    public AjaxResult exportDetailCost(PmsTableCostQuery pmsTableCostQuery) {

        List<PmsTableCostRes> list = pmsTableService.listCostList(pmsTableCostQuery);
        ExcelUtil<PmsTableCostRes> util = new ExcelUtil<PmsTableCostRes>(PmsTableCostRes.class);
        return util.exportExcel(list, "成本汇总明细数据");


    }
}
