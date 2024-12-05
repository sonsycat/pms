package com.zjry.pms.bonus.controller;

import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.pms.bonus.domain.PmsBonusEnter;
import com.zjry.pms.bonus.domain.PmsBonusStaff;
import com.zjry.pms.bonus.service.IPmsBonusStaffService;
import com.zjry.pms.evaluate.domain.PmsEvaluateData;
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
 * 奖金核算Controller
 *
 * @author zjry
 * @date 2022-10-19
 */
@RestController
@RequestMapping("/bonus/staff")
@Api(tags = {"【奖金核算】"})
public class PmsBonusStaffController extends BaseController {

    @Autowired
    private IPmsBonusStaffService pmsBonusStaffService;


    @GetMapping("/listBonusStaff")
    @ApiOperation("获取科室对应人员奖金指标奖金值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public AjaxResult listBonusStaff(PmsEvaluateData pmsEvaluateData){
        return AjaxResult.success(pmsBonusStaffService.selectListBonusStaff(pmsEvaluateData));
    }
    @PreAuthorize("@ss.hasPermi('bonus:staff:save')")
    @Log(title = "员工奖金提交", businessType = BusinessType.UPDATE)
    @PostMapping("/save")
    @ApiOperation("员工奖金提交")
    public AjaxResult saveBonusStaff(@RequestBody List<PmsBonusStaff> pmsBonusStaffList) {
        Map<String,String> map = pmsBonusStaffService.saveBonusStaff(pmsBonusStaffList);
        if("1".equals(map.get("result"))){
            return error(map.get("mes").toString());
        }
        return AjaxResult.success("保存成功");

    }
    @PreAuthorize("@ss.hasPermi('bonus:staff:export')")
    @Log(title = "员工奖金导入数据", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出模板Excel")
    public AjaxResult export(PmsBonusEnter pmsBonusEnter) {
        List<PmsBonusStaff> list = new ArrayList<>();
        ExcelUtil<PmsBonusStaff> util = new ExcelUtil<PmsBonusStaff>(PmsBonusStaff.class);
        return util.exportExcel(list, "员工奖金导入数据");
    }
    @Log(title = "员工奖金明细", businessType = BusinessType.EXPORT)
    @GetMapping("/exportDetail")
    @ApiOperation("导出员工奖金明细列表Excel")
    public AjaxResult exportDetail(PmsEvaluateData pmsEvaluateData) {
        List<PmsBonusStaff> list = pmsBonusStaffService.selectListBonusStaff(pmsEvaluateData);
        ExcelUtil<PmsBonusStaff> util = new ExcelUtil<PmsBonusStaff>(PmsBonusStaff.class);
        return util.exportExcel(list, "员工奖金明细数据");
    }

    @PostMapping("/importData")
    @ApiOperation("导入奖金")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, String deptCode,String actDate) {
        ExcelUtil<PmsBonusStaff> util = new ExcelUtil<PmsBonusStaff>(PmsBonusStaff.class);
        List<PmsBonusStaff> pmsBonusStaffList = null;
        try {
            pmsBonusStaffList = util.importExcel(file.getInputStream());
            pmsBonusStaffList.removeAll(Collections.singleton(null)); //移除所有的null元素
            if (null != pmsBonusStaffList && pmsBonusStaffList.size() == 0) {
                return error("导入失败:excle格式转换失败");
            } else {
                Map map = pmsBonusStaffService.importData(pmsBonusStaffList,deptCode,actDate);
                if("1".equals(map.get("result"))){
                    return error(map.get("mes").toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return error("导入失败：模板异常！");
        }
        return success();
    }
}
