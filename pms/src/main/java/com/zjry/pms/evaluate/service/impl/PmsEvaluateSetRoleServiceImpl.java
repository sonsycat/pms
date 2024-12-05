package com.zjry.pms.evaluate.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.common.utils.DateUtils;
import com.zjry.pms.evaluate.domain.PmsEvaRleList;
import com.zjry.pms.evaluate.domain.PmsEvaluateSetIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.evaluate.mapper.PmsEvaluateSetRoleMapper;
import com.zjry.pms.evaluate.domain.PmsEvaluateSetRole;
import com.zjry.pms.evaluate.service.IPmsEvaluateSetRoleService;

import javax.annotation.Resource;

/**
 * 指标集角色Service业务层处理
 * 
 * @author mk
 * @date 2022-08-05
 */
@Service
public class PmsEvaluateSetRoleServiceImpl extends ServiceImpl<PmsEvaluateSetRoleMapper, PmsEvaluateSetRole> implements IPmsEvaluateSetRoleService {
    @Resource
    private PmsEvaluateSetRoleMapper pmsEvaluateSetRoleMapper;

    /**
     * 查询指标集角色
     * 
     * @param id 指标集角色ID
     * @return 指标集角色
     */
    @Override
    public PmsEvaluateSetRole selectPmsEvaluateSetRoleById(Long id) {
        return pmsEvaluateSetRoleMapper.selectPmsEvaluateSetRoleById(id);
    }

    /**
     * 查询指标集角色列表
     * 
     * @param pmsEvaluateSetRole 指标集角色
     * @return 指标集角色
     */
    @Override
    public List<PmsEvaluateSetRole> selectPmsEvaluateSetRoleList(PmsEvaluateSetRole pmsEvaluateSetRole) {
        return pmsEvaluateSetRoleMapper.selectPmsEvaluateSetRoleList(pmsEvaluateSetRole);
    }

    /**
     * 新增指标集角色
     * 
     * @param pmsEvaRleList 指标集角色
     * @return 结果
     */
    @Override
    public void insertPmsEvaluateSetRole(PmsEvaRleList pmsEvaRleList) {
        pmsEvaluateSetRoleMapper.deleteByCompCodeAndsetCode(pmsEvaRleList.getCompCode(),pmsEvaRleList.getSetCode());
        List<PmsEvaluateSetRole> list = pmsEvaRleList.getListPms();
        if(list!=null && list.size()>0){
            for(PmsEvaluateSetRole item : list){
                item.preInsert();
                item.setCompCode(pmsEvaRleList.getCompCode());
                item.setSetCode(pmsEvaRleList.getSetCode());
                item.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
                pmsEvaluateSetRoleMapper.insertPmsEvaluateSetRole(item);
            }
        }
    }

    /**
     * 修改指标集角色
     * 
     * @param pmsEvaluateSetRole 指标集角色
     * @return 结果
     */
    @Override
    public int updatePmsEvaluateSetRole(PmsEvaluateSetRole pmsEvaluateSetRole) {
    	pmsEvaluateSetRole.preUpdate();
        return pmsEvaluateSetRoleMapper.updatePmsEvaluateSetRole(pmsEvaluateSetRole);
    }

    /**
     * 批量删除指标集角色
     * 
     * @param ids 需要删除的指标集角色ID
     * @return 结果
     */
    @Override
    public int deletePmsEvaluateSetRoleByIds(Long[] ids) {
        return pmsEvaluateSetRoleMapper.deletePmsEvaluateSetRoleByIds(ids);
    }

    /**
     * 删除指标集角色信息
     * 
     * @param id 指标集角色ID
     * @return 结果
     */
    @Override
    public int deletePmsEvaluateSetRoleById(Long id) {
        return pmsEvaluateSetRoleMapper.deletePmsEvaluateSetRoleById(id);
    }
}
