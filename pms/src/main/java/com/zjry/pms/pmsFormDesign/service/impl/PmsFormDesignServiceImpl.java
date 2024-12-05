package com.zjry.pms.pmsFormDesign.service.impl;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.core.domain.entity.SysMenu;
import com.zjry.common.utils.DateUtils;
import com.zjry.common.utils.StringUtils;
import com.zjry.formDesign.vo.*;
import com.zjry.pms.common.utils.JsonMapper;
import com.zjry.pms.encode.service.IHrEncodeRuleService;
import com.zjry.pms.pmsFormDesign.domain.PmsFormDesign;
import com.zjry.pms.pmsFormDesign.domain.PmsFormDesignField;
import com.zjry.pms.pmsFormDesign.mapper.PmsFormDesignFieldMapper;
import com.zjry.pms.pmsFormDesign.mapper.PmsFormDesignMapper;
import com.zjry.pms.pmsFormDesign.service.IFormDesignService;
import com.zjry.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 自定义表单Service业务层处理
 * 
 * @author yangtao
 * @date 2021-07-27
 */
@Service
public class PmsFormDesignServiceImpl extends ServiceImpl<PmsFormDesignMapper, PmsFormDesign> implements IFormDesignService {
	@Autowired
    private PmsFormDesignMapper formDesignMapper;
	@Autowired
    private PmsFormDesignFieldMapper formDesignFieldMapper;
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
    public PmsFormDesign selectFormDesignById(String id) {
        return formDesignMapper.selectFormDesignById(id);
    }

    /**
     * 查询自定义表单列表
     * 
     * @param formDesign 自定义表单
     * @return 自定义表单
     */
    @Override
    public List<PmsFormDesign> selectFormDesignList(PmsFormDesign formDesign) {
        return formDesignMapper.selectFormDesignList(formDesign);
    }

    /**
     * 新增自定义表单
     * 
     * @param formDesign 自定义表单
     * @return 结果
     */
    @Override
    public int insertFormDesign(PmsFormDesign formDesign) {
    	formDesign.setId(UUID.randomUUID().toString().replaceAll("-", ""));
    	formDesign.setDelFlag("0");
    	formDesign.setUpdateDate(DateUtils.getNowDate());
		formDesign.setFormCode(hrEncodeRuleService.generateEncode("pms_form_design", "FORM_CODE").replace("-",""));
    	formDesign.setFormUrl("/subsidy/formProcess?formDesignId="+formDesign.getId());
        return formDesignMapper.insertFormDesign(formDesign);
    }

    /**
     * 修改自定义表单
     * 
     * @param formDesign 自定义表单
     * @return 结果
     */
    @Override
    public int updateFormDesign(PmsFormDesign formDesign) {
        return formDesignMapper.updateFormDesign(formDesign);
    }
    
    @Override
    @Transactional
	public int updateFormJson(FormDesignVo fd) {
		// 保存表单json信息
		PmsFormDesign formDesign = formDesignMapper.selectFormDesignById(fd.getFdId());
		if(StringUtils.isNull(formDesign.getRemarks())){
			SysMenu newSysMenu = new SysMenu();
			newSysMenu.setMenuName(formDesign.getFormName());
			newSysMenu.setParentId(Long.parseLong("3753"));
			newSysMenu.setParentName("");
			newSysMenu.setPath(formDesign.getId());
			newSysMenu.setComponent("subsidy/formProcess/index");
			newSysMenu.setIsFrame("1");
			newSysMenu.setIsCache("0");
			newSysMenu.setMenuType("C");
			newSysMenu.setVisible("0");
			newSysMenu.setStatus("0");
			newSysMenu.setIcon("502");
			newSysMenu.setDelFlag("0");
//			newSysMenu.setOrderNum(formDesign.getSortNo());
			newSysMenu.preInsert();
			Map<String,String> map = new HashMap<>();
			map.put("formDesignId", formDesign.getId());
			map.put("formName", formDesign.getFormName());
			String json = JsonMapper.getInstance().toJson(map);
			newSysMenu.setQuery(json);
			menuService.insertMenu(newSysMenu);
			//回写MenuId
			PmsFormDesign formDesignTemp = new PmsFormDesign();
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
		PmsFormDesign formDesign = new PmsFormDesign();
    	formDesign.setId(formDesignId);
    	formDesign.setFormData(formData);
    	formDesignMapper.updateFormData(formDesign);
    	
    	return 1;
	}
    
    private void createFields(PmsFormDesign formDesign) {
    	String formDesignId = formDesign.getId();
    	FormJson formJsonObj = JSON.parseObject(formDesign.getFormJson(), FormJson.class);
    	List<FieldConfig> list = formJsonObj.getList();
    	List<PmsFormDesignField> fdcList = createFieldList(formDesignId, list);
    	
    	formDesignFieldMapper.delByDesignId(formDesignId);

    	formDesignFieldMapper.insertBatch(fdcList);
    }
    
    private List<PmsFormDesignField> createFieldList(String formDesignId, List<FieldConfig> list) {
    	List<PmsFormDesignField> fdcList = new ArrayList<>();
    	if(list == null || list.size() == 0) {
    		return null;
    	}
    	Integer sortNo = 0;
    	for(FieldConfig fc : list) {
    		List<Column> columns = fc.getColumns();
    		if(columns != null && columns.size() > 0) {
    			for(Column column : columns) {
    				for(FieldConfig fcc : column.getList()) {
    					PmsFormDesignField fdc = createFDF(formDesignId, fcc, ++sortNo);
						fdc.setFieldDesc(fc.getName());
			    		fdcList.add(fdc);
    				}
    			}
    		} else {
    			PmsFormDesignField fdc = createFDF(formDesignId, fc, ++sortNo);
				fdc.setFieldDesc(fc.getName());
    			fdcList.add(fdc);
    		}
    	}
    	return fdcList;
    }
    
    /**
     * 构造字段及配置信息
     * @param formDesignId
     * @param fc
     * @param sortNo
     * @return
     */
    private PmsFormDesignField createFDF(String formDesignId, FieldConfig fc, Integer sortNo) {
    	// 先检查是否已经配置过,如果配置过，则直接返回
    	PmsFormDesignField fdf = formDesignFieldMapper.selectOne(formDesignId, fc.getModel());
    	if(fdf != null) {
    		return fdf;
    	}
		PmsFormDesignField fdc = new PmsFormDesignField();
		fdc.setFormDesignId(formDesignId);
		fdc.setIsQuery("1");
		fdc.setIsList("1");
		fdc.setIsEdit("1");
		fdc.setIsNotNull("0");
		fdc.setFieldLength(200);
		fdc.setFieldName(fc.getModel());
		fdc.setFieldDesc(fc.getName());
		fdc.setSortNo(sortNo);
		fdc.setFieldType(fc.getType());
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
	private void createTable(PmsFormDesign formDesign) {
		List<PmsFormDesignField> configs = formDesignFieldMapper.findByDesignId(formDesign.getId());
		for(PmsFormDesignField config : configs) {
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
			for(PmsFormDesignField config : configs) {
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
    
    @Override
	public boolean checkFormCodeUnique(String formCode) {
		return formDesignMapper.checkFormCodeUnique(formCode) > 0;
	}
}
