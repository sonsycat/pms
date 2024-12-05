package com.zjry.pms.evaluate.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.zjry.formDesign.util.JacksonUtil;
import com.zjry.pms.basic.domain.AssetsBasicPublicVo;
import com.zjry.pms.evaluate.domain.*;
import com.zjry.pms.project.domain.CostProject;
import com.zjry.pms.project.service.ICostProjectService;
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
import com.zjry.pms.evaluate.service.IPmsEvaluateSetDeptService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 指标集与科室管理Controller
 *
 * @author mk
 * @date 2022-08-09
 */
@RestController
@RequestMapping("/evaluate/setdept")
@Api(tags = {"【指标集与科室管理】"})
public class PmsEvaluateSetDeptController extends BaseController {
    @Autowired
    private IPmsEvaluateSetDeptService pmsEvaluateSetDeptService;
    @Autowired
    private ICostProjectService costProjectService;

    /**
     * 查询指标集与科室管理列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:setdept:list')")
    @GetMapping("/list")
    @ApiOperation("查询指标集与科室管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsEvaluateSetDept pmsEvaluateSetDept) {
        List<CostProject> list = costProjectService.deptInfoByType(null,pmsEvaluateSetDept.getCompCode());
        HashMap<String, PmsEvaluateSetDept> hs = new HashMap<String, PmsEvaluateSetDept>();
        List<AssetsBasicPublicVo> data = new ArrayList<AssetsBasicPublicVo>();
        for (CostProject item : list) {
            PmsEvaluateSetDept pe = new PmsEvaluateSetDept();
            pe.setDeptCode(item.getDeptCode().toString());
            pe.setDeptName(item.getDeptName());
            hs.put(item.getDeptCode().toString(), pe);
        }
        startPage();
        List<PmsEvaluateSetDept> listN = pmsEvaluateSetDeptService.selectPmsSetDeptList(pmsEvaluateSetDept);
        for (PmsEvaluateSetDept itemN : listN) {
            if (itemN.getInternalScore() != null){
                List<PmsEvaluateBscSort> pmsEvaluateBscSortList = JacksonUtil.toList(itemN.getInternalScore().toString(), PmsEvaluateBscSort.class);
                itemN.setInternalScore(pmsEvaluateBscSortList);
            }
            hs.put(itemN.getDeptCode(), itemN);
        }
        List<PmsEvaluateSetDept> ldept = new ArrayList<>();
        for (String key : hs.keySet()) {
            ldept.add(hs.get(key));
        }
        return getDataTable(ldept);
    }

    /**
     * 导出指标集与科室管理列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:setdept:export')")
    @Log(title = "指标集与科室管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出指标集与科室管理列表Excel")
    public AjaxResult export(PmsEvaluateSetDept pmsEvaluateSetDept) {
        List<PmsEvaluateSetDept> list = pmsEvaluateSetDeptService.selectPmsSetDeptList(pmsEvaluateSetDept);
        ExcelUtil<PmsEvaluateSetDept> util = new ExcelUtil<PmsEvaluateSetDept>(PmsEvaluateSetDept.class);
        return util.exportExcel(list, "指标集与科室管理数据");
    }

    /**
     * 获取指标集与科室管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('evaluate:setdept:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取指标集与科室管理详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsEvaluateSetDeptService.selectPmsEvaluateSetDeptById(id));
    }



    /**
     * 新增指标集与科室管理
     */
    @PreAuthorize("@ss.hasPermi('evaluate:setdept:add')")
    @Log(title = "指标集与科室管理", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增指标集与科室管理")
    public AjaxResult add(@RequestBody PmsEvaluateSetDept pmsEvaluateSetDept) {
        PmsEvaluateSetDept pe = new PmsEvaluateSetDept();
        pe.setCompCode(pmsEvaluateSetDept.getCompCode());
        pe.setDeptCode(pmsEvaluateSetDept.getDeptCode());
        pe.setNowDate(pmsEvaluateSetDept.getNowDate());
        pe.setSetCode(pmsEvaluateSetDept.getSetCode());
        List<PmsEvaluateSetDept> listN = pmsEvaluateSetDeptService.selectPmsSetDeptList(pe);
        if(listN.size()>0){
            return error("该科室已有当前月数据");
        }
        return toAjax(pmsEvaluateSetDeptService.insertPmsEvaluateSetDept(pmsEvaluateSetDept));
    }

    @PostMapping("/copy")
    @ApiOperation("新增指标集与科室管理")
    public AjaxResult copy(@RequestBody PmsEvaluateSetDept pmsEvaluateSetDept) {
        PmsEvaluateSetDept pes = new PmsEvaluateSetDept();
        pes.setCompCode(pmsEvaluateSetDept.getCompCode());
        pes.setNowDate(pmsEvaluateSetDept.getNowDate());
        List<PmsEvaluateSetDept> listN = pmsEvaluateSetDeptService.selectPmsSetDeptList(pes);
        try {
            pmsEvaluateSetDeptService.copy(listN, pmsEvaluateSetDept.getCopyDate());
        } catch (Exception e) {
            return error();
        }
        return success();
    }

    @PostMapping("/savePmsEvaSetDeptDN")
    @ApiOperation("新增指标集与科室管理")
    public AjaxResult savePmsEvaSetDeptDN(@RequestBody SetDeptDNList setDeptDNList) {
        try {
            pmsEvaluateSetDeptService.savePmsEvaSetDeptDN(setDeptDNList);
        } catch (Exception e) {
            return error();
        }
        return success();
    }



    /**
     * 修改指标集与科室管理
     */
    @PreAuthorize("@ss.hasPermi('evaluate:setdept:edit')")
    @Log(title = "指标集与科室管理", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改指标集与科室管理")
    public AjaxResult edit(@RequestBody PmsEvaluateSetDept pmsEvaluateSetDept) {
        return toAjax(pmsEvaluateSetDeptService.updatePmsEvaluateSetDept(pmsEvaluateSetDept));
    }

    /**
     * 删除指标集与科室管理
     */
    @PreAuthorize("@ss.hasPermi('evaluate:setdept:remove')")
    @Log(title = "指标集与科室管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除指标集与科室管理")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsEvaluateSetDeptService.deletePmsEvaluateSetDeptByIds(ids));
    }

    /**
     * 获取指标集对应指标的BSC分类
     */
    @PreAuthorize("@ss.hasPermi('evaluate:setdept:query')")
    @GetMapping(value = "/selectBSCSort/{setCode}")
    @ApiOperation("获取指标集与科室管理详细信息")
    public AjaxResult selectBscSort(@PathVariable("setCode") String setCode) {
        return AjaxResult.success(pmsEvaluateSetDeptService.selectBscSort(setCode));
    }
}
