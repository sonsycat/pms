/**
 * 
 */
package com.zjry.formDesign.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zjry.common.core.controller.BaseController;
import com.zjry.common.utils.StringUtils;
import com.zjry.formDesign.domain.FormDesign;
import com.zjry.formDesign.domain.FormDesignField;
import com.zjry.formDesign.mapper.FormDesignFieldMapper;
import com.zjry.formDesign.service.IFormDesignService;
import com.zjry.formDesign.util.ObjUtil;
import com.zjry.formDesign.vo.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author yangtao
 * 对外接口
 */
@Api(tags={"【自定义表单接口】"})
@RestController
@RequestMapping("/api/fd")
public class FormDesignAPIController extends BaseController {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IFormDesignService iFormDesignService;
	@Autowired
    private FormDesignFieldMapper formDesignFieldMapper;
	
	/**
	 * 查询所有自定义表单
	 * @param formCode
	 * @return
	 */
	@ApiOperation("查询自定义表单列表")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "formCode",value = "表单编码", dataType = "string", paramType = "query", required = false)
	})
	@RequestMapping(value="getFormDesignList", method=RequestMethod.GET)
	public ResponseBody getFormDesignList(@RequestParam(value="formCode", required=false) String formCode) {
		FormDesign formDesign = new FormDesign();
		if(StringUtils.isNotEmpty(formCode)) {
			formDesign.setFormCode(formCode);
		}
		List<FormDesign> fdList = iFormDesignService.selectFormDesignList(formDesign);
		
		String[] ps = {"id", "formCode", "formName"};
		List<Map<String, Object>> list = new ArrayList<>();
		for(FormDesign fd : fdList) {
			Map<String, Object> map = ObjUtil.obj2map(fd, ps);
			list.add(map);
		}
		return ResponseBody.ok(list);
	}
	
	/**
	 * 根据表单设计id，查询其对应的字段
	 * @param formDesignId
	 * @return
	 */
	@ApiOperation("查询表单对于的字段信息")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "formDesignId",value = "表单id", dataType = "string", paramType = "query", required = true)
	})
	@RequestMapping(value="getFormDesignFields", method=RequestMethod.GET)
	public ResponseBody getFormDesignFields(@RequestParam("formDesignId") String formDesignId) {
		List<FormDesignField> fdfList = formDesignFieldMapper.findByDesignId(formDesignId);
		
		String[] ps = {"fieldName", "fieldDesc", "queryType", "dbType", "fieldLength", "sortNo"};
		List<Map<String, Object>> list = new ArrayList<>();
		for(FormDesignField fdf : fdfList) {
			Map<String, Object> map = ObjUtil.obj2map(fdf, ps);
			list.add(map);
		}
		
		FormDesign fd = iFormDesignService.selectFormDesignById(formDesignId);
		Map<String, Object> data = new HashMap<>();
		data.put("formHtml", fd.getFormHtml());
		data.put("formField", list);
		return ResponseBody.ok(data);
	}
	
}
