package com.zjry.pms.bonus.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.utils.DateUtils;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.pms.apportionment.domain.CostDatabase;
import com.zjry.pms.apportionment.mapper.CostDatabaseMapper;
import com.zjry.pms.apportionment.service.DBChangeService;
import com.zjry.pms.bonus.domain.PmsAccountDeptNew;
import com.zjry.pms.bonus.domain.PmsBonusCalculation;
import com.zjry.pms.bonus.domain.PmsBonusCalculationHis;
import com.zjry.pms.bonus.domain.PmsBonusPersonAudit;
import com.zjry.pms.bonus.mapper.PmsBonusCalculationMapper;
import com.zjry.pms.bonus.mapper.PmsBonusCalculationNewMapper;
import com.zjry.pms.bonus.service.IPmsBonusCalculationNewService;
import com.zjry.pms.bonus.service.IPmsBonusPersonAuditService;
import com.zjry.pms.common.Constants;
import com.zjry.pms.common.service.impl.WebSocketServer;
import com.zjry.pms.common.utils.UserUtils;
import com.zjry.pms.evaluate.domain.*;
import com.zjry.pms.evaluate.mapper.PmsEvaluateDataMapper;
import com.zjry.pms.evaluate.service.IPmsEvaluateIndexService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 奖金核算Service业务层处理
 *
 * @author zjry
 * @date 2022-10-19
 */
@Service
public class PmsBonusCalculationNewServiceImpl extends ServiceImpl<PmsBonusCalculationMapper, PmsBonusCalculation> implements IPmsBonusCalculationNewService {

    @Autowired
    private PmsBonusCalculationMapper pmsBonusCalculationMapper;
    @Autowired
    private PmsBonusCalculationNewMapper pmsBonusCalculationNewMapper;
    @Autowired
    private PmsEvaluateDataMapper pmsEvaluateDataMapper;
    @Resource
    private CostDatabaseMapper costDatabaseMapper;
    @Autowired
    private DBChangeService dbChangeService;
    @Autowired
    private IPmsEvaluateIndexService pmsEvaluateIndexService;
    @Autowired
    private IPmsBonusPersonAuditService iPmsBonusPersonAuditService;
    /* 进度条开始进度 */
    private static final int PERCENTAGE_START = 10;
    /* 进度条结束进度 */
    private static final int PERCENTAGE_END = 100;


    @Override
    public List<PmsEvaluateData> selectBonusList(PmsEvaluateData pmsEvaluateData) {
        return  pmsBonusCalculationNewMapper.selectBonusList(pmsEvaluateData);
    }

    @Override
    public List<JSONObject> selectAccountDeptEvaluateDataList(PmsEvaluateData pmsEvaluateData) {
        List<JSONObject> result = new ArrayList<>();
        List<PmsEvaluateDataAccountDept> tempList = pmsBonusCalculationNewMapper.selectAccountDeptEvaluateDataList(pmsEvaluateData);
        if(CollectionUtils.isNotEmpty(tempList)){
            for(PmsEvaluateDataAccountDept item : tempList){
                String deptRoomCode = item.getDeptRoomCode();
                JSONObject comItem = exitDeptRoom(deptRoomCode, result);
                if (comItem == null) {
                    comItem = new JSONObject();
                    comItem.put("actDate", item.getActDate());
                    comItem.put("deptRoomId", item.getDeptRoomId());
                    comItem.put("deptRoomCode", deptRoomCode);
                    comItem.put("deptRoomName", item.getDeptRoomName());
                    result.add(comItem);
                }
                comItem.put(item.getGuideCode(), item.getGuideValueResult());
            };
        }
        return result;
    }

