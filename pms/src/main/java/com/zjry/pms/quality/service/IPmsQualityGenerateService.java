package com.zjry.pms.quality.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.quality.domain.PmsQualityGenerate;

import javax.xml.crypto.Data;

/**
 * 质量数据生成Service接口
 * 
 * @author mk
 * @date 2022-07-26
 */
public interface IPmsQualityGenerateService extends IService<PmsQualityGenerate> {
    /**
     * 查询质量数据生成
     * 
     * @param id 质量数据生成ID
     * @return 质量数据生成
     */
    public PmsQualityGenerate selectPmsQualityGenerateById(Long id);

    /**
     * 查询质量数据生成列表
     * 
     * @param pmsQualityGenerate 质量数据生成
     * @return 质量数据生成集合
     */
    public List<PmsQualityGenerate> selectPmsQualityGenerateList(PmsQualityGenerate pmsQualityGenerate);

    /**
     * 新增质量数据生成
     * 
     * @param pmsQualityGenerate 质量数据生成
     * @return 结果
     */
    public int insertPmsQualityGenerate(PmsQualityGenerate pmsQualityGenerate);

    /**
     * 修改质量数据生成
     * 
     * @param pmsQualityGenerate 质量数据生成
     * @return 结果
     */
    public int updatePmsQualityGenerate(PmsQualityGenerate pmsQualityGenerate);

    /**
     * 批量删除质量数据生成
     * 
     * @param ids 需要删除的质量数据生成ID
     * @return 结果
     */
    public int deletePmsQualityGenerateByIds(Long[] ids);

    /**
     * 删除质量数据生成信息
     * 
     * @param id 质量数据生成ID
     * @return 结果
     */
    public int deletePmsQualityGenerateById(Long id);

    void creatZLSJ(PmsQualityGenerate pmsQualityGenerate) throws Exception;

    List<PmsQualityGenerate> selectSumData(Long compCode, Long deptCode, Date qDate);

    List<PmsQualityGenerate> listForD(PmsQualityGenerate pmsQualityGenerate);
}
