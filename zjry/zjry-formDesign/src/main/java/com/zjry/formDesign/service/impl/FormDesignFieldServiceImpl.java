package com.zjry.formDesign.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.formDesign.domain.FormDesignField;
import com.zjry.formDesign.mapper.FormDesignFieldMapper;
import com.zjry.formDesign.service.IFormDesignFieldService;

/**
 * 自定义表单Service业务层处理
 * 
 * @author yangtao
 * @date 2021-07-27
 */
@Service
public class FormDesignFieldServiceImpl extends ServiceImpl<FormDesignFieldMapper, FormDesignField> implements IFormDesignFieldService {
    @Autowired
    private FormDesignFieldMapper formDesignFieldMapper;

    public List<FormDesignField> findByDesignId(String formDesignId) {
		return formDesignFieldMapper.findByDesignId(formDesignId);
	}
	
    @Transactional
	public void save(List<FormDesignField> fdcList) {
		if(fdcList != null && fdcList.size() > 0) {
			String formDesignId = fdcList.get(0).getFormDesignId();
			formDesignFieldMapper.delByDesignId(formDesignId);
			
			formDesignFieldMapper.insertBatch(fdcList);
		}
	}
}
