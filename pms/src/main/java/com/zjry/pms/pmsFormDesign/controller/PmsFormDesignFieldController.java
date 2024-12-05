package com.zjry.pms.pmsFormDesign.controller;

import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.pms.pmsFormDesign.domain.PmsFormDesignField;
import com.zjry.pms.pmsFormDesign.service.IFormDesignFieldService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 自定义表单配置Controller
 *
 * @author yangtao
 * @date 2021-09-29
 */
@RestController
@RequestMapping("/pmsFormDesignField")
@Api(tags={"【自定义表单配置】"})
public class PmsFormDesignFieldController extends BaseController {
    @Autowired
    private IFormDesignFieldService iFormDesignFieldService;
    
    @GetMapping(value = "/getFormConfig")
	public AjaxResult getFormConfig(@RequestParam("formDesignId") String formDesignId) {
		List<PmsFormDesignField> list = iFormDesignFieldService.findByDesignId(formDesignId);
		return AjaxResult.success(list);
	}
	
	@PostMapping(value = "/save")
	public AjaxResult save(@RequestBody PmsFormDesignField formDesignField) {
        iFormDesignFieldService.save(formDesignField.getFdcList());
        return AjaxResult.success();
	}
    
}
