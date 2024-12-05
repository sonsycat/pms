package com.zjry.pms.basic.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.basic.domain.PmsHisIncome;

/**
 * HIS收入类别Mapper接口
 * 
 * @author maozt
 * @date 2022-06-16
 */
public interface PmsHisIncomeMapper extends BaseMapper<PmsHisIncome> {
    /**
     * 查询HIS收入类别
     * 
     * @param id HIS收入类别ID
     * @return HIS收入类别
     */
    public PmsHisIncome selectPmsHisIncomeById(Long id);

    /**
     * 查询HIS收入类别列表
     * 
     * @param pmsHisIncome HIS收入类别
     * @return HIS收入类别集合
     */
    public List<PmsHisIncome> selectPmsHisIncomeList(PmsHisIncome pmsHisIncome);

    /**
     * 查询HIS收入类别列表
     *
     * @param pmsHisIncome HIS收入类别
     * @return HIS收入类别集合
     */
    public List<PmsHisIncome> selectPmsHisIncomeListByName(PmsHisIncome pmsHisIncome);

    /**
     * 新增HIS收入类别
     * 
     * @param pmsHisIncome HIS收入类别
     * @return 结果
     */
    public int insertPmsHisIncome(PmsHisIncome pmsHisIncome);

    /**
     * 修改HIS收入类别
     * 
     * @param pmsHisIncome HIS收入类别
     * @return 结果
     */
    public int updatePmsHisIncome(PmsHisIncome pmsHisIncome);

    /**
     * 删除HIS收入类别
     * 
     * @param id HIS收入类别ID
     * @return 结果
     */
    public int deletePmsHisIncomeById(Long id);

    /**
     * 批量删除HIS收入类别
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsHisIncomeByIds(Long[] ids);
}
