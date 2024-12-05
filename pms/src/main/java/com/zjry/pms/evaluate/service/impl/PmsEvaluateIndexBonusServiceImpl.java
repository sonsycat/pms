package com.zjry.pms.evaluate.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.pms.common.utils.UserUtils;
import com.zjry.pms.evaluate.domain.PmsEvaluateIndexBonusDto;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.evaluate.mapper.PmsEvaluateIndexBonusMapper;
import com.zjry.pms.evaluate.domain.PmsEvaluateIndexBonus;
import com.zjry.pms.evaluate.service.IPmsEvaluateIndexBonusService;

/**
 * 奖金指标Service业务层处理
 * 
 * @author zjry
 * @date 2022-11-08
 */
@Service
public class PmsEvaluateIndexBonusServiceImpl extends ServiceImpl<PmsEvaluateIndexBonusMapper, PmsEvaluateIndexBonus> implements IPmsEvaluateIndexBonusService {
    @Autowired
    private PmsEvaluateIndexBonusMapper pmsEvaluateIndexBonusMapper;

    /**
     * 查询奖金指标
     * 
     * @param id 奖金指标ID
     * @return 奖金指标
     */
    @Override
    public PmsEvaluateIndexBonus selectPmsEvaluateIndexBonusById(Long id) {
        return pmsEvaluateIndexBonusMapper.selectPmsEvaluateIndexBonusById(id);
    }

    /**
     * 查询奖金指标列表
     * 
     * @param pmsEvaluateIndexBonus 奖金指标
     * @return 奖金指标
     */
    @Override
    public List<PmsEvaluateIndexBonus> selectPmsEvaluateIndexBonusList(PmsEvaluateIndexBonus pmsEvaluateIndexBonus) {
        return pmsEvaluateIndexBonusMapper.selectPmsEvaluateIndexBonusList(pmsEvaluateIndexBonus);
    }

    /**
     * 新增奖金指标
     * 
     * @param pmsEvaluateIndexBonus 奖金指标
     * @return 结果
     */
    @Override
    public int insertPmsEvaluateIndexBonus(PmsEvaluateIndexBonus pmsEvaluateIndexBonus) {
    	pmsEvaluateIndexBonus.preInsert();
        return pmsEvaluateIndexBonusMapper.insertPmsEvaluateIndexBonus(pmsEvaluateIndexBonus);
    }

    /**
     * 修改奖金指标
     * 
     * @param pmsEvaluateIndexBonus 奖金指标
     * @return 结果
     */
    @Override
    public int updatePmsEvaluateIndexBonus(PmsEvaluateIndexBonus pmsEvaluateIndexBonus) {
    	pmsEvaluateIndexBonus.preUpdate();
        return pmsEvaluateIndexBonusMapper.updatePmsEvaluateIndexBonus(pmsEvaluateIndexBonus);
    }

    /**
     * 批量删除奖金指标
     * 
     * @param ids 需要删除的奖金指标ID
     * @return 结果
     */
    @Override
    public int deletePmsEvaluateIndexBonusByIds(Long[] ids) {
        return pmsEvaluateIndexBonusMapper.deletePmsEvaluateIndexBonusByIds(ids);
    }

    /**
     * 删除奖金指标信息
     * 
     * @param id 奖金指标ID
     * @return 结果
     */
    @Override
    public int deletePmsEvaluateIndexBonusById(Long id) {
        return pmsEvaluateIndexBonusMapper.deletePmsEvaluateIndexBonusById(id);
    }

    @Override
    public int saveBatch(PmsEvaluateIndexBonusDto pmsEvaluateIndexBonusDto){
        /* 先删除旧数据 */
        PmsEvaluateIndexBonus pmsEvaluateIndexBonus = new PmsEvaluateIndexBonus();
        pmsEvaluateIndexBonus.setDeptRoomType(pmsEvaluateIndexBonusDto.getDeptRoomType());
        pmsEvaluateIndexBonus.setCompCode(UserUtils.getSysUser().getDeptId());
        pmsEvaluateIndexBonusMapper.deleteBatch(pmsEvaluateIndexBonus);
        List<PmsEvaluateIndexBonus> evaluateIndexBonusList = pmsEvaluateIndexBonusDto.getEvaluateIndexBonusList();
        if(CollectionUtils.isNotEmpty(evaluateIndexBonusList)){
            evaluateIndexBonusList.forEach(i->{
                i.setCompCode(pmsEvaluateIndexBonusDto.getCompCode()!=null?pmsEvaluateIndexBonusDto.getCompCode():UserUtils.getSysUser().getDeptId());
                i.setDeptRoomType(pmsEvaluateIndexBonusDto.getDeptRoomType());
                i.setDelFlag("0");
                i.preInsert();
            });
            return pmsEvaluateIndexBonusMapper.insertBatch(evaluateIndexBonusList);
        }

        return 1;
    }
}
