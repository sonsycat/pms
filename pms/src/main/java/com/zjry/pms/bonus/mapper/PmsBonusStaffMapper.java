package com.zjry.pms.bonus.mapper;

import com.zjry.pms.bonus.domain.PmsBonusStaff;
import com.zjry.pms.evaluate.domain.PmsEvaluateData;
import com.zjry.pms.system.domain.PmsSystemStaff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 核算科室设置Mapper接口
 * 
 * @author zjry
 * @date 2022-09-29
 */
public interface PmsBonusStaffMapper{

    List<PmsBonusStaff> selectListBonusStaff(PmsEvaluateData pmsEvaluateData);

    String selectBonusTotalAmount(@Param("deptCode") String deptCode, @Param("actDate")String actDate);

    void insertData(List<PmsBonusStaff> dataList);

    void updateData(List<PmsBonusStaff> dataList);

    void deleteBonusStaff(@Param("deptCode") String deptCode, @Param("actDate")String actDate);

    List<PmsSystemStaff> selectSystemStaffByDeptCode(@Param("deptCode") String deptCode);

    String selectBonusAssignedAmount(@Param("deptCode") String deptCode, @Param("actDate")String actDate);
}
