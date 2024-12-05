package com.zjry.pms.apportionment.controller;

import java.util.List;

import com.zjry.common.utils.StringUtils;
import com.zjry.pms.apportionment.domain.CopyCostAppProject;
import com.zjry.pms.apportionment.service.ICostApportionmentProjectDeptService;
import com.zjry.pms.apportionment.service.ICostApportionmentProjectRatioService;
import com.zjry.pms.project.domain.CostProject;
import com.zjry.pms.project.service.ICostProjectService;
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
import com.zjry.pms.apportionment.domain.CostApportionmentProject;
import com.zjry.pms.apportionment.service.ICostApportionmentProjectService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 分摊方案管理Controller
 *
 * @author mk
 * @date 2022-05-12
 */
@RestController
@RequestMapping("/apportionment/project")
@Api(tags={"【分摊方案管理】"})
public class CostApportionmentProjectController extends BaseController {
    @Autowired
    private ICostApportionmentProjectService costApportionmentProjectService;
    @Autowired
    private ICostApportionmentProjectDeptService costApportionmentProjectDeptService;
    @Autowired
    private ICostApportionmentProjectRatioService costApportionmentProjectRatioService;
    @Autowired
    private ICostProjectService costProjectService;

    /**
     * 查询分摊方案管理列表
     */
    @PreAuthorize("@ss.hasPermi('apportionment:project:list')")
    @GetMapping("/list")
    @ApiOperation("查询分摊方案管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(CostApportionmentProject costApportionmentProject) {
        startPage();
        List<CostApportionmentProject> list = costApportionmentProjectService.selectCostApportionmentProjectList(costApportionmentProject);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('apportionment:project:list')")
    @GetMapping("/selectForProject")
    @ApiOperation("成本新增的pom框接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo selectForProject(CostApportionmentProject costApportionmentProject) {
        costApportionmentProject.setProjectType("2");
        startPage();
        List<CostApportionmentProject> list = costApportionmentProjectService.selectForProject(costApportionmentProject);
        return getDataTable(list);
    }



    /**
     * 导出分摊方案管理列表
     */
    @PreAuthorize("@ss.hasPermi('apportionment:project:export')")
    @Log(title = "分摊方案管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出分摊方案管理列表Excel")
    public AjaxResult export(CostApportionmentProject costApportionmentProject) {
        costApportionmentProject.setProjectType("2");
        List<CostApportionmentProject> list = costApportionmentProjectService.selectCostApportionmentProjectList(costApportionmentProject);
        ExcelUtil<CostApportionmentProject> util = new ExcelUtil<CostApportionmentProject>(CostApportionmentProject.class);
        return util.exportExcel(list, "分摊方案管理数据");
    }

    /**
     * 获取分摊方案管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('apportionment:project:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取分摊方案管理详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(costApportionmentProjectService.selectCostApportionmentProjectById(id));
    }

    /**
     * 新增分摊方案管理
     */
    @PreAuthorize("@ss.hasPermi('apportionment:project:add')")
    @Log(title = "分摊方案管理", businessType = BusinessType.INSERT)
    @PostMapping("/copy")
    @ApiOperation("新增分摊方案管理")
    public AjaxResult copy(@RequestBody CopyCostAppProject costAppProject) {
        try {
            String msg = costApportionmentProjectService.copyProject(costAppProject);
            if(StringUtils.isNotEmpty(msg)){
                return error(msg);
            }
        } catch (Exception e) {
           return error(e.getMessage());
        }
        return success();
    }


    /**
     * 新增分摊方案管理
     */
    @PreAuthorize("@ss.hasPermi('apportionment:project:add')")
    @Log(title = "分摊方案管理", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增分摊方案管理")
    public AjaxResult add(@RequestBody CostApportionmentProject costApportionmentProject) {
        CostApportionmentProject cap = new CostApportionmentProject();
        cap.setProjectCode(costApportionmentProject.getProjectCode());
        cap.setNowDate(costApportionmentProject.getNowDate());
        List<CostApportionmentProject> list = costApportionmentProjectService.selectCostApportionmentProjectList(cap);
        if(list.size()>0){
            return error("该日期下已存在该方案编码");
        }
        return toAjax(costApportionmentProjectService.insertCostApportionmentProject(costApportionmentProject));
    }

    /**
     * 修改分摊方案管理
     */
    @PreAuthorize("@ss.hasPermi('apportionment:project:edit')")
    @Log(title = "分摊方案管理", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改分摊方案管理")
    public AjaxResult edit(@RequestBody CostApportionmentProject costApportionmentProject) {
        CostApportionmentProject cap = new CostApportionmentProject();
        cap.setProjectCode(costApportionmentProject.getProjectCode());
        cap.setNowDate(costApportionmentProject.getNowDate());
        cap.setId(costApportionmentProject.getId());
        List<CostApportionmentProject> list = costApportionmentProjectService.selectCostApportionmentProjectList(cap);
        if(list.size()>0){
            return error("该日期下已存在该方案编码");
        }
        if (!costApportionmentProjectService.selectCostApportionmentProjectById(costApportionmentProject.getId()).getProjectType().equals(costApportionmentProject.getProjectType())) {
            costApportionmentProjectDeptService.deleteCostApportionmentProjectDeptByProjectCode(costApportionmentProject.getProjectInCode());
            costApportionmentProjectRatioService.deleteCostApportionmentProjectRatioByProjectCode(costApportionmentProject.getProjectInCode());
        }
        return toAjax(costApportionmentProjectService.updateCostApportionmentProject(costApportionmentProject));
    }

    /**
     * 删除分摊方案管理
     */
    @PreAuthorize("@ss.hasPermi('apportionment:project:remove')")
    @Log(title = "分摊方案管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除分摊方案管理")
    public AjaxResult remove(@PathVariable Long[] ids) {
        CostApportionmentProject cap =  costApportionmentProjectService.selectCostApportionmentProjectById(ids[0]);
        CostProject cp = new CostProject();
        cp.setProjectFt(cap.getProjectCode());
       List<CostProject> list = costProjectService.selectCostProjectList(cp);
        if(list!=null && list.size()>0){
            return error("该方案已被使用不能删除");
        }
        return toAjax(costApportionmentProjectService.deleteCostApportionmentProjectByIds(ids));
    }
}
