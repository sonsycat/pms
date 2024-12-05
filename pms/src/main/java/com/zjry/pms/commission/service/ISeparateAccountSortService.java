package com.zjry.pms.commission.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.pms.commission.domain.SeparateAccountSort;

/**
 * 独立核算提成比类别Service接口
 *
 * @author zjry
 * @date 2022-09-27
 */
public interface ISeparateAccountSortService extends IService<SeparateAccountSort> {

    /**
     * 查询独立核算提成比类别
     *
     * @param id 独立核算提成比类别ID
     * @return 独立核算提成比类别
     */
    public SeparateAccountSort selectSeparateAccountSortById(Long id);

    /**
     * 查询独立核算提成比类别列表
     *
     * @param separateAccountSort 独立核算提成比类别
     * @return 独立核算提成比类别集合
     */
    public List<SeparateAccountSort> selectSeparateAccountSortList(SeparateAccountSort separateAccountSort);

    /**
     * 新增独立核算提成比类别
     *
     * @param separateAccountSort 独立核算提成比类别
     * @return 结果
     */
    public AjaxResult insertSeparateAccountSort(SeparateAccountSort separateAccountSort);

    /**
     * 修改独立核算提成比类别
     *
     * @param separateAccountSort 独立核算提成比类别
     * @return 结果
     */
    public AjaxResult updateSeparateAccountSort(SeparateAccountSort separateAccountSort);

    /**
     * 批量删除独立核算提成比类别
     *
     * @param ids 需要删除的独立核算提成比类别ID
     * @return 结果
     */
    public AjaxResult deleteSeparateAccountSortByIds(Long[] ids);

    /**
     * 删除独立核算提成比类别信息
     *
     * @param id 独立核算提成比类别ID
     * @return 结果
     */
    public int deleteSeparateAccountSortById(Long id);
}
