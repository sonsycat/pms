package com.zjry.pms.skill.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.skill.mapper.PmsSkillMonographMapper;
import com.zjry.pms.skill.domain.PmsSkillMonograph;
import com.zjry.pms.skill.service.IPmsSkillMonographService;

/**
 * 专著Service业务层处理
 * 
 * @author maozt
 * @date 2022-08-02
 */
@Service
public class PmsSkillMonographServiceImpl extends ServiceImpl<PmsSkillMonographMapper, PmsSkillMonograph> implements IPmsSkillMonographService {
    @Autowired
    private PmsSkillMonographMapper pmsSkillMonographMapper;

    /**
     * 查询专著
     * 
     * @param id 专著ID
     * @return 专著
     */
    @Override
    public PmsSkillMonograph selectPmsSkillMonographById(Long id) {
        return pmsSkillMonographMapper.selectPmsSkillMonographById(id);
    }

    /**
     * 查询专著列表
     * 
     * @param pmsSkillMonograph 专著
     * @return 专著
     */
    @Override
    public List<PmsSkillMonograph> selectPmsSkillMonographList(PmsSkillMonograph pmsSkillMonograph) {
        return pmsSkillMonographMapper.selectPmsSkillMonographList(pmsSkillMonograph);
    }

    /**
     * 查询专著列表
     *
     * @param pmsSkillMonograph 专著
     * @return 专著
     */
    @Override
    public List<PmsSkillMonograph> selectPmsSkillMonographListByName(PmsSkillMonograph pmsSkillMonograph) {
        return pmsSkillMonographMapper.selectPmsSkillMonographListByName(pmsSkillMonograph);
    }

    /**
     * 新增专著
     * 
     * @param pmsSkillMonograph 专著
     * @return 结果
     */
    @Override
    public int insertPmsSkillMonograph(PmsSkillMonograph pmsSkillMonograph) {
    	pmsSkillMonograph.preInsert();
        return pmsSkillMonographMapper.insertPmsSkillMonograph(pmsSkillMonograph);
    }

    /**
     * 修改专著
     * 
     * @param pmsSkillMonograph 专著
     * @return 结果
     */
    @Override
    public int updatePmsSkillMonograph(PmsSkillMonograph pmsSkillMonograph) {
    	pmsSkillMonograph.preUpdate();
        return pmsSkillMonographMapper.updatePmsSkillMonograph(pmsSkillMonograph);
    }

    /**
     * 批量删除专著
     * 
     * @param ids 需要删除的专著ID
     * @return 结果
     */
    @Override
    public int deletePmsSkillMonographByIds(Long[] ids) {
        return pmsSkillMonographMapper.deletePmsSkillMonographByIds(ids);
    }

    /**
     * 删除专著信息
     * 
     * @param id 专著ID
     * @return 结果
     */
    @Override
    public int deletePmsSkillMonographById(Long id) {
        return pmsSkillMonographMapper.deletePmsSkillMonographById(id);
    }
}
