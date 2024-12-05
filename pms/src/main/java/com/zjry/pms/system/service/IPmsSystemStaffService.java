package com.zjry.pms.system.service;

import com.zjry.common.core.domain.AjaxResult;
import com.zjry.pms.system.domain.PmsSystemStaff;

import java.util.List;
import java.util.Map;

/**
 * 指标配置维护Service接口
 * 
 * @author zjry
 * @date 2022-10-09
 */
public interface IPmsSystemStaffService {

    List<PmsSystemStaff> selectPmsSystemStaffList(PmsSystemStaff pmsSystemStaff);

    PmsSystemStaff selectPmsSystemStaffById(Long id);

    int updatePmsSystemStaff(PmsSystemStaff pmsSystemStaff);

    int insertPmsSystemStaff(PmsSystemStaff param);

    int deletePmsSystemStaffByIds(Long[] ids);

    int importData(List<PmsSystemStaff> pmsSystemStaffsFiles);

}
