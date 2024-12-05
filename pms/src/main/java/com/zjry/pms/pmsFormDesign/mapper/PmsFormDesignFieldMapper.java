/**
 * Copyright &copy; 2008-2019 <a href="http://www.sxzjrj.com">ZhiJie</a> All rights reserved.
 */
package com.zjry.pms.pmsFormDesign.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.pmsFormDesign.domain.PmsFormDesignField;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自定义表单配置DAO接口
 * @author Yangtao
 */
public interface PmsFormDesignFieldMapper extends BaseMapper<PmsFormDesignField> {

	public List<PmsFormDesignField> findByDesignId(String formDesignId);
	
	public void delByDesignId(String formDesignId);
	
	public PmsFormDesignField selectOne(@Param("formDesignId") String formDesignId, @Param("fieldName") String fieldName);
	
	public void insertBatch(List<PmsFormDesignField> list);
}