package com.zjry.pms.average.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.average.domain.PmsAveragePersonDetail;

/**
 * 平均奖科人数明细Mapper接口
 * 
 * @author zwf
 * @date 2022-09-27
 */
public interface PmsAveragePersonDetailMapper extends BaseMapper<PmsAveragePersonDetail> {
    /**
     * 查询平均奖科人数明细
     * 
     * @param id 平均奖科人数明细ID
     * @return 平均奖科人数明细
     */
    public PmsAveragePersonDetail selectPmsAveragePersonDetailById(Long id);

    /**
     * 查询平均奖科人数明细列表
     * 
     * @param pmsAveragePersonDetail 平均奖科人数明细
     * @return 平均奖科人数明细集合
     */
    public List<PmsAveragePersonDetail> selectPmsAveragePersonDetailList(PmsAveragePersonDetail pmsAveragePersonDetail);

    /**
     * 新增平均奖科人数明细
     * 
     * @param pmsAveragePersonDetail 平均奖科人数明细
     * @return 结果
     */
    public int insertPmsAveragePersonDetail(PmsAveragePersonDetail pmsAveragePersonDetail);

    /**
     * 修改平均奖科人数明细
     * 
     * @param pmsAveragePersonDetail 平均奖科人数明细
     * @return 结果
     */
    public int updatePmsAveragePersonDetail(PmsAveragePersonDetail pmsAveragePersonDetail);

    /**
     * 删除平均奖科人数明细
     * 
     * @param id 平均奖科人数明细ID
     * @return 结果
     */
    public int deletePmsAveragePersonDetailById(Long id);

    /**
     * 批量删除平均奖科人数明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsAveragePersonDetailByIds(Long[] ids);

    int deleteByAvgCode(PmsAveragePersonDetail pmsAveragePersonDetail);

    int insertBatch(List<PmsAveragePersonDetail> pmsAveragePersonDetailList);

    void copyData(PmsAveragePersonDetail pmsAveragePersonDetail);
}
