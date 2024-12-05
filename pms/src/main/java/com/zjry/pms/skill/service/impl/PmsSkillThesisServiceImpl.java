package com.zjry.pms.skill.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.skill.mapper.PmsSkillThesisMapper;
import com.zjry.pms.skill.domain.PmsSkillThesis;
import com.zjry.pms.skill.service.IPmsSkillThesisService;

/**
 * 论文Service业务层处理
 * 
 * @author maozt
 * @date 2022-07-28
 */
@Service
public class PmsSkillThesisServiceImpl extends ServiceImpl<PmsSkillThesisMapper, PmsSkillThesis> implements IPmsSkillThesisService {
    @Autowired
    private PmsSkillThesisMapper pmsSkillThesisMapper;

    /**
     * 查询论文
     * 
     * @param id 论文ID
     * @return 论文
     */
    @Override
    public PmsSkillThesis selectPmsSkillThesisById(Long id) {
        return pmsSkillThesisMapper.selectPmsSkillThesisById(id);
    }

    /**
     * 查询论文列表
     * 
     * @param pmsSkillThesis 论文
     * @return 论文
     */
    @Override
    public List<PmsSkillThesis> selectPmsSkillThesisList(PmsSkillThesis pmsSkillThesis) {
        return pmsSkillThesisMapper.selectPmsSkillThesisList(pmsSkillThesis);
    }

    /**
     * 查询论文列表
     *
     * @param pmsSkillThesis 论文
     * @return 论文
     */
    @Override
    public List<PmsSkillThesis> selectPmsSkillThesisListByName(PmsSkillThesis pmsSkillThesis) {
        return pmsSkillThesisMapper.selectPmsSkillThesisListByName(pmsSkillThesis);
    }

    /**
     * 新增论文
     * 
     * @param pmsSkillThesis 论文
     * @return 结果
     */
    @Override
    public int insertPmsSkillThesis(PmsSkillThesis pmsSkillThesis) {
    	pmsSkillThesis.preInsert();
        return pmsSkillThesisMapper.insertPmsSkillThesis(pmsSkillThesis);
    }

    /**
     * 修改论文
     * 
     * @param pmsSkillThesis 论文
     * @return 结果
     */
    @Override
    public int updatePmsSkillThesis(PmsSkillThesis pmsSkillThesis) {
    	pmsSkillThesis.preUpdate();
        return pmsSkillThesisMapper.updatePmsSkillThesis(pmsSkillThesis);
    }

    /**
     * 批量删除论文
     * 
     * @param ids 需要删除的论文ID
     * @return 结果
     */
    @Override
    public int deletePmsSkillThesisByIds(Long[] ids) {
        return pmsSkillThesisMapper.deletePmsSkillThesisByIds(ids);
    }

    /**
     * 删除论文信息
     * 
     * @param id 论文ID
     * @return 结果
     */
    @Override
    public int deletePmsSkillThesisById(Long id) {
        return pmsSkillThesisMapper.deletePmsSkillThesisById(id);
    }
}
