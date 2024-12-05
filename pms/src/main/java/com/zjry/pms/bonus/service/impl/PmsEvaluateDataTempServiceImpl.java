package com.zjry.pms.bonus.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.bonus.mapper.PmsEvaluateDataTempMapper;
import com.zjry.pms.bonus.domain.PmsEvaluateDataTemp;
import com.zjry.pms.bonus.service.IPmsEvaluateDataTempService;

/**
 * 指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)Service业务层处理
 * 
 * @author zjry
 * @date 2022-10-25
 */
@Service
public class PmsEvaluateDataTempServiceImpl extends ServiceImpl<PmsEvaluateDataTempMapper, PmsEvaluateDataTemp> implements IPmsEvaluateDataTempService {
    @Autowired
    private PmsEvaluateDataTempMapper pmsEvaluateDataTempMapper;

    /**
     * 查询指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)
     * 
     * @param id 指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)ID
     * @return 指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)
     */
    @Override
    public PmsEvaluateDataTemp selectPmsEvaluateDataTempById(Long id) {
        return pmsEvaluateDataTempMapper.selectPmsEvaluateDataTempById(id);
    }

    /**
     * 查询指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)列表
     * 
     * @param pmsEvaluateDataTemp 指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)
     * @return 指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)
     */
    @Override
    public List<PmsEvaluateDataTemp> selectPmsEvaluateDataTempList(PmsEvaluateDataTemp pmsEvaluateDataTemp) {
        return pmsEvaluateDataTempMapper.selectPmsEvaluateDataTempList(pmsEvaluateDataTemp);
    }

    /**
     * 新增指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)
     * 
     * @param pmsEvaluateDataTemp 指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)
     * @return 结果
     */
    @Override
    public int insertPmsEvaluateDataTemp(PmsEvaluateDataTemp pmsEvaluateDataTemp) {
    	pmsEvaluateDataTemp.preInsert();
        return pmsEvaluateDataTempMapper.insertPmsEvaluateDataTemp(pmsEvaluateDataTemp);
    }

    /**
     * 修改指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)
     * 
     * @param pmsEvaluateDataTemp 指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)
     * @return 结果
     */
    @Override
    public int updatePmsEvaluateDataTemp(PmsEvaluateDataTemp pmsEvaluateDataTemp) {
    	pmsEvaluateDataTemp.preUpdate();
        return pmsEvaluateDataTempMapper.updatePmsEvaluateDataTemp(pmsEvaluateDataTemp);
    }

    /**
     * 批量删除指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)
     * 
     * @param ids 需要删除的指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)ID
     * @return 结果
     */
    @Override
    public int deletePmsEvaluateDataTempByIds(Long[] ids) {
        return pmsEvaluateDataTempMapper.deletePmsEvaluateDataTempByIds(ids);
    }

    /**
     * 删除指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)信息
     * 
     * @param id 指标数据值零时(pms_bonus_person_audit通过后,写入pms_evaluate_data)ID
     * @return 结果
     */
    @Override
    public int deletePmsEvaluateDataTempById(Long id) {
        return pmsEvaluateDataTempMapper.deletePmsEvaluateDataTempById(id);
    }
}
