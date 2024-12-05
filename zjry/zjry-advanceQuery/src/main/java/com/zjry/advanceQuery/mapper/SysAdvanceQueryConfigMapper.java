package com.zjry.advanceQuery.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.advanceQuery.domain.SysAdvanceQueryConfig;
import com.zjry.advanceQuery.domain.SysAdvanceQueryConfigDetail;

/**
 * 高级查询配置Mapper接口
 * 
 * @author yangtao
 * @date 2021-10-15
 */
public interface SysAdvanceQueryConfigMapper extends BaseMapper<SysAdvanceQueryConfig> {
    /**
     * 查询高级查询配置
     * 
     * @param id 高级查询配置ID
     * @return 高级查询配置
     */
    public SysAdvanceQueryConfig selectSysAdvanceQueryConfigById(Long id);
    
    public List<Map> selectDetailByRouteId(String routeId);
    
    public SysAdvanceQueryConfig selectConfigByRouteId(String routeId);

    /**
     * 查询高级查询配置列表
     * 
     * @param sysAdvanceQueryConfig 高级查询配置
     * @return 高级查询配置集合
     */
    public List<SysAdvanceQueryConfig> selectSysAdvanceQueryConfigList(SysAdvanceQueryConfig sysAdvanceQueryConfig);

    /**
     * 新增高级查询配置
     * 
     * @param sysAdvanceQueryConfig 高级查询配置
     * @return 结果
     */
    public int insertSysAdvanceQueryConfig(SysAdvanceQueryConfig sysAdvanceQueryConfig);

    /**
     * 修改高级查询配置
     * 
     * @param sysAdvanceQueryConfig 高级查询配置
     * @return 结果
     */
    public int updateSysAdvanceQueryConfig(SysAdvanceQueryConfig sysAdvanceQueryConfig);

    /**
     * 删除高级查询配置
     * 
     * @param id 高级查询配置ID
     * @return 结果
     */
    public int deleteSysAdvanceQueryConfigById(Long id);

    /**
     * 批量删除高级查询配置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysAdvanceQueryConfigByIds(Long[] ids);

    /**
     * 批量删除${subTable.functionName}
     * 
     * @param customerIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysAdvanceQueryConfigDetailByConfigIds(Long[] ids);
    
    /**
     * 批量新增${subTable.functionName}
     * 
     * @param sysAdvanceQueryConfigDetailList ${subTable.functionName}列表
     * @return 结果
     */
    public int batchSysAdvanceQueryConfigDetail(List<SysAdvanceQueryConfigDetail> sysAdvanceQueryConfigDetailList);
    

    /**
     * 通过高级查询配置ID删除${subTable.functionName}信息
     * 
     * @param id 高级查询配置ID
     * @return 结果
     */
    public int deleteSysAdvanceQueryConfigDetailByConfigId(Long id);
    
    /**
     * 查询可以配置的表
     * @return
     */
    public List<Map> selectDbTableList();
    
    public Map selectTableByName(@Param("tableName") String tableName);
    
    public List<Map> selectTableFields(@Param("tableName") String tableName);
    
    public List<Map> selectMenuPath();
}
