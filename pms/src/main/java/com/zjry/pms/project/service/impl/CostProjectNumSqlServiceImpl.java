package com.zjry.pms.project.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.pms.apport.domain.CostApport;
import com.zjry.pms.apport.service.ICostApportService;
import com.zjry.pms.apportionment.domain.CostDatabase;
import com.zjry.pms.apportionment.mapper.CostDatabaseMapper;
import com.zjry.pms.apportionment.service.DBChangeService;
import com.zjry.pms.project.domain.CostProject;
import com.zjry.pms.project.domain.CostProjectDept;
import com.zjry.pms.project.domain.CostProjectNum;
import com.zjry.pms.project.mapper.CostProjectNumMapper;
import com.zjry.pms.project.service.ICostProjectDeptService;
import com.zjry.pms.project.service.ICostProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.project.mapper.CostProjectNumSqlMapper;
import com.zjry.pms.project.domain.CostProjectNumSql;
import com.zjry.pms.project.service.ICostProjectNumSqlService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 成本sqlService业务层处理
 *
 * @author mk
 * @date 2022-05-18
 */
@Service
public class CostProjectNumSqlServiceImpl extends ServiceImpl<CostProjectNumSqlMapper, CostProjectNumSql> implements ICostProjectNumSqlService {
    @Resource
    private CostProjectNumSqlMapper costProjectNumSqlMapper;
    @Autowired
    private DBChangeService dbChangeService;
    @Autowired
    private ICostApportService costApportService;
    @Resource
    private ICostProjectService costProjectService;

    @Autowired
    private ICostProjectDeptService costProjectDeptService;
    @Resource
    private CostProjectNumMapper costProjectNumMapper;
    @Autowired
    private CostDatabaseMapper costDatabaseMapper;

    /**
     * 查询成本sql
     *
     * @param id 成本sqlID
     * @return 成本sql
     */
    @Override
    public CostProjectNumSql selectCostProjectNumSqlById(Long id) {
        return costProjectNumSqlMapper.selectCostProjectNumSqlById(id);
    }

    /**
     * 查询成本sql列表
     *
     * @param costProjectNumSql 成本sql
     * @return 成本sql
     */
    @Override
    public List<CostProjectNumSql> selectCostProjectNumSqlList(CostProjectNumSql costProjectNumSql) {
        return costProjectNumSqlMapper.selectCostProjectNumSqlList(costProjectNumSql);
    }

    /**
     * 新增成本sql
     *
     * @param costProjectNumSql 成本sql
     * @return 结果
     */
    @Override
    public int insertCostProjectNumSql(CostProjectNumSql costProjectNumSql) {
        costProjectNumSql.preInsert();
        return costProjectNumSqlMapper.insertCostProjectNumSql(costProjectNumSql);
    }

    /**
     * 修改成本sql
     *
     * @param costProjectNumSql 成本sql
     * @return 结果
     */
    @Override
    public int updateCostProjectNumSql(CostProjectNumSql costProjectNumSql) {
        costProjectNumSql.preUpdate();
        return costProjectNumSqlMapper.updateCostProjectNumSql(costProjectNumSql);
    }

    /**
     * 批量删除成本sql
     *
     * @param ids 需要删除的成本sqlID
     * @return 结果
     */
    @Override
    public int deleteCostProjectNumSqlByIds(Long[] ids) {
        return costProjectNumSqlMapper.deleteCostProjectNumSqlByIds(ids);
    }

    /**
     * 删除成本sql信息
     *
     * @param id 成本sqlID
     * @return 结果
     */
    @Override
    public int deleteCostProjectNumSqlById(Long id) {
        return costProjectNumSqlMapper.deleteCostProjectNumSqlById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String inertBySql(CostProjectNumSql costProjectNumSql) throws Exception {
        CostDatabase costDatabase = costDatabaseMapper.selectCostDatabaseByCode(costProjectNumSql.getCoonectCode());
        List<CostProjectNum> list = dbChangeService.cPNDb(costDatabase, costProjectNumSql.getNumSql(), costProjectNumSql.getNowDate(), costProjectNumSql.getCompCode());
        List<CostProject> lists = costProjectService.getDeptList(costProjectNumSql.getCompCode().toString());
        List<Long> depts = lists.stream().map(CostProject::getDeptCode).collect(Collectors.toList());

        for (CostProjectNum cpn : list) {
            if (!depts.contains(cpn.getDeptCode())) {
                return "科室是不是当前登录用户机构的科室;";
            }
            CostProjectDept cp = new CostProjectDept();
            cp.setProjectCode(cpn.getProjectCode());
            cp.setDeptCode(cpn.getDeptCode());
            List<CostProjectDept> listCPD = costProjectDeptService.selectCostProjectDeptList(cp);
            if (listCPD == null || listCPD.size() == 0) {
                return "成本项目和科室没有对应关系";
            }
            CostApport ca = new CostApport();
            ca.setNowDate(cpn.getNowDate());
            ca.setCompCode(costProjectNumSql.getCompCode());
            List<CostApport> listCA = costApportService.selectCostApportList(ca);
            if (listCA.size() > 0) {
                return "当前年月已经有分摊数据";

            }
        }
        for (CostProjectNum cpn : list) {
            cpn.preInsert();
            costProjectNumMapper.insertCostProjectNum(cpn);
        }
        CostProjectNumSql projectNumSql = new CostProjectNumSql();
        projectNumSql.setCompCode(costProjectNumSql.getCompCode());
        List<CostProjectNumSql> listc = costProjectNumSqlMapper.selectCostProjectNumSqlList(projectNumSql);
        if(listc!=null && listc.size()>0){
            CostProjectNumSql  cpNSql =  listc.get(0);
            cpNSql.setNumSql(costProjectNumSql.getNumSql());
            cpNSql.setSqlType(costProjectNumSql.getSqlType());
            cpNSql.preUpdate();
            costProjectNumSqlMapper.updateCostProjectNumSql(cpNSql);
        }else{
            costProjectNumSql.preInsert();
            costProjectNumSqlMapper.insertCostProjectNumSql(costProjectNumSql);
        }
        return null;
    }
}
