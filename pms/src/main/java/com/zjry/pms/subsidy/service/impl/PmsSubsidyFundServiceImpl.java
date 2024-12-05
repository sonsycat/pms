package com.zjry.pms.subsidy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.utils.DateUtils;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.pms.bonus.domain.PmsBonusEnter;
import com.zjry.pms.bonus.domain.PmsBonusPersonnel;
import com.zjry.pms.subsidy.domain.PmsSubsidyAdjustBenefit;
import com.zjry.pms.subsidy.domain.PmsSubsidyFund;
import com.zjry.pms.subsidy.domain.PmsSubsidyFundBalance;
import com.zjry.pms.subsidy.mapper.PmsSubsidyAdjustBenefitMapper;
import com.zjry.pms.subsidy.mapper.PmsSubsidyFundMapper;
import com.zjry.pms.subsidy.service.IPmsSubsidyAdjustBenefitService;
import com.zjry.pms.subsidy.service.IPmsSubsidyFundService;
import com.zjry.system.domain.SysDeptRoom;
import com.zjry.system.service.ISysDeptRoomService;
import com.zjry.system.service.impl.SysDeptRoomServiceImpl;
import io.jsonwebtoken.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 科室效益调整Service业务层处理
 * 
 * @author maozt
 * @date 2022-08-17
 */
@Service
public class PmsSubsidyFundServiceImpl extends ServiceImpl<PmsSubsidyFundMapper, PmsSubsidyFund> implements IPmsSubsidyFundService {
    @Autowired
    private PmsSubsidyFundMapper pmsSubsidyFundMapper;
    @Autowired
    private ISysDeptRoomService sysDeptRoomService;
    @Override
    public PmsSubsidyFund selectPmsSubsidyFundById(Long id) {
        return pmsSubsidyFundMapper.selectPmsSubsidyFundById(id);
    }

