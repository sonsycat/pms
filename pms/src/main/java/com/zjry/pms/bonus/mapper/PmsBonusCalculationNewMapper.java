package com.zjry.pms.bonus.mapper;

import com.zjry.pms.bonus.domain.PmsAccountDeptNew;
import com.zjry.pms.bonus.domain.PmsBonusCalculationHis;
import com.zjry.pms.evaluate.domain.PmsEvaluateData;
import com.zjry.pms.evaluate.domain.PmsEvaluateDataAccountDept;
import com.zjry.pms.evaluate.domain.PmsEvaluateIndex;
import com.zjry.pms.evaluate.domain.PmsEvaluateIndexBonus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 奖金核算Mapper接口
 * 
 * @author zjry
 * @date 2022-10-19
 */
public interface PmsBonusCalculationNewMapper  {

    List<PmsAccountDeptNew> selectAccountDeptByDate(@Param("actDate") String actDate, @Param("compCode") Long compCode);

    List<PmsEvaluateIndexBonus> selectEvaluateBonusByRoomType(@Param("accountTypes") List<String> accountTypes,@Param("compCode") Long compCode);

    List<PmsEvaluateIndex> selectEvaluateIndexById(@Param("indexCodes") List<String> indexCodes);

    List<PmsEvaluateData> selectBonusList(PmsEvaluateData pmsEvaluateData);

    List<PmsEvaluateDataAccountDept> selectAccountDeptEvaluateDataList(PmsEvaluateData pmsEvaluateData);

    List<PmsEvaluateDataAccountDept> selectAccountDeptIndex(PmsEvaluateData pmsEvaluateData);

    void deleteBatch(PmsEvaluateData param);

    void insertHis(PmsBonusCalculationHis pmsBonusCalculationHis);


}
