package com.zjry.pms.bonus.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.utils.DateUtils;
import com.zjry.common.utils.StringUtils;
import com.zjry.pms.bonus.domain.PmsBonusCalculation;
import com.zjry.pms.bonus.mapper.PmsBonusCalculationMapper;
import com.zjry.pms.bonus.service.IPmsBonusCalculationService;
import com.zjry.pms.common.Constants;
import com.zjry.pms.common.utils.HttpConnectionUtil;
import com.zjry.pms.common.utils.ProcessUtils;
import com.zjry.pms.common.utils.UserUtils;
import com.zjry.pms.evaluate.domain.PmsEvaluateData;
import com.zjry.pms.evaluate.service.IPmsEvaluateIndexService;
import com.zjry.pms.process.domain.HrProcessModel;
import com.zjry.pms.process.mapper.HrProcessModelMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 奖金核算Service业务层处理
 *
 * @author zjry
 * @date 2022-10-19
 */
@Service
public class PmsBonusCalculationServiceImpl extends ServiceImpl<PmsBonusCalculationMapper, PmsBonusCalculation> implements IPmsBonusCalculationService {

    @Autowired
    private PmsBonusCalculationMapper pmsBonusCalculationMapper;
    @Autowired
    private HrProcessModelMapper hrProcessModelMapper;
    @Autowired
    private IPmsEvaluateIndexService pmsEvaluateIndexService;

    /**
     * 查询奖金核算
     *
     * @param id 奖金核算ID
     * @return 奖金核算
     */
    @Override
    public PmsBonusCalculation selectPmsBonusCalculationById(Long id) {
        return pmsBonusCalculationMapper.selectPmsBonusCalculationById(id);
    }

    /**
     * 查询奖金核算列表
     *
     * @param pmsBonusCalculation 奖金核算
     * @return 奖金核算
     */
    @Override
    public List<PmsBonusCalculation> selectPmsBonusCalculationList(PmsBonusCalculation pmsBonusCalculation) {
        return pmsBonusCalculationMapper.selectPmsBonusCalculationList(pmsBonusCalculation);
    }

    /**
     * 新增奖金核算
     *
     * @param pmsBonusCalculation 奖金核算
     * @return 结果
     */
    @Override
    @Transactional
    public int insertPmsBonusCalculation(PmsBonusCalculation pmsBonusCalculation) {
        int i;
        if(pmsBonusCalculation.getId() != null){
            pmsBonusCalculation.preUpdate();
            i = pmsBonusCalculationMapper.updatePmsBonusCalculation(pmsBonusCalculation);
        }else{
            pmsBonusCalculation.preInsert();
            pmsBonusCalculation.setAuditState(Constants.CHECK_DRAFT);
            pmsBonusCalculation.setArchiveState(Constants.COMMON_NO);
            pmsBonusCalculation.setCompCode(UserUtils.getSysUser().getDeptId());
            i = pmsBonusCalculationMapper.insertPmsBonusCalculation(pmsBonusCalculation);
            /* 启动审批流程 */
            //TODO;
//            this.getProId(pmsBonusCalculation);
        }
        /* 生成奖金数据 */
        List<PmsEvaluateData> pmsEvaluateData = pmsEvaluateIndexService.calculateEvaluateIndex(DateUtils.parseDateToStr(Constants.DATE_MONTH_FORMATTER, pmsBonusCalculation.getPeriod()));
        return CollectionUtils.isNotEmpty(pmsEvaluateData)?pmsEvaluateData.size():0;
    }

    @Override
    public AjaxResult getProId(PmsBonusCalculation param) {
        PmsBonusCalculation exit = pmsBonusCalculationMapper.selectPmsBonusCalculationById(param.getId());
        String procedureId = exit.getProcedureId();
        //流程id
        String proclnsId = exit.getProcedureId();
        if (StringUtils.isEmpty(procedureId)) {
            //开始流程
            HrProcessModel query = new HrProcessModel();
            //流程标识
            HrProcessModel processModel = hrProcessModelMapper.selectByProcInsId("jiangjin");
            Map<String, String> params = ProcessUtils.startProcess(processModel.getProcinsId(),
                    processModel.getTabelName(),
                    exit.getId(),
                    exit.getName(),
                    processModel.getFormUrl());
            try {

                String result = HttpConnectionUtil.post(ProcessUtils.getConfigValueByKey("processUrl") + "/api/wf/startProcess", params);
                if (StringUtils.isNotEmpty(result)) {
                    JSONObject json = JSONObject.parseObject(result);
                    Map<String, String> map = (Map<String, String>) json.get("data");
                    if (StringUtils.isNotEmpty(map)) {
                        proclnsId = map.get("procInsId");
                        //将流程的示例ID写到业务表中
                        PmsBonusCalculation update = new PmsBonusCalculation();
                        update.setId(param.getId());
                        update.setProcedureId(proclnsId);
                        update.preUpdate();
                        pmsBonusCalculationMapper.updatePmsBonusCalculation(update);
                    }
                }
                Map<String, String> businessParam = ProcessUtils.startProcess(proclnsId, param.getId(), param.getName());
                HttpConnectionUtil.post(ProcessUtils.getConfigValueByKey("processUrl") + "/api/wf/addBusinessData", businessParam);
            } catch (Exception e) {
                e.printStackTrace();
                return AjaxResult.error("流程接口异常");
            }

        }
        return AjaxResult.success(procedureId);
    }

