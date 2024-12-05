package com.zjry.pms.quality.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.quality.domain.PmsQualityIndex;

/**
 * 质量考评指标Service接口
 * 
 * @author yzh
 * @date 2022-07-05
 */
public interface IPmsQualityIndexService extends IService<PmsQualityIndex> {
    /**
     * 查询质量考评指标
     * 
     * @param id 质量考评指标ID
     * @return 质量考评指标
     */
    public PmsQualityIndex selectPmsQualityIndexById(Long id);

    /**
     * 查询质量考评指标列表
     * 
     * @param pmsQualityIndex 质量考评指标
     * @return 质量考评指标集合
     */
    public List<PmsQualityIndex> selectPmsQualityIndexList(PmsQualityIndex pmsQualityIndex);

    /**
     * 新增质量考评指标
     * 
     * @param pmsQualityIndex 质量考评指标
     * @return 结果
     */
    public int insertPmsQualityIndex(PmsQualityIndex pmsQualityIndex);

    /**
     * 修改质量考评指标
     * 
     * @param pmsQualityIndex 质量考评指标
     * @return 结果
     */
    public int updatePmsQualityIndex(PmsQualityIndex pmsQualityIndex);

    /**
     * 批量删除质量考评指标
     * 
     * @param ids 需要删除的质量考评指标ID
     * @return 结果
     */
    public int deletePmsQualityIndexByIds(Long[] ids);

    /**
     * 删除质量考评指标信息
     * 
     * @param id 质量考评指标ID
     * @return 结果
     */
    public int deletePmsQualityIndexById(Long id);
}
