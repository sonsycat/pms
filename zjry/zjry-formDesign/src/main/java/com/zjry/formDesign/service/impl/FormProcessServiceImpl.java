package com.zjry.formDesign.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.common.utils.DateUtils;
import com.zjry.formDesign.domain.FormDesign;
import com.zjry.formDesign.domain.FormDesignField;
import com.zjry.formDesign.mapper.FormDesignFieldMapper;
import com.zjry.formDesign.mapper.FormDesignMapper;
import com.zjry.formDesign.mapper.FormProcessMapper;
import com.zjry.formDesign.service.IFormProcessService;
import com.zjry.formDesign.vo.Column;
import com.zjry.formDesign.vo.FieldConfig;
import com.zjry.formDesign.vo.FormDesignVo;
import com.zjry.formDesign.vo.FormJson;

import cn.hutool.core.lang.UUID;

/**
 * 自定义表单Service业务层处理
 * 
 * @author yangtao
 * @date 2021-07-27
 */
@Service
public class FormProcessServiceImpl extends ServiceImpl<FormProcessMapper, Map> implements IFormProcessService {
    @Autowired
    private FormDesignMapper formDesignMapper;
    @Autowired
    private FormDesignFieldMapper formDesignFieldMapper;
    @Autowired
    private FormProcessMapper formProcessMapper;

    /**
     * 查询自定义表单
     * 
     * @param id 自定义表单ID
     * @return 自定义表单
     */
    @Override
    public Map<String, Object> findById(String formDesignId, String id) {
        return formProcessMapper.findById(this.findTableName(formDesignId), id);
    }
    
    @Override
    public Map<String, Object> findFormData(String formDesignId, String id) {
    	Map<String, Object> data = this.findById(formDesignId, id);
        List<FormDesignField> fieldList = formDesignFieldMapper.findByDesignId(formDesignId);
        Map<String, Object> formData = new HashMap<String, Object>();
        for(FormDesignField field : fieldList) {
        	field.setFieldValue(data.get(field.getFieldName()));
        }
        formData.put("id", data.get("id"));
        formData.put("datas", fieldList);
        return formData;
    }

    /**
     * 查询自定义表单列表
     * 
     * @param formDesign 自定义表单
     * @return 自定义表单
     */
    @Override
    public List<Map<String, Object>> selectList(String formDesignId, Map<String, Object> param) {
        return formProcessMapper.findList(this.findTableName(formDesignId), this.getQueryParam(formDesignId, param));
    }
    
    private String findTableName(String formDesignId) {
    	FormDesign formDesign = formDesignMapper.selectFormDesignById(formDesignId);
        String tableName = formDesign.getGenTableName();
        return tableName;
    }
    
    private Map<String, Object> getQueryParam(String formDesignId, Map<String, Object> param) {
    	Map<String, Object> queryParam = new HashMap<>();
    	for(Object key : param.keySet()) {
    		// 多选框处理
    		if(param.get(key) != null && param.get(key) instanceof List) {
    			List<String> list = (List<String>) param.get(key);
    			Collections.sort(list);
    			param.put(key.toString(), String.join(",", list));
    		}
			if(param.get(key) != null && !"".equals(param.get(key).toString())) {
				// 查出字段的queryType，拼入key
				FormDesignField fdf = formDesignFieldMapper.selectOne(formDesignId, key.toString().toLowerCase());
				queryParam.put(key+"~"+fdf.getQueryType(), param.get(key));
			}
		}
//		newParams.put("CREATE_BY~eq", UserUtils.getUser().getId());
    	return queryParam;
    }

    /**
     * 新增自定义表单
     * 
     * @param formDesign 自定义表单
     * @return 结果
     */
    @Override
    public int insert(String formDesignId, Map<String, Object> params) {
    	FormDesign formDesign = formDesignMapper.selectFormDesignById(formDesignId);
        String tableName = formDesign.getGenTableName();
    	return formProcessMapper.insert(tableName, convertParams(params, "insert"));
    }
    
    private Map<String, Object> convertParams(Map<String, Object> params, String opType) {
    	for(String key : params.keySet()) {
    		// 多选框处理
    		if(params.get(key) != null && params.get(key) instanceof List) {
    			List<String> list = (List<String>) params.get(key);
    			Collections.sort(list);
    			params.put(key, String.join(",", list));
    		}
    	}
    	if("insert".equals(opType)) {
    		params.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
    		params.put("del_flag", BaseEntity.DEL_FLAG_NORMAL);
    		params.put("create_date", DateUtils.getTime());
    	}
    	params.put("update_date", DateUtils.getTime());
    	return params;
    }

    /**
     * 修改自定义表单
     * 
     * @param formDesign 自定义表单
     * @return 结果
     */
    @Override
    public int update(String formDesignId, Map<String, Object> params, String id) {
        return formProcessMapper.update(this.findTableName(formDesignId), convertParams(params, "update"), id);
    }
    
