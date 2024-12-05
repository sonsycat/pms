package com.zjry.pms.bonus.controller;

import java.util.List;

import com.zjry.pms.bonus.domain.PmsDeptSortDto;
import com.zjry.pms.common.utils.UserUtils;
import io.swagger.annotations.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.enums.BusinessType;
import com.zjry.pms.bonus.domain.PmsDeptSort;
import com.zjry.pms.bonus.service.IPmsDeptSortService;
import com.zjry.common.utils.poi.ExcelUtil;

/**
 * 核算单位类别设置Controller
 *
 * @author zjry
 * @date 2022-09-27
 */
@RestController
@RequestMapping("/bonus/deptSort")
@Api(tags={"【核算单位类别设置】"})
public class PmsDeptSortController extends BaseController {
    @Autowired
    private IPmsDeptSortService pmsDeptSortService;

    /**
     * 查询核算单位类别设置列表
     */
    @PreAuthorize("@ss.hasPermi('bonus:deptSort:list')")
    @GetMapping("/list")
    @ApiOperation("查询核算单位类别设置列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public List list(PmsDeptSort pmsDeptSort) {
        pmsDeptSort.setCompCode(UserUtils.getSysUser().getDeptId());
        return pmsDeptSortService.selectPmsDeptSortList(pmsDeptSort);
    }

    /**
     * 导出核算单位类别设置列表
     */
    @PreAuthorize("@ss.hasPermi('bonus:deptSort:export')")
    @Log(title = "核算单位类别设置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出核算单位类别设置列表Excel")
    public AjaxResult export(PmsDeptSort pmsDeptSort) {
        List<PmsDeptSort> list = pmsDeptSortService.selectPmsDeptSortList(pmsDeptSort);
        ExcelUtil<PmsDeptSort> util = new ExcelUtil<PmsDeptSort>(PmsDeptSort.class);
        return util.exportExcel(list, "核算单位类别设置数据");
    }

    /**
     * 获取核算单位类别设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('bonus:deptSort:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取核算单位类别设置详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsDeptSortService.selectPmsDeptSortById(id));
    }

    /**
     * 保存数据
     */
    @PreAuthorize("@ss.hasPermi('bonus:deptSort:save')")
    @Log(title = "保存数据", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("保存数据")
    public AjaxResult add(@RequestBody PmsDeptSortDto data) {
        return toAjax(pmsDeptSortService.saveData(data));
    }

    /**
     * 删除核算单位类别设置
     */
    @PreAuthorize("@ss.hasPermi('bonus:deptSort:remove')")
    @Log(title = "核算单位类别设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除核算单位类别设置")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsDeptSortService.deletePmsDeptSortByIds(ids));
    }

    /**
     * 通过年月复制数据
     */
    @PostMapping("/copy")
    @PreAuthorize("@ss.hasPermi('bonus:deptSort:copy')")
    @ApiOperation("新增指标集与科室管理")
    public AjaxResult copy(@RequestBody PmsDeptSort pmsDeptSort) {
        if(pmsDeptSort.getNowDate() == null || pmsDeptSort.getCopyDate() == null){
            return error("参数为空");
        }
        try {
            PmsDeptSort param = new PmsDeptSort();
            param.setNowDate(pmsDeptSort.getCopyDate());
            List<PmsDeptSort> list = pmsDeptSortService.queryList(param);
            if(CollectionUtils.isNotEmpty(list)){
                return error("当前年月已有数据");
            }

            list = pmsDeptSortService.queryList(pmsDeptSort);
            if(CollectionUtils.isEmpty(list)){
                return error("需复制年月没有数据");
            }

            pmsDeptSort.preInsert();
            pmsDeptSortService.copyData(pmsDeptSort);
        } catch (Exception e) {
            logger.error("系统错误", e);
            return error();
        }
        return success();
    }
}
