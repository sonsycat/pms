package com.zjry.formDesign.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zjry.formDesign.service.IFormDesignFieldService;
import com.zjry.pms.encode.service.IHrEncodeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.entity.SysMenu;
import com.zjry.common.utils.DateUtils;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.common.utils.StringUtils;
import com.zjry.formDesign.domain.FormDesign;
import com.zjry.formDesign.domain.FormDesignField;
import com.zjry.formDesign.mapper.FormDesignFieldMapper;
import com.zjry.formDesign.mapper.FormDesignMapper;
import com.zjry.formDesign.service.IFormDesignService;
import com.zjry.formDesign.vo.Column;
import com.zjry.formDesign.vo.FieldConfig;
import com.zjry.formDesign.vo.FormDesignVo;
import com.zjry.formDesign.vo.FormJson;
import com.zjry.formDesign.vo.OptionsObj;
import com.zjry.pms.common.utils.JsonMapper;
import com.zjry.system.service.ISysMenuService;

import cn.hutool.core.lang.UUID;

import javax.annotation.Resource;

/**
 * 自定义表单Service业务层处理
 * 
 * @author yangtao
 * @date 2021-07-27
 */
@Service
public class FormDesignServiceImpl extends ServiceImpl<FormDesignMapper, FormDesign> implements IFormDesignService {
    @Resource
    private FormDesignMapper formDesignMapper;
    @Resource
    private FormDesignFieldMapper formDesignFieldMapper;
    @Autowired
	private ISysMenuService menuService;
	@Autowired
	private IHrEncodeRuleService hrEncodeRuleService;


    /**
     * 查询自定义表单
     * 
     * @param id 自定义表单ID
     * @return 自定义表单
     */
    @Override
    public FormDesign selectFormDesignById(String id) {
        return formDesignMapper.selectFormDesignById(id);
    }

    /**
     * 查询自定义表单列表
     * 
     * @param formDesign 自定义表单
     * @return 自定义表单
     */
    @Override
    public List<FormDesign> selectFormDesignList(FormDesign formDesign) {
        return formDesignMapper.selectFormDesignList(formDesign);
    }

    /**
     * 新增自定义表单
     * 
     * @param formDesign 自定义表单
     * @return 结果
     */
    @Override
    public int insertFormDesign(FormDesign formDesign) {
		String str = UUID.randomUUID().toString().replaceAll("-", "");
    	formDesign.setId(str);
    	formDesign.setDelFlag("0");
    	formDesign.setUpdateDate(DateUtils.getNowDate());
		formDesign.setFormCode(hrEncodeRuleService.generateEncode("form_design", "FORM_CODE").replace("-",""));
		formDesign.setFormUrl("/tool/formProcess?formDesignId="+formDesign.getId());
		formDesign.setOriginalCode(formDesign.getOriginalCode());
//		if(StringUtils.isNotEmpty(formDesign.getOriginalCode())){
//			List<FormDesignField> list = formDesignFieldMapper.findByDesignId(formDesign.getOriginalCode());
//			if(list.size()>0){
//				for(FormDesignField item : list){
//					item.setId(UUID.randomUUID().toString());
//					item.setFormDesignId(str);
//				}
//				formDesignFieldMapper.insertBatch(list);
//			}
//
//		}
        return formDesignMapper.insertFormDesign(formDesign);
    }

    /**
     * 修改自定义表单
     * 
     * @param formDesign 自定义表单
     * @return 结果
     */
    @Override
    public int updateFormDesign(FormDesign formDesign) {
        return formDesignMapper.updateFormDesign(formDesign);
    }
    
