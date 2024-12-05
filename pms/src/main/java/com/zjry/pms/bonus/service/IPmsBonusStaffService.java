package com.zjry.pms.bonus.service;

import com.zjry.pms.bonus.domain.PmsBonusStaff;
import com.zjry.pms.evaluate.domain.PmsEvaluateData;

import java.util.List;
import java.util.Map;

/**
 * 指标数据值（外部获取）Service接口
 * 
 * @author zjry
 * @date 2022-10-17
 */
public interface IPmsBonusStaffService {

    List<PmsBonusStaff> selectListBonusStaff(PmsEvaluateData pmsEvaluateData);

    Map<String, String> saveBonusStaff(List<PmsBonusStaff> pmsBonusStaffList);

    Map importData(List<PmsBonusStaff> pmsBonusStaffList, String deptCode,String actDate);
}