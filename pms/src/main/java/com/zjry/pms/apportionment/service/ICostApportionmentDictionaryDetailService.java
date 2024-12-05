package com.zjry.pms.apportionment.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.apportionment.domain.CostApportionmentDictionaryDetail;

/**
 * 比例分摊字典明细Service接口
 * 
 * @author yzh
 * @date 2022-05-12
 */
public interface ICostApportionmentDictionaryDetailService extends IService<CostApportionmentDictionaryDetail> {
    /**
     * 查询比例分摊字典明细
     * 
     * @param id 比例分摊字典明细ID
     * @return 比例分摊字典明细
     */
    public CostApportionmentDictionaryDetail selectCostApportionmentDictionaryDetailById(Long id);

    /**
     * 查询比例分摊字典明细列表
     * 
     * @param costApportionmentDictionaryDetail 比例分摊字典明细
     * @return 比例分摊字典明细集合
     */
    public List<CostApportionmentDictionaryDetail> selectCostApportionmentDictionaryDetailList(CostApportionmentDictionaryDetail costApportionmentDictionaryDetail);

    /**
     * 新增比例分摊字典明细
     * 
     * @param costApportionmentDictionaryDetail 比例分摊字典明细
     * @return 结果
     */
    public int insertCostApportionmentDictionaryDetail(CostApportionmentDictionaryDetail costApportionmentDictionaryDetail);

    /**
     * 修改比例分摊字典明细
     * 
     * @param costApportionmentDictionaryDetail 比例分摊字典明细
     * @return 结果
     */
    public int updateCostApportionmentDictionaryDetail(CostApportionmentDictionaryDetail costApportionmentDictionaryDetail);

    /**
     * 批量删除比例分摊字典明细
     * 
     * @param ids 需要删除的比例分摊字典明细ID
     * @return 结果
     */
    public int deleteCostApportionmentDictionaryDetailByIds(Long[] ids);

    /**
     * 删除比例分摊字典明细信息
     * 
     * @param id 比例分摊字典明细ID
     * @return 结果
     */
    public int deleteCostApportionmentDictionaryDetailById(Long id);
}
