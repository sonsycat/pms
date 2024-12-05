package com.zjry.pms.bonus.mapper;

import com.zjry.pms.bonus.domain.PmsStatementAchievementType;
import com.zjry.pms.bonus.domain.PmsStatementEquivalent;
import com.zjry.pms.bonus.domain.PmsStatementEquivalentType;
import com.zjry.pms.bonus.domain.PmsStatementLabourType;
import com.zjry.pms.bonus.domain.equivalent.*;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * 奖金核算Mapper接口
 * 
 * @author zjry
 * @date 2022-10-19
 */
public interface PmsBonusStatementMapper {


    List<PmsStatementEquivalentType> selectEquivalentTypelist();

    List<PmsStatementAchievementType> selectAchievementTypeList();

    List<PmsStatementLabourType> selectlabourTypelistList();

    List<PmsStatementEquivalent> selectEquivalentList(PmsStatementEquivalent pmsStatementEquivalent);

    Integer getEquivalentListSum(PmsStatementEquivalent pmsStatementEquivalent);

    ArrayList<PmsStatementEquivalentOne> subList(@Param("pmsStatementEquivalent") PmsStatementEquivalent pmsStatementEquivalent, @Param("startLen")int startLen);

    ArrayList<PmsStatementEquivalentTwo> subListTwo(@Param("pmsStatementEquivalent")PmsStatementEquivalent pmsStatementEquivalent, @Param("startLen")int startLen);

    ArrayList<PmsStatementEquivalentThree> subListThree(@Param("pmsStatementEquivalent")PmsStatementEquivalent pmsStatementEquivalent, @Param("startLen")int startLen);

    ArrayList<PmsStatementEquivalentFour> subListFour(@Param("pmsStatementEquivalent")PmsStatementEquivalent pmsStatementEquivalent, @Param("startLen")int startLen);

    ArrayList<PmsStatementEquivalentFive> subListFive(@Param("pmsStatementEquivalent")PmsStatementEquivalent pmsStatementEquivalent, @Param("startLen")int startLen);

    ArrayList<PmsStatementEquivalentSix> subListSix(@Param("pmsStatementEquivalent")PmsStatementEquivalent pmsStatementEquivalent, @Param("startLen")int startLen);

    ArrayList<PmsStatementEquivalentSeven> subListSeven(@Param("pmsStatementEquivalent")PmsStatementEquivalent pmsStatementEquivalent, @Param("startLen")int startLen);
}
