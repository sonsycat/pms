package com.zjry.pms.bonus.controller;

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
import com.zjry.pms.bonus.domain.PmsEvaluateDataTemp;
import com.zjry.pms.bonus.service.IPmsEvaluateDataTempService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)Controller
 *
 * @author zjry
 * @date 2022-10-25
 */
@RestController
@RequestMapping("/bonus/temp")
@Api(tags={"【指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)】"})
public class PmsEvaluateDataTempController extends BaseController {
    @Autowired
    private IPmsEvaluateDataTempService pmsEvaluateDataTempService;

    /**
     * 查询指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)列表
     */
    @PreAuthorize("@ss.hasPermi('bonus:temp:list')")
    @GetMapping("/list")
    @ApiOperation("查询指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsEvaluateDataTemp pmsEvaluateDataTemp) {
        startPage();
        List<PmsEvaluateDataTemp> list = pmsEvaluateDataTempService.selectPmsEvaluateDataTempList(pmsEvaluateDataTemp);
        return getDataTable(list);
    }

    /**
     * 导出指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)列表
     */
    @PreAuthorize("@ss.hasPermi('bonus:temp:export')")
    @Log(title = "指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)列表Excel")
    public AjaxResult export(PmsEvaluateDataTemp pmsEvaluateDataTemp) {
        List<PmsEvaluateDataTemp> list = pmsEvaluateDataTempService.selectPmsEvaluateDataTempList(pmsEvaluateDataTemp);
        ExcelUtil<PmsEvaluateDataTemp> util = new ExcelUtil<PmsEvaluateDataTemp>(PmsEvaluateDataTemp.class);
        return util.exportExcel(list, "指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)数据");
    }

    /**
     * 获取指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)详细信息
     */
    @PreAuthorize("@ss.hasPermi('bonus:temp:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsEvaluateDataTempService.selectPmsEvaluateDataTempById(id));
    }

    /**
     * 新增指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)
     */
    @PreAuthorize("@ss.hasPermi('bonus:temp:add')")
    @Log(title = "指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)")
    public AjaxResult add(@RequestBody PmsEvaluateDataTemp pmsEvaluateDataTemp) {
        return toAjax(pmsEvaluateDataTempService.insertPmsEvaluateDataTemp(pmsEvaluateDataTemp));
    }

    /**
     * 修改指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)
     */
    @PreAuthorize("@ss.hasPermi('bonus:temp:edit')")
    @Log(title = "指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)")
    public AjaxResult edit(@RequestBody PmsEvaluateDataTemp pmsEvaluateDataTemp) {
        return toAjax(pmsEvaluateDataTempService.updatePmsEvaluateDataTemp(pmsEvaluateDataTemp));
    }

    /**
     * 删除指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)
     */
    @PreAuthorize("@ss.hasPermi('bonus:temp:remove')")
    @Log(title = "指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsEvaluateDataTempService.deletePmsEvaluateDataTempByIds(ids));
    }
}
