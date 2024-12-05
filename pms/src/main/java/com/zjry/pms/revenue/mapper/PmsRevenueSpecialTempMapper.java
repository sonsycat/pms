package com.zjry.pms.revenue.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.revenue.domain.PmsRevenueSpecialTemp;

/**
 * 特殊收入项目方案临时Mapper接口
 * 
 * @author maozt
 * @date 2022-06-21
 */
public interface PmsRevenueSpecialTempMapper extends BaseMapper<PmsRevenueSpecialTemp> {
    /**
     * 查询特殊收入项目方案临时
     * 
     * @param id 特殊收入项目方案临时ID
     * @return 特殊收入项目方案临时
     */
    public PmsRevenueSpecialTemp selectPmsRevenueSpecialTempById(Long id);

    /**
     * 查询特殊收入项目方案临时列表
     * 
     * @param pmsRevenueSpecialTemp 特殊收入项目方案临时
     * @return 特殊收入项目方案临时集合
     */
    public List<PmsRevenueSpecialTemp> selectPmsRevenueSpecialTempList(PmsRevenueSpecialTemp pmsRevenueSpecialTemp);

    /**
     * 查询特殊收入项目方案临时列表
     *
     * @param pmsRevenueSpecialTemp 特殊收入项目方案临时
     * @return 特殊收入项目方案临时集合
     */
    public List<PmsRevenueSpecialTemp> selectPmsRevenueSpecialTempListNoDel(PmsRevenueSpecialTemp pmsRevenueSpecialTemp);

    /**
     * 新增特殊收入项目方案临时
     * 
     * @param pmsRevenueSpecialTemp 特殊收入项目方案临时
     * @return 结果
     */
    public int insertPmsRevenueSpecialTemp(PmsRevenueSpecialTemp pmsRevenueSpecialTemp);

    /**
     * 修改特殊收入项目方案临时
     * 
     * @param pmsRevenueSpecialTemp 特殊收入项目方案临时
     * @return 结果
     */
    public int updatePmsRevenueSpecialTemp(PmsRevenueSpecialTemp pmsRevenueSpecialTemp);

    /**
     * 删除特殊收入项目方案临时
     * 
     * @param id 特殊收入项目方案临时ID
     * @return 结果
     */
    public int deletePmsRevenueSpecialTempById(Long id);

    /**
     * 批量删除特殊收入项目方案临时
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsRevenueSpecialTempByIds(Long[] ids);

    /**
     * 删除特殊收入项目方案临时信息
     *
     * @param compCode 特殊收入项目方案临时ID
     * @return 结果
     */
    public int deletePmsRevenueSpecialTempByCompCode(Long compCode);
}
