package com.zjry.pms.bonus.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import com.zjry.pms.encode.service.IHrEncodeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.bonus.mapper.PmsDeptSortDetailMapper;
import com.zjry.pms.bonus.domain.PmsDeptSortDetail;
import com.zjry.pms.bonus.service.IPmsDeptSortDetailService;

/**
 * 核算单位类别Service业务层处理
 * 
 * @author zjry
 * @date 2022-09-27
 */
@Service
public class PmsDeptSortDetailServiceImpl extends ServiceImpl<PmsDeptSortDetailMapper, PmsDeptSortDetail> implements IPmsDeptSortDetailService {
    @Autowired
    private PmsDeptSortDetailMapper pmsDeptSortDetailMapper;
    @Autowired
    private IHrEncodeRuleService hrEncodeRuleService;

    /**
     * 查询核算单位类别
     * 
     * @param id 核算单位类别ID
     * @return 核算单位类别
     */
    @Override
    public PmsDeptSortDetail selectPmsDeptSortDetailById(Long id) {
        return pmsDeptSortDetailMapper.selectPmsDeptSortDetailById(id);
    }

    /**
     * 查询核算单位类别列表
     * 
     * @param pmsDeptSortDetail 核算单位类别
     * @return 核算单位类别
     */
    @Override
    public List<PmsDeptSortDetail> selectPmsDeptSortDetailList(PmsDeptSortDetail pmsDeptSortDetail) {
        return pmsDeptSortDetailMapper.selectPmsDeptSortDetailList(pmsDeptSortDetail);
    }

    /**
     * 新增核算单位类别
     * 
     * @param pmsDeptSortDetail 核算单位类别
     * @return 结果
     */
    @Override
    public int insertPmsDeptSortDetail(PmsDeptSortDetail pmsDeptSortDetail) {
    	pmsDeptSortDetail.preInsert();
        pmsDeptSortDetail.setEditAllow(PmsDeptSortDetail.EDIT_ALLOW_YES);
        pmsDeptSortDetail.setSortCode(hrEncodeRuleService.generateEncode("pms_dept_sort_detail", "SORT_CODE"));
        return pmsDeptSortDetailMapper.insertPmsDeptSortDetail(pmsDeptSortDetail);
    }

    /**
     * 修改核算单位类别
     * 
     * @param pmsDeptSortDetail 核算单位类别
     * @return 结果
     */
    @Override
    public int updatePmsDeptSortDetail(PmsDeptSortDetail pmsDeptSortDetail) {
    	pmsDeptSortDetail.preUpdate();
        return pmsDeptSortDetailMapper.updatePmsDeptSortDetail(pmsDeptSortDetail);
    }

    /**
     * 批量删除核算单位类别
     * 
     * @param ids 需要删除的核算单位类别ID
     * @return 结果
     */
    @Override
    public int deletePmsDeptSortDetailByIds(Long[] ids) {
        return pmsDeptSortDetailMapper.deletePmsDeptSortDetailByIds(ids);
    }

    /**
     * 删除核算单位类别信息
     * 
     * @param id 核算单位类别ID
     * @return 结果
     */
    @Override
    public int deletePmsDeptSortDetailById(Long id) {
        return pmsDeptSortDetailMapper.deletePmsDeptSortDetailById(id);
    }
}
