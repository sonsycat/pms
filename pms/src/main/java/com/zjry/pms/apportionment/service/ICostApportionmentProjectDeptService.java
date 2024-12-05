package com.zjry.pms.apportionment.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.apportionment.domain.CostApportionmentProjectDept;
import com.zjry.pms.apportionment.domain.DeptCostProject;

/**
 * 分摊方案管理科室设置Service接口
 * 
 * @author mk
 * @date 2022-05-13
 */
public interface ICostApportionmentProjectDeptService extends IService<CostApportionmentProjectDept> {
    /**
     * 查询分摊方案管理科室设置
     * 
     * @param id 分摊方案管理科室设置ID
     * @return 分摊方案管理科室设置
     */
    public CostApportionmentProjectDept selectCostApportionmentProjectDeptById(Long id);

    /**
     * 查询分摊方案管理科室设置列表
     * 
     * @param costApportionmentProjectDept 分摊方案管理科室设置
     * @return 分摊方案管理科室设置集合
     */
    public List<CostApportionmentProjectDept> selectCostApportionmentProjectDeptList(CostApportionmentProjectDept costApportionmentProjectDept);

    /**
     * 查询科室设置列表
     *
     * @param costApportionmentProjectDept 分摊方案管理科室设置
     * @return 分摊方案管理科室设置集合
     */
    public List<CostApportionmentProjectDept> getDeptProject(CostApportionmentProjectDept costApportionmentProjectDept);

    /**
     * 查询科室设置列表
     *
     * @param costApportionmentProjectDept 分摊方案管理科室设置
     * @return 分摊方案管理科室设置集合
     */
    public List<CostApportionmentProjectDept> getDeptProject2(CostApportionmentProjectDept costApportionmentProjectDept);

    /**
     * 新增分摊方案管理科室设置
     * 
     * @param costApportionmentProjectDept 分摊方案管理科室设置
     * @return 结果
     */
    public int insertCostApportionmentProjectDept(CostApportionmentProjectDept costApportionmentProjectDept);

    /**
     * 新增分摊方案管理科室设置
     *
     * @param costApportionmentProjectDept 分摊方案管理科室设置
     * @return 结果
     */
    public void insertCostApportionmentProjectDeptPl(CostApportionmentProjectDept costApportionmentProjectDept);

    /**
     * 修改分摊方案管理科室设置
     * 
     * @param costApportionmentProjectDept 分摊方案管理科室设置
     * @return 结果
     */
    public int updateCostApportionmentProjectDept(CostApportionmentProjectDept costApportionmentProjectDept);

    /**
     * 批量删除分摊方案管理科室设置
     * 
     * @param ids 需要删除的分摊方案管理科室设置ID
     * @return 结果
     */
    public int deleteCostApportionmentProjectDeptByIds(Long[] ids);

    /**
     * 删除分摊方案管理科室设置信息
     * 
     * @param id 分摊方案管理科室设置ID
     * @return 结果
     */
    public int deleteCostApportionmentProjectDeptById(Long id);

    /**
     * 分摊方案编码
     *
     * @param projectCode 分摊方案编码
     * @return 结果
     */
    public int deleteCostApportionmentProjectDeptByProjectCode(String projectCode);

    void insertCostDept(DeptCostProject deptCostProject);
}
