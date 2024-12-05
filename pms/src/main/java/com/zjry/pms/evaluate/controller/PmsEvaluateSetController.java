package com.zjry.pms.evaluate.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.pms.common.Constants;
import com.zjry.pms.evaluate.domain.PmsEvaluateSet;
import com.zjry.pms.evaluate.domain.PmsEvaluateSetIndex;
import com.zjry.pms.evaluate.service.IPmsEvaluateSetIndexService;
import com.zjry.pms.evaluate.service.IPmsEvaluateSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 指标集管理Controller
 *
 * @author mk
 * @date 2022-08-04
 */
@RestController
@RequestMapping("/evaluate/set")
@Api(tags={"【指标集管理】"})
public class PmsEvaluateSetController extends BaseController {
    @Autowired
    private IPmsEvaluateSetService pmsEvaluateSetService;
    @Autowired
    private IPmsEvaluateSetIndexService pmsEvaluateSetIndexService;

    /**
     * 查询指标集管理列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:set:list')")
    @GetMapping("/list")
    @ApiOperation("查询指标集管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsEvaluateSet pmsEvaluateSet) {
        pmsEvaluateSet.setIsBonus(Constants.IS_CHANGE_NO);
        startPage();
        List<PmsEvaluateSet> list = pmsEvaluateSetService.selectPmsEvaluateSetList(pmsEvaluateSet);
        return getDataTable(list);
    }


    @PreAuthorize("@ss.hasPermi('evaluate:set:list')")
    @GetMapping("/bonusList")
    @ApiOperation("查询指标集管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo bonusList(PmsEvaluateSet pmsEvaluateSet) {
        pmsEvaluateSet.setIsBonus(Constants.IS_CHANGE_YES);
        startPage();
        List<PmsEvaluateSet> list = pmsEvaluateSetService.selectPmsEvaluateSetList(pmsEvaluateSet);
        return getDataTable(list);
    }

    /**
     * 导出指标集管理列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:set:export')")
    @Log(title = "指标集管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出指标集管理列表Excel")
    public AjaxResult export(PmsEvaluateSet pmsEvaluateSet) {
        List<PmsEvaluateSet> list = pmsEvaluateSetService.selectPmsEvaluateSetList(pmsEvaluateSet);
        ExcelUtil<PmsEvaluateSet> util = new ExcelUtil<PmsEvaluateSet>(PmsEvaluateSet.class);
        return util.exportExcel(list, "指标集管理数据");
    }

    /**
     * 获取指标集管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('evaluate:set:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取指标集管理详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsEvaluateSetService.selectPmsEvaluateSetById(id));
    }

    /**
     * 新增指标集管理
     */
    @PreAuthorize("@ss.hasPermi('evaluate:set:add')")
    @Log(title = "复制指标集管理", businessType = BusinessType.INSERT)
    @PostMapping("/copy")
    @ApiOperation("复制指标集管理")
    public AjaxResult copy(@RequestBody PmsEvaluateSet pmsEvaluateSet) {
        try {
            QueryWrapper<PmsEvaluateSet> qw = new QueryWrapper();
            qw.lambda().eq(PmsEvaluateSet::getSetName, pmsEvaluateSet.getSetName())
                    .eq(PmsEvaluateSet::getIsBonus, Constants.COMMON_YES)
                    .last("LIMIT 1");
            int count = pmsEvaluateSetService.count(qw);
            if(count > 0){
                return error("指标集名称已存在");
            }
            pmsEvaluateSetService.copy(pmsEvaluateSet);
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return success();
    }

    /**
     * 新增指标集管理
     */
    @PreAuthorize("@ss.hasPermi('evaluate:set:add')")
    @Log(title = "指标集管理", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增指标集管理")
    public AjaxResult add(@RequestBody PmsEvaluateSet pmsEvaluateSet) {
        PmsEvaluateSet pms = new PmsEvaluateSet();
        pms.setCompCode(pmsEvaluateSet.getCompCode());
        pms.setSName(pmsEvaluateSet.getSetName());
        pms.setIsBonus(Constants.IS_CHANGE_NO);
        List<PmsEvaluateSet> list = pmsEvaluateSetService.selectPmsEvaluateSetList(pms);
        if(list.size()>0){
            return error("指标集名称已存在");
        }
        pmsEvaluateSet.setIsBonus(Constants.IS_CHANGE_NO);
        return toAjax(pmsEvaluateSetService.insertPmsEvaluateSet(pmsEvaluateSet));
    }

    /**
     * 新增指标集管理
     */
    @PreAuthorize("@ss.hasPermi('evaluate:set:add')")
    @Log(title = "指标集管理", businessType = BusinessType.INSERT)
    @PostMapping("/addBonus")
    @ApiOperation("新增指标集管理")
    public AjaxResult addBonus(@RequestBody PmsEvaluateSet pmsEvaluateSet) {
        PmsEvaluateSet pms = new PmsEvaluateSet();
        pms.setCompCode(pmsEvaluateSet.getCompCode());
        pms.setSName(pmsEvaluateSet.getSetName());
        pms.setIsBonus(Constants.IS_CHANGE_YES);
        List<PmsEvaluateSet> list = pmsEvaluateSetService.selectPmsEvaluateSetList(pms);
        if(list.size()>0){
            return error("指标集名称已存在");
        }
        pmsEvaluateSet.setIsBonus(Constants.IS_CHANGE_YES);
        return toAjax(pmsEvaluateSetService.insertPmsEvaluateSet(pmsEvaluateSet));
    }

    /**
     * 修改指标集管理
     */
    @PreAuthorize("@ss.hasPermi('evaluate:set:edit')")
    @Log(title = "指标集管理", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改指标集管理")
    public AjaxResult edit(@RequestBody PmsEvaluateSet pmsEvaluateSet) {
        PmsEvaluateSet pms = new PmsEvaluateSet();
        pms.setId(pmsEvaluateSet.getId());
        pms.setCompCode(pmsEvaluateSet.getCompCode());
        pms.setSName(pmsEvaluateSet.getSetName());
        List<PmsEvaluateSet> list = pmsEvaluateSetService.selectPmsEvaluateSetList(pms);
        if(list.size()>0){
            return error("指标集名称重复");
        }
        PmsEvaluateSetIndex pes = new PmsEvaluateSetIndex();
        pes.setCompCode(pmsEvaluateSet.getCompCode());
        pes.setSetCode(pmsEvaluateSet.getSetCode());
        List<PmsEvaluateSetIndex> list1 = pmsEvaluateSetIndexService.selectPmsEvaluateSetIndexList(pes);
        if(list1.size()>0){
            PmsEvaluateSet pes1 = pmsEvaluateSetService.selectPmsEvaluateSetById(pmsEvaluateSet.getId());
            if(!pes1.getOrgType().equals(pmsEvaluateSet.getOrgType())){
                return error("该指标集已选择指标");
            }
        }
        return toAjax(pmsEvaluateSetService.updatePmsEvaluateSet(pmsEvaluateSet));
    }

    /**
     * 删除指标集管理
     */
    @PreAuthorize("@ss.hasPermi('evaluate:set:remove')")
    @Log(title = "指标集管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除指标集管理")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsEvaluateSetService.deletePmsEvaluateSetByIds(ids));
    }
}
