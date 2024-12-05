package com.zjry.pms.revenue.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.common.utils.DateUtils;
import com.zjry.pms.encode.service.IHrEncodeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.revenue.mapper.PmsRevenueMapper;
import com.zjry.pms.revenue.domain.PmsRevenue;
import com.zjry.pms.revenue.service.IPmsRevenueService;

/**
 * 收入核算方案Service业务层处理
 * 
 * @author mk
 * @date 2022-06-20
 */
@Service
public class PmsRevenueServiceImpl extends ServiceImpl<PmsRevenueMapper, PmsRevenue> implements IPmsRevenueService {
    @Autowired
    private PmsRevenueMapper pmsRevenueMapper;
    @Autowired
    private IHrEncodeRuleService hrEncodeRuleService;

    /**
     * 查询收入核算方案
     * 
     * @param id 收入核算方案ID
     * @return 收入核算方案
     */
    @Override
    public PmsRevenue selectPmsRevenueById(Long id) {
        return pmsRevenueMapper.selectPmsRevenueById(id);
    }

    /**
     * 查询收入核算方案列表
     * 
     * @param pmsRevenue 收入核算方案
     * @return 收入核算方案
     */
    @Override
    public List<PmsRevenue> selectPmsRevenueList(PmsRevenue pmsRevenue) {
        return pmsRevenueMapper.selectPmsRevenueList(pmsRevenue);
    }

    /**
     * 新增收入核算方案
     * 
     * @param pmsRevenue 收入核算方案
     * @return 结果
     */
    @Override
    public int insertPmsRevenue(PmsRevenue pmsRevenue) {
    	pmsRevenue.preInsert();
        String rcode = hrEncodeRuleService.generateEncode("pms_revenue", "REVENUE_CODE");
        pmsRevenue.setRevenueCode(rcode);
        pmsRevenue.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        return pmsRevenueMapper.insertPmsRevenue(pmsRevenue);
    }

    /**
     * 修改收入核算方案
     * 
     * @param pmsRevenue 收入核算方案
     * @return 结果
     */
    @Override
    public int updatePmsRevenue(PmsRevenue pmsRevenue) {
    	pmsRevenue.preUpdate();
        return pmsRevenueMapper.updatePmsRevenue(pmsRevenue);
    }

    /**
     * 批量删除收入核算方案
     * 
     * @param ids 需要删除的收入核算方案ID
     * @return 结果
     */
    @Override
    public int deletePmsRevenueByIds(Long[] ids) {
        return pmsRevenueMapper.deletePmsRevenueByIds(ids);
    }

    /**
     * 删除收入核算方案信息
     * 
     * @param id 收入核算方案ID
     * @return 结果
     */
    @Override
    public int deletePmsRevenueById(Long id) {
        return pmsRevenueMapper.deletePmsRevenueById(id);
    }
}
