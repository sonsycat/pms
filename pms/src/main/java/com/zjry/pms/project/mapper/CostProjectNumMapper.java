package com.zjry.pms.project.mapper;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.project.domain.CostProjectNum;
import org.apache.ibatis.annotations.Param;

/**
 * 成本金额管理Mapper接口
 * 
 * @author mk
 * @date 2022-05-18
 */
public interface CostProjectNumMapper extends BaseMapper<CostProjectNum> {
    /**
     * 查询成本金额管理
     * 
     * @param id 成本金额管理ID
     * @return 成本金额管理
     */
    public CostProjectNum selectCostProjectNumById(Long id);

    /**
     * 查询成本金额管理列表
     * 
     * @param costProjectNum 成本金额管理
     * @return 成本金额管理集合
     */
    public List<CostProjectNum> selectCostProjectNumList(CostProjectNum costProjectNum);
    
    /**
     * 查询项目列表
     * 
     * @param costProjectNum 查询项目列表
     * @return 查询项目列表
     */
    public List<CostProjectNum> selectCostProjectNumListGroup(CostProjectNum costProjectNum);

    /**
     * 新增成本金额管理
     * 
     * @param costProjectNum 成本金额管理
     * @return 结果
     */
    public int insertCostProjectNum(CostProjectNum costProjectNum);

    /**
     * 修改成本金额管理
     * 
     * @param costProjectNum 成本金额管理
     * @return 结果
     */
    public int updateCostProjectNum(CostProjectNum costProjectNum);

    /**
     * 删除成本金额管理
     * 
     * @param id 成本金额管理ID
     * @return 结果
     */
    public int deleteCostProjectNumById(Long id);

    /**
     * 批量删除成本金额管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCostProjectNumByIds(Long[] ids);

    int deleteByCodeAndDate(@Param("deptCode") Long deptCode,@Param("nowDate") Date nowDate);

    List<Long> selectCostPNDept(CostProjectNum costProjectNum);
}
