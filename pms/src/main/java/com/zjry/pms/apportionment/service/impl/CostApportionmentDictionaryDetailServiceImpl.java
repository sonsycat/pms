package com.zjry.pms.apportionment.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.pms.apportionment.domain.CostApportionmentDictionaryDetail;
import com.zjry.pms.apportionment.mapper.CostApportionmentDictionaryDetailMapper;
import com.zjry.pms.apportionment.service.ICostApportionmentDictionaryDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 比例分摊字典明细Service业务层处理
 * 
 * @author yzh
 * @date 2022-05-12
 */
@Service
public class CostApportionmentDictionaryDetailServiceImpl extends ServiceImpl<CostApportionmentDictionaryDetailMapper, CostApportionmentDictionaryDetail> implements ICostApportionmentDictionaryDetailService {
    @Autowired
    private CostApportionmentDictionaryDetailMapper costApportionmentDictionaryDetailMapper;

    /**
     * 查询比例分摊字典明细
     * 
     * @param id 比例分摊字典明细ID
     * @return 比例分摊字典明细
     */
    @Override
    public CostApportionmentDictionaryDetail selectCostApportionmentDictionaryDetailById(Long id) {
        return costApportionmentDictionaryDetailMapper.selectCostApportionmentDictionaryDetailById(id);
    }

    /**
     * 查询比例分摊字典明细列表
     * 
     * @param costApportionmentDictionaryDetail 比例分摊字典明细
     * @return 比例分摊字典明细
     */
    @Override
    public List<CostApportionmentDictionaryDetail> selectCostApportionmentDictionaryDetailList(CostApportionmentDictionaryDetail costApportionmentDictionaryDetail) {
        return costApportionmentDictionaryDetailMapper.selectCostApportionmentDictionaryDetailList(costApportionmentDictionaryDetail);
    }

    /**
     * 新增比例分摊字典明细
     * 
     * @param costApportionmentDictionaryDetail 比例分摊字典明细
     * @return 结果
     */
    @Override
    public int insertCostApportionmentDictionaryDetail(CostApportionmentDictionaryDetail costApportionmentDictionaryDetail) {
    	costApportionmentDictionaryDetail.preInsert();
        return costApportionmentDictionaryDetailMapper.insertCostApportionmentDictionaryDetail(costApportionmentDictionaryDetail);
    }

    /**
     * 修改比例分摊字典明细
     * 
     * @param costApportionmentDictionaryDetail 比例分摊字典明细
     * @return 结果
     */
    @Override
    public int updateCostApportionmentDictionaryDetail(CostApportionmentDictionaryDetail costApportionmentDictionaryDetail) {
    	costApportionmentDictionaryDetail.preUpdate();
        return costApportionmentDictionaryDetailMapper.updateCostApportionmentDictionaryDetail(costApportionmentDictionaryDetail);
    }

    /**
     * 批量删除比例分摊字典明细
     * 
     * @param ids 需要删除的比例分摊字典明细ID
     * @return 结果
     */
    @Override
    public int deleteCostApportionmentDictionaryDetailByIds(Long[] ids) {
        return costApportionmentDictionaryDetailMapper.deleteCostApportionmentDictionaryDetailByIds(ids);
    }

    /**
     * 删除比例分摊字典明细信息
     * 
     * @param id 比例分摊字典明细ID
     * @return 结果
     */
    @Override
    public int deleteCostApportionmentDictionaryDetailById(Long id) {
        return costApportionmentDictionaryDetailMapper.deleteCostApportionmentDictionaryDetailById(id);
    }
}
