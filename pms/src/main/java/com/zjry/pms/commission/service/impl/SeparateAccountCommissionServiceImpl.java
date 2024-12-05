package com.zjry.pms.commission.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.utils.DateUtils;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.common.utils.StringUtils;
import com.zjry.pms.commission.domain.SeparateAccountCommission;
import com.zjry.pms.commission.domain.SeparateAccountCommissionDto;
import com.zjry.pms.commission.domain.SeparateAccountCommissionVo;
import com.zjry.pms.commission.mapper.SeparateAccountCommissionMapper;
import com.zjry.pms.commission.service.ISeparateAccountCommissionService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 独立核算提成比Service业务层处理
 *
 * @author zjry
 * @date 2022-09-27
 */
@Service
public class SeparateAccountCommissionServiceImpl extends ServiceImpl<SeparateAccountCommissionMapper, SeparateAccountCommission> implements ISeparateAccountCommissionService {

    @Autowired
    private SeparateAccountCommissionMapper separateAccountCommissionMapper;

    /**
     * 查询独立核算提成比
     *
     * @param id 独立核算提成比ID
     * @return 独立核算提成比
     */
    @Override
    public SeparateAccountCommission selectSeparateAccountCommissionById(Long id) {
        return separateAccountCommissionMapper.selectSeparateAccountCommissionById(id);
    }

    /**
     * 查询独立核算提成比列表
     *
     * @param separateAccountCommission 独立核算提成比
     * @return 独立核算提成比
     */
    @Override
    public List<SeparateAccountCommission> selectSeparateAccountCommissionList(SeparateAccountCommission separateAccountCommission) {
        return separateAccountCommissionMapper.selectSeparateAccountCommissionList(separateAccountCommission);
    }

    /**
     * 新增独立核算提成比
     *
     * @param data 独立核算提成比
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertSeparateAccountCommission(SeparateAccountCommissionDto data) {
        List<SeparateAccountCommission> list = new ArrayList<>();
        Date commissionDate = data.getCommissionDate();
        JSONArray arr = data.getData();
        arr.forEach(item -> {
            Map<String, Object> commission = (LinkedHashMap) item;
            String deptRoomCode = (String) commission.get("deptRoomCode");
            String deptRoomName = (String) commission.get("deptRoomName");
            for (String k : commission.keySet()) {
                if (!StringUtils.equals(k, "deptRoomCode") && !StringUtils.equals(k, "deptRoomName") && commission.get(k) != null) {
                    SeparateAccountCommission insert = new SeparateAccountCommission();
                    insert.setSortCode(k);
                    insert.setRatio(new BigDecimal(commission.get(k) + ""));
                    insert.setDeptCode(deptRoomCode);
                    insert.setDeptName(deptRoomName);
                    insert.setCommissionDate(commissionDate);
                    insert.setDelFlag("0");
                    Date now = DateUtils.getNowDate();
                    insert.setCreateTime(now);
                    insert.setUpdateTime(now);
                    LoginUser loginUser = SecurityUtils.getLoginUser();
                    if (loginUser != null) {
                        insert.setCreateBy(loginUser.getUser().getUserId().toString());
                        insert.setUpdateBy(loginUser.getUser().getUserId().toString());
                    }

                    list.add(insert);
                }
            }
        });
        //删除当月旧数据
        if (commissionDate != null) {
            separateAccountCommissionMapper.realDelete(commissionDate);
        }
        //插入新数据
        if (CollectionUtils.isNotEmpty(list)) {
            separateAccountCommissionMapper.saveBatch(list);
        }

        return 1;
    }

    /**
     * 修改独立核算提成比
     *
     * @param separateAccountCommission 独立核算提成比
     * @return 结果
     */
    @Override
    public int updateSeparateAccountCommission(SeparateAccountCommission separateAccountCommission) {
        separateAccountCommission.preUpdate();
        return separateAccountCommissionMapper.updateSeparateAccountCommission(separateAccountCommission);
    }

    /**
     * 批量删除独立核算提成比
     *
     * @param ids 需要删除的独立核算提成比ID
     * @return 结果
     */
    @Override
    public int deleteSeparateAccountCommissionByIds(Long[] ids) {
        return separateAccountCommissionMapper.deleteSeparateAccountCommissionByIds(ids);
    }

    /**
     * 删除独立核算提成比信息
     *
     * @param id 独立核算提成比ID
     * @return 结果
     */
    @Override
    public int deleteSeparateAccountCommissionById(Long id) {
        return separateAccountCommissionMapper.deleteSeparateAccountCommissionById(id);
    }

    @Override
    public List<JSONObject> selectSeparateAccountCommissionList2(SeparateAccountCommission separateAccountCommission) {
        List<JSONObject> result = new ArrayList<>();
        List<SeparateAccountCommissionVo> tempList = separateAccountCommissionMapper.getCommissionTemp(separateAccountCommission.getCommissionDate());
        tempList.forEach(item -> {
            String deptRoomCode = item.getDeptRoomCode();
            JSONObject comItem = exitDeptRoom(deptRoomCode, result);
            if (comItem == null) {
                comItem = new JSONObject();
                comItem.put("deptRoomCode", deptRoomCode);
                comItem.put("deptRoomName", item.getDeptRoomName());
                result.add(comItem);
            }
            if(StringUtils.isNotEmpty(item.getSortCode())){
                comItem.put(item.getSortCode(), item.getRatio());
            }
        });
        return result;

    }

    @Override
    public int copy(SeparateAccountCommissionDto dto) {

        Date copyDate = dto.getCopyDate();
        Date nowDate = dto.getNowDate();
        //查询当月数据
        SeparateAccountCommission query = new SeparateAccountCommission();
        query.setDelFlag("0");
        query.setCommissionDate(nowDate);
        List<SeparateAccountCommission> list = separateAccountCommissionMapper.selectSeparateAccountCommissionList(query);
        //删除旧数据
        separateAccountCommissionMapper.realDelete(copyDate);
        list.forEach(item -> {
            item.setCommissionDate(copyDate);
            Date now = DateUtils.getNowDate();
            item.setCreateTime(now);
            item.setUpdateTime(now);
            LoginUser loginUser = SecurityUtils.getLoginUser();
            if (loginUser != null) {
                item.setCreateBy(loginUser.getUser().getUserId().toString());
                item.setUpdateBy(loginUser.getUser().getUserId().toString());
            }
        });
        //插入数据
        separateAccountCommissionMapper.saveBatch(list);
        return 1;
    }

    public JSONObject exitDeptRoom(String deptRoomCode, List<JSONObject> list) {
        for (JSONObject jsonObject : list) {
            String tempCode = jsonObject.getString("deptRoomCode");
            if (StringUtils.equals(tempCode, deptRoomCode)) {
                return jsonObject;
            }
        }
        return null;
    }

}
