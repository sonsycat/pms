package com.zjry.pms.second.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.pms.evaluate.domain.PmsEvaluateIndexDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.second.mapper.PmsSecondDistributionDetailMapper;
import com.zjry.pms.second.domain.PmsSecondDistributionDetail;
import com.zjry.pms.second.service.IPmsSecondDistributionDetailService;

/**
 * 二次分配指标集明细Service业务层处理
 * 
 * @author maozt
 * @date 2022-09-26
 */
@Service
public class PmsSecondDistributionDetailServiceImpl extends ServiceImpl<PmsSecondDistributionDetailMapper, PmsSecondDistributionDetail> implements IPmsSecondDistributionDetailService {
    @Autowired
    private PmsSecondDistributionDetailMapper pmsSecondDistributionDetailMapper;

    /**
     * 查询二次分配指标集明细
     * 
     * @param id 二次分配指标集明细ID
     * @return 二次分配指标集明细
     */
    @Override
    public PmsSecondDistributionDetail selectPmsSecondDistributionDetailById(Long id) {
        return pmsSecondDistributionDetailMapper.selectPmsSecondDistributionDetailById(id);
    }

    /**
     * 查询二次分配指标集明细列表
     * 
     * @param pmsSecondDistributionDetail 二次分配指标集明细
     * @return 二次分配指标集明细
     */
    @Override
    public List<PmsSecondDistributionDetail> selectPmsSecondDistributionDetailList(PmsSecondDistributionDetail pmsSecondDistributionDetail) {
        return pmsSecondDistributionDetailMapper.selectPmsSecondDistributionDetailList(pmsSecondDistributionDetail);
    }

    /**
     * 查询二次分配指标集明细列表
     *
     * @param pmsSecondDistributionDetail 二次分配指标集明细
     * @return 二次分配指标集明细
     */
    @Override
    public List<PmsSecondDistributionDetail> getDistributionEvaluate(PmsSecondDistributionDetail pmsSecondDistributionDetail) {
        List<PmsSecondDistributionDetail> distributionEvaluate = pmsSecondDistributionDetailMapper.getDistributionEvaluate(pmsSecondDistributionDetail);
        PmsSecondDistributionDetail distributionDetail = new PmsSecondDistributionDetail();
        if ("1".equals(pmsSecondDistributionDetail.getIsProject())) {
            for (PmsSecondDistributionDetail secondDistributionDetail : distributionEvaluate) {
                distributionDetail.setDisCode(pmsSecondDistributionDetail.getDisCode());
                distributionDetail.setIndexCode(secondDistributionDetail.getIndexCode());
                PmsSecondDistributionDetail detail = pmsSecondDistributionDetailMapper.selectPmsSecondDistributionDetailList(distributionDetail).get(0);
                secondDistributionDetail.setWeights(detail.getWeights());
            }
        }
        return distributionEvaluate;
    }

    /**
     * 新增二次分配指标集明细
     * 
     * @param pmsSecondDistributionDetail 二次分配指标集明细
     * @return 结果
     */
    @Override
    public int insertPmsSecondDistributionDetail(PmsSecondDistributionDetail pmsSecondDistributionDetail) {
    	pmsSecondDistributionDetail.preInsert();
        return pmsSecondDistributionDetailMapper.insertPmsSecondDistributionDetail(pmsSecondDistributionDetail);
    }

    /**
     * 修改二次分配指标集明细
     * 
     * @param pmsSecondDistributionDetail 二次分配指标集明细
     * @return 结果
     */
    @Override
    public int updatePmsSecondDistributionDetail(PmsSecondDistributionDetail pmsSecondDistributionDetail) {
    	pmsSecondDistributionDetail.preUpdate();
        return pmsSecondDistributionDetailMapper.updatePmsSecondDistributionDetail(pmsSecondDistributionDetail);
    }

    /**
     * 批量删除二次分配指标集明细
     * 
     * @param ids 需要删除的二次分配指标集明细ID
     * @return 结果
     */
    @Override
    public int deletePmsSecondDistributionDetailByIds(Long[] ids) {
        return pmsSecondDistributionDetailMapper.deletePmsSecondDistributionDetailByIds(ids);
    }

    /**
     * 删除二次分配指标集明细信息
     * 
     * @param id 二次分配指标集明细ID
     * @return 结果
     */
    @Override
    public int deletePmsSecondDistributionDetailById(Long id) {
        return pmsSecondDistributionDetailMapper.deletePmsSecondDistributionDetailById(id);
    }

    /**
     * 删除二次分配指标集明细信息
     *
     * @param disCode 二次分配指标集明细ID
     * @return 结果
     */
    @Override
    public int deletePmsSecondDistributionDetailByDisCode(String disCode) {
        return pmsSecondDistributionDetailMapper.deletePmsSecondDistributionDetailByDisCode(disCode);
    }

    /**
     * 获取医生对应考核指标结果集
     * @return
     */
    @Override
    public List<PmsEvaluateIndexDto> selectDetailListForDoctor(Long compCode, String nowDate){
        return pmsSecondDistributionDetailMapper.selectDetailListForDoctor(compCode, nowDate);
    }
}
