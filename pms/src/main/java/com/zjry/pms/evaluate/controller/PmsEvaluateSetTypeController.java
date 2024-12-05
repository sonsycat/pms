package com.zjry.pms.evaluate.controller;

import java.util.List;

import com.zjry.common.utils.StringUtils;
import com.zjry.pms.common.Constants;
import com.zjry.pms.evaluate.domain.PmsEvaluateBsc;
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
import com.zjry.pms.evaluate.domain.PmsEvaluateSetType;
import com.zjry.pms.evaluate.service.IPmsEvaluateSetTypeService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 指标集分类Controller
 *
 * @author mk
 * @date 2022-08-03
 */
@RestController
@RequestMapping("/settype/settype")
@Api(tags={"【指标集分类】"})
public class PmsEvaluateSetTypeController extends BaseController {
    @Autowired
    private IPmsEvaluateSetTypeService pmsEvaluateSetTypeService;

    /**
     * 查询指标集分类列表
     */
    @PreAuthorize("@ss.hasPermi('settype:settype:list')")
    @GetMapping("/list")
    @ApiOperation("查询指标集分类列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsEvaluateSetType pmsEvaluateSetType) {
        List<PmsEvaluateSetType> list = pmsEvaluateSetTypeService.selectPmsEvaluateSetTypeList(pmsEvaluateSetType);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('evaluate:bsc:list')")
    @GetMapping("/returnList")
    @ApiOperation("查询BSC分类列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public AjaxResult returnList(PmsEvaluateSetType pmsEvaluateSetType) {
        List<PmsEvaluateSetType> list = pmsEvaluateSetTypeService.selectPmsEvaluateSetTypeList(pmsEvaluateSetType);
        return AjaxResult.success(list);
    }

    /**
     * 导出指标集分类列表
     */
    @PreAuthorize("@ss.hasPermi('settype:settype:export')")
    @Log(title = "指标集分类", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出指标集分类列表Excel")
    public AjaxResult export(PmsEvaluateSetType pmsEvaluateSetType) {
        List<PmsEvaluateSetType> list = pmsEvaluateSetTypeService.selectPmsEvaluateSetTypeList(pmsEvaluateSetType);
        ExcelUtil<PmsEvaluateSetType> util = new ExcelUtil<PmsEvaluateSetType>(PmsEvaluateSetType.class);
        return util.exportExcel(list, "指标集分类数据");
    }

    /**
     * 获取指标集分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('settype:settype:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取指标集分类详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsEvaluateSetTypeService.selectPmsEvaluateSetTypeById(id));
    }

    /**
     * 新增指标集分类
     */
    @PreAuthorize("@ss.hasPermi('settype:settype:add')")
    @Log(title = "指标集分类", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增指标集分类")
    public AjaxResult add(@RequestBody PmsEvaluateSetType pmsEvaluateSetType) {
        PmsEvaluateSetType sbmc = new PmsEvaluateSetType();
        if(StringUtils.isEmpty(pmsEvaluateSetType.getParentCode())){
            sbmc.setParentCode(Constants.PARENT_CODE);
        }else{
            sbmc.setParentCode(pmsEvaluateSetType.getParentCode());
        }
        sbmc.setBName(pmsEvaluateSetType.getBscName());
        List<PmsEvaluateSetType> list = pmsEvaluateSetTypeService.selectPmsEvaluateSetTypeList(sbmc);
        if(list.size()>0){
            return error("操作失败:同级别下已有该名称");
        }
        try {
            pmsEvaluateSetTypeService.insertPmsEvaluateSetType(pmsEvaluateSetType);
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return success();
    }

    /**
     * 修改指标集分类
     */
    @PreAuthorize("@ss.hasPermi('settype:settype:edit')")
    @Log(title = "指标集分类", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改指标集分类")
    public AjaxResult edit(@RequestBody PmsEvaluateSetType pmsEvaluateSetType) {
        PmsEvaluateSetType sbmc = new PmsEvaluateSetType();
        if(StringUtils.isEmpty(pmsEvaluateSetType.getParentCode())){
            sbmc.setParentCode(Constants.PARENT_CODE);
        }else{
            sbmc.setParentCode(pmsEvaluateSetType.getParentCode());
        }
        sbmc.setBName(pmsEvaluateSetType.getBscCode());
        sbmc.setId(pmsEvaluateSetType.getId());
        List<PmsEvaluateSetType> list = pmsEvaluateSetTypeService.selectPmsEvaluateSetTypeList(sbmc);
        if(list.size()>0){
            return error("操作失败:同级别下已有该名称");
        }
        return toAjax(pmsEvaluateSetTypeService.updatePmsEvaluateSetType(pmsEvaluateSetType));
    }
    /**
     * 启用停用
     */
    @Log(title = "启用停用", businessType = BusinessType.UPDATE)
    @PostMapping("/isStop")
    @ApiOperation("物资字典分类启用停用")
    public AjaxResult updateIsStop(@RequestBody PmsEvaluateSetType pmsEvaluateSetType) {
        String msg = null;
        try {
            msg = pmsEvaluateSetTypeService.updateIsStop(pmsEvaluateSetType.getId(),pmsEvaluateSetType.getIsStop());
        } catch (Exception e) {
            return error(e.getMessage());
        }
        if(StringUtils.isNotEmpty(msg)){
            return error(msg);
        }
        return success();
    }


    /**
     * 删除指标集分类
     */
    @PreAuthorize("@ss.hasPermi('settype:settype:remove')")
    @Log(title = "指标集分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除指标集分类")
    public AjaxResult remove(@PathVariable Long[] ids) {
        String msg = null;
        try {
            msg = pmsEvaluateSetTypeService.deletePmsEvaluateSetTypeByIds(ids);
        } catch (Exception e) {
            return error(e.getMessage());
        }
        if(StringUtils.isNotEmpty(msg)){
            return error(msg);
        }
        return success();
    }
}
