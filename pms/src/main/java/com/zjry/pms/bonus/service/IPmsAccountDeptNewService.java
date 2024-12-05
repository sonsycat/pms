package com.zjry.pms.bonus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.bonus.domain.PmsAccountDept;
import com.zjry.pms.bonus.domain.PmsAccountDeptNew;
import com.zjry.pms.bonus.domain.PmsCopyAccountDeptNew;
import com.zjry.pms.bonus.domain.PmsSaveAccountDeptNew;

import java.util.List;

/**
 * 核算科室设置Service接口
 * 
 * @author zjry
 * @date 2022-09-29
 */
public interface IPmsAccountDeptNewService extends IService<PmsAccountDeptNew> {



    /**
     * 查询核算科室设置列表
     * 
     * @param pmsAccountDeptNew 核算科室设置
     * @return 核算科室设置集合
     */
    public List<PmsAccountDeptNew> selectPmsAccountDeptList(PmsAccountDeptNew pmsAccountDeptNew);


    void saveAccountDeptNew(List<PmsSaveAccountDeptNew> pmsSaveAccountDeptNewList);

    List<PmsAccountDeptNew> queryList(PmsCopyAccountDeptNew param);

    void copyData(PmsCopyAccountDeptNew pmsCopyAccountDeptNew,List<PmsAccountDeptNew> copyList);
}
