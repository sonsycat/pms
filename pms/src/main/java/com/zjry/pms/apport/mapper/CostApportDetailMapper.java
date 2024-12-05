package com.zjry.pms.apport.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.apport.domain.CostApportDetail;

/**
 * 分摊明细Mapper接口
 * 
 * @author yzh
 * @date 2022-05-17
 */
public interface CostApportDetailMapper extends BaseMapper<CostApportDetail> {
    /**
     * 查询分摊明细
     * 
     * @param id 分摊明细ID
     * @return 分摊明细
     */
    public CostApportDetail selectCostApportDetailById(Long id);
    
    /**
     * 查询科室类型
     * 
     * @param id 分摊明细ID
     * @return 分摊明细
     */
    public CostApportDetail selectDeptRoomById(Long id);
    
    /**
     * 查询科室类型
     * 
     * @param id 分摊明细ID
     * @return 分摊明细
     */
    public CostApportDetail selectDeptNameById(Long id);
    
    
    /**
     * 查询分摊明细
     * 
     * @param id 分摊明细ID
     * @return 分摊明细
     */
    public CostApportDetail selectCostApportFtje(CostApportDetail costApportDetail);
    
    /**
     * 查询分摊明细
     * 
     * @param id 分摊明细ID
     * @return 分摊明细
     */
    public CostApportDetail selectCostApportZjcb(CostApportDetail costApportDetail);

    /**
     * 查询分摊明细列表
     * 
     * @param costApportDetail 分摊明细
     * @return 分摊明细集合
     */
    public List<CostApportDetail> selectCostApportDetailList(CostApportDetail costApportDetail);
    
    /**
     * 查询分摊明细列表
     * 
     * @param costApportDetail 分摊明细
     * @return 分摊明细集合
     */
    public List<CostApportDetail> selectCostApportDetailLists(CostApportDetail costApportDetail);
    
    /**
     * 查询按科室编码分组
     * 
     * @param costApportDetail 分摊明细
     * @return 查询按科室编码分组
     */
    public List<CostApportDetail> selectCostApportDetailListGroupDept(CostApportDetail costApportDetail);
    
    

    /**
     * 新增分摊明细
     * 
     * @param costApportDetail 分摊明细
     * @return 结果
     */
    public int insertCostApportDetail(CostApportDetail costApportDetail);

    /**
     * 修改分摊明细
     * 
     * @param costApportDetail 分摊明细
     * @return 结果
     */
    public int updateCostApportDetail(CostApportDetail costApportDetail);

    /**
     * 删除分摊明细
     * 
     * @param code 分摊明细code
     * @return 结果
     */
    public int deleteCostApportDetailByCode(CostApportDetail costApportDetail);
    
    /**
     * 删除分摊明细
     * 
     * @param id 分摊明细ID
     * @return 结果
     */
    public int deleteCostApportDetailById(Long id);

    /**
     * 批量删除分摊明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCostApportDetailByIds(Long[] ids);
}
