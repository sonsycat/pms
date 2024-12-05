package com.zjry.pms.evaluate.controller;

import java.util.ArrayList;
import java.util.List;

import com.zjry.common.utils.StringUtils;
import com.zjry.pms.common.Constants;
import com.zjry.pms.common.utils.UserUtils;
import com.zjry.pms.evaluate.domain.PmsEvaluateIndex;
import com.zjry.pms.evaluate.service.IPmsEvaluateIndexService;
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
import com.zjry.pms.evaluate.domain.PmsEvaluateBsc;
import com.zjry.pms.evaluate.service.IPmsEvaluateBscService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * BSC分类Controller
 *
 * @author mk
 * @date 2022-08-01
 */
@RestController
@RequestMapping("/evaluate/bsc")
@Api(tags={"【BSC分类】"})
public class PmsEvaluateBscController extends BaseController {
    @Autowired
    private IPmsEvaluateBscService pmsEvaluateBscService;
    @Autowired
    private IPmsEvaluateIndexService pmsEvaluateIndexService;

    /**
     * 查询BSC分类列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:bsc:list')")
    @GetMapping("/list")
    @ApiOperation("查询BSC分类列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsEvaluateBsc pmsEvaluateBsc) {
        List<PmsEvaluateBsc> list = pmsEvaluateBscService.selectPmsEvaluateBscList(pmsEvaluateBsc);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('evaluate:bsc:list')")
    @GetMapping("/returnList")
    @ApiOperation("查询BSC分类列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public AjaxResult returnList(PmsEvaluateBsc pmsEvaluateBsc) {
        List<PmsEvaluateBsc> list = pmsEvaluateBscService.selectPmsEvaluateBscList(pmsEvaluateBsc);
        return AjaxResult.success(list);
    }

    @PreAuthorize("@ss.hasPermi('evaluate:bsc:list')")
    @GetMapping("/listForTree")
    @ApiOperation("查询BSC分类列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public AjaxResult listForTree(PmsEvaluateBsc pmsEvaluateBsc, String orgType, String isBonus) {
        List<PmsEvaluateBsc> list = new ArrayList<>();
        if(Constants.COMMON_NO.equals(isBonus)){
            list = pmsEvaluateBscService.selectPmsEvaluateBscList(pmsEvaluateBsc);
        }
        for(PmsEvaluateBsc item : list){
            item.setIsLast("1");
        }
        PmsEvaluateIndex pm = new PmsEvaluateIndex();
        pm.setOrgType(orgType);
        pm.setIsBonus(isBonus);
        pm.setCompCode(UserUtils.getSysUser().getDeptId());
        List<PmsEvaluateIndex> pmsEvaluateIndexList = pmsEvaluateIndexService.selectPmsEvaluateIndexList(pm);
        for(PmsEvaluateIndex pei : pmsEvaluateIndexList){
            PmsEvaluateBsc peb = new PmsEvaluateBsc();
            peb.setBscCode(pei.getIndexCode());
            peb.setBscName(pei.getIndexName());
            peb.setIsLast("0");
            peb.setParentCode(pei.getBscSecondCode());
            list.add(peb);
        }
        return AjaxResult.success(list);
    }



    /**
     * 导出BSC分类列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:bsc:export')")
    @Log(title = "BSC分类", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出BSC分类列表Excel")
    public AjaxResult export(PmsEvaluateBsc pmsEvaluateBsc) {
        List<PmsEvaluateBsc> list = pmsEvaluateBscService.selectPmsEvaluateBscList(pmsEvaluateBsc);
        ExcelUtil<PmsEvaluateBsc> util = new ExcelUtil<PmsEvaluateBsc>(PmsEvaluateBsc.class);
        return util.exportExcel(list, "BSC分类数据");
    }

    /**
     * 获取BSC分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('evaluate:bsc:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取BSC分类详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsEvaluateBscService.selectPmsEvaluateBscById(id));
    }

    /**
     * 新增BSC分类
     */
    @PreAuthorize("@ss.hasPermi('evaluate:bsc:add')")
    @Log(title = "BSC分类", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增BSC分类")
    public AjaxResult add(@RequestBody PmsEvaluateBsc pmsEvaluateBsc) {
        PmsEvaluateBsc sbmc = new PmsEvaluateBsc();
        if(StringUtils.isEmpty(pmsEvaluateBsc.getParentCode())){
            sbmc.setParentCode(Constants.PARENT_CODE);
        }else{
            sbmc.setParentCode(pmsEvaluateBsc.getParentCode());
        }
        sbmc.setBName(pmsEvaluateBsc.getBscName());
        List<PmsEvaluateBsc> list = pmsEvaluateBscService.selectPmsEvaluateBscList(sbmc);
        if(list.size()>0){
            return error("操作失败:同级别下已有该名称");
        }
        try {
            pmsEvaluateBscService.insertPmsEvaluateBsc(pmsEvaluateBsc);
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return success();
    }

    /**
     * 修改BSC分类
     */
    @PreAuthorize("@ss.hasPermi('evaluate:bsc:edit')")
    @Log(title = "BSC分类", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改BSC分类")
    public AjaxResult edit(@RequestBody PmsEvaluateBsc pmsEvaluateBsc) {
        PmsEvaluateBsc sbmc = new PmsEvaluateBsc();
        if(StringUtils.isEmpty(pmsEvaluateBsc.getParentCode())){
            sbmc.setParentCode(Constants.PARENT_CODE);
        }else{
            sbmc.setParentCode(pmsEvaluateBsc.getParentCode());
        }
        sbmc.setBName(pmsEvaluateBsc.getBscCode());
        sbmc.setId(pmsEvaluateBsc.getId());
        List<PmsEvaluateBsc> list = pmsEvaluateBscService.selectPmsEvaluateBscList(sbmc);
        if(list.size()>0){
            return error("操作失败:同级别下已有该名称");
        }
        return toAjax(pmsEvaluateBscService.updatePmsEvaluateBsc(pmsEvaluateBsc));
    }

    /**
     * 启用停用
     */
    @Log(title = "BSC启用停用", businessType = BusinessType.UPDATE)
    @PostMapping("/isStop")
    @ApiOperation("物资字典分类启用停用")
    public AjaxResult updateIsStop(@RequestBody PmsEvaluateBsc pmsEvaluateBsc) {
        String msg = null;
        try {
            msg = pmsEvaluateBscService.updateIsStop(pmsEvaluateBsc.getId(),pmsEvaluateBsc.getIsStop());
        } catch (Exception e) {
            return error(e.getMessage());
        }
        if(StringUtils.isNotEmpty(msg)){
            return error(msg);
        }
        return success();
    }

    /**
     * 删除BSC分类
     */
    @PreAuthorize("@ss.hasPermi('evaluate:bsc:remove')")
    @Log(title = "BSC分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除BSC分类")
    public AjaxResult remove(@PathVariable Long[] ids) {
        String msg = null;
        try {
            msg = pmsEvaluateBscService.deletePmsEvaluateBscByIds(ids);
        } catch (Exception e) {
            return error(e.getMessage());
        }
        if(StringUtils.isNotEmpty(msg)){
            return error(msg);
        }
        return success();
    }
}