    @Override
    @Transactional
	public int updateFormJson(FormDesignVo fd) {
    	// 保存表单json信息
    	FormDesign formDesign = formDesignMapper.selectFormDesignById(fd.getFdId());
    	if(StringUtils.isNull(formDesign.getRemarks())){
    		SysMenu newSysMenu = new SysMenu();
        	newSysMenu.setMenuName(formDesign.getFormName());
        	newSysMenu.setParentId(Long.parseLong("3609"));
        	newSysMenu.setParentName("");
        	newSysMenu.setPath(formDesign.getId());
        	newSysMenu.setComponent("tool/formProcess/index");
        	newSysMenu.setIsFrame("1");
        	newSysMenu.setIsCache("0");
        	newSysMenu.setMenuType("C");
        	newSysMenu.setVisible("0");
        	newSysMenu.setStatus("0");
        	newSysMenu.setIcon("404");
        	newSysMenu.setDelFlag("0");
        	newSysMenu.setOrderNum(formDesign.getSortNo());
        	newSysMenu.preInsert();
        	Map<String,String> map = new HashMap<>();
        	map.put("formDesignId", formDesign.getId());
        	map.put("formName", formDesign.getFormName());
        	String json = JsonMapper.getInstance().toJson(map);
        	newSysMenu.setQuery(json);
        	menuService.insertMenu(newSysMenu);
        	//回写MenuId
        	FormDesign formDesignTemp = new FormDesign();
        	formDesignTemp.setId(fd.getFdId());
        	formDesignTemp.setRemarks(newSysMenu.getMenuId()+"");
        	formDesignMapper.updateFormDesign(formDesignTemp);
    	}
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

	@Override
	public String selectForSortNo(Long compCode) {
		String no = formDesignMapper.selectForSortNo(compCode);
		if(StringUtils.isNotEmpty(no)){
		 return (Integer.parseInt(no)+1)+"";
		}else {
			return "1";
		}
	}

	private void createFields(FormDesign formDesign) {
    	String formDesignId = formDesign.getId();
    	FormJson formJsonObj = JSON.parseObject(formDesign.getFormJson(), FormJson.class);
    	List<FieldConfig> list = formJsonObj.getList();
    	List<FormDesignField> fdcList = createFieldList(formDesignId, list);
    	formDesignFieldMapper.delByDesignId(formDesignId);
		List<FormDesignField> allList = new ArrayList<FormDesignField>();
		HashMap<String,FormDesignField> hm = new HashMap<String,FormDesignField>();
		List<FormDesignField> originalList = formDesignFieldMapper.findByDesignId(formDesign.getOriginalCode());
		for(FormDesignField item : fdcList){
			hm.put(item.getFieldName(),item);
		}
		for(FormDesignField oitem : originalList){
			for (String key : hm.keySet()) {
				if(oitem.getFieldName().equals(key)){
					oitem.setId(UUID.randomUUID().toString());
					oitem.setFormDesignId(formDesign.getId());
					hm.put(key,oitem);
					break;
				}
			}
		}
		for (String key : hm.keySet()) {
			allList.add(hm.get(key));
		}


    	formDesignFieldMapper.insertBatch(allList);
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
    					FormDesignField fdc = createFDF(formDesignId, fcc, ++sortNo, column.getSpan());
			    		fdcList.add(fdc);
    				}
    			}
    		} else {
    			FormDesignField fdc = createFDF(formDesignId, fc, ++sortNo, "12");
    			fdcList.add(fdc);
    		}
    	}
		FormDesignField fdf = new FormDesignField();
		fdf.setFormDesignId(formDesignId);
		fdf.setIsQuery("1");
		fdf.setIsList("0");
		fdf.setIsEdit("0");
		fdf.setIsNotNull("0");
		fdf.setFieldLength(200);
		fdf.setFieldName("comp_code");
		fdf.setFieldDesc("单位");
		fdf.setSortNo(sortNo);
		fdf.setFieldType("input");
		fdf.setDbType("fdf");
		fdf.setId(UUID.randomUUID().toString());
		fdf.setQueryType("eq");
		fdcList.add(fdf);
    	return fdcList;
    }
    
    /**
     * 构造字段及配置信息
     * @param formDesignId
     * @param fc
     * @param sortNo
     * @return
     */
    private FormDesignField createFDF(String formDesignId, FieldConfig fc, Integer sortNo, String span) {
    	// 先检查是否已经配置过,如果配置过，则直接返回
    	FormDesignField fdf = formDesignFieldMapper.selectOne(formDesignId, fc.getModel());
    	if(fdf != null) {
			fdf.setFieldDesc(fc.getName());
    		return fdf;
    	}
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
		fdc.setSpan(Integer.parseInt(span));
		fdc.setFieldType(fc.getType());
		fdc.setDisplayType(fc.getOptions().getType());
		if("input".equals(fc.getType()) || "textarea".equals(fc.getType())) {
			fdc.setQueryType("like");
		} else if("date".equals(fc.getType())){
			fdc.setQueryType("between");
		}else{
			fdc.setQueryType("eq");
		}
		
		if("date".equals(fc.getType()) || "time".equals(fc.getType())) {
			fdc.setDbType("datetime");
		} else {
			fdc.setDbType("varchar");
		}
		fdc.setId(UUID.randomUUID().toString());
		
		if("radio".equals(fc.getType()) || "checkbox".equals(fc.getType())) {
			List<OptionsObj> options = fc.getOptions().getOptions();
			if(options != null && options.size() > 0) {
				fdc.setOptions(JSONObject.toJSONString(options));
			}
		}
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
    public int deleteFormDesignByIds(String[] ids) {
        return formDesignMapper.deleteFormDesignByIds(ids);
    }

    /**
     * 删除自定义表单信息
     * 
     * @param id 自定义表单ID
     * @return 结果
     */
    @Override
    public int deleteFormDesignById(String id) {
        return formDesignMapper.deleteFormDesignById(id);
    }
    
    public boolean checkFormCodeUnique(String formCode) {
		return formDesignMapper.checkFormCodeUnique(formCode) > 0;
	}
}
