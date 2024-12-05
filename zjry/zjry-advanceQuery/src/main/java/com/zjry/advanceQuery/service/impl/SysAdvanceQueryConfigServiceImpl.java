package com.zjry.advanceQuery.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.advanceQuery.domain.SysAdvanceQueryConfig;
import com.zjry.advanceQuery.domain.SysAdvanceQueryConfigDetail;
import com.zjry.advanceQuery.mapper.SysAdvanceQueryConfigMapper;
import com.zjry.advanceQuery.service.ISysAdvanceQueryConfigService;
import com.zjry.common.utils.DateUtils;
import com.zjry.common.utils.StringUtils;

/**
 * 高级查询配置Service业务层处理
 * 
 * @author yangtao
 * @date 2021-10-15
 */
@Service
public class SysAdvanceQueryConfigServiceImpl extends ServiceImpl<SysAdvanceQueryConfigMapper, SysAdvanceQueryConfig> implements ISysAdvanceQueryConfigService {
    @Autowired
    private SysAdvanceQueryConfigMapper sysAdvanceQueryConfigMapper;
    @Autowired
	private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 查询高级查询配置
     * 
     * @param id 高级查询配置ID
     * @return 高级查询配置
     */
    @Override
    public SysAdvanceQueryConfig selectSysAdvanceQueryConfigById(Long id) {
        return sysAdvanceQueryConfigMapper.selectSysAdvanceQueryConfigById(id);
    }
    
    @Override
    public List<Map> selectDetailByRouteId(String routeId) {
        return sysAdvanceQueryConfigMapper.selectDetailByRouteId(routeId);
    }

    /**
     * 查询高级查询配置列表
     * 
     * @param sysAdvanceQueryConfig 高级查询配置
     * @return 高级查询配置
     */
    @Override
    public List<SysAdvanceQueryConfig> selectSysAdvanceQueryConfigList(SysAdvanceQueryConfig sysAdvanceQueryConfig) {
        return sysAdvanceQueryConfigMapper.selectSysAdvanceQueryConfigList(sysAdvanceQueryConfig);
    }

    /**
     * 新增高级查询配置
     * 
     * @param sysAdvanceQueryConfig 高级查询配置
     * @return 结果
     */
    @Transactional
    @Override
    public int insertSysAdvanceQueryConfig(SysAdvanceQueryConfig sysAdvanceQueryConfig) {
        sysAdvanceQueryConfig.setCreateTime(DateUtils.getNowDate());
        int rows = sysAdvanceQueryConfigMapper.insertSysAdvanceQueryConfig(sysAdvanceQueryConfig);
        insertSysAdvanceQueryConfigDetail(sysAdvanceQueryConfig);
        return rows;
    }

    /**
     * 修改高级查询配置
     * 
     * @param sysAdvanceQueryConfig 高级查询配置
     * @return 结果
     */
    @Transactional
    @Override
    public int updateSysAdvanceQueryConfig(SysAdvanceQueryConfig sysAdvanceQueryConfig) {
        sysAdvanceQueryConfig.setUpdateTime(DateUtils.getNowDate());
        sysAdvanceQueryConfigMapper.deleteSysAdvanceQueryConfigDetailByConfigId(sysAdvanceQueryConfig.getId());
        insertSysAdvanceQueryConfigDetail(sysAdvanceQueryConfig);
        return sysAdvanceQueryConfigMapper.updateSysAdvanceQueryConfig(sysAdvanceQueryConfig);
    }
    
    @Transactional
    @Override
    public int saveDetail(SysAdvanceQueryConfig sysAdvanceQueryConfig) {
        sysAdvanceQueryConfig.setUpdateTime(DateUtils.getNowDate());
        sysAdvanceQueryConfigMapper.deleteSysAdvanceQueryConfigDetailByConfigId(sysAdvanceQueryConfig.getId());
        insertSysAdvanceQueryConfigDetail(sysAdvanceQueryConfig);
        return 1;
    }

    /**
     * 批量删除高级查询配置
     * 
     * @param ids 需要删除的高级查询配置ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteSysAdvanceQueryConfigByIds(Long[] ids) {
        sysAdvanceQueryConfigMapper.deleteSysAdvanceQueryConfigDetailByConfigIds(ids);
        return sysAdvanceQueryConfigMapper.deleteSysAdvanceQueryConfigByIds(ids);
    }

    /**
     * 删除高级查询配置信息
     * 
     * @param id 高级查询配置ID
     * @return 结果
     */
    @Override
    public int deleteSysAdvanceQueryConfigById(Long id) {
        sysAdvanceQueryConfigMapper.deleteSysAdvanceQueryConfigDetailByConfigId(id);
        return sysAdvanceQueryConfigMapper.deleteSysAdvanceQueryConfigById(id);
    }

    /**
     * 新增${subTable.functionName}信息
     * 
     * @param sysAdvanceQueryConfig 高级查询配置对象
     */
    public void insertSysAdvanceQueryConfigDetail(SysAdvanceQueryConfig sysAdvanceQueryConfig) {
        List<SysAdvanceQueryConfigDetail> sysAdvanceQueryConfigDetailList = sysAdvanceQueryConfig.getSysAdvanceQueryConfigDetailList();
        Long id = sysAdvanceQueryConfig.getId();
        if (StringUtils.isNotNull(sysAdvanceQueryConfigDetailList))
        {
            List<SysAdvanceQueryConfigDetail> list = new ArrayList<SysAdvanceQueryConfigDetail>();
            for (SysAdvanceQueryConfigDetail sysAdvanceQueryConfigDetail : sysAdvanceQueryConfigDetailList)
            {
                sysAdvanceQueryConfigDetail.setConfigId(id);
                list.add(sysAdvanceQueryConfigDetail);
            }
            if (list.size() > 0)
            {
                sysAdvanceQueryConfigMapper.batchSysAdvanceQueryConfigDetail(list);
            }
        }
    }
    
    public List<Map> selectMenuPath() {
    	return sysAdvanceQueryConfigMapper.selectMenuPath();
    }
    
    public boolean checkSqlIdExists(String sqlId) {
    	return sqlSessionTemplate.getConfiguration().hasStatement(sqlId);
    }
}
