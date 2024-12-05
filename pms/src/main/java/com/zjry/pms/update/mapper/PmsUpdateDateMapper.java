package com.zjry.pms.update.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.update.domain.PmsUpdateDate;

/**
 * 更新日期Mapper接口
 * 
 * @author maozt
 * @date 2022-07-06
 */
public interface PmsUpdateDateMapper extends BaseMapper<PmsUpdateDate> {
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
    public int insertPmsUpdateDate(PmsUpdateDate pmsUpdateDate);

    /**
     * 修改更新日期
     * 
     * @param pmsUpdateDate 更新日期
     * @return 结果
     */
    public int updatePmsUpdateDate(PmsUpdateDate pmsUpdateDate);

    /**
     * 删除更新日期
     * 
     * @param id 更新日期ID
     * @return 结果
     */
    public int deletePmsUpdateDateById(Long id);

    /**
     * 批量删除更新日期
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsUpdateDateByIds(Long[] ids);
}
