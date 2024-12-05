package com.zjry.formDesign.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zjry.formDesign.domain.FormDesignField;
import com.zjry.formDesign.service.IFormDesignFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.formDesign.service.IFormProcessService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 自定义表单展示Controller
 *
 * @author yangtao
 * @date 2021-09-28
 */
@RestController
@RequestMapping("/formProcess")
@Api(tags={"【自定义表单展示】"})
public class FormProcessController extends BaseController {
	
    @Autowired
    private IFormProcessService iFormProcessService;
    @Autowired
    private IFormDesignFieldService iFormDesignFieldService;

    /**
     * 查询列表
     */
//    @PreAuthorize("@ss.hasPermi('formDesign:formDesign:list')")
    @PostMapping("/list")
    @ApiOperation("查询列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
        @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(@RequestBody Map<String, Object> params) {

    	String formDesignId = (String) params.get("formDesignId");
    	params.remove("formDesignId");

        List<FormDesignField> listField = iFormDesignFieldService.findByDesignId(formDesignId);
        startPage();
        params.remove("pageNum");
        params.remove("pageSize");
        List<Map<String, Object>> list = iFormProcessService.selectList(formDesignId, params,listField);
        return getDataTable(list);
    }

    @GetMapping("/selectForDetails")
    @ApiOperation("查询列表")
    public TableDataInfo selectForDetails(String formDesignId, String deptCode, Date qDate) {
        List<Map<String, Object>> list = iFormProcessService.selectForDetails(formDesignId,deptCode,qDate);
        return getDataTable(list);
    }




    /**
     * 获取自定义表单详细信息
     */
//    @PreAuthorize("@ss.hasPermi('formDesign:formDesign:query')")
    @GetMapping(value = "/getInfo/{formDesignId}/{id}")
    @ApiOperation("获取详细信息")
    public AjaxResult getInfo(@PathVariable("formDesignId") String formDesignId, @PathVariable("id") String id) {
    	System.out.println("formDesignId:" + formDesignId);
    	System.out.println("id:" + id);
    	return AjaxResult.success(iFormProcessService.findFormData(formDesignId, id));
    }

    /**
     * 新增自定义表单
     */
//    @PreAuthorize("@ss.hasPermi('formDesign:formDesign:add')")
    @Log(title = "自定义表单", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    @ApiOperation("保存")
    public AjaxResult save(@RequestBody Map<String, Object> params) {
    	String formDesignId = (String) params.get("formDesignId");
    	System.out.println("formDesignId--" + formDesignId);
    	params.remove("formDesignId");
    	System.out.println("formParams:" + params);
    	
    	if(params.get("id") != null) {
    		return toAjax(iFormProcessService.update(formDesignId, params, params.get("id").toString()));
    	} else {
    		return toAjax(iFormProcessService.insert(formDesignId, params));
    	}
    }

    /**
     * 删除
     */
//    @PreAuthorize("@ss.hasPermi('formDesign:formDesign:remove')")
    @Log(title = "自定义表单", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{formDesignId}/{id}")
    @ApiOperation("删除")
    public AjaxResult remove(@PathVariable("formDesignId") String formDesignId, @PathVariable("id") String id) {
        return toAjax(iFormProcessService.deleteById(formDesignId, id));
    }
    
}
