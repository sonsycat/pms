package com.zjry.pms.skill.controller;

import java.util.List;
import io.swagger.annotations.*;
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
import com.zjry.pms.skill.domain.PmsSkillOpinion;
import com.zjry.pms.skill.service.IPmsSkillOpinionService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 审批意见Controller
 *
 * @author maozt
 * @date 2022-07-27
 */
@RestController
@RequestMapping("/skill/opinion")
@Api(tags={"【审批意见】"})
public class PmsSkillOpinionController extends BaseController {
    @Autowired
    private IPmsSkillOpinionService pmsSkillOpinionService;

    /**
     * 查询审批意见列表
     */
    @GetMapping("/list")
    @ApiOperation("查询审批意见列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsSkillOpinion pmsSkillOpinion) {
        startPage();
        List<PmsSkillOpinion> list = pmsSkillOpinionService.selectPmsSkillOpinionList(pmsSkillOpinion);
        return getDataTable(list);
    }

    /**
     * 导出审批意见列表
     */
    @Log(title = "审批意见", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出审批意见列表Excel")
    public AjaxResult export(PmsSkillOpinion pmsSkillOpinion) {
        List<PmsSkillOpinion> list = pmsSkillOpinionService.selectPmsSkillOpinionList(pmsSkillOpinion);
        ExcelUtil<PmsSkillOpinion> util = new ExcelUtil<PmsSkillOpinion>(PmsSkillOpinion.class);
        return util.exportExcel(list, "审批意见数据");
    }

    /**
     * 获取审批意见详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取审批意见详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsSkillOpinionService.selectPmsSkillOpinionById(id));
    }

    /**
     * 新增审批意见
     */
    @Log(title = "审批意见", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增审批意见")
    public AjaxResult add(@RequestBody PmsSkillOpinion pmsSkillOpinion) {
        return toAjax(pmsSkillOpinionService.insertPmsSkillOpinion(pmsSkillOpinion));
    }

    /**
     * 修改审批意见
     */
    @Log(title = "审批意见", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改审批意见")
    public AjaxResult edit(@RequestBody PmsSkillOpinion pmsSkillOpinion) {
        return toAjax(pmsSkillOpinionService.updatePmsSkillOpinion(pmsSkillOpinion));
    }

    /**
     * 删除审批意见
     */
    @Log(title = "审批意见", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除审批意见")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsSkillOpinionService.deletePmsSkillOpinionByIds(ids));
    }
}
