package com.zjry.pms.skill.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.common.utils.StringUtils;
import com.zjry.pms.common.Constants;
import com.zjry.pms.common.utils.HttpConnectionUtil;
import com.zjry.pms.common.utils.ProcessUtils;
import com.zjry.pms.process.domain.HrProcessModel;
import com.zjry.pms.process.service.IHrProcessModelService;
import com.zjry.pms.skill.domain.PmsSkillOpinion;
import com.zjry.pms.skill.domain.PmsSkillAchieve;
import com.zjry.pms.skill.service.IPmsSkillOpinionService;
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
import com.zjry.pms.skill.domain.PmsSkillAchieve;
import com.zjry.pms.skill.service.IPmsSkillAchieveService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 成果Controller
 *
 * @author maozt
 * @date 2022-08-01
 */
@RestController
@RequestMapping("/skill/achieve")
@Api(tags={"【成果】"})
public class PmsSkillAchieveController extends BaseController {
    @Autowired
    private IPmsSkillAchieveService pmsSkillAchieveService;
    @Autowired
    private IHrProcessModelService hrProcessModelService;
    @Autowired
    private IPmsSkillOpinionService pmsSkillOpinionService;

    /**
     * 查询成果列表
     */
    @PreAuthorize("@ss.hasPermi('skill:achieve:list')")
    @GetMapping("/list")
    @ApiOperation("查询成果列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsSkillAchieve pmsSkillAchieve) {
        startPage();
        List<PmsSkillAchieve> list = pmsSkillAchieveService.selectPmsSkillAchieveList(pmsSkillAchieve);
        return getDataTable(list);
    }

    /**
     * 导出成果列表
     */
    @PreAuthorize("@ss.hasPermi('skill:achieve:export')")
    @Log(title = "成果", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出成果列表Excel")
    public AjaxResult export(PmsSkillAchieve pmsSkillAchieve) {
        List<PmsSkillAchieve> list = pmsSkillAchieveService.selectPmsSkillAchieveList(pmsSkillAchieve);
        ExcelUtil<PmsSkillAchieve> util = new ExcelUtil<PmsSkillAchieve>(PmsSkillAchieve.class);
        return util.exportExcel(list, "成果数据");
    }

    /**
     * 获取成果详细信息
     */
    @PreAuthorize("@ss.hasPermi('skill:achieve:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取成果详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        if(id== Constants.ID||id.equals(Constants.ID)) {
            HrProcessModel hrProcessModel = new HrProcessModel();
            hrProcessModel.setTabelName("pms_skill_achieve");
            List<HrProcessModel> hrProcessModelList = hrProcessModelService.selectHrProcessModelList(hrProcessModel);
            if (hrProcessModelList.size() > 0) {
                //获取流程的访问地址
                String processUrl = ProcessUtils.getConfigValueByKey("processUrl");
                String proclnsId = "";
                Map<String, String> params = new HashMap<String, String>(16);
                params = ProcessUtils.startProcess(hrProcessModelList.get(0).getProcinsId(), "pms_skill_achieve", 0, "成果", hrProcessModelList.get(0).getFormUrl());
                String result = "";
                try {
                    //启动流程获取流程的ID，proclnsId
                    result = HttpConnectionUtil.post(processUrl + "/api/wf/startProcess", params);
                } catch (Exception e) {
                    return AjaxResult.error("请求接口异常！");
                }
                if (StringUtils.isNotEmpty(result)) {
                    JSONObject json = JSONObject.parseObject(result);
                    Map<String, String> map = (Map<String, String>) json.get("data");
                    if (StringUtils.isNotEmpty(map)) {
                        proclnsId = map.get("procInsId");
                        //将流程的示例ID写到业务表中
                    }
                }
                if (StringUtils.isEmpty(proclnsId)) {
                    return AjaxResult.error("未获取到配置流程ID！");
                } else {
                    PmsSkillAchieve pmsSkillAchieve = new PmsSkillAchieve();
                    pmsSkillAchieve.setProcedureId(proclnsId);
                    return AjaxResult.success(pmsSkillAchieve);
                }
            } else {
                return AjaxResult.error("流程未匹配！");
            }
        }else {
            return AjaxResult.success(pmsSkillAchieveService.selectPmsSkillAchieveById(id));
        }
    }

