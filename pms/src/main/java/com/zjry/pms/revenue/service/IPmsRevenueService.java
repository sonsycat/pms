package com.zjry.pms.revenue.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.revenue.domain.PmsRevenue;

/**
 * 收入核算方案Service接口
 * 
 * @author mk
 * @date 2022-06-20
 */
public interface IPmsRevenueService extends IService<PmsRevenue> {
    /**
     * 查询收入核算方案
     * 
     * @param id 收入核算方案ID
     * @return 收入核算方案
     */
    public PmsRevenue selectPmsRevenueById(Long id);

    /**
     * 查询收入核算方案列表
     * 
     * @param pmsRevenue 收入核算方案
     * @return 收入核算方案集合
     */
    public List<PmsRevenue> selectPmsRevenueList(PmsRevenue pmsRevenue);

    /**
     * 新增收入核算方案
     * 
     * @param pmsRevenue 收入核算方案
     * @return 结果
     */
    public int insertPmsRevenue(PmsRevenue pmsRevenue);

    /**
     * 修改收入核算方案
     * 
     * @param pmsRevenue 收入核算方案
     * @return 结果
     */
    public int updatePmsRevenue(PmsRevenue pmsRevenue);

    /**
     * 批量删除收入核算方案
     * 
     * @param ids 需要删除的收入核算方案ID
     * @return 结果
     */
    public int deletePmsRevenueByIds(Long[] ids);

    /**
     * 删除收入核算方案信息
     * 
     * @param id 收入核算方案ID
     * @return 结果
     */
    public int deletePmsRevenueById(Long id);
}
