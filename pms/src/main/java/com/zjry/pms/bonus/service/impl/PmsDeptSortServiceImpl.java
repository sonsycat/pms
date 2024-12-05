package com.zjry.pms.bonus.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.utils.DateUtils;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.common.utils.StringUtils;
import com.zjry.pms.bonus.domain.PmsDeptSortDto;
import com.zjry.pms.commission.domain.SeparateAccountCommission;
import com.zjry.pms.common.utils.UserUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.bonus.mapper.PmsDeptSortMapper;
import com.zjry.pms.bonus.domain.PmsDeptSort;
import com.zjry.pms.bonus.service.IPmsDeptSortService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 核算单位类别设置Service业务层处理
 * 
 * @author zjry
 * @date 2022-09-27
 */
@Service
public class PmsDeptSortServiceImpl extends ServiceImpl<PmsDeptSortMapper, PmsDeptSort> implements IPmsDeptSortService {
    @Autowired
    private PmsDeptSortMapper pmsDeptSortMapper;

    /**
     * 查询核算单位类别设置
     * 
     * @param id 核算单位类别设置ID
     * @return 核算单位类别设置
     */
    @Override
    public PmsDeptSort selectPmsDeptSortById(Long id) {
        return pmsDeptSortMapper.selectPmsDeptSortById(id);
    }

    /**
     * 查询核算单位类别设置列表
     * 
     * @param pmsDeptSort 核算单位类别设置
     * @return 核算单位类别设置
     */
    @Override
    public List<PmsDeptSort> selectPmsDeptSortList(PmsDeptSort pmsDeptSort) {
        return pmsDeptSortMapper.selectPmsDeptSortList(pmsDeptSort);
    }

    /**
     * 新增核算单位类别设置
     * 
     * @param pmsDeptSort 核算单位类别设置
     * @return 结果
     */
    @Override
    public int insertPmsDeptSort(PmsDeptSort pmsDeptSort) {
    	pmsDeptSort.preInsert();
        return pmsDeptSortMapper.insertPmsDeptSort(pmsDeptSort);
    }

    /**
     * 修改核算单位类别设置
     * 
     * @param pmsDeptSort 核算单位类别设置
     * @return 结果
     */
    @Override
    public int updatePmsDeptSort(PmsDeptSort pmsDeptSort) {
    	pmsDeptSort.preUpdate();
        return pmsDeptSortMapper.updatePmsDeptSort(pmsDeptSort);
    }

    /**
     * 批量删除核算单位类别设置
     * 
     * @param ids 需要删除的核算单位类别设置ID
     * @return 结果
     */
    @Override
    public int deletePmsDeptSortByIds(Long[] ids) {
        return pmsDeptSortMapper.deletePmsDeptSortByIds(ids);
    }

    /**
     * 删除核算单位类别设置信息
     * 
     * @param id 核算单位类别设置ID
     * @return 结果
     */
    @Override
    public int deletePmsDeptSortById(Long id) {
        return pmsDeptSortMapper.deletePmsDeptSortById(id);
    }

    @Override
    public List<PmsDeptSort> queryList(PmsDeptSort pmsDeptSort){
        return pmsDeptSortMapper.queryList(pmsDeptSort);
    }

    @Override
    public void copyData(PmsDeptSort pmsDeptSort){
        pmsDeptSortMapper.copyData(pmsDeptSort);
    }

    /**
     * 保存列表数据
     *
     * @param data 核算单位配置列表数据
     * @return 结果
     */
    @Override
    @Transactional
    public int saveData(PmsDeptSortDto data){
        List<PmsDeptSort> list = data.getData();
        if(CollectionUtils.isNotEmpty(list)){
            list.forEach(sort -> {
                sort.setCompCode(UserUtils.getSysUser().getDeptId());
                sort.setNowDate(data.getNowDate());
                sort.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
                sort.preInsert();
            });
        }

        PmsDeptSort pmsDeptSort = new PmsDeptSort();
        pmsDeptSort.setNowDate(data.getNowDate());

        //删除旧数据
        pmsDeptSortMapper.deleteBatch(pmsDeptSort);
        //插入新数据
        if (CollectionUtils.isNotEmpty(list)) {
            pmsDeptSortMapper.insertBatch(list);
        }
        return 1;
    }
}
