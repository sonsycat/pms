package com.zjry.pms.apportionment.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.apportionment.domain.CostApportionmentProject;

/**
 * 分摊方案管理Mapper接口
 * 
 * @author mk
 * @date 2022-05-12
 */
public interface CostApportionmentProjectMapper extends BaseMapper<CostApportionmentProject> {
    /**
     * 查询分摊方案管理
     * 
     * @param id 分摊方案管理ID
     * @return 分摊方案管理
     */
    public CostApportionmentProject selectCostApportionmentProjectById(Long id);

    /**
     * 查询分摊方案管理
     *
     * @param projectCode 方案编码
     * @return 分摊方案管理
     */
    public CostApportionmentProject selectCostApportionmentProjectByProjectCode(String projectCode);

    /**
     * 查询分摊方案管理列表
     * 
     * @param costApportionmentProject 分摊方案管理
     * @return 分摊方案管理集合
     */
    public List<CostApportionmentProject> selectCostApportionmentProjectList(CostApportionmentProject costApportionmentProject);

    /**
     * 新增分摊方案管理
     * 
     * @param costApportionmentProject 分摊方案管理
     * @return 结果
     */
    public int insertCostApportionmentProject(CostApportionmentProject costApportionmentProject);

    /**
     * 修改分摊方案管理
     * 
     * @param costApportionmentProject 分摊方案管理
     * @return 结果
     */
    public int updateCostApportionmentProject(CostApportionmentProject costApportionmentProject);

    /**
     * 删除分摊方案管理
     * 
     * @param id 分摊方案管理ID
     * @return 结果
     */
    public int deleteCostApportionmentProjectById(Long id);

    /**
     * 批量删除分摊方案管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCostApportionmentProjectByIds(Long[] ids);

    List<CostApportionmentProject> selectForProject(CostApportionmentProject costApportionmentProject);
}
