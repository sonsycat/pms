package com.zjry.pms.commission.mapper;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.commission.domain.SeparateAccountCommission;
import com.zjry.pms.commission.domain.SeparateAccountCommissionDto;
import com.zjry.pms.commission.domain.SeparateAccountCommissionVo;
import org.apache.ibatis.annotations.Param;
import org.json.JSONObject;

/**
 * 独立核算提成比Mapper接口
 *
 * @author zjry
 * @date 2022-09-27
 */
public interface SeparateAccountCommissionMapper extends BaseMapper<SeparateAccountCommission> {

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
     * @param separateAccountCommission 独立核算提成比
     * @return 结果
     */
    public int insertSeparateAccountCommission(SeparateAccountCommission separateAccountCommission);

    /**
     * 修改独立核算提成比
     *
     * @param separateAccountCommission 独立核算提成比
     * @return 结果
     */
    public int updateSeparateAccountCommission(SeparateAccountCommission separateAccountCommission);

    /**
     * 删除独立核算提成比
     *
     * @param id 独立核算提成比ID
     * @return 结果
     */
    public int deleteSeparateAccountCommissionById(Long id);

    /**
     * 批量删除独立核算提成比
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSeparateAccountCommissionByIds(Long[] ids);

    List<SeparateAccountCommissionVo> getCommissionTemp(@Param("commissionDate") Date commissionDate);

    void realDelete(@Param("date") Date date);

    /**
     * 批量插入
     * @param list
     */
    void saveBatch(@Param("list") List<SeparateAccountCommission> list);
}
