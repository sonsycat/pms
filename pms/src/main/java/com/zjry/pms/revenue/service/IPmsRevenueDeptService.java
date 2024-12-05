package com.zjry.pms.revenue.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.revenue.domain.PmRevDept;
import com.zjry.pms.revenue.domain.PmsRevenueDept;

/**
 * 收入核算科室设置Service接口
 * 
 * @author mk
 * @date 2022-06-21
 */
public interface IPmsRevenueDeptService extends IService<PmsRevenueDept> {
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
     * @param pmRevDept 收入核算科室设置
     * @return 结果
     */
    public void insertPmsRevenueDept(PmRevDept pmRevDept)throws Exception;

    /**
     * 修改收入核算科室设置
     * 
     * @param pmRevDept 收入核算科室设置
     * @return 结果
     */
    public void updatePmsRevenueDept(PmRevDept pmRevDept) throws Exception;

    /**
     * 批量删除收入核算科室设置
     * 
     * @param ids 需要删除的收入核算科室设置ID
     * @return 结果
     */
    public int deletePmsRevenueDeptByIds(Long[] ids);

    /**
     * 删除收入核算科室设置信息
     * 
     * @param id 收入核算科室设置ID
     * @return 结果
     */
    public int deletePmsRevenueDeptById(Long id);
}
