package com.zjry.pms.bonus.controller;

import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.DateUtils;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.pms.average.domain.PmsAveragePersonDetail;
import com.zjry.pms.basic.domain.PmsHisIncome;
import com.zjry.pms.bonus.domain.PmsBonusEnter;
import com.zjry.pms.bonus.domain.PmsBonusPersonAudit;
import com.zjry.pms.bonus.service.IPmsBonusEnterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * 奖金导入Controller
 *
 * @author zjry
 * @date 2022-10-19
 */
@RestController
@RequestMapping("/bonus/enter")
@Api(tags = {"【奖金导入】"})
public class PmsBonusEnterController extends BaseController {

    @Autowired
    private IPmsBonusEnterService pmsBonusEnterService;

    /**
     * 查询奖金导入列表
     */
    @PreAuthorize("@ss.hasPermi('bonus:enter:list')")
    @GetMapping("/list")
    @ApiOperation("查询奖金导入列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsBonusEnter pmsBonusEnter) {
        startPage();
        List<PmsBonusEnter> list = pmsBonusEnterService.selectPmsBonusEnterList(pmsBonusEnter);
        return getDataTable(list);
    }
    /*
     * 获取奖金导入详细信息
     */
//    @PreAuthorize("@ss.hasPermi('bonus:calculation:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取奖金导入详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsBonusEnterService.selectPmsBonusEnterById(id));
    }

    /**
     * 删除奖金导入
     */
    @PreAuthorize("@ss.hasPermi('bonus:enter:remove')")
    @Log(title = "奖金导入", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除奖金导入")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsBonusEnterService.deletePmsBonusEnterByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('bonus:enter:save')")
    @Log(title = "奖金提交", businessType = BusinessType.UPDATE)
    @PostMapping("/save")
    @ApiOperation("奖金提交")
    public AjaxResult commitAudit(@RequestBody List<PmsBonusEnter> pmsBonusEnterList) {
        int i = pmsBonusEnterService.insertPmsBonusEnterLists(pmsBonusEnterList);
        if(i > 0){
            return AjaxResult.success("奖金数据已生成");
        }else{
            return AjaxResult.success("奖金数据已生成，数据为空");
        }
    }
    @PreAuthorize("@ss.hasPermi('bonus:enter:export')")
    @Log(title = "奖金导入数据", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出模板Excel")
    public AjaxResult export(PmsBonusEnter pmsBonusEnter) {
        List<PmsBonusEnter> list = new ArrayList<>();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (loginUser != null) {
            PmsBonusEnter dto = new PmsBonusEnter();
            dto.setDeptCode(loginUser.getUser().getDeptRoomId());
            dto.setDeptName(loginUser.getUser().getDeptRoomName());
            list.add(dto);
        }

        ExcelUtil<PmsBonusEnter> util = new ExcelUtil<PmsBonusEnter>(PmsBonusEnter.class);
        return util.exportExcel(list, "奖金导入数据");
    }
    @Log(title = "奖金绩效明细", businessType = BusinessType.EXPORT)
    @GetMapping("/exportDetail")
    @ApiOperation("导出奖金绩效明细列表Excel")
    public AjaxResult exportDetail(PmsBonusEnter pmsBonusEnter) {
        List<PmsBonusEnter> list = pmsBonusEnterService.selectPmsBonusEnterList(pmsBonusEnter);
        ExcelUtil<PmsBonusEnter> util = new ExcelUtil<PmsBonusEnter>(PmsBonusEnter.class);
        return util.exportExcel(list, "奖金绩效明细数据");
    }

    @PostMapping("/importData")
    @ApiOperation("导入奖金")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, String enterTime) {
        ExcelUtil<PmsBonusEnter> util = new ExcelUtil<PmsBonusEnter>(PmsBonusEnter.class);
        List<PmsBonusEnter> pmsBonusEnterFies = null;
        try {
            pmsBonusEnterFies = util.importExcel(file.getInputStream());
            pmsBonusEnterFies.removeAll(Collections.singleton(null)); //移除所有的null元素
            if (null != pmsBonusEnterFies && pmsBonusEnterFies.size() == 0) {
                return error("导入失败:excle格式转换失败");
            } else {
                Map map = pmsBonusEnterService.importData(pmsBonusEnterFies,enterTime);
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
    @PreAuthorize("@ss.hasPermi('bonus:enter:edit')")
    @Log(title = "奖金导入修改", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("奖金导入修改")
    public AjaxResult edit(@RequestBody PmsBonusEnter pmsBonusEnter) {
        return toAjax(pmsBonusEnterService.updatePmsBonusEnter(pmsBonusEnter));
    }
}