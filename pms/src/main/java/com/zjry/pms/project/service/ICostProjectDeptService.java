package com.zjry.pms.project.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.project.domain.CostProjectDept;

/**
 * 成本项目管理_科室Service接口
 * 
 * @author mk
 * @date 2022-05-11
 */
public interface ICostProjectDeptService extends IService<CostProjectDept> {
    /**
     * 查询成本项目管理_科室
     * 
     * @param id 成本项目管理_科室ID
     * @return 成本项目管理_科室
     */
    public CostProjectDept selectCostProjectDeptById(Long id);

    /**
     * 查询成本项目管理_科室列表
     * 
     * @param costProjectDept 成本项目管理_科室
     * @return 成本项目管理_科室集合
     */
    public List<CostProjectDept> selectCostProjectDeptList(CostProjectDept costProjectDept);

    /**
     * 新增成本项目管理_科室
     * 
     * @param costProjectDept 成本项目管理_科室
     * @return 结果
     */
    public int insertCostProjectDept(CostProjectDept costProjectDept);

    /**
     * 修改成本项目管理_科室
     * 
     * @param costProjectDept 成本项目管理_科室
     * @return 结果
     */
    public int updateCostProjectDept(CostProjectDept costProjectDept);

    /**
     * 批量删除成本项目管理_科室
     * 
     * @param ids 需要删除的成本项目管理_科室ID
     * @return 结果
     */
    public int deleteCostProjectDeptByIds(Long[] ids);


    /**
     * 删除成本项目管理_科室信息
     * 
     * @param id 成本项目管理_科室ID
     * @return 结果
     */
    public int deleteCostProjectDeptById(Long id);

    /**
     * 删除成本项目管理_科室信息
     *
     * @param projectCode 成本项目科室设置项目编码
     * @return 结果
     */
    public int deleteCostProjectDeptByProjectCode(String projectCode);

    List<CostProjectDept> selectForDept(CostProjectDept costProjectDept);
}
