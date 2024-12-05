package com.zjry.pms.bonus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.entity.SysUser;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.utils.DateUtils;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.pms.bonus.domain.PmsBonusPersonnel;
import com.zjry.pms.bonus.mapper.PmsBonusPersonnelMapper;
import com.zjry.pms.bonus.service.IPmsBonusPersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 奖金核算Service业务层处理
 *
 * @author zjry
 * @date 2022-10-19
 */
@Service
public class PmsBonusPersonnelServiceImpl extends ServiceImpl<PmsBonusPersonnelMapper, PmsBonusPersonnel> implements IPmsBonusPersonnelService {

    @Autowired
    private PmsBonusPersonnelMapper pmsBonusPersonnelMapper;


    @Override
    public List<PmsBonusPersonnel> selectPmsBonusPersonnelList(PmsBonusPersonnel pmsBonusPersonnel) {
        if(null != pmsBonusPersonnel){
            if(null == pmsBonusPersonnel.getPersonnelDate()){
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
                pmsBonusPersonnel.setPersonnelDate(format1.format(new Date()));
            }
        }
        return pmsBonusPersonnelMapper.selectPmsBonusPersonnelList(pmsBonusPersonnel);
    }

    @Override
    public PmsBonusPersonnel selectPmsBonusPersonnelById(Long id) {
        return pmsBonusPersonnelMapper.selectPmsBonusPersonnelById(id);
    }

    @Override
    public int deletePmsBonusPersonnelByIds(Long[] ids) {
        Date deleteTime = DateUtils.getNowDate();
        String updateBy = "";
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (loginUser != null) {
            updateBy = loginUser.getUser().getUserId().toString();
        }
        return pmsBonusPersonnelMapper.deletePmsBonusPersonnelByIds(ids,deleteTime,updateBy);
    }

    @Override
    @Transactional
    public int insertPmsBonusPersonnelLists(List<PmsBonusPersonnel> pmsBonusPersonnelList) {
        //分割集合，量大修改
//        List<List<PmsBonusEnter>> newList = ListUtil.partition(pmsBonusEnterList, 30);
//        newList.forEach(i -> {
//            System.out.println("集合长度：" + i.size());
//        });

        int i = pmsBonusPersonnelMapper.insertPmsBonusPersonnelLists(pmsBonusPersonnelList);
        return i;
    }
    @Override
    public int updatePmsBonusPersonnel(PmsBonusPersonnel pmsBonusPersonnel) {
        pmsBonusPersonnel.preUpdate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        try {
            pmsBonusPersonnel.setPersonnelTime(dateFormat.parse(pmsBonusPersonnel.getPersonnelDate()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pmsBonusPersonnelMapper.updatePmsBonusPersonnel(pmsBonusPersonnel);
    }
    @Override
    public  Map  importData(List<PmsBonusPersonnel> pmsBonusPersonnelFies, String personnelDate) {
        Map map = new HashMap();
        if(null != pmsBonusPersonnelFies && pmsBonusPersonnelFies.size() > 0){
           //查询之前日期信息，有则删除
            PmsBonusPersonnel pmsBonusPersonnel = new PmsBonusPersonnel();
            pmsBonusPersonnel.setPersonnelDate(personnelDate);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
            try {
                pmsBonusPersonnel.setPersonnelTime(dateFormat.parse(personnelDate));
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<PmsBonusPersonnel> oldPmsbonusPersonnelList = selectPmsBonusPersonnelList( pmsBonusPersonnel);
            if(null != oldPmsbonusPersonnelList && oldPmsbonusPersonnelList.size() > 0){
                //删除旧信息
                List<Long> idList = oldPmsbonusPersonnelList.stream()
                        .map(PmsBonusPersonnel::getId)
                        .collect(Collectors.toList());
                Long [] ids = idList.toArray(new Long[0]);
                deletePmsBonusPersonnelByIds(ids);
            }
           //保存文件信息
            Date now = DateUtils.getNowDate();
            String userId = "";
            LoginUser loginUser = SecurityUtils.getLoginUser();
            if (loginUser != null) {
                userId = loginUser.getUser().getUserId().toString();
            }
            for (PmsBonusPersonnel pmsBonusPersonnelFY : pmsBonusPersonnelFies) {
                pmsBonusPersonnelFY.setPersonnelDate(personnelDate);
                try {
                    pmsBonusPersonnelFY.setPersonnelTime(dateFormat.parse(personnelDate));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                pmsBonusPersonnelFY.setCreateTime(now);
                pmsBonusPersonnelFY.setUpdateTime(now);
                pmsBonusPersonnelFY.setCreateBy(userId);
                pmsBonusPersonnelFY.setUpdateBy(userId);
            }
            int i = insertPmsBonusPersonnelLists( pmsBonusPersonnelFies);

        }
        map.put("result","1");
        return map;
    }
}
