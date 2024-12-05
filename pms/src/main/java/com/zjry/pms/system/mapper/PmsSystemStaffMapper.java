package com.zjry.pms.system.mapper;

import com.zjry.pms.system.domain.PmsSystemStaff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 科基金Mapper接口
 * 
 * @author maozt
 * @date 2022-08-17
 */
public interface PmsSystemStaffMapper  {


    List<PmsSystemStaff> selectPmsSystemStaffList(PmsSystemStaff pmsSystemStaff);

    PmsSystemStaff selectPmsSystemStaffById(Long id);

    int updatePmsSystemStaff(PmsSystemStaff pmsSystemStaff);

    int insertPmsSystemStaff(PmsSystemStaff param);

    int deletePmsSystemStaffByIds(Long[] ids);

    int insertPmsSystemStaffLists(@Param("pmsSystemStaffList")List<PmsSystemStaff> pmsSystemStaffsFiles);
}
