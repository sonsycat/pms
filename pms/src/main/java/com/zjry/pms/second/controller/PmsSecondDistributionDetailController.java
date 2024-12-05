package com.zjry.pms.second.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.zjry.pms.second.domain.PmsSecondDistribution;
import io.swagger.annotations.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.zjry.pms.second.domain.PmsSecondDistributionDetail;
import com.zjry.pms.second.service.IPmsSecondDistributionDetailService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 二次分配指标集明细Controller
 *
 * @author maozt
 * @date 2022-09-26
 */
@RestController
@RequestMapping("/second/distributionDetail")
@Api(tags={"【二次分配指标集明细】"})
public class PmsSecondDistributionDetailController extends BaseController {
    @Autowired
    private IPmsSecondDistributionDetailService pmsSecondDistributionDetailService;

    /**
     * 查询二次分配指标集明细列表
     */
    @PreAuthorize("@ss.hasPermi('second:distribution:list')")
    @GetMapping("/list")
    @ApiOperation("查询二次分配指标集明细列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsSecondDistributionDetail pmsSecondDistributionDetail) {
        startPage();
        List<PmsSecondDistributionDetail> list = pmsSecondDistributionDetailService.selectPmsSecondDistributionDetailList(pmsSecondDistributionDetail);
        return getDataTable(list);
    }

    /**
     * 查询二次分配指标集明细列表
     */
    @PreAuthorize("@ss.hasPermi('second:distribution:list')")
    @GetMapping("/listDistributionEvaluate")
    @ApiOperation("查询二次分配指标集明细列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo listEvaluate(PmsSecondDistributionDetail pmsSecondDistributionDetail) {
        startPage();
        List<PmsSecondDistributionDetail> list = pmsSecondDistributionDetailService.getDistributionEvaluate(pmsSecondDistributionDetail);
        return getDataTable(list);
    }

    /**
     * 新增指标
     */
    @PreAuthorize("@ss.hasPermi('second:distribution:add')")
    @Log(title = "新增指标", businessType = BusinessType.UPDATE)
    @PostMapping("/addDistributionEvaluate")
    @ApiOperation("新增指标")
    public AjaxResult addDistributionEvaluate(@RequestBody PmsSecondDistribution pmsSecondDistribution) {
        String disCode = pmsSecondDistribution.getDisCode();
        if (StringUtils.isNotEmpty(disCode)){
            pmsSecondDistributionDetailService.deletePmsSecondDistributionDetailByDisCode(disCode);
        }
        List<PmsSecondDistributionDetail> pmsSecondDistributionDetails = pmsSecondDistribution.getPmsSecondDistributionDetails();
        if (CollectionUtils.isNotEmpty(pmsSecondDistributionDetails)) {
            if(pmsSecondDistributionDetails.size() == 1){
                pmsSecondDistributionDetails.get(0).preInsert();
                pmsSecondDistributionDetails.get(0).setCompCode(pmsSecondDistribution.getCompCode());
                pmsSecondDistributionDetails.get(0).setDisCode(disCode);
                pmsSecondDistributionDetails.get(0).setWeightRatio(BigDecimal.ONE);
                pmsSecondDistributionDetailService.insertPmsSecondDistributionDetail(pmsSecondDistributionDetails.get(0));
            }else{
                BigDecimal weightRatio = null;
                BigDecimal total = pmsSecondDistributionDetails.stream().map(i -> new BigDecimal(StringUtils.isNotBlank(i.getWeights())?i.getWeights():"0")).reduce(BigDecimal.ZERO, BigDecimal::add);
                if(total.compareTo(BigDecimal.ZERO) == 0){
                    weightRatio = BigDecimal.ONE.divide(new BigDecimal(pmsSecondDistributionDetails.size()), 4, RoundingMode.HALF_UP);
                }
                for (PmsSecondDistributionDetail pmsSecondDistributionDetail : pmsSecondDistributionDetails) {
                    pmsSecondDistributionDetail.preInsert();
                    pmsSecondDistributionDetail.setCompCode(pmsSecondDistribution.getCompCode());
                    pmsSecondDistributionDetail.setDisCode(disCode);
                    pmsSecondDistributionDetail.setWeightRatio(weightRatio!=null?weightRatio:(total.compareTo(BigDecimal.ZERO)==1?(StringUtils.isNotBlank(pmsSecondDistributionDetail.getWeights())?new BigDecimal(pmsSecondDistributionDetail.getWeights()).divide(total, 4, RoundingMode.HALF_UP):BigDecimal.ZERO):BigDecimal.ZERO));
                    pmsSecondDistributionDetailService.insertPmsSecondDistributionDetail(pmsSecondDistributionDetail);
                }
            }
        }
        return success();
    }

    /**
     * 导出二次分配指标集明细列表
     */
    @PreAuthorize("@ss.hasPermi('second:distribution:export')")
    @Log(title = "二次分配指标集明细", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出二次分配指标集明细列表Excel")
    public AjaxResult export(PmsSecondDistributionDetail pmsSecondDistributionDetail) {
        List<PmsSecondDistributionDetail> list = pmsSecondDistributionDetailService.selectPmsSecondDistributionDetailList(pmsSecondDistributionDetail);
        ExcelUtil<PmsSecondDistributionDetail> util = new ExcelUtil<PmsSecondDistributionDetail>(PmsSecondDistributionDetail.class);
        return util.exportExcel(list, "二次分配指标集明细数据");
    }

    /**
     * 获取二次分配指标集明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('second:distribution:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取二次分配指标集明细详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsSecondDistributionDetailService.selectPmsSecondDistributionDetailById(id));
    }

    /**
     * 新增二次分配指标集明细
     */
    @PreAuthorize("@ss.hasPermi('second:distribution:add')")
    @Log(title = "二次分配指标集明细", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增二次分配指标集明细")
    public AjaxResult add(@RequestBody PmsSecondDistributionDetail pmsSecondDistributionDetail) {
        return toAjax(pmsSecondDistributionDetailService.insertPmsSecondDistributionDetail(pmsSecondDistributionDetail));
    }

    /**
     * 修改二次分配指标集明细
     */
    @PreAuthorize("@ss.hasPermi('second:distribution:edit')")
    @Log(title = "二次分配指标集明细", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改二次分配指标集明细")
    public AjaxResult edit(@RequestBody PmsSecondDistributionDetail pmsSecondDistributionDetail) {
        return toAjax(pmsSecondDistributionDetailService.updatePmsSecondDistributionDetail(pmsSecondDistributionDetail));
    }

    /**
     * 删除二次分配指标集明细
     */
    @PreAuthorize("@ss.hasPermi('second:distribution:remove')")
    @Log(title = "二次分配指标集明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除二次分配指标集明细")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsSecondDistributionDetailService.deletePmsSecondDistributionDetailByIds(ids));
    }
}
