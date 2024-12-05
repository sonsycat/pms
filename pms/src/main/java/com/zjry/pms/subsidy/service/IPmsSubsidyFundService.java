package com.zjry.pms.subsidy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.subsidy.domain.PmsSubsidyFund;
import com.zjry.pms.subsidy.domain.PmsSubsidyFundBalance;

import java.util.List;

/**
 * 科基金Service接口
 * 
 * @author maozt
 * @date 2022-08-17
 */
public interface IPmsSubsidyFundService extends IService<PmsSubsidyFund> {
    public PmsSubsidyFund selectPmsSubsidyFundById(Long id);

    /**
     * 查询科基金列表
     * 
     * @param pmsSubsidyFund 科基金
     * @return 科基金集合
     */
    public List<PmsSubsidyFund> selectPmsSubsidyFundList(PmsSubsidyFund pmsSubsidyFund);

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
    /**
     * 批量删除科基金
     *
     * @param ids 需要删除的科基金ID
     * @return 结果
     */
    public int deletePmsSubsidyFund(Long[] ids);

    int submitBalance(PmsSubsidyFund pmsSubsidyFund);

}
