package com.zjry.pms.evaluate.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.evaluate.mapper.PmsBalancedScorecardMapper;
import com.zjry.pms.evaluate.domain.PmsBalancedScorecard;
import com.zjry.pms.evaluate.service.IPmsBalancedScorecardService;

/**
 * 平衡记分卡Service业务层处理
 * 
 * @author zjry
 * @date 2022-10-19
 */
@Service
public class PmsBalancedScorecardServiceImpl extends ServiceImpl<PmsBalancedScorecardMapper, PmsBalancedScorecard> implements IPmsBalancedScorecardService {
    @Autowired
    private PmsBalancedScorecardMapper pmsBalancedScorecardMapper;

    /**
     * 查询平衡记分卡
     * 
     * @param id 平衡记分卡ID
     * @return 平衡记分卡
     */
    @Override
    public PmsBalancedScorecard selectPmsBalancedScorecardById(Long id) {
        return pmsBalancedScorecardMapper.selectPmsBalancedScorecardById(id);
    }

    /**
     * 查询平衡记分卡列表
     * 
     * @param pmsBalancedScorecard 平衡记分卡
     * @return 平衡记分卡
     */
    @Override
    public List<PmsBalancedScorecard> selectPmsBalancedScorecardList(PmsBalancedScorecard pmsBalancedScorecard) {
        return pmsBalancedScorecardMapper.selectPmsBalancedScorecardList(pmsBalancedScorecard);
    }

    /**
     * 新增平衡记分卡
     * 
     * @param pmsBalancedScorecard 平衡记分卡
     * @return 结果
     */
    @Override
    public int insertPmsBalancedScorecard(PmsBalancedScorecard pmsBalancedScorecard) {
    	pmsBalancedScorecard.preInsert();
        return pmsBalancedScorecardMapper.insertPmsBalancedScorecard(pmsBalancedScorecard);
    }

    /**
     * 修改平衡记分卡
     * 
     * @param pmsBalancedScorecard 平衡记分卡
     * @return 结果
     */
    @Override
    public int updatePmsBalancedScorecard(PmsBalancedScorecard pmsBalancedScorecard) {
    	pmsBalancedScorecard.preUpdate();
        return pmsBalancedScorecardMapper.updatePmsBalancedScorecard(pmsBalancedScorecard);
    }

    /**
     * 批量删除平衡记分卡
     * 
     * @param ids 需要删除的平衡记分卡ID
     * @return 结果
     */
    @Override
    public int deletePmsBalancedScorecardByIds(Long[] ids) {
        return pmsBalancedScorecardMapper.deletePmsBalancedScorecardByIds(ids);
    }

    /**
     * 删除平衡记分卡信息
     * 
     * @param id 平衡记分卡ID
     * @return 结果
     */
    @Override
    public int deletePmsBalancedScorecardById(Long id) {
        return pmsBalancedScorecardMapper.deletePmsBalancedScorecardById(id);
    }
}