    @Override
    @Transactional
	public int updateFormJson(FormDesignVo fd) {
		// 保存表单json信息
    	FormDesign formDesign = formDesignMapper.selectFormDesignById(fd.getFdId());
    	formDesign.setFormJson(JSONObject.toJSONString(fd.getFormJson()));
    	formDesign.setFormHtml(fd.getFormHtml());
    	formDesign.setUpdateDate(new Date());
    	formDesignMapper.updateFormJson(formDesign);
    	
    	// 保存表单字段信息
    	createFields(formDesign);
    	
    	// 建表
    	createTable(formDesign);
    	
    	return 1;
	}
    
    @Override
    @Transactional
	public int updateFormData(String formDesignId, String formData) {
    	FormDesign formDesign = new FormDesign();
    	formDesign.setId(formDesignId);
    	formDesign.setFormData(formData);
    	formDesignMapper.updateFormData(formDesign);
    	
    	return 1;
	}
    
    private void createFields(FormDesign formDesign) {
    	String formDesignId = formDesign.getId();
    	FormJson formJsonObj = JSON.parseObject(formDesign.getFormJson(), FormJson.class);
    	List<FieldConfig> list = formJsonObj.getList();
    	List<FormDesignField> fdcList = createFieldList(formDesignId, list);
    	
    	formDesignFieldMapper.delByDesignId(formDesignId);
    	formDesignFieldMapper.insertBatch(fdcList);
    }
    
    private List<FormDesignField> createFieldList(String formDesignId, List<FieldConfig> list) {
    	List<FormDesignField> fdcList = new ArrayList<>();
    	if(list == null || list.size() == 0) {
    		return null;
    	}
    	Integer sortNo = 0;
    	for(FieldConfig fc : list) {
    		List<Column> columns = fc.getColumns();
    		if(columns != null && columns.size() > 0) {
    			for(Column column : columns) {
    				for(FieldConfig fcc : column.getList()) {
    					FormDesignField fdc = createFDF(formDesignId, fcc, ++sortNo);
			    		fdcList.add(fdc);
    				}
    			}
    		} else {
    			FormDesignField fdc = createFDF(formDesignId, fc, ++sortNo);
    			fdcList.add(fdc);
    		}
    	}
    	return fdcList;
    }
    
    private FormDesignField createFDF(String formDesignId, FieldConfig fc, Integer sortNo) {
    	FormDesignField fdc = new FormDesignField();
		fdc.setFormDesignId(formDesignId);
		fdc.setIsQuery("1");
		fdc.setIsList("1");
		fdc.setIsEdit("1");
		fdc.setIsNotNull("0");
		fdc.setFieldLength(200);
		fdc.setFieldName(fc.getModel());
		fdc.setFieldDesc(fc.getName());
		fdc.setSortNo(sortNo);
		if("input".equals(fc.getType()) || "textarea".equals(fc.getType())) {
			fdc.setQueryType("like");
		} else {
			fdc.setQueryType("=");
		}
		
		if("date".equals(fc.getType()) || "time".equals(fc.getType())) {
			fdc.setDbType("datetime");
		} else {
			fdc.setDbType("varchar");
		}
		fdc.setId(UUID.randomUUID().toString());
		
		return fdc;
    }
    
    /**
	 * 生成数据库
	 * @param formDesign
	 */
	private void createTable(FormDesign formDesign) {
		List<FormDesignField> configs = formDesignFieldMapper.findByDesignId(formDesign.getId());
		for(FormDesignField config : configs) {
			config.setNullVal("1".equals(config.getIsNotNull()) ? "NOT NULL" : "");
			String validType = config.getValidType();
			if("number".equals(validType) || "digits".equals(validType)) {
				config.setDbType("number(10,2)");
			} else {
				config.setDbType("varchar("+config.getFieldLength()+")");
			}
		}
		
		String genTableName = "DYNAMIC_".concat(formDesign.getFormCode());
		// 判断表是否存在
		Integer tableExists = formDesignMapper.checkTableExists(genTableName.toUpperCase());
		if(tableExists == 0) {
			// 表不存在
			formDesignMapper.createTable(genTableName, configs);
		} else {
			// 表已存在，则更新列
			for(FormDesignField config : configs) {
				Integer columnExists = formDesignMapper.checkColumnExists(genTableName.toUpperCase(), config.getFieldName());
				if(columnExists == 0) {
					formDesignMapper.addColumn(genTableName, config.getFieldName(), config.getDbType());
				} else {
					formDesignMapper.modifyColumn(genTableName, config.getFieldName(), config.getDbType());
				}
			}
		}
		// 更新表名
		formDesign.setGenTableName(genTableName);
		formDesign.setUpdateDate(new Date());
		formDesignMapper.updateFormDesign(formDesign);
	}

    /**
     * 批量删除自定义表单
     * 
     * @param ids 需要删除的自定义表单ID
     * @return 结果
     */
    @Override
    public int deleteById(String formDesignId, String id) {
    	FormDesign formDesign = formDesignMapper.selectFormDesignById(formDesignId);
        String tableName = formDesign.getGenTableName();
        return formProcessMapper.deleteById(tableName, id);
    }

    public boolean checkFormCodeUnique(String formCode) {
		return formDesignMapper.checkFormCodeUnique(formCode) > 0;
	}
}