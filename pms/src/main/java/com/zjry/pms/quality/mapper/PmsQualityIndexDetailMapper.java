package com.zjry.pms.quality.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.quality.domain.PmsQualityIndexDetail;

/**
 * 质量考评指标明细Mapper接口
 * 
 * @author yzh
 * @date 2022-07-05
 */
public interface PmsQualityIndexDetailMapper extends BaseMapper<PmsQualityIndexDetail> {
    /**
     * 查询质量考评指标明细
     * 
     * @param id 质量考评指标明细ID
     * @return 质量考评指标明细
     */
    public PmsQualityIndexDetail selectPmsQualityIndexDetailById(Long id);

    /**
     * 查询质量考评指标明细列表
     * 
     * @param pmsQualityIndexDetail 质量考评指标明细
     * @return 质量考评指标明细集合
     */
    public List<PmsQualityIndexDetail> selectPmsQualityIndexDetailList(PmsQualityIndexDetail pmsQualityIndexDetail);

    /**
     * 新增质量考评指标明细
     * 
     * @param pmsQualityIndexDetail 质量考评指标明细
     * @return 结果
     */
    public int insertPmsQualityIndexDetail(PmsQualityIndexDetail pmsQualityIndexDetail);

    /**
     * 修改质量考评指标明细
     * 
     * @param pmsQualityIndexDetail 质量考评指标明细
     * @return 结果
     */
    public int updatePmsQualityIndexDetail(PmsQualityIndexDetail pmsQualityIndexDetail);

    /**
     * 删除质量考评指标明细
     * 
     * @param id 质量考评指标明细ID
     * @return 结果
     */
    public int deletePmsQualityIndexDetailById(Long id);

    /**
     * 批量删除质量考评指标明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsQualityIndexDetailByIds(Long[] ids);
}
