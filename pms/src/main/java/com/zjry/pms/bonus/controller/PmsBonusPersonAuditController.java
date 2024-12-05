package com.zjry.pms.bonus.controller;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjry.pms.bonus.domain.PmsBonusPersonAuditDto;
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
import com.zjry.pms.bonus.domain.PmsBonusPersonAudit;
import com.zjry.pms.bonus.service.IPmsBonusPersonAuditService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 奖金人员审核Controller
 *
 * @author zjry
 * @date 2022-10-25
 */
@RestController
@RequestMapping("/bonus/audit")
@Api(tags = {"【奖金人员审核】"})
public class PmsBonusPersonAuditController extends BaseController {

    @Autowired
    private IPmsBonusPersonAuditService pmsBonusPersonAuditService;

    /**
     * 查询奖金人员审核列表
     */
    @PreAuthorize("@ss.hasPermi('bonus:audit:list')")
    @GetMapping("/list")
    @ApiOperation("查询奖金人员审核列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsBonusPersonAudit pmsBonusPersonAudit) {
        startPage();
        List<PmsBonusPersonAudit> list = pmsBonusPersonAuditService.selectPmsBonusPersonAuditList(pmsBonusPersonAudit);
        return getDataTable(list);
    }

    /**
     * 导出奖金人员审核列表
     */
    @PreAuthorize("@ss.hasPermi('bonus:audit:export')")
    @Log(title = "奖金人员审核", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出奖金人员审核列表Excel")
    public AjaxResult export(PmsBonusPersonAudit pmsBonusPersonAudit) {
        List<PmsBonusPersonAudit> list = pmsBonusPersonAuditService.selectPmsBonusPersonAuditList(pmsBonusPersonAudit);
        ExcelUtil<PmsBonusPersonAudit> util = new ExcelUtil<PmsBonusPersonAudit>(PmsBonusPersonAudit.class);
        return util.exportExcel(list, "奖金人员审核数据");
    }

    /**
     * 获取奖金人员审核详细信息
     */
    @PreAuthorize("@ss.hasPermi('bonus:audit:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取奖金人员审核详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsBonusPersonAuditService.selectPmsBonusPersonAuditById(id));
    }

    /**
     * 新增奖金人员审核
     */
    @PreAuthorize("@ss.hasPermi('bonus:audit:add')")
    @Log(title = "奖金人员审核", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增奖金人员审核")
    public AjaxResult add(@RequestBody PmsBonusPersonAuditDto param) {
        return pmsBonusPersonAuditService.insertPmsBonusPersonAudit(param);
    }

    /**
     * 修改奖金人员审核
     */
    @PreAuthorize("@ss.hasPermi('bonus:audit:edit')")
    @Log(title = "奖金人员审核", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改奖金人员审核")
    public AjaxResult edit(@RequestBody PmsBonusPersonAudit pmsBonusPersonAudit) {
        return toAjax(pmsBonusPersonAuditService.updatePmsBonusPersonAudit(pmsBonusPersonAudit));
    }

    /**
     * 删除奖金人员审核
     */
    @PreAuthorize("@ss.hasPermi('bonus:audit:remove')")
    @Log(title = "奖金人员审核", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除奖金人员审核")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsBonusPersonAuditService.deletePmsBonusPersonAuditByIds(ids));
    }

    /**
     * 新增奖金人员审核
     */
    @PreAuthorize("@ss.hasPermi('bonus:audit:audit')")
    @Log(title = "奖金人员审核", businessType = BusinessType.UPDATE)
    @PostMapping("/audit")
    @ApiOperation("奖金人员执行审核")
    public AjaxResult audit(@RequestBody PmsBonusPersonAuditDto param) {
        return pmsBonusPersonAuditService.audit(param);
    }

    /**
     * 获取奖金人员流程信息
     */
    @GetMapping("/getProcessId")
    @ApiOperation("奖金人员执行审核")
    public AjaxResult getProcessId(PmsBonusPersonAudit param) {
        QueryWrapper<PmsBonusPersonAudit> wrapper = new QueryWrapper();
        wrapper.lambda().select(PmsBonusPersonAudit::getProcedureId)
                .eq(PmsBonusPersonAudit::getDeptCode,param.getDeptCode())
                .eq(PmsBonusPersonAudit::getNowDate, param.getNowDate()).last("LIMIT 1");
        return AjaxResult.success(pmsBonusPersonAuditService.getOne(wrapper));
    }
}
