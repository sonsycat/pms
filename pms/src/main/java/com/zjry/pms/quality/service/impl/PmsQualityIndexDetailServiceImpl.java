package com.zjry.pms.quality.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.quality.mapper.PmsQualityIndexDetailMapper;
import com.zjry.pms.quality.domain.PmsQualityIndexDetail;
import com.zjry.pms.quality.service.IPmsQualityIndexDetailService;

/**
 * 质量考评指标明细Service业务层处理
 * 
 * @author yzh
 * @date 2022-07-05
 */
@Service
public class PmsQualityIndexDetailServiceImpl extends ServiceImpl<PmsQualityIndexDetailMapper, PmsQualityIndexDetail> implements IPmsQualityIndexDetailService {
    @Autowired
    private PmsQualityIndexDetailMapper pmsQualityIndexDetailMapper;

    /**
     * 查询质量考评指标明细
     * 
     * @param id 质量考评指标明细ID
     * @return 质量考评指标明细
     */
    @Override
    public PmsQualityIndexDetail selectPmsQualityIndexDetailById(Long id) {
        return pmsQualityIndexDetailMapper.selectPmsQualityIndexDetailById(id);
    }

    /**
     * 查询质量考评指标明细列表
     * 
     * @param pmsQualityIndexDetail 质量考评指标明细
     * @return 质量考评指标明细
     */
    @Override
    public List<PmsQualityIndexDetail> selectPmsQualityIndexDetailList(PmsQualityIndexDetail pmsQualityIndexDetail) {
        return pmsQualityIndexDetailMapper.selectPmsQualityIndexDetailList(pmsQualityIndexDetail);
    }

    /**
     * 新增质量考评指标明细
     * 
     * @param pmsQualityIndexDetail 质量考评指标明细
     * @return 结果
     */
    @Override
    public int insertPmsQualityIndexDetail(PmsQualityIndexDetail pmsQualityIndexDetail) {
    	pmsQualityIndexDetail.preInsert();
        return pmsQualityIndexDetailMapper.insertPmsQualityIndexDetail(pmsQualityIndexDetail);
    }

    /**
     * 修改质量考评指标明细
     * 
     * @param pmsQualityIndexDetail 质量考评指标明细
     * @return 结果
     */
    @Override
    public int updatePmsQualityIndexDetail(PmsQualityIndexDetail pmsQualityIndexDetail) {
    	pmsQualityIndexDetail.preUpdate();
        return pmsQualityIndexDetailMapper.updatePmsQualityIndexDetail(pmsQualityIndexDetail);
    }

    /**
     * 批量删除质量考评指标明细
     * 
     * @param ids 需要删除的质量考评指标明细ID
     * @return 结果
     */
    @Override
    public int deletePmsQualityIndexDetailByIds(Long[] ids) {
        return pmsQualityIndexDetailMapper.deletePmsQualityIndexDetailByIds(ids);
    }

    /**
     * 删除质量考评指标明细信息
     * 
     * @param id 质量考评指标明细ID
     * @return 结果
     */
    @Override
    public int deletePmsQualityIndexDetailById(Long id) {
        return pmsQualityIndexDetailMapper.deletePmsQualityIndexDetailById(id);
    }
}
