package com.zjry.pms.drgs.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.drgs.domain.DrgsEnrollmentDiagnosisAndOperation;

/**
 * ADRG入组诊断及操作Mapper接口
 * 
 * @author zjry
 * @date 2023-03-20
 */
public interface DrgsEnrollmentDiagnosisAndOperationMapper extends BaseMapper<DrgsEnrollmentDiagnosisAndOperation> {
    /**
     * 查询ADRG入组诊断及操作
     * 
     * @param id ADRG入组诊断及操作ID
     * @return ADRG入组诊断及操作
     */
    public DrgsEnrollmentDiagnosisAndOperation selectDrgsEnrollmentDiagnosisAndOperationById(Long id);

    /**
     * 查询ADRG入组诊断及操作列表
     * 
     * @param drgsEnrollmentDiagnosisAndOperation ADRG入组诊断及操作
     * @return ADRG入组诊断及操作集合
     */
    public List<DrgsEnrollmentDiagnosisAndOperation> selectDrgsEnrollmentDiagnosisAndOperationList(DrgsEnrollmentDiagnosisAndOperation drgsEnrollmentDiagnosisAndOperation);

    /**
     * 新增ADRG入组诊断及操作
     * 
     * @param drgsEnrollmentDiagnosisAndOperation ADRG入组诊断及操作
     * @return 结果
     */
    public int insertDrgsEnrollmentDiagnosisAndOperation(DrgsEnrollmentDiagnosisAndOperation drgsEnrollmentDiagnosisAndOperation);

    /**
     * 修改ADRG入组诊断及操作
     * 
     * @param drgsEnrollmentDiagnosisAndOperation ADRG入组诊断及操作
     * @return 结果
     */
    public int updateDrgsEnrollmentDiagnosisAndOperation(DrgsEnrollmentDiagnosisAndOperation drgsEnrollmentDiagnosisAndOperation);

    /**
     * 删除ADRG入组诊断及操作
     * 
     * @param id ADRG入组诊断及操作ID
     * @return 结果
     */
    public int deleteDrgsEnrollmentDiagnosisAndOperationById(Long id);

    /**
     * 批量删除ADRG入组诊断及操作
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDrgsEnrollmentDiagnosisAndOperationByIds(Long[] ids);
}
