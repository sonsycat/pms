package com.zjry.pms.apportionment.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.pms.apportionment.domain.CopyCostAppProject;
import com.zjry.pms.apportionment.domain.CostApportionmentProjectDept;
import com.zjry.pms.apportionment.domain.CostApportionmentProjectRatio;
import com.zjry.pms.apportionment.mapper.CostApportionmentProjectDeptMapper;
import com.zjry.pms.apportionment.mapper.CostApportionmentProjectRatioMapper;
import com.zjry.pms.encode.service.IHrEncodeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.apportionment.mapper.CostApportionmentProjectMapper;
import com.zjry.pms.apportionment.domain.CostApportionmentProject;
import com.zjry.pms.apportionment.service.ICostApportionmentProjectService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 分摊方案管理Service业务层处理
 * 
 * @author mk
 * @date 2022-05-12
 */
@Service
public class CostApportionmentProjectServiceImpl extends ServiceImpl<CostApportionmentProjectMapper, CostApportionmentProject> implements ICostApportionmentProjectService {
    @Resource
    private CostApportionmentProjectMapper costApportionmentProjectMapper;
    @Autowired
    private IHrEncodeRuleService hrEncodeRuleService;
    @Resource
    private CostApportionmentProjectRatioMapper costApportionmentProjectRatioMapper;
    @Resource
    private CostApportionmentProjectDeptMapper costApportionmentProjectDeptMapper;

    /**
     * 查询分摊方案管理
     * 
     * @param id 分摊方案管理ID
     * @return 分摊方案管理
     */
    @Override
    public CostApportionmentProject selectCostApportionmentProjectById(Long id) {
        return costApportionmentProjectMapper.selectCostApportionmentProjectById(id);
    }

    /**
     * 查询分摊方案管理
     *
     * @param projectCode 方案编码
     * @return 分摊方案管理
     */
    @Override
    public CostApportionmentProject selectCostApportionmentProjectByProjectCode(String projectCode) {
        return costApportionmentProjectMapper.selectCostApportionmentProjectByProjectCode(projectCode);
    }

    /**
     * 查询分摊方案管理列表
     * 
     * @param costApportionmentProject 分摊方案管理
     * @return 分摊方案管理
     */
    @Override
    public List<CostApportionmentProject> selectCostApportionmentProjectList(CostApportionmentProject costApportionmentProject) {
        return costApportionmentProjectMapper.selectCostApportionmentProjectList(costApportionmentProject);
    }

    /**
     * 新增分摊方案管理
     * 
     * @param costApportionmentProject 分摊方案管理
     * @return 结果
     */
    @Override
    public int insertCostApportionmentProject(CostApportionmentProject costApportionmentProject) {
    	costApportionmentProject.preInsert();
        costApportionmentProject.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        String projectCode = hrEncodeRuleService.generateEncode("cost_apportionment_project", "PROJECT_IN_CODE");
        costApportionmentProject.setProjectInCode(projectCode);
        return costApportionmentProjectMapper.insertCostApportionmentProject(costApportionmentProject);
    }

    /**
     * 修改分摊方案管理
     * 
     * @param costApportionmentProject 分摊方案管理
     * @return 结果
     */
    @Override
    public int updateCostApportionmentProject(CostApportionmentProject costApportionmentProject) {
    	costApportionmentProject.preUpdate();
        return costApportionmentProjectMapper.updateCostApportionmentProject(costApportionmentProject);
    }

    /**
     * 批量删除分摊方案管理
     * 
     * @param ids 需要删除的分摊方案管理ID
     * @return 结果
     */
    @Override
    public int deleteCostApportionmentProjectByIds(Long[] ids) {
        return costApportionmentProjectMapper.deleteCostApportionmentProjectByIds(ids);
    }

    /**
     * 删除分摊方案管理信息
     * 
     * @param id 分摊方案管理ID
     * @return 结果
     */
    @Override
    public int deleteCostApportionmentProjectById(Long id) {
        return costApportionmentProjectMapper.deleteCostApportionmentProjectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String copyProject(CopyCostAppProject costAppProject)throws Exception {
       List<CostApportionmentProject> list = costAppProject.getCostApportionmentProjectList();
       String msg = "";
       if(list==null || list.size() == 0){
           return "复制数据不能为空";
       }
       for(CostApportionmentProject item: list){
           CostApportionmentProject cap = new CostApportionmentProject();
           cap.setProjectCode(item.getProjectCode());
           cap.setNowDate(costAppProject.getNowDate());
           List<CostApportionmentProject> listc = costApportionmentProjectMapper.selectCostApportionmentProjectList(cap);
           if(listc.size()>0){
               return "该日期下已存在该方案编码";
           }
       }
       //复制主表和子表
        for(CostApportionmentProject item: list){
            item.preInsert();
            String projectCode = hrEncodeRuleService.generateEncode("cost_apportionment_project", "PROJECT_IN_CODE");
            CostApportionmentProjectRatio ratio = new CostApportionmentProjectRatio();
            ratio.setProjectCode(item.getProjectInCode());
            List<CostApportionmentProjectRatio> listRatio =  costApportionmentProjectRatioMapper.selectCostApportionmentProjectRatioList(ratio);
            for(CostApportionmentProjectRatio itr : listRatio){
               // CostApportionmentProjectRatio cRatio = listRatio.get(0);
                itr.setProjectCode(projectCode);
                itr.preInsert();
                costApportionmentProjectRatioMapper.insertCostApportionmentProjectRatio(itr);
            }
//            if(listRatio!=null && listRatio.size()>0){
//
//            }
            CostApportionmentProjectDept dept = new CostApportionmentProjectDept();
            dept.setProjectCode(item.getProjectInCode());
            List<CostApportionmentProjectDept> listDept = costApportionmentProjectDeptMapper.selectCostApportionmentProjectDeptList(dept);
         for(CostApportionmentProjectDept itt: listDept){
             //CostApportionmentProjectDept cDept = listDept.get(0);
             itt.setProjectCode(projectCode);
             itt.preInsert();
             costApportionmentProjectDeptMapper.insertCostApportionmentProjectDept(itt);
         }


            item.setProjectInCode(projectCode);
            item.setNowDate(costAppProject.getNowDate());
            costApportionmentProjectMapper.insertCostApportionmentProject(item);

        }
        return null;
    }

    @Override
    public List<CostApportionmentProject> selectForProject(CostApportionmentProject costApportionmentProject) {
        return costApportionmentProjectMapper.selectForProject(costApportionmentProject);
    }
}
