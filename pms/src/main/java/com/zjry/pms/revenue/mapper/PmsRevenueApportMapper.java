package com.zjry.pms.revenue.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.revenue.domain.PmsRevenueApport;

/**
 * 收入生成Mapper接口
 * 
 * @author mk
 * @date 2022-06-28
 */
public interface PmsRevenueApportMapper extends BaseMapper<PmsRevenueApport> {
    /**
     * 查询收入生成
     * 
     * @param id 收入生成ID
     * @return 收入生成
     */
    public PmsRevenueApport selectPmsRevenueApportById(Long id);

    /**
     * 查询收入生成列表
     * 
     * @param pmsRevenueApport 收入生成
     * @return 收入生成集合
     */
    public List<PmsRevenueApport> selectPmsRevenueApportList(PmsRevenueApport pmsRevenueApport);

    /**
     * 新增收入生成
     * 
     * @param pmsRevenueApport 收入生成
     * @return 结果
     */
    public int insertPmsRevenueApport(PmsRevenueApport pmsRevenueApport);

    /**
     * 修改收入生成
     * 
     * @param pmsRevenueApport 收入生成
     * @return 结果
     */
    public int updatePmsRevenueApport(PmsRevenueApport pmsRevenueApport);

    /**
     * 删除收入生成
     * 
     * @param id 收入生成ID
     * @return 结果
     */
    public int deletePmsRevenueApportById(Long id);

    /**
     * 批量删除收入生成
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsRevenueApportByIds(Long[] ids);

    public void deleteByState();
    public void updateByState();

    String getAfterValue(PmsRevenueApport pmsRevenueApport);
}