    /**
     * 新增成果
     */
    @PreAuthorize("@ss.hasPermi('skill:achieve:add')")
    @Log(title = "成果", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增成果")
    public AjaxResult add(@RequestBody PmsSkillAchieve pmsSkillAchieve) {
        try {
            if (pmsSkillAchieveService.selectPmsSkillAchieveListByName(pmsSkillAchieve).size() != 0) {
                throw new Exception("编号不能重复");
            }
            pmsSkillAchieve.setAuditState(Constants.CHECK_DRAFT);
            int row = pmsSkillAchieveService.insertPmsSkillAchieve(pmsSkillAchieve);
            if(StringUtils.isNull(pmsSkillAchieve.getProcedureId())) {
                if (row > 0) {
                    HrProcessModel hrProcessModel = new HrProcessModel();
                    hrProcessModel.setTabelName("pms_skill_achieve");
                    List<HrProcessModel> hrProcessModelList = hrProcessModelService.selectHrProcessModelList(hrProcessModel);
                    if (hrProcessModelList.size() > 0) {
                        //获取流程的访问地址
                        String processUrl = ProcessUtils.getConfigValueByKey("processUrl");
                        String proclnsId = "";
                        Map<String, String> params = new HashMap<String, String>(16);
                        PmsSkillAchieve SkillAchieve = new PmsSkillAchieve();
                        SkillAchieve.setCompCode(pmsSkillAchieve.getCompCode());
                        SkillAchieve.setAchieveCode(pmsSkillAchieve.getAchieveCode());
                        pmsSkillAchieve = pmsSkillAchieveService.selectPmsSkillAchieveList(pmsSkillAchieve).get(0);
                        params = ProcessUtils.startProcess(hrProcessModelList.get(0).getProcinsId(), "pms_skill_achieve", pmsSkillAchieve.getId(), pmsSkillAchieve.getAchieveName() + "成果", hrProcessModelList.get(0).getFormUrl());
                        String result = "";
                        try {
                            //启动流程获取流程的ID，proclnsId
                            result = HttpConnectionUtil.post(processUrl + "/api/wf/startProcess", params);
                        } catch (Exception e) {
                            return AjaxResult.error("请求接口异常！");
                        }
                        if (StringUtils.isNotEmpty(result)) {
                            JSONObject json = JSONObject.parseObject(result);
                            Map<String, String> map = (Map<String, String>) json.get("data");
                            if (StringUtils.isNotEmpty(map)) {
                                proclnsId = map.get("procInsId");
                                //将流程的示例ID写到业务表中
                                pmsSkillAchieve.setProcedureId(proclnsId);
                                ;
                            }
                        }
                        if (StringUtils.isEmpty(proclnsId)) {
                            return AjaxResult.error("未获取到配置流程ID！");
                        } else {
                            pmsSkillAchieveService.updatePmsSkillAchieve(pmsSkillAchieve);
                            return AjaxResult.success();
                        }
                    } else {
                        return AjaxResult.error("流程未匹配！");
                    }
                }else {
                    return error();
                }
            }else {
                //获取流程的访问地址
                String processUrl = ProcessUtils.getConfigValueByKey("processUrl");
                Map<String, String> params = new HashMap<String, String>(16);
                params = ProcessUtils.startProcess(pmsSkillAchieve.getProcedureId(),pmsSkillAchieve.getId(),pmsSkillAchieve.getAchieveName()+"成果");
                try {
                    //启动流程获取流程的ID，proclnsId
                    HttpConnectionUtil.post(processUrl+"/api/wf/addBusinessData", params);
                } catch (Exception e) {
                    return AjaxResult.error("请求接口异常！");
                }
                return AjaxResult.success();
            }
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 修改成果
     */
    @PreAuthorize("@ss.hasPermi('skill:achieve:edit')")
    @Log(title = "成果", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改成果")
    public AjaxResult edit(@RequestBody PmsSkillAchieve pmsSkillAchieve) {
        try {
            if (pmsSkillAchieveService.selectPmsSkillAchieveListByName(pmsSkillAchieve).size() != 0) {
                throw new Exception("编号不能重复");
            }
            return toAjax(pmsSkillAchieveService.updatePmsSkillAchieve(pmsSkillAchieve));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 修改成果
     */
    @PreAuthorize("@ss.hasPermi('skill:topic:edit')")
    @Log(title = "成果", businessType = BusinessType.UPDATE)
    @PutMapping("/upd")
    @ApiOperation("修改成果")
    public AjaxResult upd(@RequestBody PmsSkillAchieve pmsSkillAchieve) {
        try {
            if (pmsSkillAchieveService.selectPmsSkillAchieveListByName(pmsSkillAchieve).size() > 0) {
                throw new Exception("编号不能重复");
            }
            int row = 0;
            if(StringUtils.isNull(pmsSkillAchieve.getId())){
                pmsSkillAchieve.setAuditState(Constants.CHECK_DRAFT);
                row = pmsSkillAchieveService.insertPmsSkillAchieve(pmsSkillAchieve);
                //获取流程的访问地址
                String processUrl = ProcessUtils.getConfigValueByKey("processUrl");
                Map<String, String> params = new HashMap<String, String>(16);
                params = ProcessUtils.startProcess(pmsSkillAchieve.getProcedureId(),pmsSkillAchieve.getId(),pmsSkillAchieve.getAchieveName()+"成果");
                try {
                    //启动流程获取流程的ID，proclnsId
                    HttpConnectionUtil.post(processUrl+"/api/wf/addBusinessData", params);
                } catch (Exception e) {
                    return AjaxResult.error("请求接口异常！");
                }
            }else{
                row = pmsSkillAchieveService.updatePmsSkillAchieve(pmsSkillAchieve);
            }
            pmsSkillAchieve.setAuditState(Constants.CHECK_SUBMIT);
            row = pmsSkillAchieveService.updatePmsSkillAchieve(pmsSkillAchieve);
            if(row>0){
                String processUrl = ProcessUtils.getConfigValueByKey("processUrl");
                Map<String, String> params = new HashMap<String, String>(16);
                params = ProcessUtils.dealProcess(pmsSkillAchieve.getProcedureId(), pmsSkillAchieve.getComment(), pmsSkillAchieve.getAssignee());
                String result="";
                try {
                    params.put("actionName", "提交");
                    result = HttpConnectionUtil.post(processUrl+"/api/wf/completeTask", params);
                } catch (Exception e) {
                    return AjaxResult.error("请求接口异常！");
                }
                if(StringUtils.isEmpty(result)){
                    return AjaxResult.error("请求接口有误！");
                }else{
                    JSONObject json = JSONObject.parseObject(result);
                    int code1 = (int) json.get("code");
                    if(code1 == Constants.HTTP_CODE){
                        JSONObject data = JSONObject.parseObject(json.get("data").toString());

                        int finishStatus = (int) data.get("finishStatus");
                        //如果是流程最后一个节点将数据状态变为通过Constants.CHECK_PASS
                        if(finishStatus==Constants.HTTP_CODE1){
                            pmsSkillAchieve.setAuditState(Constants.CHECK_PASS);
                            pmsSkillAchieveService.updatePmsSkillAchieve(pmsSkillAchieve);
                        }else{
                            pmsSkillAchieve.setAuditState(Constants.CHECK_SUBMIT);
                            pmsSkillAchieveService.updatePmsSkillAchieve(pmsSkillAchieve);
                        }
                        PmsSkillOpinion pmsSkillOpinion = new PmsSkillOpinion();
                        pmsSkillOpinion.setCompCode(pmsSkillAchieve.getCompCode());
                        pmsSkillOpinion.setTechnoCode(pmsSkillAchieve.getAchieveCode());
                        pmsSkillOpinion.setOpinion(pmsSkillAchieve.getComment());
                        pmsSkillOpinion.setApprovelTime(new Date());
                        LoginUser loginUser = SecurityUtils.getLoginUser();
                        if (loginUser != null) {
                            pmsSkillOpinion.setApprovelName(loginUser.getUser().getNickName());
                        }
                        pmsSkillOpinion.setAuditState(Constants.CHECK_SUBMIT);
                        pmsSkillOpinion.setTechnoType(Constants.TECHNO_TYPE_CG);
                        pmsSkillOpinionService.insertPmsSkillOpinion(pmsSkillOpinion);
                        return AjaxResult.success("成果审核通过!");
                    }else if(code1 == Constants.HTTP_CODE2){
                        return AjaxResult.error("没有权限审核！");
                    }
                }
            }
            return success();
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 修改成果
     */
    @PreAuthorize("@ss.hasPermi('skill:topic:edit')")
    @Log(title = "成果", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    @ApiOperation("修改成果")
    public AjaxResult audit(@RequestBody PmsSkillAchieve pmsSkillAchieve) {
        try {
            if(ObjectUtils.isEmpty(pmsSkillAchieve.getId())){
                return AjaxResult.error("成果ID不能为空");
            }else{
                String processUrl = ProcessUtils.getConfigValueByKey("processUrl");
                Map<String, String> params = new HashMap<String, String>(16);
                params = ProcessUtils.dealProcess(pmsSkillAchieve.getProcedureId(), pmsSkillAchieve.getComment(), pmsSkillAchieve.getAssignee());
                String result="";
                //点击审核通过按钮时判断的状态这个参数getAuditState()得和前台的一致
                if(pmsSkillAchieve.getAuditState().equals(Constants.CHECK_PASS)){
                    params.put("actionName", "通过");
                    try {
                        result = HttpConnectionUtil.post(processUrl+"/api/wf/completeTask", params);
                    } catch (Exception e) {
                        return AjaxResult.error("请求接口异常！");
                    }
                    if(StringUtils.isEmpty(result)){
                        return AjaxResult.error("请求接口有误！");
                    }else{
                        JSONObject json = JSONObject.parseObject(result);
                        int code = (int) json.get("code");
                        if(code == Constants.HTTP_CODE){
                            JSONObject data = JSONObject.parseObject(json.get("data").toString());
                            int finishStatus = (int) data.get("finishStatus");
                            //如果是流程最后一个节点将数据状态变为通过Constants.CHECK_PASS
                            if(finishStatus==Constants.HTTP_CODE1){
                                pmsSkillAchieve.setAuditState(Constants.CHECK_PASS);
                                pmsSkillAchieveService.updatePmsSkillAchieve(pmsSkillAchieve);
                            }
                            PmsSkillOpinion pmsSkillOpinion = new PmsSkillOpinion();
                            pmsSkillOpinion.setOpinion(pmsSkillAchieve.getComment());
                            pmsSkillOpinion.setApprovelTime(new Date());
                            LoginUser loginUser = SecurityUtils.getLoginUser();
                            if (loginUser != null) {
                                pmsSkillOpinion.setApprovelName(loginUser.getUser().getNickName());
                            }
                            pmsSkillAchieve = pmsSkillAchieveService.selectPmsSkillAchieveById(pmsSkillAchieve.getId());
                            pmsSkillOpinion.setCompCode(pmsSkillAchieve.getCompCode());
                            pmsSkillOpinion.setTechnoCode(pmsSkillAchieve.getAchieveCode());
                            pmsSkillOpinion.setAuditState(Constants.CHECK_PASS);
                            pmsSkillOpinion.setTechnoType(Constants.TECHNO_TYPE_CG);
                            pmsSkillOpinionService.insertPmsSkillOpinion(pmsSkillOpinion);
                            return AjaxResult.success("成果审核通过!");
                        }else if(code == Constants.HTTP_CODE2){
                            return AjaxResult.error("没有权限审核！");
                        }
                    }
                    //驳回
                }else if(pmsSkillAchieve.getAuditState().equals(Constants.CHECK_REFUSE)){
                    try {
                        result = HttpConnectionUtil.post(processUrl+"/api/wf/backTask", params);
                    } catch (Exception e) {
                        return AjaxResult.error("请求接口异常！");
                    }
                    if(StringUtils.isEmpty(result)){
                        return AjaxResult.error("请求接口有误！");
                    }else{
                        JSONObject json = JSONObject.parseObject(result);
                        int code = (int) json.get("code");
                        if(code == Constants.HTTP_CODE){
                            JSONObject data = JSONObject.parseObject(json.get("data").toString());
                            boolean isFirstActivity = (boolean) data.get("isFirstActivity");
                            //如果是流程第一个节点将数据状态变为草稿Constants.CHECK_DRAFT，其余驳回数据状态不填
                            if(isFirstActivity){
                                pmsSkillAchieve.setAuditState(Constants.CHECK_DRAFT);
                                pmsSkillAchieveService.updatePmsSkillAchieve(pmsSkillAchieve);
                            }
                            PmsSkillOpinion pmsSkillOpinion = new PmsSkillOpinion();
                            pmsSkillOpinion.setOpinion(pmsSkillAchieve.getComment());
                            pmsSkillOpinion.setApprovelTime(new Date());
                            LoginUser loginUser = SecurityUtils.getLoginUser();
                            if (loginUser != null) {
                                pmsSkillOpinion.setApprovelName(loginUser.getUser().getNickName());
                            }
                            pmsSkillAchieve = pmsSkillAchieveService.selectPmsSkillAchieveById(pmsSkillAchieve.getId());
                            pmsSkillOpinion.setCompCode(pmsSkillAchieve.getCompCode());
                            pmsSkillOpinion.setTechnoCode(pmsSkillAchieve.getAchieveCode());
                            pmsSkillOpinion.setAuditState(Constants.CHECK_REFUSE);
                            pmsSkillOpinion.setTechnoType(Constants.TECHNO_TYPE_CG);
                            pmsSkillOpinionService.insertPmsSkillOpinion(pmsSkillOpinion);
                            return AjaxResult.success("成果审核驳回!");
                        }else if(code == Constants.HTTP_CODE2){
                            return AjaxResult.error("没有权限审核！");
                        }
                    }
                    //终止
                }else if(pmsSkillAchieve.getAuditState().equals(Constants.CHECK_STOP)){
                    params.put("stopReason",pmsSkillAchieve.getComment());
                    try {
                        result = HttpConnectionUtil.post(processUrl+"/api/wf/stopProcess", params);
                    } catch (Exception e) {
                        return AjaxResult.error("请求接口异常！");
                    }
                    if(StringUtils.isEmpty(result)){
                        return AjaxResult.error("请求接口有误！");
                    }else{
                        JSONObject json = JSONObject.parseObject(result);
                        int code = (int) json.get("code");
                        //将数据状态变为终止Constants.CHECK_STOP
                        if(code == Constants.HTTP_CODE){
                            pmsSkillAchieve.setAuditState(Constants.CHECK_STOP);
                            pmsSkillAchieveService.updatePmsSkillAchieve(pmsSkillAchieve);
                            PmsSkillOpinion pmsSkillOpinion = new PmsSkillOpinion();
                            pmsSkillOpinion.setOpinion(pmsSkillAchieve.getComment());
                            pmsSkillOpinion.setApprovelTime(new Date());
                            LoginUser loginUser = SecurityUtils.getLoginUser();
                            if (loginUser != null) {
                                pmsSkillOpinion.setApprovelName(loginUser.getUser().getNickName());
                            }
                            pmsSkillAchieve = pmsSkillAchieveService.selectPmsSkillAchieveById(pmsSkillAchieve.getId());
                            pmsSkillOpinion.setCompCode(pmsSkillAchieve.getCompCode());
                            pmsSkillOpinion.setTechnoCode(pmsSkillAchieve.getAchieveCode());
                            pmsSkillOpinion.setAuditState(Constants.CHECK_STOP);
                            pmsSkillOpinion.setTechnoType(Constants.TECHNO_TYPE_CG);
                            pmsSkillOpinionService.insertPmsSkillOpinion(pmsSkillOpinion);
                            return AjaxResult.success("成果审核终止！");
                        }else if(code == Constants.HTTP_CODE2){
                            return AjaxResult.error("没有权限审核！");
                        }
                    }
                }
            }
            return null;
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 删除成果
     */
    @PreAuthorize("@ss.hasPermi('skill:achieve:remove')")
    @Log(title = "成果", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除成果")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsSkillAchieveService.deletePmsSkillAchieveByIds(ids));
    }
}
