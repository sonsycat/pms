package com.zjry.pms.bonus.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.utils.StringUtils;
import com.zjry.pms.bonus.domain.PmsBonusPersonAuditDto;
import com.zjry.pms.bonus.domain.PmsEvaluateDataTemp;
import com.zjry.pms.bonus.mapper.PmsEvaluateDataTempMapper;
import com.zjry.pms.common.Constants;
import com.zjry.pms.common.utils.HttpConnectionUtil;
import com.zjry.pms.common.utils.ProcessUtils;
import com.zjry.pms.evaluate.domain.PmsEvaluateData;
import com.zjry.pms.evaluate.mapper.PmsEvaluateDataMapper;
import com.zjry.pms.process.domain.HrProcessModel;
import com.zjry.pms.process.service.IHrProcessModelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.bonus.mapper.PmsBonusPersonAuditMapper;
import com.zjry.pms.bonus.domain.PmsBonusPersonAudit;
import com.zjry.pms.bonus.service.IPmsBonusPersonAuditService;

/**
 * 奖金人员审核Service业务层处理
 *
 * @author zjry
 * @date 2022-10-25
 */
@Service
@Slf4j
public class PmsBonusPersonAuditServiceImpl extends ServiceImpl<PmsBonusPersonAuditMapper, PmsBonusPersonAudit> implements IPmsBonusPersonAuditService {

    @Autowired
    private PmsBonusPersonAuditMapper pmsBonusPersonAuditMapper;

    @Autowired
    private PmsEvaluateDataTempMapper evaluateDataTempMapper;

    @Autowired
    private IHrProcessModelService hrProcessModelService;

    @Autowired
    private PmsEvaluateDataMapper evaluateDataMapper;

    /**
     * 查询奖金人员审核
     *
     * @param id 奖金人员审核ID
     * @return 奖金人员审核
     */
    @Override
    public PmsBonusPersonAudit selectPmsBonusPersonAuditById(Long id) {
        return pmsBonusPersonAuditMapper.selectPmsBonusPersonAuditById(id);
    }

    /**
     * 查询奖金人员审核列表
     *
     * @param pmsBonusPersonAudit 奖金人员审核
     * @return 奖金人员审核
     */
    @Override
    public List<PmsBonusPersonAudit> selectPmsBonusPersonAuditList(PmsBonusPersonAudit pmsBonusPersonAudit) {
        return pmsBonusPersonAuditMapper.selectPmsBonusPersonAuditList(pmsBonusPersonAudit);
    }

