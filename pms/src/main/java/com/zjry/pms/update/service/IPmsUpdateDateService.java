package com.zjry.pms.update.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.update.domain.PmsUpdateDate;

/**
 * 更新日期Service接口
 * 
 * @author maozt
 * @date 2022-07-06
 */
public interface IPmsUpdateDateService extends IService<PmsUpdateDate> {
    /**
     * 查询更新日期
     * 
     * @param id 更新日期ID
     * @return 更新日期
     */
    public PmsUpdateDate selectPmsUpdateDateById(Long id);

    /**
     * 查询更新日期列表
     * 
     * @param pmsUpdateDate 更新日期
     * @return 更新日期集合
     */
    public List<PmsUpdateDate> selectPmsUpdateDateList(PmsUpdateDate pmsUpdateDate);

    /**
     * 新增更新日期
     * 
     * @param pmsUpdateDate 更新日期
     * @return 结果
     */
    public int insertPmsUpdateDate(PmsUpdateDate pmsUpdateDate) throws Exception;

    /**
     * 修改更新日期
     * 
     * @param pmsUpdateDate 更新日期
     * @return 结果
     */
    public int updatePmsUpdateDate(PmsUpdateDate pmsUpdateDate);

    /**
     * 批量删除更新日期
     * 
     * @param ids 需要删除的更新日期ID
     * @return 结果
     */
    public int deletePmsUpdateDateByIds(Long[] ids);

    /**
     * 删除更新日期信息
     * 
     * @param id 更新日期ID
     * @return 结果
     */
    public int deletePmsUpdateDateById(Long id);
}
