package com.zjry.pms.revenue.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.common.utils.DateUtils;
import com.zjry.pms.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.revenue.mapper.PmsRevenueNumMapper;
import com.zjry.pms.revenue.domain.PmsRevenueNum;
import com.zjry.pms.revenue.service.IPmsRevenueNumService;

/**
 * 收入录入Service业务层处理
 * 
 * @author mk
 * @date 2022-06-22
 */
@Service
public class PmsRevenueNumServiceImpl extends ServiceImpl<PmsRevenueNumMapper, PmsRevenueNum> implements IPmsRevenueNumService {
    @Autowired
    private PmsRevenueNumMapper pmsRevenueNumMapper;

    /**
     * 查询收入录入
     * 
     * @param id 收入录入ID
     * @return 收入录入
     */
    @Override
    public PmsRevenueNum selectPmsRevenueNumById(Long id) {
        return pmsRevenueNumMapper.selectPmsRevenueNumById(id);
    }

    /**
     * 查询收入录入列表
     * 
     * @param pmsRevenueNum 收入录入
     * @return 收入录入
     */
    @Override
    public List<PmsRevenueNum> selectPmsRevenueNumList(PmsRevenueNum pmsRevenueNum) {
        return pmsRevenueNumMapper.selectPmsRevenueNumList(pmsRevenueNum);
    }

    /**
     * 新增收入录入
     * 
     * @param pmsRevenueNum 收入录入
     * @return 结果
     */
    @Override
    public int insertPmsRevenueNum(PmsRevenueNum pmsRevenueNum) {
    	pmsRevenueNum.preInsert();
        pmsRevenueNum.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
//        pmsRevenueNum.setRevenueKind(Constants.IS_CHANGE_NO);
        pmsRevenueNum.setRevenueKind(Constants.CHECK_PASS);
        return pmsRevenueNumMapper.insertPmsRevenueNum(pmsRevenueNum);
    }

    /**
     * 修改收入录入
     * 
     * @param pmsRevenueNum 收入录入
     * @return 结果
     */
    @Override
    public int updatePmsRevenueNum(PmsRevenueNum pmsRevenueNum) {
    	pmsRevenueNum.preUpdate();
        return pmsRevenueNumMapper.updatePmsRevenueNum(pmsRevenueNum);
    }

    /**
     * 批量删除收入录入
     * 
     * @param ids 需要删除的收入录入ID
     * @return 结果
     */
    @Override
    public int deletePmsRevenueNumByIds(Long[] ids) {
        return pmsRevenueNumMapper.deletePmsRevenueNumByIds(ids);
    }

    /**
     * 删除收入录入信息
     * 
     * @param id 收入录入ID
     * @return 结果
     */
    @Override
    public int deletePmsRevenueNumById(Long id) {
        return pmsRevenueNumMapper.deletePmsRevenueNumById(id);
    }

    @Override
    public List<PmsRevenueNum> selectPmsForApp(PmsRevenueNum prn) {
        return pmsRevenueNumMapper.selectPmsForApp(prn);
    }

    @Override
    public String beforeBreak(PmsRevenueNum prn) {
        return pmsRevenueNumMapper.beforeBreak(prn);
    }
}
