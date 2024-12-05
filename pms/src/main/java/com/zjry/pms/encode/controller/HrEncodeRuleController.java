package com.zjry.pms.encode.controller;

import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.pms.encode.domain.HrEncodeRule;
import com.zjry.pms.encode.service.IHrEncodeRuleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 编码规则Controller
 *
 * @author syw
 * @date 2021-06-25
 */
@RestController
@RequestMapping("/encode/rule")
@Api(tags={"【编码规则】"})
public class HrEncodeRuleController extends BaseController
{
    @Autowired
    private IHrEncodeRuleService hrEncodeRuleService;

    /**
     * 查询编码规则列表
     */
    @PreAuthorize("@ss.hasPermi('encode:rule:list')")
    @GetMapping("/list")
    @ApiOperation("查询编码规则列表")
        @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(HrEncodeRule hrEncodeRule)
    {
        startPage();
        List<HrEncodeRule> list = hrEncodeRuleService.selectHrEncodeRuleList(hrEncodeRule);
        return getDataTable(list);
    }
    
    /**
     * 导出编码规则列表
     */
    @PreAuthorize("@ss.hasPermi('encode:rule:export')")
    @Log(title = "编码规则", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出编码规则列表Excel")
    public AjaxResult export(HrEncodeRule hrEncodeRule)
    {
        List<HrEncodeRule> list = hrEncodeRuleService.selectHrEncodeRuleList(hrEncodeRule);
        ExcelUtil<HrEncodeRule> util = new ExcelUtil<HrEncodeRule>(HrEncodeRule.class);
        return util.exportExcel(list, "编码规则数据");
    }

    /**
     * 获取编码规则详细信息
     */
    @PreAuthorize("@ss.hasPermi('encode:rule:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取编码规则详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(hrEncodeRuleService.selectHrEncodeRuleById(id));
    }

    /**
     * 新增编码规则
     */
    @PreAuthorize("@ss.hasPermi('encode:rule:add')")
    @Log(title = "编码规则", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增编码规则")
    public AjaxResult add(@RequestBody  @Valid HrEncodeRule hrEncodeRule)
    {
        HrEncodeRule hrEncodeRule1=new HrEncodeRule();
        hrEncodeRule1.setRuleDb(hrEncodeRule.getRuleDb());
        hrEncodeRule1.setRuleColumn(hrEncodeRule.getRuleColumn());
        hrEncodeRule1.setDelFlag("0");
        List<HrEncodeRule> list = hrEncodeRuleService.selectHrEncodeRuleList(hrEncodeRule1);
         if(list!=null && list.size()>0){
            return  AjaxResult.error("已经存在该表，以及该表字段的编码规则");
         }

        String ruleContent=hrEncodeRule.getRuleFirst()+"-"+hrEncodeRule.getRuleSecond()+"-"+hrEncodeRule.getRuleThird();
        hrEncodeRule.setRuleContent(ruleContent);
        hrEncodeRule.setDelFlag("0");
        hrEncodeRule.preInsert();
        return toAjax(hrEncodeRuleService.insertHrEncodeRule(hrEncodeRule));
    }

    /**
     * 修改编码规则
     */
    @PreAuthorize("@ss.hasPermi('encode:rule:edit')")
    @Log(title = "编码规则", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改编码规则")
    public AjaxResult edit(@RequestBody HrEncodeRule hrEncodeRule)
    {
        HrEncodeRule hrEncodeRule2=new HrEncodeRule();
        hrEncodeRule2.setRuleDb(hrEncodeRule.getRuleDb());
        hrEncodeRule2.setRuleColumn(hrEncodeRule.getRuleColumn());
        hrEncodeRule2.setDelFlag("0");
        List<HrEncodeRule> list = hrEncodeRuleService.selectHrEncodeRuleList(hrEncodeRule2);
        if(list!=null && list.size()>0){
            return  AjaxResult.error("已经存在该表，以及该表字段的编码规则");
        }
        String ruleContent=hrEncodeRule.getRuleFirst()+"-"+hrEncodeRule.getRuleSecond()+"-"+hrEncodeRule.getRuleThird();
        hrEncodeRule.setRuleContent(ruleContent);
        hrEncodeRule.preUpdate();
        return toAjax(hrEncodeRuleService.updateHrEncodeRule(hrEncodeRule));
    }

    /**
     * 删除编码规则
     */
    @PreAuthorize("@ss.hasPermi('encode:rule:remove')")
    @Log(title = "编码规则", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除编码规则")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(hrEncodeRuleService.deleteHrEncodeRuleByIds(ids));
    }
}
