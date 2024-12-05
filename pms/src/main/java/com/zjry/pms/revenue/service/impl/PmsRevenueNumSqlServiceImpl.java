package com.zjry.pms.revenue.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.common.utils.DateUtils;
import com.zjry.pms.apportionment.domain.CostDatabase;
import com.zjry.pms.apportionment.mapper.CostDatabaseMapper;
import com.zjry.pms.apportionment.service.DBChangeService;
import com.zjry.pms.common.Constants;
import com.zjry.pms.revenue.domain.PmsRevenueNum;
import com.zjry.pms.revenue.mapper.PmsRevenueNumMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.revenue.mapper.PmsRevenueNumSqlMapper;
import com.zjry.pms.revenue.domain.PmsRevenueNumSql;
import com.zjry.pms.revenue.service.IPmsRevenueNumSqlService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 收入sqlService业务层处理
 * 
 * @author mk
 * @date 2022-06-23
 */
@Service
public class PmsRevenueNumSqlServiceImpl extends ServiceImpl<PmsRevenueNumSqlMapper, PmsRevenueNumSql> implements IPmsRevenueNumSqlService {
    @Resource
    private PmsRevenueNumSqlMapper pmsRevenueNumSqlMapper;
    @Resource
    private CostDatabaseMapper costDatabaseMapper;
    @Autowired
    private DBChangeService dbChangeService;
    @Resource
    private PmsRevenueNumMapper pmsRevenueNumMapper;

    /**
     * 查询收入sql
     * 
     * @param id 收入sqlID
     * @return 收入sql
     */
    @Override
    public PmsRevenueNumSql selectPmsRevenueNumSqlById(Long id) {
        return pmsRevenueNumSqlMapper.selectPmsRevenueNumSqlById(id);
    }

    /**
     * 查询收入sql列表
     * 
     * @param pmsRevenueNumSql 收入sql
     * @return 收入sql
     */
    @Override
    public List<PmsRevenueNumSql> selectPmsRevenueNumSqlList(PmsRevenueNumSql pmsRevenueNumSql) {
        return pmsRevenueNumSqlMapper.selectPmsRevenueNumSqlList(pmsRevenueNumSql);
    }

    /**
     * 新增收入sql
     * 
     * @param pmsRevenueNumSql 收入sql
     * @return 结果
     */
    @Override
    public int insertPmsRevenueNumSql(PmsRevenueNumSql pmsRevenueNumSql) {
    	pmsRevenueNumSql.preInsert();
        return pmsRevenueNumSqlMapper.insertPmsRevenueNumSql(pmsRevenueNumSql);
    }

    /**
     * 修改收入sql
     * 
     * @param pmsRevenueNumSql 收入sql
     * @return 结果
     */
    @Override
    public int updatePmsRevenueNumSql(PmsRevenueNumSql pmsRevenueNumSql) {
    	pmsRevenueNumSql.preUpdate();
        return pmsRevenueNumSqlMapper.updatePmsRevenueNumSql(pmsRevenueNumSql);
    }

    /**
     * 批量删除收入sql
     * 
     * @param ids 需要删除的收入sqlID
     * @return 结果
     */
    @Override
    public int deletePmsRevenueNumSqlByIds(Long[] ids) {
        return pmsRevenueNumSqlMapper.deletePmsRevenueNumSqlByIds(ids);
    }

    /**
     * 删除收入sql信息
     * 
     * @param id 收入sqlID
     * @return 结果
     */
    @Override
    public int deletePmsRevenueNumSqlById(Long id) {
        return pmsRevenueNumSqlMapper.deletePmsRevenueNumSqlById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String inertBySql(PmsRevenueNumSql pmsRevenueNumSql)throws Exception {
        PmsRevenueNum prn = new PmsRevenueNum();
        prn.setOccureT(pmsRevenueNumSql.getNowDate());
        prn.setCompCode(pmsRevenueNumSql.getCompCode());
        prn.setRevenueKind(Constants.CARD_STATE_ONE);
        List<PmsRevenueNum> list1 =  pmsRevenueNumMapper.selectPmsRevenueNumList(prn);
        if(list1.size()>0){
            return "该单位当月已提取过数据";
        }
        CostDatabase costDatabase = costDatabaseMapper.selectCostDatabaseByCode(pmsRevenueNumSql.getCoonectCode());
        List<PmsRevenueNum> list = dbChangeService.cPRNdb(costDatabase, pmsRevenueNumSql.getNumSql(),  pmsRevenueNumSql.getNowDate(), pmsRevenueNumSql.getCompCode());
        if(list!=null && list.size()>0){
                for(PmsRevenueNum  item : list){
                    item.preInsert();
                    item.setDelFlag(Constants.CARD_STATE_TWO);
                    item.setRevenueKind(Constants.CARD_STATE_ONE);
                    pmsRevenueNumMapper.insertPmsRevenueNum(item);
                }

            pmsRevenueNumMapper.updateByFlag();
            pmsRevenueNumSql.preInsert();
            pmsRevenueNumSql.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
            pmsRevenueNumSqlMapper.insertPmsRevenueNumSql(pmsRevenueNumSql);
        }else {
            return "提取数据为空";
        }
        return null;
    }

    @Override
    public void deleteByFlag() {
        pmsRevenueNumMapper.deleteByFlag();
    }
}
