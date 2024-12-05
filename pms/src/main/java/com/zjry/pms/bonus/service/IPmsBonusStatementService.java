package com.zjry.pms.bonus.service;

import com.zjry.pms.bonus.domain.PmsStatementAchievementType;
import com.zjry.pms.bonus.domain.PmsStatementEquivalent;
import com.zjry.pms.bonus.domain.PmsStatementEquivalentType;
import com.zjry.pms.bonus.domain.PmsStatementLabourType;

import java.util.List;

public interface IPmsBonusStatementService {
    List<PmsStatementEquivalentType> selectEquivalentTypelist();

    List<PmsStatementAchievementType> selectAchievementTypeList();

    List<PmsStatementLabourType> selectlabourTypelistList();

    List<PmsStatementEquivalent> selectEquivalentList(PmsStatementEquivalent pmsStatementEquivalent);

    <T> List<T> selectEquivalentListByThread(PmsStatementEquivalent pmsStatementEquivalent);
}
