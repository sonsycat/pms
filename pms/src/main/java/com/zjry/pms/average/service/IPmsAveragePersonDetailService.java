package com.zjry.pms.average.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.average.domain.PmsAveragePersonDetail;
import com.zjry.pms.average.domain.PmsAveragePersonDetailList;

/**
 * 平均奖科人数明细Service接口
 * 
 * @author zwf
 * @date 2022-09-27
 */
public interface IPmsAveragePersonDetailService extends IService<PmsAveragePersonDetail> {
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
     * 批量删除平均奖科人数明细
     * 
     * @param ids 需要删除的平均奖科人数明细ID
     * @return 结果
     */
    public int deletePmsAveragePersonDetailByIds(Long[] ids);

    /**
     * 删除平均奖科人数明细信息
     * 
     * @param id 平均奖科人数明细ID
     * @return 结果
     */
    public int deletePmsAveragePersonDetailById(Long id);

    int savePmsAveragePersonDetail(PmsAveragePersonDetailList data);

    void copyData(PmsAveragePersonDetail pmsAveragePersonDetail);
}
