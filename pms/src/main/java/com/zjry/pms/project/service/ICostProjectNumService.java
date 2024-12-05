package com.zjry.pms.project.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.project.domain.CostNumList;
import com.zjry.pms.project.domain.CostProjectNum;

/**
 * 成本金额管理Service接口
 * 
 * @author mk
 * @date 2022-05-18
 */
public interface ICostProjectNumService extends IService<CostProjectNum> {
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
     * @param costNumList 成本金额管理
     * @return 结果
     */
    public String insertCostProjectNum(CostNumList costNumList) throws Exception;

    /**
     * 修改成本金额管理
     * 
     * @param costProjectNum 成本金额管理
     * @return 结果
     */
    public int updateCostProjectNum(CostProjectNum costProjectNum);

    /**
     * 批量删除成本金额管理
     * 
     * @param ids 需要删除的成本金额管理ID
     * @return 结果
     */
    public int deleteCostProjectNumByIds(Long[] ids);

    /**
     * 删除成本金额管理信息
     * 
     * @param id 成本金额管理ID
     * @return 结果
     */
    public int deleteCostProjectNumById(Long id);

    List<Long> selectCostPNDept(CostProjectNum costProjectNum);

    void importData(List<CostProjectNum> costProjectfies, Long compCode);

    int deleteByCodeAndDate(Long deptCode, Date nowDate);
}
