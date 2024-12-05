package com.zjry.pms.apport.controller;

import java.util.Date;
import java.util.List;
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
import com.zjry.pms.apport.domain.CostApport;
import com.zjry.pms.apport.domain.CostApportDetail;
import com.zjry.pms.apport.service.ICostApportDetailService;
import com.zjry.pms.apport.service.ICostApportService;
import com.zjry.pms.encode.service.IHrEncodeRuleService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 分摊计算Controller
 *
 * @author yzh
 * @date 2022-05-17
 */
@RestController
@RequestMapping("/apport/apport")
@Api(tags={"【分摊计算】"})
public class CostApportController extends BaseController {
    @Autowired
    private ICostApportService costApportService;
    @Autowired
    private ICostApportDetailService costApportDetailService;
    @Autowired
    private IHrEncodeRuleService hrEncodeRuleService;
   
    
    /**
     * 查询分摊计算列表
     */
    @PreAuthorize("@ss.hasPermi('apport:apport:list')")
    @GetMapping("/list")
    @ApiOperation("查询分摊计算列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(CostApport costApport) {
        startPage();
        List<CostApport> list = costApportService.selectCostApportList(costApport);
        return getDataTable(list);
    }

    /**
     * 导出分摊计算列表
     */
    @PreAuthorize("@ss.hasPermi('apport:apport:export')")
    @Log(title = "分摊计算", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出分摊计算列表Excel")
    public AjaxResult export(CostApport costApport) {
        List<CostApport> list = costApportService.selectCostApportList(costApport);
        ExcelUtil<CostApport> util = new ExcelUtil<CostApport>(CostApport.class);
        return util.exportExcel(list, "分摊计算数据");
    }

    /**
     * 获取分摊计算详细信息
     */
    @PreAuthorize("@ss.hasPermi('apport:apport:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取分摊计算详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(costApportService.selectCostApportById(id));
    }

    /**
     * 验证成本数据
     */
    @PreAuthorize("@ss.hasPermi('apport:apport:add')")
    @Log(title = "分摊计算", businessType = BusinessType.INSERT)
    @PostMapping("/verification")
    @ApiOperation("验证成本数据")
    public AjaxResult verification(@RequestBody CostApport costApport) {
    	CostApport costApportTemp = new CostApport();
    	costApportTemp.setCompCode(costApport.getCompCode());
    	costApportTemp.setNowDate(costApport.getNowDate());
    	List<CostApport>list = costApportService.selectCostApportList(costApportTemp);
    	if(list.size()>0){
    		return AjaxResult.error("该核算年月已经有分摊数据！");
    	}
    	String str  = costApportService.verification(costApport);
    	if(str!=""){
    		return AjaxResult.error(str);
    	}
        return AjaxResult.success();
    }
    
    /**
     * 验证科室快照

    @PreAuthorize("@ss.hasPermi('apport:apport:add')")
    @Log(title = "分摊计算", businessType = BusinessType.INSERT)
    @PostMapping("/verification1")
    @ApiOperation("验证科室快照")
    public AjaxResult verification1(@RequestBody CostApport costApport) {
    	CostApport costApportTemp = new CostApport();
    	costApportTemp.setCompCode(costApport.getCompCode());
    	costApportTemp.setNowDate(costApport.getNowDate());
    	List<CostApport>list = costApportService.selectCostApportList(costApportTemp);
    	if(list.size()>0){
    		return AjaxResult.error("该核算年月已经有分摊数据！");
    	}
    	String str  = costApportService.verification1(costApport);
    	if(str!=""){
    		return AjaxResult.error(str);
    	}
        return AjaxResult.success();
    }
     */
    /**
     * 验证分摊方案
     */
    @PreAuthorize("@ss.hasPermi('apport:apport:add')")
    @Log(title = "分摊计算", businessType = BusinessType.INSERT)
    @PostMapping("/verification2")
    @ApiOperation("验证分摊方案")
    public AjaxResult verification2(@RequestBody CostApport costApport) {
    	CostApport costApportTemp = new CostApport();
    	costApportTemp.setCompCode(costApport.getCompCode());
    	costApportTemp.setNowDate(costApport.getNowDate());
    	List<CostApport>list = costApportService.selectCostApportList(costApportTemp);
    	if(list.size()>0){
    		return AjaxResult.error("该核算年月已经有分摊数据！");
    	}
    	String str  = costApportService.verification2(costApport);
    	if(str!=""){
    		return AjaxResult.error(str);
    	}
        return AjaxResult.success();
    }

    /**
     * 验证分摊比例
     */
    @PreAuthorize("@ss.hasPermi('apport:apport:add')")
    @Log(title = "分摊计算", businessType = BusinessType.INSERT)
    @PostMapping("/verification3")
    @ApiOperation("验证分摊比例")
    public AjaxResult verification3(@RequestBody CostApport costApport) {
    	CostApport costApportTemp = new CostApport();
    	costApportTemp.setCompCode(costApport.getCompCode());
    	costApportTemp.setNowDate(costApport.getNowDate());
    	List<CostApport>list = costApportService.selectCostApportList(costApportTemp);
    	if(list.size()>0){
    		return AjaxResult.error("该核算年月已经有分摊数据！");
    	}
    	String str  = costApportService.verification3(costApport);
    	if(str!=""){
    		return AjaxResult.error(str);
    	}
        return AjaxResult.success();
    }

    /**
     * 分摊计算
     */
    @Log(title = "分摊计算", businessType = BusinessType.INSERT)
    @PostMapping("/ftjs")
    @ApiOperation("分摊计算")
    public AjaxResult ftjs(@RequestBody CostApport costApport) {
    	String apportCode = hrEncodeRuleService.generateEncode("cost_apport", "apportCode");
    	costApport.setApportCode(apportCode);
    	costApport.setApportTime(new Date());
		try {
			costApportService.ftjs(costApport);
		} catch (Exception e) {
			e.printStackTrace();
			AjaxResult.error();
		}
		if(costApport.getApportType()=="1"||"1".equals(costApport.getApportType())){
			costApportService.insertCostApport(costApport);
		}
        return AjaxResult.success(costApport);
    }
                                  
    /**
     * 一级分摊计算
     */
    @Log(title = "分摊计算", businessType = BusinessType.INSERT)
    @PostMapping("/yjftjs")
    @ApiOperation("分摊计算")
    public AjaxResult yjftjs(@RequestBody CostApport costApport) {
		try {
			costApportService.yjft(costApport);
		} catch (Exception e) {
			e.printStackTrace();
			AjaxResult.error();
		}
		if(costApport.getApportType()=="2"||"2".equals(costApport.getApportType())){
			costApportService.insertCostApport(costApport);
		}
        return AjaxResult.success(costApport);
    }
    
    
    /**
     * 二级分摊计算
     */
    @Log(title = "分摊计算", businessType = BusinessType.INSERT)
    @PostMapping("/ejftjs")
    @ApiOperation("分摊计算")
    public AjaxResult ejftjs(@RequestBody CostApport costApport) {
		try {
			costApportService.ejft(costApport);
		} catch (Exception e) {
			e.printStackTrace();
			AjaxResult.error();
		}
		if(costApport.getApportType()=="3"||"3".equals(costApport.getApportType())){
			costApportService.insertCostApport(costApport);
		}
        return AjaxResult.success(costApport);
    }
    
    /**
     * 三级分摊计算
     */
    @Log(title = "分摊计算", businessType = BusinessType.INSERT)
    @PostMapping("/sjftjs")
    @ApiOperation("分摊计算")
    public AjaxResult sjftjs(@RequestBody CostApport costApport) {
		try {
			costApportService.sjft(costApport);
		} catch (Exception e) {
			e.printStackTrace();
			AjaxResult.error();
		}
		if(costApport.getApportType()=="4"||"4".equals(costApport.getApportType())){
			costApportService.insertCostApport(costApport);
		}
        return AjaxResult.success(costApport);
    }

    /**
     * 修改分摊计算
     */
    @PreAuthorize("@ss.hasPermi('apport:apport:edit')")
    @Log(title = "分摊计算", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改分摊计算")
    public AjaxResult edit(@RequestBody CostApport costApport) {
        return toAjax(costApportService.updateCostApport(costApport));
    }

    /**
     * 删除分摊计算
     */
    @PreAuthorize("@ss.hasPermi('apport:apport:remove')")
    @Log(title = "分摊计算", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除分摊计算")
    public AjaxResult remove(@PathVariable Long[] ids) {
    	if(ids.length>0){
			CostApport costApport = costApportService.selectCostApportById(ids[0]);
			CostApportDetail costApportDetail = new CostApportDetail();
			costApportDetail.setApportCode(costApport.getApportCode());
			costApportDetailService.deleteCostApportDetailByCode(costApportDetail);
			costApportService.deleteCostApportById(ids[0]);
    	}
    	return AjaxResult.success();
    }
}
