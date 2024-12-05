package com.zjry.pms.evaluate.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.common.utils.StringUtils;
import com.zjry.formDesign.util.JacksonUtil;
import com.zjry.pms.common.Constants;
import com.zjry.pms.common.utils.DateUtils;
import com.zjry.pms.common.utils.SpringContextHolder;
import com.zjry.pms.common.utils.UserUtils;
import com.zjry.pms.evaluate.domain.*;
import com.zjry.pms.evaluate.mapper.PmsBalancedScorecardMapper;
import com.zjry.pms.evaluate.mapper.PmsEvaluateSetDeptMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.evaluate.mapper.PmsEvaluateSetDeptDetailMapper;
import com.zjry.pms.evaluate.service.IPmsEvaluateSetDeptDetailService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 指标集与科室管理明细Service业务层处理
 *
 * @author mk
 * @date 2022-08-09
 */
@Service
public class PmsEvaluateSetDeptDetailServiceImpl extends ServiceImpl<PmsEvaluateSetDeptDetailMapper, PmsEvaluateSetDeptDetail> implements IPmsEvaluateSetDeptDetailService {

    @Resource
    private PmsEvaluateSetDeptDetailMapper pmsEvaluateSetDeptDetailMapper;

    @Resource
    private PmsEvaluateSetDeptMapper pmsEvaluateSetDeptMapper;

    @Autowired
    private PmsBalancedScorecardMapper balancedScorecardMapper;

    /**
     * 查询指标集与科室管理明细
     *
     * @param id 指标集与科室管理明细ID
     * @return 指标集与科室管理明细
     */
    @Override
    public PmsEvaluateSetDeptDetail selectPmsEvaluateSetDeptDetailById(Long id) {
        return pmsEvaluateSetDeptDetailMapper.selectPmsEvaluateSetDeptDetailById(id);
    }

    /**
     * 查询指标集与科室管理明细列表
     *
     * @param pmsEvaluateSetDeptDetail 指标集与科室管理明细
     * @return 指标集与科室管理明细
     */
    @Override
    public List<PmsEvaluateSetDeptDetail> selectPmsEvaluateSetDeptDetailList(PmsEvaluateSetDeptDetail pmsEvaluateSetDeptDetail) {
        return pmsEvaluateSetDeptDetailMapper.selectPmsEvaluateSetDeptDetailList(pmsEvaluateSetDeptDetail);
    }

    /**
     * 新增指标集与科室管理明细
     *
     * @param pmsEvaSetDeptDetailList 指标集与科室管理明细
     * @return 结果
     */
    @Override
    public void insertPmsEvaluateSetDeptDetail(PmsEvaSetDeptDetailList pmsEvaSetDeptDetailList) {
        pmsEvaluateSetDeptDetailMapper.deleteByDataAndCode(pmsEvaSetDeptDetailList.getCompCode(), pmsEvaSetDeptDetailList.getNowDate(), pmsEvaSetDeptDetailList.getSetDeptCode());
        List<PmsEvaluateSetDeptDetail> list = pmsEvaSetDeptDetailList.getListPms();
        for (PmsEvaluateSetDeptDetail item : list) {
            item.preInsert();
            item.setCompCode(pmsEvaSetDeptDetailList.getCompCode());
            item.setSetDeptCode(pmsEvaSetDeptDetailList.getSetDeptCode());
            item.setNowDate(pmsEvaSetDeptDetailList.getNowDate());
            pmsEvaluateSetDeptDetailMapper.insertPmsEvaluateSetDeptDetail(item);
        }

    }

    /**
     * 修改指标集与科室管理明细
     *
     * @param pmsEvaluateSetDeptDetail 指标集与科室管理明细
     * @return 结果
     */
    @Override
    public int updatePmsEvaluateSetDeptDetail(PmsEvaluateSetDeptDetail pmsEvaluateSetDeptDetail) {
        pmsEvaluateSetDeptDetail.preUpdate();
        return pmsEvaluateSetDeptDetailMapper.updatePmsEvaluateSetDeptDetail(pmsEvaluateSetDeptDetail);
    }

