package com.zjry.pms.evaluate.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.evaluate.domain.PmsEvaluateData;
import com.zjry.pms.evaluate.domain.PmsEvaluateDataAccountDept;
import com.zjry.pms.evaluate.domain.PmsEvaluatePersonNotBonus;

/**
 * 指标数据值（外部获取）Service接口
 * 
 * @author zjry
 * @date 2022-10-17
 */
public interface IPmsEvaluateDataService extends IService<PmsEvaluateData> {
    /**
     * 查询指标数据值（外部获取）
     * 
     * @param id 指标数据值（外部获取）ID
     * @return 指标数据值（外部获取）
     */
    public PmsEvaluateData selectPmsEvaluateDataById(Long id);

    /**
     * 查询指标数据值（外部获取）列表
     * 
     * @param pmsEvaluateData 指标数据值（外部获取）
     * @return 指标数据值（外部获取）集合
     */
    public List<PmsEvaluateData> selectPmsEvaluateDataList(PmsEvaluateData pmsEvaluateData);

    /**
     * 新增指标数据值（外部获取）
     * 
     * @param pmsEvaluateData 指标数据值（外部获取）
     * @return 结果
     */
    public int insertPmsEvaluateData(PmsEvaluateData pmsEvaluateData);

    /**
     * 修改指标数据值（外部获取）
     * 
     * @param pmsEvaluateData 指标数据值（外部获取）
     * @return 结果
     */
    public int updatePmsEvaluateData(PmsEvaluateData pmsEvaluateData);

    /**
     * 批量删除指标数据值（外部获取）
     * 
     * @param ids 需要删除的指标数据值（外部获取）ID
     * @return 结果
     */
    public int deletePmsEvaluateDataByIds(Long[] ids);

    /**
     * 删除指标数据值（外部获取）信息
     * 
     * @param id 指标数据值（外部获取）ID
     * @return 结果
     */
    public int deletePmsEvaluateDataById(Long id);

    /**
     * 获取科室对应奖金指标奖金值
     * @param pmsEvaluateData 日期
     * @return 结果
     */
    List<JSONObject> selectAccountDeptEvaluateDataList(PmsEvaluateData pmsEvaluateData);

    /**
     * 获取科室对应奖金指标
     * @param pmsEvaluateData 日期
     * @return 结果
     */
    List<PmsEvaluateDataAccountDept> selectAccountDeptIndex(PmsEvaluateData pmsEvaluateData);

    /**
     * 获取科室对应人员奖金指标
     * @param pmsEvaluateData 日期 科室编码
     * @return 结果
     */
    List<PmsEvaluateDataAccountDept> selectAccountPersonIndex(PmsEvaluateData pmsEvaluateData);

    /**
     * 获取科室对应人员奖金指标奖金值
     * @param pmsEvaluateData 日期 科室编码
     * @return 结果
     */
    List<JSONObject> selectAccountPersonEvaluateDataList(PmsEvaluateData pmsEvaluateData);

    /**
     * 获取奖金
     * @param pmsEvaluateData
     * @return
     */
    List<PmsEvaluateData> selectBonusList(PmsEvaluateData pmsEvaluateData);

    /**
     * 查询没有奖金数据的人员列表
     * @param pmsEvaluateData
     * @return 结果
     */
    List<PmsEvaluatePersonNotBonus> selectDoctorListNotBonus(PmsEvaluateData pmsEvaluateData);

    /**
     * 获取科室奖金(按指标分组)
     * @param pmsEvaluateData
     * @return
     */
    List<PmsEvaluateData> selectDeptBonusList(PmsEvaluateData pmsEvaluateData);
}