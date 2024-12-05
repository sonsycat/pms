package com.zjry.pms.basic.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.common.utils.DateUtils;
import com.zjry.pms.common.utils.PinyinUtil;
import com.zjry.pms.encode.service.IHrEncodeRuleService;
import com.zjry.pms.project.domain.CostProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.basic.mapper.PmsHisIncomeMapper;
import com.zjry.pms.basic.domain.PmsHisIncome;
import com.zjry.pms.basic.service.IPmsHisIncomeService;
import org.springframework.transaction.annotation.Transactional;

/**
 * HIS收入类别Service业务层处理
 * 
 * @author maozt
 * @date 2022-06-16
 */
@Service
public class PmsHisIncomeServiceImpl extends ServiceImpl<PmsHisIncomeMapper, PmsHisIncome> implements IPmsHisIncomeService {
    @Autowired
    private PmsHisIncomeMapper pmsHisIncomeMapper;
    @Autowired
    private IHrEncodeRuleService hrEncodeRuleService;

    /**
     * 查询HIS收入类别
     * 
     * @param id HIS收入类别ID
     * @return HIS收入类别
     */
    @Override
    public PmsHisIncome selectPmsHisIncomeById(Long id) {
        return pmsHisIncomeMapper.selectPmsHisIncomeById(id);
    }

    /**
     * 查询HIS收入类别列表
     * 
     * @param pmsHisIncome HIS收入类别
     * @return HIS收入类别
     */
    @Override
    public List<PmsHisIncome> selectPmsHisIncomeList(PmsHisIncome pmsHisIncome) {
        return pmsHisIncomeMapper.selectPmsHisIncomeList(pmsHisIncome);
    }

    /**
     * 查询HIS收入类别列表
     *
     * @param pmsHisIncome HIS收入类别
     * @return HIS收入类别
     */
    @Override
    public List<PmsHisIncome> selectPmsHisIncomeListByName(PmsHisIncome pmsHisIncome) {
        return pmsHisIncomeMapper.selectPmsHisIncomeListByName(pmsHisIncome);
    }

    /**
     * 新增HIS收入类别
     * 
     * @param pmsHisIncome HIS收入类别
     * @return 结果
     */
    @Override
    public int insertPmsHisIncome(PmsHisIncome pmsHisIncome) throws Exception {
        PmsHisIncome hisIncome = new PmsHisIncome();
        hisIncome.setCompCode(pmsHisIncome.getCompCode());
        hisIncome.setRevenueName(pmsHisIncome.getRevenueName());
        if (pmsHisIncomeMapper.selectPmsHisIncomeListByName(hisIncome).size() > 0) {
            throw new Exception("当前单位已有相同名称");
        }
    	pmsHisIncome.preInsert();
        pmsHisIncome.setRevenueCode(hrEncodeRuleService.generateEncode("pms_his_income", "REVENUE_CODE"));
        return pmsHisIncomeMapper.insertPmsHisIncome(pmsHisIncome);
    }

    /**
     * 修改HIS收入类别
     * 
     * @param pmsHisIncome HIS收入类别
     * @return 结果
     */
    @Override
    public int updatePmsHisIncome(PmsHisIncome pmsHisIncome) throws Exception {
        PmsHisIncome hisIncome = new PmsHisIncome();
        hisIncome.setCompCode(pmsHisIncome.getCompCode());
        hisIncome.setRevenueName(pmsHisIncome.getRevenueName());
        hisIncome.setId(pmsHisIncome.getId());
        if (pmsHisIncomeMapper.selectPmsHisIncomeListByName(hisIncome).size() > 0) {
            throw new Exception("当前单位已有相同名称");
        }
    	pmsHisIncome.preUpdate();
        return pmsHisIncomeMapper.updatePmsHisIncome(pmsHisIncome);
    }

    /**
     * 批量删除HIS收入类别
     * 
     * @param ids 需要删除的HIS收入类别ID
     * @return 结果
     */
    @Override
    public int deletePmsHisIncomeByIds(Long[] ids) {
        return pmsHisIncomeMapper.deletePmsHisIncomeByIds(ids);
    }

    /**
     * 删除HIS收入类别信息
     * 
     * @param id HIS收入类别ID
     * @return 结果
     */
    @Override
    public int deletePmsHisIncomeById(Long id) {
        return pmsHisIncomeMapper.deletePmsHisIncomeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importData(List<PmsHisIncome> pmsHisIncomes, Long compCode )throws Exception {
        for (PmsHisIncome pmsHisIncome : pmsHisIncomes){
            pmsHisIncome.setCompCode(compCode);
            pmsHisIncome.setSpell(PinyinUtil.getPinYinHeadChar(pmsHisIncome.getRevenueName()).toUpperCase());
            this.insertPmsHisIncome(pmsHisIncome);
        }
    }
}
