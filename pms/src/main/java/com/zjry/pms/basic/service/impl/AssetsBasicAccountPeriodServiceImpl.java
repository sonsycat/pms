package com.zjry.pms.basic.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.basic.mapper.AssetsBasicAccountPeriodMapper;
import com.zjry.pms.basic.domain.AssetsBasicAccountPeriod;
import com.zjry.pms.basic.service.IAssetsBasicAccountPeriodService;

/**
 * 年度帐期间管理Service业务层处理
 * 
 * @author maozt
 * @date 2021-08-06
 */
@Service
public class AssetsBasicAccountPeriodServiceImpl extends ServiceImpl<AssetsBasicAccountPeriodMapper, AssetsBasicAccountPeriod> implements IAssetsBasicAccountPeriodService {
    @Autowired
    private AssetsBasicAccountPeriodMapper assetsBasicAccountPeriodMapper;

    /**
     * 查询年度帐期间管理
     * 
     * @param id 年度帐期间管理ID
     * @return 年度帐期间管理
     */
    @Override
    public AssetsBasicAccountPeriod selectAssetsBasicAccountPeriodById(Long id) {
        return assetsBasicAccountPeriodMapper.selectAssetsBasicAccountPeriodById(id);
    }

    /**
     * 查询年度帐期间管理列表
     * 
     * @param assetsBasicAccountPeriod 年度帐期间管理
     * @return 年度帐期间管理
     */
    @Override
    public List<AssetsBasicAccountPeriod> selectAssetsBasicAccountPeriodList(AssetsBasicAccountPeriod assetsBasicAccountPeriod) {
        return assetsBasicAccountPeriodMapper.selectAssetsBasicAccountPeriodList(assetsBasicAccountPeriod);
    }

    /**
     * 新增年度帐期间管理
     * 
     * @param assetsBasicAccountPeriod 年度帐期间管理
     * @return 结果
     */
    @Override
    public int insertAssetsBasicAccountPeriod(AssetsBasicAccountPeriod assetsBasicAccountPeriod) {
        assetsBasicAccountPeriod.preInsert();
        return assetsBasicAccountPeriodMapper.insertAssetsBasicAccountPeriod(assetsBasicAccountPeriod);
    }

    /**
     * 修改年度帐期间管理
     * 
     * @param assetsBasicAccountPeriod 年度帐期间管理
     * @return 结果
     */
    @Override
    public int updateAssetsBasicAccountPeriod(AssetsBasicAccountPeriod assetsBasicAccountPeriod) {
        assetsBasicAccountPeriod.setUpdateTime(DateUtils.getNowDate());
        return assetsBasicAccountPeriodMapper.updateAssetsBasicAccountPeriod(assetsBasicAccountPeriod);
    }

    /**
     * 批量删除年度帐期间管理
     * 
     * @param ids 需要删除的年度帐期间管理ID
     * @return 结果
     */
    @Override
    public int deleteAssetsBasicAccountPeriodByIds(Long[] ids) {
        return assetsBasicAccountPeriodMapper.deleteAssetsBasicAccountPeriodByIds(ids);
    }

    /**
     * 根据名称年度批量删除年度帐期间管理
     *
     * @param assetsBasicAccountPeriod 需要删除的年度帐期间管理ID
     * @return 结果
     */
    @Override
    public int deleteAssetsBasicAccountPeriodByNameYear(AssetsBasicAccountPeriod assetsBasicAccountPeriod) {
        return assetsBasicAccountPeriodMapper.deleteAssetsBasicAccountPeriodByNameYear(assetsBasicAccountPeriod);
    }

    /**
     * 删除年度帐期间管理信息
     * 
     * @param id 年度帐期间管理ID
     * @return 结果
     */
    @Override
    public int deleteAssetsBasicAccountPeriodById(Long id) {
        return assetsBasicAccountPeriodMapper.deleteAssetsBasicAccountPeriodById(id);
    }

