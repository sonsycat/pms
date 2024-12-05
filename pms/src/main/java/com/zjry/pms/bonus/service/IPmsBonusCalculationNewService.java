package com.zjry.pms.bonus.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.pms.bonus.domain.PmsBonusCalculation;
import com.zjry.pms.evaluate.domain.PmsEvaluateData;
import com.zjry.pms.evaluate.domain.PmsEvaluateDataAccountDept;

import java.util.List;

/**
 * 奖金核算Service接口
 *
 * @author zjry
 * @date 2022-10-19
 */
public interface IPmsBonusCalculationNewService extends IService<PmsBonusCalculation> {

    /**
     * 新增奖金核算
     *
     * @param pmsBonusCalculation 奖金核算
     * @return 结果
     */
    public int insertPmsBonusCalculation(PmsBonusCalculation pmsBonusCalculation);


    List<PmsEvaluateData> selectBonusList(PmsEvaluateData param);

    List<JSONObject> selectAccountDeptEvaluateDataList(PmsEvaluateData pmsEvaluateData);

    List<PmsEvaluateDataAccountDept> selectAccountDeptIndex(PmsEvaluateData pmsEvaluateData);

    AjaxResult bonusCommit(PmsBonusCalculation pmsBonusCalculation);

    AjaxResult bonusAudit(PmsBonusCalculation pmsBonusCalculation);

    AjaxResult bonusArchive(Long id);
}