    /**
     * 批量删除指标集与科室管理明细
     *
     * @param ids 需要删除的指标集与科室管理明细ID
     * @return 结果
     */
    @Override
    public int deletePmsEvaluateSetDeptDetailByIds(Long[] ids) {
        return pmsEvaluateSetDeptDetailMapper.deletePmsEvaluateSetDeptDetailByIds(ids);
    }

    /**
     * 删除指标集与科室管理明细信息
     *
     * @param id 指标集与科室管理明细ID
     * @return 结果
     */
    @Override
    public int deletePmsEvaluateSetDeptDetailById(Long id) {
        return pmsEvaluateSetDeptDetailMapper.deletePmsEvaluateSetDeptDetailById(id);
    }

    @Override
    public List<PmsEvaSetDeptDN> selectPmsForDKSN(PmsEvaluateSetDeptDetail pmsEvaluateSetDeptDetail) {
        List<PmsEvaluateSetDeptDetail> list = pmsEvaluateSetDeptDetailMapper.selectPmsForDKSN(pmsEvaluateSetDeptDetail);
        HashMap<String, PmsEvaSetDeptDN> hs = new HashMap<String, PmsEvaSetDeptDN>();
        List<PmsEvaSetDeptDN> pdnL = new ArrayList<PmsEvaSetDeptDN>();
        for (PmsEvaluateSetDeptDetail item : list) {
            if (null == hs.get(item.getCompCode() + item.getDeptCode() + item.getSetCode())) {
                PmsEvaSetDeptDN pedn = new PmsEvaSetDeptDN();
                pedn.setAccountCode(item.getAccountCode());
                pedn.setCompCode(item.getCompCode());
                pedn.setIndexCode(item.getIndexCode());
                pedn.setReduce(item.getReduce());
                pedn.setIndexScore(item.getIndexScore());
                pedn.setDeduct(item.getDeduct());
                pedn.setIncrease(item.getIncrease());
                pedn.setExtra(item.getExtra());
                pedn.setUnit(item.getUnit());
                pedn.setIncrease(item.getIncrease());
                pedn.setIsHyper(item.getIsHyper());
                pedn.setIsNegative(item.getIsNegative());
                pedn.setIsFixed(item.getIsFixed());
                pedn.setThreshold(item.getThreshold());
                pedn.setSetCode(item.getSetCode());
                pedn.setOverLimit(item.getOverLimit());
                pedn.setDeductionLimit(item.getDeductionLimit());
                pedn.setDeptCode(item.getDeptCode());
                pedn.setDeptName(item.getDeptName());
                if ("01".equals(DateUtils.getMonth(item.getNowDate()))) {
                    pedn.setOneDate(item.getNowDate());
                    pedn.setOneID(item.getId());
                    pedn.setOneTarget(item.getTarget());
                } else if ("02".equals(DateUtils.getMonth(item.getNowDate()))) {
                    pedn.setTwoDate(item.getNowDate());
                    pedn.setTwoID(item.getId());
                    pedn.setTwoTarget(item.getTarget());
                } else if ("03".equals(DateUtils.getMonth(item.getNowDate()))) {
                    pedn.setThreeDate(item.getNowDate());
                    pedn.setThreeID(item.getId());
                    pedn.setThreeTarget(item.getTarget());
                } else if ("04".equals(DateUtils.getMonth(item.getNowDate()))) {
                    pedn.setFourDate(item.getNowDate());
                    pedn.setFourID(item.getId());
                    pedn.setFourTarget(item.getTarget());
                } else if ("05".equals(DateUtils.getMonth(item.getNowDate()))) {
                    pedn.setFiveDate(item.getNowDate());
                    pedn.setFiveID(item.getId());
                    pedn.setFiveTarget(item.getTarget());
                } else if ("06".equals(DateUtils.getMonth(item.getNowDate()))) {
                    pedn.setSixDate(item.getNowDate());
                    pedn.setSixID(item.getId());
                    pedn.setSixTarget(item.getTarget());
                } else if ("07".equals(DateUtils.getMonth(item.getNowDate()))) {
                    pedn.setSevenDate(item.getNowDate());
                    pedn.setSevenID(item.getId());
                    pedn.setSevenTarget(item.getTarget());
                } else if ("08".equals(DateUtils.getMonth(item.getNowDate()))) {
                    pedn.setEightDate(item.getNowDate());
                    pedn.setEightID(item.getId());
                    pedn.setEightTarget(item.getTarget());
                } else if ("09".equals(DateUtils.getMonth(item.getNowDate()))) {
                    pedn.setNineDate(item.getNowDate());
                    pedn.setNineID(item.getId());
                    pedn.setNineTarget(item.getTarget());
                } else if ("10".equals(DateUtils.getMonth(item.getNowDate()))) {
                    pedn.setTenDate(item.getNowDate());
                    pedn.setTenID(item.getId());
                    pedn.setTenTarget(item.getTarget());
                } else if ("11".equals(DateUtils.getMonth(item.getNowDate()))) {
                    pedn.setElevenDate(item.getNowDate());
                    pedn.setElevenID(item.getId());
                    pedn.setElevenTarget(item.getTarget());
                } else if ("12".equals(DateUtils.getMonth(item.getNowDate()))) {
                    pedn.setTwelveDate(item.getNowDate());
                    pedn.setTwelveID(item.getId());
                    pedn.setTwelveTarget(item.getTarget());
                }
                hs.put(item.getCompCode() + item.getDeptCode() + item.getSetCode(), pedn);
            } else {
                PmsEvaSetDeptDN pedn = hs.get(item.getCompCode() + item.getDeptCode() + item.getSetCode());
                if ("01".equals(DateUtils.getMonth(item.getNowDate()))) {
                    pedn.setOneDate(item.getNowDate());
                    pedn.setOneID(item.getId());
                    pedn.setOneTarget(item.getTarget());
                } else if ("02".equals(DateUtils.getMonth(item.getNowDate()))) {
                    pedn.setTwoDate(item.getNowDate());
                    pedn.setTwoID(item.getId());
                    pedn.setTwoTarget(item.getTarget());
                } else if ("03".equals(DateUtils.getMonth(item.getNowDate()))) {
                    pedn.setThreeDate(item.getNowDate());
                    pedn.setThreeID(item.getId());
                    pedn.setThreeTarget(item.getTarget());
                } else if ("04".equals(DateUtils.getMonth(item.getNowDate()))) {
                    pedn.setFourDate(item.getNowDate());
                    pedn.setFourID(item.getId());
                    pedn.setFourTarget(item.getTarget());
                } else if ("05".equals(DateUtils.getMonth(item.getNowDate()))) {
                    pedn.setFiveDate(item.getNowDate());
                    pedn.setFiveID(item.getId());
                    pedn.setFiveTarget(item.getTarget());
                } else if ("06".equals(DateUtils.getMonth(item.getNowDate()))) {
                    pedn.setSixDate(item.getNowDate());
                    pedn.setSixID(item.getId());
                    pedn.setSixTarget(item.getTarget());
                } else if ("07".equals(DateUtils.getMonth(item.getNowDate()))) {
                    pedn.setSevenDate(item.getNowDate());
                    pedn.setSevenID(item.getId());
                    pedn.setSevenTarget(item.getTarget());
                } else if ("08".equals(DateUtils.getMonth(item.getNowDate()))) {
                    pedn.setEightDate(item.getNowDate());
                    pedn.setEightID(item.getId());
                    pedn.setEightTarget(item.getTarget());
                } else if ("09".equals(DateUtils.getMonth(item.getNowDate()))) {
                    pedn.setNineDate(item.getNowDate());
                    pedn.setNineID(item.getId());
                    pedn.setNineTarget(item.getTarget());
                } else if ("10".equals(DateUtils.getMonth(item.getNowDate()))) {
                    pedn.setTenDate(item.getNowDate());
                    pedn.setTenID(item.getId());
                    pedn.setTenTarget(item.getTarget());
                } else if ("11".equals(DateUtils.getMonth(item.getNowDate()))) {
                    pedn.setElevenDate(item.getNowDate());
                    pedn.setElevenID(item.getId());
                    pedn.setElevenTarget(item.getTarget());
                } else if ("12".equals(DateUtils.getMonth(item.getNowDate()))) {
                    pedn.setTwelveDate(item.getNowDate());
                    pedn.setTwelveID(item.getId());
                    pedn.setTwelveTarget(item.getTarget());
                }
                hs.put(item.getCompCode() + item.getDeptCode() + item.getSetCode(), pedn);
            }
        }
        for (String key : hs.keySet()) {
            pdnL.add(hs.get(key));
        }

        return pdnL;
    }

