package com.zjry.pms.revenue.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.revenue.mapper.PmsRevenueSpecialMapper;
import com.zjry.pms.revenue.domain.PmsRevenueSpecial;
import com.zjry.pms.revenue.service.IPmsRevenueSpecialService;

/**
 * 特殊收入项目方案Service业务层处理
 * 
 * @author maozt
 * @date 2022-06-21
 */
@Service
public class PmsRevenueSpecialServiceImpl extends ServiceImpl<PmsRevenueSpecialMapper, PmsRevenueSpecial> implements IPmsRevenueSpecialService {
    @Autowired
    private PmsRevenueSpecialMapper pmsRevenueSpecialMapper;

    /**
     * 查询特殊收入项目方案
     * 
     * @param id 特殊收入项目方案ID
     * @return 特殊收入项目方案
     */
    @Override
    public PmsRevenueSpecial selectPmsRevenueSpecialById(Long id) {
        return pmsRevenueSpecialMapper.selectPmsRevenueSpecialById(id);
    }

    /**
     * 查询特殊收入项目方案列表
     * 
     * @param pmsRevenueSpecial 特殊收入项目方案
     * @return 特殊收入项目方案
     */
    @Override
    public List<PmsRevenueSpecial> selectPmsRevenueSpecialList(PmsRevenueSpecial pmsRevenueSpecial) {
        return pmsRevenueSpecialMapper.selectPmsRevenueSpecialList(pmsRevenueSpecial);
    }

    /**
     * 新增特殊收入项目方案
     * 
     * @param pmsRevenueSpecial 特殊收入项目方案
     * @return 结果
     */
    @Override
    public int insertPmsRevenueSpecial(PmsRevenueSpecial pmsRevenueSpecial) throws Exception {
        PmsRevenueSpecial revenueSpecial = new PmsRevenueSpecial();
        revenueSpecial.setCompCode(pmsRevenueSpecial.getCompCode());
        revenueSpecial.setBillDept(pmsRevenueSpecial.getBillDept());
        revenueSpecial.setExecDept(pmsRevenueSpecial.getExecDept());
        revenueSpecial.setProjectCode(pmsRevenueSpecial.getProjectCode());
        if (pmsRevenueSpecialMapper.selectPmsRevenueSpecialList(revenueSpecial).size() != 0) {
            throw new Exception("同一单位下、项目名称、开单科室、执行科室不能全部相同");
        }
    	pmsRevenueSpecial.preInsert();
        return pmsRevenueSpecialMapper.insertPmsRevenueSpecial(pmsRevenueSpecial);
    }

    /**
     * 修改特殊收入项目方案
     * 
     * @param pmsRevenueSpecial 特殊收入项目方案
     * @return 结果
     */
    @Override
    public int updatePmsRevenueSpecial(PmsRevenueSpecial pmsRevenueSpecial) throws Exception {
        PmsRevenueSpecial revenueSpecial = new PmsRevenueSpecial();
        revenueSpecial.setCompCode(pmsRevenueSpecial.getCompCode());
        revenueSpecial.setBillDept(pmsRevenueSpecial.getBillDept());
        revenueSpecial.setExecDept(pmsRevenueSpecial.getExecDept());
        revenueSpecial.setProjectCode(pmsRevenueSpecial.getProjectCode());
        revenueSpecial.setSpecialCode(pmsRevenueSpecial.getSpecialCode());
        if (pmsRevenueSpecialMapper.selectPmsRevenueSpecialListByNotSpecialCode(revenueSpecial).size() != 0) {
            throw new Exception("同一单位下、项目名称、开单科室、执行科室不能全部相同");
        }
    	pmsRevenueSpecial.preUpdate();
        return pmsRevenueSpecialMapper.updatePmsRevenueSpecial(pmsRevenueSpecial);
    }

    /**
     * 修改特殊收入项目方案
     *
     * @param pmsRevenueSpecial 特殊收入项目方案
     * @return 结果
     */
    @Override
    public int updatePmsRevenueSpecialBySpecialCode(PmsRevenueSpecial pmsRevenueSpecial) throws Exception {
        PmsRevenueSpecial revenueSpecial = new PmsRevenueSpecial();
        revenueSpecial.setCompCode(pmsRevenueSpecial.getCompCode());
        revenueSpecial.setBillDept(pmsRevenueSpecial.getBillDept());
        revenueSpecial.setExecDept(pmsRevenueSpecial.getExecDept());
        revenueSpecial.setProjectCode(pmsRevenueSpecial.getProjectCode());
        revenueSpecial.setSpecialCode(pmsRevenueSpecial.getSpecialCode());
        if (pmsRevenueSpecialMapper.selectPmsRevenueSpecialListByNotSpecialCode(revenueSpecial).size() != 0) {
            throw new Exception("同一单位下、项目名称、开单科室、执行科室不能全部相同");
        }
    	pmsRevenueSpecial.preUpdate();
        return pmsRevenueSpecialMapper.updatePmsRevenueSpecialBySpecialCode(pmsRevenueSpecial);
    }

    /**
     * 批量删除特殊收入项目方案
     * 
     * @param ids 需要删除的特殊收入项目方案ID
     * @return 结果
     */
    @Override
    public int deletePmsRevenueSpecialByIds(Long[] ids) {
        return pmsRevenueSpecialMapper.deletePmsRevenueSpecialByIds(ids);
    }

    /**
     * 删除特殊收入项目方案信息
     * 
     * @param id 特殊收入项目方案ID
     * @return 结果
     */
    @Override
    public int deletePmsRevenueSpecialById(Long id) {
        return pmsRevenueSpecialMapper.deletePmsRevenueSpecialById(id);
    }

    /**
     * 删除特殊收入项目方案信息
     *
     * @param pmsRevenueSpecial 特殊收入项目方案ID
     * @return 结果
     */
    @Override
    public int deletePmsRevenueSpecialBySpecialCode(PmsRevenueSpecial pmsRevenueSpecial) {
        return pmsRevenueSpecialMapper.deletePmsRevenueSpecialBySpecialCode(pmsRevenueSpecial);
    }
}
