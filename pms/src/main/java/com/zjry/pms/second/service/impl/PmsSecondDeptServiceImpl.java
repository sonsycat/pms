package com.zjry.pms.second.service.impl;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.second.mapper.PmsSecondDeptMapper;
import com.zjry.pms.second.domain.PmsSecondDept;
import com.zjry.pms.second.service.IPmsSecondDeptService;

/**
 * 二次分配科室指标Service业务层处理
 * 
 * @author maozt
 * @date 2022-09-27
 */
@Service
public class PmsSecondDeptServiceImpl extends ServiceImpl<PmsSecondDeptMapper, PmsSecondDept> implements IPmsSecondDeptService {
    @Autowired
    private PmsSecondDeptMapper pmsSecondDeptMapper;

    /**
     * 查询二次分配科室指标
     * 
     * @param id 二次分配科室指标ID
     * @return 二次分配科室指标
     */
    @Override
    public PmsSecondDept selectPmsSecondDeptById(Long id) {
        return pmsSecondDeptMapper.selectPmsSecondDeptById(id);
    }

    /**
     * 查询二次分配科室指标列表
     * 
     * @param pmsSecondDept 二次分配科室指标
     * @return 二次分配科室指标
     */
    @Override
    public List<PmsSecondDept> selectPmsSecondDeptList(PmsSecondDept pmsSecondDept) {
        return pmsSecondDeptMapper.selectPmsSecondDeptList(pmsSecondDept);
    }

    /**
     * 查询二次分配科室指标列表
     *
     * @param pmsSecondDept 二次分配科室指标
     * @return 二次分配科室指标
     */
    @Override
    public List<PmsSecondDept> selectPmsSecondDeptListByCompCode(PmsSecondDept pmsSecondDept) {
        return pmsSecondDeptMapper.selectPmsSecondDeptListByCompCode(pmsSecondDept);
    }

    /**
     * 新增二次分配科室指标
     * 
     * @param pmsSecondDept 二次分配科室指标
     * @return 结果
     */
    @Override
    public int insertPmsSecondDept(PmsSecondDept pmsSecondDept) {
    	pmsSecondDept.preInsert();
        return pmsSecondDeptMapper.insertPmsSecondDept(pmsSecondDept);
    }

    /**
     * 修改二次分配科室指标
     * 
     * @param pmsSecondDept 二次分配科室指标
     * @return 结果
     */
    @Override
    public int updatePmsSecondDept(PmsSecondDept pmsSecondDept) {
    	pmsSecondDept.preUpdate();
        return pmsSecondDeptMapper.updatePmsSecondDept(pmsSecondDept);
    }

    /**
     * 批量删除二次分配科室指标
     * 
     * @param ids 需要删除的二次分配科室指标ID
     * @return 结果
     */
    @Override
    public int deletePmsSecondDeptByIds(Long[] ids) {
        return pmsSecondDeptMapper.deletePmsSecondDeptByIds(ids);
    }

    /**
     * 删除二次分配科室指标信息
     * 
     * @param id 二次分配科室指标ID
     * @return 结果
     */
    @Override
    public int deletePmsSecondDeptById(Long id) {
        return pmsSecondDeptMapper.deletePmsSecondDeptById(id);
    }

    /**
     * 删除二次分配科室指标信息
     *
     * @param date 二次分配科室指标ID
     * @return 结果
     */
    @Override
    public int deletePmsSecondDeptByNowDate(PmsSecondDept pmsSecondDept) {
        return pmsSecondDeptMapper.deletePmsSecondDeptByNowDate(pmsSecondDept);
    }
}
