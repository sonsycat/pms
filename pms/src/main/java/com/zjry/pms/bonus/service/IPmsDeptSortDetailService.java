package com.zjry.pms.bonus.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.bonus.domain.PmsDeptSortDetail;

/**
 * 核算单位类别Service接口
 * 
 * @author zjry
 * @date 2022-09-27
 */
public interface IPmsDeptSortDetailService extends IService<PmsDeptSortDetail> {
    /**
     * 查询核算单位类别
     * 
     * @param id 核算单位类别ID
     * @return 核算单位类别
     */
    PmsDeptSortDetail selectPmsDeptSortDetailById(Long id);

    /**
     * 查询核算单位类别列表
     * 
     * @param pmsDeptSortDetail 核算单位类别
     * @return 核算单位类别集合
     */
    List<PmsDeptSortDetail> selectPmsDeptSortDetailList(PmsDeptSortDetail pmsDeptSortDetail);

    /**
     * 新增核算单位类别
     * 
     * @param pmsDeptSortDetail 核算单位类别
     * @return 结果
     */
    int insertPmsDeptSortDetail(PmsDeptSortDetail pmsDeptSortDetail);

    /**
     * 修改核算单位类别
     * 
     * @param pmsDeptSortDetail 核算单位类别
     * @return 结果
     */
    int updatePmsDeptSortDetail(PmsDeptSortDetail pmsDeptSortDetail);

    /**
     * 批量删除核算单位类别
     * 
     * @param ids 需要删除的核算单位类别ID
     * @return 结果
     */
    int deletePmsDeptSortDetailByIds(Long[] ids);

    /**
     * 删除核算单位类别信息
     * 
     * @param id 核算单位类别ID
     * @return 结果
     */
    int deletePmsDeptSortDetailById(Long id);
}
