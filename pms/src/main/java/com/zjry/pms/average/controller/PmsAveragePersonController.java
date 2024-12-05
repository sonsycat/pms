package com.zjry.pms.average.controller;

import java.util.List;

import com.zjry.pms.average.domain.PmsAveragePersonDetail;
import com.zjry.pms.average.service.IPmsAveragePersonDetailService;
import com.zjry.pms.bonus.domain.PmsDeptSort;
import io.swagger.annotations.*;
import org.apache.commons.collections4.CollectionUtils;
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
import com.zjry.pms.average.domain.PmsAveragePerson;
import com.zjry.pms.average.service.IPmsAveragePersonService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 平均奖科人数Controller
 *
 * @author zwf
 * @date 2022-09-27
 */
@RestController
@RequestMapping("/average/average")
@Api(tags={"【平均奖科人数】"})
public class PmsAveragePersonController extends BaseController {
    @Autowired
    private IPmsAveragePersonService pmsAveragePersonService;

    /**
     * 查询平均奖科人数列表
     */
    @PreAuthorize("@ss.hasPermi('average:average:list')")
    @GetMapping("/list")
    @ApiOperation("查询平均奖科人数列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsAveragePerson pmsAveragePerson) {
        startPage();
        List<PmsAveragePerson> list = pmsAveragePersonService.selectPmsAveragePersonList(pmsAveragePerson);
        return getDataTable(list);
    }

    /**
     * 导出平均奖科人数列表
     */
    @PreAuthorize("@ss.hasPermi('average:average:export')")
    @Log(title = "平均奖科人数", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出平均奖科人数列表Excel")
    public AjaxResult export(PmsAveragePerson pmsAveragePerson) {
        List<PmsAveragePerson> list = pmsAveragePersonService.selectPmsAveragePersonList(pmsAveragePerson);
        ExcelUtil<PmsAveragePerson> util = new ExcelUtil<PmsAveragePerson>(PmsAveragePerson.class);
        return util.exportExcel(list, "平均奖科人数数据");
    }

    /**
     * 获取平均奖科人数详细信息
     */
    @PreAuthorize("@ss.hasPermi('average:average:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取平均奖科人数详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsAveragePersonService.selectPmsAveragePersonById(id));
    }

    /**
     * 新增平均奖科人数
     */
    @PreAuthorize("@ss.hasPermi('average:average:add')")
    @Log(title = "平均奖科人数", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增平均奖科人数")
    public AjaxResult add(@RequestBody PmsAveragePerson pmsAveragePerson) {
        return toAjax(pmsAveragePersonService.insertPmsAveragePerson(pmsAveragePerson));
    }

    /**
     * 修改平均奖科人数
     */
    @PreAuthorize("@ss.hasPermi('average:average:edit')")
    @Log(title = "平均奖科人数", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改平均奖科人数")
    public AjaxResult edit(@RequestBody PmsAveragePerson pmsAveragePerson) {
        return toAjax(pmsAveragePersonService.updatePmsAveragePerson(pmsAveragePerson));
    }

    /**
     * 删除平均奖科人数
     */
    @PreAuthorize("@ss.hasPermi('average:average:remove')")
    @Log(title = "平均奖科人数", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除平均奖科人数")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsAveragePersonService.deletePmsAveragePersonByIds(ids));
    }

    /**
     * 获取平均奖科人数最新日期
     */
    @PreAuthorize("@ss.hasPermi('average:average:add')")
    @GetMapping(value = "/getNewDate")
    @ApiOperation("获取平均奖科人数最新日期")
    public AjaxResult getNewDate() {
        return AjaxResult.success(pmsAveragePersonService.selectAveragePersonNewDate());
    }

    /**
     * 通过年月复制数据
     */
    @PostMapping("/copy")
    @ApiOperation("复制平均奖科人数信息")
    public AjaxResult copy(@RequestBody PmsAveragePerson pmsAveragePerson) {
        if(pmsAveragePerson.getSetDate() == null || pmsAveragePerson.getCopyDate() == null){
            return error("参数为空");
        }
        try {
            List<PmsAveragePerson> list = pmsAveragePersonService.selectPmsAveragePersonByDate(pmsAveragePerson);
            if(CollectionUtils.isEmpty(list)){
                return error("复制年月没有数据");
            }
            PmsAveragePerson param = new PmsAveragePerson();
            param.setSetDate(pmsAveragePerson.getCopyDate());
            list = pmsAveragePersonService.selectPmsAveragePersonByDate(param);
            if(CollectionUtils.isNotEmpty(list)){
                return error("拷贝年月已有数据");
            }
            // 复制数据
            pmsAveragePersonService.copyData(pmsAveragePerson);
        } catch (Exception e) {
            logger.error("系统错误", e);
            return error();
        }
        return success();
    }
}
