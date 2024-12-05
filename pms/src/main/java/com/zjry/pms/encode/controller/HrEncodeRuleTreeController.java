package com.zjry.pms.encode.controller;

import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.pms.encode.domain.HrEncodeRuleTree;
import com.zjry.pms.encode.service.IHrEncodeRuleTreeService;

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
 * 编码规则树形Controller
 *
 * @author syw
 * @date 2021-06-25
 */
@RestController
@RequestMapping("/encode/tree")
@Api(tags={"【编码规则树形】"})
public class HrEncodeRuleTreeController extends BaseController
{
    @Autowired
    private IHrEncodeRuleTreeService hrEncodeRuleTreeService;

    /**
     * 查询编码规则树形列表
     */
    @PreAuthorize("@ss.hasPermi('encode:tree:list')")
    @GetMapping("/list")
    @ApiOperation("查询编码规则树形列表")
        @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(HrEncodeRuleTree hrEncodeRuleTree)
    {
        startPage();
        List<HrEncodeRuleTree> list = hrEncodeRuleTreeService.selectHrEncodeRuleTreeList(hrEncodeRuleTree);
        return getDataTable(list);
    }
    
    /**
     * 导出编码规则树形列表
     */
    @PreAuthorize("@ss.hasPermi('encode:tree:export')")
    @Log(title = "编码规则树形", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出编码规则树形列表Excel")
    public AjaxResult export(HrEncodeRuleTree hrEncodeRuleTree)
    {
        List<HrEncodeRuleTree> list = hrEncodeRuleTreeService.selectHrEncodeRuleTreeList(hrEncodeRuleTree);
        ExcelUtil<HrEncodeRuleTree> util = new ExcelUtil<HrEncodeRuleTree>(HrEncodeRuleTree.class);
        return util.exportExcel(list, "编码规则树形数据");
    }

    /**
     * 获取编码规则树形详细信息
     */
    @PreAuthorize("@ss.hasPermi('encode:tree:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取编码规则树形详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(hrEncodeRuleTreeService.selectHrEncodeRuleTreeById(id));
    }

    /**
     * 新增编码规则树形
     */
    @PreAuthorize("@ss.hasPermi('encode:tree:add')")
    @Log(title = "编码规则树形", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增编码规则树形")
    public AjaxResult add(@RequestBody @Valid HrEncodeRuleTree hrEncodeRuleTree)
    {

        HrEncodeRuleTree hrEncodeRuleTree1=new HrEncodeRuleTree();
        hrEncodeRuleTree1.setRuleDb(hrEncodeRuleTree.getRuleDb());
        hrEncodeRuleTree1.setRuleColumn(hrEncodeRuleTree.getRuleColumn());
        hrEncodeRuleTree1.setDelFlag("0");
        List<HrEncodeRuleTree> list = hrEncodeRuleTreeService.selectHrEncodeRuleTreeList(hrEncodeRuleTree1);
        if(list!=null && list.size()>0){
            return  AjaxResult.error("已经存在该表，以及该表字段的编码规则");
        }


        hrEncodeRuleTree.setDelFlag("0");
        hrEncodeRuleTree.preInsert();

        return toAjax(hrEncodeRuleTreeService.insertHrEncodeRuleTree(hrEncodeRuleTree));
    }

    /**
     * 修改编码规则树形
     */
    @PreAuthorize("@ss.hasPermi('encode:tree:edit')")
    @Log(title = "编码规则树形", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改编码规则树形")
    public AjaxResult edit(@RequestBody HrEncodeRuleTree hrEncodeRuleTree)
    {
        HrEncodeRuleTree hrEncodeRuleTree2=new HrEncodeRuleTree();
        hrEncodeRuleTree2.setRuleDb(hrEncodeRuleTree.getRuleDb());
        hrEncodeRuleTree2.setRuleColumn(hrEncodeRuleTree.getRuleColumn());
        hrEncodeRuleTree2.setDelFlag("0");
        List<HrEncodeRuleTree> list = hrEncodeRuleTreeService.selectHrEncodeRuleTreeList(hrEncodeRuleTree2);
        if(list!=null && list.size()>0){
            return  AjaxResult.error("已经存在该表，以及该表字段的编码规则");
        }
        hrEncodeRuleTree.preUpdate();
        return toAjax(hrEncodeRuleTreeService.updateHrEncodeRuleTree(hrEncodeRuleTree));
    }

    /**
     * 删除编码规则树形
     */
    @PreAuthorize("@ss.hasPermi('encode:tree:remove')")
    @Log(title = "编码规则树形", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除编码规则树形")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(hrEncodeRuleTreeService.deleteHrEncodeRuleTreeByIds(ids));
    }
}
