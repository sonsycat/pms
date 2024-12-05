package com.zjry.pms.revenue.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.revenue.mapper.PmsRevenueSpecialDeptMapper;
import com.zjry.pms.revenue.domain.PmsRevenueSpecialDept;
import com.zjry.pms.revenue.service.IPmsRevenueSpecialDeptService;

/**
 * 特殊收入项目科室设置Service业务层处理
 * 
 * @author maozt
 * @date 2022-06-23
 */
@Service
public class PmsRevenueSpecialDeptServiceImpl extends ServiceImpl<PmsRevenueSpecialDeptMapper, PmsRevenueSpecialDept> implements IPmsRevenueSpecialDeptService {
    @Autowired
    private PmsRevenueSpecialDeptMapper pmsRevenueSpecialDeptMapper;

    /**
     * 查询特殊收入项目科室设置
     * 
     * @param id 特殊收入项目科室设置ID
     * @return 特殊收入项目科室设置
     */
    @Override
    public PmsRevenueSpecialDept selectPmsRevenueSpecialDeptById(Long id) {
        return pmsRevenueSpecialDeptMapper.selectPmsRevenueSpecialDeptById(id);
    }

    /**
     * 查询特殊收入项目科室设置列表
     * 
     * @param pmsRevenueSpecialDept 特殊收入项目科室设置
     * @return 特殊收入项目科室设置
     */
    @Override
    public List<PmsRevenueSpecialDept> selectPmsRevenueSpecialDeptList(PmsRevenueSpecialDept pmsRevenueSpecialDept) {
        return pmsRevenueSpecialDeptMapper.selectPmsRevenueSpecialDeptList(pmsRevenueSpecialDept);
    }

    /**
     * 新增特殊收入项目科室设置
     * 
     * @param pmsRevenueSpecialDept 特殊收入项目科室设置
     * @return 结果
     */
    @Override
    public int insertPmsRevenueSpecialDept(PmsRevenueSpecialDept pmsRevenueSpecialDept) {
    	pmsRevenueSpecialDept.preInsert();
        return pmsRevenueSpecialDeptMapper.insertPmsRevenueSpecialDept(pmsRevenueSpecialDept);
    }

    /**
     * 新增特殊收入项目科室设置
     *
     * @param pmsRevenueSpecialDept 特殊收入项目科室设置
     * @return 结果
     */
    @Override
    public int insertPmsRevenueSpecialDeptPl(PmsRevenueSpecialDept pmsRevenueSpecialDept) {
        List<PmsRevenueSpecialDept> deptList = pmsRevenueSpecialDept.getDeptList();
        int row = 0;
        for (PmsRevenueSpecialDept revenueSpecialDept : deptList) {
            revenueSpecialDept.setSpecialCode(pmsRevenueSpecialDept.getSpecialCode());
            row += this.insertPmsRevenueSpecialDept(revenueSpecialDept);
        }
        return row;
    }

    /**
     * 修改特殊收入项目科室设置
     * 
     * @param pmsRevenueSpecialDept 特殊收入项目科室设置
     * @return 结果
     */
    @Override
    public int updatePmsRevenueSpecialDept(PmsRevenueSpecialDept pmsRevenueSpecialDept) {
    	pmsRevenueSpecialDept.preUpdate();
        return pmsRevenueSpecialDeptMapper.updatePmsRevenueSpecialDept(pmsRevenueSpecialDept);
    }

    /**
     * 批量删除特殊收入项目科室设置
     * 
     * @param ids 需要删除的特殊收入项目科室设置ID
     * @return 结果
     */
    @Override
    public int deletePmsRevenueSpecialDeptByIds(Long[] ids) {
        return pmsRevenueSpecialDeptMapper.deletePmsRevenueSpecialDeptByIds(ids);
    }

    /**
     * 删除特殊收入项目科室设置信息
     * 
     * @param id 特殊收入项目科室设置ID
     * @return 结果
     */
    @Override
    public int deletePmsRevenueSpecialDeptById(Long id) {
        return pmsRevenueSpecialDeptMapper.deletePmsRevenueSpecialDeptById(id);
    }

    /**
     * 删除特殊收入项目科室设置信息
     *
     * @param projectCode 特殊收入项目科室设置ID
     * @return 结果
     */
    @Override
    public int deletePmsRevenueSpecialDeptByProjectCode(String projectCode) {
        return pmsRevenueSpecialDeptMapper.deletePmsRevenueSpecialDeptByProjectCode(projectCode);
    }

    /**
     * 查询科室设置列表
     *
     * @param pmsRevenueSpecialDept 分摊方案管理科室设置
     * @return 分摊方案管理科室设置集合
     */
    @Override
    public List<PmsRevenueSpecialDept> getDeptProject(PmsRevenueSpecialDept pmsRevenueSpecialDept) {
        List<PmsRevenueSpecialDept> deptProject = pmsRevenueSpecialDeptMapper.getDeptProject(pmsRevenueSpecialDept);
        PmsRevenueSpecialDept projectDept = new PmsRevenueSpecialDept();
        if ("1".equals(pmsRevenueSpecialDept.getIsProject())) {
            for (PmsRevenueSpecialDept revenueSpecialDept : deptProject) {
                projectDept.setSpecialCode(pmsRevenueSpecialDept.getSpecialCode());
                projectDept.setDeptCode(revenueSpecialDept.getDeptCode());
                PmsRevenueSpecialDept dept = pmsRevenueSpecialDeptMapper.selectPmsRevenueSpecialDeptList(projectDept).get(0);
                revenueSpecialDept.setRatio(dept.getRatio());
            }
        }
        return deptProject;
    }
}
