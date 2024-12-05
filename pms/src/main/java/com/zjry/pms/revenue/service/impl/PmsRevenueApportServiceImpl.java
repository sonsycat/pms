package com.zjry.pms.revenue.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import com.zjry.common.utils.StringUtils;
import com.zjry.pms.common.Constants;
import com.zjry.pms.revenue.domain.*;
import com.zjry.pms.revenue.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import com.zjry.pms.revenue.service.IPmsRevenueApportService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 收入生成Service业务层处理
 * 
 * @author mk
 * @date 2022-06-28
 */
@Service
public class PmsRevenueApportServiceImpl extends ServiceImpl<PmsRevenueApportMapper, PmsRevenueApport> implements IPmsRevenueApportService {
    @Resource
    private PmsRevenueApportMapper pmsRevenueApportMapper;
    @Resource
    private PmsRevenueSpecialMapper pmsRevenueSpecialMapper;
    @Resource
    private PmsRevenueSpecialDeptMapper pmsRevenueSpecialDeptMapper;
    @Resource
    private PmsRevenueMapper pmsRevenueMapper;
    @Resource
    private PmsRevenueDeptMapper pmsRevenueDeptMapper;
    @Resource
    private PmsRevenueNumMapper pmsRevenueNumMapper;


    /**
     * 查询收入生成
     * 
     * @param id 收入生成ID
     * @return 收入生成
     */
    @Override
    public PmsRevenueApport selectPmsRevenueApportById(Long id) {
        return pmsRevenueApportMapper.selectPmsRevenueApportById(id);
    }

    /**
     * 查询收入生成列表
     * 
     * @param pmsRevenueApport 收入生成
     * @return 收入生成
     */
    @Override
    public List<PmsRevenueApport> selectPmsRevenueApportList(PmsRevenueApport pmsRevenueApport) {
        return pmsRevenueApportMapper.selectPmsRevenueApportList(pmsRevenueApport);
    }

    /**
     * 新增收入生成
     * 
     * @param pmsRevenueApport 收入生成
     * @return 结果
     */
    @Override
    public int insertPmsRevenueApport(PmsRevenueApport pmsRevenueApport) {
    	pmsRevenueApport.preInsert();
        return pmsRevenueApportMapper.insertPmsRevenueApport(pmsRevenueApport);
    }

    /**
     * 修改收入生成
     * 
     * @param pmsRevenueApport 收入生成
     * @return 结果
     */
    @Override
    public int updatePmsRevenueApport(PmsRevenueApport pmsRevenueApport) {
    	pmsRevenueApport.preUpdate();
        return pmsRevenueApportMapper.updatePmsRevenueApport(pmsRevenueApport);
    }

    /**
     * 批量删除收入生成
     * 
     * @param ids 需要删除的收入生成ID
     * @return 结果
     */
    @Override
    public int deletePmsRevenueApportByIds(Long[] ids) {
        return pmsRevenueApportMapper.deletePmsRevenueApportByIds(ids);
    }

