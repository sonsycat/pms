package com.zjry.formDesign.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.formDesign.domain.FormDesignField;
import com.zjry.formDesign.vo.FormDesignVo;

/**
 * 自定义表单Service接口
 * 
 * @author yangtao
 * @date 2021-07-27
 */
public interface IFormProcessService extends IService<Map> {
    /**
     * 查询自定义表单
     * 
     * @param id 自定义表单ID
     * @return 自定义表单
     */
    public Map<String, Object> findById(String formDesignId, String id);
    
    public Map<String, Object> findFormData(String formDesignId, String id);

    /**
     * 查询自定义表单列表
     * 
     * @param formDesign 自定义表单
     * @return 自定义表单集合
     */
    public List<Map<String, Object>> selectList(String formDesignId, Map<String, Object> param,List<FormDesignField> listField);

    /**
     * 新增自定义表单
     * 
     * @param formDesign 自定义表单
     * @return 结果
     */
    public int insert(String formDesignId, Map<String, Object> param);

    /**
     * 修改自定义表单
     * 
     * @param formDesign 自定义表单
     * @return 结果
     */
    public int update(String formDesignId, Map<String, Object> params, String id);

    public int updateFormJson(FormDesignVo formDesignVo);
    
    /**
     * 批量删除自定义表单
     * 
     * @param ids 需要删除的自定义表单ID
     * @return 结果
     */
    public int deleteById(String formDesignId, String id);

    public boolean checkFormCodeUnique(String formCode);
    
    public int updateFormData(String formDesignId, String formData);

    List<Map<String, Object>> selectForDetails(String formDesignId, String deptCode, Date qDate);
}
