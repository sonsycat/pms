package com.zjry.pms.evaluate.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.evaluate.domain.PmsEvaRleList;
import com.zjry.pms.evaluate.domain.PmsEvaluateSetRole;

/**
 * 指标集角色Service接口
 * 
 * @author mk
 * @date 2022-08-05
 */
public interface IPmsEvaluateSetRoleService extends IService<PmsEvaluateSetRole> {
    /**
     * 查询指标集角色
     * 
     * @param id 指标集角色ID
     * @return 指标集角色
     */
    public PmsEvaluateSetRole selectPmsEvaluateSetRoleById(Long id);

    /**
     * 查询指标集角色列表
     * 
     * @param pmsEvaluateSetRole 指标集角色
     * @return 指标集角色集合
     */
    public List<PmsEvaluateSetRole> selectPmsEvaluateSetRoleList(PmsEvaluateSetRole pmsEvaluateSetRole);

    /**
     * 新增指标集角色
     * 
     * @param pmsEvaluateSetRole 指标集角色
     * @return 结果
     */
    public void insertPmsEvaluateSetRole(PmsEvaRleList pmsEvaRleList);

    /**
     * 修改指标集角色
     * 
     * @param pmsEvaluateSetRole 指标集角色
     * @return 结果
     */
    public int updatePmsEvaluateSetRole(PmsEvaluateSetRole pmsEvaluateSetRole);

    /**
     * 批量删除指标集角色
     * 
     * @param ids 需要删除的指标集角色ID
     * @return 结果
     */
    public int deletePmsEvaluateSetRoleByIds(Long[] ids);

    /**
     * 删除指标集角色信息
     * 
     * @param id 指标集角色ID
     * @return 结果
     */
    public int deletePmsEvaluateSetRoleById(Long id);
}
