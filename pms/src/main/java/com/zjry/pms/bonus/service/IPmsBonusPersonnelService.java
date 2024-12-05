package com.zjry.pms.bonus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.bonus.domain.PmsBonusPersonnel;

import java.util.List;
import java.util.Map;

/**
 * 奖金核算Service接口
 *
 * @author zjry
 * @date 2022-10-19
 */
public interface IPmsBonusPersonnelService extends IService<PmsBonusPersonnel> {


    List<PmsBonusPersonnel> selectPmsBonusPersonnelList(PmsBonusPersonnel pmsBonusPersonnel);

    PmsBonusPersonnel selectPmsBonusPersonnelById(Long id);

    int deletePmsBonusPersonnelByIds(Long[] ids);

    int insertPmsBonusPersonnelLists(List<PmsBonusPersonnel> pmsBonusPersonnelList);

    Map  importData(List<PmsBonusPersonnel> pmsBonusPersonnelFies, String personnelDate);

    int updatePmsBonusPersonnel(PmsBonusPersonnel pmsBonusPersonnel);

}
