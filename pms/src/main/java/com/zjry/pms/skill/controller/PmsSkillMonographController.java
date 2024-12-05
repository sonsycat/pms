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
import com.zjry.pms.encode.service.IHrEncodeRuleService;
import com.zjry.pms.process.domain.HrProcessModel;
import com.zjry.pms.process.service.IHrProcessModelService;
import com.zjry.pms.skill.domain.*;
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
import com.zjry.pms.skill.domain.PmsSkillMonograph;
import com.zjry.pms.skill.service.IPmsSkillMonographService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 专著Controller
 *
 * @author maozt
 * @date 2022-08-02
 */
@RestController
@RequestMapping("/skill/monograph")
@Api(tags={"【专著】"})
public class PmsSkillMonographController extends BaseController {
    @Autowired
    private IPmsSkillMonographService pmsSkillMonographService;
    @Autowired
    private IHrEncodeRuleService hrEncodeRuleService;
    @Autowired
    private IHrProcessModelService hrProcessModelService;
    @Autowired
    private IPmsSkillOpinionService pmsSkillOpinionService;

    /**
     * 查询专著列表
     */
    @PreAuthorize("@ss.hasPermi('skill:monograph:list')")
    @GetMapping("/list")
    @ApiOperation("查询专著列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsSkillMonograph pmsSkillMonograph) {
        startPage();
        List<PmsSkillMonograph> list = pmsSkillMonographService.selectPmsSkillMonographList(pmsSkillMonograph);
        return getDataTable(list);
    }