    /**
     * 删除收入生成信息
     * 
     * @param id 收入生成ID
     * @return 结果
     */
    @Override
    public int deletePmsRevenueApportById(Long id) {
        return pmsRevenueApportMapper.deletePmsRevenueApportById(id);
    }
    //门诊正常收入分解
    //合并方法 ,传入状态 deptType(1.门诊 2.住院) 收入类型 srlx (正常,附加)
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void generate1(List<PmsRevenueNum> list,String deptType,String srlx)throws Exception {
        //第一次循环, 循环 项目 执行科室和开单科室都有的 特殊项目
        List<PmsRevenueNum> listRemove = new ArrayList<>();
        for (PmsRevenueNum item : list){
            PmsRevenueSpecial prs = new PmsRevenueSpecial();
            prs.setCompCode(item.getCompCode());
            prs.setBillDept(item.getBillDept());
            prs.setExecDept(item.getExecDept());
            prs.setRevenueCode(item.getRevenueCode());
            List<PmsRevenueSpecial> list1 = pmsRevenueSpecialMapper.selectPmsRevenueSpecialList(prs);
            if(list1.size()>0){
                calculationSpecial (list1,item,deptType,srlx);
//                list.remove(item);
                listRemove.add(item);
            }
        }
        list.removeAll(listRemove);
        //第二次循环,循环 项目 开单科室都有的 特殊项目
        for (PmsRevenueNum item : list){
            PmsRevenueSpecial prs = new PmsRevenueSpecial();
            prs.setCompCode(item.getCompCode());
            prs.setBillDept(item.getBillDept());
            prs.setExecDeptIsNull(Constants.CARD_STATE_ONE);
            prs.setRevenueCode(item.getRevenueCode());
            List<PmsRevenueSpecial> list2 = pmsRevenueSpecialMapper.selectPmsRevenueSpecialList(prs);
            if(list2.size()>0){
                calculationSpecial (list2,item,deptType,srlx);
                //list.remove(item);
                listRemove.add(item);
            }
        }
        list.removeAll(listRemove);
        //第三次循环, 循环 项目 执行科室都有的 特殊项目
        for (PmsRevenueNum item : list){
            PmsRevenueSpecial prs = new PmsRevenueSpecial();
            prs.setCompCode(item.getCompCode());
            prs.setExecDept(item.getBillDept());
            prs.setBillDeptIsNull(Constants.CARD_STATE_ONE);
            prs.setRevenueCode(item.getRevenueCode());
            List<PmsRevenueSpecial> list3 = pmsRevenueSpecialMapper.selectPmsRevenueSpecialList(prs);
            if(list3.size()>0){
                calculationSpecial (list3,item,deptType,srlx);
                //list.remove(item);
                listRemove.add(item);
            }
        }
        list.removeAll(listRemove);
        //第四次循环,循环普通的项目
        for (PmsRevenueNum item : list){
            PmsRevenue prs = new PmsRevenue();
            prs.setCompCode(item.getCompCode());
            prs.setRevenueCode(item.getRevenueCode());
            //参与核算的项目
            prs.setAccountType(Constants.CARD_STATE_ONE);
            List<PmsRevenue> list4 = pmsRevenueMapper.selectPmsRevenueList(prs);
            if(list4.size()>0){
                calculationNoSpecial (list4,item,deptType,srlx);
            }
        }
    }

    @Override
    public void deleteByState() {
        pmsRevenueApportMapper.deleteByState();
    }

    @Override
    public void updateByState() {
        pmsRevenueApportMapper.updateByState();
    }

    @Override
    public List<PmsApportInfo> beforeBreak(PmsRevenueApport pmsRevenueApport) {
        List<PmsApportInfo> pais = new ArrayList<>();
        //门诊正常
        String v1 =  getBeforeValue(pmsRevenueApport,Constants.CARD_STATE_ONE,Constants.CARD_STATE_ONE);
        BigDecimal mzzc;
        if(v1!=null){
            mzzc = new BigDecimal(v1).setScale(2, BigDecimal.ROUND_HALF_UP);
       }else{
            mzzc = new BigDecimal(0);
        }
        PmsApportInfo p1 = new PmsApportInfo();
        p1.setName("门诊收入");
        p1.setValue(mzzc);
        pais.add(p1);
        //门诊附加
        String v2 =  getBeforeValue(pmsRevenueApport,Constants.CARD_STATE_TWO,Constants.CARD_STATE_ONE);
        BigDecimal mzfj;
        if(v2!=null){
            mzfj = new BigDecimal(v2).setScale(2, BigDecimal.ROUND_HALF_UP);
        }else{
            mzfj = new BigDecimal(0);
        }
        PmsApportInfo p2 = new PmsApportInfo();
        p2.setName("门诊附加收入");
        p2.setValue(mzfj);
        pais.add(p2);
        //住院正常
        String v3 =  getBeforeValue(pmsRevenueApport,Constants.CARD_STATE_ONE,Constants.CARD_STATE_TWO);
        BigDecimal zyzc;
        if(v3!=null){
            zyzc = new BigDecimal(v3).setScale(2, BigDecimal.ROUND_HALF_UP);
        }else{
            zyzc = new BigDecimal(0);
        }
        PmsApportInfo p3 = new PmsApportInfo();
        p3.setName("住院收入");
        p3.setValue(zyzc);
        pais.add(p3);
        //住院附加
        String v4 =  getBeforeValue(pmsRevenueApport,Constants.CARD_STATE_TWO,Constants.CARD_STATE_TWO);
        BigDecimal zyfj;
        if(v4!=null){
            zyfj = new BigDecimal(v4).setScale(2, BigDecimal.ROUND_HALF_UP);
        }else{
            zyfj = new BigDecimal(0);
        }
        PmsApportInfo p4 = new PmsApportInfo();
        p4.setName("住院附加收入");
        p4.setValue(zyfj);
        pais.add(p4);

        PmsApportInfo p5 = new PmsApportInfo();
        p5.setName("合计");
        BigDecimal hj = mzzc.add(mzfj).add(zyzc).add(zyfj);
        p5.setValue(hj);
        pais.add(p5);
        return pais;
    }

