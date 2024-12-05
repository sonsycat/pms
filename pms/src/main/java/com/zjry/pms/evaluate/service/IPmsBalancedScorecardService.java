package com.zjry.pms.evaluate.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.evaluate.domain.PmsBalancedScorecard;

/**
 * 平衡记分卡Service接口
 * 
 * @author zjry
 * @date 2022-10-19
 */
public interface IPmsBalancedScorecardService extends IService<PmsBalancedScorecard> {
    /**
     * 查询平衡记分卡
     * 
     * @param id 平衡记分卡ID
     * @return 平衡记分卡
     */
    public PmsBalancedScorecard selectPmsBalancedScorecardById(Long id);

    /**
     * 查询平衡记分卡列表
     * 
     * @param pmsBalancedScorecard 平衡记分卡
     * @return 平衡记分卡集合
     */
    public List<PmsBalancedScorecard> selectPmsBalancedScorecardList(PmsBalancedScorecard pmsBalancedScorecard);

    /**
     * 新增平衡记分卡
     * 
     * @param pmsBalancedScorecard 平衡记分卡
     * @return 结果
     */
    public int insertPmsBalancedScorecard(PmsBalancedScorecard pmsBalancedScorecard);

    /**
     * 修改平衡记分卡
     * 
     * @param pmsBalancedScorecard 平衡记分卡
     * @return 结果
     */
    public int updatePmsBalancedScorecard(PmsBalancedScorecard pmsBalancedScorecard);

    /**
     * 批量删除平衡记分卡
     * 
     * @param ids 需要删除的平衡记分卡ID
     * @return 结果
     */
    public int deletePmsBalancedScorecardByIds(Long[] ids);

    /**
     * 删除平衡记分卡信息
     * 
     * @param id 平衡记分卡ID
     * @return 结果
     */
    public int deletePmsBalancedScorecardById(Long id);
}
