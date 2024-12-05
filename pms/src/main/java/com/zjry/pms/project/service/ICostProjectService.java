package com.zjry.pms.project.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.project.domain.CostProject;

/**
 * 成本项目管理Service接口
 * 
 * @author mk
 * @date 2022-05-06
 */
public interface ICostProjectService extends IService<CostProject> {
    /**
     * 查询成本项目管理
     * 
     * @param id 成本项目管理ID
     * @return 成本项目管理
     */
    public CostProject selectCostProjectById(Long id);

    /**
     * 查询成本项目管理列表
     * 
     * @param costProject 成本项目管理
     * @return 成本项目管理集合
     */
    public List<CostProject> selectCostProjectList(CostProject costProject);

    /**
     * 新增成本项目管理
     * 
     * @param costProject 成本项目管理
     * @return 结果
     */
    public int insertCostProject(CostProject costProject) throws Exception;

    /**
     * 修改成本项目管理
     * 
     * @param costProject 成本项目管理
     * @return 结果
     */
    public int updateCostProject(CostProject costProject);

    /**
     * 批量删除成本项目管理
     * 
     * @param ids 需要删除的成本项目管理ID
     * @return 结果
     */
    public String deleteCostProjectByIds(Long[] ids) throws Exception;

    /**
     * 删除成本项目管理信息
     * 
     * @param id 成本项目管理ID
     * @return 结果
     */
    public int deleteCostProjectById(Long id);

    void importData(List<CostProject> costProjectfies,Long compCode ) throws Exception;

    /**
     * 查询单位下科室
     *
     * @param compCode 单位编码
     * @return 收入项目管理集合
     */
    public List<CostProject> getDeptList(String compCode,String isCostCalculate);

    /**
     * 查询单位下科室
     *
     * @param compCode 单位编码
     * @return 收入项目管理集合
     */
    public List<CostProject> getDeptList(String compCode);

    List<CostProject> deptInfoByType(String type,Long compCode);

    List<CostProject> getDeptForPms(String compCode, String clinicType,String deptName);
}