    @Override
    public List<PmsApportInfo> afterBreak(PmsRevenueApport pmsRevenueApport) {
        List<PmsApportInfo> pais = new ArrayList<>();
        //门诊正常
        String v1 =  getAfterValue(pmsRevenueApport,Constants.CARD_STATE_ONE,Constants.CARD_STATE_ONE);
        BigDecimal mzzc;
        if(v1!=null){
            mzzc = new BigDecimal(v1).setScale(2, BigDecimal.ROUND_HALF_UP);
        }else{
            mzzc = new BigDecimal(0);
        }
        PmsApportInfo p1 = new PmsApportInfo();
        p1.setName("门诊收入");
        p1.setValue(mzzc);
        pais.add(p1);
        //门诊附加
        String v2 =  getAfterValue(pmsRevenueApport,Constants.CARD_STATE_TWO,Constants.CARD_STATE_ONE);
        BigDecimal mzfj;
        if(v2!=null){
            mzfj = new BigDecimal(v2).setScale(2, BigDecimal.ROUND_HALF_UP);
        }else{
            mzfj = new BigDecimal(0);
        }
        PmsApportInfo p2 = new PmsApportInfo();
        p2.setName("门诊附加收入");
        p2.setValue(mzfj);
        pais.add(p2);
        //住院正常
        String v3 =  getAfterValue(pmsRevenueApport,Constants.CARD_STATE_ONE,Constants.CARD_STATE_TWO);
        BigDecimal zyzc;
        if(v3!=null){
            zyzc = new BigDecimal(v3).setScale(2, BigDecimal.ROUND_HALF_UP);
        }else{
            zyzc = new BigDecimal(0);
        }
        PmsApportInfo p3 = new PmsApportInfo();
        p3.setName("住院收入");
        p3.setValue(zyzc);
        pais.add(p3);
        //住院附加
        String v4 =  getAfterValue(pmsRevenueApport,Constants.CARD_STATE_TWO,Constants.CARD_STATE_TWO);
        BigDecimal zyfj;
        if(v4!=null){
            zyfj = new BigDecimal(v4).setScale(2, BigDecimal.ROUND_HALF_UP);
        }else{
            zyfj = new BigDecimal(0);
        }
        PmsApportInfo p4 = new PmsApportInfo();
        p4.setName("住院附加收入");
        p4.setValue(zyfj);
        pais.add(p4);

        PmsApportInfo p5 = new PmsApportInfo();
        p5.setName("合计");
        BigDecimal hj = mzzc.add(mzfj).add(zyzc).add(zyfj);
        p5.setValue(hj);
        pais.add(p5);
        return pais;
    }

