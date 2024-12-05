package com.zjry.pms.evaluate.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.common.utils.StringUtils;
import com.zjry.pms.common.Constants;
import com.zjry.pms.encode.service.IHrEncodeRuleTreeService;
import com.zjry.pms.evaluate.domain.PmsEvaluateSet;
import com.zjry.pms.evaluate.mapper.PmsEvaluateSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.evaluate.mapper.PmsEvaluateSetTypeMapper;
import com.zjry.pms.evaluate.domain.PmsEvaluateSetType;
import com.zjry.pms.evaluate.service.IPmsEvaluateSetTypeService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 指标集分类Service业务层处理
 * 
 * @author mk
 * @date 2022-08-03
 */
@Service
public class PmsEvaluateSetTypeServiceImpl extends ServiceImpl<PmsEvaluateSetTypeMapper, PmsEvaluateSetType> implements IPmsEvaluateSetTypeService {
    @Resource
    private PmsEvaluateSetTypeMapper pmsEvaluateSetTypeMapper;
    @Autowired
    private IHrEncodeRuleTreeService hrEncodeRuleTreeService;
    @Autowired
    private PmsEvaluateSetMapper pmsEvaluateSetMapper;

    /**
     * 查询指标集分类
     * 
     * @param id 指标集分类ID
     * @return 指标集分类
     */
    @Override
    public PmsEvaluateSetType selectPmsEvaluateSetTypeById(Long id) {
        return pmsEvaluateSetTypeMapper.selectPmsEvaluateSetTypeById(id);
    }

    /**
     * 查询指标集分类列表
     * 
     * @param pmsEvaluateSetType 指标集分类
     * @return 指标集分类
     */
    @Override
    public List<PmsEvaluateSetType> selectPmsEvaluateSetTypeList(PmsEvaluateSetType pmsEvaluateSetType) {
        return pmsEvaluateSetTypeMapper.selectPmsEvaluateSetTypeList(pmsEvaluateSetType);
    }

    /**
     * 新增指标集分类
     * 
     * @param pmsEvaluateSetType 指标集分类
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertPmsEvaluateSetType(PmsEvaluateSetType pmsEvaluateSetType)throws Exception {
        pmsEvaluateSetType.preInsert();
        pmsEvaluateSetType.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        pmsEvaluateSetType.setBscCode(hrEncodeRuleTreeService.generateTreeEncode("pms_evaluate_set_type","BSC_CODE",pmsEvaluateSetType.getParentCode()));
        if(StringUtils.isEmpty(pmsEvaluateSetType.getParentCode())){
            pmsEvaluateSetType.setParentCode(Constants.PARENT_CODE);
            pmsEvaluateSetType.setAncestors(Constants.PARENT_CODES);
        }else{
            PmsEvaluateSetType parent =  pmsEvaluateSetTypeMapper.getBecCode(pmsEvaluateSetType.getParentCode());
            if(Constants.IS_CHANGE_YES.equals(parent.getIsLast())){
                parent.setIsLast(Constants.IS_CHANGE_NO);
                pmsEvaluateSetTypeMapper.updatePmsEvaluateSetType(parent);
            }
            pmsEvaluateSetType.setAncestors(parent.getAncestors()+parent.getBscCode()+",");
        }
        return pmsEvaluateSetTypeMapper.insertPmsEvaluateSetType(pmsEvaluateSetType);
    }

    @Override
    public String updateIsStop(Long id, String isStop) {
        PmsEvaluateSetType sbmc = pmsEvaluateSetTypeMapper.selectPmsEvaluateSetTypeById(id);
        if(isStop.equals(Constants.IS_STOP_NO)){
            if(!sbmc.getParentCode().equals(Constants.PARENT_CODE)){
                PmsEvaluateSetType   parent =  pmsEvaluateSetTypeMapper.getBecCode(sbmc.getParentCode());
                if(parent.getIsStop().equals(Constants.IS_STOP_YES)){
                    return "操作失败:父级处于停用状态";
                }
            }

        }else{
            //停用
            if(Constants.IS_CHANGE_NO.equals(sbmc.getIsLast())){
                return "操作失败:有下级不允许停用";
            }
        }
        PmsEvaluateSetType spbmc = new PmsEvaluateSetType();
        spbmc.setId(id);
        spbmc.setIsStop(isStop);
        pmsEvaluateSetTypeMapper.updatePmsEvaluateSetType(spbmc);
        return null;
    }



    /**
     * 修改指标集分类
     * 
     * @param pmsEvaluateSetType 指标集分类
     * @return 结果
     */
    @Override
    public int updatePmsEvaluateSetType(PmsEvaluateSetType pmsEvaluateSetType) {
    	pmsEvaluateSetType.preUpdate();
        return pmsEvaluateSetTypeMapper.updatePmsEvaluateSetType(pmsEvaluateSetType);
    }

    /**
     * 批量删除指标集分类
     * 
     * @param ids 需要删除的指标集分类ID
     * @return 结果
     */
    @Override
    public String deletePmsEvaluateSetTypeByIds(Long[] ids) {
        PmsEvaluateSetType sbmc = pmsEvaluateSetTypeMapper.selectPmsEvaluateSetTypeById(ids[0]);
        if(Constants.IS_CHANGE_NO.equals(sbmc.getIsLast())){
            return "操作失败:有下级不允许删除";
        }
        QueryWrapper<PmsEvaluateSet> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(PmsEvaluateSet::getId);
        if(Constants.PARENT_CODE.equals(sbmc.getParentCode())){
            wrapper.lambda().eq(PmsEvaluateSet::getOneCode, sbmc.getBscCode()).last("LIMIT 1");
        }else{
            wrapper.lambda().eq(PmsEvaluateSet::getSecondCode, sbmc.getBscCode()).last("LIMIT 1");
        }
        Integer count = pmsEvaluateSetMapper.selectCount(wrapper);
        if(count > 0){
            return "操作失败:指标集分类已使用，不允许删除";
        }

        pmsEvaluateSetTypeMapper.deletePmsEvaluateSetTypeByIds(ids);
        PmsEvaluateSetType bsc = new PmsEvaluateSetType();
        bsc.setParentCode(sbmc.getParentCode());
        List<PmsEvaluateSetType> list = pmsEvaluateSetTypeMapper.selectPmsEvaluateSetTypeList(bsc);
        if(list.size()==0){
            bsc = pmsEvaluateSetTypeMapper.getBecCode(sbmc.getParentCode());
            bsc.setIsLast(Constants.IS_CHANGE_YES);
            pmsEvaluateSetTypeMapper.updatePmsEvaluateSetType(bsc);
        }
        return null;

    }

    /**
     * 删除指标集分类信息
     * 
     * @param id 指标集分类ID
     * @return 结果
     */
    @Override
    public int deletePmsEvaluateSetTypeById(Long id) {
        return pmsEvaluateSetTypeMapper.deletePmsEvaluateSetTypeById(id);
    }
}
