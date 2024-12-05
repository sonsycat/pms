package com.zjry.pms.pmsFormDesign.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.formDesign.vo.FormDesignVo;
import com.zjry.pms.pmsFormDesign.domain.PmsFormDesign;

import java.util.List;

/**
 * 自定义表单Service接口
 * 
 * @author yangtao
 * @date 2021-07-27
 */
public interface IFormDesignService extends IService<PmsFormDesign> {
    /**
     * 查询自定义表单
     * 
     * @param id 自定义表单ID
     * @return 自定义表单
     */
    public PmsFormDesign selectFormDesignById(String id);

    /**
     * 查询自定义表单列表
     * 
     * @param formDesign 自定义表单
     * @return 自定义表单集合
     */
    public List<PmsFormDesign> selectFormDesignList(PmsFormDesign formDesign);

    /**
     * 新增自定义表单
     * 
     * @param formDesign 自定义表单
     * @return 结果
     */
    public int insertFormDesign(PmsFormDesign formDesign);

    /**
     * 修改自定义表单
     * 
     * @param formDesign 自定义表单
     * @return 结果
     */
    public int updateFormDesign(PmsFormDesign formDesign);

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
    
}
