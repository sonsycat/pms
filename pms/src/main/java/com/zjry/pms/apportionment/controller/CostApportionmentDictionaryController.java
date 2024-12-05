package com.zjry.pms.apportionment.controller;

import java.util.List;

import com.zjry.common.utils.StringUtils;
import com.zjry.pms.apportionment.domain.CostApportionmentProjectRatio;
import com.zjry.pms.apportionment.service.ICostApportionmentProjectRatioService;
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
import com.zjry.pms.apportionment.domain.CostApportionmentDictionary;
import com.zjry.pms.apportionment.service.ICostApportionmentDictionaryService;
import com.zjry.pms.encode.service.IHrEncodeRuleService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 比例分摊字典管理Controller
 *
 * @author yzh
 * @date 2022-05-10
 */
@RestController
@RequestMapping("/apportionment/dictionary")
@Api(tags={"【比例分摊字典管理】"})
public class CostApportionmentDictionaryController extends BaseController {
    @Autowired
    private ICostApportionmentDictionaryService costApportionmentDictionaryService;
    @Autowired
    private IHrEncodeRuleService hrEncodeRuleService;
    @Autowired
    private ICostApportionmentProjectRatioService costApportionmentProjectRatioService;
    /**
     * 查询比例分摊字典管理列表
     */
    @PreAuthorize("@ss.hasPermi('apportionment:dictionary:list')")
    @GetMapping("/list")
    @ApiOperation("查询比例分摊字典管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(CostApportionmentDictionary costApportionmentDictionary) {
        costApportionmentDictionary.setRatioType("2");
        startPage();
        List<CostApportionmentDictionary> list = costApportionmentDictionaryService.selectCostApportionmentDictionaryList(costApportionmentDictionary);
        return getDataTable(list);
    }
    
    /**
     * 查询比例分摊字典管理列表
     */
    @PreAuthorize("@ss.hasPermi('apportionment:dictionary:list')")
    @PostMapping("/lists")
    @ApiOperation("查询比例分摊字典管理列表")
    public AjaxResult lists(@RequestBody CostApportionmentDictionary costApportionmentDictionary) {
    	try {
			costApportionmentDictionary = costApportionmentDictionaryService.selectCostApportionmentDictionaryLists(costApportionmentDictionary);
		} catch (Exception e) {
    	    if (StringUtils.isNotEmpty(e.getMessage())) {
                return AjaxResult.error(e.getMessage());
            } else {
                return AjaxResult.error("数据源连接异常或SQL异常！");
            }
		}
        return AjaxResult.success(costApportionmentDictionary);
    }

    /**
     * 导出比例分摊字典管理列表
     */
    @PreAuthorize("@ss.hasPermi('apportionment:dictionary:export')")
    @Log(title = "比例分摊字典管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出比例分摊字典管理列表Excel")
    public AjaxResult export(CostApportionmentDictionary costApportionmentDictionary) {
        List<CostApportionmentDictionary> list = costApportionmentDictionaryService.selectCostApportionmentDictionaryList(costApportionmentDictionary);
        ExcelUtil<CostApportionmentDictionary> util = new ExcelUtil<CostApportionmentDictionary>(CostApportionmentDictionary.class);
        return util.exportExcel(list, "比例分摊字典管理数据");
    }

    /**
     * 获取比例分摊字典管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('apportionment:dictionary:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取比例分摊字典管理详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(costApportionmentDictionaryService.selectCostApportionmentDictionaryById(id));
    }

    /**
     * 新增比例分摊字典管理
     */
    @PreAuthorize("@ss.hasPermi('apportionment:dictionary:add')")
    @Log(title = "比例分摊字典管理", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增比例分摊字典管理")
    public AjaxResult add(@RequestBody CostApportionmentDictionary costApportionmentDictionary) {
    	CostApportionmentDictionary costApportionmentDictionaryTemp = new CostApportionmentDictionary();
    	costApportionmentDictionaryTemp.setRatioName(costApportionmentDictionary.getRatioName());
        costApportionmentDictionaryTemp.setCompCode(costApportionmentDictionary.getCompCode());
    	List<CostApportionmentDictionary>list = costApportionmentDictionaryService.selectCostApportionmentDictionaryListByName(costApportionmentDictionaryTemp);
    	if(list.size()>0){
    		return AjaxResult.error("同一单位比例名称不能重复！");
    	}
    	String ratioCode = hrEncodeRuleService.generateEncode("cost_apportionment_dictionary", "RATIO_CODE");
    	costApportionmentDictionary.setRatioCode(ratioCode);
        return toAjax(costApportionmentDictionaryService.insertCostApportionmentDictionary(costApportionmentDictionary));
    }

    /**
     * 修改比例分摊字典管理
     */
    @PreAuthorize("@ss.hasPermi('apportionment:dictionary:edit')")
    @Log(title = "比例分摊字典管理", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改比例分摊字典管理")
    public AjaxResult edit(@RequestBody CostApportionmentDictionary costApportionmentDictionary) {
    	CostApportionmentDictionary costApportionmentDictionaryTemp = new CostApportionmentDictionary();
    	costApportionmentDictionaryTemp.setId(costApportionmentDictionary.getId());
    	costApportionmentDictionaryTemp.setRatioName(costApportionmentDictionary.getRatioName());
        costApportionmentDictionaryTemp.setCompCode(costApportionmentDictionary.getCompCode());
    	List<CostApportionmentDictionary>list = costApportionmentDictionaryService.selectCostApportionmentDictionaryListByName(costApportionmentDictionaryTemp);
    	if(list.size()>0){
    		return AjaxResult.error("同一单位比例名称不能重复！");
    	}
        return toAjax(costApportionmentDictionaryService.updateCostApportionmentDictionary(costApportionmentDictionary));
    }

    /**
     * 删除比例分摊字典管理
     */
    @PreAuthorize("@ss.hasPermi('apportionment:dictionary:remove')")
    @Log(title = "比例分摊字典管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除比例分摊字典管理")
    public AjaxResult remove(@PathVariable Long[] ids) {
        CostApportionmentDictionary cad =  costApportionmentDictionaryService.selectCostApportionmentDictionaryById(ids[0]);
        CostApportionmentProjectRatio  capr = new CostApportionmentProjectRatio();
        capr.setRatioCode(cad.getRatioCode());
        capr.setCompCode(cad.getCompCode());
        List<CostApportionmentProjectRatio> list = costApportionmentProjectRatioService.selectCostApportionmentProjectRatioList(capr);
             if(list != null && list.size()>0){
                 return error("该比例已被引用不能删除");
             }
        return toAjax(costApportionmentDictionaryService.deleteCostApportionmentDictionaryByIds(ids));
    }
}
