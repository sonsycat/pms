package com.zjry.pms.pmsFormDesign.controller;

import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.pms.pmsFormDesign.service.IFormProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 自定义表单展示Controller
 *
 * @author yangtao
 * @date 2021-09-28
 */
@RestController
@RequestMapping("/pmsFormProcess")
@Api(tags={"【自定义表单展示】"})
public class PmsFormProcessController extends BaseController {
	
    @Autowired
    private IFormProcessService iFormProcessService;

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
    	startPage();

    	String formDesignId = (String) params.get("formDesignId");
    	params.remove("formDesignId");
    	params.remove("pageNum");
    	params.remove("pageSize");
    	
        List<Map<String, Object>> list = iFormProcessService.selectList(formDesignId, params);
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
