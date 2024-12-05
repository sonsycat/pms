package com.zjry.pms.commission.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.commission.domain.SeparateAccountSort;

/**
 * 独立核算提成比类别Mapper接口
 * 
 * @author zjry
 * @date 2022-09-27
 */
public interface SeparateAccountSortMapper extends BaseMapper<SeparateAccountSort> {
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
    public int insertSeparateAccountSort(SeparateAccountSort separateAccountSort);

    /**
     * 修改独立核算提成比类别
     * 
     * @param separateAccountSort 独立核算提成比类别
     * @return 结果
     */
    public int updateSeparateAccountSort(SeparateAccountSort separateAccountSort);

    /**
     * 删除独立核算提成比类别
     * 
     * @param id 独立核算提成比类别ID
     * @return 结果
     */
    public int deleteSeparateAccountSortById(Long id);

    /**
     * 批量删除独立核算提成比类别
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSeparateAccountSortByIds(Long[] ids);
}
