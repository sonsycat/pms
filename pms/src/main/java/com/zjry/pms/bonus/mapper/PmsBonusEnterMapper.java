package com.zjry.pms.bonus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.bonus.domain.PmsBonusEnter;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 奖金核算Mapper接口
 * 
 * @author zjry
 * @date 2022-10-19
 */
public interface PmsBonusEnterMapper extends BaseMapper<PmsBonusEnter> {

    List<PmsBonusEnter> selectPmsBonusEnterList(@Param("pmsBonusEnter")PmsBonusEnter pmsBonusEnter,@Param("ids")Long[] ids );

    PmsBonusEnter selectPmsBonusEnterById(Long id);

    int insertPmsBonusEnterLists(@Param("pmsBonusEnterList") List<PmsBonusEnter> pmsBonusEnterList);

    int deletePmsBonusEnterByIds(@Param("ids") Long[] ids, @Param("deleteTime") Date deleteTime, @Param("updateBy")String updateBy);

    int updatePmsBonusEnter(PmsBonusEnter pmsBonusEnter);

    List<PmsBonusEnter> selectMesByDateAndCode(@Param("deptCode")String deptCode, @Param("enterDate")String enterDate);
}
