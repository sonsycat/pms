package com.zjry.pms.revenue.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.revenue.mapper.PmsRevenueSpecialTempMapper;
import com.zjry.pms.revenue.domain.PmsRevenueSpecialTemp;
import com.zjry.pms.revenue.service.IPmsRevenueSpecialTempService;

/**
 * 特殊收入项目方案临时Service业务层处理
 * 
 * @author maozt
 * @date 2022-06-21
 */
@Service
public class PmsRevenueSpecialTempServiceImpl extends ServiceImpl<PmsRevenueSpecialTempMapper, PmsRevenueSpecialTemp> implements IPmsRevenueSpecialTempService {
    @Autowired
    private PmsRevenueSpecialTempMapper pmsRevenueSpecialTempMapper;

    /**
     * 查询特殊收入项目方案临时
     * 
     * @param id 特殊收入项目方案临时ID
     * @return 特殊收入项目方案临时
     */
    @Override
    public PmsRevenueSpecialTemp selectPmsRevenueSpecialTempById(Long id) {
        return pmsRevenueSpecialTempMapper.selectPmsRevenueSpecialTempById(id);
    }

    /**
     * 查询特殊收入项目方案临时列表
     * 
     * @param pmsRevenueSpecialTemp 特殊收入项目方案临时
     * @return 特殊收入项目方案临时
     */
    @Override
    public List<PmsRevenueSpecialTemp> selectPmsRevenueSpecialTempList(PmsRevenueSpecialTemp pmsRevenueSpecialTemp) {
        return pmsRevenueSpecialTempMapper.selectPmsRevenueSpecialTempList(pmsRevenueSpecialTemp);
    }

    /**
     * 查询特殊收入项目方案临时列表
     *
     * @param pmsRevenueSpecialTemp 特殊收入项目方案临时
     * @return 特殊收入项目方案临时
     */
    @Override
    public List<PmsRevenueSpecialTemp> selectPmsRevenueSpecialTempListNoDel(PmsRevenueSpecialTemp pmsRevenueSpecialTemp) {
        return pmsRevenueSpecialTempMapper.selectPmsRevenueSpecialTempListNoDel(pmsRevenueSpecialTemp);
    }

    /**
     * 新增特殊收入项目方案临时
     * 
     * @param pmsRevenueSpecialTemp 特殊收入项目方案临时
     * @return 结果
     */
    @Override
    public int insertPmsRevenueSpecialTemp(PmsRevenueSpecialTemp pmsRevenueSpecialTemp) {
    	pmsRevenueSpecialTemp.preInsert();
        return pmsRevenueSpecialTempMapper.insertPmsRevenueSpecialTemp(pmsRevenueSpecialTemp);
    }

    /**
     * 修改特殊收入项目方案临时
     * 
     * @param pmsRevenueSpecialTemp 特殊收入项目方案临时
     * @return 结果
     */
    @Override
    public int updatePmsRevenueSpecialTemp(PmsRevenueSpecialTemp pmsRevenueSpecialTemp) {
    	pmsRevenueSpecialTemp.preUpdate();
        return pmsRevenueSpecialTempMapper.updatePmsRevenueSpecialTemp(pmsRevenueSpecialTemp);
    }

    /**
     * 批量删除特殊收入项目方案临时
     * 
     * @param ids 需要删除的特殊收入项目方案临时ID
     * @return 结果
     */
    @Override
    public int deletePmsRevenueSpecialTempByIds(Long[] ids) {
        return pmsRevenueSpecialTempMapper.deletePmsRevenueSpecialTempByIds(ids);
    }

    /**
     * 删除特殊收入项目方案临时信息
     * 
     * @param id 特殊收入项目方案临时ID
     * @return 结果
     */
    @Override
    public int deletePmsRevenueSpecialTempById(Long id) {
        return pmsRevenueSpecialTempMapper.deletePmsRevenueSpecialTempById(id);
    }

    /**
     * 删除特殊收入项目方案临时信息
     *
     * @param compCode 特殊收入项目方案临时ID
     * @return 结果
     */
    @Override
    public int deletePmsRevenueSpecialTempByCompCode(Long compCode) {
        return pmsRevenueSpecialTempMapper.deletePmsRevenueSpecialTempByCompCode(compCode);
    }
}
