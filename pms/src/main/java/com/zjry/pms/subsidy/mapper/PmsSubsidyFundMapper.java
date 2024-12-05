package com.zjry.pms.subsidy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.subsidy.domain.PmsSubsidyFund;
import com.zjry.pms.subsidy.domain.PmsSubsidyFundBalance;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 科基金Mapper接口
 * 
 * @author maozt
 * @date 2022-08-17
 */
public interface PmsSubsidyFundMapper extends BaseMapper<PmsSubsidyFund> {

    /**
     * 查询科基金列表
     * 
     * @param pmsSubsidyFund 科基金
     * @return 科基金集合
     */
    public List<PmsSubsidyFund> selectPmsSubsidyFundList(PmsSubsidyFund pmsSubsidyFund);
    public PmsSubsidyFund selectPmsSubsidyFundById(Long id);

    /**
     * 新增科基金
     * 
     * @param pmsSubsidyFund 科基金
     * @return 结果
     */
    public int insertPmsSubsidyFund(PmsSubsidyFund pmsSubsidyFund);

    /**
     * 修改科基金
     * 
     * @param pmsSubsidyFund 科基金
     * @return 结果
     */
    public int updatePmsSubsidyFund(PmsSubsidyFund pmsSubsidyFund);

    public PmsSubsidyFundBalance getDeptFundBalance(String deptId);

    int deletePmsSubsidyFund(@Param("ids") Long[] ids, @Param("deleteTime")Date deleteTime, @Param("updateBy")String updateBy);

    Date getDeptMaxDate(String deptCode);
    Date getDeptMaxDateNoId(@Param("deptCode")String deptCode,@Param("id")Long id);

    PmsSubsidyFund getMesByDateAndDept(PmsSubsidyFund pmsSubsidyFund);

    String selectFundBalanceByDeptCode(String deptCode);

    List<PmsSubsidyFund> selectPmsSubsidyFundByIds(@Param("ids") Long[] ids);

    void updateFundState(PmsSubsidyFund pmsSubsidyFund);

    void insertFundBalance(PmsSubsidyFundBalance pmsSubsidyFundBalance);

    void updateFundBalance(PmsSubsidyFundBalance pmsSubsidyFundBalance);

    void updateFundStaterByIds(@Param("ids")Long[] ids);


    List<PmsSubsidyFund> getMesByDateANdState(PmsSubsidyFund pmsSubsidyFund);
}
