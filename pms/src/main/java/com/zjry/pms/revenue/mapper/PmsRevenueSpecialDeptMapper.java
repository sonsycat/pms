package com.zjry.pms.revenue.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.revenue.domain.PmsRevenueSpecialDept;

/**
 * 特殊收入项目科室设置Mapper接口
 * 
 * @author maozt
 * @date 2022-06-23
 */
public interface PmsRevenueSpecialDeptMapper extends BaseMapper<PmsRevenueSpecialDept> {
    /**
     * 查询特殊收入项目科室设置
     * 
     * @param id 特殊收入项目科室设置ID
     * @return 特殊收入项目科室设置
     */
    public PmsRevenueSpecialDept selectPmsRevenueSpecialDeptById(Long id);

    /**
     * 查询特殊收入项目科室设置列表
     * 
     * @param pmsRevenueSpecialDept 特殊收入项目科室设置
     * @return 特殊收入项目科室设置集合
     */
    public List<PmsRevenueSpecialDept> selectPmsRevenueSpecialDeptList(PmsRevenueSpecialDept pmsRevenueSpecialDept);

    /**
     * 新增特殊收入项目科室设置
     * 
     * @param pmsRevenueSpecialDept 特殊收入项目科室设置
     * @return 结果
     */
    public int insertPmsRevenueSpecialDept(PmsRevenueSpecialDept pmsRevenueSpecialDept);

    /**
     * 修改特殊收入项目科室设置
     * 
     * @param pmsRevenueSpecialDept 特殊收入项目科室设置
     * @return 结果
     */
    public int updatePmsRevenueSpecialDept(PmsRevenueSpecialDept pmsRevenueSpecialDept);

    /**
     * 删除特殊收入项目科室设置
     * 
     * @param id 特殊收入项目科室设置ID
     * @return 结果
     */
    public int deletePmsRevenueSpecialDeptById(Long id);

    /**
     * 批量删除特殊收入项目科室设置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsRevenueSpecialDeptByIds(Long[] ids);

    /**
     * 删除特殊收入项目科室设置信息
     *
     * @param projectCode 特殊收入项目科室设置ID
     * @return 结果
     */
    public int deletePmsRevenueSpecialDeptByProjectCode(String projectCode);

    /**
     * 查询科室设置列表
     *
     * @param pmsRevenueSpecialDept 分摊方案管理科室设置
     * @return 分摊方案管理科室设置集合
     */
    public List<PmsRevenueSpecialDept> getDeptProject(PmsRevenueSpecialDept pmsRevenueSpecialDept);
}
