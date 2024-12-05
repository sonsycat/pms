package com.zjry.pms.average.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.average.domain.PmsAveragePerson;

/**
 * 平均奖科人数Service接口
 * 
 * @author zwf
 * @date 2022-09-27
 */
public interface IPmsAveragePersonService extends IService<PmsAveragePerson> {
    /**
     * 查询平均奖科人数
     * 
     * @param id 平均奖科人数ID
     * @return 平均奖科人数
     */
    public PmsAveragePerson selectPmsAveragePersonById(Long id);

    /**
     * 查询平均奖科人数列表
     * 
     * @param pmsAveragePerson 平均奖科人数
     * @return 平均奖科人数集合
     */
    public List<PmsAveragePerson> selectPmsAveragePersonList(PmsAveragePerson pmsAveragePerson);

    /**
     * 新增平均奖科人数
     * 
     * @param pmsAveragePerson 平均奖科人数
     * @return 结果
     */
    public int insertPmsAveragePerson(PmsAveragePerson pmsAveragePerson);

    /**
     * 修改平均奖科人数
     * 
     * @param pmsAveragePerson 平均奖科人数
     * @return 结果
     */
    public int updatePmsAveragePerson(PmsAveragePerson pmsAveragePerson);

    /**
     * 批量删除平均奖科人数
     * 
     * @param ids 需要删除的平均奖科人数ID
     * @return 结果
     */
    public int deletePmsAveragePersonByIds(Long[] ids);

    /**
     * 删除平均奖科人数信息
     * 
     * @param id 平均奖科人数ID
     * @return 结果
     */
    public int deletePmsAveragePersonById(Long id);

    /**
     * 查询平均奖科人数最新日期
     * @return 结果
     */
    PmsAveragePerson selectAveragePersonNewDate();

    List<PmsAveragePerson> selectPmsAveragePersonByDate(PmsAveragePerson pmsAveragePerson);

    void copyData(PmsAveragePerson pmsAveragePerson);
}
