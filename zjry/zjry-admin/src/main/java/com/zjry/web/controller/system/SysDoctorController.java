package com.zjry.web.controller.system;

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
import com.zjry.system.domain.SysDoctor;
import com.zjry.system.service.ISysDoctorService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 医生信息Controller
 *
 * @author mingc
 * @date 2021-06-18
 */
@RestController
@RequestMapping("/system/doctor")
@Api(tags={"【医生信息】"})
public class SysDoctorController extends BaseController
{
    @Autowired
    private ISysDoctorService sysDoctorService;

/**
 * 查询医生信息列表
 */
@PreAuthorize("@ss.hasPermi('system:doctor:list')")
@GetMapping("/list")
@ApiOperation("查询医生信息列表")
        @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(SysDoctor sysDoctor)
    {
        startPage();
        List<SysDoctor> list = sysDoctorService.selectSysDoctorList(sysDoctor);
        return getDataTable(list);
    }
    
    /**
     * 导出医生信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:doctor:export')")
    @Log(title = "医生信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出医生信息列表Excel")
    public AjaxResult export(SysDoctor sysDoctor)
    {
        List<SysDoctor> list = sysDoctorService.selectSysDoctorList(sysDoctor);
        ExcelUtil<SysDoctor> util = new ExcelUtil<SysDoctor>(SysDoctor.class);
        return util.exportExcel(list, "医生信息数据");
    }

    /**
     * 获取医生信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:doctor:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取医生信息详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysDoctorService.selectSysDoctorById(id));
    }

    /**
     * 新增医生信息
     */
    @PreAuthorize("@ss.hasPermi('system:doctor:add')")
    @Log(title = "医生信息", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增医生信息")
    public AjaxResult add(@RequestBody SysDoctor sysDoctor)
    {
        return toAjax(sysDoctorService.insertSysDoctor(sysDoctor));
    }

    /**
     * 修改医生信息
     */
    @PreAuthorize("@ss.hasPermi('system:doctor:edit')")
    @Log(title = "医生信息", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改医生信息")
    public AjaxResult edit(@RequestBody SysDoctor sysDoctor)
    {
        return toAjax(sysDoctorService.updateSysDoctor(sysDoctor));
    }

    /**
     * 删除医生信息
     */
    @PreAuthorize("@ss.hasPermi('system:doctor:remove')")
    @Log(title = "医生信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除医生信息")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysDoctorService.deleteSysDoctorByIds(ids));
    }
}
