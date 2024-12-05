package com.zjry.pms.apportionment.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.zjry.pms.apportionment.domain.CostApportionmentDictionary;
import com.zjry.pms.apportionment.domain.CostApportionmentProject;
import com.zjry.pms.apportionment.service.ICostApportionmentDictionaryService;
import com.zjry.pms.apportionment.service.ICostApportionmentProjectService;
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
import com.zjry.pms.apportionment.domain.CostApportionmentProjectRatio;
import com.zjry.pms.apportionment.service.ICostApportionmentProjectRatioService;
import com.zjry.common.utils.poi.ExcelUtil;

/**
 * 分摊方案管理比例设置Controller
 *
 * @author mk
 * @date 2022-05-13
 */
@RestController
@RequestMapping("/apportionment/ratio")
@Api(tags={"【分摊方案管理比例设置】"})
public class CostApportionmentProjectRatioController extends BaseController {
    @Autowired
    private ICostApportionmentProjectRatioService costApportionmentProjectRatioService;
    @Autowired
    private ICostApportionmentDictionaryService costApportionmentDictionaryService;
    @Autowired
    private ICostApportionmentProjectService costApportionmentProjectService;

    /**
     * 查询分摊方案管理比例设置列表
     */
    @PreAuthorize("@ss.hasPermi('apportionment:project:list')")
    @GetMapping("/list")
    @ApiOperation("查询分摊方案管理比例设置列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public AjaxResult list(CostApportionmentProjectRatio costApportionmentProjectRatio) {
        List<CostApportionmentProjectRatio> list = costApportionmentProjectRatioService.selectCostApportionmentProjectRatioList(costApportionmentProjectRatio);
        String projectCode = costApportionmentProjectRatio.getProjectCode();
        List<String> ratioCodes = list.stream().map(CostApportionmentProjectRatio::getRatioCode).collect(Collectors.toList());
        CostApportionmentDictionary costApportionmentDictionary = new CostApportionmentDictionary();
        costApportionmentDictionary.setRatioType(costApportionmentProjectRatio.getRatioType());
        costApportionmentDictionary.setCompCode(costApportionmentProjectRatio.getCompCode());
        List<CostApportionmentDictionary> list2 = costApportionmentDictionaryService.selectCostApportionmentDictionaryList(costApportionmentDictionary);
        for (CostApportionmentDictionary apportionmentDictionary : list2) {
            if (!ratioCodes.contains(apportionmentDictionary.getRatioCode())) {
                costApportionmentProjectRatio = new CostApportionmentProjectRatio();
                costApportionmentProjectRatio.setProjectCode(projectCode);
                costApportionmentProjectRatio.setRatioCode(apportionmentDictionary.getRatioCode());
                costApportionmentProjectRatio.setRatioName(apportionmentDictionary.getRatioName());
                list.add(costApportionmentProjectRatio);
            }
        }
        costApportionmentProjectRatio = new CostApportionmentProjectRatio();
        costApportionmentProjectRatio.setProjectCode(projectCode);
        costApportionmentProjectRatio.setCostDeptInfos(list);
        return AjaxResult.success(costApportionmentProjectRatio);
    }

    /**
     * 导出分摊方案管理比例设置列表
     */
    @PreAuthorize("@ss.hasPermi('apportionment:project:export')")
    @Log(title = "分摊方案管理比例设置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出分摊方案管理比例设置列表Excel")
    public AjaxResult export(CostApportionmentProjectRatio costApportionmentProjectRatio) {
        List<CostApportionmentProjectRatio> list = costApportionmentProjectRatioService.selectCostApportionmentProjectRatioList(costApportionmentProjectRatio);
        ExcelUtil<CostApportionmentProjectRatio> util = new ExcelUtil<CostApportionmentProjectRatio>(CostApportionmentProjectRatio.class);
        return util.exportExcel(list, "分摊方案管理比例设置数据");
    }

