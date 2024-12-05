package com.zjry.pms.revenue.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.common.utils.DateUtils;
import com.zjry.pms.revenue.domain.PmRevDept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.revenue.mapper.PmsRevenueDeptMapper;
import com.zjry.pms.revenue.domain.PmsRevenueDept;
import com.zjry.pms.revenue.service.IPmsRevenueDeptService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 收入核算科室设置Service业务层处理
 * 
 * @author mk
 * @date 2022-06-21
 */
@Service
public class PmsRevenueDeptServiceImpl extends ServiceImpl<PmsRevenueDeptMapper, PmsRevenueDept> implements IPmsRevenueDeptService {
    @Autowired
    private PmsRevenueDeptMapper pmsRevenueDeptMapper;

    /**
     * 查询收入核算科室设置
     * 
     * @param id 收入核算科室设置ID
     * @return 收入核算科室设置
     */
    @Override
    public PmsRevenueDept selectPmsRevenueDeptById(Long id) {
        return pmsRevenueDeptMapper.selectPmsRevenueDeptById(id);
    }

    /**
     * 查询收入核算科室设置列表
     * 
     * @param pmsRevenueDept 收入核算科室设置
     * @return 收入核算科室设置
     */
    @Override
    public List<PmsRevenueDept> selectPmsRevenueDeptList(PmsRevenueDept pmsRevenueDept) {
        return pmsRevenueDeptMapper.selectPmsRevenueDeptList(pmsRevenueDept);
    }

    /**
     * 新增收入核算科室设置
     * 
     * @param pmRevDept 收入核算科室设置
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertPmsRevenueDept(PmRevDept pmRevDept)throws Exception {
        pmsRevenueDeptMapper.delByRevCODE(pmRevDept.getRevenueCode());
        List<PmsRevenueDept> list = pmRevDept.getPmsRevenueDepts();
        for (PmsRevenueDept prd : list){
            prd.preInsert();
            prd.setCompCode(pmRevDept.getCompCode());
            prd.setRevenueCode(pmRevDept.getRevenueCode());
            prd.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
            pmsRevenueDeptMapper.insertPmsRevenueDept(prd);
        }
    }

    /**
     * 修改收入核算科室设置
     * 
     * @param pmRevDept 收入核算科室设置
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePmsRevenueDept(PmRevDept pmRevDept)throws Exception {
        pmsRevenueDeptMapper.delByRevCODE(pmRevDept.getRevenueCode());
        List<PmsRevenueDept> list = pmRevDept.getPmsRevenueDepts();
        for (PmsRevenueDept prd : list){
            prd.preInsert();
            prd.setCompCode(pmRevDept.getCompCode());
            prd.setRevenueCode(pmRevDept.getRevenueCode());
            prd.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
            pmsRevenueDeptMapper.updatePmsRevenueDept(prd);
        }
    }

    /**
     * 批量删除收入核算科室设置
     * 
     * @param ids 需要删除的收入核算科室设置ID
     * @return 结果
     */
    @Override
    public int deletePmsRevenueDeptByIds(Long[] ids) {
        return pmsRevenueDeptMapper.deletePmsRevenueDeptByIds(ids);
    }

    /**
     * 删除收入核算科室设置信息
     * 
     * @param id 收入核算科室设置ID
     * @return 结果
     */
    @Override
    public int deletePmsRevenueDeptById(Long id) {
        return pmsRevenueDeptMapper.deletePmsRevenueDeptById(id);
    }
}
