package com.zjry.pms.evaluate.service.impl;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.common.utils.StringUtils;
import com.zjry.pms.encode.service.IHrEncodeRuleService;
import com.zjry.pms.evaluate.domain.*;
import com.zjry.pms.evaluate.mapper.PmsEvaluateSetDeptDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.evaluate.mapper.PmsEvaluateSetDeptMapper;
import com.zjry.pms.evaluate.service.IPmsEvaluateSetDeptService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 指标集与科室管理Service业务层处理
 * 
 * @author mk
 * @date 2022-08-09
 */
@Service
public class PmsEvaluateSetDeptServiceImpl extends ServiceImpl<PmsEvaluateSetDeptMapper, PmsEvaluateSetDept> implements IPmsEvaluateSetDeptService {
    @Resource
    private PmsEvaluateSetDeptMapper pmsEvaluateSetDeptMapper;
    @Autowired
    private IHrEncodeRuleService hrEncodeRuleService;
    @Resource
    private PmsEvaluateSetDeptDetailMapper pmsEvaluateSetDeptDetailMapper;

    /**
     * 查询指标集与科室管理
     * 
     * @param id 指标集与科室管理ID
     * @return 指标集与科室管理
     */
    @Override
    public PmsEvaluateSetDept selectPmsEvaluateSetDeptById(Long id) {
        return pmsEvaluateSetDeptMapper.selectPmsEvaluateSetDeptById(id);
    }

    @Override
    public List<PmsEvaluateSetDept> selectPmsSetDeptList(PmsEvaluateSetDept pmsEvaluateSetDept) {
        return pmsEvaluateSetDeptMapper.selectPmsSetDeptList(pmsEvaluateSetDept);
    }

    /**
     * 查询指标集与科室管理列表
     * 
     * @param pmsEvaluateSetDept 指标集与科室管理
     * @return 指标集与科室管理
     */
    @Override
    public List<PmsEvaluateSetDept> selectPmsEvaluateSetDeptList(PmsEvaluateSetDept pmsEvaluateSetDept) {
        return pmsEvaluateSetDeptMapper.selectPmsEvaluateSetDeptList(pmsEvaluateSetDept);
    }

    /**
     * 新增指标集与科室管理
     * 
     * @param pmsEvaluateSetDept 指标集与科室管理
     * @return 结果
     */
    @Override
    public int insertPmsEvaluateSetDept(PmsEvaluateSetDept pmsEvaluateSetDept) {
    	pmsEvaluateSetDept.preInsert();
        pmsEvaluateSetDept.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        String setDeptCode = hrEncodeRuleService.generateEncode("pms_evaluate_set_dept", "SET_DEPT_CODE");
        pmsEvaluateSetDept.setSetDeptCode(setDeptCode);
        return pmsEvaluateSetDeptMapper.insertPmsEvaluateSetDept(pmsEvaluateSetDept);
    }

    /**
     * 修改指标集与科室管理
     * 
     * @param pmsEvaluateSetDept 指标集与科室管理
     * @return 结果
     */
    @Override
    public int updatePmsEvaluateSetDept(PmsEvaluateSetDept pmsEvaluateSetDept) {
    	pmsEvaluateSetDept.preUpdate();
        return pmsEvaluateSetDeptMapper.updatePmsEvaluateSetDept(pmsEvaluateSetDept);
    }

    /**
     * 批量删除指标集与科室管理
     * 
     * @param ids 需要删除的指标集与科室管理ID
     * @return 结果
     */
    @Override
    public int deletePmsEvaluateSetDeptByIds(Long[] ids) {
        return pmsEvaluateSetDeptMapper.deletePmsEvaluateSetDeptByIds(ids);
    }

