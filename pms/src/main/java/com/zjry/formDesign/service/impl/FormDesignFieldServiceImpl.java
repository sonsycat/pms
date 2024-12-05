package com.zjry.formDesign.service.impl;

import java.util.List;

import com.zjry.common.core.domain.entity.SysMenu;
import com.zjry.formDesign.domain.FormDesign;
import com.zjry.formDesign.mapper.FormDesignMapper;
import com.zjry.formDesign.util.ObjUtil;
import com.zjry.pms.basic.domain.AssetsBasicPublicVo;
import com.zjry.pms.common.Constants;
import com.zjry.system.mapper.SysMenuMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.formDesign.domain.FormDesignField;
import com.zjry.formDesign.mapper.FormDesignFieldMapper;
import com.zjry.formDesign.service.IFormDesignFieldService;

import javax.annotation.Resource;

/**
 * 自定义表单Service业务层处理
 * 
 * @author yangtao
 * @date 2021-07-27
 */
@Service
public class FormDesignFieldServiceImpl extends ServiceImpl<FormDesignFieldMapper, FormDesignField> implements IFormDesignFieldService {
    @Resource
    private FormDesignFieldMapper formDesignFieldMapper;
	@Resource
	private FormDesignMapper formDesignMapper;
	@Resource
	private SysMenuMapper sysMenuMapper;

    @Override
	public List<FormDesignField> findByDesignId(String formDesignId) {
		return formDesignFieldMapper.findByDesignId(formDesignId);
	}
	
    @Override
	@Transactional
	public void save(List<FormDesignField> fdcList) {
		if(fdcList != null && fdcList.size() > 0) {
			String formDesignId = fdcList.get(0).getFormDesignId();
			formDesignFieldMapper.delByDesignId(formDesignId);
			
			formDesignFieldMapper.insertBatch(fdcList);
		}
	}

	@Override
	public int updateFormDesignField(FormDesignField formDesignField) {
		 if(Constants.PMS_ZDLX_ONE.equals(formDesignField.getPmsType())|| Constants.PMS_ZDLX_SIX.equals(formDesignField.getPmsType()) || Constants.PMS_ZDLX_EIGHT.equals(formDesignField.getPmsType()) || Constants.PMS_ZDLX_NINE.equals(formDesignField.getPmsType())  ){
			 formDesignField.setDbType("varchar");
             if(Constants.PMS_ZDLX_ONE.equals(formDesignField.getPmsType()) && formDesignField.getTextLines()!=null && Integer.parseInt(formDesignField.getTextLines())>1){
				 formDesignField.setFieldType("textarea");
			 }else{
				 formDesignField.setFieldType("input");
			 }

		 }else if(Constants.PMS_ZDLX_TWO.equals(formDesignField.getPmsType())){
			 formDesignField.setDbType("varchar");
			 formDesignField.setFieldType("number");
		 } else if(Constants.PMS_ZDLX_THREE.equals(formDesignField.getPmsType())){
			 formDesignField.setDbType("varchar");
			 if(Constants.PMS_ZDLX_ONE.equals(formDesignField.getOptions()) ){
				 formDesignField.setFieldType("select");
			 }else if(Constants.PMS_ZDLX_TWO.equals(formDesignField.getOptions())){
				 formDesignField.setFieldType("radio");
			 }else if(Constants.PMS_ZDLX_THREE.equals(formDesignField.getOptions())){
				 formDesignField.setFieldType("checkbox");
			 }
		 }else if(Constants.PMS_ZDLX_FOUR.equals(formDesignField.getPmsType())){
			 formDesignField.setDbType("datetime");
			 formDesignField.setFieldType("date");
		 }else if(Constants.PMS_ZDLX_FIVE.equals(formDesignField.getPmsType()) ||Constants.PMS_ZDLX_SEVEN.equals(formDesignField.getPmsType()) ){
			 formDesignField.setDbType("varchar");
			 formDesignField.setFieldType("select");
		 }

		return formDesignFieldMapper.updateFormDesignField(formDesignField);
	}

	@Override
	public List<AssetsBasicPublicVo> selectDictType(String dictLabel) {
		return formDesignFieldMapper.selectDictType(dictLabel);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateAll(FormDesign formDesign) {
		formDesignMapper.updateFormDesign(formDesign);
		List<FormDesignField> list = formDesign.getFdcList();
		if(StringUtils.isNotBlank(formDesign.getRemarks()) ){
			if(null != ObjUtil.tryParseLong(formDesign.getRemarks())){
				SysMenu menu = new SysMenu();
				menu.setMenuId(Long.parseLong(formDesign.getRemarks()));
				menu.setOrderNum(formDesign.getSortNo());
				sysMenuMapper.updateMenu(menu);
			}
		}
		for(FormDesignField fdf : list){
			if(Constants.IS_CHANGE_YES.equals(fdf.getIsQuery()) ){
				fdf.setIsListQuery(Constants.IS_CHANGE_YES);
			}
			formDesignFieldMapper.updateFormDesignField(fdf);
		}

	}

}
