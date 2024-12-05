package com.zjry.pms.bonus.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.bonus.domain.PmsDeptSort;

/**
 * 核算单位类别设置Mapper接口
 * 
 * @author zjry
 * @date 2022-09-27
 */
public interface PmsDeptSortMapper extends BaseMapper<PmsDeptSort> {
    /**
     * 查询核算单位类别设置
     * 
     * @param id 核算单位类别设置ID
     * @return 核算单位类别设置
     */
    PmsDeptSort selectPmsDeptSortById(Long id);

    /**
     * 查询核算单位类别设置列表
     * 
     * @param pmsDeptSort 核算单位类别设置
     * @return 核算单位类别设置集合
     */
    List<PmsDeptSort> selectPmsDeptSortList(PmsDeptSort pmsDeptSort);

    /**
     * 新增核算单位类别设置
     * 
     * @param pmsDeptSort 核算单位类别设置
     * @return 结果
     */
    int insertPmsDeptSort(PmsDeptSort pmsDeptSort);

    /**
     * 修改核算单位类别设置
     * 
     * @param pmsDeptSort 核算单位类别设置
     * @return 结果
     */
    int updatePmsDeptSort(PmsDeptSort pmsDeptSort);

    /**
     * 删除核算单位类别设置
     * 
     * @param id 核算单位类别设置ID
     * @return 结果
     */
    int deletePmsDeptSortById(Long id);

    /**
     * 批量删除核算单位类别设置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deletePmsDeptSortByIds(Long[] ids);

    /**
     * 单表查询列表数据
     * @param pmsDeptSort
     * @return
     */
    List<PmsDeptSort> queryList(PmsDeptSort pmsDeptSort);

    void copyData(PmsDeptSort pmsDeptSort);

    /**
     * 批量删除
     * @return 结果
     */
    int deleteBatch(PmsDeptSort pmsDeptSort);

    /**
     * 批量插入
     * @return 结果
     */
    int insertBatch(List<PmsDeptSort> list);
}
