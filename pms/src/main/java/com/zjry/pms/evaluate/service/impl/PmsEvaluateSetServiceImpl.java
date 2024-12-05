package com.zjry.pms.evaluate.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.common.utils.DateUtils;
import com.zjry.pms.common.Constants;
import com.zjry.pms.encode.service.IHrEncodeRuleService;
import com.zjry.pms.evaluate.domain.PmsEvaluateSetIndex;
import com.zjry.pms.evaluate.domain.PmsEvaluateSetRole;
import com.zjry.pms.evaluate.mapper.PmsEvaluateSetIndexMapper;
import com.zjry.pms.evaluate.mapper.PmsEvaluateSetRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.evaluate.mapper.PmsEvaluateSetMapper;
import com.zjry.pms.evaluate.domain.PmsEvaluateSet;
import com.zjry.pms.evaluate.service.IPmsEvaluateSetService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 指标集管理Service业务层处理
 * 
 * @author mk
 * @date 2022-08-04
 */
@Service
public class PmsEvaluateSetServiceImpl extends ServiceImpl<PmsEvaluateSetMapper, PmsEvaluateSet> implements IPmsEvaluateSetService {
    @Resource
    private PmsEvaluateSetMapper pmsEvaluateSetMapper;
    @Autowired
    private IHrEncodeRuleService hrEncodeRuleService;
    @Resource
    private PmsEvaluateSetRoleMapper pmsEvaluateSetRoleMapper;
    @Resource
    private PmsEvaluateSetIndexMapper pmsEvaluateSetIndexMapper;



    /**
     * 查询指标集管理
     * 
     * @param id 指标集管理ID
     * @return 指标集管理
     */
    @Override
    public PmsEvaluateSet selectPmsEvaluateSetById(Long id) {
        return pmsEvaluateSetMapper.selectPmsEvaluateSetById(id);
    }

    /**
     * 查询指标集管理列表
     * 
     * @param pmsEvaluateSet 指标集管理
     * @return 指标集管理
     */
    @Override
    public List<PmsEvaluateSet> selectPmsEvaluateSetList(PmsEvaluateSet pmsEvaluateSet) {
        return pmsEvaluateSetMapper.selectPmsEvaluateSetList(pmsEvaluateSet);
    }

    /**
     * 新增指标集管理
     * 
     * @param pmsEvaluateSet 指标集管理
     * @return 结果
     */
    @Override
    public int insertPmsEvaluateSet(PmsEvaluateSet pmsEvaluateSet) {
    	pmsEvaluateSet.preInsert();
        String setCode = hrEncodeRuleService.generateEncode("pms_evaluate_set", "SET_CODE");
        pmsEvaluateSet.setSetCode(setCode);
        pmsEvaluateSet.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        return pmsEvaluateSetMapper.insertPmsEvaluateSet(pmsEvaluateSet);
    }

    /**
     * 修改指标集管理
     * 
     * @param pmsEvaluateSet 指标集管理
     * @return 结果
     */
    @Override
    public int updatePmsEvaluateSet(PmsEvaluateSet pmsEvaluateSet) {
    	pmsEvaluateSet.preUpdate();
        return pmsEvaluateSetMapper.updatePmsEvaluateSet(pmsEvaluateSet);
    }

    /**
     * 批量删除指标集管理
     * 
     * @param ids 需要删除的指标集管理ID
     * @return 结果
     */
    @Override
    public int deletePmsEvaluateSetByIds(Long[] ids) {
        return pmsEvaluateSetMapper.deletePmsEvaluateSetByIds(ids);
    }

    /**
     * 删除指标集管理信息
     * 
     * @param id 指标集管理ID
     * @return 结果
     */
    @Override
    public int deletePmsEvaluateSetById(Long id) {
        return pmsEvaluateSetMapper.deletePmsEvaluateSetById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void copy(PmsEvaluateSet pmsEvaluateSet)throws Exception {
        PmsEvaluateSet pms = new PmsEvaluateSet();
        pms.setCompCode(pmsEvaluateSet.getCompCode());
        pms.setSetCode(pmsEvaluateSet.getSetCode());
        List<PmsEvaluateSet> list = pmsEvaluateSetMapper.selectPmsEvaluateSetList(pms);
        PmsEvaluateSet pmsSet = list.get(0);
        String setCode = hrEncodeRuleService.generateEncode("pms_evaluate_set", "SET_CODE");
        pmsSet.setIsBonus(pmsSet.getIsBonus());
        pmsSet.setSetCode(setCode);
        pmsSet.setSetName(pmsEvaluateSet.getSetName());
        pmsSet.preInsert();
        pmsEvaluateSetMapper.insertPmsEvaluateSet(pmsSet);

        PmsEvaluateSetRole pmsRole = new PmsEvaluateSetRole();
        pmsRole.setCompCode(pmsEvaluateSet.getCompCode());
        pmsRole.setSetCode(pmsEvaluateSet.getSetCode());
        List<PmsEvaluateSetRole>  list1 = pmsEvaluateSetRoleMapper.selectPmsEvaluateSetRoleList(pmsRole);
        for(PmsEvaluateSetRole item : list1){
            item.preInsert();
            item.setSetCode(setCode);
            pmsEvaluateSetRoleMapper.insertPmsEvaluateSetRole(item);
        }

        PmsEvaluateSetIndex pmsInd = new PmsEvaluateSetIndex();
        pmsInd.setCompCode(pmsEvaluateSet.getCompCode());
        pmsInd.setSetCode(pmsEvaluateSet.getSetCode());
        List<PmsEvaluateSetIndex> list2 = pmsEvaluateSetIndexMapper.selectPmsEvaluateSetIndexList(pmsInd);
        for(PmsEvaluateSetIndex item : list2){
            item.preInsert();
            item.setSetCode(setCode);
            pmsEvaluateSetIndexMapper.insertPmsEvaluateSetIndex(item);
        }
    }
}
