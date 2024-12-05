package com.zjry.pms.average.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.common.utils.DateUtils;
import com.zjry.pms.average.domain.PmsAveragePerson;
import com.zjry.pms.average.domain.PmsAveragePersonDetailList;
import com.zjry.pms.average.mapper.PmsAveragePersonMapper;
import com.zjry.pms.common.utils.SpringContextHolder;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.average.mapper.PmsAveragePersonDetailMapper;
import com.zjry.pms.average.domain.PmsAveragePersonDetail;
import com.zjry.pms.average.service.IPmsAveragePersonDetailService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 平均奖科人数明细Service业务层处理
 * 
 * @author zwf
 * @date 2022-09-27
 */
@Service
public class PmsAveragePersonDetailServiceImpl extends ServiceImpl<PmsAveragePersonDetailMapper, PmsAveragePersonDetail> implements IPmsAveragePersonDetailService {
    @Resource
    private PmsAveragePersonDetailMapper pmsAveragePersonDetailMapper;
    @Resource
    private PmsAveragePersonMapper pmsAveragePersonMapper;

    private static Logger logger = LoggerFactory.getLogger(SpringContextHolder.class);

    /**
     * 查询平均奖科人数明细
     * 
     * @param id 平均奖科人数明细ID
     * @return 平均奖科人数明细
     */
    @Override
    public PmsAveragePersonDetail selectPmsAveragePersonDetailById(Long id) {
        return pmsAveragePersonDetailMapper.selectPmsAveragePersonDetailById(id);
    }

    /**
     * 查询平均奖科人数明细列表
     * 
     * @param pmsAveragePersonDetail 平均奖科人数明细
     * @return 平均奖科人数明细
     */
    @Override
    public List<PmsAveragePersonDetail> selectPmsAveragePersonDetailList(PmsAveragePersonDetail pmsAveragePersonDetail) {
        PmsAveragePerson pmsAveragePerson = pmsAveragePersonMapper.selectPmsAveragePersonById(pmsAveragePersonDetail.getAvgId());
        pmsAveragePersonDetail.setAvgCode(pmsAveragePerson.getCode());
        return pmsAveragePersonDetailMapper.selectPmsAveragePersonDetailList(pmsAveragePersonDetail);
    }

    /**
     * 新增平均奖科人数明细
     * 
     * @param pmsAveragePersonDetail 平均奖科人数明细
     * @return 结果
     */
    @Override
    public int insertPmsAveragePersonDetail(PmsAveragePersonDetail pmsAveragePersonDetail) {
    	pmsAveragePersonDetail.preInsert();
        return pmsAveragePersonDetailMapper.insertPmsAveragePersonDetail(pmsAveragePersonDetail);
    }

    /**
     * 修改平均奖科人数明细
     * 
     * @param pmsAveragePersonDetail 平均奖科人数明细
     * @return 结果
     */
    @Override
    public int updatePmsAveragePersonDetail(PmsAveragePersonDetail pmsAveragePersonDetail) {
    	pmsAveragePersonDetail.preUpdate();
        return pmsAveragePersonDetailMapper.updatePmsAveragePersonDetail(pmsAveragePersonDetail);
    }

    /**
     * 批量删除平均奖科人数明细
     * 
     * @param ids 需要删除的平均奖科人数明细ID
     * @return 结果
     */
    @Override
    public int deletePmsAveragePersonDetailByIds(Long[] ids) {
        return pmsAveragePersonDetailMapper.deletePmsAveragePersonDetailByIds(ids);
    }

    /**
     * 删除平均奖科人数明细信息
     * 
     * @param id 平均奖科人数明细ID
     * @return 结果
     */
    @Override
    public int deletePmsAveragePersonDetailById(Long id) {
        return pmsAveragePersonDetailMapper.deletePmsAveragePersonDetailById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int savePmsAveragePersonDetail(PmsAveragePersonDetailList data) {
        logger.info("List:"+data);
        List<PmsAveragePersonDetail> pmsAveragePersonDetailList = data.getData();
        // 根据id查询avgCode
        String code = pmsAveragePersonMapper.selectPmsAveragePersonById(data.getAvgId()).getCode();
        if (CollectionUtils.isNotEmpty(pmsAveragePersonDetailList)){
            pmsAveragePersonDetailList.forEach(sort -> {
                sort.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
                sort.setDeptCode(sort.getDepCode());
                sort.setDeptName(sort.getDepName());
                sort.setAvgCode(code);
                sort.preInsert();
            });
        }
        PmsAveragePersonDetail pmsAveragePersonDetail = new PmsAveragePersonDetail();
        pmsAveragePersonDetail.setAvgCode(code);
        // 删除旧数据
        pmsAveragePersonDetailMapper.deleteByAvgCode(pmsAveragePersonDetail);
        // 插入新数据
        if (CollectionUtils.isNotEmpty(pmsAveragePersonDetailList)){
            pmsAveragePersonDetailMapper.insertBatch(pmsAveragePersonDetailList);
        }
        return 1;
    }

    @Override
    public void copyData(PmsAveragePersonDetail pmsAveragePersonDetail) {
        //
        pmsAveragePersonDetailMapper.copyData(pmsAveragePersonDetail);
    }
}
