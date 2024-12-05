package com.zjry.pms.quality.controller;

import java.util.Date;
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
import com.zjry.pms.quality.domain.PmsQualityGenerate;
import com.zjry.pms.quality.service.IPmsQualityGenerateService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

import javax.xml.crypto.Data;

/**
 * 质量数据生成Controller
 *
 * @author mk
 * @date 2022-07-26
 */
@RestController
@RequestMapping("/quality/quality")
@Api(tags={"【质量数据生成】"})
public class PmsQualityGenerateController extends BaseController {
    @Autowired
    private IPmsQualityGenerateService pmsQualityGenerateService;

    /**
     * 查询质量数据生成列表
     */
    @PreAuthorize("@ss.hasPermi('quality:quality:list')")
    @GetMapping("/list")
    @ApiOperation("查询质量数据生成列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsQualityGenerate pmsQualityGenerate) {
        startPage();
        List<PmsQualityGenerate> list = pmsQualityGenerateService.selectPmsQualityGenerateList(pmsQualityGenerate);
        return getDataTable(list);
    }


    /**
     * 查询质量数据生成列表
     */
    @PreAuthorize("@ss.hasPermi('quality:quality:list')")
    @GetMapping("/listForD")
    @ApiOperation("查询质量数据生成列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo listForD(PmsQualityGenerate pmsQualityGenerate) {
        startPage();
        List<PmsQualityGenerate> list = pmsQualityGenerateService.listForD(pmsQualityGenerate);
        return getDataTable(list);
    }


    @GetMapping("/queryForTrue")
    @ApiOperation("查询质量数据生成列表")
    public AjaxResult queryForTrue(PmsQualityGenerate pmsQualityGenerate) {
        PmsQualityGenerate pqg = new PmsQualityGenerate();
        pqg.setQTime(pmsQualityGenerate.getOccureTime());
        pqg.setCompCode(pmsQualityGenerate.getCompCode());
        List<PmsQualityGenerate> list = pmsQualityGenerateService.selectPmsQualityGenerateList(pqg);
        if(list.size()>0){
            return AjaxResult.success(false);
        }else{
            return AjaxResult.success(true);
        }
    }

    @PostMapping("/creatZLSJ")
    @ApiOperation("查询质量数据生成列表")
    public AjaxResult creatZLSJ(@RequestBody  PmsQualityGenerate pmsQualityGenerate) {
        try {
            pmsQualityGenerateService.creatZLSJ(pmsQualityGenerate);
        } catch (Exception e) {
            logger.error("质量数据生成出错：", e);
           return error(e.getMessage());
        }
        return success();
    }


    @PreAuthorize("@ss.hasPermi('quality:quality:list')")
    @GetMapping("/selectSumData")
    @ApiOperation("查询汇总列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo selectSumData(Long compCode, Long deptCode, Date qDate) {
        startPage();
        List<PmsQualityGenerate> list = pmsQualityGenerateService.selectSumData(compCode, deptCode, qDate);
        return getDataTable(list);
    }



    /**
     * 导出质量数据生成列表
     */
    @PreAuthorize("@ss.hasPermi('quality:quality:export')")
    @Log(title = "质量数据生成", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出质量数据生成列表Excel")
    public AjaxResult export(PmsQualityGenerate pmsQualityGenerate) {
        List<PmsQualityGenerate> list = pmsQualityGenerateService.selectPmsQualityGenerateList(pmsQualityGenerate);
        ExcelUtil<PmsQualityGenerate> util = new ExcelUtil<PmsQualityGenerate>(PmsQualityGenerate.class);
        return util.exportExcel(list, "质量数据生成数据");
    }

    /**
     * 获取质量数据生成详细信息
     */
    @PreAuthorize("@ss.hasPermi('quality:quality:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取质量数据生成详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsQualityGenerateService.selectPmsQualityGenerateById(id));
    }

    /**
     * 新增质量数据生成
     */
    @PreAuthorize("@ss.hasPermi('quality:quality:add')")
    @Log(title = "质量数据生成", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增质量数据生成")
    public AjaxResult add(@RequestBody PmsQualityGenerate pmsQualityGenerate) {
        return toAjax(pmsQualityGenerateService.insertPmsQualityGenerate(pmsQualityGenerate));
    }

    /**
     * 修改质量数据生成
     */
    @PreAuthorize("@ss.hasPermi('quality:quality:edit')")
    @Log(title = "质量数据生成", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改质量数据生成")
    public AjaxResult edit(@RequestBody PmsQualityGenerate pmsQualityGenerate) {
        return toAjax(pmsQualityGenerateService.updatePmsQualityGenerate(pmsQualityGenerate));
    }

    /**
     * 删除质量数据生成
     */
    @PreAuthorize("@ss.hasPermi('quality:quality:remove')")
    @Log(title = "质量数据生成", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除质量数据生成")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsQualityGenerateService.deletePmsQualityGenerateByIds(ids));
    }
}
