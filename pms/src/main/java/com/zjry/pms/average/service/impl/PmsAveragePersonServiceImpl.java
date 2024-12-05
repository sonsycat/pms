package com.zjry.pms.average.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import com.zjry.pms.average.domain.PmsAveragePersonDetail;
import com.zjry.pms.average.mapper.PmsAveragePersonDetailMapper;
import com.zjry.pms.average.service.IPmsAveragePersonDetailService;
import com.zjry.pms.common.service.impl.SysFlowcodeServiceImpl;
import com.zjry.pms.common.utils.SpringContextHolder;
import com.zjry.pms.encode.service.IHrEncodeRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.average.mapper.PmsAveragePersonMapper;
import com.zjry.pms.average.domain.PmsAveragePerson;
import com.zjry.pms.average.service.IPmsAveragePersonService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 平均奖科人数Service业务层处理
 * 
 * @author zwf
 * @date 2022-09-27
 */
@Service
public class PmsAveragePersonServiceImpl extends ServiceImpl<PmsAveragePersonMapper, PmsAveragePerson> implements IPmsAveragePersonService {
    @Resource
    private PmsAveragePersonMapper pmsAveragePersonMapper;
    @Autowired
    private IHrEncodeRuleService hrEncodeRuleService;
    @Autowired
    private IPmsAveragePersonDetailService iPmsAveragePersonDetailService;
    @Resource
    private PmsAveragePersonDetailMapper pmsAveragePersonDetailMapper;

    private static Logger logger = LoggerFactory.getLogger(SpringContextHolder.class);

    /**
     * 查询平均奖科人数
     * 
     * @param id 平均奖科人数ID
     * @return 平均奖科人数
     */
    @Override
    public PmsAveragePerson selectPmsAveragePersonById(Long id) {
        return pmsAveragePersonMapper.selectPmsAveragePersonById(id);
    }

    /**
     * 查询平均奖科人数列表
     * 
     * @param pmsAveragePerson 平均奖科人数
     * @return 平均奖科人数
     */
    @Override
    public List<PmsAveragePerson> selectPmsAveragePersonList(PmsAveragePerson pmsAveragePerson) {
        return pmsAveragePersonMapper.selectPmsAveragePersonList(pmsAveragePerson);
    }

    /**
     * 新增平均奖科人数
     * 
     * @param pmsAveragePerson 平均奖科人数
     * @return 结果
     */
    @Override
    public int insertPmsAveragePerson(PmsAveragePerson pmsAveragePerson) {
    	pmsAveragePerson.preInsert();
        // 设置平均奖科人数编码
        pmsAveragePerson.setCode(hrEncodeRuleService.generateEncode("pms_average_person","CODE"));
        return pmsAveragePersonMapper.insertPmsAveragePerson(pmsAveragePerson);
    }

    /**
     * 修改平均奖科人数
     * 
     * @param pmsAveragePerson 平均奖科人数
     * @return 结果
     */
    @Override
    public int updatePmsAveragePerson(PmsAveragePerson pmsAveragePerson) {
    	pmsAveragePerson.preUpdate();
        return pmsAveragePersonMapper.updatePmsAveragePerson(pmsAveragePerson);
    }

    /**
     * 批量删除平均奖科人数
     * 
     * @param ids 需要删除的平均奖科人数ID
     * @return 结果
     */
    @Override
    public int deletePmsAveragePersonByIds(Long[] ids) {
        // 删除明细表数据
        PmsAveragePersonDetail pmsAveragePersonDetail = new PmsAveragePersonDetail();
        for (Long id : ids) {
            pmsAveragePersonDetail.setAvgCode(pmsAveragePersonMapper.selectPmsAveragePersonById(id).getCode());
            pmsAveragePersonDetailMapper.deleteByAvgCode(pmsAveragePersonDetail);
        }
        // 删除主表数据
        return pmsAveragePersonMapper.deletePmsAveragePersonByIds(ids);
    }

    /**
     * 删除平均奖科人数信息
     * 
     * @param id 平均奖科人数ID
     * @return 结果
     */
    @Override
    public int deletePmsAveragePersonById(Long id) {
        // 删除明细表数据
        PmsAveragePersonDetail pmsAveragePersonDetail = new PmsAveragePersonDetail();
        pmsAveragePersonDetail.setAvgCode(pmsAveragePersonMapper.selectPmsAveragePersonById(id).getCode());
        pmsAveragePersonDetailMapper.deleteByAvgCode(pmsAveragePersonDetail);
        // 删除主表数据
        return pmsAveragePersonMapper.deletePmsAveragePersonById(id);
    }

    /**
     * 查询平均奖科人数最新日期
     * @return 结果
     */
    @Override
    public PmsAveragePerson selectAveragePersonNewDate() {
        PmsAveragePerson pmsAveragePerson = pmsAveragePersonMapper.selectAveragePersonNewDate();
        // 没有数据则使用当前月份
        if (pmsAveragePerson == null){
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            PmsAveragePerson person = new PmsAveragePerson();
            person.setSetDate(date);
            return person;
        }
        // 最新日期的已有数据加一个月
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(pmsAveragePerson.getSetDate());
        calendar.add(Calendar.MONTH, 1);
        Date date = calendar.getTime();
        pmsAveragePerson.setSetDate(date);
        return pmsAveragePerson;
    }

    @Override
    public List<PmsAveragePerson> selectPmsAveragePersonByDate(PmsAveragePerson pmsAveragePerson) {
        return pmsAveragePersonMapper.selectPmsAveragePersonByDate(pmsAveragePerson);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void copyData(PmsAveragePerson pmsAveragePerson) {
        // 设置编码
        pmsAveragePerson.setCode(hrEncodeRuleService.generateEncode("pms_average_person","CODE"));
        pmsAveragePerson.preInsert();
        pmsAveragePerson.setSetTime(DateUtils.getNowDate());
        pmsAveragePersonMapper.copyData(pmsAveragePerson);
        // 复制子表数据
        PmsAveragePersonDetail pmsAveragePersonDetail = new PmsAveragePersonDetail();
        pmsAveragePersonDetail.setAvgCode(pmsAveragePerson.getCode());
        pmsAveragePersonDetail.preInsert();
        // 通过年月查询主表编码 给复制编码赋值
        pmsAveragePersonDetail.setCopyCode(pmsAveragePersonMapper.selectPmsAveragePersonByDate(pmsAveragePerson).get(0).getCode());
        iPmsAveragePersonDetailService.copyData(pmsAveragePersonDetail);
    }
}
