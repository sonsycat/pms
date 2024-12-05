package com.zjry.pms.apportionment.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.pms.apportionment.domain.DeptCostProject;
import com.zjry.pms.encode.service.IHrEncodeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.apportionment.mapper.CostApportionmentProjectDeptMapper;
import com.zjry.pms.apportionment.domain.CostApportionmentProjectDept;
import com.zjry.pms.apportionment.service.ICostApportionmentProjectDeptService;

import javax.annotation.Resource;

/**
 * 分摊方案管理科室设置Service业务层处理
 * 
 * @author mk
 * @date 2022-05-13
 */
@Service
public class CostApportionmentProjectDeptServiceImpl extends ServiceImpl<CostApportionmentProjectDeptMapper, CostApportionmentProjectDept> implements ICostApportionmentProjectDeptService {
    @Resource
    private CostApportionmentProjectDeptMapper costApportionmentProjectDeptMapper;
    @Autowired
    private IHrEncodeRuleService hrEncodeRuleService;

    /**
     * 查询分摊方案管理科室设置
     * 
     * @param id 分摊方案管理科室设置ID
     * @return 分摊方案管理科室设置
     */
    @Override
    public CostApportionmentProjectDept selectCostApportionmentProjectDeptById(Long id) {
        return costApportionmentProjectDeptMapper.selectCostApportionmentProjectDeptById(id);
    }

    /**
     * 查询分摊方案管理科室设置列表
     * 
     * @param costApportionmentProjectDept 分摊方案管理科室设置
     * @return 分摊方案管理科室设置
     */
    @Override
    public List<CostApportionmentProjectDept> selectCostApportionmentProjectDeptList(CostApportionmentProjectDept costApportionmentProjectDept) {
        return costApportionmentProjectDeptMapper.selectCostApportionmentProjectDeptList(costApportionmentProjectDept);
    }

    /**
     * 查询科室设置列表
     *
     * @param costApportionmentProjectDept 分摊方案管理科室设置
     * @return 分摊方案管理科室设置集合
     */
    @Override
    public List<CostApportionmentProjectDept> getDeptProject(CostApportionmentProjectDept costApportionmentProjectDept) {
        List<CostApportionmentProjectDept> deptProject = costApportionmentProjectDeptMapper.getDeptProject(costApportionmentProjectDept);
        CostApportionmentProjectDept projectDept = new CostApportionmentProjectDept();
        if ("1".equals(costApportionmentProjectDept.getIsProject())) {
            for (CostApportionmentProjectDept apportionmentProjectDept : deptProject) {
                projectDept.setProjectCode(costApportionmentProjectDept.getProjectCode());
                projectDept.setDeptId(apportionmentProjectDept.getDeptCode());
                CostApportionmentProjectDept dept = costApportionmentProjectDeptMapper.selectCostApportionmentProjectDeptList(projectDept).get(0);
                apportionmentProjectDept.setRatio(dept.getRatio());
                apportionmentProjectDept.setFirstRatio(dept.getFirstRatio());
                apportionmentProjectDept.setSecondRatio(dept.getSecondRatio());
                apportionmentProjectDept.setThirdRatio(dept.getThirdRatio());
            }
        }
        return deptProject;
    }

    /**
     * 查询科室设置列表
     *
     * @param costApportionmentProjectDept 分摊方案管理科室设置
     * @return 分摊方案管理科室设置集合
     */
    @Override
    public List<CostApportionmentProjectDept> getDeptProject2(CostApportionmentProjectDept costApportionmentProjectDept) {
        List<CostApportionmentProjectDept> deptProject = costApportionmentProjectDeptMapper.getDeptProject2(costApportionmentProjectDept);
        CostApportionmentProjectDept projectDept = new CostApportionmentProjectDept();
        if ("1".equals(costApportionmentProjectDept.getIsProject())) {
            for (CostApportionmentProjectDept apportionmentProjectDept : deptProject) {
                projectDept.setProjectCode(costApportionmentProjectDept.getProjectCode());
                projectDept.setDeptId(apportionmentProjectDept.getDeptCode());
                CostApportionmentProjectDept dept = costApportionmentProjectDeptMapper.selectCostApportionmentProjectDeptList(projectDept).get(0);
                apportionmentProjectDept.setRatio(dept.getRatio());
                apportionmentProjectDept.setFirstRatio(dept.getFirstRatio());
                apportionmentProjectDept.setSecondRatio(dept.getSecondRatio());
                apportionmentProjectDept.setThirdRatio(dept.getThirdRatio());
            }
        }
        return deptProject;
    }

