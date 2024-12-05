package com.zjry.pms.evaluate.controller;

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
import com.zjry.pms.evaluate.domain.PmsBalancedScorecard;
import com.zjry.pms.evaluate.service.IPmsBalancedScorecardService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 平衡记分卡Controller
 *
 * @author zjry
 * @date 2022-10-19
 */
@RestController
@RequestMapping("/evaluate/scorecard")
@Api(tags={"【平衡记分卡】"})
public class PmsBalancedScorecardController extends BaseController {
    @Autowired
    private IPmsBalancedScorecardService pmsBalancedScorecardService;

    /**
     * 查询平衡记分卡列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:scorecard:list')")
    @GetMapping("/list")
    @ApiOperation("查询平衡记分卡列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsBalancedScorecard pmsBalancedScorecard) {
        startPage();
        List<PmsBalancedScorecard> list = pmsBalancedScorecardService.selectPmsBalancedScorecardList(pmsBalancedScorecard);
        return getDataTable(list);
    }

    /**
     * 导出平衡记分卡列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:scorecard:export')")
    @Log(title = "平衡记分卡", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出平衡记分卡列表Excel")
    public AjaxResult export(PmsBalancedScorecard pmsBalancedScorecard) {
        List<PmsBalancedScorecard> list = pmsBalancedScorecardService.selectPmsBalancedScorecardList(pmsBalancedScorecard);
        ExcelUtil<PmsBalancedScorecard> util = new ExcelUtil<PmsBalancedScorecard>(PmsBalancedScorecard.class);
        return util.exportExcel(list, "平衡记分卡数据");
    }

    /**
     * 获取平衡记分卡详细信息
     */
    @PreAuthorize("@ss.hasPermi('evaluate:scorecard:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取平衡记分卡详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsBalancedScorecardService.selectPmsBalancedScorecardById(id));
    }

    /**
     * 新增平衡记分卡
     */
    @PreAuthorize("@ss.hasPermi('evaluate:scorecard:add')")
    @Log(title = "平衡记分卡", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增平衡记分卡")
    public AjaxResult add(@RequestBody PmsBalancedScorecard pmsBalancedScorecard) {
        return toAjax(pmsBalancedScorecardService.insertPmsBalancedScorecard(pmsBalancedScorecard));
    }

    /**
     * 修改平衡记分卡
     */
    @PreAuthorize("@ss.hasPermi('evaluate:scorecard:edit')")
    @Log(title = "平衡记分卡", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改平衡记分卡")
    public AjaxResult edit(@RequestBody PmsBalancedScorecard pmsBalancedScorecard) {
        return toAjax(pmsBalancedScorecardService.updatePmsBalancedScorecard(pmsBalancedScorecard));
    }

    /**
     * 删除平衡记分卡
     */
    @PreAuthorize("@ss.hasPermi('evaluate:scorecard:remove')")
    @Log(title = "平衡记分卡", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除平衡记分卡")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsBalancedScorecardService.deletePmsBalancedScorecardByIds(ids));
    }
}
