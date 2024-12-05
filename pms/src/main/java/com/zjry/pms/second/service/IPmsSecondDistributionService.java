package com.zjry.pms.second.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.second.domain.PmsSecondDistribution;

/**
 * 二次分配指标集设置Service接口
 * 
 * @author maozt
 * @date 2022-09-26
 */
public interface IPmsSecondDistributionService extends IService<PmsSecondDistribution> {
    /**
     * 查询二次分配指标集设置
     * 
     * @param id 二次分配指标集设置ID
     * @return 二次分配指标集设置
     */
    public PmsSecondDistribution selectPmsSecondDistributionById(Long id);

    /**
     * 查询二次分配指标集设置列表
     * 
     * @param pmsSecondDistribution 二次分配指标集设置
     * @return 二次分配指标集设置集合
     */
    public List<PmsSecondDistribution> selectPmsSecondDistributionList(PmsSecondDistribution pmsSecondDistribution);

    /**
     * 新增二次分配指标集设置
     * 
     * @param pmsSecondDistribution 二次分配指标集设置
     * @return 结果
     */
    public int insertPmsSecondDistribution(PmsSecondDistribution pmsSecondDistribution) throws Exception;

    /**
     * 修改二次分配指标集设置
     * 
     * @param pmsSecondDistribution 二次分配指标集设置
     * @return 结果
     */
    public int updatePmsSecondDistribution(PmsSecondDistribution pmsSecondDistribution) throws Exception;

    /**
     * 批量删除二次分配指标集设置
     * 
     * @param ids 需要删除的二次分配指标集设置ID
     * @return 结果
     */
    public int deletePmsSecondDistributionByIds(Long[] ids);

    /**
     * 删除二次分配指标集设置信息
     * 
     * @param id 二次分配指标集设置ID
     * @return 结果
     */
    public int deletePmsSecondDistributionById(Long id);
}
