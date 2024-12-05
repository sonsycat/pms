package com.zjry.pms.evaluate.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.evaluate.domain.PmsEvaluateSetRole;
import org.apache.ibatis.annotations.Param;

/**
 * 指标集角色Mapper接口
 * 
 * @author mk
 * @date 2022-08-05
 */
public interface PmsEvaluateSetRoleMapper extends BaseMapper<PmsEvaluateSetRole> {
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
    public int insertPmsEvaluateSetRole(PmsEvaluateSetRole pmsEvaluateSetRole);

    /**
     * 修改指标集角色
     * 
     * @param pmsEvaluateSetRole 指标集角色
     * @return 结果
     */
    public int updatePmsEvaluateSetRole(PmsEvaluateSetRole pmsEvaluateSetRole);

    /**
     * 删除指标集角色
     * 
     * @param id 指标集角色ID
     * @return 结果
     */
    public int deletePmsEvaluateSetRoleById(Long id);

    /**
     * 批量删除指标集角色
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsEvaluateSetRoleByIds(Long[] ids);

    void deleteByCompCodeAndsetCode(@Param("compCode") Long compCode, @Param("setCode")  String setCode);
}
