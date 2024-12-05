package com.zjry.pms.update.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import com.zjry.pms.encode.service.IHrEncodeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.update.mapper.PmsUpdateDateMapper;
import com.zjry.pms.update.domain.PmsUpdateDate;
import com.zjry.pms.update.service.IPmsUpdateDateService;

/**
 * 更新日期Service业务层处理
 * 
 * @author maozt
 * @date 2022-07-06
 */
@Service
public class PmsUpdateDateServiceImpl extends ServiceImpl<PmsUpdateDateMapper, PmsUpdateDate> implements IPmsUpdateDateService {
    @Autowired
    private PmsUpdateDateMapper pmsUpdateDateMapper;
    @Autowired
    private IHrEncodeRuleService hrEncodeRuleService;

    /**
     * 查询更新日期
     * 
     * @param id 更新日期ID
     * @return 更新日期
     */
    @Override
    public PmsUpdateDate selectPmsUpdateDateById(Long id) {
        return pmsUpdateDateMapper.selectPmsUpdateDateById(id);
    }

    /**
     * 查询更新日期列表
     * 
     * @param pmsUpdateDate 更新日期
     * @return 更新日期
     */
    @Override
    public List<PmsUpdateDate> selectPmsUpdateDateList(PmsUpdateDate pmsUpdateDate) {
        return pmsUpdateDateMapper.selectPmsUpdateDateList(pmsUpdateDate);
    }

    /**
     * 新增更新日期
     * 
     * @param pmsUpdateDate 更新日期
     * @return 结果
     */
    @Override
    public int insertPmsUpdateDate(PmsUpdateDate pmsUpdateDate) throws Exception {
        if (pmsUpdateDate.getStartTime().getTime() > pmsUpdateDate.getEndTime().getTime()) {
            throw new Exception("开始时间不能大于结束时间");
        }
        PmsUpdateDate updateDate = new PmsUpdateDate();
        updateDate.setRevenueName(pmsUpdateDate.getRevenueName());
        updateDate.setTargetMonth(pmsUpdateDate.getTargetMonth());
        updateDate.setCompCode(pmsUpdateDate.getCompCode());
        if (pmsUpdateDateMapper.selectPmsUpdateDateList(updateDate).size() > 0 ) {
            throw new Exception("目标年月和调整表不能重复");
        }
    	pmsUpdateDate.preInsert();
        pmsUpdateDate.setRevenueCode(hrEncodeRuleService.generateEncode("pms_update_date", "REVENUE_CODE"));
        return pmsUpdateDateMapper.insertPmsUpdateDate(pmsUpdateDate);
    }

    /**
     * 修改更新日期
     * 
     * @param pmsUpdateDate 更新日期
     * @return 结果
     */
    @Override
    public int updatePmsUpdateDate(PmsUpdateDate pmsUpdateDate) {
    	pmsUpdateDate.preUpdate();
        return pmsUpdateDateMapper.updatePmsUpdateDate(pmsUpdateDate);
    }

    /**
     * 批量删除更新日期
     * 
     * @param ids 需要删除的更新日期ID
     * @return 结果
     */
    @Override
    public int deletePmsUpdateDateByIds(Long[] ids) {
        return pmsUpdateDateMapper.deletePmsUpdateDateByIds(ids);
    }

    /**
     * 删除更新日期信息
     * 
     * @param id 更新日期ID
     * @return 结果
     */
    @Override
    public int deletePmsUpdateDateById(Long id) {
        return pmsUpdateDateMapper.deletePmsUpdateDateById(id);
    }
}
