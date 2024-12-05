package com.zjry.pms.quality.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.quality.mapper.PmsQualityIndexMapper;
import com.zjry.pms.quality.domain.PmsQualityIndex;
import com.zjry.pms.quality.service.IPmsQualityIndexService;

/**
 * 质量考评指标Service业务层处理
 * 
 * @author yzh
 * @date 2022-07-05
 */
@Service
public class PmsQualityIndexServiceImpl extends ServiceImpl<PmsQualityIndexMapper, PmsQualityIndex> implements IPmsQualityIndexService {
    @Autowired
    private PmsQualityIndexMapper pmsQualityIndexMapper;

    /**
     * 查询质量考评指标
     * 
     * @param id 质量考评指标ID
     * @return 质量考评指标
     */
    @Override
    public PmsQualityIndex selectPmsQualityIndexById(Long id) {
        return pmsQualityIndexMapper.selectPmsQualityIndexById(id);
    }

    /**
     * 查询质量考评指标列表
     * 
     * @param pmsQualityIndex 质量考评指标
     * @return 质量考评指标
     */
    @Override
    public List<PmsQualityIndex> selectPmsQualityIndexList(PmsQualityIndex pmsQualityIndex) {
        return pmsQualityIndexMapper.selectPmsQualityIndexList(pmsQualityIndex);
    }

    /**
     * 新增质量考评指标
     * 
     * @param pmsQualityIndex 质量考评指标
     * @return 结果
     */
    @Override
    public int insertPmsQualityIndex(PmsQualityIndex pmsQualityIndex) {
    	pmsQualityIndex.preInsert();
        return pmsQualityIndexMapper.insertPmsQualityIndex(pmsQualityIndex);
    }

    /**
     * 修改质量考评指标
     * 
     * @param pmsQualityIndex 质量考评指标
     * @return 结果
     */
    @Override
    public int updatePmsQualityIndex(PmsQualityIndex pmsQualityIndex) {
    	pmsQualityIndex.preUpdate();
        return pmsQualityIndexMapper.updatePmsQualityIndex(pmsQualityIndex);
    }

    /**
     * 批量删除质量考评指标
     * 
     * @param ids 需要删除的质量考评指标ID
     * @return 结果
     */
    @Override
    public int deletePmsQualityIndexByIds(Long[] ids) {
        return pmsQualityIndexMapper.deletePmsQualityIndexByIds(ids);
    }

    /**
     * 删除质量考评指标信息
     * 
     * @param id 质量考评指标ID
     * @return 结果
     */
    @Override
    public int deletePmsQualityIndexById(Long id) {
        return pmsQualityIndexMapper.deletePmsQualityIndexById(id);
    }
}