    @Override
    public void calculateScorecardByMonth(Date date) {
        List<EvaluateSetDeptDetailDto> list = pmsEvaluateSetDeptDetailMapper.getSetDeptDetailByMonth(date);
        List<PmsBalancedScorecard> insertList = new ArrayList<>();
        list.forEach(item -> {
            PmsBalancedScorecard scorecard = new PmsBalancedScorecard();
            BigDecimal scoreValue = calculationIndexScore(item);
            scorecard.setDeptCode(item.getDeptRoomCode());
            scorecard.setNowDate(date);
            scorecard.setIndexCode(item.getIndexCode());
            scorecard.setScorecardValue(scoreValue);
            insertList.add(scorecard);
            scorecard.setDelFlag("0");
            scorecard.setCreateTime(new Date());
            scorecard.setUpdateTime(new Date());
        });
        balancedScorecardMapper.saveBatch(insertList);
    }

    /**
     * 平衡积分卡计算
     * @param evaluateDataList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void calculateBalanceScore(Date date, List<PmsEvaluateData> evaluateDataList){
        List<EvaluateSetDeptDetailDto> list = pmsEvaluateSetDeptDetailMapper.getSetDeptDetailList(date);
        List<PmsBalancedScorecard> scoreCardList = new ArrayList<>();
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        EvaluateSetDeptDetailDto evaluateSetDeptDetailDto;
        Map<String, EvaluateSetDeptDetailDto> evaluateSetDeptDetailDtoMap = list.stream().collect(Collectors.toMap(
                dto -> {
                    return dto.getDeptRoomCode() + "_" + dto.getIndexCode();  //(Id1+Id2)作为key
                }, Function.identity(),(v1,v2)->v1));
        for(PmsEvaluateData data : evaluateDataList){
            if(evaluateSetDeptDetailDtoMap.containsKey(data.getUnitCode()+"_"+data.getGuideCode())){
                evaluateSetDeptDetailDto = evaluateSetDeptDetailDtoMap.get(data.getUnitCode()+"_"+data.getGuideCode());
                evaluateSetDeptDetailDto.setRealValue(data.getGuideValueResult());
                BigDecimal calScore = calculationIndexScore(evaluateSetDeptDetailDto);
                data.setGuideValueResult(calScore==null?data.getGuideValueResult():calScore);

                PmsBalancedScorecard scorecard = new PmsBalancedScorecard();
                scorecard.setDeptCode(evaluateSetDeptDetailDto.getDeptRoomCode());
                scorecard.setNowDate(date);
                scorecard.setIndexCode(evaluateSetDeptDetailDto.getIndexCode());
                scorecard.setScorecardValue(data.getGuideValueResult());
                scorecard.preInsert();
                scorecard.setCompCode(UserUtils.getSysUser().getDeptId());
                scoreCardList.add(scorecard);
            }
        }
        /* 评分记分卡数据添加（先删除后插入） */
        balancedScorecardMapper.deleteByMonth(DateUtils.formatDate(date, Constants.DATE_MONTH_FORMATTER));
        if(CollectionUtils.isNotEmpty(scoreCardList)){
            balancedScorecardMapper.saveBatch(scoreCardList);
        }
    }

    /**
     * 计算指标分值
     *
     * @param param
     * @return
     */
    @Override
    public BigDecimal calculationIndexScore(EvaluateSetDeptDetailDto param) {
        //实际值
        BigDecimal realValue = param.getRealValue();
        //目标值
        BigDecimal target = param.getTarget();
        //阈值
        BigDecimal threshold = param.getThreshold();
        //超分限制
        BigDecimal overLimit = param.getOverLimit();
        //减分限制
        BigDecimal deductionLimit = param.getDeductionLimit();
        //指标分值
        BigDecimal indexScore = param.getIndexScore();
        //单位,0:百分比,1:分值
        String unit = param.getUnit();
        //计算结果
        BigDecimal result = new BigDecimal("0");
        //如果为固定分值,直接为指标分值
        if (param.getIsFixed()) {
            return indexScore;
        }
        //计算实际值与目标值的偏差
        BigDecimal diff = realValue.subtract(target);
        int compare = diff.compareTo(BigDecimal.ZERO);
        if (compare == 0) {
            //实际值跟目标值一样
            result = indexScore;
        } else if (compare > 0) {
            //实际值大于目标值
            result = addRealValue(param, realValue, target, threshold, overLimit, indexScore, unit, result, diff);
        } else {
            //实际值小于目标值
            result = reduceRealValue(param, realValue, target, threshold, deductionLimit, indexScore, unit, result, diff);
        }
        //是否允许超分
        if (!param.getIsHyper() && result.compareTo(target) > 0) {
            result = target;
        }
        //是否允许为负
        if (!param.getIsNegative() && result.compareTo(BigDecimal.ZERO) < 0) {
            result = new BigDecimal("0.00");
        }

        return result.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public List<PmsEvaluateBscSort> getBscScoreById(Long id) {
        PmsEvaluateSetDept pmsEvaluateSetDept = pmsEvaluateSetDeptMapper.selectPmsEvaluateSetDeptById(id);
        String bsc = pmsEvaluateSetDept.getInternalScore().toString();
        List<PmsEvaluateBscSort> pmsEvaluateBscSortList = JacksonUtil.toList(bsc, PmsEvaluateBscSort.class);
        return pmsEvaluateBscSortList;
    }

    private static BigDecimal reduceRealValue(EvaluateSetDeptDetailDto param,
                                              BigDecimal realValue,
                                              BigDecimal target,
                                              BigDecimal threshold,
                                              BigDecimal deductionLimit,
                                              BigDecimal indexScore,
                                              String unit,
                                              BigDecimal result,
                                              BigDecimal diff) {
        BigDecimal commonRate = param.getDeduct().divide(param.getReduce(), 4, RoundingMode.HALF_UP);
        if (StringUtils.equals("0", unit)) {
            commonRate = param.getDeduct().divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP).divide(param.getReduce(), 4, RoundingMode.HALF_UP);
        } else if (StringUtils.equals("1", unit)) {
            commonRate = param.getDeduct().divide(param.getReduce(), 4, RoundingMode.HALF_UP);
        }

        if (threshold != null && deductionLimit != null && realValue.compareTo(threshold.multiply(target).negate()) < 0) {
            //如果有阈值并且达到阈值范围
            BigDecimal common = target.subtract(threshold.multiply(target));
            BigDecimal extRate = deductionLimit.divide(param.getReduce(), 4, RoundingMode.HALF_UP);
            if (StringUtils.equals("0", unit)) {
                extRate = deductionLimit.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP).divide(param.getReduce(), 4, RoundingMode.HALF_UP);
            } else if (StringUtils.equals("1", unit)) {
                extRate = deductionLimit.divide(param.getReduce(), 4, RoundingMode.HALF_UP);
            }

            BigDecimal ext = realValue.subtract(threshold.multiply(target).negate());
            if (StringUtils.equals("0", unit)) {
                //百分比
                //正常分值
                result = indexScore.subtract(common.multiply(commonRate).multiply(indexScore));
                //超分分值
                result = result.subtract(ext.multiply(extRate).multiply(indexScore));
            } else if (StringUtils.equals("1", unit)) {
                //分值
                //正常分值
                result = indexScore.subtract(common.multiply(commonRate));
                //超分分值
                result = result.subtract(ext.multiply(extRate));
            }
        } else {
            //没有阈值或者没有达到阈值范围
            if (StringUtils.equals("0", unit)) {
                //百分比
                result = indexScore.subtract(diff.multiply(commonRate).multiply(indexScore));
            } else if (StringUtils.equals("1", unit)) {
                //分值
                result = indexScore.subtract(diff.multiply(commonRate));
            }
        }
        return result;
    }

    private static BigDecimal addRealValue(EvaluateSetDeptDetailDto param,
                                           BigDecimal realValue,
                                           BigDecimal target,
                                           BigDecimal threshold,
                                           BigDecimal overLimit,
                                           BigDecimal indexScore,
                                           String unit,
                                           BigDecimal result,
                                           BigDecimal diff) {
        BigDecimal commonRate = new BigDecimal("0");
        if (StringUtils.equals("0", unit)) {
            commonRate = param.getIncrease().compareTo(BigDecimal.ZERO)==0?BigDecimal.ZERO:param.getExtra().divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP).divide(param.getIncrease(), 4, RoundingMode.HALF_UP);
        } else if (StringUtils.equals("1", unit)) {
            commonRate = param.getIncrease().compareTo(BigDecimal.ZERO)==0?BigDecimal.ZERO:param.getExtra().divide(param.getIncrease(), 4, RoundingMode.HALF_UP);
        }
        if (threshold != null && overLimit != null && realValue.compareTo(threshold.multiply(target)) > 0) {
            //如果有阈值并且达到阈值范围
            BigDecimal common = threshold.multiply(target).subtract(target);
            BigDecimal extRate = new BigDecimal("0");
            if (StringUtils.equals("0", unit)) {
                extRate = param.getIncrease().compareTo(BigDecimal.ZERO)==0?BigDecimal.ZERO:overLimit.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP).divide(param.getIncrease(), 4, RoundingMode.HALF_UP);
            } else if (StringUtils.equals("1", unit)) {
                extRate = param.getIncrease().compareTo(BigDecimal.ZERO)==0?BigDecimal.ZERO:overLimit.divide(param.getIncrease(), 4, RoundingMode.HALF_UP);
            }
            BigDecimal ext = realValue.subtract(threshold.multiply(target));
            if (StringUtils.equals("0", unit)) {
                //百分比
                //正常分值
                result = common.multiply(commonRate).multiply(indexScore).add(indexScore);
                //超分分值
                result = ext.multiply(extRate).multiply(indexScore).add(result);
            } else if (StringUtils.equals("1", unit)) {
                //分值
                //正常分值
                result = common.multiply(commonRate).add(indexScore);
                //超分分值
                result = ext.multiply(extRate).add(result);
            }
        } else {
            //没有阈值或者没有达到阈值范围
            if (StringUtils.equals("0", unit)) {
                //百分比
                result = diff.multiply(commonRate.multiply(indexScore)).add(indexScore);
            } else if (StringUtils.equals("1", unit)) {
                result = diff.multiply(commonRate).add(indexScore);
            }
        }
        return result;
    }

