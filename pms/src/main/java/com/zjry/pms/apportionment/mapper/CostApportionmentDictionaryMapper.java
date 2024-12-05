package com.zjry.pms.apportionment.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.apportionment.domain.CostApportionmentDictionary;

/**
 * 比例分摊字典管理Mapper接口
 * 
 * @author yzh
 * @date 2022-05-10
 */
public interface CostApportionmentDictionaryMapper extends BaseMapper<CostApportionmentDictionary> {
    /**
     * 查询比例分摊字典管理
     * 
     * @param id 比例分摊字典管理ID
     * @return 比例分摊字典管理
     */
    public CostApportionmentDictionary selectCostApportionmentDictionaryById(Long id);

    /**
     * 查询比例分摊字典管理列表
     * 
     * @param costApportionmentDictionary 比例分摊字典管理
     * @return 比例分摊字典管理集合
     */
    public List<CostApportionmentDictionary> selectCostApportionmentDictionaryList(CostApportionmentDictionary costApportionmentDictionary);
    
    /**
     * 查询比例分摊字典管理列表
     * 
     * @param costApportionmentDictionary 比例分摊字典管理
     * @return 比例分摊字典管理集合
     */
    public List<CostApportionmentDictionary> selectCostApportionmentDictionaryListByName(CostApportionmentDictionary costApportionmentDictionary);

    /**
     * 新增比例分摊字典管理
     * 
     * @param costApportionmentDictionary 比例分摊字典管理
     * @return 结果
     */
    public int insertCostApportionmentDictionary(CostApportionmentDictionary costApportionmentDictionary);

    /**
     * 修改比例分摊字典管理
     * 
     * @param costApportionmentDictionary 比例分摊字典管理
     * @return 结果
     */
    public int updateCostApportionmentDictionary(CostApportionmentDictionary costApportionmentDictionary);

    /**
     * 删除比例分摊字典管理
     * 
     * @param id 比例分摊字典管理ID
     * @return 结果
     */
    public int deleteCostApportionmentDictionaryById(Long id);

    /**
     * 批量删除比例分摊字典管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCostApportionmentDictionaryByIds(Long[] ids);
}
