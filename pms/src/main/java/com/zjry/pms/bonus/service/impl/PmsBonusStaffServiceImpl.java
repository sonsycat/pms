package com.zjry.pms.bonus.service.impl;

import com.zjry.pms.bonus.domain.PmsBonusStaff;
import com.zjry.pms.bonus.mapper.PmsBonusStaffMapper;
import com.zjry.pms.bonus.service.IPmsBonusStaffService;
import com.zjry.pms.evaluate.domain.PmsEvaluateData;
import com.zjry.pms.system.domain.PmsSystemStaff;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
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
public class PmsBonusStaffServiceImpl  implements IPmsBonusStaffService {
    @Autowired
    private PmsBonusStaffMapper pmsBonusStaffMapper;


    @Override
    public List<PmsBonusStaff> selectListBonusStaff(PmsEvaluateData pmsEvaluateData) {
        return pmsBonusStaffMapper.selectListBonusStaff(pmsEvaluateData);
    }

    @Override
    @Transactional
    public Map<String, String> saveBonusStaff(List<PmsBonusStaff> pmsBonusStaffList) {
        Map<String, String> result = new HashMap<>();
        //判断奖金金额是否满足 根据日期和科室查询奖金总额度
        String deptCode = pmsBonusStaffList.get(0).getDeptCode();
        String actDate = pmsBonusStaffList.get(0).getActDate();
        String bonusTotalAmount = pmsBonusStaffMapper.selectBonusTotalAmount(deptCode,actDate);
        BigDecimal btotal = StringUtils.isEmpty(bonusTotalAmount)? BigDecimal.ZERO:new BigDecimal(bonusTotalAmount);
        //查询已分配的金额
        String bonusAssignedAmount = pmsBonusStaffMapper.selectBonusAssignedAmount(deptCode,actDate);
        BigDecimal assigned = StringUtils.isEmpty(bonusAssignedAmount)? BigDecimal.ZERO:new BigDecimal(bonusAssignedAmount);
        //剩余可分配金额
        BigDecimal beLeftAmount = btotal.subtract(assigned);
        BigDecimal total = pmsBonusStaffList.stream()
                .map(PmsBonusStaff::getBonusAmount) // 获取每个Item的price属性
                .reduce(BigDecimal.ZERO, BigDecimal::add); // 对其进行求和
        int flag = total.compareTo(beLeftAmount);

        if (flag > 0) {
            result.put("result","1");
            result.put("mes","可分配奖金为："+beLeftAmount+",实际分配："+total);
            return result;
        }
        //有id的是修改，没有id是提交保存
        Map<String, List<PmsBonusStaff>> map = pmsBonusStaffList.stream()
                .collect(Collectors.groupingBy(item -> {
                    if (item.getId() == null || item.getId().equals("")) {
                        return "insertData";
                    }
                    return "updateData";
                }));
        //根据有无id分组，批量保存或者修改
        if(null!=map.get("insertData") && map.get("insertData").size()>0){
            //批量新增数据
            List<PmsBonusStaff> dataList= map.get("insertData");
            for (PmsBonusStaff pmsBonusStaff : dataList) {
                pmsBonusStaff.preInsert();
            }
            pmsBonusStaffMapper.insertData(dataList);

        }
        if(null!=map.get("updateData") && map.get("updateData").size()>0){
            //批量新增数据
            List<PmsBonusStaff> dataList= map.get("updateData");
            for (PmsBonusStaff pmsBonusStaff : dataList) {
                pmsBonusStaff.preUpdate();
            }
            pmsBonusStaffMapper.updateData(dataList);

        }
        result.put("result","2");
        result.put("mes","保存成功");
        return result;
    }

    @Override
    @Transactional
    public Map importData(List<PmsBonusStaff> pmsBonusStaffList, String deptCode,String actDate) {
        Map<String, String> result = new HashMap<>();
        //判断文件数据是否都属于deptCode数据
        for (PmsBonusStaff pmsBonusStaff : pmsBonusStaffList) {
            if(!deptCode.equals(pmsBonusStaff.getDeptCode())){
                result.put("result","1");
                result.put("mes","只可以导入展示的科室数据");
                return result;
            }
        }
        //判断员工是否录入
        //查询科室全部员工
        List<PmsSystemStaff> pmsSystemStaffList = pmsBonusStaffMapper.selectSystemStaffByDeptCode(deptCode);
        if(null == pmsSystemStaffList || pmsSystemStaffList.size()<=0){
            result.put("result","1");
            result.put("mes","请先导入员工数据");
            return result;
        }
        String mes = "";
        for (PmsBonusStaff pmsBonusStaff : pmsBonusStaffList) {
            boolean flag = false;
            for (PmsSystemStaff pmsSystemStaff : pmsSystemStaffList) {
                if(pmsBonusStaff.getStaffCode().equals(pmsSystemStaff.getStaffCode()) &&
                   pmsBonusStaff.getStaffName().equals(pmsSystemStaff.getStaffName())){
                    flag = true;
                    continue;
                }
            }
            if(!flag){
                mes = mes+pmsBonusStaff.getStaffName()+",";
            }
        }
        if(null!=mes && mes.length()>0){
            result.put("result","1");
            result.put("mes",mes+"不存在员工数据");
            return result;
        }
        //判断奖金金额是否满足 根据日期和科室查询奖金总额度
        String bonusTotalAmount = pmsBonusStaffMapper.selectBonusTotalAmount(deptCode,actDate);
        BigDecimal total = pmsBonusStaffList.stream()
                .map(PmsBonusStaff::getBonusAmount) // 获取每个Item的price属性
                .reduce(BigDecimal.ZERO, BigDecimal::add); // 对其进行求和
        int flag = total.compareTo(new BigDecimal(bonusTotalAmount));

        if (flag > 0) {
            result.put("result","1");
            result.put("mes","可分配奖金为："+bonusTotalAmount+",实际分配："+total);
            return result;
        }
        //按照文件数据批量删除之前数据
        pmsBonusStaffMapper.deleteBonusStaff(deptCode,actDate);
        //保存现有数据
        SimpleDateFormat sim1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        SimpleDateFormat sim2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (PmsBonusStaff pmsBonusStaff : pmsBonusStaffList) {
            pmsBonusStaff.preInsert();
            String s1 = pmsBonusStaff.getActDate();
            try {
                Date date = sim1.parse(s1);
                pmsBonusStaff.setActDate(sim2.format(date));
            }catch (ParseException e ){
                e.printStackTrace();
            }
        }
        pmsBonusStaffMapper.insertData(pmsBonusStaffList);
        result.put("result","2");
        result.put("mes","导入成功");
        return result;
    }
}
