package com.zjry.pms.bonus.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.bonus.domain.PmsDeptSortDetail;

/**
 * 核算单位类别Mapper接口
 * 
 * @author zjry
 * @date 2022-09-27
 */
public interface PmsDeptSortDetailMapper extends BaseMapper<PmsDeptSortDetail> {
    /**
     * 查询核算单位类别
     * 
     * @param id 核算单位类别ID
     * @return 核算单位类别
     */
    public PmsDeptSortDetail selectPmsDeptSortDetailById(Long id);

    /**
     * 查询核算单位类别列表
     * 
     * @param pmsDeptSortDetail 核算单位类别
     * @return 核算单位类别集合
     */
    public List<PmsDeptSortDetail> selectPmsDeptSortDetailList(PmsDeptSortDetail pmsDeptSortDetail);

    /**
     * 新增核算单位类别
     * 
     * @param pmsDeptSortDetail 核算单位类别
     * @return 结果
     */
    public int insertPmsDeptSortDetail(PmsDeptSortDetail pmsDeptSortDetail);

    /**
     * 修改核算单位类别
     * 
     * @param pmsDeptSortDetail 核算单位类别
     * @return 结果
     */
    public int updatePmsDeptSortDetail(PmsDeptSortDetail pmsDeptSortDetail);

    /**
     * 删除核算单位类别
     * 
     * @param id 核算单位类别ID
     * @return 结果
     */
    public int deletePmsDeptSortDetailById(Long id);

    /**
     * 批量删除核算单位类别
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsDeptSortDetailByIds(Long[] ids);
}
