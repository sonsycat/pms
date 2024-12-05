package com.zjry.pms.apportionment.controller;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
import com.zjry.pms.apportionment.domain.CostDatabase;
import com.zjry.pms.apportionment.service.ICostDatabaseService;
import com.zjry.pms.encode.service.IHrEncodeRuleService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 多源数据库设置Controller
 *
 * @author yzh
 * @date 2022-05-12
 */
@RestController
@RequestMapping("/apportionment/database")
@Api(tags={"【多源数据库设置】"})
public class CostDatabaseController extends BaseController {
    @Autowired
    private ICostDatabaseService costDatabaseService;
    @Autowired
    private IHrEncodeRuleService hrEncodeRuleService;

    /**
     * 查询多源数据库设置列表
     */
    @GetMapping("/list")
    @ApiOperation("查询多源数据库设置列表")
    public TableDataInfo list(CostDatabase costDatabase) {
        List<CostDatabase> list = costDatabaseService.selectCostDatabaseList(costDatabase);
        return getDataTable(list);
    }

    /**
     * 导出多源数据库设置列表
     */
    @Log(title = "多源数据库设置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出多源数据库设置列表Excel")
    public AjaxResult export(CostDatabase costDatabase) {
        List<CostDatabase> list = costDatabaseService.selectCostDatabaseList(costDatabase);
        ExcelUtil<CostDatabase> util = new ExcelUtil<CostDatabase>(CostDatabase.class);
        return util.exportExcel(list, "多源数据库设置数据");
    }

    /**
     * 获取多源数据库设置详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取多源数据库设置详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(costDatabaseService.selectCostDatabaseById(id));
    }

    /**
     * 新增多源数据库设置
     */
    @Log(title = "多源数据库设置", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增多源数据库设置")
    public AjaxResult add(@Validated @RequestBody CostDatabase costDatabase) {
        if(validDuplicateName(costDatabase)){
            return AjaxResult.error("名称已存在");
        }
        String code = hrEncodeRuleService.generateEncode("cost_database", "CODE");
    	costDatabase.setCode(code);
        return toAjax(costDatabaseService.insertCostDatabase(costDatabase));
    }

    /**
     * 修改多源数据库设置
     */
    @Log(title = "多源数据库设置", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改多源数据库设置")
    public AjaxResult edit(@Validated @RequestBody CostDatabase costDatabase) {
        if(validDuplicateName(costDatabase)){
            return AjaxResult.error("名称已存在");
        }
        return toAjax(costDatabaseService.updateCostDatabase(costDatabase));
    }

    /**
     * 删除多源数据库设置
     */
    @Log(title = "多源数据库设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除多源数据库设置")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(costDatabaseService.deleteCostDatabaseByIds(ids));
    }

    private boolean validDuplicateName(CostDatabase costDatabase){
        costDatabase.setName(costDatabase.getName().trim());
        QueryWrapper<CostDatabase> qw = new QueryWrapper<>();
        qw.lambda().select(CostDatabase::getId, CostDatabase::getName);
        qw.lambda().eq(CostDatabase::getName, costDatabase.getName());
        if(null != costDatabase.getId()){
            qw.lambda().ne(CostDatabase::getId, costDatabase.getId());
        }
        CostDatabase one = costDatabaseService.getOne(qw);
        if(one != null){
            return true;
        }

        return false;
    }
}
