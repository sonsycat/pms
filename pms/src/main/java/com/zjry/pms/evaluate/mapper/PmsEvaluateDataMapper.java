package com.zjry.pms.evaluate.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.bonus.domain.PmsEvaluateDataTemp;
import com.zjry.pms.evaluate.domain.PmsEvaluateData;
import com.zjry.pms.evaluate.domain.PmsEvaluateDataAccountDept;
import com.zjry.pms.evaluate.domain.PmsEvaluatePersonNotBonus;
import org.apache.ibatis.annotations.Param;

/**
 * 指标数据值（外部获取）Mapper接口
 *
 * @author zjry
 * @date 2022-10-17
 */
public interface PmsEvaluateDataMapper extends BaseMapper<PmsEvaluateData> {

    /**
     * 查询指标数据值（外部获取）
     *
     * @param id 指标数据值（外部获取）ID
     * @return 指标数据值（外部获取）
     */
    PmsEvaluateData selectPmsEvaluateDataById(Long id);

    /**
     * 查询指标数据值（外部获取）列表
     *
     * @param pmsEvaluateData 指标数据值（外部获取）
     * @return 指标数据值（外部获取）集合
     */
    List<PmsEvaluateData> selectPmsEvaluateDataList(PmsEvaluateData pmsEvaluateData);

    /**
     * 新增指标数据值（外部获取）
     *
     * @param pmsEvaluateData 指标数据值（外部获取）
     * @return 结果
     */
    int insertPmsEvaluateData(PmsEvaluateData pmsEvaluateData);

    /**
     * 修改指标数据值（外部获取）
     *
     * @param pmsEvaluateData 指标数据值（外部获取）
     * @return 结果
     */
    int updatePmsEvaluateData(PmsEvaluateData pmsEvaluateData);

    /**
     * 删除指标数据值（外部获取）
     *
     * @param id 指标数据值（外部获取）ID
     * @return 结果
     */
    int deletePmsEvaluateDataById(Long id);

    /**
     * 批量删除指标数据值（外部获取）
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deletePmsEvaluateDataByIds(Long[] ids);

    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    int insertBatch(List<PmsEvaluateData> list);

    int deleteBatch(PmsEvaluateData pmsEvaluateData);

    /**
     * 获取科室对应奖金指标奖金值
     *
     * @param pmsEvaluateData 日期
     * @return 结果
     */
    List<PmsEvaluateDataAccountDept> selectAccountDeptEvaluateDataList(PmsEvaluateData pmsEvaluateData);

    /**
     * 获取科室对应奖金指标
     *
     * @param pmsEvaluateData 日期
     * @return 结果
     */
    List<PmsEvaluateDataAccountDept> selectAccountDeptIndex(PmsEvaluateData pmsEvaluateData);

    /**
     * 获取科室对应人员奖金指标
     *
     * @param pmsEvaluateData 日期 科室编码
     * @return 结果
     */
    List<PmsEvaluateDataAccountDept> selectAccountPersonIndex(PmsEvaluateData pmsEvaluateData);

    /**
     * 获取科室对应人员奖金指标奖金值
     *
     * @param pmsEvaluateData 日期 科室编码
     * @return 结果
     */
    List<PmsEvaluateDataAccountDept> selectAccountPersonEvaluateDataList(PmsEvaluateData pmsEvaluateData);

    /**
     * 获取奖金
     *
     * @param pmsEvaluateData
     * @return
     */
    List<PmsEvaluateData> selectBonusList(PmsEvaluateData pmsEvaluateData);

    /**
     * 批量插入
     * @param addList
     */
    void insertBatch2(@Param("list") List<PmsEvaluateDataTemp> addList);

    /**
     * 批量修改
     * @param updateList
     */
    void updateBatch(@Param("list") List<PmsEvaluateData> updateList);

    /**
     * 获取科室对应人员奖金指标奖金值临时表数据
     * @param pmsEvaluateData 日期 科室编码
     * @return 结果
     */
    List<PmsEvaluateDataAccountDept> selectAccountPersonEvaluateDataTempList(PmsEvaluateData pmsEvaluateData);

    /**
     * 查询没有奖金数据的人员列表
     * @param pmsEvaluateData
     * @return 结果
     */
    List<PmsEvaluatePersonNotBonus> selectDoctorListNotBonus(PmsEvaluateData pmsEvaluateData);

    /**
     * 查询没有奖金数据的人员列表（原始表）
     * @param pmsEvaluateData
     * @return 结果
     */
    List<PmsEvaluatePersonNotBonus> selectDoctorListNotBonusData(PmsEvaluateData pmsEvaluateData);

    /**
     * 获取科室奖金
     * @param pmsEvaluateData
     * @return
     */
    List<PmsEvaluateData> selectDeptBonusList(PmsEvaluateData pmsEvaluateData);
}
