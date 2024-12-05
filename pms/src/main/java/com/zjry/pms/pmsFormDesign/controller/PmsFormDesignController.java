package com.zjry.pms.pmsFormDesign.controller;

import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.formDesign.vo.FormDesignVo;
import com.zjry.pms.pmsFormDesign.domain.PmsFormDesign;
import com.zjry.pms.pmsFormDesign.service.IFormDesignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 自定义表单Controller
 *
 * @author yangtao
 * @date 2021-07-27
 */
@RestController
@RequestMapping("/formDesign/pmsFormDesign")
@Api(tags={"【自定义表单】"})
public class PmsFormDesignController extends BaseController {
    @Autowired
    private IFormDesignService iFormDesignService;

    /**
     * 查询自定义表单列表
     */
//    @PreAuthorize("@ss.hasPermi('formDesign:formDesign:list')")
    @GetMapping("/list")
    @ApiOperation("查询自定义表单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsFormDesign formDesign) {
        startPage();
        List<PmsFormDesign> list = iFormDesignService.selectFormDesignList(formDesign);
        return getDataTable(list);
    }

    /**
     * 导出自定义表单列表
     */
   // @PreAuthorize("@ss.hasPermi('formDesign:formDesign:export')")
    @Log(title = "自定义表单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出自定义表单列表Excel")
    public AjaxResult export(PmsFormDesign formDesign) {
        List<PmsFormDesign> list = iFormDesignService.selectFormDesignList(formDesign);
        ExcelUtil<PmsFormDesign> util = new ExcelUtil<PmsFormDesign>(PmsFormDesign.class);
        return util.exportExcel(list, "自定义表单数据");
    }

    /**
     * 获取自定义表单详细信息
     */
  //  @PreAuthorize("@ss.hasPermi('formDesign:formDesign:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取自定义表单详细信息")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(iFormDesignService.selectFormDesignById(id));
    }

    /**
     * 新增自定义表单
     */
   // @PreAuthorize("@ss.hasPermi('formDesign:formDesign:add')")
    @Log(title = "自定义表单", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增自定义表单")
    public AjaxResult add(@RequestBody PmsFormDesign formDesign) {
        return toAjax(iFormDesignService.insertFormDesign(formDesign));
    }

    /**
     * 修改自定义表单
     */
   // @PreAuthorize("@ss.hasPermi('formDesign:formDesign:edit')")
    @Log(title = "自定义表单", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改自定义表单")
    public AjaxResult edit(@RequestBody PmsFormDesign formDesign) {
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
    
    //@PreAuthorize("@ss.hasPermi('formDesign:formDesign:edit')")
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