    public String getAfterValue (PmsRevenueApport pmsRevenueApport,String srlx ,String mzlx){
        pmsRevenueApport.setRevenueKind(srlx);
        pmsRevenueApport.setRevenueType(mzlx);
        return pmsRevenueApportMapper.getAfterValue(pmsRevenueApport);
    }

    public String getBeforeValue (PmsRevenueApport pmsRevenueApport,String srlx ,String mzlx){
        PmsRevenueNum prn = new PmsRevenueNum();
        prn.setOccureTime(pmsRevenueApport.getOccureTime());
        prn.setCompCode(pmsRevenueApport.getCompCode());
        prn.setRevenueKind(srlx);
        prn.setRevenueType(mzlx);
        if(StringUtils.isNotEmpty(pmsRevenueApport.getSettleType())){
            prn.setSettleMark(pmsRevenueApport.getSettleType());
        }
        return pmsRevenueNumMapper.beforeBreak(prn);
    }

    private void calculationNoSpecial(List<PmsRevenue> list4, PmsRevenueNum item, String deptType, String srlx) {
        PmsRevenue prsl = list4.get(0);
        //合作医疗
        BigDecimal hzyl  = item.getActualNum().multiply(new BigDecimal(prsl.getCoopMed())).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
        //固定折算
        BigDecimal gdzs  = item.getActualNum().multiply(new BigDecimal(prsl.getFixConv())).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
        //剩余金额
        BigDecimal syje  = item.getActualNum().subtract(hzyl).subtract(gdzs).setScale(2, BigDecimal.ROUND_HALF_UP);
        //查询第三方科室
        PmsRevenueDept prsd = new PmsRevenueDept();
        prsd.setRevenueCode(prsl.getRevenueCode());
        prsd.setCompCode(item.getCompCode());
        prsd.setDeptType(deptType);
        List<PmsRevenueDept> lstorsd = pmsRevenueDeptMapper.selectPmsRevenueDeptList(prsd);
        for(PmsRevenueDept sfks : lstorsd){
            BigDecimal sfje = syje.multiply(new BigDecimal(sfks.getRatio())).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
            saveSpecialApp (item, prsl.getRevenueCode(),sfje , sfks.getDeptCode(),  deptType , srlx);
            syje = syje.subtract(sfje).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        if(syje.compareTo(new BigDecimal(0))!=0) {
            //门诊
            if (deptType.equals(Constants.CARD_STATE_ONE)) {
                //门诊开单
                BigDecimal mzkd = syje.multiply(new BigDecimal(prsl.getOutpBill())).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                saveSpecialApp(item, prsl.getRevenueCode(), mzkd, item.getBillDept(), deptType, srlx);
                //门诊执行
                BigDecimal mzzx = syje.multiply(new BigDecimal(prsl.getOutpExec())).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                saveSpecialApp(item, prsl.getRevenueCode(), mzzx, item.getExecDept(), deptType, srlx);
                //门诊护理
                BigDecimal mzhl = syje.multiply(new BigDecimal(prsl.getOutpCare())).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                saveSpecialApp(item, prsl.getRevenueCode(), mzhl, item.getCareDept(), deptType, srlx);
            } else {
                //住院开单
                BigDecimal zykd = syje.multiply(new BigDecimal(prsl.getInpBill())).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                saveSpecialApp(item, prsl.getRevenueCode(), zykd, item.getBillDept(), deptType, srlx);
                //住院执行
                BigDecimal zyzx = syje.multiply(new BigDecimal(prsl.getInpExec())).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                saveSpecialApp(item, prsl.getRevenueCode(), zyzx, item.getBillDept(), deptType, srlx);
                //住院护理
                BigDecimal zyhl = syje.multiply(new BigDecimal(prsl.getInpCare())).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                saveSpecialApp(item, prsl.getRevenueCode(), zyhl, item.getBillDept(), deptType, srlx);

            }
        }
    }

    public void calculationSpecial ( List<PmsRevenueSpecial> list1, PmsRevenueNum item,String deptType,String srlx){
        PmsRevenueSpecial prsl = list1.get(0);
        //合作医疗
        BigDecimal hzyl  = item.getActualNum().multiply(new BigDecimal(prsl.getCoopMed())).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
        //固定折算
        BigDecimal gdzs  = item.getActualNum().multiply(new BigDecimal(prsl.getFixConv())).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
        //剩余金额
        BigDecimal syje  = item.getActualNum().subtract(hzyl).subtract(gdzs).setScale(2, BigDecimal.ROUND_HALF_UP);
        //查询第三方科室
        PmsRevenueSpecialDept prsd = new PmsRevenueSpecialDept();
        prsd.setSpecialCode(prsl.getSpecialCode());
        prsd.setIsProject(Constants.CARD_STATE_ONE);
        prsd.setClinicType(deptType);
//        List<PmsRevenueSpecialDept> lstorsd = pmsRevenueSpecialDeptMapper.getDeptProject(prsd);
        List<PmsRevenueSpecialDept> lstorsd = pmsRevenueSpecialDeptMapper.selectPmsRevenueSpecialDeptList(prsd);
        for(PmsRevenueSpecialDept sfks : lstorsd){
            BigDecimal sfje = syje.multiply(new BigDecimal(sfks.getRatio())).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
            saveSpecialApp (item, prsl.getRevenueCode(),sfje , sfks.getDeptCode(),  deptType , srlx);
            syje = syje.subtract(sfje).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        if(syje.compareTo(new BigDecimal(0))!=0){
            //门诊
            if(deptType.equals(Constants.CARD_STATE_ONE)){
                //门诊开单
                BigDecimal  mzkd = syje.multiply(new BigDecimal(prsl.getOutpBill())).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                saveSpecialApp (item, prsl.getRevenueCode(),mzkd , item.getBillDept(),  deptType , srlx);
                //门诊执行
                BigDecimal  mzzx = syje.multiply(new BigDecimal(prsl.getOutpExec())).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                saveSpecialApp (item, prsl.getRevenueCode(),mzzx , item.getExecDept(),  deptType , srlx);
                //门诊护理
                BigDecimal  mzhl = syje.multiply(new BigDecimal(prsl.getOutpCare())).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                saveSpecialApp (item, prsl.getRevenueCode(),mzhl , item.getCareDept(),  deptType , srlx);
            }else{
                //住院开单
                BigDecimal  zykd = syje.multiply(new BigDecimal(prsl.getInpBill())).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                saveSpecialApp (item, prsl.getRevenueCode(),zykd , item.getBillDept(),  deptType , srlx);
                //住院执行
                BigDecimal  zyzx = syje.multiply(new BigDecimal(prsl.getInpExec())).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                saveSpecialApp (item, prsl.getRevenueCode(),zyzx , item.getBillDept(),  deptType , srlx);
                //住院护理
                BigDecimal  zyhl = syje.multiply(new BigDecimal(prsl.getInpCare())).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
                saveSpecialApp (item, prsl.getRevenueCode(),zyhl , item.getBillDept(),  deptType , srlx);

            }
        }


    }

    public void saveSpecialApp (PmsRevenueNum item,String revenueCode,BigDecimal sfje ,Long deptCode, String deptType ,String srlx){
        PmsRevenueApport pra = new PmsRevenueApport();
        pra.setCompCode(item.getCompCode());
        pra.setOccureTime(item.getOccureTime());
        pra.setRevenueCode(revenueCode);
        pra.setReceiveNum(null);
        pra.setActualNum(sfje);
        pra.setDpetCode(deptCode);
        pra.setRevenueType(deptType);
        pra.setRevenueKind(srlx);
        pra.setSettleType(item.getSettleMark());
        pra.preInsert();
        pra.setDelFlag(Constants.CARD_STATE_TWO);
        pmsRevenueApportMapper.insertPmsRevenueApport(pra);
    }






}
