package com.zjry.pms.apportionment.controller;

import java.util.List;

import com.zjry.pms.apportionment.domain.DeptCostProject;
import io.swagger.annotations.*;
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
import com.zjry.pms.apportionment.domain.CostApportionmentProjectDept;
import com.zjry.pms.apportionment.service.ICostApportionmentProjectDeptService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 分摊方案管理科室设置Controller
 *
 * @author mk
 * @date 2022-05-13
 */
@RestController
@RequestMapping("/apportionment/dept")
@Api(tags={"【分摊方案管理科室设置】"})
public class CostApportionmentProjectDeptController extends BaseController {
    @Autowired
    private ICostApportionmentProjectDeptService costApportionmentProjectDeptService;

    /**
     * 查询分摊方案管理科室设置列表
     */
    @PreAuthorize("@ss.hasPermi('apportionment:project:list')")
    @GetMapping("/list")
    @ApiOperation("查询分摊方案管理科室设置列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(CostApportionmentProjectDept costApportionmentProjectDept) {
        startPage();
        List<CostApportionmentProjectDept> list = costApportionmentProjectDeptService.selectCostApportionmentProjectDeptList(costApportionmentProjectDept);
        return getDataTable(list);
    }

    /**
     * 查询科室设置列表
     * isSProject 1 已选科室 2 待选科室
     * storeCode 关联仓库编码
     */
    @PreAuthorize("@ss.hasPermi('apportionment:project:list')")
    @GetMapping("/listProjectDept")
    @ApiOperation("根据用户查询关联仓库管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo listProjectDept(CostApportionmentProjectDept costApportionmentProjectDept) {
        startPage();
        List<CostApportionmentProjectDept> list = costApportionmentProjectDeptService.getDeptProject(costApportionmentProjectDept);
        return getDataTable(list);
    }

    /**
     * 查询科室设置列表
     * isSProject 1 已选科室 2 待选科室
     * storeCode 关联仓库编码
     */
    @PreAuthorize("@ss.hasPermi('apportionment:project:list')")
    @GetMapping("/listProjectDept2")
    @ApiOperation("根据用户查询关联仓库管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo listProjectDept2(CostApportionmentProjectDept costApportionmentProjectDept) {
        startPage();
        List<CostApportionmentProjectDept> list = costApportionmentProjectDeptService.getDeptProject2(costApportionmentProjectDept);
        return getDataTable(list);
    }

    /**
     * 导出分摊方案管理科室设置列表
     */
    @PreAuthorize("@ss.hasPermi('apportionment:project:export')")
    @Log(title = "分摊方案管理科室设置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出分摊方案管理科室设置列表Excel")
    public AjaxResult export(CostApportionmentProjectDept costApportionmentProjectDept) {
        List<CostApportionmentProjectDept> list = costApportionmentProjectDeptService.selectCostApportionmentProjectDeptList(costApportionmentProjectDept);
        ExcelUtil<CostApportionmentProjectDept> util = new ExcelUtil<CostApportionmentProjectDept>(CostApportionmentProjectDept.class);
        return util.exportExcel(list, "分摊方案管理科室设置数据");
    }

    /**
     * 获取分摊方案管理科室设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('apportionment:project:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取分摊方案管理科室设置详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(costApportionmentProjectDeptService.selectCostApportionmentProjectDeptById(id));
    }

    /**
     * 新增分摊方案管理科室设置
     */
    @PreAuthorize("@ss.hasPermi('apportionment:project:add')")
    @Log(title = "分摊方案管理科室设置", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增分摊方案管理科室设置")
    public AjaxResult add(@RequestBody DeptCostProject deptCostProject) {
        costApportionmentProjectDeptService.insertCostDept(deptCostProject);
        return success();
    }

    /**
     * 新增科室设置
     */
    @PreAuthorize("@ss.hasPermi('apportionment:project:add')")
    @Log(title = "新增科室设置", businessType = BusinessType.UPDATE)
    @PostMapping("/addProjectDept")
    @ApiOperation("新增科室设置")
    public AjaxResult addProjectDept(@RequestBody CostApportionmentProjectDept costApportionmentProjectDept) {
        String projectCode = costApportionmentProjectDept.getProjectCode();
        if (StringUtils.isNotEmpty(projectCode)){
            costApportionmentProjectDeptService.deleteCostApportionmentProjectDeptByProjectCode(projectCode);
        }
        costApportionmentProjectDeptService.insertCostApportionmentProjectDeptPl(costApportionmentProjectDept);
        return success();
    }

    /**
     * 修改分摊方案管理科室设置
     */
    @PreAuthorize("@ss.hasPermi('apportionment:project:edit')")
    @Log(title = "分摊方案管理科室设置", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改分摊方案管理科室设置")
    public AjaxResult edit(@RequestBody CostApportionmentProjectDept costApportionmentProjectDept) {
        return toAjax(costApportionmentProjectDeptService.updateCostApportionmentProjectDept(costApportionmentProjectDept));
    }

    /**
     * 删除分摊方案管理科室设置
     */
    @PreAuthorize("@ss.hasPermi('apportionment:project:remove')")
    @Log(title = "分摊方案管理科室设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除分摊方案管理科室设置")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(costApportionmentProjectDeptService.deleteCostApportionmentProjectDeptByIds(ids));
    }
}
