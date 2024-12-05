package com.zjry.pms.commission.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.pms.commission.domain.SeparateAccountCommission;
import com.zjry.pms.commission.domain.SeparateAccountSort;
import com.zjry.pms.commission.mapper.SeparateAccountCommissionMapper;
import com.zjry.pms.commission.mapper.SeparateAccountSortMapper;
import com.zjry.pms.commission.service.ISeparateAccountSortService;
import com.zjry.pms.common.Constants;
import com.zjry.pms.common.service.ISysFlowcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 独立核算提成比类别Service业务层处理
 *
 * @author zjry
 * @date 2022-09-27
 */
@Service
public class SeparateAccountSortServiceImpl extends ServiceImpl<SeparateAccountSortMapper, SeparateAccountSort> implements ISeparateAccountSortService {

    @Autowired
    private SeparateAccountSortMapper separateAccountSortMapper;

    @Autowired
    private ISysFlowcodeService flowcodeService;

    @Autowired
    private SeparateAccountCommissionMapper separateAccountCommissionMapper;

    /**
     * 查询独立核算提成比类别
     *
     * @param id 独立核算提成比类别ID
     * @return 独立核算提成比类别
     */
    @Override
    public SeparateAccountSort selectSeparateAccountSortById(Long id) {
        return separateAccountSortMapper.selectSeparateAccountSortById(id);
    }

    /**
     * 查询独立核算提成比类别列表
     *
     * @param separateAccountSort 独立核算提成比类别
     * @return 独立核算提成比类别
     */
    @Override
    public List<SeparateAccountSort> selectSeparateAccountSortList(SeparateAccountSort separateAccountSort) {
        return separateAccountSortMapper.selectSeparateAccountSortList(separateAccountSort);
    }

    /**
     * 新增独立核算提成比类别
     *
     * @param separateAccountSort 独立核算提成比类别
     * @return 结果
     */
    @Override
    public AjaxResult insertSeparateAccountSort(SeparateAccountSort separateAccountSort) {
        separateAccountSort.preInsert();

        Integer count = new LambdaQueryChainWrapper<>(separateAccountSortMapper)
                .eq(SeparateAccountSort::getSortName, separateAccountSort.getSortName())
                .count();
        if (count > 0) {
            return AjaxResult.error("类别名称重复");
        }
        separateAccountSort.setSortCode(Constants.HSTCLB + flowcodeService.getEntityFlowcodeNoCode(Constants.HSTCLB, 6));
        int i = separateAccountSortMapper.insertSeparateAccountSort(separateAccountSort);
        if (i > 0) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error();
        }
    }

    /**
     * 修改独立核算提成比类别
     *
     * @param separateAccountSort 独立核算提成比类别
     * @return 结果
     */
    @Override
    public AjaxResult updateSeparateAccountSort(SeparateAccountSort separateAccountSort) {
        separateAccountSort.preUpdate();

        Integer count = new LambdaQueryChainWrapper<>(separateAccountSortMapper)
                .eq(SeparateAccountSort::getSortName, separateAccountSort.getSortName())
                .ne(SeparateAccountSort::getId, separateAccountSort.getId())
                .count();
        if (count > 0) {
            return AjaxResult.error("类别名称重复");
        }
        separateAccountSortMapper.updateSeparateAccountSort(separateAccountSort);
        return AjaxResult.success();
    }

    /**
     * 批量删除独立核算提成比类别
     *
     * @param ids 需要删除的独立核算提成比类别ID
     * @return 结果
     */
    @Override
    public AjaxResult deleteSeparateAccountSortByIds(Long[] ids) {
        if (ids != null && ids.length > 0) {
            List<SeparateAccountSort> list = new LambdaQueryChainWrapper<>(separateAccountSortMapper).select(SeparateAccountSort::getSortCode).in(SeparateAccountSort::getId, ids).list();
            List<String> sortCodes = list.stream().map(SeparateAccountSort::getSortCode).collect(Collectors.toList());
            Integer count = new LambdaQueryChainWrapper<>(separateAccountCommissionMapper).in(SeparateAccountCommission::getSortCode, sortCodes).count();
            if (count > 0) {
                return AjaxResult.error("类别被引用,无法删除");
            }
        }
        separateAccountSortMapper.deleteSeparateAccountSortByIds(ids);
        return AjaxResult.success();
    }

    /**
     * 删除独立核算提成比类别信息
     *
     * @param id 独立核算提成比类别ID
     * @return 结果
     */
    @Override
    public int deleteSeparateAccountSortById(Long id) {
        return separateAccountSortMapper.deleteSeparateAccountSortById(id);
    }
}
