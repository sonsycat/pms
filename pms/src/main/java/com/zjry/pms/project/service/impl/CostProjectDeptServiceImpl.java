package com.zjry.pms.project.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.BaseEntity;
import org.springframework.stereotype.Service;
import com.zjry.pms.project.mapper.CostProjectDeptMapper;
import com.zjry.pms.project.domain.CostProjectDept;
import com.zjry.pms.project.service.ICostProjectDeptService;

import javax.annotation.Resource;

/**
 * 成本项目管理_科室Service业务层处理
 * 
 * @author mk
 * @date 2022-05-11
 */
@Service
public class CostProjectDeptServiceImpl extends ServiceImpl<CostProjectDeptMapper, CostProjectDept> implements ICostProjectDeptService {
    @Resource
    private CostProjectDeptMapper costProjectDeptMapper;

    /**
     * 查询成本项目管理_科室
     * 
     * @param id 成本项目管理_科室ID
     * @return 成本项目管理_科室
     */
    @Override
    public CostProjectDept selectCostProjectDeptById(Long id) {
        return costProjectDeptMapper.selectCostProjectDeptById(id);
    }

    /**
     * 查询成本项目管理_科室列表
     * 
     * @param costProjectDept 成本项目管理_科室
     * @return 成本项目管理_科室
     */
    @Override
    public List<CostProjectDept> selectCostProjectDeptList(CostProjectDept costProjectDept) {
        return costProjectDeptMapper.selectCostProjectDeptList(costProjectDept);
    }

    /**
     * 新增成本项目管理_科室
     * 
     * @param costProjectDept 成本项目管理_科室
     * @return 结果
     */
    @Override
    public int insertCostProjectDept(CostProjectDept costProjectDept) {
    	costProjectDept.preInsert();
        costProjectDept.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        return costProjectDeptMapper.insertCostProjectDept(costProjectDept);
    }

    /**
     * 修改成本项目管理_科室
     * 
     * @param costProjectDept 成本项目管理_科室
     * @return 结果
     */
    @Override
    public int updateCostProjectDept(CostProjectDept costProjectDept) {
    	costProjectDept.preUpdate();
        return costProjectDeptMapper.updateCostProjectDept(costProjectDept);
    }

    /**
     * 批量删除成本项目管理_科室
     * 
     * @param ids 需要删除的成本项目管理_科室ID
     * @return 结果
     */
    @Override
    public int deleteCostProjectDeptByIds(Long[] ids) {
        return costProjectDeptMapper.deleteCostProjectDeptByIds(ids);
    }

    /**
     * 删除成本项目管理_科室信息
     * 
     * @param id 成本项目管理_科室ID
     * @return 结果
     */
    @Override
    public int deleteCostProjectDeptById(Long id) {
        return costProjectDeptMapper.deleteCostProjectDeptById(id);
    }

    /**
     * 删除成本项目管理_科室信息
     *
     * @param projectCode 成本项目科室设置项目编码
     * @return 结果
     */
    @Override
    public int deleteCostProjectDeptByProjectCode(String projectCode) {
        return costProjectDeptMapper.deleteCostProjectDeptByProjectCode(projectCode);
    }

    @Override
    public List<CostProjectDept> selectForDept(CostProjectDept costProjectDept) {
        return costProjectDeptMapper.selectForDept( costProjectDept);
    }
}
