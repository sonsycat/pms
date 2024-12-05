package com.zjry.formDesign.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.formDesign.domain.FormDesign;
import com.zjry.formDesign.domain.FormDesignField;
import com.zjry.pms.basic.domain.AssetsBasicPublicVo;

/**
 * 自定义表单Service接口
 * 
 * @author yangtao
 * @date 2021-09-29
 */
public interface IFormDesignFieldService extends IService<FormDesignField> {

	public List<FormDesignField> findByDesignId(String formDesignId);
	
	public void save(List<FormDesignField> fdcList);

	public int updateFormDesignField(FormDesignField formDesignField);

	public List<AssetsBasicPublicVo> selectDictType(String dictLabel);

	void updateAll(FormDesign formDesign);
}