    /**
     * 新增奖金人员审核
     *
     * @param param 奖金人员审核
     * @return 结果 吧bhgyy8
     */
    @Override
    public AjaxResult insertPmsBonusPersonAudit(PmsBonusPersonAuditDto param) {
        PmsBonusPersonAudit audit = param.getAudit();
        JSONArray list = param.getData();
        String deptCode = audit.getDeptCode();
        Date nowDate = audit.getNowDate();
        PmsBonusPersonAudit existAudit;
        PmsBonusPersonAudit query = new PmsBonusPersonAudit();
        query.setDeptCode(deptCode);
        query.setNowDate(nowDate);
        query.setDelFlag("0");
        List<PmsBonusPersonAudit> existAuditList = pmsBonusPersonAuditMapper.selectPmsBonusPersonAuditList(query);
        //保存审核记录
        if (CollectionUtils.isEmpty(existAuditList)) {
            log.info("第一次保存");
            audit.setDelFlag("0");
            audit.preInsert();
            audit.setAuditState(Constants.CHECK_DRAFT);
            pmsBonusPersonAuditMapper.insertPmsBonusPersonAudit(audit);
            existAudit = audit;
        } else {
            existAudit = existAuditList.get(0);
        }
        //保存指标数据

        if (!StringUtils.equals(Constants.CHECK_DRAFT, existAudit.getAuditState())) {
            return AjaxResult.error("只有草稿状态才可保存或者提交");
        }
        //删除旧数据
        PmsEvaluateDataTemp tempDelete = new PmsEvaluateDataTemp();
        tempDelete.setDelFlag("0");
        tempDelete.setDeptCode(deptCode);
        tempDelete.setActDate(nowDate);
        evaluateDataTempMapper.realDelete(tempDelete);
        //保存新数据
        List<PmsEvaluateDataTemp> tempList = new ArrayList<>();
        list.forEach(item -> {
            Map<String, Object> itemObj = (LinkedHashMap) item;
            String unitCode = (String) itemObj.get("unitCode");
            String remark = (String) itemObj.get("remark");
            for (String k : itemObj.keySet()) {
                if (!StringUtils.equals(k, "unitCode") &&
                        !StringUtils.equals(k, "deptCode") &&
                        !StringUtils.equals(k, "docName") &&
                        !StringUtils.equals(k, "docCode") &&
                        !StringUtils.equals(k, "deptRoomName") &&
                        !StringUtils.equals(k, "actDate") &&
                        !StringUtils.equals(k, "deptRoomCode") &&
                        !StringUtils.contains(k, "_") &&
                        !StringUtils.contains(k, "remark") &&
                        itemObj.get(k) != null
                ) {
                    PmsEvaluateDataTemp insert = new PmsEvaluateDataTemp();
                    insert.setUnitCode(unitCode);
                    if (itemObj.get(k + "_") != null && StringUtils.isNotEmpty(itemObj.get(k + "_") + "")) {
                        insert.setGuideValue(new BigDecimal(itemObj.get(k + "_") + ""));
                    }
                    insert.setGuideCode(k);
                    insert.setGuideValueResult(new BigDecimal(itemObj.get(k) + ""));
                    insert.setGuideType("R");
                    insert.setActDate(nowDate);
                    insert.setDeptCode(deptCode);
                    insert.setDelFlag("0");
                    insert.setRemark(remark);
                    insert.preInsert();
                    tempList.add(insert);
                }
            }

        });
        if (CollectionUtils.isNotEmpty(tempList)) {
            evaluateDataTempMapper.insertBatch(tempList);
        }
        //启动流程
        String procedureId = existAudit.getProcedureId();
        if (StringUtils.isEmpty(procedureId)) {
            HrProcessModel modelQuery = new HrProcessModel();
            modelQuery.setTabelName("pms_bonus_person_audit");
            HrProcessModel model = hrProcessModelService.selectHrProcessModelList(modelQuery).get(0);
            String processUrl = ProcessUtils.getConfigValueByKey("processUrl");
            Map<String, String> params = ProcessUtils.startProcess(model.getProcinsId(),
                    "pms_bonus_person_audit",
                    existAudit.getId(),
                    DateUtil.format(existAudit.getNowDate(), "yyyy年MM月奖金生成人员"),
                    model.getFormUrl());
            String result = HttpConnectionUtil.post(processUrl + "/api/wf/startProcess", params);
            if (StringUtils.isNotEmpty(result)) {
                JSONObject json = JSONObject.parseObject(result);
                Map<String, String> map = (Map<String, String>) json.get("data");
                if (StringUtils.isNotEmpty(map)) {
                    procedureId = map.get("procInsId");
                    //将流程的示例ID写到业务表中
                    existAudit.setProcedureId(procedureId);
                    pmsBonusPersonAuditMapper.updatePmsBonusPersonAudit(existAudit);
                }
            }
            Map<String, String> params2 = ProcessUtils.startProcess(procedureId, existAudit.getId(), DateUtil.format(existAudit.getNowDate(), "yyyy年MM月奖金生成人员"));
            HttpConnectionUtil.post(ProcessUtils.getConfigValueByKey("processUrl") + "/api/wf/addBusinessData", params2);
        }

        //提交流程
        if (param.getIsCommit()) {
            Map<String, String> params = ProcessUtils.dealProcess(procedureId, audit.getComment(), audit.getAssignee());
            params.put("actionName", "提交");
            String result = HttpConnectionUtil.post(ProcessUtils.getConfigValueByKey("processUrl") + "/api/wf/completeTask", params);
            JSONObject resultObj = JSONObject.parseObject(result);
            int intValue = resultObj.getIntValue("code");
            if (intValue == 200) {
                //修改数据
                PmsBonusPersonAudit update = new PmsBonusPersonAudit();
                update.setAuditState(Constants.CHECK_SUBMIT);
                update.preUpdate();
                update.setId(existAudit.getId());
                pmsBonusPersonAuditMapper.updatePmsBonusPersonAudit(update);
            }
        }
        return AjaxResult.success(procedureId);
    }

    /**
     * 修改奖金人员审核
     *
     * @param pmsBonusPersonAudit 奖金人员审核
     * @return 结果
     */
    @Override
    public int updatePmsBonusPersonAudit(PmsBonusPersonAudit pmsBonusPersonAudit) {
        pmsBonusPersonAudit.preUpdate();
        return pmsBonusPersonAuditMapper.updatePmsBonusPersonAudit(pmsBonusPersonAudit);
    }

    /**
     * 批量删除奖金人员审核
     *
     * @param ids 需要删除的奖金人员审核ID
     * @return 结果
     */
    @Override
    public int deletePmsBonusPersonAuditByIds(Long[] ids) {
        return pmsBonusPersonAuditMapper.deletePmsBonusPersonAuditByIds(ids);
    }

    /**
     * 删除奖金人员审核信息
     *
     * @param id 奖金人员审核ID
     * @return 结果
     */
    @Override
    public int deletePmsBonusPersonAuditById(Long id) {
        return pmsBonusPersonAuditMapper.deletePmsBonusPersonAuditById(id);
    }

