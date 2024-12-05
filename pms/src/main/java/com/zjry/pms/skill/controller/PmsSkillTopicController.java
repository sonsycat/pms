package com.zjry.pms.skill.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.common.utils.StringUtils;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.pms.common.Constants;
import com.zjry.pms.common.utils.HttpConnectionUtil;
import com.zjry.pms.common.utils.ProcessUtils;
import com.zjry.pms.process.domain.HrProcessModel;
import com.zjry.pms.process.service.IHrProcessModelService;
import com.zjry.pms.skill.domain.PmsSkillOpinion;
import com.zjry.pms.skill.domain.PmsSkillTopic;
import com.zjry.pms.skill.service.IPmsSkillOpinionService;
import com.zjry.pms.skill.service.IPmsSkillTopicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课题Controller
 *
 * @author maozt
 * @date 2022-07-26
 */
@RestController
@RequestMapping("/skill/topic")
@Api(tags={"【课题】"})
public class PmsSkillTopicController extends BaseController {
    @Autowired
    private IPmsSkillTopicService pmsSkillTopicService;
    @Autowired
    private IHrProcessModelService hrProcessModelService;
    @Autowired
    private IPmsSkillOpinionService pmsSkillOpinionService;

    /**
     * 查询课题列表
     */
    @PreAuthorize("@ss.hasPermi('skill:topic:list')")
    @GetMapping("/list")
    @ApiOperation("查询课题列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsSkillTopic pmsSkillTopic) {
        startPage();
        List<PmsSkillTopic> list = pmsSkillTopicService.selectPmsSkillTopicList(pmsSkillTopic);
        return getDataTable(list);
    }

