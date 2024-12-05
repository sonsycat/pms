package com.zjry.pms.system.service.inpl;

import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.utils.DateUtils;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.pms.bonus.domain.PmsBonusEnter;
import com.zjry.pms.system.domain.PmsSystemStaff;
import com.zjry.pms.system.mapper.PmsSystemStaffMapper;
import com.zjry.pms.system.service.IPmsSystemStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PmsSystemStaffService implements IPmsSystemStaffService {

    @Autowired
    private PmsSystemStaffMapper pmsSystemStaffMapper;

    @Override
    public List<PmsSystemStaff> selectPmsSystemStaffList(PmsSystemStaff pmsSystemStaff) {
        return pmsSystemStaffMapper.selectPmsSystemStaffList(pmsSystemStaff);
    }

    @Override
    public PmsSystemStaff selectPmsSystemStaffById(Long id) {
        return pmsSystemStaffMapper.selectPmsSystemStaffById(id);
    }

    @Override
    @Transactional
    public int updatePmsSystemStaff(PmsSystemStaff pmsSystemStaff) {
        pmsSystemStaff.preUpdate();
        return pmsSystemStaffMapper.updatePmsSystemStaff(pmsSystemStaff);
    }

    @Override
    @Transactional
    public int insertPmsSystemStaff(PmsSystemStaff param) {
        param.preInsert();
        return pmsSystemStaffMapper.insertPmsSystemStaff(param);
    }

    @Override
    @Transactional
    public int deletePmsSystemStaffByIds(Long[] ids) {
        return pmsSystemStaffMapper.deletePmsSystemStaffByIds(ids);
    }

    @Override
    public int importData(List<PmsSystemStaff> pmsSystemStaffsFiles) {
        int i= 0;
        if(null != pmsSystemStaffsFiles && pmsSystemStaffsFiles.size() > 0){
            //保存文件信息
            String userId = "";
            LoginUser loginUser = SecurityUtils.getLoginUser();
            if (loginUser != null) {
                userId = loginUser.getUser().getUserId().toString();
            }
            for (PmsSystemStaff pmsSystemStaff : pmsSystemStaffsFiles) {
                pmsSystemStaff.setCreateTime(DateUtils.getNowDate());
                pmsSystemStaff.setCreateBy(userId);
            }
            i = pmsSystemStaffMapper.insertPmsSystemStaffLists( pmsSystemStaffsFiles);

        }
        return i;

    }


}
