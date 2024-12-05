package com.zjry.formDesign.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    
    @PostMapping(value = "getFormConfig")
	public AjaxResult getFormConfig(@RequestParam("formDesignId") String formDesignId) {
		List<FormDesignField> list = iFormDesignFieldService.findByDesignId(formDesignId);
		return AjaxResult.success(list);
	}
	
	@PostMapping(value = "save")
	public AjaxResult save(@RequestBody FormDesignField formDesignField) {
        iFormDesignFieldService.save(formDesignField.getFdcList());
        return AjaxResult.success();
	}
    
}