    /**
     * 删除指标集与科室管理信息
     * 
     * @param id 指标集与科室管理ID
     * @return 结果
     */
    @Override
    public int deletePmsEvaluateSetDeptById(Long id) {
        return pmsEvaluateSetDeptMapper.deletePmsEvaluateSetDeptById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void copy(List<PmsEvaluateSetDept> listN, Date copyDate)throws Exception {
        for(PmsEvaluateSetDept item : listN){
            String setDeptCode = hrEncodeRuleService.generateEncode("pms_evaluate_set_dept", "SET_DEPT_CODE");
            PmsEvaluateSetDeptDetail pesd = new PmsEvaluateSetDeptDetail();
            pesd.setCompCode(item.getCompCode());
            pesd.setNowDate(item.getNowDate());
            pesd.setSetDeptCode(item.getSetDeptCode());
            List<PmsEvaluateSetDeptDetail> list = pmsEvaluateSetDeptDetailMapper.selectPmsEvaluateSetDeptDetailList(pesd);
            for(PmsEvaluateSetDeptDetail ite : list){
                ite.setSetDeptCode(setDeptCode);
                ite.setNowDate(copyDate);
                ite.preInsert();
                pmsEvaluateSetDeptDetailMapper.insertPmsEvaluateSetDeptDetail(ite);
            }
            item.setNowDate(copyDate);
            item.setSetDeptCode(setDeptCode);
            item.preInsert();
            pmsEvaluateSetDeptMapper.insertPmsEvaluateSetDept(item);
        }



    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void savePmsEvaSetDeptDN(SetDeptDNList setDeptDNList)throws Exception {
        List<PmsEvaSetDeptDN> list = setDeptDNList.getPmsList();
        for(PmsEvaSetDeptDN item: list){
            if(StringUtils.isNotEmpty(item.getOneTarget())){
               if(null!=item.getOneID()){
                   creatDetailInfo(item,item.getOneTarget(),item.getOneID());
               }else{
                creatSetDeptInfo(item,item.getOneDate(),item.getOneTarget());
               }
            }
            if(StringUtils.isNotEmpty(item.getTwoTarget())){
                if(null!=item.getTwoID()){
                    creatDetailInfo(item,item.getTwoTarget(),item.getTwoID());
                }else{
                    creatSetDeptInfo(item,item.getTwoDate(),item.getTwoTarget());
                }
            }
            if(StringUtils.isNotEmpty(item.getThreeTarget())){
                if(null!=item.getThreeID()){
                    creatDetailInfo(item,item.getThreeTarget(),item.getThreeID());
                }else{
                    creatSetDeptInfo(item,item.getThreeDate(),item.getThreeTarget());
                }
            }
            if(StringUtils.isNotEmpty(item.getFourTarget())){
                if(null!=item.getFourID()){
                    creatDetailInfo(item,item.getFourTarget(),item.getFourID());
                }else{
                    creatSetDeptInfo(item,item.getFourDate(),item.getFourTarget());
                }
            }
            if(StringUtils.isNotEmpty(item.getFiveTarget())){
                if(null!=item.getFiveID()){
                    creatDetailInfo(item,item.getFiveTarget(),item.getFiveID());
                }else{
                    creatSetDeptInfo(item,item.getFiveDate(),item.getFiveTarget());
                }
            }
            if(StringUtils.isNotEmpty(item.getSixTarget())){
                if(null!=item.getSixID()){
                    creatDetailInfo(item,item.getSixTarget(),item.getSixID());
                }else{
                    creatSetDeptInfo(item,item.getSixDate(),item.getSixTarget());
                }
            }
            if(StringUtils.isNotEmpty(item.getSevenTarget())){
                if(null!=item.getSevenID()){
                    creatDetailInfo(item,item.getSevenTarget(),item.getSevenID());
                }else{
                    creatSetDeptInfo(item,item.getSevenDate(),item.getSevenTarget());
                }
            }
            if(StringUtils.isNotEmpty(item.getEightTarget())){
                if(null!=item.getEightID()){
                    creatDetailInfo(item,item.getEightTarget(),item.getEightID());
                }else{
                    creatSetDeptInfo(item,item.getEightDate(),item.getEightTarget());
                }
            }
            if(StringUtils.isNotEmpty(item.getNineTarget())){
                if(null!=item.getNineID()){
                    creatDetailInfo(item,item.getNineTarget(),item.getNineID());
                }else{
                    creatSetDeptInfo(item,item.getNineDate(),item.getNineTarget());
                }
            }
            if(StringUtils.isNotEmpty(item.getTenTarget())){
                if(null!=item.getTenID()){
                    creatDetailInfo(item,item.getTenTarget(),item.getTenID());
                }else{
                    creatSetDeptInfo(item,item.getTenDate(),item.getTenTarget());
                }
            }
            if(StringUtils.isNotEmpty(item.getElevenTarget())){
                if(null!=item.getElevenID()){
                    creatDetailInfo(item,item.getElevenTarget(),item.getElevenID());
                }else{
                    creatSetDeptInfo(item,item.getElevenDate(),item.getElevenTarget());
                }
            }
            if(StringUtils.isNotEmpty(item.getTwelveTarget())){
                if(null!=item.getTwelveID()){
                    creatDetailInfo(item,item.getTwelveTarget(),item.getTwelveID());
                }else{
                    creatSetDeptInfo(item,item.getTwelveDate(),item.getTwelveTarget());
                }
            }
        }
    }

    @Override
    public List<PmsEvaluateBscSort> selectBscSort(String setCode) {
        return pmsEvaluateSetDeptMapper.selectBscSort(setCode);
    }

    private void  creatDetailInfo(PmsEvaSetDeptDN item,String target,Long id){
        PmsEvaluateSetDeptDetail pesd = new PmsEvaluateSetDeptDetail();
        pesd.setReduce(item.getReduce());
        pesd.setDeduct(item.getDeduct());
        pesd.setIncrease(item.getIncrease());
        pesd.setExtra(item.getExtra());
        pesd.setUnit(item.getUnit());
        pesd.setTarget(target);
        pesd.setIndexScore(item.getIndexScore());
        pesd.setIsHyper(item.getIsHyper());
        pesd.setIsNegative(item.getIsNegative());
        pesd.setIsFixed(item.getIsFixed());
        pesd.setThreshold(item.getThreshold());
        pesd.setOverLimit(item.getOverLimit());
        pesd.setDeductionLimit(item.getDeductionLimit());
        pesd.preUpdate();
        pesd.setId(id);
        pmsEvaluateSetDeptDetailMapper.updatePmsEvaluateSetDeptDetail(pesd);
    }

    private void creatSetDeptInfo(PmsEvaSetDeptDN item, Date nowDate, String target)throws Exception {
        PmsEvaluateSetDept pes = new PmsEvaluateSetDept();
        pes.setCompCode(item.getCompCode());
        pes.setAccountCode(item.getAccountCode());
        String setDeptCode = hrEncodeRuleService.generateEncode("pms_evaluate_set_dept", "SET_DEPT_CODE");
        pes.setSetDeptCode(setDeptCode);
        pes.setSetCode(item.getSetCode());
        pes.setDeptCode(item.getDeptCode().toString());
        pes.setNowDate(nowDate);
        pes.preUpdate();
        pes.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        pmsEvaluateSetDeptMapper.insertPmsEvaluateSetDept(pes);
        //=========================子表======================================
        PmsEvaluateSetDeptDetail pesd = new PmsEvaluateSetDeptDetail();
        pesd.setCompCode(item.getCompCode());
        pesd.setAccountCode(item.getAccountCode());
        pesd.setSetDeptCode(setDeptCode);
        pesd.setIndexCode(item.getIndexCode());
        pesd.setReduce(item.getReduce());
        pesd.setDeduct(item.getDeduct());
        pesd.setIncrease(item.getIncrease());
        pesd.setExtra(item.getExtra());
        pesd.setUnit(item.getUnit());
        pesd.setTarget(target);
        pesd.setIndexScore(item.getIndexScore());
        pesd.setIsHyper(item.getIsHyper());
        pesd.setIsNegative(item.getIsNegative());
        pesd.setIsFixed(item.getIsFixed());
        pesd.setThreshold(item.getThreshold());
        pesd.setOverLimit(item.getOverLimit());
        pesd.setDeductionLimit(item.getDeductionLimit());
        pesd.setNowDate(nowDate);
        pesd.preInsert();
        pesd.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        pmsEvaluateSetDeptDetailMapper.insertPmsEvaluateSetDeptDetail(pesd);



    }
}
