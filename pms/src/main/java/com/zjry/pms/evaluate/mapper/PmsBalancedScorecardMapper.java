package com.zjry.pms.evaluate.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.evaluate.domain.PmsBalancedScorecard;
import org.apache.ibatis.annotations.Param;

/**
 * 平衡记分卡Mapper接口
 *
 * @author zjry
 * @date 2022-10-19
 */
public interface PmsBalancedScorecardMapper extends BaseMapper<PmsBalancedScorecard> {

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
     * 删除平衡记分卡
     *
     * @param id 平衡记分卡ID
     * @return 结果
     */
    public int deletePmsBalancedScorecardById(Long id);

    /**
     * 批量删除平衡记分卡
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsBalancedScorecardByIds(Long[] ids);

    /**
     * 批量保存
     *
     * @param insertList
     */
    void saveBatch(@Param("list") List<PmsBalancedScorecard> insertList);

    /**
     * 按月删除
     * @param nowDate 2022-10-01
     * @return
     */
    int deleteByMonth(String nowDate);
}
