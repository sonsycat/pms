package com.zjry.pms.bonus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.pms.bonus.domain.PmsBonusEnter;

import java.util.List;
import java.util.Map;

/**
 * 奖金核算Service接口
 *
 * @author zjry
 * @date 2022-10-19
 */
public interface IPmsBonusEnterService extends IService<PmsBonusEnter> {


    List<PmsBonusEnter> selectPmsBonusEnterList(PmsBonusEnter pmsBonusEnter);

    PmsBonusEnter selectPmsBonusEnterById(Long id);

    int deletePmsBonusEnterByIds(Long[] ids);

    int insertPmsBonusEnterLists(List<PmsBonusEnter> pmsBonusEnterList);

    Map  importData(List<PmsBonusEnter> pmsBonusEnterFies, String enterDate);

    int updatePmsBonusEnter(PmsBonusEnter pmsBonusEnter);

}
