package com.zjry.pms.bonus.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.bonus.domain.PmsEvaluateDataTemp;

/**
 * 指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)Service接口
 * 
 * @author zjry
 * @date 2022-10-25
 */
public interface IPmsEvaluateDataTempService extends IService<PmsEvaluateDataTemp> {
    /**
     * 查询指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)
     * 
     * @param id 指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)ID
     * @return 指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)
     */
    public PmsEvaluateDataTemp selectPmsEvaluateDataTempById(Long id);

    /**
     * 查询指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)列表
     * 
     * @param pmsEvaluateDataTemp 指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)
     * @return 指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)集合
     */
    public List<PmsEvaluateDataTemp> selectPmsEvaluateDataTempList(PmsEvaluateDataTemp pmsEvaluateDataTemp);

    /**
     * 新增指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)
     * 
     * @param pmsEvaluateDataTemp 指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)
     * @return 结果
     */
    public int insertPmsEvaluateDataTemp(PmsEvaluateDataTemp pmsEvaluateDataTemp);

    /**
     * 修改指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)
     * 
     * @param pmsEvaluateDataTemp 指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)
     * @return 结果
     */
    public int updatePmsEvaluateDataTemp(PmsEvaluateDataTemp pmsEvaluateDataTemp);

    /**
     * 批量删除指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)
     * 
     * @param ids 需要删除的指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)ID
     * @return 结果
     */
    public int deletePmsEvaluateDataTempByIds(Long[] ids);

    /**
     * 删除指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)信息
     * 
     * @param id 指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)ID
     * @return 结果
     */
    public int deletePmsEvaluateDataTempById(Long id);
}