    @Override
    public AjaxResult commitAudit(PmsBonusCalculation param) {
        PmsBonusCalculation exit = pmsBonusCalculationMapper.selectPmsBonusCalculationById(param.getId());
        //提交流程
        if (!StringUtils.equals(exit.getAuditState(), "0")) {
            return AjaxResult.error("奖金状态已更新,无法提交");
        }

        Map<String, String> params = ProcessUtils.dealProcess(exit.getProcedureId(), param.getComment(), param.getAssignee());
        params.put("actionName", "提交");
        String result = HttpConnectionUtil.post(ProcessUtils.getConfigValueByKey("processUrl") + "/api/wf/completeTask", params);
        if (StringUtils.isEmpty(result)) {
            return AjaxResult.error("请求接口有误！");
        }
        JSONObject json = JSONObject.parseObject(result);
        int code = (int) json.get("code");
        if (code == Constants.HTTP_CODE2) {
            return AjaxResult.error("没有权限审核！");
        }
        JSONObject data = JSONObject.parseObject(json.get("data").toString());
        int finishStatus = (int) data.get("finishStatus");
        if (finishStatus != Constants.HTTP_CODE1) {
            PmsBonusCalculation update = new PmsBonusCalculation();
            update.setId(param.getId());
            update.setAuditState("1");
            update.preUpdate();
            pmsBonusCalculationMapper.updatePmsBonusCalculation(update);
        }
        return AjaxResult.success("提交成功");
    }

    /**
     * 修改奖金核算
     *
     * @param pmsBonusCalculation 奖金核算
     * @return 结果
     */
    @Override
    public int updatePmsBonusCalculation(PmsBonusCalculation pmsBonusCalculation) {
        pmsBonusCalculation.preUpdate();
        return pmsBonusCalculationMapper.updatePmsBonusCalculation(pmsBonusCalculation);
    }

    /**
     * 批量删除奖金核算
     *
     * @param ids 需要删除的奖金核算ID
     * @return 结果
     */
    @Override
    public int deletePmsBonusCalculationByIds(Long[] ids) {
        return pmsBonusCalculationMapper.deletePmsBonusCalculationByIds(ids);
    }

    /**
     * 删除奖金核算信息
     *
     * @param id 奖金核算ID
     * @return 结果
     */
    @Override
    public int deletePmsBonusCalculationById(Long id) {
        return pmsBonusCalculationMapper.deletePmsBonusCalculationById(id);
    }

    @Override
    public AjaxResult audit(PmsBonusCalculation param) {
        Long id = param.getId();
        String auditState = param.getAuditState();
        PmsBonusCalculation exit = pmsBonusCalculationMapper.selectPmsBonusCalculationById(id);
        String result = null;
        Map<String, String> params = ProcessUtils.dealProcess(param.getProcedureId(), param.getComment(), param.getAssignee());
        if (auditState.equals(Constants.CHECK_PASS)) {
            params.put("actionName", "通过");
            result = HttpConnectionUtil.post(ProcessUtils.getConfigValueByKey("processUrl") + "/api/wf/completeTask", params);
        } else if (auditState.equals(Constants.CHECK_REFUSE)) {
            result = HttpConnectionUtil.post(ProcessUtils.getConfigValueByKey("processUrl") + "/api/wf/backTask", params);
        } else if (auditState.equals(Constants.CHECK_STOP)) {
            result = HttpConnectionUtil.post(ProcessUtils.getConfigValueByKey("processUrl") + "/api/wf/stopProcess", params);
        }

        JSONObject json = JSONObject.parseObject(result);
        int code = (int) json.get("code");
        if (code == Constants.HTTP_CODE) {
            Object dataObj = json.get("data");
            if (dataObj != null) {
                JSONObject data = JSONObject.parseObject(dataObj.toString());
                Object isFirstActivityObj = data.get("isFirstActivity");
                boolean isFirstActivity = false;
                if (isFirstActivityObj != null) {
                    isFirstActivity = (boolean) isFirstActivityObj;
                }
                if (isFirstActivity && auditState.equals(Constants.CHECK_REFUSE)) {
                    param.setAuditState(Constants.CHECK_DRAFT);
                    pmsBonusCalculationMapper.updatePmsBonusCalculation(param);
                }
            }
            pmsBonusCalculationMapper.updatePmsBonusCalculation(param);

        } else if (code == Constants.HTTP_CODE2) {
            return AjaxResult.error("没有权限审核！");
        }
        return AjaxResult.success("审核成功");
    }
}
