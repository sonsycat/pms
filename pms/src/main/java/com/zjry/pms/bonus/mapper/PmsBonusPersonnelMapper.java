package com.zjry.pms.bonus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.bonus.domain.PmsBonusEnter;
import com.zjry.pms.bonus.domain.PmsBonusPersonnel;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 奖金核算Mapper接口
 * 
 * @author zjry
 * @date 2022-10-19
 */
public interface PmsBonusPersonnelMapper extends BaseMapper<PmsBonusPersonnel> {

    List<PmsBonusPersonnel> selectPmsBonusPersonnelList(PmsBonusPersonnel pmsBonusPersonnel);

    PmsBonusPersonnel selectPmsBonusPersonnelById(Long id);

    int insertPmsBonusPersonnelLists(@Param("pmsBonusPersonnelList") List<PmsBonusPersonnel> pmsBonusPersonnelList);

    int deletePmsBonusPersonnelByIds(@Param("ids") Long[] ids, @Param("deleteTime") Date deleteTime, @Param("updateBy")String updateBy);

    int updatePmsBonusPersonnel(PmsBonusPersonnel pmsBonusPersonnel);

}
