package com.zjry.pms.foundation.controller;

import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.StringUtils;
import com.zjry.pms.foundation.domain.PmsEquivalentDept;
import com.zjry.pms.foundation.domain.PmsEquivalentItem;
import com.zjry.pms.foundation.domain.PmsFundationEquivalent;
import com.zjry.pms.foundation.domain.PmsMatchDept;
import com.zjry.pms.foundation.service.IPmsFoundationEquivalentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础当量Controller
 *
 * @author yzh
 * @date 2022-05-17
 */
@RestController
@RequestMapping("/foundation/equivalent")
@Api(tags={"【基础当量】"})
public class PmsFoundationEquivalentController extends BaseController {
    @Autowired
    private IPmsFoundationEquivalentService pmsFoundationEquivalentService;
    
    /**
     * 查询基础当量列表
     */
    @PreAuthorize("@ss.hasPermi('foundation:equivalent:list')")
    @GetMapping("/list")
    @ApiOperation("查询基础当量列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsFundationEquivalent pmsFundationEquivalent) {
        startPage();
        List<PmsFundationEquivalent> list = pmsFoundationEquivalentService.selectPmsEquivalentItemList(pmsFundationEquivalent);
        return getDataTable(list);
    }
    @GetMapping("/matchDept")
    @ApiOperation("查询基础当量对应科室列表")
    public AjaxResult matchDeptlist() {
        List<PmsMatchDept> data = pmsFoundationEquivalentService.selectPmsMatchDeptlist();
        if (StringUtils.isNull(data)) {
            data = new ArrayList<PmsMatchDept>();
        }
        return AjaxResult.success(data);
    }
    @GetMapping("/dept")
    @ApiOperation("查询基础当量部门列表")
    public AjaxResult deptlist() {
        List<PmsEquivalentDept> data = pmsFoundationEquivalentService.selectPmsEquivalentDept();
        if (StringUtils.isNull(data)) {
            data = new ArrayList<PmsEquivalentDept>();
        }
        return AjaxResult.success(data);
    }
    @GetMapping(value ="/item1/{keywords}")
    @ApiOperation("查询基础当量部门列表")
    public AjaxResult itemlist(@PathVariable("keywords") String keywords) {
        if(null == keywords){
            return AjaxResult.success(new ArrayList<PmsEquivalentItem>());
        }
        List<PmsEquivalentItem> data = pmsFoundationEquivalentService.selectPmsEquivalentItem( keywords);
        if (StringUtils.isNull(data)) {
            data = new ArrayList<PmsEquivalentItem>();
        }
        return AjaxResult.success(data);
    }
    @GetMapping(value = "/{id}")
    @ApiOperation("获取奖金导入详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsFoundationEquivalentService.selectpPmsFundationEquivalentById(id));
    }
    @PreAuthorize("@ss.hasPermi('foundation:equivalent:edit')")
    @Log(title = "奖金导入修改", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("奖金导入修改")
    public AjaxResult edit(@RequestBody PmsFundationEquivalent pmsFundationEquivalent) {
        return toAjax(pmsFoundationEquivalentService.updatePmsFundationEquivalent(pmsFundationEquivalent));
    }

}
