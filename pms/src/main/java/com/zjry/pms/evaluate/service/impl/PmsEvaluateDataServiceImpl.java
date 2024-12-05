package com.zjry.pms.evaluate.service.impl;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.StringUtils;
import com.zjry.pms.bonus.domain.PmsBonusPersonAudit;
import com.zjry.pms.bonus.service.IPmsBonusPersonAuditService;
import com.zjry.pms.common.Constants;
import com.zjry.pms.common.utils.UserUtils;
import com.zjry.pms.evaluate.domain.PmsEvaluateDataAccountDept;
import com.zjry.pms.evaluate.domain.PmsEvaluatePersonNotBonus;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.evaluate.mapper.PmsEvaluateDataMapper;
import com.zjry.pms.evaluate.domain.PmsEvaluateData;
import com.zjry.pms.evaluate.service.IPmsEvaluateDataService;

/**
 * 指标数据值（外部获取）Service业务层处理
 * 
 * @author zjry
 * @date 2022-10-17
 */
@Service
public class PmsEvaluateDataServiceImpl extends ServiceImpl<PmsEvaluateDataMapper, PmsEvaluateData> implements IPmsEvaluateDataService {
    @Autowired
    private PmsEvaluateDataMapper pmsEvaluateDataMapper;
    @Autowired
    private IPmsBonusPersonAuditService iPmsBonusPersonAuditService;

    /**
     * 查询指标数据值（外部获取）
     * 
     * @param id 指标数据值（外部获取）ID
     * @return 指标数据值（外部获取）
     */
    @Override
    public PmsEvaluateData selectPmsEvaluateDataById(Long id) {
        return pmsEvaluateDataMapper.selectPmsEvaluateDataById(id);
    }

    /**
     * 查询指标数据值（外部获取）列表
     * 
     * @param pmsEvaluateData 指标数据值（外部获取）
     * @return 指标数据值（外部获取）
     */
    @Override
    public List<PmsEvaluateData> selectPmsEvaluateDataList(PmsEvaluateData pmsEvaluateData) {
        return pmsEvaluateDataMapper.selectPmsEvaluateDataList(pmsEvaluateData);
    }

    /**
     * 新增指标数据值（外部获取）
     * 
     * @param pmsEvaluateData 指标数据值（外部获取）
     * @return 结果
     */
    @Override
    public int insertPmsEvaluateData(PmsEvaluateData pmsEvaluateData) {
    	pmsEvaluateData.preInsert();
        return pmsEvaluateDataMapper.insertPmsEvaluateData(pmsEvaluateData);
    }

    /**
     * 修改指标数据值（外部获取）
     * 
     * @param pmsEvaluateData 指标数据值（外部获取）
     * @return 结果
     */
    @Override
    public int updatePmsEvaluateData(PmsEvaluateData pmsEvaluateData) {
    	pmsEvaluateData.preUpdate();
        return pmsEvaluateDataMapper.updatePmsEvaluateData(pmsEvaluateData);
    }

    /**
     * 批量删除指标数据值（外部获取）
     * 
     * @param ids 需要删除的指标数据值（外部获取）ID
     * @return 结果
     */
    @Override
    public int deletePmsEvaluateDataByIds(Long[] ids) {
        return pmsEvaluateDataMapper.deletePmsEvaluateDataByIds(ids);
    }

    /**
     * 删除指标数据值（外部获取）信息
     * 
     * @param id 指标数据值（外部获取）ID
     * @return 结果
     */
    @Override
    public int deletePmsEvaluateDataById(Long id) {
        return pmsEvaluateDataMapper.deletePmsEvaluateDataById(id);
    }

