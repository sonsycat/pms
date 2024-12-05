package com.zjry.pms.apport.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.apport.mapper.CostApportDetailMapper;
import com.zjry.pms.apport.domain.CostApportDetail;
import com.zjry.pms.apport.service.ICostApportDetailService;

/**
 * 分摊明细Service业务层处理
 * 
 * @author yzh
 * @date 2022-05-17
 */
@Service
public class CostApportDetailServiceImpl extends ServiceImpl<CostApportDetailMapper, CostApportDetail> implements ICostApportDetailService {
    @Autowired
    private CostApportDetailMapper costApportDetailMapper;

    /**
     * 查询分摊明细
     * 
     * @param id 分摊明细ID
     * @return 分摊明细
     */
    @Override
    public CostApportDetail selectCostApportDetailById(Long id) {
        return costApportDetailMapper.selectCostApportDetailById(id);
    }
    
    /**
     * 查询科室类型
     * 
     * @param id 分摊明细ID
     * @return 分摊明细
     */
    @Override
    public CostApportDetail selectDeptRoomById(Long id){
    	return costApportDetailMapper.selectDeptRoomById(id);
    }

    /**
     * 查询分摊明细列表
     * 
     * @param costApportDetail 分摊明细
     * @return 分摊明细
     */
    @Override
    public List<CostApportDetail> selectCostApportDetailList(CostApportDetail costApportDetail) {
        return costApportDetailMapper.selectCostApportDetailList(costApportDetail);
    }
    
    /**
     * 查询分摊明细列表
     * 
     * @param costApportDetail 分摊明细
     * @return 分摊明细
     */
    @Override
    public List<CostApportDetail> selectCostApportDetailLists(CostApportDetail costApportDetail) {
        return costApportDetailMapper.selectCostApportDetailLists(costApportDetail);
    }

    /**
     * 查询按科室编码分组
     * 
     * @param costApportDetail 分摊明细
     * @return 查询按科室编码分组
     */
    @Override
    public List<CostApportDetail> selectCostApportDetailListGroupDept(CostApportDetail costApportDetail){
    	return costApportDetailMapper.selectCostApportDetailListGroupDept(costApportDetail);
    }
    
    /**
     * 新增分摊明细
     * 
     * @param costApportDetail 分摊明细
     * @return 结果
     */
    @Override
    public int insertCostApportDetail(CostApportDetail costApportDetail) {
    	costApportDetail.preInsert();
        return costApportDetailMapper.insertCostApportDetail(costApportDetail);
    }

    /**
     * 修改分摊明细
     * 
     * @param costApportDetail 分摊明细
     * @return 结果
     */
    @Override
    public int updateCostApportDetail(CostApportDetail costApportDetail) {
    	costApportDetail.preUpdate();
        return costApportDetailMapper.updateCostApportDetail(costApportDetail);
    }

    /**
     * 批量删除分摊明细
     * 
     * @param ids 需要删除的分摊明细ID
     * @return 结果
     */
    @Override
    public int deleteCostApportDetailByIds(Long[] ids) {
        return costApportDetailMapper.deleteCostApportDetailByIds(ids);
    }

    /**
     * 删除分摊明细信息
     * 
     * @param id 分摊明细ID
     * @return 结果
     */
    @Override
    public int deleteCostApportDetailById(Long id) {
        return costApportDetailMapper.deleteCostApportDetailById(id);
    }

    /**
     * 删除分摊明细
     * 
     * @param code 分摊明细code
     * @return 结果
     */
    @Override
    public int deleteCostApportDetailByCode(CostApportDetail costApportDetail){
    	return costApportDetailMapper.deleteCostApportDetailByCode(costApportDetail);
    }
}
