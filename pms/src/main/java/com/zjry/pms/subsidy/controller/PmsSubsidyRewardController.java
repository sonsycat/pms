package com.zjry.pms.subsidy.controller;

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
import com.zjry.pms.subsidy.domain.PmsSubsidyReward;
import com.zjry.pms.subsidy.service.IPmsSubsidyRewardService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 奖惩项目维护Controller
 *
 * @author maozt
 * @date 2022-08-15
 */
@RestController
@RequestMapping("/subsidy/reward")
@Api(tags={"【奖惩项目维护】"})
public class PmsSubsidyRewardController extends BaseController {
    @Autowired
    private IPmsSubsidyRewardService pmsSubsidyRewardService;

    /**
     * 查询奖惩项目维护列表
     */
    @PreAuthorize("@ss.hasPermi('subsidy:reward:list')")
    @GetMapping("/list")
    @ApiOperation("查询奖惩项目维护列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsSubsidyReward pmsSubsidyReward) {
        startPage();
        List<PmsSubsidyReward> list = pmsSubsidyRewardService.selectPmsSubsidyRewardList(pmsSubsidyReward);
        return getDataTable(list);
    }

    /**
     * 导出奖惩项目维护列表
     */
    @PreAuthorize("@ss.hasPermi('subsidy:reward:export')")
    @Log(title = "奖惩项目维护", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出奖惩项目维护列表Excel")
    public AjaxResult export(PmsSubsidyReward pmsSubsidyReward) {
        List<PmsSubsidyReward> list = pmsSubsidyRewardService.selectPmsSubsidyRewardList(pmsSubsidyReward);
        ExcelUtil<PmsSubsidyReward> util = new ExcelUtil<PmsSubsidyReward>(PmsSubsidyReward.class);
        return util.exportExcel(list, "奖惩项目维护数据");
    }

    /**
     * 获取奖惩项目维护详细信息
     */
    @PreAuthorize("@ss.hasPermi('subsidy:reward:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取奖惩项目维护详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsSubsidyRewardService.selectPmsSubsidyRewardById(id));
    }

    /**
     * 新增奖惩项目维护
     */
    @PreAuthorize("@ss.hasPermi('subsidy:reward:add')")
    @Log(title = "奖惩项目维护", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增奖惩项目维护")
    public AjaxResult add(@RequestBody PmsSubsidyReward pmsSubsidyReward) {
        try {
            return toAjax(pmsSubsidyRewardService.insertPmsSubsidyReward(pmsSubsidyReward));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 修改奖惩项目维护
     */
    @PreAuthorize("@ss.hasPermi('subsidy:reward:edit')")
    @Log(title = "奖惩项目维护", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改奖惩项目维护")
    public AjaxResult edit(@RequestBody PmsSubsidyReward pmsSubsidyReward) {
        try {
            return toAjax(pmsSubsidyRewardService.updatePmsSubsidyReward(pmsSubsidyReward));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 删除奖惩项目维护
     */
    @PreAuthorize("@ss.hasPermi('subsidy:reward:remove')")
    @Log(title = "奖惩项目维护", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除奖惩项目维护")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsSubsidyRewardService.deletePmsSubsidyRewardByIds(ids));
    }
}
