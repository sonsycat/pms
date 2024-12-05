package com.zjry.formDesign.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.formDesign.domain.FormDesign;
import com.zjry.formDesign.vo.FormDesignVo;
import com.zjry.formDesign.vo.FormJson;

/**
 * 自定义表单Service接口
 * 
 * @author yangtao
 * @date 2021-07-27
 */
public interface IFormDesignService extends IService<FormDesign> {
    /**
     * 查询自定义表单
     * 
     * @param id 自定义表单ID
     * @return 自定义表单
     */
    public FormDesign selectFormDesignById(String id);

    /**
     * 查询自定义表单列表
     * 
     * @param formDesign 自定义表单
     * @return 自定义表单集合
     */
    public List<FormDesign> selectFormDesignList(FormDesign formDesign);

    /**
     * 新增自定义表单
     * 
     * @param formDesign 自定义表单
     * @return 结果
     */
    public int insertFormDesign(FormDesign formDesign);

    /**
     * 修改自定义表单
     * 
     * @param formDesign 自定义表单
     * @return 结果
     */
    public int updateFormDesign(FormDesign formDesign);

    public int updateFormJson(FormDesignVo formDesignVo);
    
    /**
     * 批量删除自定义表单
     * 
     * @param ids 需要删除的自定义表单ID
     * @return 结果
     */
    public int deleteFormDesignByIds(String[] ids);

    /**
     * 删除自定义表单信息
     * 
     * @param id 自定义表单ID
     * @return 结果
     */
    public int deleteFormDesignById(String id);
    
    public boolean checkFormCodeUnique(String formCode);
    
    public int updateFormData(String formDesignId, String formData);

    String selectForSortNo(Long compCode);
}