    /**
     * 导出专著列表
     */
    @PreAuthorize("@ss.hasPermi('skill:monograph:export')")
    @Log(title = "专著", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出专著列表Excel")
    public AjaxResult export(PmsSkillMonograph pmsSkillMonograph) {
        List<PmsSkillMonograph> list = pmsSkillMonographService.selectPmsSkillMonographList(pmsSkillMonograph);
        ExcelUtil<PmsSkillMonograph> util = new ExcelUtil<PmsSkillMonograph>(PmsSkillMonograph.class);
        return util.exportExcel(list, "专著数据");
    }

    /**
     * 获取专著详细信息
     */
    @PreAuthorize("@ss.hasPermi('skill:monograph:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取专著详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        if(id== Constants.ID||id.equals(Constants.ID)) {
            HrProcessModel hrProcessModel = new HrProcessModel();
            hrProcessModel.setTabelName("pms_skill_monograph");
            List<HrProcessModel> hrProcessModelList = hrProcessModelService.selectHrProcessModelList(hrProcessModel);
            if (hrProcessModelList.size() > 0) {
                //获取流程的访问地址
                String processUrl = ProcessUtils.getConfigValueByKey("processUrl");
                String proclnsId = "";
                Map<String, String> params = new HashMap<String, String>(16);
                params = ProcessUtils.startProcess(hrProcessModelList.get(0).getProcinsId(), "pms_skill_monograph", 0, "专著", hrProcessModelList.get(0).getFormUrl());
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
            return AjaxResult.success(pmsSkillMonographService.selectPmsSkillMonographById(id));
        }
    }

    /**
     * 新增专著
     */
    @PreAuthorize("@ss.hasPermi('skill:monograph:add')")
    @Log(title = "专著", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增专著")
    public AjaxResult add(@RequestBody PmsSkillMonograph pmsSkillMonograph) {
        try {
            String monographCode = hrEncodeRuleService.generateEncode("pms_skill_monograph", "MONOGRAPH_CODE");
            pmsSkillMonograph.setMonographCode(monographCode);

            if (pmsSkillMonographService.selectPmsSkillMonographListByName(pmsSkillMonograph).size() != 0) {
                throw new Exception("名称不能重复");
            }
            pmsSkillMonograph.setAuditState(Constants.CHECK_DRAFT);

            int row = pmsSkillMonographService.insertPmsSkillMonograph(pmsSkillMonograph);
            if(StringUtils.isNull(pmsSkillMonograph.getProcedureId())) {
                if (row > 0) {
                    HrProcessModel hrProcessModel = new HrProcessModel();
                    hrProcessModel.setTabelName("pms_skill_monograph");
                    List<HrProcessModel> hrProcessModelList = hrProcessModelService.selectHrProcessModelList(hrProcessModel);
                    if (hrProcessModelList.size() > 0) {
                        //获取流程的访问地址
                        String processUrl = ProcessUtils.getConfigValueByKey("processUrl");
                        String proclnsId = "";
                        Map<String, String> params = new HashMap<String, String>(16);
                        PmsSkillMonograph SkillMonograph = new PmsSkillMonograph();
                        SkillMonograph.setCompCode(pmsSkillMonograph.getCompCode());
                        SkillMonograph.setMonographCode(pmsSkillMonograph.getMonographCode());
                        pmsSkillMonograph = pmsSkillMonographService.selectPmsSkillMonographList(pmsSkillMonograph).get(0);
                        params = ProcessUtils.startProcess(hrProcessModelList.get(0).getProcinsId(), "pms_skill_monograph", pmsSkillMonograph.getId(), pmsSkillMonograph.getMonographName() + "专著", hrProcessModelList.get(0).getFormUrl());
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
                                pmsSkillMonograph.setProcedureId(proclnsId);
                                ;
                            }
                        }
                        if (StringUtils.isEmpty(proclnsId)) {
                            return AjaxResult.error("未获取到配置流程ID！");
                        } else {
                            pmsSkillMonographService.updatePmsSkillMonograph(pmsSkillMonograph);
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
                params = ProcessUtils.startProcess(pmsSkillMonograph.getProcedureId(),pmsSkillMonograph.getId(),pmsSkillMonograph.getMonographName()+"专著");
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
     * 修改专著
     */
    @PreAuthorize("@ss.hasPermi('skill:monograph:edit')")
    @Log(title = "专著", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改专著")
    public AjaxResult edit(@RequestBody PmsSkillMonograph pmsSkillMonograph) {
        try {
            if (pmsSkillMonographService.selectPmsSkillMonographListByName(pmsSkillMonograph).size() != 0) {
                throw new Exception("名称不能重复");
            }
            return toAjax(pmsSkillMonographService.updatePmsSkillMonograph(pmsSkillMonograph));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 修改专著
     */
    @PreAuthorize("@ss.hasPermi('skill:topic:edit')")
    @Log(title = "专著", businessType = BusinessType.UPDATE)
    @PutMapping("/upd")
    @ApiOperation("修改专著")
    public AjaxResult upd(@RequestBody PmsSkillMonograph pmsSkillMonograph) {
        try {
            if (pmsSkillMonographService.selectPmsSkillMonographListByName(pmsSkillMonograph).size() > 0) {
                throw new Exception("编号不能重复");
            }
            int row = 0;
            if(StringUtils.isNull(pmsSkillMonograph.getId())){
                String monographCode = hrEncodeRuleService.generateEncode("pms_skill_monograph", "MONOGRAPH_CODE");
                pmsSkillMonograph.setMonographCode(monographCode);
                pmsSkillMonograph.setAuditState(Constants.CHECK_DRAFT);
                row = pmsSkillMonographService.insertPmsSkillMonograph(pmsSkillMonograph);
                //获取流程的访问地址
                String processUrl = ProcessUtils.getConfigValueByKey("processUrl");
                Map<String, String> params = new HashMap<String, String>(16);
                params = ProcessUtils.startProcess(pmsSkillMonograph.getProcedureId(),pmsSkillMonograph.getId(),pmsSkillMonograph.getMonographName()+"专著");
                try {
                    //启动流程获取流程的ID，proclnsId
                    HttpConnectionUtil.post(processUrl+"/api/wf/addBusinessData", params);
                } catch (Exception e) {
                    return AjaxResult.error("请求接口异常！");
                }
            }else{
                row = pmsSkillMonographService.updatePmsSkillMonograph(pmsSkillMonograph);
            }
            pmsSkillMonograph.setAuditState(Constants.CHECK_SUBMIT);
            row = pmsSkillMonographService.updatePmsSkillMonograph(pmsSkillMonograph);
            if(row>0){
                String processUrl = ProcessUtils.getConfigValueByKey("processUrl");
                Map<String, String> params = new HashMap<String, String>(16);
                params = ProcessUtils.dealProcess(pmsSkillMonograph.getProcedureId(), pmsSkillMonograph.getComment(), pmsSkillMonograph.getAssignee());
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
                            pmsSkillMonograph.setAuditState(Constants.CHECK_PASS);
                            pmsSkillMonographService.updatePmsSkillMonograph(pmsSkillMonograph);
                        }else{
                            pmsSkillMonograph.setAuditState(Constants.CHECK_SUBMIT);
                            pmsSkillMonographService.updatePmsSkillMonograph(pmsSkillMonograph);
                        }
                        PmsSkillOpinion pmsSkillOpinion = new PmsSkillOpinion();
                        pmsSkillOpinion.setCompCode(pmsSkillMonograph.getCompCode());
                        pmsSkillOpinion.setTechnoCode(pmsSkillMonograph.getMonographCode());
                        pmsSkillOpinion.setOpinion(pmsSkillMonograph.getComment());
                        pmsSkillOpinion.setApprovelTime(new Date());
                        LoginUser loginUser = SecurityUtils.getLoginUser();
                        if (loginUser != null) {
                            pmsSkillOpinion.setApprovelName(loginUser.getUser().getNickName());
                        }
                        pmsSkillOpinion.setAuditState(Constants.CHECK_SUBMIT);
                        pmsSkillOpinion.setTechnoType(Constants.TECHNO_TYPE_CG);
                        pmsSkillOpinionService.insertPmsSkillOpinion(pmsSkillOpinion);
                        return AjaxResult.success("专著审核通过!");
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
     * 修改专著
     */
    @PreAuthorize("@ss.hasPermi('skill:topic:edit')")
    @Log(title = "专著", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    @ApiOperation("修改专著")
    public AjaxResult audit(@RequestBody PmsSkillMonograph pmsSkillMonograph) {
        try {
            if(ObjectUtils.isEmpty(pmsSkillMonograph.getId())){
                return AjaxResult.error("专著ID不能为空");
            }else{
                String processUrl = ProcessUtils.getConfigValueByKey("processUrl");
                Map<String, String> params = new HashMap<String, String>(16);
                params = ProcessUtils.dealProcess(pmsSkillMonograph.getProcedureId(), pmsSkillMonograph.getComment(), pmsSkillMonograph.getAssignee());
                String result="";
                //点击审核通过按钮时判断的状态这个参数getAuditState()得和前台的一致
                if(pmsSkillMonograph.getAuditState().equals(Constants.CHECK_PASS)){
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
                                pmsSkillMonograph.setAuditState(Constants.CHECK_PASS);
                                pmsSkillMonographService.updatePmsSkillMonograph(pmsSkillMonograph);
                            }
                            PmsSkillOpinion pmsSkillOpinion = new PmsSkillOpinion();
                            pmsSkillOpinion.setOpinion(pmsSkillMonograph.getComment());
                            pmsSkillOpinion.setApprovelTime(new Date());
                            LoginUser loginUser = SecurityUtils.getLoginUser();
                            if (loginUser != null) {
                                pmsSkillOpinion.setApprovelName(loginUser.getUser().getNickName());
                            }
                            pmsSkillMonograph = pmsSkillMonographService.selectPmsSkillMonographById(pmsSkillMonograph.getId());
                            pmsSkillOpinion.setCompCode(pmsSkillMonograph.getCompCode());
                            pmsSkillOpinion.setTechnoCode(pmsSkillMonograph.getMonographCode());
                            pmsSkillOpinion.setAuditState(Constants.CHECK_PASS);
                            pmsSkillOpinion.setTechnoType(Constants.TECHNO_TYPE_CG);
                            pmsSkillOpinionService.insertPmsSkillOpinion(pmsSkillOpinion);
                            return AjaxResult.success("专著审核通过!");
                        }else if(code == Constants.HTTP_CODE2){
                            return AjaxResult.error("没有权限审核！");
                        }
                    }
                    //驳回
                }else if(pmsSkillMonograph.getAuditState().equals(Constants.CHECK_REFUSE)){
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
                                pmsSkillMonograph.setAuditState(Constants.CHECK_DRAFT);
                                pmsSkillMonographService.updatePmsSkillMonograph(pmsSkillMonograph);
                            }
                            PmsSkillOpinion pmsSkillOpinion = new PmsSkillOpinion();
                            pmsSkillOpinion.setOpinion(pmsSkillMonograph.getComment());
                            pmsSkillOpinion.setApprovelTime(new Date());
                            LoginUser loginUser = SecurityUtils.getLoginUser();
                            if (loginUser != null) {
                                pmsSkillOpinion.setApprovelName(loginUser.getUser().getNickName());
                            }
                            pmsSkillMonograph = pmsSkillMonographService.selectPmsSkillMonographById(pmsSkillMonograph.getId());
                            pmsSkillOpinion.setCompCode(pmsSkillMonograph.getCompCode());
                            pmsSkillOpinion.setTechnoCode(pmsSkillMonograph.getMonographCode());
                            pmsSkillOpinion.setAuditState(Constants.CHECK_REFUSE);
                            pmsSkillOpinion.setTechnoType(Constants.TECHNO_TYPE_CG);
                            pmsSkillOpinionService.insertPmsSkillOpinion(pmsSkillOpinion);
                            return AjaxResult.success("专著审核驳回!");
                        }else if(code == Constants.HTTP_CODE2){
                            return AjaxResult.error("没有权限审核！");
                        }
                    }
                    //终止
                }else if(pmsSkillMonograph.getAuditState().equals(Constants.CHECK_STOP)){
                    params.put("stopReason",pmsSkillMonograph.getComment());
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
                            pmsSkillMonograph.setAuditState(Constants.CHECK_STOP);
                            pmsSkillMonographService.updatePmsSkillMonograph(pmsSkillMonograph);
                            PmsSkillOpinion pmsSkillOpinion = new PmsSkillOpinion();
                            pmsSkillOpinion.setOpinion(pmsSkillMonograph.getComment());
                            pmsSkillOpinion.setApprovelTime(new Date());
                            LoginUser loginUser = SecurityUtils.getLoginUser();
                            if (loginUser != null) {
                                pmsSkillOpinion.setApprovelName(loginUser.getUser().getNickName());
                            }
                            pmsSkillMonograph = pmsSkillMonographService.selectPmsSkillMonographById(pmsSkillMonograph.getId());
                            pmsSkillOpinion.setCompCode(pmsSkillMonograph.getCompCode());
                            pmsSkillOpinion.setTechnoCode(pmsSkillMonograph.getMonographCode());
                            pmsSkillOpinion.setAuditState(Constants.CHECK_STOP);
                            pmsSkillOpinion.setTechnoType(Constants.TECHNO_TYPE_CG);
                            pmsSkillOpinionService.insertPmsSkillOpinion(pmsSkillOpinion);
                            return AjaxResult.success("专著审核终止！");
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
     * 删除专著
     */
    @PreAuthorize("@ss.hasPermi('skill:monograph:remove')")
    @Log(title = "专著", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除专著")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsSkillMonographService.deletePmsSkillMonographByIds(ids));
    }
}
