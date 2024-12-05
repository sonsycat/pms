package com.zjry.pms.bonus.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.bonus.domain.PmsIndex;

/**
 * 指标配置维护Service接口
 * 
 * @author zjry
 * @date 2022-10-09
 */
public interface IPmsIndexService extends IService<PmsIndex> {
    /**
     * 查询指标配置维护
     * 
     * @param id 指标配置维护ID
     * @return 指标配置维护
     */
    public PmsIndex selectPmsIndexById(Long id);

    /**
     * 查询指标配置维护列表
     * 
     * @param pmsIndex 指标配置维护
     * @return 指标配置维护集合
     */
    public List<PmsIndex> selectPmsIndexList(PmsIndex pmsIndex);

    /**
     * 新增指标配置维护
     * 
     * @param pmsIndex 指标配置维护
     * @return 结果
     */
    public int insertPmsIndex(PmsIndex pmsIndex);

    /**
     * 修改指标配置维护
     * 
     * @param pmsIndex 指标配置维护
     * @return 结果
     */
    public int updatePmsIndex(PmsIndex pmsIndex);

    /**
     * 批量删除指标配置维护
     * 
     * @param ids 需要删除的指标配置维护ID
     * @return 结果
     */
    public int deletePmsIndexByIds(Long[] ids);

    /**
     * 删除指标配置维护信息
     * 
     * @param id 指标配置维护ID
     * @return 结果
     */
    public int deletePmsIndexById(Long id);
}
