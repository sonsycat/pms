package com.zjry.pms.quality.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.common.utils.DateUtils;
import com.zjry.formDesign.domain.FormDesign;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.quality.mapper.PmsQualityGenerateMapper;
import com.zjry.pms.quality.domain.PmsQualityGenerate;
import com.zjry.pms.quality.service.IPmsQualityGenerateService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.crypto.Data;

/**
 * 质量数据生成Service业务层处理
 * 
 * @author mk
 * @date 2022-07-26
 */
@Service
public class PmsQualityGenerateServiceImpl extends ServiceImpl<PmsQualityGenerateMapper, PmsQualityGenerate> implements IPmsQualityGenerateService {
    @Resource
    private PmsQualityGenerateMapper pmsQualityGenerateMapper;

    /**
     * 查询质量数据生成
     * 
     * @param id 质量数据生成ID
     * @return 质量数据生成
     */
    @Override
    public PmsQualityGenerate selectPmsQualityGenerateById(Long id) {
        return pmsQualityGenerateMapper.selectPmsQualityGenerateById(id);
    }

    /**
     * 查询质量数据生成列表
     * 
     * @param pmsQualityGenerate 质量数据生成
     * @return 质量数据生成
     */
    @Override
    public List<PmsQualityGenerate> selectPmsQualityGenerateList(PmsQualityGenerate pmsQualityGenerate) {
        return pmsQualityGenerateMapper.selectPmsQualityGenerateList(pmsQualityGenerate);
    }

    /**
     * 新增质量数据生成
     * 
     * @param pmsQualityGenerate 质量数据生成
     * @return 结果
     */
    @Override
    public int insertPmsQualityGenerate(PmsQualityGenerate pmsQualityGenerate) {
    	pmsQualityGenerate.preInsert();
        return pmsQualityGenerateMapper.insertPmsQualityGenerate(pmsQualityGenerate);
    }

    /**
     * 修改质量数据生成
     * 
     * @param pmsQualityGenerate 质量数据生成
     * @return 结果
     */
    @Override
    public int updatePmsQualityGenerate(PmsQualityGenerate pmsQualityGenerate) {
    	pmsQualityGenerate.preUpdate();
        return pmsQualityGenerateMapper.updatePmsQualityGenerate(pmsQualityGenerate);
    }

    /**
     * 批量删除质量数据生成
     * 
     * @param ids 需要删除的质量数据生成ID
     * @return 结果
     */
    @Override
    public int deletePmsQualityGenerateByIds(Long[] ids) {
        return pmsQualityGenerateMapper.deletePmsQualityGenerateByIds(ids);
    }

    /**
     * 删除质量数据生成信息
     * 
     * @param id 质量数据生成ID
     * @return 结果
     */
    @Override
    public int deletePmsQualityGenerateById(Long id) {
        return pmsQualityGenerateMapper.deletePmsQualityGenerateById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void creatZLSJ(PmsQualityGenerate pmsQualityGenerate)throws Exception {
        pmsQualityGenerateMapper.deleteByOTimeAndCompCode(pmsQualityGenerate.getOccureTime(),pmsQualityGenerate.getCompCode());
        List<FormDesign> listF = pmsQualityGenerateMapper.selectFormD(pmsQualityGenerate.getCompCode());
        for(FormDesign fd : listF){
            String tName = "dynamic_"+fd.getFormCode();
            List<PmsQualityGenerate> list = pmsQualityGenerateMapper.summaryData(tName,pmsQualityGenerate.getCompCode(),pmsQualityGenerate.getOccureTime());
            for(PmsQualityGenerate pqg : list){
                pqg.setCompCode(pmsQualityGenerate.getCompCode());
                pqg.setOccureTime(new Date());
                pqg.preInsert();
                //指标分值
               String indexScore  = pmsQualityGenerateMapper.selectForIndexScore(pqg.getIndexCode());
                pqg.setIndexScore(indexScore);
                //模板编码
                pqg.setTempCode(fd.getId());
                pqg.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
                pmsQualityGenerateMapper.insertPmsQualityGenerate(pqg);
            }
        }

    }

    @Override
    public List<PmsQualityGenerate> selectSumData(Long compCode,Long deptCode, Date qDate) {
        List<PmsQualityGenerate> list = pmsQualityGenerateMapper.selectSumData(compCode,deptCode,qDate);
        List<PmsQualityGenerate> list1 = pmsQualityGenerateMapper.selectIndexData(compCode,deptCode,qDate);
        for(PmsQualityGenerate item : list){
            for(PmsQualityGenerate item1 : list1){
                if(item.getDeptCode().equals(item1.getDeptCode()) ){
                    BigDecimal indexScore = StringUtils.isNotBlank(item1.getIndexScore())?new BigDecimal(item1.getIndexScore()):BigDecimal.ZERO;
                    item.setActualScore((new BigDecimal(item.getActualScore()).add(indexScore)).setScale(2, RoundingMode.HALF_UP).toString());
                }
            }
        }
        for(PmsQualityGenerate pqg : list){
            if(StringUtils.isNotEmpty(pqg.getOccTime())){
                String occTime = pqg.getOccTime().substring(0,4)+"-"+pqg.getOccTime().substring(4,6);
                pqg.setOccTime(occTime);
            }
        }
        return list;
    }

    @Override
    public List<PmsQualityGenerate> listForD(PmsQualityGenerate pmsQualityGenerate) {
        List<PmsQualityGenerate>  list = pmsQualityGenerateMapper.listForD(pmsQualityGenerate);
        for(PmsQualityGenerate item :list){
            if(item.getActualScore()!=null){
                item.setActualScore(new BigDecimal(item.getActualScore()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            }
            if(item.getIndexScore()!=null){
                item.setIndexScore(new BigDecimal(item.getIndexScore()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            }
        }
        return list;
    }
}
