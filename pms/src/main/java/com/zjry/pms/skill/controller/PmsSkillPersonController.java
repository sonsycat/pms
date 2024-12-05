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
import com.zjry.pms.skill.domain.PmsSkillPerson;
import com.zjry.pms.skill.service.IPmsSkillPersonService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 技术参与人Controller
 *
 * @author maozt
 * @date 2022-08-01
 */
@RestController
@RequestMapping("/skill/person")
@Api(tags={"【技术参与人】"})
public class PmsSkillPersonController extends BaseController {
    @Autowired
    private IPmsSkillPersonService pmsSkillPersonService;

    /**
     * 查询技术参与人列表
     */
    @GetMapping("/list")
    @ApiOperation("查询技术参与人列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsSkillPerson pmsSkillPerson) {
        startPage();
        List<PmsSkillPerson> list = pmsSkillPersonService.selectPmsSkillPersonList(pmsSkillPerson);
        return getDataTable(list);
    }

    /**
     * 导出技术参与人列表
     */
    @Log(title = "技术参与人", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出技术参与人列表Excel")
    public AjaxResult export(PmsSkillPerson pmsSkillPerson) {
        List<PmsSkillPerson> list = pmsSkillPersonService.selectPmsSkillPersonList(pmsSkillPerson);
        ExcelUtil<PmsSkillPerson> util = new ExcelUtil<PmsSkillPerson>(PmsSkillPerson.class);
        return util.exportExcel(list, "技术参与人数据");
    }

    /**
     * 获取技术参与人详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取技术参与人详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsSkillPersonService.selectPmsSkillPersonById(id));
    }

    /**
     * 新增技术参与人
     */
    @Log(title = "技术参与人", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增技术参与人")
    public AjaxResult add(@RequestBody PmsSkillPerson pmsSkillPerson) {
        return toAjax(pmsSkillPersonService.insertPmsSkillPerson(pmsSkillPerson));
    }

    /**
     * 修改技术参与人
     */
    @Log(title = "技术参与人", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改技术参与人")
    public AjaxResult edit(@RequestBody PmsSkillPerson pmsSkillPerson) {
        pmsSkillPersonService.deletePmsSkillPerson(pmsSkillPerson);
        return toAjax(pmsSkillPersonService.insertPmsSkillPersonPl(pmsSkillPerson));
    }

    /**
     * 删除技术参与人
     */
    @Log(title = "技术参与人", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除技术参与人")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsSkillPersonService.deletePmsSkillPersonByIds(ids));
    }
}
