package com.zjry.pms.bonus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.bonus.domain.PmsAccountDept;
import com.zjry.pms.bonus.domain.PmsAccountDeptNew;
import com.zjry.pms.bonus.domain.PmsCopyAccountDeptNew;
import com.zjry.pms.bonus.domain.PmsSaveAccountDeptNew;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 核算科室设置Mapper接口
 * 
 * @author zjry
 * @date 2022-09-29
 */
public interface PmsAccountDeptNewMapper extends BaseMapper<PmsAccountDeptNew> {


    /**
     * 查询核算科室设置列表
     * 
     * @param pmsAccountDeptNew 核算科室设置
     * @return 核算科室设置集合
     */
    public List<PmsAccountDeptNew> selectPmsAccountDeptList(PmsAccountDeptNew pmsAccountDeptNew);



    void insertData(List<PmsSaveAccountDeptNew> list);

    void updateData(List<PmsSaveAccountDeptNew> list);

    List<PmsAccountDeptNew> queryList(PmsCopyAccountDeptNew param);
}
