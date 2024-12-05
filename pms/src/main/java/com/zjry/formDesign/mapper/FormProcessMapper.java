package com.zjry.formDesign.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.formDesign.domain.FormDesign;
import com.zjry.formDesign.domain.FormDesignField;

/**
 * 自定义表单Mapper接口
 * 
 * @author yangtao
 * @date 2021-07-27
 */
public interface FormProcessMapper extends BaseMapper<Map> {
	
	public Map<String, Object> findById(@Param("tableName") String tableName, @Param("id") String id);
	
    /**
     * 查询自定义表单列表
     * 
     * @param formDesign 自定义表单
     * @return 自定义表单集合
     */
    public List<Map<String, Object>> findList(@Param("tableName") String tableName, @Param("params") Map<String, Object> params, @Param("orders") String orders);

    public List<Map<String, Object>>  selectForDetails(@Param("tableName") String tableName, @Param("deptCode") String deptCode, @Param("qDate") Date qDate);
    /**
     * 新增自定义表单
     * 
     * @param formDesign 自定义表单
     * @return 结果
     */
    public int insert(@Param("tableName") String tableName, @Param("tableData") Map<String, Object> params);

    /**
     * 修改自定义表单
     * 
     * @param formDesign 自定义表单
     * @return 结果
     */
    public int update(@Param("tableName") String tableName, 
    		@Param("tableData") Map<String, Object> params,
    		@Param("id") String id);

    public int updateFormJson(FormDesign formDesign);
    
    public int updateFormData(FormDesign formDesign);
    
    /**
     * 删除自定义表单
     * 
     * @param id 自定义表单ID
     * @return 结果
     */
    public int deleteById(@Param("tableName") String tableName, @Param("id") String id);

    /**
     * 批量删除自定义表单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFormDesignByIds(String[] ids);
    
    public Integer checkFormCodeUnique(String formCode);
	
	public Integer checkTableExists(String tableName);
	
	public Integer checkColumnExists(@Param("tableName") String tableName, @Param("columnName") String columnName);

	public void createTable(@Param("tableName") String tableName, 
			@Param("configs") List<FormDesignField> configs);
	
	public void dropTable(@Param("tableName") String tableName);
	
	public void addColumn(@Param("tableName") String tableName, 
			@Param("columnName") String columnName, @Param("dbType") String dbType);
	
	public void modifyColumn(@Param("tableName") String tableName, 
			@Param("columnName") String columnName, @Param("dbType") String dbType);
	
}