//    public static void main(String[] args) {
//        EvaluateSetDeptDetailDto dto = new EvaluateSetDeptDetailDto();
//        //降分量
//        dto.setReduce(new BigDecimal(10));
//        //扣分
//        dto.setDeduct(new BigDecimal(-5));
//        //增加量
//        dto.setIncrease(new BigDecimal(20));
//        //加分
//        dto.setExtra(new BigDecimal(8));
//        //单位
//        dto.setUnit("0");
//        //目标
//        dto.setTarget(new BigDecimal(50));
//        //指标分数
//        dto.setIndexScore(new BigDecimal(40));
//        //超分
//        dto.setIsHyper(true);
//        //可负
//        dto.setIsNegative(true);
//        //固定
//        dto.setIsFixed(false);
//        //阈值
//        dto.setThreshold(new BigDecimal("2"));
//        dto.setOverLimit(new BigDecimal(2));
//        dto.setDeductionLimit(new BigDecimal(-2));
//        //实际值
//        dto.setRealValue(new BigDecimal(-200));
//        System.out.println(calculationIndexScore(dto));
//
//    }

    @Override
    public List<PmsEvaluateSetDeptDetail> selectPmsForDKSY(PmsEvaluateSetDeptDetail pmsEvaluateSetDeptDetail) {
        List<PmsEvaluateSetDeptDetail> list = new ArrayList<PmsEvaluateSetDeptDetail>();
        PmsEvaluateSetDept pmsDept = new PmsEvaluateSetDept();
        pmsDept.setNowDate(pmsEvaluateSetDeptDetail.getNowDate());
        pmsDept.setIndexCode(pmsEvaluateSetDeptDetail.getIndexCode());
        pmsDept.setCompCode(pmsEvaluateSetDeptDetail.getCompCode());
        List<PmsEvaluateSetDept> list1 = pmsEvaluateSetDeptMapper.selectPmsEvaluateSetDeptList(pmsDept);
        for (PmsEvaluateSetDept item : list1) {
            PmsEvaluateSetDeptDetail pmsSetDeptD = new PmsEvaluateSetDeptDetail();
            pmsSetDeptD.setSetDeptCode(item.getSetDeptCode());
            pmsSetDeptD.setNowDate(item.getNowDate());
            pmsSetDeptD.setIndexCode(item.getIndexCode());
            pmsSetDeptD.setCompCode(item.getCompCode());
            List<PmsEvaluateSetDeptDetail> list2 = pmsEvaluateSetDeptDetailMapper.selectPmsEvaluateSetDeptDetailList(pmsSetDeptD);
            if (null != list2 && list2.size() > 0) {
                list2.get(0).setDeptCode(Long.parseLong(item.getDeptCode()));
                list2.get(0).setDeptName(item.getDeptName());
                list.add(list2.get(0));
            } else {
                PmsEvaluateSetDeptDetail pms = new PmsEvaluateSetDeptDetail();
                pms.setSetDeptCode(item.getSetDeptCode());
                pms.setNowDate(item.getNowDate());
                pms.setIndexCode(item.getIndexCode());
                pms.setCompCode(item.getCompCode());
                pms.setDeptCode(Long.parseLong(item.getDeptCode()));
                pms.setDeptName(item.getDeptName());
                list.add(pms);
            }
        }
        return list;
    }

    @Override
    public void savePmsForDKSY(SetDeptDyList setDeptDyList) {
        List<PmsEvaluateSetDeptDetail> list = setDeptDyList.getPmsList();
        for (PmsEvaluateSetDeptDetail item : list) {
            if (null != item.getId()) {
                item.preUpdate();
                pmsEvaluateSetDeptDetailMapper.updatePmsEvaluateSetDeptDetail(item);
            } else {
                item.preInsert();
                item.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
                pmsEvaluateSetDeptDetailMapper.insertPmsEvaluateSetDeptDetail(item);
            }
        }
    }
}
