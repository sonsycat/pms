package com.zjry.pms.evaluate.controller;

import java.util.List;

import com.zjry.pms.common.Constants;
import com.zjry.pms.common.utils.UserUtils;
import com.zjry.pms.evaluate.domain.PmsEvaluateIndexBonusDto;
import io.swagger.annotations.*;
import org.apache.commons.collections4.CollectionUtils;
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
import com.zjry.pms.evaluate.domain.PmsEvaluateIndexBonus;
import com.zjry.pms.evaluate.service.IPmsEvaluateIndexBonusService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 奖金指标Controller
 *
 * @author zjry
 * @date 2022-11-08
 */
@RestController
@RequestMapping("/evaluate/bonusIndex")
@Api(tags={"【奖金指标】"})
public class PmsEvaluateIndexBonusController extends BaseController {
    @Autowired
    private IPmsEvaluateIndexBonusService pmsEvaluateIndexBonusService;

    /**
     * 查询奖金指标列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:bonusIndex:list')")
    @GetMapping("/list")
    @ApiOperation("查询奖金指标列表")
    public AjaxResult list(PmsEvaluateIndexBonus pmsEvaluateIndexBonus) {
        pmsEvaluateIndexBonus.setCompCode(UserUtils.getSysUser().getDeptId());
        pmsEvaluateIndexBonus.setOrgType(Constants.ORG_TYPE_2);
        List<PmsEvaluateIndexBonus> list = pmsEvaluateIndexBonusService.selectPmsEvaluateIndexBonusList(pmsEvaluateIndexBonus);
        return AjaxResult.success(list);
    }

    /**
     * 获取奖金指标详细信息
     */
    @PreAuthorize("@ss.hasPermi('evaluate:bonusIndex:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取奖金指标详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsEvaluateIndexBonusService.selectPmsEvaluateIndexBonusById(id));
    }

    /**
     * 新增奖金指标
     */
    @PreAuthorize("@ss.hasPermi('evaluate:bonusIndex:add')")
    @Log(title = "奖金指标", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增奖金指标")
    public AjaxResult add(@RequestBody PmsEvaluateIndexBonusDto pmsEvaluateIndexBonusDto) {
        return toAjax(pmsEvaluateIndexBonusService.saveBatch(pmsEvaluateIndexBonusDto));
    }

    /**
     * 修改奖金指标
     */
    @PreAuthorize("@ss.hasPermi('evaluate:bonusIndex:edit')")
    @Log(title = "奖金指标", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改奖金指标")
    public AjaxResult edit(@RequestBody PmsEvaluateIndexBonus pmsEvaluateIndexBonus) {
        return toAjax(pmsEvaluateIndexBonusService.updatePmsEvaluateIndexBonus(pmsEvaluateIndexBonus));
    }

    /**
     * 删除奖金指标
     */
    @PreAuthorize("@ss.hasPermi('evaluate:bonusIndex:remove')")
    @Log(title = "奖金指标", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除奖金指标")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsEvaluateIndexBonusService.deletePmsEvaluateIndexBonusByIds(ids));
    }
}
