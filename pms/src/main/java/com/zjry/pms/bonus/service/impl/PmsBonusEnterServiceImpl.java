package com.zjry.pms.bonus.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.domain.entity.SysUser;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.utils.DateUtils;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.pms.bonus.domain.PmsBonusEnter;
import com.zjry.pms.bonus.domain.PmsBonusPersonAudit;
import com.zjry.pms.bonus.mapper.PmsBonusEnterMapper;
import com.zjry.pms.bonus.service.IPmsBonusEnterService;
import com.zjry.system.domain.SysDeptRoom;
import com.zjry.system.service.ISysDeptRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 奖金核算Service业务层处理
 *
 * @author zjry
 * @date 2022-10-19
 */
@Service
public class PmsBonusEnterServiceImpl extends ServiceImpl<PmsBonusEnterMapper, PmsBonusEnter> implements IPmsBonusEnterService {

    @Autowired
    private PmsBonusEnterMapper pmsBonusEnterMapper;
    @Autowired
    private ISysDeptRoomService sysDeptRoomService;


    @Override
    public List<PmsBonusEnter> selectPmsBonusEnterList(PmsBonusEnter pmsBonusEnter) {
        if(null != pmsBonusEnter){
            if(null == pmsBonusEnter.getEnterDate()){
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
                pmsBonusEnter.setEnterDate(format1.format(new Date()));
            }
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        List<SysDeptRoom> sysDeptRoomList = new ArrayList<>();
        if (loginUser != null) {
            SysDeptRoom sysDeptRoom = new SysDeptRoom();
            sysDeptRoom.setDeptId(loginUser.getUser().getDeptId());
            sysDeptRoomList = sysDeptRoomService.selectSysDeptRoomList(sysDeptRoom);
        }

        if(null != sysDeptRoomList && sysDeptRoomList.size() > 0) {
            List<Long> idList = sysDeptRoomList.stream()
                    .map(SysDeptRoom::getDeptRoomId)
                    .collect(Collectors.toList());
            Long[] ids = idList.toArray(new Long[0]);
            return pmsBonusEnterMapper.selectPmsBonusEnterList(pmsBonusEnter,ids );
        }else{
            return null;
        }

    }

    @Override
    public PmsBonusEnter selectPmsBonusEnterById(Long id) {
        return pmsBonusEnterMapper.selectPmsBonusEnterById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deletePmsBonusEnterByIds(Long[] ids) {
        Date deleteTime = DateUtils.getNowDate();
        String updateBy = "";
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (loginUser != null) {
            updateBy = loginUser.getUser().getUserId().toString();
        }
        return pmsBonusEnterMapper.deletePmsBonusEnterByIds(ids,deleteTime,updateBy);
    }

    @Override
    @Transactional
    public int insertPmsBonusEnterLists(List<PmsBonusEnter> pmsBonusEnterList) {
        //分割集合，量大修改
//        List<List<PmsBonusEnter>> newList = ListUtil.partition(pmsBonusEnterList, 30);
//        newList.forEach(i -> {
//            System.out.println("集合长度：" + i.size());
//        });

        int i = pmsBonusEnterMapper.insertPmsBonusEnterLists(pmsBonusEnterList);
        return i;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePmsBonusEnter(PmsBonusEnter pmsBonusEnter) {
        pmsBonusEnter.preUpdate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        try {
            pmsBonusEnter.setEnterTime(dateFormat.parse(pmsBonusEnter.getEnterDate()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pmsBonusEnterMapper.updatePmsBonusEnter(pmsBonusEnter);
    }
    @Override
    public  Map  importData(List<PmsBonusEnter> pmsBonusEnterFies, String enterDate) {
        Map map = new HashMap();
        if(null != pmsBonusEnterFies && pmsBonusEnterFies.size() > 0){
            //校验科室信息
           map =  checkDept(pmsBonusEnterFies);
           if("2".equals(map.get("result").toString())){
               return map;
            }
           //查询之前日期信息，有则删除
            String deptCode = pmsBonusEnterFies.get(0).getDeptCode();
            List<PmsBonusEnter> oldPmsbonusEnterList = pmsBonusEnterMapper.selectMesByDateAndCode( deptCode,enterDate);
            if(null != oldPmsbonusEnterList && oldPmsbonusEnterList.size() > 0){
                //删除旧信息
                List<Long> idList = oldPmsbonusEnterList.stream()
                        .map(PmsBonusEnter::getId)
                        .collect(Collectors.toList());
                Long [] ids = idList.toArray(new Long[0]);
                deletePmsBonusEnterByIds(ids);
            }
           //保存文件信息
            Date now = DateUtils.getNowDate();
            String userId = "";
            LoginUser loginUser = SecurityUtils.getLoginUser();
            if (loginUser != null) {
                userId = loginUser.getUser().getUserId().toString();
            }
            for (PmsBonusEnter pmsBonusEnterFY : pmsBonusEnterFies) {
                pmsBonusEnterFY.setEnterDate(enterDate);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
                    pmsBonusEnterFY.setEnterTime(dateFormat.parse(enterDate));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                pmsBonusEnterFY.setCreateTime(now);
                pmsBonusEnterFY.setUpdateTime(now);
                pmsBonusEnterFY.setCreateBy(userId);
                pmsBonusEnterFY.setUpdateBy(userId);
            }
            int i = insertPmsBonusEnterLists( pmsBonusEnterFies);

        }
        map.put("result","1");
        return map;
    }
    private Map checkDept(List<PmsBonusEnter> pmsBonusEnterFies){
        Map map = new HashMap();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if(null != loginUser){
            SysUser sysUser = loginUser.getUser();
            if(null != sysUser){
                String deptId = sysUser.getDeptRoomId();
                String deptName = sysUser.getDeptRoomName();
                List<PmsBonusEnter> filterList = pmsBonusEnterFies.stream()
                        .filter(item -> !item.getDeptCode().equals(deptId) || !item.getDeptName().equals(deptName))
                        .collect(Collectors.toList());
                if(null != filterList && filterList.size()>0) {
                    map.put("result", "2");
                    map.put("mes", "文件科室编码与科室名称不合规，正确的可是编码" + deptId + ",正确的科室名称" + deptName);
                    return map;
                }
            }else{
                map.put("result","2");
                map.put("mes","登入用户权限异常，请重新登入！");
                return map;
            }
        }else{
            map.put("result","2");
            map.put("mes","登入用户权限异常，请重新登入！");
            return map;
        }
        map.put("result","1");
        return map;
    }
}
