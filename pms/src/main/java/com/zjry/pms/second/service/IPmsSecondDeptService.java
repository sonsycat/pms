package com.zjry.pms.second.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.second.domain.PmsSecondDept;

/**
 * 二次分配科室指标Service接口
 * 
 * @author maozt
 * @date 2022-09-27
 */
public interface IPmsSecondDeptService extends IService<PmsSecondDept> {
    /**
     * 查询二次分配科室指标
     * 
     * @param id 二次分配科室指标ID
     * @return 二次分配科室指标
     */
    public PmsSecondDept selectPmsSecondDeptById(Long id);

    /**
     * 查询二次分配科室指标列表
     * 
     * @param pmsSecondDept 二次分配科室指标
     * @return 二次分配科室指标集合
     */
    public List<PmsSecondDept> selectPmsSecondDeptList(PmsSecondDept pmsSecondDept);

    /**
     * 查询二次分配科室指标列表
     *
     * @param pmsSecondDept 二次分配科室指标
     * @return 二次分配科室指标集合
     */
    public List<PmsSecondDept> selectPmsSecondDeptListByCompCode(PmsSecondDept pmsSecondDept);

    /**
     * 新增二次分配科室指标
     * 
     * @param pmsSecondDept 二次分配科室指标
     * @return 结果
     */
    public int insertPmsSecondDept(PmsSecondDept pmsSecondDept);

    /**
     * 修改二次分配科室指标
     * 
     * @param pmsSecondDept 二次分配科室指标
     * @return 结果
     */
    public int updatePmsSecondDept(PmsSecondDept pmsSecondDept);

    /**
     * 批量删除二次分配科室指标
     * 
     * @param ids 需要删除的二次分配科室指标ID
     * @return 结果
     */
    public int deletePmsSecondDeptByIds(Long[] ids);

    /**
     * 删除二次分配科室指标信息
     * 
     * @param id 二次分配科室指标ID
     * @return 结果
     */
    public int deletePmsSecondDeptById(Long id);

    /**
     * 删除二次分配科室指标信息
     *
     * @param date 二次分配科室指标ID
     * @return 结果
     */
    public int deletePmsSecondDeptByNowDate(PmsSecondDept pmsSecondDept);
}