    /**
     * 获取科室对应奖金指标奖金值
     * @param pmsEvaluateData 日期
     * @return 结果
     */
    @Override
    public List<JSONObject> selectAccountDeptEvaluateDataList(PmsEvaluateData pmsEvaluateData) {
        List<JSONObject> result = new ArrayList<>();
        List<PmsEvaluateDataAccountDept> tempList = pmsEvaluateDataMapper.selectAccountDeptEvaluateDataList(pmsEvaluateData);
        if(CollectionUtils.isNotEmpty(tempList)){
            Set<String> set = tempList.stream().map(data -> data.getDeptRoomCode()).collect(Collectors.toSet());
            QueryWrapper<PmsBonusPersonAudit> wrapper = new QueryWrapper<>();
            wrapper.lambda().select(PmsBonusPersonAudit::getAuditState, PmsBonusPersonAudit::getDeptCode)
                    .eq(PmsBonusPersonAudit::getCompCode, UserUtils.getSysUser().getDeptId())
                    .eq(PmsBonusPersonAudit::getNowDate, pmsEvaluateData.getActDate())
                    .in(PmsBonusPersonAudit::getDeptCode, set).orderByDesc(PmsBonusPersonAudit::getUpdateTime);
            List<PmsBonusPersonAudit> auditList = iPmsBonusPersonAuditService.list(wrapper);
            Map<String, String> auditMap = new HashMap<>();
            if(CollectionUtils.isNotEmpty(auditList)){
                auditMap = auditList.stream().collect(Collectors.toMap(PmsBonusPersonAudit::getDeptCode, PmsBonusPersonAudit::getAuditState, (v1,v2)->v1));
            }
            for(PmsEvaluateDataAccountDept item : tempList){
                String deptRoomCode = item.getDeptRoomCode();
                JSONObject comItem = exitDeptRoom(deptRoomCode, result);
                if (comItem == null) {
                    comItem = new JSONObject();
                    comItem.put("actDate", item.getActDate());
                    comItem.put("deptRoomId", item.getDeptRoomId());
                    comItem.put("deptRoomCode", deptRoomCode);
                    comItem.put("deptRoomName", item.getDeptRoomName());
                    if(auditMap.containsKey(item.getDeptRoomId())){
                        comItem.put("auditState", auditMap.get(item.getDeptRoomId()));
                    }else{
                        comItem.put("auditState", Constants.CHECK_DRAFT);
                    }
                    result.add(comItem);
                }
                comItem.put(item.getGuideCode(), item.getGuideValueResult());
            };
        }
        return result;
    }

    /**
     * 获取科室对应奖金指标
     * @param pmsEvaluateData 日期
     * @return 结果
     */
    @Override
    public List<PmsEvaluateDataAccountDept> selectAccountDeptIndex(PmsEvaluateData pmsEvaluateData) {
        return pmsEvaluateDataMapper.selectAccountDeptIndex(pmsEvaluateData);
    }

    /**
     * 获取科室对应人员奖金指标
     * @param pmsEvaluateData 日期 科室编码
     * @return 结果
     */
    @Override
    public List<PmsEvaluateDataAccountDept> selectAccountPersonIndex(PmsEvaluateData pmsEvaluateData) {
        return pmsEvaluateDataMapper.selectAccountPersonIndex(pmsEvaluateData);
    }

    /**
     * 获取科室对应人员奖金指标奖金值
     * @param pmsEvaluateData 日期 科室编码
     * @return 结果
     */
    @Override
    public List<JSONObject> selectAccountPersonEvaluateDataList(PmsEvaluateData pmsEvaluateData) {
        // 判断科室在此日期下是否已修改保存过奖金数据 如果已修改过但未提交审核的查询临时表内数据 未修改过查询原始数据表数据 修改过但已通过审核的查询原始表数据
        PmsBonusPersonAudit pmsBonusPersonAuditNew = new PmsBonusPersonAudit();
        pmsBonusPersonAuditNew.setNowDate(pmsEvaluateData.getActDate());
        pmsBonusPersonAuditNew.setDeptCode(pmsEvaluateData.getDeptCode());
        List<PmsBonusPersonAudit> pmsBonusPersonAuditList = iPmsBonusPersonAuditService.selectPmsBonusPersonAuditList(pmsBonusPersonAuditNew);
        List<PmsEvaluateDataAccountDept> tempList = new ArrayList<>();
        // 人员奖金审核表中无数据 直接查询原始数据表内数据
        if (pmsBonusPersonAuditList.isEmpty()){
            tempList = pmsEvaluateDataMapper.selectAccountPersonEvaluateDataList(pmsEvaluateData);
        } else
        // 审核状态为草稿 审核中 驳回时 查询临时表数据
        if (pmsBonusPersonAuditList.get(0).getAuditState().equals(Constants.CHECK_DRAFT)
                || pmsBonusPersonAuditList.get(0).getAuditState().equals(Constants.CHECK_SUBMIT)
                || pmsBonusPersonAuditList.get(0).getAuditState().equals(Constants.CHECK_REFUSE)){
            tempList = pmsEvaluateDataMapper.selectAccountPersonEvaluateDataTempList(pmsEvaluateData);
        } else
        // 审核状态为通过 终止时 查询原始表数据
        if (pmsBonusPersonAuditList.get(0).getAuditState().equals(Constants.CHECK_PASS)
                || pmsBonusPersonAuditList.get(0).getAuditState().equals(Constants.CHECK_STOP)){
            tempList = pmsEvaluateDataMapper.selectAccountPersonEvaluateDataList(pmsEvaluateData);
        }
        List<JSONObject> result = new ArrayList<>();
        tempList.forEach(item -> {
            String deptRoomCode = item.getDeptRoomCode();
            String docCode = item.getDocCode();
            JSONObject comItem = exitDocRoom(docCode, result);
            if (comItem == null) {
                comItem = new JSONObject();
                comItem.put("docCode", docCode);
                comItem.put("deptRoomCode", deptRoomCode);
                comItem.put("deptRoomName", item.getDeptRoomName());
                comItem.put("docName", item.getDocName());
                comItem.put("remark", item.getRemark());
                result.add(comItem);
            }
            comItem.put(item.getGuideCode(), item.getGuideValueResult());
            comItem.put(item.getGuideCode() + "_", item.getGuideValue());
        });
        return result;
    }

