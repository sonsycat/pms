package com.zjry.pms.apport.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.apport.domain.CostApport;

/**
 * 分摊计算Service接口
 * 
 * @author yzh
 * @date 2022-05-17
 */
public interface ICostApportService extends IService<CostApport> {
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
     * 分摊计算
     * 
     * @param costApport 分摊计算
     * @return 结果
     */
    public void ftjs(CostApport costApport) throws Exception;
    
    /**
     * 一级分摊计算
     * 
     * @param costApport 分摊计算
     * @return 结果
     */
    public void yjft(CostApport costApport) throws Exception;
    
    /**
     * 二级分摊计算
     * 
     * @param costApport 分摊计算
     * @return 结果
     */
    public void ejft(CostApport costApport) throws Exception;
    
    /**
     * 三级分摊计算
     * 
     * @param costApport 分摊计算
     * @return 结果
     */
    public void sjft(CostApport costApport) throws Exception;

    /**
     * 修改分摊计算
     * 
     * @param costApport 分摊计算
     * @return 结果
     */
    public int updateCostApport(CostApport costApport);

    /**
     * 批量删除分摊计算
     * 
     * @param ids 需要删除的分摊计算ID
     * @return 结果
     */
    public int deleteCostApportByIds(Long[] ids);

    /**
     * 删除分摊计算信息
     * 
     * @param id 分摊计算ID
     * @return 结果
     */
    public int deleteCostApportById(Long id);
    
    
    /**
     * 验证
     * @param costApport
     * @return
     */
    public String verification(CostApport costApport);
    /**
     * 验证
     * @param costApport
     * @return
     */
   // public String verification1(CostApport costApport);
    /**
     * 验证
     * @param costApport
     * @return
     */
    public String verification2(CostApport costApport);
    /**
     * 验证
     * @param costApport
     * @return
     */
    public String verification3(CostApport costApport);
}
