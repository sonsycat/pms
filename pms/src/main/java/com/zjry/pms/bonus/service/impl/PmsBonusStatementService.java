package com.zjry.pms.bonus.service.impl;

import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.pms.bonus.domain.PmsStatementAchievementType;
import com.zjry.pms.bonus.domain.PmsStatementEquivalent;
import com.zjry.pms.bonus.domain.PmsStatementEquivalentType;
import com.zjry.pms.bonus.domain.PmsStatementLabourType;
import com.zjry.pms.bonus.domain.equivalent.*;
import com.zjry.pms.bonus.mapper.PmsBonusStatementMapper;
import com.zjry.pms.bonus.service.IPmsBonusStatementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class PmsBonusStatementService implements IPmsBonusStatementService {
    @Autowired
    private PmsBonusStatementMapper pmsBonusStatementMapper;
    @Override
    public List<PmsStatementEquivalentType> selectEquivalentTypelist() {
        return pmsBonusStatementMapper.selectEquivalentTypelist();
    }

    @Override
    public List<PmsStatementAchievementType> selectAchievementTypeList() {
        return pmsBonusStatementMapper.selectAchievementTypeList();
    }

    @Override
    public List<PmsStatementLabourType> selectlabourTypelistList() {
        return pmsBonusStatementMapper.selectlabourTypelistList();
    }

    @Override
    public List<PmsStatementEquivalent> selectEquivalentList(PmsStatementEquivalent pmsStatementEquivalent) {
        if(null != pmsStatementEquivalent){
            if(null == pmsStatementEquivalent.getDateTime()){
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
                pmsStatementEquivalent.setDate(format1.format(new Date()));
            }else{
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
                pmsStatementEquivalent.setDate(format1.format(pmsStatementEquivalent.getDateTime()));
            }
        }else{
            return null;
        }
        return pmsBonusStatementMapper.selectEquivalentList(pmsStatementEquivalent);
    }

    @Override
    public <T> List<T> selectEquivalentListByThread(PmsStatementEquivalent pmsStatementEquivalent) {
        if(null != pmsStatementEquivalent){
            if(null == pmsStatementEquivalent.getDateTime()){
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
                pmsStatementEquivalent.setDate(format1.format(new Date()));
            }else{
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
                pmsStatementEquivalent.setDate(format1.format(pmsStatementEquivalent.getDateTime()));
            }
        }else{
            return null;
        }
        // 一个线程最大处理数据量
        int THREAD_COUNT_SIZE = 3000;
        //计算表总数
        Integer integer = pmsBonusStatementMapper.getEquivalentListSum(pmsStatementEquivalent);
        //记录开始时间
        long start = System.currentTimeMillis();
        //new个和表总数一样长的ArrayList
        List<T> threadList=new ArrayList<>(integer);
        // 线程数，以5000条数据为一个线程，总数据大小除以5000，再加1
        int round = integer / THREAD_COUNT_SIZE + 1;
        //new一个临时储存List的Map，以线程名为k，用做list排序
        Map<Integer,ArrayList> temporaryMap = new HashMap<>(round);
        // 程序计数器
        final CountDownLatch count = new CountDownLatch(round);
        // 创建线程
        ExecutorService executor = Executors.newFixedThreadPool(round);
        // 分配数据
        for (int i = 0; i < round; i++) {
            //该线程的查询开始值
            int startLen = i * THREAD_COUNT_SIZE;
            int k = i + 1;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    if("1".equals(pmsStatementEquivalent.getEquivalentTypeCode())){
                        ArrayList<PmsStatementEquivalentOne> dto = pmsBonusStatementMapper.subList(pmsStatementEquivalent,startLen);
                        temporaryMap.put(k,dto);
                    }else if("2".equals(pmsStatementEquivalent.getEquivalentTypeCode())){
                        ArrayList<PmsStatementEquivalentTwo> dto = pmsBonusStatementMapper.subListTwo(pmsStatementEquivalent,startLen);
                        temporaryMap.put(k,dto);
                    }else if("3".equals(pmsStatementEquivalent.getEquivalentTypeCode())){
                        ArrayList<PmsStatementEquivalentThree> dto = pmsBonusStatementMapper.subListThree(pmsStatementEquivalent,startLen);
                        temporaryMap.put(k,dto);
                    }else if("4".equals(pmsStatementEquivalent.getEquivalentTypeCode())){
                        ArrayList<PmsStatementEquivalentFour> dto = pmsBonusStatementMapper.subListFour(pmsStatementEquivalent,startLen);
                        temporaryMap.put(k,dto);
                    }else if("5".equals(pmsStatementEquivalent.getEquivalentTypeCode())){
                        ArrayList<PmsStatementEquivalentFive> dto = pmsBonusStatementMapper.subListFive(pmsStatementEquivalent,startLen);
                        temporaryMap.put(k,dto);
                    }else if("6".equals(pmsStatementEquivalent.getEquivalentTypeCode())){
                        ArrayList<PmsStatementEquivalentSix> dto = pmsBonusStatementMapper.subListSix(pmsStatementEquivalent,startLen);
                        temporaryMap.put(k,dto);
                    }else if("7".equals(pmsStatementEquivalent.getEquivalentTypeCode())){
                        ArrayList<PmsStatementEquivalentSeven> dto = pmsBonusStatementMapper.subListSeven(pmsStatementEquivalent,startLen);
                        temporaryMap.put(k,dto);
                    }
                    // 计数器 -1(唤醒阻塞线程)
                    count.countDown();
                }
            });
        }
        try {
            // 阻塞线程(主线程等待所有子线程 一起执行业务)
            count.await();
            //结束时间
            long end = System.currentTimeMillis();
            log.info("数据查询耗时:" + (end - start) + "ms");
            //通过循环遍历临时map，把map的值有序的放进List里
            temporaryMap.keySet().forEach(k->{
                threadList.addAll(temporaryMap.get(k));
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //清除临时map，释放内存
            temporaryMap.clear();
            // 终止线程池
            // 启动一次顺序关闭，执行以前提交的任务，但不接受新任务。若已经关闭，则调用没有其他作用。
            executor.shutdown();
        }
        //输出list的长度
        log.info("当量数据长度为："+threadList.size());
        return threadList;

    }
}
