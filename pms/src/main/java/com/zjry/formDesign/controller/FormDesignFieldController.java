package com.zjry.formDesign.controller;

import java.util.ArrayList;
import java.util.List;

import com.zjry.formDesign.domain.FormDesign;
import com.zjry.pms.basic.domain.AssetsBasicPublicVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.formDesign.domain.FormDesignField;
import com.zjry.formDesign.service.IFormDesignFieldService;

import io.swagger.annotations.Api;

/**
 * 自定义表单配置Controller
 *
 * @author yangtao
 * @date 2021-09-29
 */
@RestController
@RequestMapping("/formDesignField")
@Api(tags={"【自定义表单配置】"})
public class FormDesignFieldController extends BaseController {
    @Autowired
    private IFormDesignFieldService iFormDesignFieldService;
    
    @GetMapping(value = "/getFormConfig")
	public AjaxResult getFormConfig(String formDesignId) {
		List<FormDesignField> list = iFormDesignFieldService.findByDesignId(formDesignId);
		return AjaxResult.success(list);
	}

	@GetMapping(value = "/getForZB")
	public AjaxResult getForZB(String formDesignId) {
		List<FormDesignField> list = iFormDesignFieldService.findByDesignId(formDesignId);
		List<FormDesignField> listzb = new ArrayList<>();
		for(FormDesignField fdf : list){
			if("number".equals(fdf.getFieldType())|| "date".equals(fdf.getFieldType()) || "cs_dept".equals(fdf.getFieldName()) ){
				listzb.add(fdf);
			}
		}
		return AjaxResult.success(listzb);
	}

	
	@PostMapping(value = "/save")
	public AjaxResult save(@RequestBody FormDesignField formDesignField) {
        iFormDesignFieldService.save(formDesignField.getFdcList());
        return AjaxResult.success();
	}

	@PostMapping(value = "/updateFormDesignField")
	public AjaxResult updateFormDesignField(@RequestBody FormDesignField formDesignField) {
		return toAjax(iFormDesignFieldService.updateFormDesignField(formDesignField));
	}

	@PostMapping(value = "/updateAll")
	public AjaxResult updateAll(@RequestBody FormDesign formDesign) {
		iFormDesignFieldService.updateAll(formDesign);
		return AjaxResult.success();
	}



	@GetMapping(value = "/selectDictType")
	public AjaxResult selectDictType(String dictLabel) {
		List<AssetsBasicPublicVo> list = iFormDesignFieldService.selectDictType(dictLabel);
		return AjaxResult.success(list);
	}


    
}