    public JSONObject exitDeptRoom(String deptCode, List<JSONObject> list) {
        for (JSONObject jsonObject : list) {
            String tempCode = jsonObject.getString("deptRoomCode");
            if (StringUtils.equals(tempCode, deptCode)) {
                return jsonObject;
            }
        }
        return null;
    }

    public JSONObject exitDocRoom(String docCode, List<JSONObject> list) {
        for (JSONObject jsonObject : list) {
            String tempCode = jsonObject.getString("docCode");
            if (StringUtils.equals(tempCode, docCode)) {
                return jsonObject;
            }
        }
        return null;
    }

    /**
     * 获取奖金
     * @param pmsEvaluateData
     * @return
     */
    @Override
    public List<PmsEvaluateData> selectBonusList(PmsEvaluateData pmsEvaluateData){
        return pmsEvaluateDataMapper.selectBonusList(pmsEvaluateData);
    }

    @Override
    public List<PmsEvaluatePersonNotBonus> selectDoctorListNotBonus(PmsEvaluateData pmsEvaluateData) {
        // 判断科室在此日期下是否已修改保存过奖金数据 如果已修改过但未提交审核的查询临时表内数据 未修改过查询原始数据表数据 修改过但已通过审核的查询原始表数据
        PmsBonusPersonAudit pmsBonusPersonAuditNew = new PmsBonusPersonAudit();
        pmsBonusPersonAuditNew.setNowDate(pmsEvaluateData.getActDate());
        pmsBonusPersonAuditNew.setDeptCode(pmsEvaluateData.getDeptCode());
        List<PmsBonusPersonAudit> pmsBonusPersonAuditList = iPmsBonusPersonAuditService.selectPmsBonusPersonAuditList(pmsBonusPersonAuditNew);
        List<PmsEvaluatePersonNotBonus> tempList = new ArrayList<>();
        // 人员奖金审核表中无数据 直接查询原始数据表内数据
        if (pmsBonusPersonAuditList.isEmpty()){
            tempList = pmsEvaluateDataMapper.selectDoctorListNotBonusData(pmsEvaluateData);
        } else
            // 审核状态为草稿 审核中 驳回时 查询临时表数据
            if (pmsBonusPersonAuditList.get(0).getAuditState().equals(Constants.CHECK_DRAFT)
                    || pmsBonusPersonAuditList.get(0).getAuditState().equals(Constants.CHECK_SUBMIT)
                    || pmsBonusPersonAuditList.get(0).getAuditState().equals(Constants.CHECK_REFUSE)){
                tempList = pmsEvaluateDataMapper.selectDoctorListNotBonus(pmsEvaluateData);
            } else
                // 审核状态为通过 终止时 查询原始表数据
                if (pmsBonusPersonAuditList.get(0).getAuditState().equals(Constants.CHECK_PASS)
                        || pmsBonusPersonAuditList.get(0).getAuditState().equals(Constants.CHECK_STOP)){
                    tempList = pmsEvaluateDataMapper.selectDoctorListNotBonusData(pmsEvaluateData);
                }
        return tempList;
    }

    @Override
    public List<PmsEvaluateData> selectDeptBonusList(PmsEvaluateData pmsEvaluateData) {
        return pmsEvaluateDataMapper.selectDeptBonusList(pmsEvaluateData);
    }
}