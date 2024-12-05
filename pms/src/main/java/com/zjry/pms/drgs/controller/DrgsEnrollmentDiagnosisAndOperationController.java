package com.zjry.pms.drgs.controller;

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
import com.zjry.pms.drgs.domain.DrgsEnrollmentDiagnosisAndOperation;
import com.zjry.pms.drgs.service.IDrgsEnrollmentDiagnosisAndOperationService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * ADRG入组诊断及操作Controller
 *
 * @author zjry
 * @date 2023-03-20
 */
@RestController
@RequestMapping("/drgs/enrollmentDiagnosisAndOperation")
@Api(tags={"【ADRG入组诊断及操作】"})
public class DrgsEnrollmentDiagnosisAndOperationController extends BaseController {
    @Autowired
    private IDrgsEnrollmentDiagnosisAndOperationService drgsEnrollmentDiagnosisAndOperationService;

    /**
     * 查询ADRG入组诊断及操作列表
     */
    @PreAuthorize("@ss.hasPermi('drgs:enrollmentDiagnosisAndOperation:list')")
    @GetMapping("/list")
    @ApiOperation("查询ADRG入组诊断及操作列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(DrgsEnrollmentDiagnosisAndOperation drgsEnrollmentDiagnosisAndOperation) {
        startPage();
        List<DrgsEnrollmentDiagnosisAndOperation> list = drgsEnrollmentDiagnosisAndOperationService.selectDrgsEnrollmentDiagnosisAndOperationList(drgsEnrollmentDiagnosisAndOperation);
        return getDataTable(list);
    }

    /**
     * 导出ADRG入组诊断及操作列表
     */
    @PreAuthorize("@ss.hasPermi('drgs:enrollmentDiagnosisAndOperation:export')")
    @Log(title = "ADRG入组诊断及操作", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出ADRG入组诊断及操作列表Excel")
    public AjaxResult export(DrgsEnrollmentDiagnosisAndOperation drgsEnrollmentDiagnosisAndOperation) {
        List<DrgsEnrollmentDiagnosisAndOperation> list = drgsEnrollmentDiagnosisAndOperationService.selectDrgsEnrollmentDiagnosisAndOperationList(drgsEnrollmentDiagnosisAndOperation);
        ExcelUtil<DrgsEnrollmentDiagnosisAndOperation> util = new ExcelUtil<DrgsEnrollmentDiagnosisAndOperation>(DrgsEnrollmentDiagnosisAndOperation.class);
        return util.exportExcel(list, "ADRG入组诊断及操作数据");
    }

    /**
     * 获取ADRG入组诊断及操作详细信息
     */
    @PreAuthorize("@ss.hasPermi('drgs:enrollmentDiagnosisAndOperation:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取ADRG入组诊断及操作详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(drgsEnrollmentDiagnosisAndOperationService.selectDrgsEnrollmentDiagnosisAndOperationById(id));
    }

    /**
     * 新增ADRG入组诊断及操作
     */
    @PreAuthorize("@ss.hasPermi('drgs:enrollmentDiagnosisAndOperation:add')")
    @Log(title = "ADRG入组诊断及操作", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增ADRG入组诊断及操作")
    public AjaxResult add(@RequestBody DrgsEnrollmentDiagnosisAndOperation drgsEnrollmentDiagnosisAndOperation) {
        return toAjax(drgsEnrollmentDiagnosisAndOperationService.insertDrgsEnrollmentDiagnosisAndOperation(drgsEnrollmentDiagnosisAndOperation));
    }

    /**
     * 修改ADRG入组诊断及操作
     */
    @PreAuthorize("@ss.hasPermi('drgs:enrollmentDiagnosisAndOperation:edit')")
    @Log(title = "ADRG入组诊断及操作", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改ADRG入组诊断及操作")
    public AjaxResult edit(@RequestBody DrgsEnrollmentDiagnosisAndOperation drgsEnrollmentDiagnosisAndOperation) {
        return toAjax(drgsEnrollmentDiagnosisAndOperationService.updateDrgsEnrollmentDiagnosisAndOperation(drgsEnrollmentDiagnosisAndOperation));
    }

    /**
     * 删除ADRG入组诊断及操作
     */
    @PreAuthorize("@ss.hasPermi('drgs:enrollmentDiagnosisAndOperation:remove')")
    @Log(title = "ADRG入组诊断及操作", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除ADRG入组诊断及操作")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(drgsEnrollmentDiagnosisAndOperationService.deleteDrgsEnrollmentDiagnosisAndOperationByIds(ids));
    }
}