    /**
     * 查询科室效益调整列表
     * 
     * @param pmsSubsidyFund 科室效益调整
     * @return 科室效益调整
     */
    @Override
    public List<PmsSubsidyFund> selectPmsSubsidyFundList(PmsSubsidyFund pmsSubsidyFund) {
        if(null == pmsSubsidyFund.getFundTime()){
            pmsSubsidyFund.setFundTime(new Date());
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        String date = dateFormat.format(pmsSubsidyFund.getFundTime());
        try {
            Date dateTime = dateFormat.parse(date);
            pmsSubsidyFund.setFundTime(dateTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        List<PmsSubsidyFund> list =  pmsSubsidyFundMapper.selectPmsSubsidyFundList(pmsSubsidyFund);
        if(null != list && list.size() > 0){
            //根据科室查询科基金余额
            String fundBalance = pmsSubsidyFundMapper.selectFundBalanceByDeptCode(pmsSubsidyFund.getDeptCode());
            if(null == fundBalance){
                fundBalance = "0";
            }
            for (PmsSubsidyFund subsidyFund : list) {
                subsidyFund.setFundBalance(new BigDecimal(fundBalance));
            }
        }
        return list;
    }

    /**
     * 新增科室效益调整
     * 
     * @param pmsSubsidyFund 科室效益调整
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertPmsSubsidyFund(PmsSubsidyFund pmsSubsidyFund) {
        //判断月份
        Map map = judgeData(pmsSubsidyFund);
        if(map.get("result").equals("2")){
            throw new RuntimeException(map.get("mes").toString());
        }
//        //同意日期不可新增数据
//        PmsSubsidyFund dto = pmsSubsidyFundMapper.getMesByDateAndDept(pmsSubsidyFund);
//        if(null != dto){
//            throw new RuntimeException("不可重复录入同一科室同一时间数据");
//        }
        //获取当前科室最大时间
        Date deptMesDate = pmsSubsidyFundMapper.getDeptMaxDate(pmsSubsidyFund.getDeptCode());
        if(null != deptMesDate){
            int result2 = pmsSubsidyFund.getFundTime().compareTo(deptMesDate);
            if (result2 < 0) {
                throw new RuntimeException("不可录入已存在数据之前时间的数据");
            }
        }
        //按照月份提交的数据不可新增
        List<PmsSubsidyFund>  pmsSubsidyFundList = pmsSubsidyFundMapper.getMesByDateANdState(pmsSubsidyFund);
        if(null != pmsSubsidyFundList && pmsSubsidyFundList.size() >0){
            throw new RuntimeException("已提交的科室月份不可新增");
        }
//        //当前科室的科基金余额
//        BigDecimal fundBalance = BigDecimal.ZERO;
//        if(!pmsSubsidyFund.getDeptCode().equals(null)){
//            PmsSubsidyFundBalance pmsSubsidyFundBalance = pmsSubsidyFundMapper.getDeptFundBalance( pmsSubsidyFund.getDeptCode());
//            if(null != pmsSubsidyFundBalance){
//                fundBalance = pmsSubsidyFundBalance.getFundBalance();
//            }
//
//        }
//        //判断添加的科基金金额是否大于当前余额
//        if( pmsSubsidyFund.getFundMoney().compareTo(BigDecimal.ZERO) <0){
//            int result = BigDecimal.ZERO.subtract(pmsSubsidyFund.getFundMoney()).compareTo(fundBalance);
//            if (result > 0) {
//                //添加的金额过大
//                throw new RuntimeException("当前科基金余额为"+fundBalance+",新增的科基金金额不满足！");
//            }
//        }
        pmsSubsidyFund.preInsert();
        pmsSubsidyFund.setFundState("1");
        return pmsSubsidyFundMapper.insertPmsSubsidyFund(pmsSubsidyFund);
    }

    /**
     * 修改科室效益调整
     * 
     * @param pmsSubsidyFund 科室效益调整
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePmsSubsidyFund(PmsSubsidyFund pmsSubsidyFund) {
        //查询科基金状态
        PmsSubsidyFund dot = selectPmsSubsidyFundById(pmsSubsidyFund.getId());
        if("2".equals(dot.getFundState())){
            throw new RuntimeException("已提交的数据不可修改！");
        }
        //判断月份
        Map map = judgeData(pmsSubsidyFund);
        if(map.get("result").equals("2")){
            throw new RuntimeException(map.get("mes").toString());
        }
        //获取当前科室最大时间
        Date deptMesDate = pmsSubsidyFundMapper.getDeptMaxDateNoId(pmsSubsidyFund.getDeptCode(),pmsSubsidyFund.getId());
        if(null != deptMesDate){
            int result2 = pmsSubsidyFund.getFundTime().compareTo(deptMesDate);
            if (result2 < 0) {
                throw new RuntimeException("不可录入已存在数据之前时间的数据");
            }
        }
//        //当前科室的科基金余额
//        BigDecimal fundBalance = BigDecimal.ZERO;
//        if(!pmsSubsidyFund.getDeptCode().equals(null)){
//            PmsSubsidyFundBalance pmsSubsidyFundBalance = pmsSubsidyFundMapper.getDeptFundBalance( pmsSubsidyFund.getDeptCode());
//            if(null != pmsSubsidyFundBalance){
//                fundBalance = pmsSubsidyFundBalance.getFundBalance();
//            }
//        }
//        //判断添加的科基金金额是否大于当前余额
//        if( pmsSubsidyFund.getFundMoney().compareTo(BigDecimal.ZERO) <0) {
//            int result = BigDecimal.ZERO.subtract(pmsSubsidyFund.getFundMoney()).compareTo(fundBalance);
//            if (result > 0) {
//                //添加的金额过大
//                throw new RuntimeException("当前科基金余额为" + fundBalance + ",修改的科基金金额不满足！");
//            }
//        }
        pmsSubsidyFund.preUpdate();
        return pmsSubsidyFundMapper.updatePmsSubsidyFund(pmsSubsidyFund);
    }

    /**
     * 批量删除科室效益调整
     * 
     * @param ids 需要删除的科室效益调整ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deletePmsSubsidyFund(Long[] ids) {
        //查询科基金状态
        List<PmsSubsidyFund> dotList = pmsSubsidyFundMapper.selectPmsSubsidyFundByIds(ids);
        List<PmsSubsidyFund> list = dotList.stream().filter(pmsSubsidyFund ->"2".equals(pmsSubsidyFund.getFundState()) )
                .collect(Collectors.toList());
        if(null != list && list.size() > 0){
            throw new RuntimeException("已提交的数据不可删除！");
        }
        Date deleteTime = DateUtils.getNowDate();
        String updateBy = "";
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (loginUser != null) {
            updateBy = loginUser.getUser().getUserId().toString();
        }
        return pmsSubsidyFundMapper.deletePmsSubsidyFund(ids,deleteTime,updateBy);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int submitBalance(PmsSubsidyFund pmsSubsidyFund) {
        if(null == pmsSubsidyFund.getSubmitDate()){
            pmsSubsidyFund.setFundTime(new Date());
        }else{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
            try {
                Date dateTime = dateFormat.parse(pmsSubsidyFund.getSubmitDate());
                pmsSubsidyFund.setFundTime(dateTime);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        pmsSubsidyFund.setFundState("1");
        List<PmsSubsidyFund> list =  pmsSubsidyFundMapper.selectPmsSubsidyFundList(pmsSubsidyFund);
        if(null != list && list.size() > 0){
            //根据时间科室修改科基金状态
            pmsSubsidyFundMapper.updateFundState(pmsSubsidyFund);
            //计算科室科基金余额
            BigDecimal fundBalance = pmsSubsidyFund.getFundBalance();
            //根据科室查询原来的科基金余额
            PmsSubsidyFundBalance pmsSubsidyFundBalance =  pmsSubsidyFundMapper.getDeptFundBalance( pmsSubsidyFund.getDeptCode());
            if(null == pmsSubsidyFundBalance){
                if( fundBalance.compareTo(BigDecimal.ZERO) <0) {
                    throw new RuntimeException("当前科室余额不足分配！");
                }
                //添加科基金余额数据
                pmsSubsidyFundBalance =  new PmsSubsidyFundBalance();
                pmsSubsidyFundBalance.setDeptName(list.get(0).getDeptName());
                pmsSubsidyFundBalance.setDeptCode(pmsSubsidyFund.getDeptCode());
                pmsSubsidyFundBalance.setFundBalance(fundBalance);
                pmsSubsidyFundMapper.insertFundBalance(pmsSubsidyFundBalance);
            }else{
                if( fundBalance.compareTo(BigDecimal.ZERO) <0) {
                    int result = BigDecimal.ZERO.subtract(fundBalance).compareTo(pmsSubsidyFundBalance.getFundBalance());
                    if (result > 0) {
                        throw new RuntimeException("当前科室余额不足分配！");
                    }
                }
                pmsSubsidyFundBalance.setFundBalance(fundBalance.add(pmsSubsidyFundBalance.getFundBalance()));
                pmsSubsidyFundMapper.updateFundBalance(pmsSubsidyFundBalance);
            }
            List<Long> idList = list.stream()
                    .map(PmsSubsidyFund::getId)
                    .collect(Collectors.toList());
            Long [] ids = idList.toArray(new Long[0]);
            pmsSubsidyFundMapper.updateFundStaterByIds(ids);
        }else{
            throw new RuntimeException("没有需要提交的数据！");
        }

        return 0;
    }

    //判断数据
    private Map judgeData(PmsSubsidyFund pmsSubsidyFund){
        Map map = new HashMap();
        Date date1 = pmsSubsidyFund.getFundTime();
        Date date2 = new Date();
        int result3 = date1.compareTo(date2);
        if (result3 > 0) {
            map.put("result","2");
            map.put("mes","不可录入当前时间之后的数据");
            return map;
        }
        //当前俩个月前时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        Date twoBef = calendar.getTime();
        int result = date1.compareTo(twoBef);
        if(result < 0){
            map.put("result","2");
            map.put("mes","不可录入俩个月前的数据");
            return map;
        }
        map.put("result","1");
        return map;
    }

}
