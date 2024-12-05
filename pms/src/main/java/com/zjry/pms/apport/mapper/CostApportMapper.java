package com.zjry.pms.apport.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.apport.domain.CostApport;

/**
 * 分摊计算Mapper接口
 * 
 * @author yzh
 * @date 2022-05-17
 */
public interface CostApportMapper extends BaseMapper<CostApport> {
    /**
     * 查询分摊计算
     * 
     * @param id 分摊计算ID
     * @return 分摊计算
     */
    public CostApport selectCostApportById(Long id);

    /**
     * 查询分摊计算列表
     * 
     * @param costApport 分摊计算
     * @return 分摊计算集合
     */
    public List<CostApport> selectCostApportList(CostApport costApport);

    /**
     * 查询分摊计算列表
     *
     * @param costApport 分摊计算
     * @return 分摊计算集合
     */
    public List<CostApport> selectCostApportListOrderByNowDate(CostApport costApport);

    /**
     * 新增分摊计算
     * 
     * @param costApport 分摊计算
     * @return 结果
     */
    public int insertCostApport(CostApport costApport);

    /**
     * 修改分摊计算
     * 
     * @param costApport 分摊计算
     * @return 结果
     */
    public int updateCostApport(CostApport costApport);

    /**
     * 删除分摊计算
     * 
     * @param id 分摊计算ID
     * @return 结果
     */
    public int deleteCostApportById(Long id);

    /**
     * 批量删除分摊计算
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCostApportByIds(Long[] ids);
}