    @Override
    public List<PmsEvaluateDataAccountDept> selectAccountDeptIndex(PmsEvaluateData pmsEvaluateData) {
        if(pmsEvaluateData.getDeptType().equals("0")){
            pmsEvaluateData.setDeptType("2");
        }
        return pmsBonusCalculationNewMapper.selectAccountDeptIndex(pmsEvaluateData);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult bonusCommit(PmsBonusCalculation pmsBonusCalculation) {
        PmsBonusCalculation exit = pmsBonusCalculationMapper.selectPmsBonusCalculationById(pmsBonusCalculation.getId());
        //提交流程
        if (!com.zjry.common.utils.StringUtils.equals(exit.getAuditState(), "0")) {
            return AjaxResult.error("奖金状态已更新,无法提交");
        }
        String procedureId = RandomStringUtils.randomAlphanumeric(32);
        //修改状态
        PmsBonusCalculation update = new PmsBonusCalculation();
        update.setId(pmsBonusCalculation.getId());
        update.setAuditState("1");
        update.setProcedureId(procedureId);
        update.preUpdate();
        pmsBonusCalculationMapper.updatePmsBonusCalculation(update);
        //插入历史
        PmsBonusCalculationHis pmsBonusCalculationHis = new PmsBonusCalculationHis();
        pmsBonusCalculationHis.setProcedureId(procedureId);
        pmsBonusCalculationHis.setCalculationId(pmsBonusCalculation.getId());
        pmsBonusCalculationHis.setCommitId(Long.parseLong(update.getUpdateBy()));
        pmsBonusCalculationHis.setCommitDate(DateUtils.getNowDate());
        pmsBonusCalculationHis.setCreatDate(DateUtils.getNowDate());
        pmsBonusCalculationHis.setHisState("1");
        pmsBonusCalculationNewMapper.insertHis(pmsBonusCalculationHis);
        return AjaxResult.success("提交成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult bonusAudit(PmsBonusCalculation pmsBonusCalculation) {
        PmsBonusCalculation exit = pmsBonusCalculationMapper.selectPmsBonusCalculationById(pmsBonusCalculation.getId());
        if (!com.zjry.common.utils.StringUtils.equals(exit.getAuditState(), "1")) {
            return AjaxResult.error("奖金状态未提交,无法审核");
        }
        //修改状态
        PmsBonusCalculation update = new PmsBonusCalculation();
        update.setId(pmsBonusCalculation.getId());
        update.setAuditState(pmsBonusCalculation.getAuditState());
        update.setProcedureId(exit.getProcedureId());
        update.preUpdate();
        pmsBonusCalculationMapper.updatePmsBonusCalculation(update);
        //插入历史
        PmsBonusCalculationHis pmsBonusCalculationHis = new PmsBonusCalculationHis();
        pmsBonusCalculationHis.setProcedureId(exit.getProcedureId());
        pmsBonusCalculationHis.setCalculationId(pmsBonusCalculation.getId());
        pmsBonusCalculationHis.setAuditId(Long.parseLong(update.getUpdateBy()));
        pmsBonusCalculationHis.setAuditDate(DateUtils.getNowDate());
        pmsBonusCalculationHis.setCreatDate(DateUtils.getNowDate());
        pmsBonusCalculationHis.setHisState(pmsBonusCalculation.getAuditState());
        pmsBonusCalculationNewMapper.insertHis(pmsBonusCalculationHis);
        return AjaxResult.success("审核成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult bonusArchive(Long id) {
        PmsBonusCalculation exit = pmsBonusCalculationMapper.selectPmsBonusCalculationById(id);
        if(null == exit){
            return AjaxResult.error("数据不存在");
        }
        if(!Constants.CHECK_PASS.equals(exit.getAuditState())){
            return AjaxResult.error("审核未完成的数据，不可归档");
        }
        //修改状态
        PmsBonusCalculation update = new PmsBonusCalculation();
        update.setId(exit.getId());
        update.setArchiveState(Constants.COMMON_YES);
        update.preUpdate();
        pmsBonusCalculationMapper.updatePmsBonusCalculation(update);
        //插入历史
        PmsBonusCalculationHis pmsBonusCalculationHis = new PmsBonusCalculationHis();
        pmsBonusCalculationHis.setProcedureId(exit.getProcedureId());
        pmsBonusCalculationHis.setCalculationId(exit.getId());
        pmsBonusCalculationHis.setArchiveId(Long.parseLong(update.getUpdateBy()));
        pmsBonusCalculationHis.setArchiveDate(DateUtils.getNowDate());
        pmsBonusCalculationHis.setCreatDate(DateUtils.getNowDate());
        pmsBonusCalculationHis.setHisState(Constants.COMMON_YES);
        pmsBonusCalculationNewMapper.insertHis(pmsBonusCalculationHis);
        return AjaxResult.success("归档成功");
    }

    public JSONObject exitDeptRoom(String deptCode, List<JSONObject> list) {
        for (JSONObject jsonObject : list) {
            String tempCode = jsonObject.getString("deptRoomCode");
            if (com.zjry.common.utils.StringUtils.equals(tempCode, deptCode)) {
                return jsonObject;
            }
        }
        return null;
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
        List<PmsEvaluateData> pmsEvaluateData = this.calculateEvaluateIndex(DateUtils.parseDateToStr(Constants.DATE_MONTH_FORMATTER, pmsBonusCalculation.getPeriod()));
        return CollectionUtils.isNotEmpty(pmsEvaluateData)?pmsEvaluateData.size():0;
    }



    public List<PmsEvaluateData> calculateEvaluateIndex(String actDate){
        actDate = StringUtils.isNotBlank(actDate)?actDate:DateUtils.parseDateToStr(Constants.DATE_MONTH_FORMATTER, new Date());
        List<PmsEvaluateData> list = this.calculateEvaluateIndexOfBSC(actDate);
        if(CollectionUtils.isNotEmpty(list)){
            pmsEvaluateDataMapper.insertBatch(list);
        }
        sendWsMessage("100");
        WebSocketServer.close(String.valueOf(UserUtils.getSysUser().getUserId()));
        return list;
    }
    @Transactional
    public List<PmsEvaluateData> calculateEvaluateIndexOfBSC(String actDate){
        List<PmsEvaluateData> dataList = new ArrayList<>();
        Long compCode = UserUtils.getSysUser().getDeptId();
        /* 删除旧数据 */
        PmsEvaluateData param = new PmsEvaluateData();
        param.setActDate(DateUtils.parseDate(actDate));
//        param.setDataType("1");
        param.setCompCode(compCode);
        pmsBonusCalculationNewMapper.deleteBatch(param);

       //根据日期查询科室核算信息
        List<PmsAccountDeptNew> pmsAccountDeptNewList = pmsBonusCalculationNewMapper.selectAccountDeptByDate(actDate,compCode);
        if(CollectionUtils.isEmpty(pmsAccountDeptNewList)){
            return new ArrayList<>();
        }
        //根据查出的科室核算类别，查找指标集的设置
        List<PmsAccountDeptNew> accountTypeList = pmsAccountDeptNewList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                new TreeSet<>(Comparator.comparing(PmsAccountDeptNew :: getAccountType))), ArrayList::new));
        List<String> accountTypes = accountTypeList.stream()
                .map(PmsAccountDeptNew::getAccountType)
                .distinct()
                .collect(Collectors.toList());
        //指标集设置中独立核算是0 平均奖1，科室核算设置中独立是2 平均是1
        accountTypes = accountTypes.stream()
                .filter(o -> !o.equals("0"))
                .map(fruit -> fruit.equals("2") ? "0" : fruit)
                .collect(Collectors.toList());
        List<PmsEvaluateIndexBonus> pmsEvaluateIndexBonusList = pmsBonusCalculationNewMapper.selectEvaluateBonusByRoomType(accountTypes,compCode);
        if(CollectionUtils.isEmpty(pmsEvaluateIndexBonusList)){
            return new ArrayList<>();
        }
        //根据指标集查询指标
        List<String> indexCodes = pmsEvaluateIndexBonusList.stream()
                .map(PmsEvaluateIndexBonus::getIndexCode)
                .collect(Collectors.toList());
        List<PmsEvaluateIndex> indexList = pmsBonusCalculationNewMapper.selectEvaluateIndexById(indexCodes);
        if(CollectionUtils.isEmpty(indexList)){
            return new ArrayList<>();
        }
        List<PmsEvaluateData> pmsEvaluateDataList = new ArrayList<>();
        Map<String, PmsEvaluateIndex> indexMap = indexList.stream().collect(Collectors.toMap(PmsEvaluateIndex::getIndexCode, Function.identity()));
        Map<String, List<PmsEvaluateIndex>> indexListMap = indexList.stream().collect(Collectors.groupingBy(PmsEvaluateIndex::getIsExpress));
        /* 先计算SQL类型指标，再计算表达式类型指标 */
        if(indexListMap.containsKey(Constants.COMMON_NO)){
            calculateEvaluateOfSql(pmsEvaluateIndexBonusList,indexListMap.get(Constants.COMMON_NO), actDate, indexList.size(), pmsEvaluateDataList);
        }
        //表达式先不管
//        if(indexListMap.containsKey(Constants.COMMON_YES)){
//            calculateEvaluateOfExpr(indexListMap.get(Constants.COMMON_YES), indexMap, resultMap);
//        }
        //数据处理
        if(CollectionUtils.isEmpty(pmsEvaluateDataList)){
            return new ArrayList<>();
        }
        for (PmsEvaluateData pmsEvaluateData : pmsEvaluateDataList) {
            if(pmsEvaluateData.getDeptType().equals("0")){
                pmsEvaluateData.setDeptType("2");
            }
            for (PmsAccountDeptNew pmsAccountDeptNew : pmsAccountDeptNewList) {
                if(pmsEvaluateData.getUnitCode().equals(pmsAccountDeptNew.getDeptRoomCode() )&&
                        pmsAccountDeptNew.getAccountType().equals(pmsEvaluateData.getDeptType())){
                    pmsEvaluateData.preInsert();
                    pmsEvaluateData.setActDate(DateUtils.parseDate(actDate));
                    pmsEvaluateData.setDeptCode(pmsAccountDeptNew.getDeptRoomCode());
                    pmsEvaluateData.setGuideValueResult(pmsEvaluateData.getGuideValue());
                    pmsEvaluateData.setDataType("0");
                    pmsEvaluateData.setCompCode(UserUtils.getSysUser().getDeptId());
                    dataList.add(pmsEvaluateData);
                }
            }
        }
        return  dataList;
    }

