package com.zjry.formDesign.controller;

import java.util.List;

import com.zjry.common.utils.StringUtils;
import com.zjry.pms.quality.domain.PmsQualityIndex;
import com.zjry.pms.quality.mapper.PmsQualityIndexMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.formDesign.domain.FormDesign;
import com.zjry.formDesign.service.IFormDesignService;
import com.zjry.formDesign.vo.FormDesignVo;
import com.zjry.system.service.ISysMenuService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 自定义表单Controller
 *
 * @author yangtao
 * @date 2021-07-27
 */
@RestController
@RequestMapping("/formDesign/formDesign")
@Api(tags = {"【自定义表单】"})
public class FormDesignController extends BaseController {

    @Autowired
    private IFormDesignService iFormDesignService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private PmsQualityIndexMapper qualityIndexMapper;

    /**
     * 查询自定义表单列表
     */
    //@PreAuthorize("@ss.hasPermi('formDesign:formDesign:list')")
    @GetMapping("/list")
    @ApiOperation("查询自定义表单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(FormDesign formDesign) {
        startPage();
        List<FormDesign> list = iFormDesignService.selectFormDesignList(formDesign);
        return getDataTable(list);
    }

    @GetMapping("/selectForSortNo")
    @ApiOperation("查询排序")
    public AjaxResult selectForSortNo(Long compCode) {
        String sortNo = iFormDesignService.selectForSortNo(compCode);
        return AjaxResult.success(sortNo);
    }

    /**
     * 导出自定义表单列表
     */
    // @PreAuthorize("@ss.hasPermi('formDesign:formDesign:export')")
    @Log(title = "自定义表单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出自定义表单列表Excel")
    public AjaxResult export(FormDesign formDesign) {
        List<FormDesign> list = iFormDesignService.selectFormDesignList(formDesign);
        ExcelUtil<FormDesign> util = new ExcelUtil<FormDesign>(FormDesign.class);
        return util.exportExcel(list, "自定义表单数据");
    }

    /**
     * 获取自定义表单详细信息
     */
    //@PreAuthorize("@ss.hasPermi('formDesign:formDesign:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取自定义表单详细信息")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(iFormDesignService.selectFormDesignById(id));
    }

    /**
     * 新增自定义表单
     */
    //@PreAuthorize("@ss.hasPermi('formDesign:formDesign:add')")
    @Log(title = "自定义表单", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增自定义表单")
    public AjaxResult add(@RequestBody FormDesign formDesign) {
        FormDesign fd = new FormDesign();
        fd.setCompCode(formDesign.getCompCode());
        fd.setFName(formDesign.getFormName());
        List<FormDesign> list = iFormDesignService.selectFormDesignList(fd);
        if (list.size() > 0) {
            return error("模板标题已经存在");
        }
        FormDesign fd1 = new FormDesign();
        fd1.setCompCode(formDesign.getCompCode());
        fd1.setSortNo(formDesign.getSortNo());
        List<FormDesign> list1 = iFormDesignService.selectFormDesignList(fd1);
        if (list1.size() > 0) {
            return error("显示顺序已经存在");
        }
        FormDesign fd2 = new FormDesign();
        fd2.setCompCode(formDesign.getCompCode());
        fd2.setTitle(formDesign.getTitle());
        List<FormDesign> list2 = iFormDesignService.selectFormDesignList(fd2);
        if (list2.size() > 0) {
            return error("标题已经存在");
        }
        return toAjax(iFormDesignService.insertFormDesign(formDesign));
    }

    /**
     * 修改自定义表单
     */
    // @PreAuthorize("@ss.hasPermi('formDesign:formDesign:edit')")
    @Log(title = "自定义表单", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改自定义表单")
    public AjaxResult edit(@RequestBody FormDesign formDesign) {
        FormDesign fd = new FormDesign();
        fd.setId(formDesign.getId());
        fd.setCompCode(formDesign.getCompCode());
        fd.setFName(formDesign.getFormName());
        List<FormDesign> list = iFormDesignService.selectFormDesignList(fd);
        if (list.size() > 0) {
            return error("模板标题已经存在");
        }
        FormDesign fd1 = new FormDesign();
        fd1.setId(formDesign.getId());
        fd1.setCompCode(formDesign.getCompCode());
        fd1.setSortNo(formDesign.getSortNo());
        List<FormDesign> list1 = iFormDesignService.selectFormDesignList(fd1);
        if (list1.size() > 0) {
            return error("显示顺序已经存在");
        }
        FormDesign fd2 = new FormDesign();
        fd2.setId(formDesign.getId());
        fd2.setCompCode(formDesign.getCompCode());
        fd2.setTitle(formDesign.getTitle());
        List<FormDesign> list2 = iFormDesignService.selectFormDesignList(fd2);
        if (list2.size() > 0) {
            return error("标题已经存在");
        }
        return toAjax(iFormDesignService.updateFormDesign(formDesign));
    }

    // @PreAuthorize("@ss.hasPermi('formDesign:formDesign:edit')")
    @Log(title = "自定义表单", businessType = BusinessType.UPDATE)
    @PutMapping("/setPageNo")
    @ApiOperation("修改自定义表单")
    public AjaxResult setPageNo(@RequestBody FormDesign formDesign) {
        return toAjax(iFormDesignService.updateFormDesign(formDesign));
    }

    /**
     * 删除自定义表单
     */
    // @PreAuthorize("@ss.hasPermi('formDesign:formDesign:remove')")
    @Log(title = "自定义表单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除自定义表单")
    public AjaxResult remove(@PathVariable String[] ids) {
        for (String id : ids) {
            FormDesign formDesign = iFormDesignService.selectFormDesignById(id);

            PmsQualityIndex query = new PmsQualityIndex();
            query.setDelFlag("0");
            query.setTempCode(formDesign.getId());
            List<PmsQualityIndex> qualityIndices = qualityIndexMapper.selectPmsQualityIndexList(query);
            if (CollectionUtils.isNotEmpty(qualityIndices)) {
                return AjaxResult.error("模板被引用,无法删除");
            }

            if (StringUtils.isNotEmpty(formDesign.getRemarks())) {

                menuService.deleteMenuById(Long.parseLong(formDesign.getRemarks()));
            }

        }
        return toAjax(iFormDesignService.deleteFormDesignByIds(ids));
    }

    // @PreAuthorize("@ss.hasPermi('formDesign:formDesign:edit')")
    @Log(title = "更新表单设计", businessType = BusinessType.UPDATE)
    @PostMapping("/saveDesign")
    @ApiOperation("更新表单设计")
    public AjaxResult saveDesign(@RequestBody FormDesignVo formDesignVo, @RequestParam("fdId") String fdId) {
        formDesignVo.setFdId(fdId);
        return toAjax(iFormDesignService.updateFormJson(formDesignVo));
    }

    // @PreAuthorize("@ss.hasPermi('formDesign:formDesign:edit')")
    @Log(title = "更新表单数据", businessType = BusinessType.UPDATE)
    @PostMapping("/saveData")
    @ApiOperation("更新表单数据")
    public AjaxResult saveData(@RequestBody FormDesignVo formDesignVo) {
        return toAjax(iFormDesignService.updateFormData(formDesignVo.getFdId(), formDesignVo.getFormData()));
    }

    @RequestMapping(value = "checkFormCodeUnique")
    @ResponseBody
    public Object checkFormCodeUnique(@RequestParam("formCode") String formCode) {
        return AjaxResult.success(iFormDesignService.checkFormCodeUnique(formCode));
    }

}
