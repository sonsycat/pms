package com.zjry.pms.revenue.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.revenue.domain.PmsRevenueDept;

/**
 * 收入核算科室设置Mapper接口
 * 
 * @author mk
 * @date 2022-06-21
 */
public interface PmsRevenueDeptMapper extends BaseMapper<PmsRevenueDept> {
    /**
     * 查询收入核算科室设置
     * 
     * @param id 收入核算科室设置ID
     * @return 收入核算科室设置
     */
    public PmsRevenueDept selectPmsRevenueDeptById(Long id);

    /**
     * 查询收入核算科室设置列表
     * 
     * @param pmsRevenueDept 收入核算科室设置
     * @return 收入核算科室设置集合
     */
    public List<PmsRevenueDept> selectPmsRevenueDeptList(PmsRevenueDept pmsRevenueDept);

    /**
     * 新增收入核算科室设置
     * 
     * @param pmsRevenueDept 收入核算科室设置
     * @return 结果
     */
    public int insertPmsRevenueDept(PmsRevenueDept pmsRevenueDept);

    /**
     * 修改收入核算科室设置
     * 
     * @param pmsRevenueDept 收入核算科室设置
     * @return 结果
     */
    public int updatePmsRevenueDept(PmsRevenueDept pmsRevenueDept);

    /**
     * 删除收入核算科室设置
     * 
     * @param id 收入核算科室设置ID
     * @return 结果
     */
    public int deletePmsRevenueDeptById(Long id);

    /**
     * 批量删除收入核算科室设置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsRevenueDeptByIds(Long[] ids);

    void delByRevCODE(String revenueCode);
}
