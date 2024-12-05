package com.zjry.pms.revenue.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.revenue.domain.PmsRevenueNum;

/**
 * 收入录入Mapper接口
 * 
 * @author mk
 * @date 2022-06-22
 */
public interface PmsRevenueNumMapper extends BaseMapper<PmsRevenueNum> {
    /**
     * 查询收入录入
     * 
     * @param id 收入录入ID
     * @return 收入录入
     */
    public PmsRevenueNum selectPmsRevenueNumById(Long id);

    /**
     * 查询收入录入列表
     * 
     * @param pmsRevenueNum 收入录入
     * @return 收入录入集合
     */
    public List<PmsRevenueNum> selectPmsRevenueNumList(PmsRevenueNum pmsRevenueNum);

    /**
     * 新增收入录入
     * 
     * @param pmsRevenueNum 收入录入
     * @return 结果
     */
    public int insertPmsRevenueNum(PmsRevenueNum pmsRevenueNum);

    /**
     * 修改收入录入
     * 
     * @param pmsRevenueNum 收入录入
     * @return 结果
     */
    public int updatePmsRevenueNum(PmsRevenueNum pmsRevenueNum);

    /**
     * 删除收入录入
     * 
     * @param id 收入录入ID
     * @return 结果
     */
    public int deletePmsRevenueNumById(Long id);

    /**
     * 批量删除收入录入
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsRevenueNumByIds(Long[] ids);

    void deleteByFlag();

    void updateByFlag();

    List<PmsRevenueNum> selectPmsForApp(PmsRevenueNum prn);

    String beforeBreak(PmsRevenueNum prn);
}
