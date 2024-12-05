package com.zjry.pms.pmsFormDesign.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.pmsFormDesign.domain.PmsFormDesignField;

import java.util.List;

/**
 * 自定义表单Service接口
 * 
 * @author yangtao
 * @date 2021-09-29
 */
public interface IFormDesignFieldService extends IService<PmsFormDesignField> {

	public List<PmsFormDesignField> findByDesignId(String formDesignId);
	
	public void save(List<PmsFormDesignField> fdcList);
}
