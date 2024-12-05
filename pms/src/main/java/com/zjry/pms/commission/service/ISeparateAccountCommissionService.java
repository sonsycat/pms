package com.zjry.pms.commission.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.commission.domain.SeparateAccountCommission;
import com.zjry.pms.commission.domain.SeparateAccountCommissionDto;

/**
 * 独立核算提成比Service接口
 *
 * @author zjry
 * @date 2022-09-27
 */
public interface ISeparateAccountCommissionService extends IService<SeparateAccountCommission> {

    /**
     * 查询独立核算提成比
     *
     * @param id 独立核算提成比ID
     * @return 独立核算提成比
     */
    public SeparateAccountCommission selectSeparateAccountCommissionById(Long id);

    /**
     * 查询独立核算提成比列表
     *
     * @param separateAccountCommission 独立核算提成比
     * @return 独立核算提成比集合
     */
    public List<SeparateAccountCommission> selectSeparateAccountCommissionList(SeparateAccountCommission separateAccountCommission);

    /**
     * 新增独立核算提成比
     *
     * @param data 独立核算提成比
     * @return 结果
     *
     */
    public int insertSeparateAccountCommission(SeparateAccountCommissionDto data);

    /**
     * 修改独立核算提成比
     *
     * @param separateAccountCommission 独立核算提成比
     * @return 结果
     */
    public int updateSeparateAccountCommission(SeparateAccountCommission separateAccountCommission);

    /**
     * 批量删除独立核算提成比
     *
     * @param ids 需要删除的独立核算提成比ID
     * @return 结果
     */
    public int deleteSeparateAccountCommissionByIds(Long[] ids);

    /**
     * 删除独立核算提成比信息
     *
     * @param id 独立核算提成比ID
     * @return 结果
     */
    public int deleteSeparateAccountCommissionById(Long id);

    List<JSONObject> selectSeparateAccountCommissionList2(SeparateAccountCommission separateAccountCommission);

    int copy(SeparateAccountCommissionDto dto);
}
