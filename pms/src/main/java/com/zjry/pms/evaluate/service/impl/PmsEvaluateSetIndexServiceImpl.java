package com.zjry.pms.evaluate.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.common.utils.DateUtils;
import com.zjry.pms.evaluate.domain.PmsEvaSetIndexList;
import com.zjry.pms.evaluate.domain.PmsEvaluateSet;
import com.zjry.pms.evaluate.mapper.PmsEvaluateSetMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.evaluate.mapper.PmsEvaluateSetIndexMapper;
import com.zjry.pms.evaluate.domain.PmsEvaluateSetIndex;
import com.zjry.pms.evaluate.service.IPmsEvaluateSetIndexService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 指标集指标Service业务层处理
 * 
 * @author mk
 * @date 2022-08-04
 */
@Service
public class PmsEvaluateSetIndexServiceImpl extends ServiceImpl<PmsEvaluateSetIndexMapper, PmsEvaluateSetIndex> implements IPmsEvaluateSetIndexService {
    @Resource
    private PmsEvaluateSetIndexMapper pmsEvaluateSetIndexMapper;
    @Resource
    private PmsEvaluateSetMapper pmsEvaluateSetMapper;

    /**
     * 查询指标集指标
     * 
     * @param id 指标集指标ID
     * @return 指标集指标
     */
    @Override
    public PmsEvaluateSetIndex selectPmsEvaluateSetIndexById(Long id) {
        return pmsEvaluateSetIndexMapper.selectPmsEvaluateSetIndexById(id);
    }

    /**
     * 查询指标集指标列表
     * 
     * @param pmsEvaluateSetIndex 指标集指标
     * @return 指标集指标
     */
    @Override
    public List<PmsEvaluateSetIndex> selectPmsEvaluateSetIndexList(PmsEvaluateSetIndex pmsEvaluateSetIndex) {
        return pmsEvaluateSetIndexMapper.selectPmsEvaluateSetIndexList(pmsEvaluateSetIndex);
    }

    /**
     * 新增指标集指标
     * 
     * @param pmsEvaSetIndexList 指标集指标
     * @return 结果
     */
    @Override
    @Transactional
    public void insertPmsEvaluateSetIndex(PmsEvaSetIndexList pmsEvaSetIndexList) {
        new LambdaUpdateChainWrapper<>(pmsEvaluateSetMapper).set(PmsEvaluateSet::getDeptType, pmsEvaSetIndexList.getDeptType())
                        .eq(PmsEvaluateSet::getSetCode, pmsEvaSetIndexList.getSetCode()).update();
        pmsEvaluateSetIndexMapper.deleteByCompCodeAndsetCode(pmsEvaSetIndexList.getCompCode(),pmsEvaSetIndexList.getSetCode());
        List<PmsEvaluateSetIndex> list = pmsEvaSetIndexList.getPmsEvaluateSetIndexList();
        if(CollectionUtils.isNotEmpty(list)){
            if(list.size() == 1){
                list.get(0).preInsert();
                list.get(0).setCompCode(pmsEvaSetIndexList.getCompCode());
                list.get(0).setSetCode(pmsEvaSetIndexList.getSetCode());
                list.get(0).setWeightRatio(BigDecimal.ONE);
                pmsEvaluateSetIndexMapper.insertPmsEvaluateSetIndex(list.get(0));
            }else{
                BigDecimal weightRatio = null;
                BigDecimal total = list.stream().map(i -> new BigDecimal(StringUtils.isNotBlank(i.getWeight())?i.getWeight():"0")).reduce(BigDecimal.ZERO, BigDecimal::add);
                if(total.compareTo(BigDecimal.ZERO) == 0){
                    weightRatio = BigDecimal.ONE.divide(new BigDecimal(list.size()), 4, RoundingMode.HALF_UP);
                }
                for(PmsEvaluateSetIndex item : list){
                    item.preInsert();
                    item.setCompCode(pmsEvaSetIndexList.getCompCode());
                    item.setSetCode(pmsEvaSetIndexList.getSetCode());
                    item.setWeightRatio(weightRatio!=null?weightRatio:(total.compareTo(BigDecimal.ZERO)==1?(StringUtils.isNotBlank(item.getWeight())?new BigDecimal(item.getWeight()).divide(total, 4, RoundingMode.HALF_UP):BigDecimal.ZERO):BigDecimal.ZERO));
                    pmsEvaluateSetIndexMapper.insertPmsEvaluateSetIndex(item);
                }
            }
        }
    }

    /**
     * 修改指标集指标
     * 
     * @param pmsEvaluateSetIndex 指标集指标
     * @return 结果
     */
    @Override
    public int updatePmsEvaluateSetIndex(PmsEvaluateSetIndex pmsEvaluateSetIndex) {
        pmsEvaluateSetIndex.preUpdate();
        return pmsEvaluateSetIndexMapper.updatePmsEvaluateSetIndex(pmsEvaluateSetIndex);
    }

    /**
     * 批量删除指标集指标
     * 
     * @param ids 需要删除的指标集指标ID
     * @return 结果
     */
    @Override
    public int deletePmsEvaluateSetIndexByIds(Long[] ids) {
        return pmsEvaluateSetIndexMapper.deletePmsEvaluateSetIndexByIds(ids);
    }

    /**
     * 删除指标集指标信息
     * 
     * @param id 指标集指标ID
     * @return 结果
     */
    @Override
    public int deletePmsEvaluateSetIndexById(Long id) {
        return pmsEvaluateSetIndexMapper.deletePmsEvaluateSetIndexById(id);
    }
}
