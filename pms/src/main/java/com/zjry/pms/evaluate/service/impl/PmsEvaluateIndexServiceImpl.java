package com.zjry.pms.evaluate.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.common.utils.DateUtils;
import com.zjry.pms.apportionment.domain.CostDatabase;
import com.zjry.pms.apportionment.mapper.CostDatabaseMapper;
import com.zjry.pms.apportionment.service.DBChangeService;
import com.zjry.pms.common.Constants;
import com.zjry.pms.common.service.ISysFlowcodeService;
import com.zjry.pms.common.service.impl.WebSocketServer;
import com.zjry.pms.common.utils.UserUtils;
import com.zjry.pms.evaluate.domain.*;
import com.zjry.pms.evaluate.mapper.PmsEvaluateDataMapper;
import com.zjry.pms.evaluate.mapper.PmsEvaluateIndexMapper;
import com.zjry.pms.evaluate.service.IPmsEvaluateIndexService;
import com.zjry.pms.evaluate.service.IPmsEvaluateSetDeptDetailService;
import com.zjry.pms.second.service.IPmsSecondDistributionDetailService;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;
import org.apache.commons.collections4.CollectionUtils;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 指标管理Service业务层处理
 * 
 * @author mk
 * @date 2022-07-26
 */
@Service
public class PmsEvaluateIndexServiceImpl extends ServiceImpl<PmsEvaluateIndexMapper, PmsEvaluateIndex> implements IPmsEvaluateIndexService {
    @Resource
    private PmsEvaluateIndexMapper pmsEvaluateIndexMapper;
    @Autowired
    private ISysFlowcodeService sysFlowcodeService;
    @Resource
    private CostDatabaseMapper costDatabaseMapper;
    @Autowired
    private DBChangeService dbChangeService;
    @Autowired
    private PmsEvaluateDataMapper pmsEvaluateDataMapper;
    @Autowired
    private IPmsEvaluateSetDeptDetailService pmsEvaluateSetDeptDetailService;
    @Autowired
    private IPmsSecondDistributionDetailService pmsSecondDistributionDetailService;

    private static final String EXPR_PREFIX = "ZB";
    /* 进度条开始进度 */
    private static final int PERCENTAGE_START = 10;
    /* 进度条结束进度 */
    private static final int PERCENTAGE_END = 100;

    private static final String EXPR_PATTERN = "\\#\\{([A-Za-z0-9]+)\\}";

    /**
     * 查询指标管理
     * 
     * @param id 指标管理ID
     * @return 指标管理
     */
    @Override
    public PmsEvaluateIndex selectPmsEvaluateIndexById(Long id) {
        return pmsEvaluateIndexMapper.selectPmsEvaluateIndexById(id);
    }

    /**
     * 查询指标管理列表
     * 
     * @param pmsEvaluateIndex 指标管理
     * @return 指标管理
     */
    @Override
    public List<PmsEvaluateIndex> selectPmsEvaluateIndexList(PmsEvaluateIndex pmsEvaluateIndex) {
        return pmsEvaluateIndexMapper.selectPmsEvaluateIndexList(pmsEvaluateIndex);
    }

    /**
     * 新增指标管理
     * 
     * @param pmsEvaluateIndex 指标管理
     * @return 结果
     */
    @Override
    public int insertPmsEvaluateIndex(PmsEvaluateIndex pmsEvaluateIndex) {
       String indexCode = sysFlowcodeService.getEntityFlowcodeNoCode("ZB_INDEX_CODE",pmsEvaluateIndex.getBscSecondCode(),4);
        pmsEvaluateIndex.setIndexCode(indexCode);
    	pmsEvaluateIndex.preInsert();
        pmsEvaluateIndex.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        return pmsEvaluateIndexMapper.insertPmsEvaluateIndex(pmsEvaluateIndex);
    }

    /**
     * 修改指标管理
     * 
     * @param pmsEvaluateIndex 指标管理
     * @return 结果
     */
    @Override
    public int updatePmsEvaluateIndex(PmsEvaluateIndex pmsEvaluateIndex) {
    	pmsEvaluateIndex.preUpdate();
        return pmsEvaluateIndexMapper.updatePmsEvaluateIndex(pmsEvaluateIndex);
    }

