package com.zjry.pms.apportionment.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.apportionment.domain.CostApportionmentProjectRatio;

/**
 * 分摊方案管理比例设置Service接口
 * 
 * @author mk
 * @date 2022-05-13
 */
public interface ICostApportionmentProjectRatioService extends IService<CostApportionmentProjectRatio> {
    /**
     * 查询分摊方案管理比例设置
     * 
     * @param id 分摊方案管理比例设置ID
     * @return 分摊方案管理比例设置
     */
    public CostApportionmentProjectRatio selectCostApportionmentProjectRatioById(Long id);

    /**
     * 查询分摊方案管理比例设置列表
     * 
     * @param costApportionmentProjectRatio 分摊方案管理比例设置
     * @return 分摊方案管理比例设置集合
     */
    public List<CostApportionmentProjectRatio> selectCostApportionmentProjectRatioList(CostApportionmentProjectRatio costApportionmentProjectRatio);

    /**
     * 新增分摊方案管理比例设置
     * 
     * @param costApportionmentProjectRatio 分摊方案管理比例设置
     * @return 结果
     */
    public int insertCostApportionmentProjectRatio(CostApportionmentProjectRatio costApportionmentProjectRatio);

    /**
     * 修改分摊方案管理比例设置
     * 
     * @param costApportionmentProjectRatio 分摊方案管理比例设置
     * @return 结果
     */
    public int updateCostApportionmentProjectRatio(CostApportionmentProjectRatio costApportionmentProjectRatio);

    /**
     * 批量删除分摊方案管理比例设置
     * 
     * @param ids 需要删除的分摊方案管理比例设置ID
     * @return 结果
     */
    public int deleteCostApportionmentProjectRatioByIds(Long[] ids);

    /**
     * 删除分摊方案管理比例设置信息
     * 
     * @param id 分摊方案管理比例设置ID
     * @return 结果
     */
    public int deleteCostApportionmentProjectRatioById(Long id);

    /**
     * 删除分摊方案管理比例设置信息
     *
     * @param projectCode 方案编码
     * @return 结果
     */
    public int deleteCostApportionmentProjectRatioByProjectCode(String projectCode);
}
