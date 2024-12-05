package com.zjry.pms.bonus.controller;

import com.alibaba.fastjson.JSONArray;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.pms.bonus.domain.PmsAccountDeptNew;
import com.zjry.pms.bonus.domain.PmsCopyAccountDeptNew;
import com.zjry.pms.bonus.domain.PmsSaveAccountDeptNew;
import com.zjry.pms.bonus.service.IPmsAccountDeptNewService;
import com.zjry.pms.bonus.service.IPmsAccountDeptService;
import com.zjry.pms.common.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 核算科室设置Controller
 *
 * @author zjry
 * @date 2022-09-29
 */
@RestController
@RequestMapping("/bonus/accountDeptNew")
@Api(tags={"【核算科室设置】"})
public class PmsAccountDeptNewController extends BaseController {
    @Autowired
    private IPmsAccountDeptService pmsAccountDeptService;
    @Autowired
    private IPmsAccountDeptNewService pmsAccountDeptNewService;

    /**
     * 查询核算科室设置列表
     */
    @PreAuthorize("@ss.hasPermi('bonus:accountDeptNew:list')")
    @GetMapping("/list")
    @ApiOperation("查询核算科室设置列表")
    public List list(PmsAccountDeptNew pmsAccountDeptNew) {
        List<PmsAccountDeptNew> pmsAccountDeptNewList = pmsAccountDeptNewService.selectPmsAccountDeptList(pmsAccountDeptNew);
        return pmsAccountDeptNewList;
    }
    @PostMapping("/save")
    @PreAuthorize("@ss.hasPermi('bonus:accountDeptNew:save')")
    @ApiOperation("保存核算科室信息")
    public AjaxResult save(@RequestBody String data) {
        JSONArray jsonArray = JSONArray.parseArray(data);
        List<PmsSaveAccountDeptNew> pmsSaveAccountDeptNewList = jsonArray.toJavaList(PmsSaveAccountDeptNew.class);
        pmsAccountDeptNewService.saveAccountDeptNew(pmsSaveAccountDeptNewList);
        return success();
    }

    /**
     * 通过年月复制数据
     */
    @PostMapping("/copy")
    @PreAuthorize("@ss.hasPermi('bonus:accountDeptNew:copy')")
    @ApiOperation("复制科室核算类别")
    public AjaxResult copyNew(@RequestBody PmsCopyAccountDeptNew pmsCopyAccountDeptNew) {
        if(pmsCopyAccountDeptNew.getNowDate() == null || pmsCopyAccountDeptNew.getCopyDate() == null){
            return error("参数为空");
        }
        try {
            PmsCopyAccountDeptNew param = new PmsCopyAccountDeptNew();
            param.setNowDate(pmsCopyAccountDeptNew.getNowDate());
            param.setCompCode(UserUtils.getSysUser().getDeptId());
            List<PmsAccountDeptNew> nowList = pmsAccountDeptNewService.queryList(param);
            if(CollectionUtils.isNotEmpty(nowList)){
                return error("当前年月已有数据");
            }
            param.setNowDate(pmsCopyAccountDeptNew.getCopyDate());
            List<PmsAccountDeptNew> copyList = pmsAccountDeptNewService.queryList(param);
            if(CollectionUtils.isEmpty(copyList)){
                return error("需复制年月没有数据");
            }
            pmsAccountDeptNewService.copyData(pmsCopyAccountDeptNew,copyList);
        } catch (Exception e) {
            logger.error("系统错误", e);
            return error();
        }
        return success();
    }

}