    /**
     * 批量删除指标管理
     * 
     * @param ids 需要删除的指标管理ID
     * @return 结果
     */
    @Override
    public int deletePmsEvaluateIndexByIds(Long[] ids) {
        return pmsEvaluateIndexMapper.deletePmsEvaluateIndexByIds(ids);
    }

    /**
     * 删除指标管理信息
     * 
     * @param id 指标管理ID
     * @return 结果
     */
    @Override
    public int deletePmsEvaluateIndexById(Long id) {
        return pmsEvaluateIndexMapper.deletePmsEvaluateIndexById(id);
    }

    @Override
    public List<PmsEvaluateIndex> createDataInfo(PmsEvaluateIndex pmsEvaluateIndex)throws Exception {
        CostDatabase costDatabase = costDatabaseMapper.selectCostDatabaseByCode(pmsEvaluateIndex.getDbCode());
        List<PmsEvaluateIndex> list = dbChangeService.cPmsEvaIndexdb(costDatabase,pmsEvaluateIndex);
        return list;
    }

    /**
     * 指标是否被指标集引用
     * @param id
     * @return
     */
    @Override
    public boolean checkIsUsedInSet(String indexCode){
        return pmsEvaluateIndexMapper.checkIsUsedInSet(indexCode)>0?true:false;
    }

