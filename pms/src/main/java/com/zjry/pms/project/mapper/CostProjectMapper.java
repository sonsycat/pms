package com.zjry.pms.project.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.project.domain.CostProject;
import org.apache.ibatis.annotations.Param;

/**
 * 成本项目管理Mapper接口
 * 
 * @author mk
 * @date 2022-05-06
 */
public interface CostProjectMapper extends BaseMapper<CostProject> {
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
    public int insertCostProject(CostProject costProject);

    void updateLastByCode(@Param("projectCode") String projectCode, @Param("isLast") String isLast);

    /**
     * 修改成本项目管理
     * 
     * @param costProject 成本项目管理
     * @return 结果
     */
    public int updateCostProject(CostProject costProject);

    /**
     * 删除成本项目管理
     * 
     * @param id 成本项目管理ID
     * @return 结果
     */
    public int deleteCostProjectById(Long id);

    /**
     * 批量删除成本项目管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCostProjectByIds(Long[] ids);

    List<CostProject> getDeptList(@Param("compCode") String compCode,@Param("isCostCalculate") String isCostCalculate);

    List<CostProject> deptInfoByType( @Param("type")String type , @Param("compCode") Long compCode);

    List<CostProject> getDeptForPms(@Param("compCode")String compCode, @Param("clinicType")String clinicType , @Param("deptName")String deptName);
}
