package com.zjry.pms.basic.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.basic.domain.PmsHisIncome;
import com.zjry.pms.project.domain.CostProject;

/**
 * HIS收入类别Service接口
 * 
 * @author maozt
 * @date 2022-06-16
 */
public interface IPmsHisIncomeService extends IService<PmsHisIncome> {
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
    public int insertPmsHisIncome(PmsHisIncome pmsHisIncome) throws Exception;

    /**
     * 修改HIS收入类别
     * 
     * @param pmsHisIncome HIS收入类别
     * @return 结果
     */
    public int updatePmsHisIncome(PmsHisIncome pmsHisIncome) throws Exception;

    /**
     * 批量删除HIS收入类别
     * 
     * @param ids 需要删除的HIS收入类别ID
     * @return 结果
     */
    public int deletePmsHisIncomeByIds(Long[] ids);

    /**
     * 删除HIS收入类别信息
     * 
     * @param id HIS收入类别ID
     * @return 结果
     */
    public int deletePmsHisIncomeById(Long id);

    /**
     * 导入类别
     * @param pmsHisIncomes
     * @param compCode
     * @throws Exception
     */
    void importData(List<PmsHisIncome> pmsHisIncomes, Long compCode ) throws Exception;
}
