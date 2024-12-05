package com.zjry.pms.second.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.evaluate.domain.PmsEvaluateIndexDto;
import com.zjry.pms.second.domain.PmsSecondDistributionDetail;

/**
 * 二次分配指标集明细Service接口
 * 
 * @author maozt
 * @date 2022-09-26
 */
public interface IPmsSecondDistributionDetailService extends IService<PmsSecondDistributionDetail> {
    /**
     * 查询二次分配指标集明细
     * 
     * @param id 二次分配指标集明细ID
     * @return 二次分配指标集明细
     */
    public PmsSecondDistributionDetail selectPmsSecondDistributionDetailById(Long id);

    /**
     * 查询二次分配指标集明细列表
     * 
     * @param pmsSecondDistributionDetail 二次分配指标集明细
     * @return 二次分配指标集明细集合
     */
    public List<PmsSecondDistributionDetail> selectPmsSecondDistributionDetailList(PmsSecondDistributionDetail pmsSecondDistributionDetail);

    /**
     * 查询二次分配指标集明细列表
     *
     * @param pmsSecondDistributionDetail 二次分配指标集明细
     * @return 二次分配指标集明细集合
     */
    public List<PmsSecondDistributionDetail> getDistributionEvaluate(PmsSecondDistributionDetail pmsSecondDistributionDetail);

    /**
     * 新增二次分配指标集明细
     * 
     * @param pmsSecondDistributionDetail 二次分配指标集明细
     * @return 结果
     */
    public int insertPmsSecondDistributionDetail(PmsSecondDistributionDetail pmsSecondDistributionDetail);

    /**
     * 修改二次分配指标集明细
     * 
     * @param pmsSecondDistributionDetail 二次分配指标集明细
     * @return 结果
     */
    public int updatePmsSecondDistributionDetail(PmsSecondDistributionDetail pmsSecondDistributionDetail);

    /**
     * 批量删除二次分配指标集明细
     * 
     * @param ids 需要删除的二次分配指标集明细ID
     * @return 结果
     */
    public int deletePmsSecondDistributionDetailByIds(Long[] ids);

    /**
     * 删除二次分配指标集明细信息
     * 
     * @param id 二次分配指标集明细ID
     * @return 结果
     */
    public int deletePmsSecondDistributionDetailById(Long id);

    /**
     * 删除二次分配指标集明细信息
     *
     * @param id 二次分配指标集明细ID
     * @return 结果
     */
    public int deletePmsSecondDistributionDetailByDisCode(String disCode);

    /**
     * 获取医生对应考核指标结果集
     * @return
     */
    List<PmsEvaluateIndexDto> selectDetailListForDoctor(Long compCode, String nowDate);
}
