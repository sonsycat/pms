package com.zjry.pms.evaluate.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.evaluate.domain.PmsEvaluateBsc;

/**
 * BSC分类Service接口
 * 
 * @author mk
 * @date 2022-08-01
 */
public interface IPmsEvaluateBscService extends IService<PmsEvaluateBsc> {
    /**
     * 查询BSC分类
     * 
     * @param id BSC分类ID
     * @return BSC分类
     */
    public PmsEvaluateBsc selectPmsEvaluateBscById(Long id);

    /**
     * 查询BSC分类列表
     * 
     * @param pmsEvaluateBsc BSC分类
     * @return BSC分类集合
     */
    public List<PmsEvaluateBsc> selectPmsEvaluateBscList(PmsEvaluateBsc pmsEvaluateBsc);

    /**
     * 新增BSC分类
     * 
     * @param pmsEvaluateBsc BSC分类
     * @return 结果
     */
    public int insertPmsEvaluateBsc(PmsEvaluateBsc pmsEvaluateBsc) throws Exception;

    /**
     * 修改BSC分类
     * 
     * @param pmsEvaluateBsc BSC分类
     * @return 结果
     */
    public int updatePmsEvaluateBsc(PmsEvaluateBsc pmsEvaluateBsc);

    /**
     * 批量删除BSC分类
     * 
     * @param ids 需要删除的BSC分类ID
     * @return 结果
     */
    public String deletePmsEvaluateBscByIds(Long[] ids);

    /**
     * 删除BSC分类信息
     * 
     * @param id BSC分类ID
     * @return 结果
     */
    public int deletePmsEvaluateBscById(Long id);

    String updateIsStop(Long id, String isStop);
}