    /**
     * 获取分摊方案管理比例设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('apportionment:project:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取分摊方案管理比例设置详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(costApportionmentProjectRatioService.selectCostApportionmentProjectRatioById(id));
    }

    /**
     * 新增分摊方案管理比例设置
     */
    @PreAuthorize("@ss.hasPermi('apportionment:project:add')")
    @Log(title = "分摊方案管理比例设置", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增分摊方案管理比例设置")
    public AjaxResult add(@RequestBody CostApportionmentProjectRatio costApportionmentProjectRatio) {
        costApportionmentProjectRatioService.deleteCostApportionmentProjectRatioByProjectCode(costApportionmentProjectRatio.getProjectCode());
        List<CostApportionmentProjectRatio> costDeptInfos = costApportionmentProjectRatio.getCostDeptInfos();

        CostApportionmentProject costApportionmentProject = costApportionmentProjectService.selectCostApportionmentProjectByProjectCode(costApportionmentProjectRatio.getProjectCode());

        int row = 0;
        if ("1".equals(costApportionmentProject.getProjectType())) {
            String firstRatio = "";
            String secondRatio = "";
            String thirdRatio = "";
            for (CostApportionmentProjectRatio costDeptInfo : costDeptInfos) {
                if (!"0".equals(costDeptInfo.getFirstRatio())) {
                    firstRatio += (costDeptInfo.getRatioName() + "*" +costDeptInfo.getFirstRatio() + "+");
                }
                if (!"0".equals(costDeptInfo.getSecondRatio())) {
                    secondRatio += (costDeptInfo.getRatioName() + "*" +costDeptInfo.getSecondRatio() + "+");
                }
                if (!"0".equals(costDeptInfo.getThirdRatio())) {
                    thirdRatio += (costDeptInfo.getRatioName() + "*" +costDeptInfo.getThirdRatio() + "+");
                }
                row += costApportionmentProjectRatioService.insertCostApportionmentProjectRatio(costDeptInfo);
            }
            firstRatio = firstRatio.substring(0,firstRatio.length() - 1);
            secondRatio = secondRatio.substring(0,secondRatio.length() - 1);
            thirdRatio = thirdRatio.substring(0,thirdRatio.length() - 1);
            costApportionmentProject.setRationShow(
                    "一级分摊比例：" + firstRatio + ";二级分摊比例：" + secondRatio + ";三级分摊比例：" + thirdRatio
            );
        } else if ("2".equals(costApportionmentProject.getProjectType())) {
            String ratio = "";
            for (CostApportionmentProjectRatio costDeptInfo : costDeptInfos) {
                if (!"0".equals(costDeptInfo.getRatio())) {
                    ratio += (costDeptInfo.getRatioName() + "*" +costDeptInfo.getRatio() + "+");
                }
                row += costApportionmentProjectRatioService.insertCostApportionmentProjectRatio(costDeptInfo);
            }
            ratio = ratio.substring(0,ratio.length() - 1);
            costApportionmentProject.setRationShow("计算计入分摊比例：" + ratio);
        }
        costApportionmentProjectService.updateCostApportionmentProject(costApportionmentProject);
        return toAjax(row);
    }

    /**
     * 修改分摊方案管理比例设置
     */
    @PreAuthorize("@ss.hasPermi('apportionment:project:edit')")
    @Log(title = "分摊方案管理比例设置", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改分摊方案管理比例设置")
    public AjaxResult edit(@RequestBody CostApportionmentProjectRatio costApportionmentProjectRatio) {
        return toAjax(costApportionmentProjectRatioService.updateCostApportionmentProjectRatio(costApportionmentProjectRatio));
    }

    /**
     * 删除分摊方案管理比例设置
     */
    @PreAuthorize("@ss.hasPermi('apportionment:project:remove')")
    @Log(title = "分摊方案管理比例设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除分摊方案管理比例设置")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(costApportionmentProjectRatioService.deleteCostApportionmentProjectRatioByIds(ids));
    }
}
