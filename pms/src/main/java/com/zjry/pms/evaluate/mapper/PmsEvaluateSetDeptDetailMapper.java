package com.zjry.pms.evaluate.mapper;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.evaluate.domain.EvaluateSetDeptDetailDto;
import com.zjry.pms.evaluate.domain.PmsEvaluateSetDeptDetail;
import org.apache.ibatis.annotations.Param;

/**
 * 指标集与科室管理明细Mapper接口
 *
 * @author mk
 * @date 2022-08-09
 */
public interface PmsEvaluateSetDeptDetailMapper extends BaseMapper<PmsEvaluateSetDeptDetail> {

    /**
     * 查询指标集与科室管理明细
     *
     * @param id 指标集与科室管理明细ID
     * @return 指标集与科室管理明细
     */
    public PmsEvaluateSetDeptDetail selectPmsEvaluateSetDeptDetailById(Long id);

    /**
     * 查询指标集与科室管理明细列表
     *
     * @param pmsEvaluateSetDeptDetail 指标集与科室管理明细
     * @return 指标集与科室管理明细集合
     */
    public List<PmsEvaluateSetDeptDetail> selectPmsEvaluateSetDeptDetailList(PmsEvaluateSetDeptDetail pmsEvaluateSetDeptDetail);

    /**
     * 单指标多科室查询
     */
    public List<PmsEvaluateSetDeptDetail> selectPmsForDKSN(PmsEvaluateSetDeptDetail pmsEvaluateSetDeptDetail);

    /**
     * 新增指标集与科室管理明细
     *
     * @param pmsEvaluateSetDeptDetail 指标集与科室管理明细
     * @return 结果
     */
    public int insertPmsEvaluateSetDeptDetail(PmsEvaluateSetDeptDetail pmsEvaluateSetDeptDetail);

    /**
     * 修改指标集与科室管理明细
     *
     * @param pmsEvaluateSetDeptDetail 指标集与科室管理明细
     * @return 结果
     */
    public int updatePmsEvaluateSetDeptDetail(PmsEvaluateSetDeptDetail pmsEvaluateSetDeptDetail);

    /**
     * 删除指标集与科室管理明细
     *
     * @param id 指标集与科室管理明细ID
     * @return 结果
     */
    public int deletePmsEvaluateSetDeptDetailById(Long id);

    /**
     * 批量删除指标集与科室管理明细
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsEvaluateSetDeptDetailByIds(Long[] ids);

    void deleteByDataAndCode(@Param("compCode") Long compCode, @Param("nowDate") Date nowDate, @Param("setDeptCode") String setDeptCode);

    /**
     * 查询科室指标计算参数
     *
     * @param date
     * @return
     */
    List<EvaluateSetDeptDetailDto> getSetDeptDetailByMonth(@Param("date") Date date);

    /**
     * 查询科室指标计算参数
     *
     * @param date
     * @return
     */
    List<EvaluateSetDeptDetailDto> getSetDeptDetailList(@Param("date") Date date);
}
