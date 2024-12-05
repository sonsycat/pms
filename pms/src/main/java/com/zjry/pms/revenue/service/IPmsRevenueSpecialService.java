package com.zjry.pms.revenue.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.revenue.domain.PmsRevenueSpecial;

/**
 * 特殊收入项目方案Service接口
 * 
 * @author maozt
 * @date 2022-06-21
 */
public interface IPmsRevenueSpecialService extends IService<PmsRevenueSpecial> {
    /**
     * 查询特殊收入项目方案
     * 
     * @param id 特殊收入项目方案ID
     * @return 特殊收入项目方案
     */
    public PmsRevenueSpecial selectPmsRevenueSpecialById(Long id);

    /**
     * 查询特殊收入项目方案列表
     * 
     * @param pmsRevenueSpecial 特殊收入项目方案
     * @return 特殊收入项目方案集合
     */
    public List<PmsRevenueSpecial> selectPmsRevenueSpecialList(PmsRevenueSpecial pmsRevenueSpecial);

    /**
     * 新增特殊收入项目方案
     * 
     * @param pmsRevenueSpecial 特殊收入项目方案
     * @return 结果
     */
    public int insertPmsRevenueSpecial(PmsRevenueSpecial pmsRevenueSpecial) throws Exception;

    /**
     * 修改特殊收入项目方案
     * 
     * @param pmsRevenueSpecial 特殊收入项目方案
     * @return 结果
     */
    public int updatePmsRevenueSpecial(PmsRevenueSpecial pmsRevenueSpecial) throws Exception;

    /**
     * 修改特殊收入项目方案
     *
     * @param pmsRevenueSpecial 特殊收入项目方案
     * @return 结果
     */
    public int updatePmsRevenueSpecialBySpecialCode(PmsRevenueSpecial pmsRevenueSpecial) throws Exception;

    /**
     * 批量删除特殊收入项目方案
     * 
     * @param ids 需要删除的特殊收入项目方案ID
     * @return 结果
     */
    public int deletePmsRevenueSpecialByIds(Long[] ids);

    /**
     * 删除特殊收入项目方案信息
     * 
     * @param id 特殊收入项目方案ID
     * @return 结果
     */
    public int deletePmsRevenueSpecialById(Long id);

    /**
     * 删除特殊收入项目方案信息
     *
     * @param pmsRevenueSpecial 特殊收入项目方案ID
     * @return 结果
     */
    public int deletePmsRevenueSpecialBySpecialCode(PmsRevenueSpecial pmsRevenueSpecial);
}
