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
import com.zjry.pms.skill.domain.PmsSkillOpinion;
import com.zjry.pms.skill.domain.PmsSkillThesis;
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
import com.zjry.pms.skill.domain.PmsSkillThesis;
import com.zjry.pms.skill.service.IPmsSkillThesisService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 论文Controller
 *
 * @author maozt
 * @date 2022-07-28
 */
@RestController
@RequestMapping("/skill/thesis")
@Api(tags={"【论文】"})
public class PmsSkillThesisController extends BaseController {
    @Autowired
    private IPmsSkillThesisService pmsSkillThesisService;
    @Autowired
    private IHrEncodeRuleService hrEncodeRuleService;
    @Autowired
    private IHrProcessModelService hrProcessModelService;
    @Autowired
    private IPmsSkillOpinionService pmsSkillOpinionService;

    /**
     * 查询论文列表
     */
    @PreAuthorize("@ss.hasPermi('skill:thesis:list')")
    @GetMapping("/list")
    @ApiOperation("查询论文列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsSkillThesis pmsSkillThesis) {
        startPage();
        List<PmsSkillThesis> list = pmsSkillThesisService.selectPmsSkillThesisList(pmsSkillThesis);
        return getDataTable(list);
    }

    /**
     * 导出论文列表
     */
    @PreAuthorize("@ss.hasPermi('skill:thesis:export')")
    @Log(title = "论文", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出论文列表Excel")
    public AjaxResult export(PmsSkillThesis pmsSkillThesis) {
        List<PmsSkillThesis> list = pmsSkillThesisService.selectPmsSkillThesisList(pmsSkillThesis);
        ExcelUtil<PmsSkillThesis> util = new ExcelUtil<PmsSkillThesis>(PmsSkillThesis.class);
        return util.exportExcel(list, "论文数据");
    }

    /**
     * 获取论文详细信息
     */
    @PreAuthorize("@ss.hasPermi('skill:thesis:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取论文详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        if(id== Constants.ID||id.equals(Constants.ID)) {
            HrProcessModel hrProcessModel = new HrProcessModel();
            hrProcessModel.setTabelName("pms_skill_thesis");
            List<HrProcessModel> hrProcessModelList = hrProcessModelService.selectHrProcessModelList(hrProcessModel);
            if (hrProcessModelList.size() > 0) {
                //获取流程的访问地址
                String processUrl = ProcessUtils.getConfigValueByKey("processUrl");
                String proclnsId = "";
                Map<String, String> params = new HashMap<String, String>(16);
                params = ProcessUtils.startProcess(hrProcessModelList.get(0).getProcinsId(), "pms_skill_thesis", 0, "论文", hrProcessModelList.get(0).getFormUrl());
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
                    PmsSkillThesis pmsSkillThesis = new PmsSkillThesis();
                    pmsSkillThesis.setProcedureId(proclnsId);
                    return AjaxResult.success(pmsSkillThesis);
                }
            } else {
                return AjaxResult.error("流程未匹配！");
            }
        }else {
            return AjaxResult.success(pmsSkillThesisService.selectPmsSkillThesisById(id));
        }
    }

    /**
     * 新增论文
     */
    @PreAuthorize("@ss.hasPermi('skill:thesis:add')")
    @Log(title = "论文", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增论文")
    public AjaxResult add(@RequestBody PmsSkillThesis pmsSkillThesis) {
        try {
            if (pmsSkillThesisService.selectPmsSkillThesisListByName(pmsSkillThesis).size() != 0) {
                return error("刊物名称不能重复");
            }
            String thesisCode = hrEncodeRuleService.generateEncode("pms_skill_thesis", "THESIS_CODE");
            pmsSkillThesis.setThesisCode(thesisCode);
            pmsSkillThesis.setAuditState(Constants.CHECK_DRAFT);
            int row = pmsSkillThesisService.insertPmsSkillThesis(pmsSkillThesis);
            if(StringUtils.isNull(pmsSkillThesis.getProcedureId())) {
                if (row > 0) {
                    HrProcessModel hrProcessModel = new HrProcessModel();
                    hrProcessModel.setTabelName("pms_skill_thesis");
                    List<HrProcessModel> hrProcessModelList = hrProcessModelService.selectHrProcessModelList(hrProcessModel);
                    if (hrProcessModelList.size() > 0) {
                        //获取流程的访问地址
                        String processUrl = ProcessUtils.getConfigValueByKey("processUrl");
                        String proclnsId = "";
                        Map<String, String> params = new HashMap<String, String>(16);
                        PmsSkillThesis skillThesis = new PmsSkillThesis();
                        skillThesis.setCompCode(skillThesis.getCompCode());
                        skillThesis.setThesisCode(skillThesis.getThesisCode());
                        pmsSkillThesis = pmsSkillThesisService.selectPmsSkillThesisList(skillThesis).get(0);
                        params = ProcessUtils.startProcess(hrProcessModelList.get(0).getProcinsId(), "pms_skill_thesis", pmsSkillThesis.getId(), pmsSkillThesis.getThesisName() + "论文", hrProcessModelList.get(0).getFormUrl());
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
                                pmsSkillThesis.setProcedureId(proclnsId);
                                ;
                            }
                        }
                        if (StringUtils.isEmpty(proclnsId)) {
                            return AjaxResult.error("未获取到配置流程ID！");
                        } else {
                            pmsSkillThesisService.updatePmsSkillThesis(pmsSkillThesis);
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
                params = ProcessUtils.startProcess(pmsSkillThesis.getProcedureId(),pmsSkillThesis.getId(),pmsSkillThesis.getThesisName()+"论文");
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
     * 修改论文
     */
    @PreAuthorize("@ss.hasPermi('skill:thesis:edit')")
    @Log(title = "论文", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改论文")
    public AjaxResult edit(@RequestBody PmsSkillThesis pmsSkillThesis) {
        if (pmsSkillThesisService.selectPmsSkillThesisListByName(pmsSkillThesis).size() != 0) {
            return error("刊物名称不能重复");
        }
        return toAjax(pmsSkillThesisService.updatePmsSkillThesis(pmsSkillThesis));
    }

    /**
     * 修改论文
     */
    @PreAuthorize("@ss.hasPermi('skill:thesis:edit')")
    @Log(title = "论文", businessType = BusinessType.UPDATE)
    @PutMapping("/upd")
    @ApiOperation("修改论文")
    public AjaxResult upd(@RequestBody PmsSkillThesis pmsSkillThesis) {
        try {
            if (pmsSkillThesisService.selectPmsSkillThesisListByName(pmsSkillThesis).size() != 0) {
                return error("刊物名称不能重复");
            }
            int row = 0;
            if(StringUtils.isNull(pmsSkillThesis.getId())){
                pmsSkillThesis.setAuditState(Constants.CHECK_DRAFT);
                String thesisCode = hrEncodeRuleService.generateEncode("pms_skill_thesis", "THESIS_CODE");
                pmsSkillThesis.setThesisCode(thesisCode);
                row = pmsSkillThesisService.insertPmsSkillThesis(pmsSkillThesis);
                //获取流程的访问地址
                String processUrl = ProcessUtils.getConfigValueByKey("processUrl");
                Map<String, String> params = new HashMap<String, String>(16);
                params = ProcessUtils.startProcess(pmsSkillThesis.getProcedureId(),pmsSkillThesis.getId(),pmsSkillThesis.getThesisName()+"论文");
                try {
                    //启动流程获取流程的ID，proclnsId
                    HttpConnectionUtil.post(processUrl+"/api/wf/addBusinessData", params);
                } catch (Exception e) {
                    return AjaxResult.error("请求接口异常！");
                }
            }else{
                row = pmsSkillThesisService.updatePmsSkillThesis(pmsSkillThesis);
            }
            pmsSkillThesis.setAuditState(Constants.CHECK_SUBMIT);
            row = pmsSkillThesisService.updatePmsSkillThesis(pmsSkillThesis);
            if(row>0){
                String processUrl = ProcessUtils.getConfigValueByKey("processUrl");
                Map<String, String> params = new HashMap<String, String>(16);
                params = ProcessUtils.dealProcess(pmsSkillThesis.getProcedureId(), pmsSkillThesis.getComment(), pmsSkillThesis.getAssignee());
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
                            pmsSkillThesis.setAuditState(Constants.CHECK_PASS);
                            pmsSkillThesisService.updatePmsSkillThesis(pmsSkillThesis);
                        }else{
                            pmsSkillThesis.setAuditState(Constants.CHECK_SUBMIT);
                            pmsSkillThesisService.updatePmsSkillThesis(pmsSkillThesis);
                        }
                        PmsSkillOpinion pmsSkillOpinion = new PmsSkillOpinion();
                        pmsSkillOpinion.setCompCode(pmsSkillThesis.getCompCode());
                        pmsSkillOpinion.setTechnoCode(pmsSkillThesis.getThesisCode());
                        pmsSkillOpinion.setOpinion(pmsSkillThesis.getComment());
                        pmsSkillOpinion.setApprovelTime(new Date());
                        LoginUser loginUser = SecurityUtils.getLoginUser();
                        if (loginUser != null) {
                            pmsSkillOpinion.setApprovelName(loginUser.getUser().getNickName());
                        }
                        pmsSkillOpinion.setAuditState(Constants.CHECK_SUBMIT);
                        pmsSkillOpinion.setTechnoType(Constants.TECHNO_TYPE_LW);
                        pmsSkillOpinionService.insertPmsSkillOpinion(pmsSkillOpinion);
                        return AjaxResult.success("论文审核通过!");
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
     * 修改论文
     */
    @PreAuthorize("@ss.hasPermi('skill:thesis:edit')")
    @Log(title = "论文", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    @ApiOperation("修改论文")
    public AjaxResult audit(@RequestBody PmsSkillThesis pmsSkillThesis) {
        try {
            if(ObjectUtils.isEmpty(pmsSkillThesis.getId())){
                return AjaxResult.error("论文ID不能为空");
            }else{
                String processUrl = ProcessUtils.getConfigValueByKey("processUrl");
                Map<String, String> params = new HashMap<String, String>(16);
                params = ProcessUtils.dealProcess(pmsSkillThesis.getProcedureId(), pmsSkillThesis.getComment(), pmsSkillThesis.getAssignee());
                String result="";
                //点击审核通过按钮时判断的状态这个参数getAuditState()得和前台的一致
                if(pmsSkillThesis.getAuditState().equals(Constants.CHECK_PASS)){
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
                                pmsSkillThesis.setAuditState(Constants.CHECK_PASS);
                                pmsSkillThesisService.updatePmsSkillThesis(pmsSkillThesis);
                            }
                            PmsSkillOpinion pmsSkillOpinion = new PmsSkillOpinion();
                            pmsSkillOpinion.setOpinion(pmsSkillThesis.getComment());
                            pmsSkillOpinion.setApprovelTime(new Date());
                            LoginUser loginUser = SecurityUtils.getLoginUser();
                            if (loginUser != null) {
                                pmsSkillOpinion.setApprovelName(loginUser.getUser().getNickName());
                            }
                            pmsSkillThesis = pmsSkillThesisService.selectPmsSkillThesisById(pmsSkillThesis.getId());
                            pmsSkillOpinion.setCompCode(pmsSkillThesis.getCompCode());
                            pmsSkillOpinion.setTechnoCode(pmsSkillThesis.getThesisCode());
                            pmsSkillOpinion.setAuditState(Constants.CHECK_PASS);
                            pmsSkillOpinion.setTechnoType(Constants.TECHNO_TYPE_LW);
                            pmsSkillOpinionService.insertPmsSkillOpinion(pmsSkillOpinion);
                            return AjaxResult.success("论文审核通过!");
                        }else if(code == Constants.HTTP_CODE2){
                            return AjaxResult.error("没有权限审核！");
                        }
                    }
                    //驳回
                }else if(pmsSkillThesis.getAuditState().equals(Constants.CHECK_REFUSE)){
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
                                pmsSkillThesis.setAuditState(Constants.CHECK_DRAFT);
                                pmsSkillThesisService.updatePmsSkillThesis(pmsSkillThesis);
                            }
                            PmsSkillOpinion pmsSkillOpinion = new PmsSkillOpinion();
                            pmsSkillOpinion.setOpinion(pmsSkillThesis.getComment());
                            pmsSkillOpinion.setApprovelTime(new Date());
                            LoginUser loginUser = SecurityUtils.getLoginUser();
                            if (loginUser != null) {
                                pmsSkillOpinion.setApprovelName(loginUser.getUser().getNickName());
                            }
                            pmsSkillThesis = pmsSkillThesisService.selectPmsSkillThesisById(pmsSkillThesis.getId());
                            pmsSkillOpinion.setCompCode(pmsSkillThesis.getCompCode());
                            pmsSkillOpinion.setTechnoCode(pmsSkillThesis.getThesisCode());
                            pmsSkillOpinion.setAuditState(Constants.CHECK_REFUSE);
                            pmsSkillOpinion.setTechnoType(Constants.TECHNO_TYPE_LW);
                            pmsSkillOpinionService.insertPmsSkillOpinion(pmsSkillOpinion);
                            return AjaxResult.success("论文审核驳回!");
                        }else if(code == Constants.HTTP_CODE2){
                            return AjaxResult.error("没有权限审核！");
                        }
                    }
                    //终止
                }else if(pmsSkillThesis.getAuditState().equals(Constants.CHECK_STOP)){
                    params.put("stopReason",pmsSkillThesis.getComment());
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
                            pmsSkillThesis.setAuditState(Constants.CHECK_STOP);
                            pmsSkillThesisService.updatePmsSkillThesis(pmsSkillThesis);
                            PmsSkillOpinion pmsSkillOpinion = new PmsSkillOpinion();
                            pmsSkillOpinion.setOpinion(pmsSkillThesis.getComment());
                            pmsSkillOpinion.setApprovelTime(new Date());
                            LoginUser loginUser = SecurityUtils.getLoginUser();
                            if (loginUser != null) {
                                pmsSkillOpinion.setApprovelName(loginUser.getUser().getNickName());
                            }
                            pmsSkillThesis = pmsSkillThesisService.selectPmsSkillThesisById(pmsSkillThesis.getId());
                            pmsSkillOpinion.setCompCode(pmsSkillThesis.getCompCode());
                            pmsSkillOpinion.setTechnoCode(pmsSkillThesis.getThesisCode());
                            pmsSkillOpinion.setAuditState(Constants.CHECK_STOP);
                            pmsSkillOpinion.setTechnoType(Constants.TECHNO_TYPE_LW);
                            pmsSkillOpinionService.insertPmsSkillOpinion(pmsSkillOpinion);
                            return AjaxResult.success("论文审核终止！");
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
     * 删除论文
     */
    @PreAuthorize("@ss.hasPermi('skill:thesis:remove')")
    @Log(title = "论文", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除论文")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsSkillThesisService.deletePmsSkillThesisByIds(ids));
    }
}
