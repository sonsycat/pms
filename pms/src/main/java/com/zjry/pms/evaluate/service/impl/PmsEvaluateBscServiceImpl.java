package com.zjry.pms.evaluate.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.common.utils.DateUtils;
import com.zjry.common.utils.StringUtils;
import com.zjry.pms.common.Constants;
import com.zjry.pms.encode.service.IHrEncodeRuleTreeService;
import com.zjry.pms.evaluate.domain.PmsEvaluateIndex;
import com.zjry.pms.evaluate.mapper.PmsEvaluateIndexMapper;
import com.zjry.pms.evaluate.service.IPmsEvaluateIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.evaluate.mapper.PmsEvaluateBscMapper;
import com.zjry.pms.evaluate.domain.PmsEvaluateBsc;
import com.zjry.pms.evaluate.service.IPmsEvaluateBscService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * BSC分类Service业务层处理
 * 
 * @author mk
 * @date 2022-08-01
 */
@Service
public class PmsEvaluateBscServiceImpl extends ServiceImpl<PmsEvaluateBscMapper, PmsEvaluateBsc> implements IPmsEvaluateBscService {
    @Resource
    private PmsEvaluateBscMapper pmsEvaluateBscMapper;
    @Autowired
    private IHrEncodeRuleTreeService hrEncodeRuleTreeService;
    @Autowired
    private PmsEvaluateIndexMapper pmsEvaluateIndexMapper;

    /**
     * 查询BSC分类
     * 
     * @param id BSC分类ID
     * @return BSC分类
     */
    @Override
    public PmsEvaluateBsc selectPmsEvaluateBscById(Long id) {
        return pmsEvaluateBscMapper.selectPmsEvaluateBscById(id);
    }

    /**
     * 查询BSC分类列表
     * 
     * @param pmsEvaluateBsc BSC分类
     * @return BSC分类
     */
    @Override
    public List<PmsEvaluateBsc> selectPmsEvaluateBscList(PmsEvaluateBsc pmsEvaluateBsc) {
        return pmsEvaluateBscMapper.selectPmsEvaluateBscList(pmsEvaluateBsc);
    }

    /**
     * 新增BSC分类
     * 
     * @param pmsEvaluateBsc BSC分类
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertPmsEvaluateBsc(PmsEvaluateBsc pmsEvaluateBsc) throws Exception {
        pmsEvaluateBsc.preInsert();
        pmsEvaluateBsc.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        pmsEvaluateBsc.setBscCode(hrEncodeRuleTreeService.generateTreeEncode("pms_evaluate_bsc","BSC_CODE",pmsEvaluateBsc.getParentCode()));
        if(StringUtils.isEmpty(pmsEvaluateBsc.getParentCode())){
            pmsEvaluateBsc.setParentCode(Constants.PARENT_CODE);
            pmsEvaluateBsc.setAncestors(Constants.PARENT_CODES);
        }else{
            PmsEvaluateBsc   parent =  pmsEvaluateBscMapper.getBecCode(pmsEvaluateBsc.getParentCode());
            if(Constants.IS_CHANGE_YES.equals(parent.getIsLast())){
                parent.setIsLast(Constants.IS_CHANGE_NO);
                pmsEvaluateBscMapper.updatePmsEvaluateBsc(parent);
            }
            pmsEvaluateBsc.setAncestors(parent.getAncestors()+parent.getBscCode()+",");
        }
        return pmsEvaluateBscMapper.insertPmsEvaluateBsc(pmsEvaluateBsc);
    }


    @Override
    public String updateIsStop(Long id, String isStop) {
        PmsEvaluateBsc sbmc = pmsEvaluateBscMapper.selectPmsEvaluateBscById(id);
        if(isStop.equals(Constants.IS_STOP_NO)){
            if(!sbmc.getParentCode().equals(Constants.PARENT_CODE)){
                PmsEvaluateBsc   parent =  pmsEvaluateBscMapper.getBecCode(sbmc.getParentCode());
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
        PmsEvaluateBsc spbmc = new PmsEvaluateBsc();
        spbmc.setId(id);
        spbmc.setIsStop(isStop);
        pmsEvaluateBscMapper.updatePmsEvaluateBsc(spbmc);
        return null;
    }




    /**
     * 修改BSC分类
     * 
     * @param pmsEvaluateBsc BSC分类
     * @return 结果
     */
    @Override
    public int updatePmsEvaluateBsc(PmsEvaluateBsc pmsEvaluateBsc) {
    	pmsEvaluateBsc.preUpdate();
        return pmsEvaluateBscMapper.updatePmsEvaluateBsc(pmsEvaluateBsc);
    }

    /**
     * 批量删除BSC分类
     * 
     * @param ids 需要删除的BSC分类ID
     * @return 结果
     */
    @Override
    public String deletePmsEvaluateBscByIds(Long[] ids) {
        PmsEvaluateBsc sbmc = pmsEvaluateBscMapper.selectPmsEvaluateBscById(ids[0]);
        if(Constants.IS_CHANGE_NO.equals(sbmc.getIsLast())){
            return "操作失败:有下级不允许删除";
        }
        QueryWrapper<PmsEvaluateIndex> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(PmsEvaluateIndex::getId);
        if(Constants.PARENT_CODE.equals(sbmc.getParentCode())){
            wrapper.lambda().eq(PmsEvaluateIndex::getBscOneCode, sbmc.getBscCode()).last("LIMIT 1");
        }else{
            wrapper.lambda().eq(PmsEvaluateIndex::getBscSecondCode, sbmc.getBscCode()).last("LIMIT 1");
        }
        Integer count = pmsEvaluateIndexMapper.selectCount(wrapper);
        if(count > 0){
            return "操作失败:BSC分类已使用，不允许删除";
        }

        pmsEvaluateBscMapper.deletePmsEvaluateBscByIds(ids);
        PmsEvaluateBsc bsc = new PmsEvaluateBsc();
        bsc.setParentCode(sbmc.getParentCode());
        List<PmsEvaluateBsc> list = pmsEvaluateBscMapper.selectPmsEvaluateBscList(bsc);
        if(list.size()==0){
            bsc = pmsEvaluateBscMapper.getBecCode(sbmc.getParentCode());
            bsc.setIsLast(Constants.IS_CHANGE_YES);
            pmsEvaluateBscMapper.updatePmsEvaluateBsc(bsc);
        }
        return null;
    }

    /**
     * 删除BSC分类信息
     * 
     * @param id BSC分类ID
     * @return 结果
     */
    @Override
    public int deletePmsEvaluateBscById(Long id) {
        return pmsEvaluateBscMapper.deletePmsEvaluateBscById(id);
    }
}
