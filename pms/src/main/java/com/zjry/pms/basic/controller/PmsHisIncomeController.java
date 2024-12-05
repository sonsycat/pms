package com.zjry.pms.basic.controller;

import java.util.*;
import java.util.stream.Collectors;

import com.zjry.common.utils.StringUtils;
import com.zjry.pms.apportionment.domain.CostApportionmentProject;
import com.zjry.pms.common.Constants;
import com.zjry.pms.encode.domain.HrEncodeRuleTree;
import com.zjry.pms.project.domain.CostProject;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.enums.BusinessType;
import com.zjry.pms.basic.domain.PmsHisIncome;
import com.zjry.pms.basic.service.IPmsHisIncomeService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * HIS收入类别Controller
 *
 * @author maozt
 * @date 2022-06-16
 */
@RestController
@RequestMapping("/basic/income")
@Api(tags={"【HIS收入类别】"})
public class PmsHisIncomeController extends BaseController {
    @Autowired
    private IPmsHisIncomeService pmsHisIncomeService;

    /**
     * 查询HIS收入类别列表
     */
    @PreAuthorize("@ss.hasPermi('basic:income:list')")
    @GetMapping("/list")
    @ApiOperation("查询HIS收入类别列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsHisIncome pmsHisIncome) {
        startPage();
        List<PmsHisIncome> list = pmsHisIncomeService.selectPmsHisIncomeList(pmsHisIncome);
        return getDataTable(list);
    }

    /**
     * 导出HIS收入类别列表
     */
    @PreAuthorize("@ss.hasPermi('basic:income:export')")
    @Log(title = "HIS收入类别", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出HIS收入类别列表Excel")
    public AjaxResult export(PmsHisIncome pmsHisIncome) {
        List<PmsHisIncome> list = new ArrayList<>();
        ExcelUtil<PmsHisIncome> util = new ExcelUtil<PmsHisIncome>(PmsHisIncome.class);
        return util.exportExcel(list, "HIS收入类别数据");
    }

    /**
     * 获取HIS收入类别详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:income:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取HIS收入类别详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsHisIncomeService.selectPmsHisIncomeById(id));
    }

    /**
     * 新增HIS收入类别
     */
    @PreAuthorize("@ss.hasPermi('basic:income:add')")
    @Log(title = "HIS收入类别", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增HIS收入类别")
    public AjaxResult add(@RequestBody PmsHisIncome pmsHisIncome) {
        try {
            return toAjax(pmsHisIncomeService.insertPmsHisIncome(pmsHisIncome));
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 修改HIS收入类别
     */
    @PreAuthorize("@ss.hasPermi('basic:income:edit')")
    @Log(title = "HIS收入类别", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改HIS收入类别")
    public AjaxResult edit(@RequestBody PmsHisIncome pmsHisIncome) {
        try {
            return toAjax(pmsHisIncomeService.updatePmsHisIncome(pmsHisIncome));
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 删除HIS收入类别
     */
    @PreAuthorize("@ss.hasPermi('basic:income:remove')")
    @Log(title = "HIS收入类别", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除HIS收入类别")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsHisIncomeService.deletePmsHisIncomeByIds(ids));
    }
    // 导入资产盘点明细
    @PostMapping("/importData")
    @ApiOperation("导入资产盘点明细")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, Long compCode) {
        //验证标识
        Boolean isValidatePass = true;
        ExcelUtil<PmsHisIncome> util = new ExcelUtil<PmsHisIncome>(PmsHisIncome.class);
        List<PmsHisIncome> pmsHisIncomefies = null;
        try {
            pmsHisIncomefies = util.importExcel(file.getInputStream());
            pmsHisIncomefies.removeAll(Collections.singleton(null)); //移除所有的null元素
            if (pmsHisIncomefies.size() == 0) {
                return error("导入失败:excle格式转换失败");
            } else {
                pmsHisIncomeService.importData(pmsHisIncomefies,compCode);
            }
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return success();
    }
}
