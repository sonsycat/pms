package com.zjry.pms.bonus.controller;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjry.pms.common.utils.UserUtils;
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
import com.zjry.pms.bonus.domain.PmsDeptSortDetail;
import com.zjry.pms.bonus.service.IPmsDeptSortDetailService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 核算单位类别Controller
 *
 * @author zjry
 * @date 2022-09-27
 */
@RestController
@RequestMapping("/bonus/deptSortDetail")
@Api(tags={"【核算单位类别】"})
public class PmsDeptSortDetailController extends BaseController {
    @Autowired
    private IPmsDeptSortDetailService pmsDeptSortDetailService;

    /**
     * 查询核算单位类别列表
     */
    @PreAuthorize("@ss.hasPermi('bonus:deptSortDetail:list')")
    @GetMapping("/list")
    @ApiOperation("查询核算单位类别列表")
    public List list(PmsDeptSortDetail pmsDeptSortDetail) {
        pmsDeptSortDetail.setCompCode(UserUtils.getSysUser().getDeptId());
        return pmsDeptSortDetailService.selectPmsDeptSortDetailList(pmsDeptSortDetail);
    }

    /**
     * 导出核算单位类别列表
     */
    @PreAuthorize("@ss.hasPermi('bonus:deptSortDetail:export')")
    @Log(title = "核算单位类别", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出核算单位类别列表Excel")
    public AjaxResult export(PmsDeptSortDetail pmsDeptSortDetail) {
        List<PmsDeptSortDetail> list = pmsDeptSortDetailService.selectPmsDeptSortDetailList(pmsDeptSortDetail);
        ExcelUtil<PmsDeptSortDetail> util = new ExcelUtil<PmsDeptSortDetail>(PmsDeptSortDetail.class);
        return util.exportExcel(list, "核算单位类别数据");
    }

    /**
     * 获取核算单位类别详细信息
     */
    @PreAuthorize("@ss.hasPermi('bonus:deptSortDetail:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取核算单位类别详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsDeptSortDetailService.selectPmsDeptSortDetailById(id));
    }

    /**
     * 新增核算单位类别
     */
    @PreAuthorize("@ss.hasPermi('bonus:deptSortDetail:add')")
    @Log(title = "核算单位类别", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增核算单位类别")
    public AjaxResult add(@RequestBody PmsDeptSortDetail pmsDeptSortDetail) {
        if(validDuplicateName(null, pmsDeptSortDetail.getSortName())){
            return error("名称已存在");
        }
        pmsDeptSortDetail.setCompCode(UserUtils.getSysUser().getDeptId());
        pmsDeptSortDetail.setEditAllow(PmsDeptSortDetail.EDIT_ALLOW_YES);
        return toAjax(pmsDeptSortDetailService.insertPmsDeptSortDetail(pmsDeptSortDetail));
    }

    /**
     * 修改核算单位类别
     */
    @PreAuthorize("@ss.hasPermi('bonus:deptSortDetail:edit')")
    @Log(title = "核算单位类别", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改核算单位类别")
    public AjaxResult edit(@RequestBody PmsDeptSortDetail pmsDeptSortDetail) {
        if(validDuplicateName(pmsDeptSortDetail.getId(), pmsDeptSortDetail.getSortName())){
            return error("名称已存在");
        }
        return toAjax(pmsDeptSortDetailService.updatePmsDeptSortDetail(pmsDeptSortDetail));
    }

    /**
     * 删除核算单位类别
     */
    @PreAuthorize("@ss.hasPermi('bonus:deptSortDetail:remove')")
    @Log(title = "核算单位类别", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除核算单位类别")
    public AjaxResult remove(@PathVariable Long[] ids) {
        if(ids == null || ids.length == 0){
            return error("参数为空");
        }
        int rows = pmsDeptSortDetailService.deletePmsDeptSortDetailByIds(ids);
        if(rows <= 0){
            return error("已经使用的类别不可删除");
        }else if(ids.length > rows){
            return success("部分删除成功，已经使用的类别不可删除");
        }
        return success();
    }

    private boolean validDuplicateName(Long id, String name){
        QueryWrapper<PmsDeptSortDetail> qw = new QueryWrapper<>();
        qw.lambda().select(PmsDeptSortDetail::getId,PmsDeptSortDetail::getSortCode,PmsDeptSortDetail::getSortName)
                .eq(PmsDeptSortDetail::getSortName, name).last("LIMIT 1");
        PmsDeptSortDetail detail = pmsDeptSortDetailService.getOne(qw);
        if(detail != null && detail.getId().equals(id)){
            detail = null;
        }
        return detail!=null?true:false;
    }
}
