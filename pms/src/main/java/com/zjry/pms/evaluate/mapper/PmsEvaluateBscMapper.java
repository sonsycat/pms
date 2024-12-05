package com.zjry.pms.evaluate.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.evaluate.domain.PmsEvaluateBsc;

/**
 * BSC分类Mapper接口
 * 
 * @author mk
 * @date 2022-08-01
 */
public interface PmsEvaluateBscMapper extends BaseMapper<PmsEvaluateBsc> {
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
    public int insertPmsEvaluateBsc(PmsEvaluateBsc pmsEvaluateBsc);

    /**
     * 修改BSC分类
     * 
     * @param pmsEvaluateBsc BSC分类
     * @return 结果
     */
    public int updatePmsEvaluateBsc(PmsEvaluateBsc pmsEvaluateBsc);

    /**
     * 删除BSC分类
     * 
     * @param id BSC分类ID
     * @return 结果
     */
    public int deletePmsEvaluateBscById(Long id);

    /**
     * 批量删除BSC分类
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsEvaluateBscByIds(Long[] ids);

    PmsEvaluateBsc getBecCode(String bscCode);
}
