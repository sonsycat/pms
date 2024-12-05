package com.zjry.pms.pmsFormDesign.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.pms.pmsFormDesign.domain.PmsFormDesignField;
import com.zjry.pms.pmsFormDesign.mapper.PmsFormDesignFieldMapper;
import com.zjry.pms.pmsFormDesign.service.IFormDesignFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 自定义表单Service业务层处理
 * 
 * @author yangtao
 * @date 2021-07-27
 */
@Service
public class PmsFormDesignFieldServiceImpl extends ServiceImpl<PmsFormDesignFieldMapper, PmsFormDesignField> implements IFormDesignFieldService {
    @Autowired
    private PmsFormDesignFieldMapper formDesignFieldMapper;

    @Override
	public List<PmsFormDesignField> findByDesignId(String formDesignId) {
		return formDesignFieldMapper.findByDesignId(formDesignId);
	}
	
    @Override
	@Transactional
	public void save(List<PmsFormDesignField> fdcList) {
		if(fdcList != null && fdcList.size() > 0) {
			String formDesignId = fdcList.get(0).getFormDesignId();
			formDesignFieldMapper.delByDesignId(formDesignId);
			
			formDesignFieldMapper.insertBatch(fdcList);
		}
	}
}
