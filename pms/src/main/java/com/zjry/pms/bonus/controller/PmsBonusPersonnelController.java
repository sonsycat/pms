package com.zjry.pms.bonus.controller;

import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.pms.bonus.domain.PmsBonusPersonnel;
import com.zjry.pms.bonus.service.IPmsBonusPersonnelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 人事导入Controller
 *
 * @author zjry
 * @date 2022-10-19
 */
@RestController
@RequestMapping("/bonus/personnel")
@Api(tags = {"【人事导入】"})
public class PmsBonusPersonnelController extends BaseController {

    @Autowired
    private IPmsBonusPersonnelService pmsBonusPersonnelService;

    /**
     * 查询人事导入列表
     */
    @PreAuthorize("@ss.hasPermi('bonus:personnel:list')")
    @GetMapping("/list")
    @ApiOperation("查询人事导入列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsBonusPersonnel pmsBonusPersonnel) {
        startPage();
        List<PmsBonusPersonnel> list = pmsBonusPersonnelService.selectPmsBonusPersonnelList(pmsBonusPersonnel);
        return getDataTable(list);
    }
    /*
     * 获取人事导入详细信息
     */
//    @PreAuthorize("@ss.hasPermi('bonus:calculation:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取人事导入详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsBonusPersonnelService.selectPmsBonusPersonnelById(id));
    }

    /**
     * 删除人事导入
     */
    @PreAuthorize("@ss.hasPermi('bonus:personnel:remove')")
    @Log(title = "人事导入", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除人事导入")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsBonusPersonnelService.deletePmsBonusPersonnelByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('bonus:personnel:save')")
    @Log(title = "奖金提交", businessType = BusinessType.UPDATE)
    @PostMapping("/save")
    @ApiOperation("奖金提交")
    public AjaxResult commitAudit(@RequestBody List<PmsBonusPersonnel> pmsBonusPersonnelList) {
        int i = pmsBonusPersonnelService.insertPmsBonusPersonnelLists(pmsBonusPersonnelList);
        if(i > 0){
            return AjaxResult.success("人事数据已生成");
        }else{
            return AjaxResult.success("人事数据已生成，数据为空");
        }
    }
    @PreAuthorize("@ss.hasPermi('bonus:personnel:export')")
    @Log(title = "人事导入数据", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出模板Excel")
    public AjaxResult export(PmsBonusPersonnel pmsBonusPersonnel) {
        List<PmsBonusPersonnel> list = new ArrayList<>();
        ExcelUtil<PmsBonusPersonnel> util = new ExcelUtil<PmsBonusPersonnel>(PmsBonusPersonnel.class);
        return util.exportExcel(list, "人事导入数据");
    }
    @Log(title = "人事明细", businessType = BusinessType.EXPORT)
    @GetMapping("/exportDetail")
    @ApiOperation("导出人事明细列表Excel")
    public AjaxResult exportDetail(PmsBonusPersonnel pmsBonusPersonnel) {
        List<PmsBonusPersonnel> list = pmsBonusPersonnelService.selectPmsBonusPersonnelList(pmsBonusPersonnel);
        ExcelUtil<PmsBonusPersonnel> util = new ExcelUtil<PmsBonusPersonnel>(PmsBonusPersonnel.class);
        return util.exportExcel(list, "人事明细数据");
    }

    @PostMapping("/importData")
    @ApiOperation("导入奖金")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, String personnelTime) {
        ExcelUtil<PmsBonusPersonnel> util = new ExcelUtil<PmsBonusPersonnel>(PmsBonusPersonnel.class);
        List<PmsBonusPersonnel> pmsBonusPersonnelFiles = null;
        try {
            pmsBonusPersonnelFiles = util.importExcel(file.getInputStream());
            pmsBonusPersonnelFiles.removeAll(Collections.singleton(null)); //移除所有的null元素
            if (null != pmsBonusPersonnelFiles && pmsBonusPersonnelFiles.size() == 0) {
                return error("导入失败:excle格式转换失败");
            } else {
                Map map = pmsBonusPersonnelService.importData(pmsBonusPersonnelFiles,personnelTime);
                if("2".equals(map.get("result"))){
                    return error(map.get("mes").toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return error("导入失败：模板异常！");
        }
        return success();
    }
    @PreAuthorize("@ss.hasPermi('bonus:personnel:edit')")
    @Log(title = "人事导入修改", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("人事导入修改")
    public AjaxResult edit(@RequestBody PmsBonusPersonnel pmsBonusPersonnel) {
        return toAjax(pmsBonusPersonnelService.updatePmsBonusPersonnel(pmsBonusPersonnel));
    }
}
