package com.zjry.pms.project.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.pms.apport.domain.CostApportDetail;
import com.zjry.pms.apport.service.ICostApportDetailService;
import com.zjry.pms.common.Constants;
import com.zjry.pms.project.domain.CostProjectDept;
import com.zjry.pms.project.service.ICostProjectDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.project.mapper.CostProjectMapper;
import com.zjry.pms.project.domain.CostProject;
import com.zjry.pms.project.service.ICostProjectService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 成本项目管理Service业务层处理
 *
 * @author mk
 * @date 2022-05-06
 */
@Service
public class CostProjectServiceImpl extends ServiceImpl<CostProjectMapper, CostProject> implements ICostProjectService {
    @Resource
    private CostProjectMapper costProjectMapper;
    @Autowired
    private ICostProjectDeptService costProjectDeptService;
    @Autowired
    private ICostApportDetailService costApportDetailService;


    /**
     * 查询成本项目管理
     *
     * @param id 成本项目管理ID
     * @return 成本项目管理
     */
    @Override
    public CostProject selectCostProjectById(Long id) {
        CostProject costProject = costProjectMapper.selectCostProjectById(id);
        CostProjectDept costProjectDept = new CostProjectDept();
        costProjectDept.setCompCode(costProject.getCompCode());
        costProjectDept.setProjectCode(costProject.getProjectCode());
        List<CostProjectDept> costProjectDepts = costProjectDeptService.selectCostProjectDeptList(costProjectDept);
        List<String> deptCodes = new ArrayList<>();
        for (CostProjectDept projectDept : costProjectDepts) {
            deptCodes.add(String.valueOf(projectDept.getDeptCode()));
        }
        costProject.setDeptList(deptCodes);
        return costProject;
    }

    /**
     * 查询成本项目管理列表
     *
     * @param costProject 成本项目管理
     * @return 成本项目管理
     */
    @Override
    public List<CostProject> selectCostProjectList(CostProject costProject) {
        return costProjectMapper.selectCostProjectList(costProject);
    }

    /**
     * 新增成本项目管理
     *
     * @param costProject 成本项目管理
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertCostProject(CostProject costProject)throws Exception {
        costProject.preInsert();
        costProject.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        costProject.setIsLast(Constants.IS_CHANGE_YES);
        costProjectMapper.updateLastByCode(costProject.getParentProject(),Constants.IS_CHANGE_NO);
        return costProjectMapper.insertCostProject(costProject);
    }

    /**
     * 修改成本项目管理
     *
     * @param costProject 成本项目管理
     * @return 结果
     */
    @Override
    public int updateCostProject(CostProject costProject) {
        costProject.preUpdate();
        return costProjectMapper.updateCostProject(costProject);
    }



    /**
     * 批量删除成本项目管理
     *
     * @param ids 需要删除的成本项目管理ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteCostProjectByIds(Long[] ids)throws Exception {
        CostProject cps = costProjectMapper.selectCostProjectById(ids[0]);
        if(cps.getIsLast().equals(Constants.YSE)){
            return "非末级项目不能删除";
        }
        CostApportDetail cad = new CostApportDetail();
        cad.setProjectCode(cps.getProjectCode());
        List<CostApportDetail> listCad = costApportDetailService.selectCostApportDetailList(cad);
        if(listCad!=null && listCad.size()>0){
            return "该项目已参与分摊不能删除";
        }
        costProjectDeptService.deleteCostProjectDeptByProjectCode(cps.getProjectCode());
        String parentCode = cps.getParentProject();
        costProjectMapper.deleteCostProjectByIds(ids);
        CostProject cp = new CostProject();
        cp.setParentProject(parentCode);
        List<CostProject>  list = costProjectMapper.selectCostProjectList(cp);
        if(list == null || list.size() == 0){
            costProjectMapper.updateLastByCode(parentCode,Constants.IS_CHANGE_YES);
        }
        return null;
    }

    /**
     * 删除成本项目管理信息
     *
     * @param id 成本项目管理ID
     * @return 结果
     */
    @Override
    public int deleteCostProjectById(Long id) {
        return costProjectMapper.deleteCostProjectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importData(List<CostProject> costProjectfies,Long compCode )throws Exception {
        for (CostProject cp : costProjectfies){
            cp.preInsert();
            cp.setCompCode(compCode);
            //cp.setAccountCode(accountCode);
            cp.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
            costProjectMapper.insertCostProject(cp);
        }
    }

    /**
     * 查询单位下科室
     *
     * @param compCode 单位编码
     * @return 收入项目管理集合
     */
    @Override
    public List<CostProject> getDeptList(String compCode,String isCostCalculate) {
        return costProjectMapper.getDeptList(compCode,isCostCalculate);
    }

    /**
     * 查询单位下科室
     *
     * @param compCode 单位编码
     * @return 收入项目管理集合
     */
    @Override
    public List<CostProject> getDeptList(String compCode) {
        return this.getDeptList(compCode,null);
    }

    @Override
    public List<CostProject> deptInfoByType(String type,Long compCode) {
        return costProjectMapper.deptInfoByType(type,compCode);
    }

    @Override
    public List<CostProject> getDeptForPms(String compCode, String clinicType,String deptName) {
        return costProjectMapper.getDeptForPms( compCode, clinicType,deptName);
    }


}
