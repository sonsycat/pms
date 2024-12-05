package com.zjry.pms.second.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import com.zjry.pms.encode.service.IHrEncodeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.second.mapper.PmsSecondDistributionMapper;
import com.zjry.pms.second.domain.PmsSecondDistribution;
import com.zjry.pms.second.service.IPmsSecondDistributionService;

/**
 * 二次分配指标集设置Service业务层处理
 * 
 * @author maozt
 * @date 2022-09-26
 */
@Service
public class PmsSecondDistributionServiceImpl extends ServiceImpl<PmsSecondDistributionMapper, PmsSecondDistribution> implements IPmsSecondDistributionService {
    @Autowired
    private PmsSecondDistributionMapper pmsSecondDistributionMapper;
    @Autowired
    private IHrEncodeRuleService hrEncodeRuleService;

    /**
     * 查询二次分配指标集设置
     * 
     * @param id 二次分配指标集设置ID
     * @return 二次分配指标集设置
     */
    @Override
    public PmsSecondDistribution selectPmsSecondDistributionById(Long id) {
        return pmsSecondDistributionMapper.selectPmsSecondDistributionById(id);
    }

    /**
     * 查询二次分配指标集设置列表
     * 
     * @param pmsSecondDistribution 二次分配指标集设置
     * @return 二次分配指标集设置
     */
    @Override
    public List<PmsSecondDistribution> selectPmsSecondDistributionList(PmsSecondDistribution pmsSecondDistribution) {
        return pmsSecondDistributionMapper.selectPmsSecondDistributionList(pmsSecondDistribution);
    }

    /**
     * 新增二次分配指标集设置
     * 
     * @param pmsSecondDistribution 二次分配指标集设置
     * @return 结果
     */
    @Override
    public int insertPmsSecondDistribution(PmsSecondDistribution pmsSecondDistribution) throws Exception {
        if (pmsSecondDistributionMapper.selectPmsSecondDistributionListByName(pmsSecondDistribution).size() > 0 ) {
            throw new Exception("指标集名称重复");
        }
        pmsSecondDistribution.setDisCode(hrEncodeRuleService.generateEncode("pms_second_distribution", "DIS_CODE"));
    	pmsSecondDistribution.preInsert();
        return pmsSecondDistributionMapper.insertPmsSecondDistribution(pmsSecondDistribution);
    }

    /**
     * 修改二次分配指标集设置
     * 
     * @param pmsSecondDistribution 二次分配指标集设置
     * @return 结果
     */
    @Override
    public int updatePmsSecondDistribution(PmsSecondDistribution pmsSecondDistribution) throws Exception {
        if (pmsSecondDistributionMapper.selectPmsSecondDistributionListByName(pmsSecondDistribution).size() > 0 ) {
            throw new Exception("指标集名称重复");
        }
    	pmsSecondDistribution.preUpdate();
        return pmsSecondDistributionMapper.updatePmsSecondDistribution(pmsSecondDistribution);
    }

    /**
     * 批量删除二次分配指标集设置
     * 
     * @param ids 需要删除的二次分配指标集设置ID
     * @return 结果
     */
    @Override
    public int deletePmsSecondDistributionByIds(Long[] ids) {
        return pmsSecondDistributionMapper.deletePmsSecondDistributionByIds(ids);
    }

    /**
     * 删除二次分配指标集设置信息
     * 
     * @param id 二次分配指标集设置ID
     * @return 结果
     */
    @Override
    public int deletePmsSecondDistributionById(Long id) {
        return pmsSecondDistributionMapper.deletePmsSecondDistributionById(id);
    }
}
