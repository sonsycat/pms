package com.zjry.pms.pmsFormDesign.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.pmsFormDesign.domain.PmsFormDesign;
import com.zjry.pms.pmsFormDesign.domain.PmsFormDesignField;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自定义表单Mapper接口
 * 
 * @author yangtao
 * @date 2021-07-27
 */
public interface PmsFormDesignMapper extends BaseMapper<PmsFormDesign> {
    /**
     * 查询自定义表单
     * 
     * @param id 自定义表单ID
     * @return 自定义表单
     */
    public PmsFormDesign selectFormDesignById(String id);

    /**
     * 查询自定义表单列表
     * 
     * @param formDesign 自定义表单
     * @return 自定义表单集合
     */
    public List<PmsFormDesign> selectFormDesignList(PmsFormDesign formDesign);

    /**
     * 新增自定义表单
     * 
     * @param formDesign 自定义表单
     * @return 结果
     */
    public int insertFormDesign(PmsFormDesign formDesign);

    /**
     * 修改自定义表单
     * 
     * @param formDesign 自定义表单
     * @return 结果
     */
    public int updateFormDesign(PmsFormDesign formDesign);

    public int updateFormJson(PmsFormDesign formDesign);
    
    public int updateFormData(PmsFormDesign formDesign);
    
    /**
     * 删除自定义表单
     * 
     * @param id 自定义表单ID
     * @return 结果
     */
    public int deleteFormDesignById(String id);

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
                            @Param("configs") List<PmsFormDesignField> configs);
	
	public void dropTable(@Param("tableName") String tableName);
	
	public void addColumn(@Param("tableName") String tableName,
                          @Param("columnName") String columnName, @Param("dbType") String dbType);
	
	public void modifyColumn(@Param("tableName") String tableName,
                             @Param("columnName") String columnName, @Param("dbType") String dbType);
	
}