    /**
     * 自然月新增年度帐期间管理
     *
     * @param assetsBasicAccountPeriod 年度帐期间管理
     * @return 结果
     */
    @Override
    public int insertAssetsBasicAccountPeriodNum(AssetsBasicAccountPeriod assetsBasicAccountPeriod) throws ParseException {

        assetsBasicAccountPeriod.preInsert();
        String year = assetsBasicAccountPeriod.getAccountYear();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        //指定年的1月1号
        Date date = formatter.parse(year);
        Date startDate = null;
        Date endDate = null;
        //条数
        int num = 0;
        for (int i = 1 ; i <= 12 ; i++) {
            startDate = date;
            assetsBasicAccountPeriod.setStartDate(startDate);
            date = com.zjry.pms.common.utils.DateUtils.getLastDayOfMonth(date);
            endDate = date;
            assetsBasicAccountPeriod.setEndDate(endDate);
            date = com.zjry.pms.common.utils.DateUtils.getSpecifiedDayAfter(date);
            assetsBasicAccountPeriod.setPeriodNum(String.valueOf(i));
            if (String.valueOf(i).equals(assetsBasicAccountPeriod.getNowPeriod())) {
                assetsBasicAccountPeriod.setIsPeriod("0");
            } else {
                assetsBasicAccountPeriod.setIsPeriod("1");
            }
            if (i<10) {
                assetsBasicAccountPeriod.setPeriodName(year+"-0"+i);
            } else {
                assetsBasicAccountPeriod.setPeriodName(year+"-"+i);
            }
            num += assetsBasicAccountPeriodMapper.insertAssetsBasicAccountPeriod(assetsBasicAccountPeriod);
        }

        return num;
    }

    /**
     * 自然月新增年度帐期间管理
     *
     * @param assetsBasicAccountPeriod 年度帐期间管理
     * @return 结果
     */
    @Override
    public int insertAssetsBasicAccountPeriodNumFeel(AssetsBasicAccountPeriod assetsBasicAccountPeriod) throws ParseException {

        assetsBasicAccountPeriod.preInsert();
        //条数
        int num = 0;
        String dayLastString = assetsBasicAccountPeriod.getPeriodNum();
        int dayNext = Integer.parseInt(assetsBasicAccountPeriod.getPeriodNum()) + 1;
        String dayNextString = null;
        if (dayNext < 10) {
            dayNextString = "0" + dayNext;
        } else {
            dayNextString = String.valueOf(dayNext);
        }
        String year = assetsBasicAccountPeriod.getAccountYear();
        String yearLast = String.valueOf(Integer.parseInt(year)-1);
        String month = "12";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = formatter.parse(yearLast+"-"+month+"-"+dayNextString);
        month = "01";
        Date endDate = formatter.parse(year+"-"+month+"-"+dayLastString);
        assetsBasicAccountPeriod.setStartDate(startDate);
        assetsBasicAccountPeriod.setEndDate(endDate);
        assetsBasicAccountPeriod.setPeriodNum(String.valueOf(1));
        if (String.valueOf(1).equals(assetsBasicAccountPeriod.getNowPeriod())) {
            assetsBasicAccountPeriod.setIsPeriod("0");
        } else {
            assetsBasicAccountPeriod.setIsPeriod("1");
        }
        assetsBasicAccountPeriod.setPeriodName(year+"-0"+1);

        num += assetsBasicAccountPeriodMapper.insertAssetsBasicAccountPeriod(assetsBasicAccountPeriod);

        for (int i = 2 ; i <= 12 ; i++) {
            month = i - 1 < 10 ? "0"+(i-1) : String.valueOf(i-1);
            startDate = formatter.parse(year+"-"+month+"-"+dayNextString);
            assetsBasicAccountPeriod.setStartDate(startDate);
            month = i < 10 ? "0"+i :String.valueOf(i);
            endDate = formatter.parse(year+"-"+month+"-"+dayLastString);
            assetsBasicAccountPeriod.setEndDate(endDate);
            assetsBasicAccountPeriod.setPeriodNum(String.valueOf(i));
            if (String.valueOf(i).equals(assetsBasicAccountPeriod.getNowPeriod())) {
                assetsBasicAccountPeriod.setIsPeriod("0");
            } else {
                assetsBasicAccountPeriod.setIsPeriod("1");
            }
            if (i<10) {
                assetsBasicAccountPeriod.setPeriodName(year+"-0"+i);
            } else {
                assetsBasicAccountPeriod.setPeriodName(year+"-"+i);
            }
            num += assetsBasicAccountPeriodMapper.insertAssetsBasicAccountPeriod(assetsBasicAccountPeriod);
        }

        return num;
    }
}
