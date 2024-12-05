package com.zjry.advanceQuery.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.advanceQuery.domain.SysAdvanceQueryConfig;
import com.zjry.advanceQuery.domain.SysAdvanceQueryConfigDetail;

/**
 * 高级查询配置Service接口
 * 
 * @author yangtao
 * @date 2021-10-15
 */
public interface ISysAdvanceQueryConfigService extends IService<SysAdvanceQueryConfig> {
    /**
     * 查询高级查询配置
     * 
     * @param id 高级查询配置ID
     * @return 高级查询配置
     */
    public SysAdvanceQueryConfig selectSysAdvanceQueryConfigById(Long id);
    
    public List<Map> selectDetailByRouteId(String routeId);

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
    
    public int saveDetail(SysAdvanceQueryConfig sysAdvanceQueryConfig);

    /**
     * 批量删除高级查询配置
     * 
     * @param ids 需要删除的高级查询配置ID
     * @return 结果
     */
    public int deleteSysAdvanceQueryConfigByIds(Long[] ids);

    /**
     * 删除高级查询配置信息
     * 
     * @param id 高级查询配置ID
     * @return 结果
     */
    public int deleteSysAdvanceQueryConfigById(Long id);
    
    public List<Map> selectMenuPath();
    
    public boolean checkSqlIdExists(String sqlId);
}
