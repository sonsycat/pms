package com.zjry.pms.skill.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.skill.mapper.PmsSkillAchieveMapper;
import com.zjry.pms.skill.domain.PmsSkillAchieve;
import com.zjry.pms.skill.service.IPmsSkillAchieveService;

/**
 * 成果Service业务层处理
 * 
 * @author maozt
 * @date 2022-08-01
 */
@Service
public class PmsSkillAchieveServiceImpl extends ServiceImpl<PmsSkillAchieveMapper, PmsSkillAchieve> implements IPmsSkillAchieveService {
    @Autowired
    private PmsSkillAchieveMapper pmsSkillAchieveMapper;

    /**
     * 查询成果
     * 
     * @param id 成果ID
     * @return 成果
     */
    @Override
    public PmsSkillAchieve selectPmsSkillAchieveById(Long id) {
        return pmsSkillAchieveMapper.selectPmsSkillAchieveById(id);
    }

    /**
     * 查询成果列表
     * 
     * @param pmsSkillAchieve 成果
     * @return 成果
     */
    @Override
    public List<PmsSkillAchieve> selectPmsSkillAchieveList(PmsSkillAchieve pmsSkillAchieve) {
        return pmsSkillAchieveMapper.selectPmsSkillAchieveList(pmsSkillAchieve);
    }

    /**
     * 查询成果列表
     *
     * @param pmsSkillAchieve 成果
     * @return 成果
     */
    @Override
    public List<PmsSkillAchieve> selectPmsSkillAchieveListByName(PmsSkillAchieve pmsSkillAchieve) {
        return pmsSkillAchieveMapper.selectPmsSkillAchieveListByName(pmsSkillAchieve);
    }

    /**
     * 新增成果
     * 
     * @param pmsSkillAchieve 成果
     * @return 结果
     */
    @Override
    public int insertPmsSkillAchieve(PmsSkillAchieve pmsSkillAchieve) {
    	pmsSkillAchieve.preInsert();
        return pmsSkillAchieveMapper.insertPmsSkillAchieve(pmsSkillAchieve);
    }

    /**
     * 修改成果
     * 
     * @param pmsSkillAchieve 成果
     * @return 结果
     */
    @Override
    public int updatePmsSkillAchieve(PmsSkillAchieve pmsSkillAchieve) {
    	pmsSkillAchieve.preUpdate();
        return pmsSkillAchieveMapper.updatePmsSkillAchieve(pmsSkillAchieve);
    }

    /**
     * 批量删除成果
     * 
     * @param ids 需要删除的成果ID
     * @return 结果
     */
    @Override
    public int deletePmsSkillAchieveByIds(Long[] ids) {
        return pmsSkillAchieveMapper.deletePmsSkillAchieveByIds(ids);
    }

    /**
     * 删除成果信息
     * 
     * @param id 成果ID
     * @return 结果
     */
    @Override
    public int deletePmsSkillAchieveById(Long id) {
        return pmsSkillAchieveMapper.deletePmsSkillAchieveById(id);
    }
}
