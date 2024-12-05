/**
 * Copyright &copy; 2008-2019 <a href="http://www.sxzjrj.com">ZhiJie</a> All rights reserved.
 */
package com.zjry.formDesign.mapper;

import java.util.List;

import com.zjry.pms.basic.domain.AssetsBasicPublicVo;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.formDesign.domain.FormDesignField;

/**
 * 自定义表单配置DAO接口
 * @author Yangtao
 */
public interface FormDesignFieldMapper extends BaseMapper<FormDesignField> {

	public List<FormDesignField> findByDesignId(String formDesignId);
	
	public void delByDesignId(String formDesignId);
	
	public FormDesignField selectOne(@Param("formDesignId") String formDesignId, @Param("fieldName") String fieldName);
	
	public void insertBatch(List<FormDesignField> list);

    int updateFormDesignField(FormDesignField formDesignField);

    public List<AssetsBasicPublicVo> selectDictType(String dictLabel);
}