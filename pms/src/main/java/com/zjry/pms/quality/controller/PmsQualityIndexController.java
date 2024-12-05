package com.zjry.pms.quality.controller;

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
import com.zjry.pms.encode.service.IHrEncodeRuleService;
import com.zjry.pms.quality.domain.PmsQualityIndex;
import com.zjry.pms.quality.service.IPmsQualityIndexService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 质量考评指标Controller
 *
 * @author yzh
 * @date 2022-07-05
 */
@RestController
@RequestMapping("/quality/index")
@Api(tags={"【质量考评指标】"})
public class PmsQualityIndexController extends BaseController {
    @Autowired
    private IPmsQualityIndexService pmsQualityIndexService;
    @Autowired
    private IHrEncodeRuleService hrEncodeRuleService;

    /**
     * 查询质量考评指标列表
     */
    @PreAuthorize("@ss.hasPermi('quality:index:list')")
    @GetMapping("/list")
    @ApiOperation("查询质量考评指标列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsQualityIndex pmsQualityIndex) {
        startPage();
        List<PmsQualityIndex> list = pmsQualityIndexService.selectPmsQualityIndexList(pmsQualityIndex);
        return getDataTable(list);
    }

    /**
     * 查询质量考评指标列表
     */
    @PreAuthorize("@ss.hasPermi('quality:index:list')")
    @GetMapping("/listForTemplate")
    @ApiOperation("查询质量考评指标列表")
    public AjaxResult listForTemplate(PmsQualityIndex pmsQualityIndex) {
        PmsQualityIndex pms = new PmsQualityIndex();
        pms.setIndexName(pmsQualityIndex.getIndexName());
        pms.setCompCode(pmsQualityIndex.getCompCode());
        List<PmsQualityIndex> list = pmsQualityIndexService.selectPmsQualityIndexList(pms);
        return AjaxResult.success(list);
    }



    /**
     * 导出质量考评指标列表
     */
    @PreAuthorize("@ss.hasPermi('quality:index:export')")
    @Log(title = "质量考评指标", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出质量考评指标列表Excel")
    public AjaxResult export(PmsQualityIndex pmsQualityIndex) {
        List<PmsQualityIndex> list = pmsQualityIndexService.selectPmsQualityIndexList(pmsQualityIndex);
        ExcelUtil<PmsQualityIndex> util = new ExcelUtil<PmsQualityIndex>(PmsQualityIndex.class);
        return util.exportExcel(list, "质量考评指标数据");
    }

    /**
     * 获取质量考评指标详细信息
     */
    @PreAuthorize("@ss.hasPermi('quality:index:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取质量考评指标详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsQualityIndexService.selectPmsQualityIndexById(id));
    }

    /**
     * 新增质量考评指标
     */
    @PreAuthorize("@ss.hasPermi('quality:index:add')")
    @Log(title = "质量考评指标", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增质量考评指标")
    public AjaxResult add(@RequestBody PmsQualityIndex pmsQualityIndex) {
    	String indexCode = hrEncodeRuleService.generateEncode("pms_quality_index", "INDEX_CODE");
    	pmsQualityIndex.setIndexCode(indexCode);
        return toAjax(pmsQualityIndexService.insertPmsQualityIndex(pmsQualityIndex));
    }

    /**
     * 修改质量考评指标
     */
    @PreAuthorize("@ss.hasPermi('quality:index:edit')")
    @Log(title = "质量考评指标", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改质量考评指标")
    public AjaxResult edit(@RequestBody PmsQualityIndex pmsQualityIndex) {
        return toAjax(pmsQualityIndexService.updatePmsQualityIndex(pmsQualityIndex));
    }

    /**
     * 删除质量考评指标
     */
    @PreAuthorize("@ss.hasPermi('quality:index:remove')")
    @Log(title = "质量考评指标", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除质量考评指标")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsQualityIndexService.deletePmsQualityIndexByIds(ids));
    }
}
