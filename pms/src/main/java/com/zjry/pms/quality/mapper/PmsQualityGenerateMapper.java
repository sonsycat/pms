package com.zjry.pms.quality.mapper;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.formDesign.domain.FormDesign;
import com.zjry.formDesign.domain.FormDesignField;
import com.zjry.pms.quality.domain.PmsQualityGenerate;
import org.apache.ibatis.annotations.Param;

import javax.xml.crypto.Data;

/**
 * 质量数据生成Mapper接口
 * 
 * @author mk
 * @date 2022-07-26
 */
public interface PmsQualityGenerateMapper extends BaseMapper<PmsQualityGenerate> {
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
     * 删除质量数据生成
     * 
     * @param id 质量数据生成ID
     * @return 结果
     */
    public int deletePmsQualityGenerateById(Long id);

    /**
     * 批量删除质量数据生成
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsQualityGenerateByIds(Long[] ids);

    public List<FormDesign> selectFormD(Long compCode);

    public List<PmsQualityGenerate> summaryData(@Param("tableName") String tableName, @Param("compCode")Long compCode , @Param("csDate")Date csDate);

    String selectForIndexScore(String indexCode);

    void deleteByOTimeAndCompCode(@Param("occureTime")Date occureTime,@Param("compCode") Long compCode);

    List<PmsQualityGenerate> selectSumData(@Param("compCode")Long compCode,@Param("deptCode")Long deptCode,@Param("qDate") Date qDate);
    List<PmsQualityGenerate> selectIndexData(@Param("compCode")Long compCode,@Param("deptCode")Long deptCode,@Param("qDate") Date qDate);

    List<PmsQualityGenerate> listForD(PmsQualityGenerate pmsQualityGenerate);
}
