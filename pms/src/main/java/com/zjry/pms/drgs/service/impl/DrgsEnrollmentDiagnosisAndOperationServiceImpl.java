package com.zjry.pms.drgs.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.drgs.mapper.DrgsEnrollmentDiagnosisAndOperationMapper;
import com.zjry.pms.drgs.domain.DrgsEnrollmentDiagnosisAndOperation;
import com.zjry.pms.drgs.service.IDrgsEnrollmentDiagnosisAndOperationService;

/**
 * ADRG入组诊断及操作Service业务层处理
 * 
 * @author zjry
 * @date 2023-03-20
 */
@Service
public class DrgsEnrollmentDiagnosisAndOperationServiceImpl extends ServiceImpl<DrgsEnrollmentDiagnosisAndOperationMapper, DrgsEnrollmentDiagnosisAndOperation> implements IDrgsEnrollmentDiagnosisAndOperationService {
    @Autowired
    private DrgsEnrollmentDiagnosisAndOperationMapper drgsEnrollmentDiagnosisAndOperationMapper;

    /**
     * 查询ADRG入组诊断及操作
     * 
     * @param id ADRG入组诊断及操作ID
     * @return ADRG入组诊断及操作
     */
    @Override
    public DrgsEnrollmentDiagnosisAndOperation selectDrgsEnrollmentDiagnosisAndOperationById(Long id) {
        return drgsEnrollmentDiagnosisAndOperationMapper.selectDrgsEnrollmentDiagnosisAndOperationById(id);
    }

    /**
     * 查询ADRG入组诊断及操作列表
     * 
     * @param drgsEnrollmentDiagnosisAndOperation ADRG入组诊断及操作
     * @return ADRG入组诊断及操作
     */
    @Override
    public List<DrgsEnrollmentDiagnosisAndOperation> selectDrgsEnrollmentDiagnosisAndOperationList(DrgsEnrollmentDiagnosisAndOperation drgsEnrollmentDiagnosisAndOperation) {
        return drgsEnrollmentDiagnosisAndOperationMapper.selectDrgsEnrollmentDiagnosisAndOperationList(drgsEnrollmentDiagnosisAndOperation);
    }

    /**
     * 新增ADRG入组诊断及操作
     * 
     * @param drgsEnrollmentDiagnosisAndOperation ADRG入组诊断及操作
     * @return 结果
     */
    @Override
    public int insertDrgsEnrollmentDiagnosisAndOperation(DrgsEnrollmentDiagnosisAndOperation drgsEnrollmentDiagnosisAndOperation) {
    	drgsEnrollmentDiagnosisAndOperation.preInsert();
        return drgsEnrollmentDiagnosisAndOperationMapper.insertDrgsEnrollmentDiagnosisAndOperation(drgsEnrollmentDiagnosisAndOperation);
    }

    /**
     * 修改ADRG入组诊断及操作
     * 
     * @param drgsEnrollmentDiagnosisAndOperation ADRG入组诊断及操作
     * @return 结果
     */
    @Override
    public int updateDrgsEnrollmentDiagnosisAndOperation(DrgsEnrollmentDiagnosisAndOperation drgsEnrollmentDiagnosisAndOperation) {
    	drgsEnrollmentDiagnosisAndOperation.preUpdate();
        return drgsEnrollmentDiagnosisAndOperationMapper.updateDrgsEnrollmentDiagnosisAndOperation(drgsEnrollmentDiagnosisAndOperation);
    }

    /**
     * 批量删除ADRG入组诊断及操作
     * 
     * @param ids 需要删除的ADRG入组诊断及操作ID
     * @return 结果
     */
    @Override
    public int deleteDrgsEnrollmentDiagnosisAndOperationByIds(Long[] ids) {
        return drgsEnrollmentDiagnosisAndOperationMapper.deleteDrgsEnrollmentDiagnosisAndOperationByIds(ids);
    }

    /**
     * 删除ADRG入组诊断及操作信息
     * 
     * @param id ADRG入组诊断及操作ID
     * @return 结果
     */
    @Override
    public int deleteDrgsEnrollmentDiagnosisAndOperationById(Long id) {
        return drgsEnrollmentDiagnosisAndOperationMapper.deleteDrgsEnrollmentDiagnosisAndOperationById(id);
    }
}
