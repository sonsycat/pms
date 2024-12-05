package com.zjry.pms.quality.controller;

import java.util.ArrayList;
import java.util.List;

import com.zjry.pms.basic.domain.AssetsBasicPublicVo;
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
import com.zjry.pms.quality.domain.PmsQualityIndexDetail;
import com.zjry.pms.quality.service.IPmsQualityIndexDetailService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 质量考评指标明细Controller
 *
 * @author yzh
 * @date 2022-07-05
 */
@RestController
@RequestMapping("/quality/detail")
@Api(tags={"【质量考评指标明细】"})
public class PmsQualityIndexDetailController extends BaseController {
    @Autowired
    private IPmsQualityIndexDetailService pmsQualityIndexDetailService;

    /**
     * 查询质量考评指标明细列表
     */
    @PreAuthorize("@ss.hasPermi('quality:index:list')")
    @GetMapping("/list")
    @ApiOperation("查询质量考评指标明细列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsQualityIndexDetail pmsQualityIndexDetail) {
        startPage();
        List<PmsQualityIndexDetail> list = pmsQualityIndexDetailService.selectPmsQualityIndexDetailList(pmsQualityIndexDetail);
        return getDataTable(list);
    }
    /**
     * 查询质量考评指标明细列表
     */
    @PreAuthorize("@ss.hasPermi('quality:index:list')")
    @GetMapping("/listForPms")
    @ApiOperation("查询质量考评指标明细列表")

    public AjaxResult listForPms(String indexCode) {
        PmsQualityIndexDetail pms = new PmsQualityIndexDetail();
        pms.setIndexCode(indexCode);
        List<PmsQualityIndexDetail> list = pmsQualityIndexDetailService.selectPmsQualityIndexDetailList(pms);
//        List<AssetsBasicPublicVo>  data = new ArrayList<AssetsBasicPublicVo>();
//        if(list != null && list.size()>0){
//            list.stream().forEach(item->{
//                AssetsBasicPublicVo abpv = new AssetsBasicPublicVo();
//                abpv.setDictValue(item.getEvaCont());
//                abpv.setDictLabel(item.getIndexName());
//                data.add(abpv);
//            });
//        }
        return AjaxResult.success(list);
    }



    /**
     * 导出质量考评指标明细列表
     */
    @PreAuthorize("@ss.hasPermi('quality:index:export')")
    @Log(title = "质量考评指标明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出质量考评指标明细列表Excel")
    public AjaxResult export(PmsQualityIndexDetail pmsQualityIndexDetail) {
        List<PmsQualityIndexDetail> list = pmsQualityIndexDetailService.selectPmsQualityIndexDetailList(pmsQualityIndexDetail);
        ExcelUtil<PmsQualityIndexDetail> util = new ExcelUtil<PmsQualityIndexDetail>(PmsQualityIndexDetail.class);
        return util.exportExcel(list, "质量考评指标明细数据");
    }

    /**
     * 获取质量考评指标明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('quality:index:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取质量考评指标明细详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsQualityIndexDetailService.selectPmsQualityIndexDetailById(id));
    }

    /**
     * 新增质量考评指标明细
     */
    @PreAuthorize("@ss.hasPermi('quality:index:add')")
    @Log(title = "质量考评指标明细", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增质量考评指标明细")
    public AjaxResult add(@RequestBody PmsQualityIndexDetail pmsQualityIndexDetail) {
        return toAjax(pmsQualityIndexDetailService.insertPmsQualityIndexDetail(pmsQualityIndexDetail));
    }

    /**
     * 修改质量考评指标明细
     */
    @PreAuthorize("@ss.hasPermi('quality:index:edit')")
    @Log(title = "质量考评指标明细", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改质量考评指标明细")
    public AjaxResult edit(@RequestBody PmsQualityIndexDetail pmsQualityIndexDetail) {
        return toAjax(pmsQualityIndexDetailService.updatePmsQualityIndexDetail(pmsQualityIndexDetail));
    }

    /**
     * 删除质量考评指标明细
     */
    @PreAuthorize("@ss.hasPermi('quality:index:remove')")
    @Log(title = "质量考评指标明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除质量考评指标明细")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsQualityIndexDetailService.deletePmsQualityIndexDetailByIds(ids));
    }
}