    private void calculateEvaluateOfSql(List<PmsEvaluateIndexBonus> pmsEvaluateIndexBonusList,List<PmsEvaluateIndex> evaluateIndexList, String actDate, int dataSize,  List<PmsEvaluateData> pmsEvaluateDataList){
        for(PmsEvaluateIndex index : evaluateIndexList){
            try {
                if(StringUtils.isNotBlank(index.getDbCode())){
                    List<PmsEvaluateIndexBonus> list = pmsEvaluateIndexBonusList.stream().filter(o ->o.getIndexCode().equals(index.getIndexCode()))
                            .collect(Collectors.toList());
                    String type = list.get(0).getDeptRoomType();
                    pmsEvaluateDataList.addAll(this.generateDataFromSql(index,actDate,type));
                    calculatePercentage(pmsEvaluateDataList.size(), dataSize);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /** 表达式指标计算 */
    private void calculateEvaluateOfExpr(List<PmsEvaluateIndex> evaluateIndexList, Map<String, PmsEvaluateIndex> indexMap, Map<String, List<PmsEvaluateData>> resultMap){
        for(PmsEvaluateIndex index : evaluateIndexList){
            try {
                pmsEvaluateIndexService.generateDataFromExpr(index, indexMap, resultMap);
                calculatePercentage(resultMap.size(), indexMap.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public List<PmsEvaluateData> generateDataFromSql(PmsEvaluateIndex pmsEvaluateIndex,String date,String type)throws Exception {
        List<PmsEvaluateData> evaluateDataList = new ArrayList<>();
        CostDatabase costDatabase = costDatabaseMapper.selectCostDatabaseByCode(pmsEvaluateIndex.getDbCode());
        //判断sql是否添加奖金生成日期的条件
        if(StringUtils.isNotBlank(pmsEvaluateIndex.getIndexSql())){
            pmsEvaluateIndex.setIndexSql(pmsEvaluateIndex.getIndexSql()+" and ST_DATE = date_format('"+date+"', '%m-%Y')");
        }
        List<Map<String, Object>> dataList = dbChangeService.getDataFromDb(costDatabase, pmsEvaluateIndex.getIndexSql(), Lists.newArrayList("UNIT_CODE", "GUIDE_VALUE"));
        if(CollectionUtils.isNotEmpty(dataList)){
            dataList.forEach(data->{
                PmsEvaluateData ext = new PmsEvaluateData();
                ext.setUnitCode(String.valueOf(data.get("UNIT_CODE")));
                ext.setGuideValue(new BigDecimal(String.valueOf(data.get("GUIDE_VALUE"))));
                ext.setGuideType("1".equals(pmsEvaluateIndex.getOrgType())?"Y":("2".equals(pmsEvaluateIndex.getOrgType())?"K":"R"));
                ext.setGuideCode(pmsEvaluateIndex.getIndexCode());
                ext.setDeptType(type);
                if(Constants.COMMON_YES.equals(pmsEvaluateIndex.getIsSection())){
                    ext.setActDateBegin(pmsEvaluateIndex.getSectionStart());
                    ext.setActDateEnd(pmsEvaluateIndex.getSectionEnd());
                }
                ext.preInsert();
                evaluateDataList.add(ext);
            });
        }
        return evaluateDataList;
    }
























    /**
     * 进度条控制
     * 1.一开始进度10%；2.数据生成部分进度90%，数据库保存及其他逻辑处理进度10%；3.接口数据完成100%
     * @param curVal
     * @param total
     */
    private void calculatePercentage(int curVal, int total){
        BigDecimal percentage = new BigDecimal(curVal * 100).divide(new BigDecimal(total), 4, RoundingMode.HALF_UP);
        if(percentage.compareTo(new BigDecimal(PERCENTAGE_END - PERCENTAGE_START)) == 1){
            percentage = new BigDecimal(PERCENTAGE_END - PERCENTAGE_START);
        }
        if(percentage.compareTo(new BigDecimal(PERCENTAGE_START)) == 1){
            sendWsMessage(percentage.toPlainString());
        }
    }
    private void sendWsMessage(String msg){
        try {
            WebSocketServer.sendInfo(String.valueOf(msg), String.valueOf(UserUtils.getSysUser().getUserId()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