    @Override
    public AjaxResult audit(PmsBonusPersonAuditDto param) {
        Long id = param.getId();
        String auditState = param.getAuditState();
        PmsBonusPersonAudit exit = pmsBonusPersonAuditMapper.selectPmsBonusPersonAuditById(id);
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
                exit.setAuditState(param.getAuditState());
                exit.preUpdate();
                JSONObject data = JSONObject.parseObject(dataObj.toString());
                Object isFirstActivityObj = data.get("isFirstActivity");
                boolean isFirstActivity = false;
                if (isFirstActivityObj != null) {
                    isFirstActivity = (boolean) isFirstActivityObj;
                }
                if (isFirstActivity && auditState.equals(Constants.CHECK_REFUSE)) {
                    exit.setAuditState(Constants.CHECK_DRAFT);
                    pmsBonusPersonAuditMapper.updatePmsBonusPersonAudit(exit);
                }
            }
            pmsBonusPersonAuditMapper.updatePmsBonusPersonAudit(exit);
            if (StringUtils.equals(Constants.CHECK_PASS, param.getAuditState())) {
                //覆盖旧数据
                updateData(exit);
            }

        } else if (code == Constants.HTTP_CODE2) {
            return AjaxResult.error("没有权限审核！");
        }
        return AjaxResult.success("审核成功");
    }

    @Override
    public void updateData(PmsBonusPersonAudit exit) {
        PmsEvaluateData query = new PmsEvaluateData();
        query.setDelFlag("0");
        query.setDeptCode(exit.getDeptCode());
        query.setActDate(exit.getNowDate());
        query.setGuideType("R");
        query.setDataType("3");
        List<PmsEvaluateData> existDataList = evaluateDataMapper.selectPmsEvaluateDataList(query);
        PmsEvaluateDataTemp queryTemp = new PmsEvaluateDataTemp();
        queryTemp.setDelFlag("0");
        queryTemp.setDeptCode(exit.getDeptCode());
        queryTemp.setActDate(exit.getNowDate());
        queryTemp.setGuideType("R");
        List<PmsEvaluateDataTemp> tempList = evaluateDataTempMapper.selectPmsEvaluateDataTempList(queryTemp);

        //要删除的数据
        List<Long> deleteIds = existDataList.stream().filter(f -> {
            AtomicBoolean flag = new AtomicBoolean(true);
            tempList.forEach(item -> {
                String deptCode = item.getDeptCode();
                Date actDate = item.getActDate();
                String unitCode = item.getUnitCode();
                String guideCode = item.getGuideCode();
                String guideType = item.getGuideType();
                if (StringUtils.equals(f.getDeptCode(), deptCode) &&
                        f.getActDate().equals(actDate) &&
                        StringUtils.equals(f.getUnitCode(), unitCode) &&
                        StringUtils.equals(f.getGuideCode(), guideCode) &&
                        StringUtils.equals(f.getGuideType(), guideType)
                ) {

                    flag.set(false);
                }

            });
            return flag.get();

        }).map(PmsEvaluateData::getId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(deleteIds)) {
            evaluateDataMapper.deletePmsEvaluateDataByIds(deleteIds.toArray(new Long[deleteIds.size()]));
        }

        //新增的数据
        List<PmsEvaluateDataTemp> addList = tempList.stream().filter(f -> {
            AtomicBoolean flag = new AtomicBoolean(true);
            existDataList.forEach(item -> {
                String deptCode = item.getDeptCode();
                Date actDate = item.getActDate();
                String unitCode = item.getUnitCode();
                String guideCode = item.getGuideCode();
                String guideType = item.getGuideType();
                if (StringUtils.equals(f.getDeptCode(), deptCode) &&
                        f.getActDate().equals(actDate) &&
                        StringUtils.equals(f.getUnitCode(), unitCode) &&
                        StringUtils.equals(f.getGuideCode(), guideCode) &&
                        StringUtils.equals(f.getGuideType(), guideType)
                ) {
                    flag.set(false);
                }
            });
            return flag.get();

        }).collect(Collectors.toList());
        addList.forEach(item -> {
            item.setGuideType("R");
            item.setDataType("3");
            item.setId(null);
            try {
                item.preInsert();
            } catch (Exception e) {
                log.info("获取用户数据失败");
            }
        });
        if (CollectionUtils.isNotEmpty(addList)) {
            evaluateDataMapper.insertBatch2(addList);
        }

        //修改的数据
        List<PmsEvaluateData> updateList = new ArrayList<>();
        existDataList.forEach(f -> {
            tempList.forEach(item -> {
                String deptCode = item.getDeptCode();
                Date actDate = item.getActDate();
                String unitCode = item.getUnitCode();
                String guideCode = item.getGuideCode();
                String guideType = item.getGuideType();
                if (StringUtils.equals(f.getDeptCode(), deptCode) &&
                        f.getActDate().equals(actDate) &&
                        StringUtils.equals(f.getUnitCode(), unitCode) &&
                        StringUtils.equals(f.getGuideCode(), guideCode) &&
                        StringUtils.equals(f.getGuideType(), guideType)
                ) {
                    PmsEvaluateData update = new PmsEvaluateData();
                    update.setId(f.getId());
                    update.setGuideValueResult(item.getGuideValueResult());
                    try {
                        update.preUpdate();
                    } catch (Exception e) {
                        log.info("获取用户数据失败");
                    }
                    updateList.add(update);
                }
            });
        });
        if (CollectionUtils.isNotEmpty(updateList)) {
            evaluateDataMapper.updateBatch(updateList);
        }

    }
}