    /**
     * 新增分摊方案管理科室设置
     * 
     * @param costApportionmentProjectDept 分摊方案管理科室设置
     * @return 结果
     */
    @Override
    public int insertCostApportionmentProjectDept(CostApportionmentProjectDept costApportionmentProjectDept) {
    	costApportionmentProjectDept.preInsert();
        return costApportionmentProjectDeptMapper.insertCostApportionmentProjectDept(costApportionmentProjectDept);
    }

    /**
     * 新增分摊方案管理科室设置
     *
     * @param costApportionmentProjectDept 分摊方案管理科室设置
     * @return 结果
     */
    @Override
    public void insertCostApportionmentProjectDeptPl(CostApportionmentProjectDept costApportionmentProjectDept) {
        List<CostApportionmentProjectDept> deptList = costApportionmentProjectDept.getDeptList();
        for (CostApportionmentProjectDept apportionmentProjectDept : deptList) {
            apportionmentProjectDept.setDeptId(apportionmentProjectDept.getDeptCode());
            apportionmentProjectDept.setProjectCode(costApportionmentProjectDept.getProjectCode());
            this.insertCostApportionmentProjectDept(apportionmentProjectDept);
        }
    }

    /**
     * 修改分摊方案管理科室设置
     * 
     * @param costApportionmentProjectDept 分摊方案管理科室设置
     * @return 结果
     */
    @Override
    public int updateCostApportionmentProjectDept(CostApportionmentProjectDept costApportionmentProjectDept) {
    	costApportionmentProjectDept.preUpdate();
        return costApportionmentProjectDeptMapper.updateCostApportionmentProjectDept(costApportionmentProjectDept);
    }

    /**
     * 批量删除分摊方案管理科室设置
     * 
     * @param ids 需要删除的分摊方案管理科室设置ID
     * @return 结果
     */
    @Override
    public int deleteCostApportionmentProjectDeptByIds(Long[] ids) {
        return costApportionmentProjectDeptMapper.deleteCostApportionmentProjectDeptByIds(ids);
    }

    /**
     * 删除分摊方案管理科室设置信息
     * 
     * @param id 分摊方案管理科室设置ID
     * @return 结果
     */
    @Override
    public int deleteCostApportionmentProjectDeptById(Long id) {
        return costApportionmentProjectDeptMapper.deleteCostApportionmentProjectDeptById(id);
    }

    /**
     * 分摊方案编码
     *
     * @param projectCode 分摊方案编码
     * @return 结果
     */
    @Override
    public int deleteCostApportionmentProjectDeptByProjectCode(String projectCode) {
        return costApportionmentProjectDeptMapper.deleteCostApportionmentProjectDeptByProjectCode(projectCode);
    }

    @Override
    public void insertCostDept(DeptCostProject deptCostProject) {
        List<CostApportionmentProjectDept> list = deptCostProject.getDeptList();
        if(list != null && list.size()>0){
            list.stream().forEach(item->{
                if(item.getId()==null){
                    item.setCompCode(deptCostProject.getCompCode());
                    item.setAccountCode(deptCostProject.getAccountCode());
                    item.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
                    item.preInsert();
                    costApportionmentProjectDeptMapper.insertCostApportionmentProjectDept(item);
                }else{
                    item.preInsert();
                    costApportionmentProjectDeptMapper.updateCostApportionmentProjectDept(item);
                }
            });
        }
    }
}
