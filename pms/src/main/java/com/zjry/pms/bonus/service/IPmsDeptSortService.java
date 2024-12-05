package com.zjry.pms.bonus.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.bonus.domain.PmsDeptSort;
import com.zjry.pms.bonus.domain.PmsDeptSortDto;

/**
 * 核算单位类别设置Service接口
 * 
 * @author zjry
 * @date 2022-09-27
 */
public interface IPmsDeptSortService extends IService<PmsDeptSort> {
    /**
     * 查询核算单位类别设置
     * 
     * @param id 核算单位类别设置ID
     * @return 核算单位类别设置
     */
    public PmsDeptSort selectPmsDeptSortById(Long id);

    /**
     * 查询核算单位类别设置列表
     * 
     * @param pmsDeptSort 核算单位类别设置
     * @return 核算单位类别设置集合
     */
    public List<PmsDeptSort> selectPmsDeptSortList(PmsDeptSort pmsDeptSort);

    /**
     * 新增核算单位类别设置
     * 
     * @param pmsDeptSort 核算单位类别设置
     * @return 结果
     */
    public int insertPmsDeptSort(PmsDeptSort pmsDeptSort);

    /**
     * 修改核算单位类别设置
     * 
     * @param pmsDeptSort 核算单位类别设置
     * @return 结果
     */
    public int updatePmsDeptSort(PmsDeptSort pmsDeptSort);

    /**
     * 批量删除核算单位类别设置
     * 
     * @param ids 需要删除的核算单位类别设置ID
     * @return 结果
     */
    public int deletePmsDeptSortByIds(Long[] ids);

    /**
     * 删除核算单位类别设置信息
     * 
     * @param id 核算单位类别设置ID
     * @return 结果
     */
    public int deletePmsDeptSortById(Long id);

    /**
     * 单表查询列表数据
     * @param pmsDeptSort
     * @return
     */
    List<PmsDeptSort> queryList(PmsDeptSort pmsDeptSort);

    void copyData(PmsDeptSort pmsDeptSort);

    /**
     * 保存列表数据
     *
     * @param data 核算单位配置列表数据
     * @return 结果
     */
    int saveData(PmsDeptSortDto data);
}