    /**
     * 导出课题列表
     */
    @PreAuthorize("@ss.hasPermi('skill:topic:export')")
    @Log(title = "课题", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出课题列表Excel")
    public AjaxResult export(PmsSkillTopic pmsSkillTopic) {
        List<PmsSkillTopic> list = pmsSkillTopicService.selectPmsSkillTopicList(pmsSkillTopic);
        ExcelUtil<PmsSkillTopic> util = new ExcelUtil<PmsSkillTopic>(PmsSkillTopic.class);
        return util.exportExcel(list, "课题数据");
    }

    /**
     * 获取课题详细信息
     */
    @PreAuthorize("@ss.hasPermi('skill:topic:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取课题详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        if(id== Constants.ID||id.equals(Constants.ID)) {
            HrProcessModel hrProcessModel = new HrProcessModel();
            hrProcessModel.setTabelName("pms_skill_topic");
            List<HrProcessModel> hrProcessModelList = hrProcessModelService.selectHrProcessModelList(hrProcessModel);
            if (hrProcessModelList.size() > 0) {
                //获取流程的访问地址
                String processUrl = ProcessUtils.getConfigValueByKey("processUrl");
                String proclnsId = "";
                Map<String, String> params = new HashMap<String, String>(16);
                params = ProcessUtils.startProcess(hrProcessModelList.get(0).getProcinsId(), "pms_skill_topic", 0, "课题", hrProcessModelList.get(0).getFormUrl());
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
                    PmsSkillTopic pmsSkillTopic = new PmsSkillTopic();
                    pmsSkillTopic.setProcedureId(proclnsId);
                    return AjaxResult.success(pmsSkillTopic);
                }
            } else {
                return AjaxResult.error("流程未匹配！");
            }
        }else {
            return AjaxResult.success(pmsSkillTopicService.selectPmsSkillTopicById(id));
        }
    }

    /**
     * 新增课题
     */
    @PreAuthorize("@ss.hasPermi('skill:topic:add')")
    @Log(title = "课题", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增课题")
    public AjaxResult add(@RequestBody PmsSkillTopic pmsSkillTopic) {
        try {
            PmsSkillTopic skillTopic = new PmsSkillTopic();
            skillTopic.setId(pmsSkillTopic.getId());
            skillTopic.setCompCode(pmsSkillTopic.getCompCode());
            skillTopic.setTopicCode(pmsSkillTopic.getTopicCode());
            if (pmsSkillTopicService.selectPmsSkillTopicList(skillTopic).size() > 0) {
                throw new Exception("编号不能重复");
            }
            pmsSkillTopic.setAuditState(Constants.CHECK_DRAFT);
            int row = pmsSkillTopicService.insertPmsSkillTopic(pmsSkillTopic);
            if(StringUtils.isNull(pmsSkillTopic.getProcedureId())) {
                if (row > 0) {
                    HrProcessModel hrProcessModel = new HrProcessModel();
                    hrProcessModel.setTabelName("pms_skill_topic");
                    List<HrProcessModel> hrProcessModelList = hrProcessModelService.selectHrProcessModelList(hrProcessModel);
                    if (hrProcessModelList.size() > 0) {
                        //获取流程的访问地址
                        String processUrl = ProcessUtils.getConfigValueByKey("processUrl");
                        String proclnsId = "";
                        Map<String, String> params = new HashMap<String, String>(16);
                        skillTopic = new PmsSkillTopic();
                        skillTopic.setCompCode(pmsSkillTopic.getCompCode());
                        skillTopic.setTopicCode(pmsSkillTopic.getTopicCode());
                        pmsSkillTopic = pmsSkillTopicService.selectPmsSkillTopicList(skillTopic).get(0);
                        params = ProcessUtils.startProcess(hrProcessModelList.get(0).getProcinsId(), "pms_skill_topic", pmsSkillTopic.getId(), pmsSkillTopic.getTopicName() + "课题", hrProcessModelList.get(0).getFormUrl());
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
                                pmsSkillTopic.setProcedureId(proclnsId);
                                ;
                            }
                        }
                        if (StringUtils.isEmpty(proclnsId)) {
                            return AjaxResult.error("未获取到配置流程ID！");
                        } else {
                            pmsSkillTopicService.updatePmsSkillTopic(pmsSkillTopic);
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
                params = ProcessUtils.startProcess(pmsSkillTopic.getProcedureId(),pmsSkillTopic.getId(),pmsSkillTopic.getTopicName()+"课题");
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
     * 修改课题
     */
    @PreAuthorize("@ss.hasPermi('skill:topic:edit')")
    @Log(title = "课题", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改课题")
    public AjaxResult edit(@RequestBody PmsSkillTopic pmsSkillTopic) {
        try {
            PmsSkillTopic skillTopic = new PmsSkillTopic();
            skillTopic.setId(pmsSkillTopic.getId());
            skillTopic.setCompCode(pmsSkillTopic.getCompCode());
            skillTopic.setTopicCode(pmsSkillTopic.getTopicCode());
            if (pmsSkillTopicService.selectPmsSkillTopicList(skillTopic).size() > 0) {
                throw new Exception("编号不能重复");
            }
            return toAjax(pmsSkillTopicService.updatePmsSkillTopic(pmsSkillTopic));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 修改课题
     */
    @PreAuthorize("@ss.hasPermi('skill:topic:edit')")
    @Log(title = "课题", businessType = BusinessType.UPDATE)
    @PutMapping("/upd")
    @ApiOperation("修改课题")
    public AjaxResult upd(@RequestBody PmsSkillTopic pmsSkillTopic) {
        try {
            PmsSkillTopic skillTopic = new PmsSkillTopic();
            skillTopic.setId(pmsSkillTopic.getId());
            skillTopic.setCompCode(pmsSkillTopic.getCompCode());
            skillTopic.setTopicCode(pmsSkillTopic.getTopicCode());
            if (pmsSkillTopicService.selectPmsSkillTopicList(skillTopic).size() > 0) {
                throw new Exception("编号不能重复");
            }
            int row = 0;
            if(StringUtils.isNull(pmsSkillTopic.getId())){
                pmsSkillTopic.setAuditState(Constants.CHECK_DRAFT);
                row = pmsSkillTopicService.insertPmsSkillTopic(pmsSkillTopic);
                //获取流程的访问地址
                String processUrl = ProcessUtils.getConfigValueByKey("processUrl");
                Map<String, String> params = new HashMap<String, String>(16);
                params = ProcessUtils.startProcess(pmsSkillTopic.getProcedureId(),pmsSkillTopic.getId(),pmsSkillTopic.getTopicName()+"课题");
                try {
                    //启动流程获取流程的ID，proclnsId
                    HttpConnectionUtil.post(processUrl+"/api/wf/addBusinessData", params);
                } catch (Exception e) {
                    return AjaxResult.error("请求接口异常！");
                }
            }else{
                row = pmsSkillTopicService.updatePmsSkillTopic(pmsSkillTopic);
            }
            pmsSkillTopic.setAuditState(Constants.CHECK_SUBMIT);
            row = pmsSkillTopicService.updatePmsSkillTopic(pmsSkillTopic);
            if(row>0){
                String processUrl = ProcessUtils.getConfigValueByKey("processUrl");
                Map<String, String> params = new HashMap<String, String>(16);
                params = ProcessUtils.dealProcess(pmsSkillTopic.getProcedureId(), pmsSkillTopic.getComment(), pmsSkillTopic.getAssignee());
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
                            pmsSkillTopic.setAuditState(Constants.CHECK_PASS);
                            pmsSkillTopicService.updatePmsSkillTopic(pmsSkillTopic);
                        }else{
                            pmsSkillTopic.setAuditState(Constants.CHECK_SUBMIT);
                            pmsSkillTopicService.updatePmsSkillTopic(pmsSkillTopic);
                        }
                        PmsSkillOpinion pmsSkillOpinion = new PmsSkillOpinion();
                        pmsSkillOpinion.setCompCode(pmsSkillTopic.getCompCode());
                        pmsSkillOpinion.setTechnoCode(pmsSkillTopic.getTopicCode());
                        pmsSkillOpinion.setOpinion(pmsSkillTopic.getComment());
                        pmsSkillOpinion.setApprovelTime(new Date());
                        LoginUser loginUser = SecurityUtils.getLoginUser();
                        if (loginUser != null) {
                            pmsSkillOpinion.setApprovelName(loginUser.getUser().getNickName());
                        }
                        pmsSkillOpinion.setAuditState(Constants.CHECK_SUBMIT);
                        pmsSkillOpinion.setTechnoType(Constants.TECHNO_TYPE_KT);
                        pmsSkillOpinionService.insertPmsSkillOpinion(pmsSkillOpinion);
                        return AjaxResult.success("课题审核通过!");
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
     * 修改课题
     */
    @PreAuthorize("@ss.hasPermi('skill:topic:edit')")
    @Log(title = "课题", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    @ApiOperation("修改课题")
    public AjaxResult audit(@RequestBody PmsSkillTopic pmsSkillTopic) {
        try {
            if(ObjectUtils.isEmpty(pmsSkillTopic.getId())){
                return AjaxResult.error("课题ID不能为空");
            }else{
                String processUrl = ProcessUtils.getConfigValueByKey("processUrl");
                Map<String, String> params = new HashMap<String, String>(16);
                params = ProcessUtils.dealProcess(pmsSkillTopic.getProcedureId(), pmsSkillTopic.getComment(), pmsSkillTopic.getAssignee());
                String result="";
                //点击审核通过按钮时判断的状态这个参数getAuditState()得和前台的一致
                if(pmsSkillTopic.getAuditState().equals(Constants.CHECK_PASS)){
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
                                pmsSkillTopic.setAuditState(Constants.CHECK_PASS);
                                pmsSkillTopicService.updatePmsSkillTopic(pmsSkillTopic);
                            }
                            PmsSkillOpinion pmsSkillOpinion = new PmsSkillOpinion();
                            pmsSkillOpinion.setOpinion(pmsSkillTopic.getComment());
                            pmsSkillOpinion.setApprovelTime(new Date());
                            LoginUser loginUser = SecurityUtils.getLoginUser();
                            if (loginUser != null) {
                                pmsSkillOpinion.setApprovelName(loginUser.getUser().getNickName());
                            }
                            pmsSkillTopic = pmsSkillTopicService.selectPmsSkillTopicById(pmsSkillTopic.getId());
                            pmsSkillOpinion.setCompCode(pmsSkillTopic.getCompCode());
                            pmsSkillOpinion.setTechnoCode(pmsSkillTopic.getTopicCode());
                            pmsSkillOpinion.setAuditState(Constants.CHECK_PASS);
                            pmsSkillOpinion.setTechnoType(Constants.TECHNO_TYPE_KT);
                            pmsSkillOpinionService.insertPmsSkillOpinion(pmsSkillOpinion);
                            return AjaxResult.success("课题审核通过!");
                        }else if(code == Constants.HTTP_CODE2){
                            return AjaxResult.error("没有权限审核！");
                        }
                    }
                    //驳回
                }else if(pmsSkillTopic.getAuditState().equals(Constants.CHECK_REFUSE)){
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
                                pmsSkillTopic.setAuditState(Constants.CHECK_DRAFT);
                                pmsSkillTopicService.updatePmsSkillTopic(pmsSkillTopic);
                            }
                            PmsSkillOpinion pmsSkillOpinion = new PmsSkillOpinion();
                            pmsSkillOpinion.setOpinion(pmsSkillTopic.getComment());
                            pmsSkillOpinion.setApprovelTime(new Date());
                            LoginUser loginUser = SecurityUtils.getLoginUser();
                            if (loginUser != null) {
                                pmsSkillOpinion.setApprovelName(loginUser.getUser().getNickName());
                            }
                            pmsSkillTopic = pmsSkillTopicService.selectPmsSkillTopicById(pmsSkillTopic.getId());
                            pmsSkillOpinion.setCompCode(pmsSkillTopic.getCompCode());
                            pmsSkillOpinion.setTechnoCode(pmsSkillTopic.getTopicCode());
                            pmsSkillOpinion.setAuditState(Constants.CHECK_REFUSE);
                            pmsSkillOpinion.setTechnoType(Constants.TECHNO_TYPE_KT);
                            pmsSkillOpinionService.insertPmsSkillOpinion(pmsSkillOpinion);
                            return AjaxResult.success("课题审核驳回!");
                        }else if(code == Constants.HTTP_CODE2){
                            return AjaxResult.error("没有权限审核！");
                        }
                    }
                    //终止
                }else if(pmsSkillTopic.getAuditState().equals(Constants.CHECK_STOP)){
                    params.put("stopReason",pmsSkillTopic.getComment());
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
                            pmsSkillTopic.setAuditState(Constants.CHECK_STOP);
                            pmsSkillTopicService.updatePmsSkillTopic(pmsSkillTopic);
                            PmsSkillOpinion pmsSkillOpinion = new PmsSkillOpinion();
                            pmsSkillOpinion.setOpinion(pmsSkillTopic.getComment());
                            pmsSkillOpinion.setApprovelTime(new Date());
                            LoginUser loginUser = SecurityUtils.getLoginUser();
                            if (loginUser != null) {
                                pmsSkillOpinion.setApprovelName(loginUser.getUser().getNickName());
                            }
                            pmsSkillTopic = pmsSkillTopicService.selectPmsSkillTopicById(pmsSkillTopic.getId());
                            pmsSkillOpinion.setCompCode(pmsSkillTopic.getCompCode());
                            pmsSkillOpinion.setTechnoCode(pmsSkillTopic.getTopicCode());
                            pmsSkillOpinion.setAuditState(Constants.CHECK_STOP);
                            pmsSkillOpinion.setTechnoType(Constants.TECHNO_TYPE_KT);
                            pmsSkillOpinionService.insertPmsSkillOpinion(pmsSkillOpinion);
                            return AjaxResult.success("课题审核终止！");
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
     * 删除课题
     */
    @PreAuthorize("@ss.hasPermi('skill:topic:remove')")
    @Log(title = "课题", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除课题")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsSkillTopicService.deletePmsSkillTopicByIds(ids));
    }
}