    @Override
    public List<PmsEvaluateData> generateDataFromSql(PmsEvaluateIndex pmsEvaluateIndex)throws Exception {
        List<PmsEvaluateData> evaluateDataList = new ArrayList<>();
        CostDatabase costDatabase = costDatabaseMapper.selectCostDatabaseByCode(pmsEvaluateIndex.getDbCode());
        List<Map<String, Object>> dataList = dbChangeService.getDataFromDb(costDatabase, pmsEvaluateIndex.getIndexSql(), Lists.newArrayList("UNIT_CODE", "GUIDE_VALUE"));
        if(CollectionUtils.isNotEmpty(dataList)){
            dataList.forEach(data->{
                PmsEvaluateData ext = new PmsEvaluateData();
                ext.setUnitCode(String.valueOf(data.get("UNIT_CODE")));
                ext.setGuideValue(new BigDecimal(String.valueOf(data.get("GUIDE_VALUE"))));
                ext.setGuideType("1".equals(pmsEvaluateIndex.getOrgType())?"Y":("2".equals(pmsEvaluateIndex.getOrgType())?"K":"R"));
                ext.setGuideCode(pmsEvaluateIndex.getIndexCode());
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
     * 通过表达式生成数据
     * @param
     */
    @Override
    @Transactional
    public List<PmsEvaluateData> generateDataFromExpr(PmsEvaluateIndex pmsEvaluateIndex, Map<String, PmsEvaluateIndex> indexMap, Map<String, List<PmsEvaluateData>> resultMap){
        if(resultMap.containsKey(pmsEvaluateIndex.getIndexCode())){
            return resultMap.get(pmsEvaluateIndex.getIndexCode());
        }
        List<PmsEvaluateData> resultList = new ArrayList<>();
        String formulaCode = pmsEvaluateIndex.getFormulaCode();
        if(StringUtils.isBlank(formulaCode)){
            return resultList;
        }

        Evaluator evaluator = new Evaluator();
        /* 获取表达式中变量 */
        Set<String> variables = analysisExpr(formulaCode);
        formulaCode = formulaCode.replace("#{", "#{" + EXPR_PREFIX);
        try {
            Map<String,Map<String, PmsEvaluateData>> map = new HashMap<>();
            Set<String> itemSet = new HashSet<>();
             for(String var : variables){
                if(resultMap.containsKey(var)){
                    if(CollectionUtils.isNotEmpty(resultMap.get(var))){
                        map.put(var, resultMap.get(var).stream().collect(Collectors.toMap(PmsEvaluateData::getUnitCode,Function.identity(),(v1,v2)->v1)));
                        for(PmsEvaluateData data : resultMap.get(var)){
                            itemSet.add(data.getUnitCode());
                        }
                    }
                }else{
                    if(indexMap.containsKey(var) && Constants.COMMON_YES.equals(indexMap.get(var).getIsExpress())){
                        List<PmsEvaluateData> dataList = generateDataFromExpr(indexMap.get(var), indexMap, resultMap);
                        resultMap.put(var, dataList);
                        if(CollectionUtils.isNotEmpty(dataList)){
                            map.put(var, dataList.stream().collect(Collectors.toMap(PmsEvaluateData::getUnitCode,Function.identity(),(v1,v2)->v1)));
                            for(PmsEvaluateData data : dataList){
                                itemSet.add(data.getUnitCode());
                            }
                        }
                    }
                }
            };

            for(String item : itemSet){
                String evalStr = formulaCode.replaceAll("#\\{|\\}", "");
                PmsEvaluateData data = new PmsEvaluateData();
                for(String var : variables){
                    if(map.containsKey(var)){
                        evaluator.putVariable(EXPR_PREFIX + var, map.get(var).containsKey(item)?map.get(var).get(item).getGuideValue().stripTrailingZeros().toPlainString():"0");//定义字符串变量
                        evalStr = evalStr.replace(EXPR_PREFIX + var, map.get(var).containsKey(item)?map.get(var).get(item).getGuideValue().stripTrailingZeros().toPlainString():"0");//定义字符串变量
                    }else{
                        evaluator.putVariable(EXPR_PREFIX + var, "0");
                        evalStr = evalStr.replace(EXPR_PREFIX + var, "0");
                    }
                }
                String evaluate = evaluator.evaluate(formulaCode);
                log.debug("表达式计算公式："+formulaCode + "----------\n" + evaluate+"="+evalStr);
                evaluator.clearFunctions();
                evaluator.clearVariables();
                data.setUnitCode(item);
                data.setGuideCode(pmsEvaluateIndex.getIndexCode());
                data.setGuideValue(StringUtils.isNotBlank(evaluate)?new BigDecimal(evaluate).setScale(4, RoundingMode.HALF_UP):BigDecimal.ZERO);
                data.setGuideType("1".equals(pmsEvaluateIndex.getOrgType())?"Y":("2".equals(pmsEvaluateIndex.getOrgType())?"K":"R"));
                resultList.add(data);
            }
        } catch (EvaluationException e) {
            log.error("表达式生成数据出错：", e);
        }
        resultMap.put(pmsEvaluateIndex.getIndexCode(), resultList);
        return resultList;
    }

    /**
     * 指标计算
     * @param actDate
     */
    @Override
    public List<PmsEvaluateData> calculateEvaluateIndex(String actDate){
        actDate = StringUtils.isNotBlank(actDate)?actDate:DateUtils.parseDateToStr(Constants.DATE_MONTH_FORMATTER, new Date());
        this.calculateEvaluateIndexOfBSC(actDate);
        this.calculateEvaluateIndexOfBonus(actDate);
        return this.calculateEvaluateIndexOfDoctor(actDate);
    }
    /**
     * 指标计算-科级
     * @param actDate
     */
    @Override
    @Transactional
    public void calculateEvaluateIndexOfBSC(String actDate){
        List<PmsEvaluateIndexDto> pmsEvaluateIndexDtoList = pmsEvaluateIndexMapper.selectEvaluateSetIndexDtoOfBSC(UserUtils.getSysUser().getDeptId(), actDate, Constants.COMMON_NO);
        List<PmsEvaluateData> dataList = calculateEvaluateResult(actDate, pmsEvaluateIndexDtoList, "2", Constants.COMMON_NO, Constants.EVA_CAL_BSC);
        if(CollectionUtils.isNotEmpty(dataList)){
            pmsEvaluateSetDeptDetailService.calculateBalanceScore(DateUtils.parseDate(actDate), dataList);
            pmsEvaluateDataMapper.insertBatch(dataList);
        }
    }
    /**
     * 指标计算-奖金
     * @param actDate
     */
    @Override
    @Transactional
    public void calculateEvaluateIndexOfBonus(String actDate){
        List<PmsEvaluateIndexDto> pmsEvaluateIndexDtoList = pmsEvaluateIndexMapper.selectEvaluateSetIndexDtoOfBonus(UserUtils.getSysUser().getDeptId(), actDate, Constants.COMMON_YES);
        sendWsMessage(PERCENTAGE_START+"");
        List<PmsEvaluateData> dataList = calculateEvaluateResult(actDate, pmsEvaluateIndexDtoList, "2", Constants.COMMON_YES, Constants.EVA_CAL_BONUS);
        if(CollectionUtils.isNotEmpty(dataList)){
            System.out.println(JSON.toJSONString(dataList));
            pmsEvaluateDataMapper.insertBatch(dataList);
        }
        sendWsMessage("100");
        WebSocketServer.close(String.valueOf(UserUtils.getSysUser().getUserId()));
    }
    /**
     * 指标计算-二次核算（人员分配）
     * @param actDate
     */
    @Override
    @Transactional
    public List<PmsEvaluateData> calculateEvaluateIndexOfDoctor(String actDate){
        /* 获取医生对应考核指标 */
        List<PmsEvaluateIndexDto> pmsEvaluateIndexDtoList = pmsSecondDistributionDetailService.selectDetailListForDoctor(UserUtils.getSysUser().getDeptId(), actDate);
        List<PmsEvaluateData> dataList = calculateEvaluateResult(actDate, pmsEvaluateIndexDtoList, "3", Constants.COMMON_YES, Constants.EVA_CAL_SECOND);
        if(CollectionUtils.isNotEmpty(dataList)){
            pmsEvaluateDataMapper.insertBatch(dataList);
        }
        return dataList;
    }

    private List<PmsEvaluateData> calculateEvaluateResult(String actDate, List<PmsEvaluateIndexDto> pmsEvaluateIndexDtoList, String orgType, String isBonus, String dataType){
        Long compCode = UserUtils.getSysUser().getDeptId();
        /* 删除旧数据 */
        PmsEvaluateData param = new PmsEvaluateData();
        param.setActDate(DateUtils.parseDate(actDate));
        param.setDataType(dataType);
        param.setCompCode(compCode);
        pmsEvaluateDataMapper.deleteBatch(param);

        List<PmsEvaluateData> dataList = new ArrayList<>();
        if(CollectionUtils.isEmpty(pmsEvaluateIndexDtoList)){
            return dataList;
        }
        Set<String> indexCodeSet = pmsEvaluateIndexDtoList.stream().filter(l->StringUtils.isNotBlank(l.getIndexCode())).map(l->l.getIndexCode()).collect(Collectors.toSet());
        if(CollectionUtils.isEmpty(indexCodeSet)){
            return dataList;
        }
        /* 获取指标详细信息 */
        QueryWrapper<PmsEvaluateIndex> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(PmsEvaluateIndex::getIndexCode, PmsEvaluateIndex::getIsExpress, PmsEvaluateIndex::getIndexSql, PmsEvaluateIndex::getFormulaCode, PmsEvaluateIndex::getDbCode, PmsEvaluateIndex::getOrgType)
                .eq(PmsEvaluateIndex::getDelFlag, PmsEvaluateIndex.DEL_FLAG_NORMAL)
                .eq(PmsEvaluateIndex::getCompCode, compCode)
                .eq(PmsEvaluateIndex::getOrgType, orgType)//机构类型
                .eq(PmsEvaluateIndex::getIsBonus, isBonus);//是否奖金
        List<PmsEvaluateIndex> indexList = this.list(wrapper);
        if(CollectionUtils.isEmpty(indexList)){
            return dataList;
        }
        Map<String, List<PmsEvaluateData>> resultMap = new HashMap<>();
        Map<String, PmsEvaluateIndex> indexMap = indexList.stream().collect(Collectors.toMap(PmsEvaluateIndex::getIndexCode, Function.identity()));
        Map<String, List<PmsEvaluateIndex>> indexListMap = indexList.stream().collect(Collectors.groupingBy(PmsEvaluateIndex::getIsExpress));
        /* 先计算SQL类型指标，再计算表达式类型指标 */
        if(indexListMap.containsKey(Constants.COMMON_NO)){
            calculateEvaluateOfSql(indexListMap.get(Constants.COMMON_NO), dataType, indexList.size(), resultMap);
        }
        if(indexListMap.containsKey(Constants.COMMON_YES)){
            calculateEvaluateOfExpr(indexListMap.get(Constants.COMMON_YES),dataType, indexMap, resultMap);
        }

        /* 医生对应指标权重 */
        Map<String, PmsEvaluateIndexDto> deptIndexWeightMap;
        if(Constants.EVA_CAL_SECOND.equals(dataType)){
            deptIndexWeightMap = pmsEvaluateIndexDtoList.stream().collect(Collectors.toMap(
                    dto -> {
                        return dto.getDocCode() + "_" + dto.getIndexCode();  //(Id1+Id2)作为key
                    }, Function.identity(), (v1,v2)->v1));
        }else{
            deptIndexWeightMap = pmsEvaluateIndexDtoList.stream().collect(Collectors.toMap(
                    dto -> {
                        return dto.getDeptCode() + "_" + dto.getIndexCode();  //(Id1+Id2)作为key
                    }, Function.identity(), (v1,v2)->v1));
        }
        /* 权重计算 */
        resultMap.forEach((k,v)->{
            if(CollectionUtils.isNotEmpty(v) && indexCodeSet.contains(k)){
                v.forEach(d->{
                    if(deptIndexWeightMap.containsKey(d.getUnitCode()+"_"+d.getGuideCode())){
                        d.preInsert();
                        d.setActDate(DateUtils.parseDate(actDate));
                        d.setDeptCode(deptIndexWeightMap.get(d.getUnitCode()+"_"+d.getGuideCode()).getDeptCode());
                        d.setDataType(dataType);
                        d.setSetCode(deptIndexWeightMap.get(d.getUnitCode()+"_"+d.getGuideCode()).getSetCode());
                        d.setGuideValueResult(d.getGuideValue().multiply(deptIndexWeightMap.get(d.getUnitCode()+"_"+d.getGuideCode()).getWeightRatio()).setScale(4, RoundingMode.HALF_UP));
                        d.setCompCode(UserUtils.getSysUser().getDeptId());
                        dataList.add(d);
                    }else{

                    }
                });
            }
        });
        return dataList;
    }

    /** 表达式指标计算 */
    private void calculateEvaluateOfExpr(List<PmsEvaluateIndex> evaluateIndexList, String dataType, Map<String, PmsEvaluateIndex> indexMap, Map<String, List<PmsEvaluateData>> resultMap){
        for(PmsEvaluateIndex index : evaluateIndexList){
            try {
                this.generateDataFromExpr(index, indexMap, resultMap);
                if(Constants.EVA_CAL_BONUS.equals(dataType)){
                    calculatePercentage(resultMap.size(), indexMap.size());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /** sql指标计算 */
    private void calculateEvaluateOfSql(List<PmsEvaluateIndex> evaluateIndexList, String dataType, int dataSize, Map<String, List<PmsEvaluateData>> resultMap){
        for(PmsEvaluateIndex index : evaluateIndexList){
            try {
                if(StringUtils.isNotBlank(index.getDbCode())){
                    resultMap.put(index.getIndexCode(), this.generateDataFromSql(index));
                    if(Constants.EVA_CAL_BONUS.equals(dataType)){
                        calculatePercentage(resultMap.size(), dataSize);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

    /**
     * 解析获取表达式中变量
     * @param expr
     * @return
     */
    private Set analysisExpr(String expr){
        Pattern compile = Pattern.compile(EXPR_PATTERN);
        Matcher matcher = compile.matcher(expr);
        Set<String> variables = new HashSet<>();
        while(matcher.find()){
            variables.add(matcher.group(1));
        }
        return variables;
    }
}
