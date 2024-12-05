package com.zjry.pms.apportionment.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.apportionment.mapper.CostApportionmentProjectRatioMapper;
import com.zjry.pms.apportionment.domain.CostApportionmentProjectRatio;
import com.zjry.pms.apportionment.service.ICostApportionmentProjectRatioService;

/**
 * 分摊方案管理比例设置Service业务层处理
 * 
 * @author mk
 * @date 2022-05-13
 */
@Service
public class CostApportionmentProjectRatioServiceImpl extends ServiceImpl<CostApportionmentProjectRatioMapper, CostApportionmentProjectRatio> implements ICostApportionmentProjectRatioService {
    @Autowired
    private CostApportionmentProjectRatioMapper costApportionmentProjectRatioMapper;

    /**
     * 查询分摊方案管理比例设置
     * 
     * @param id 分摊方案管理比例设置ID
     * @return 分摊方案管理比例设置
     */
    @Override
    public CostApportionmentProjectRatio selectCostApportionmentProjectRatioById(Long id) {
        return costApportionmentProjectRatioMapper.selectCostApportionmentProjectRatioById(id);
    }

    /**
     * 查询分摊方案管理比例设置列表
     * 
     * @param costApportionmentProjectRatio 分摊方案管理比例设置
     * @return 分摊方案管理比例设置
     */
    @Override
    public List<CostApportionmentProjectRatio> selectCostApportionmentProjectRatioList(CostApportionmentProjectRatio costApportionmentProjectRatio) {
        return costApportionmentProjectRatioMapper.selectCostApportionmentProjectRatioList(costApportionmentProjectRatio);
    }

    /**
     * 新增分摊方案管理比例设置
     * 
     * @param costApportionmentProjectRatio 分摊方案管理比例设置
     * @return 结果
     */
    @Override
    public int insertCostApportionmentProjectRatio(CostApportionmentProjectRatio costApportionmentProjectRatio) {
    	costApportionmentProjectRatio.preInsert();
        costApportionmentProjectRatio.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        return costApportionmentProjectRatioMapper.insertCostApportionmentProjectRatio(costApportionmentProjectRatio);
    }

    /**
     * 修改分摊方案管理比例设置
     * 
     * @param costApportionmentProjectRatio 分摊方案管理比例设置
     * @return 结果
     */
    @Override
    public int updateCostApportionmentProjectRatio(CostApportionmentProjectRatio costApportionmentProjectRatio) {
    	costApportionmentProjectRatio.preUpdate();
        return costApportionmentProjectRatioMapper.updateCostApportionmentProjectRatio(costApportionmentProjectRatio);
    }

    /**
     * 批量删除分摊方案管理比例设置
     * 
     * @param ids 需要删除的分摊方案管理比例设置ID
     * @return 结果
     */
    @Override
    public int deleteCostApportionmentProjectRatioByIds(Long[] ids) {
        return costApportionmentProjectRatioMapper.deleteCostApportionmentProjectRatioByIds(ids);
    }

    /**
     * 删除分摊方案管理比例设置信息
     * 
     * @param id 分摊方案管理比例设置ID
     * @return 结果
     */
    @Override
    public int deleteCostApportionmentProjectRatioById(Long id) {
        return costApportionmentProjectRatioMapper.deleteCostApportionmentProjectRatioById(id);
    }

    /**
     * 删除分摊方案管理比例设置信息
     *
     * @param projectCode 方案编码
     * @return 结果
     */
    @Override
    public int deleteCostApportionmentProjectRatioByProjectCode(String projectCode) {
        return costApportionmentProjectRatioMapper.deleteCostApportionmentProjectRatioByProjectCode(projectCode);
    }
}
