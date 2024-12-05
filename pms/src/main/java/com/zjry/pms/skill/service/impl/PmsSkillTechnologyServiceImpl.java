package com.zjry.pms.skill.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.skill.mapper.PmsSkillTechnologyMapper;
import com.zjry.pms.skill.domain.PmsSkillTechnology;
import com.zjry.pms.skill.service.IPmsSkillTechnologyService;

/**
 * 新业务新技术Service业务层处理
 * 
 * @author maozt
 * @date 2022-08-02
 */
@Service
public class PmsSkillTechnologyServiceImpl extends ServiceImpl<PmsSkillTechnologyMapper, PmsSkillTechnology> implements IPmsSkillTechnologyService {
    @Autowired
    private PmsSkillTechnologyMapper pmsSkillTechnologyMapper;

    /**
     * 查询新业务新技术
     * 
     * @param id 新业务新技术ID
     * @return 新业务新技术
     */
    @Override
    public PmsSkillTechnology selectPmsSkillTechnologyById(Long id) {
        return pmsSkillTechnologyMapper.selectPmsSkillTechnologyById(id);
    }

    /**
     * 查询新业务新技术列表
     * 
     * @param pmsSkillTechnology 新业务新技术
     * @return 新业务新技术
     */
    @Override
    public List<PmsSkillTechnology> selectPmsSkillTechnologyList(PmsSkillTechnology pmsSkillTechnology) {
        return pmsSkillTechnologyMapper.selectPmsSkillTechnologyList(pmsSkillTechnology);
    }

    /**
     * 查询新业务新技术列表
     *
     * @param pmsSkillTechnology 新业务新技术
     * @return 新业务新技术
     */
    @Override
    public List<PmsSkillTechnology> selectPmsSkillTechnologyListByName(PmsSkillTechnology pmsSkillTechnology) {
        return pmsSkillTechnologyMapper.selectPmsSkillTechnologyListByName(pmsSkillTechnology);
    }

    /**
     * 新增新业务新技术
     * 
     * @param pmsSkillTechnology 新业务新技术
     * @return 结果
     */
    @Override
    public int insertPmsSkillTechnology(PmsSkillTechnology pmsSkillTechnology) {
    	pmsSkillTechnology.preInsert();
        return pmsSkillTechnologyMapper.insertPmsSkillTechnology(pmsSkillTechnology);
    }

    /**
     * 修改新业务新技术
     * 
     * @param pmsSkillTechnology 新业务新技术
     * @return 结果
     */
    @Override
    public int updatePmsSkillTechnology(PmsSkillTechnology pmsSkillTechnology) {
    	pmsSkillTechnology.preUpdate();
        return pmsSkillTechnologyMapper.updatePmsSkillTechnology(pmsSkillTechnology);
    }

    /**
     * 批量删除新业务新技术
     * 
     * @param ids 需要删除的新业务新技术ID
     * @return 结果
     */
    @Override
    public int deletePmsSkillTechnologyByIds(Long[] ids) {
        return pmsSkillTechnologyMapper.deletePmsSkillTechnologyByIds(ids);
    }

    /**
     * 删除新业务新技术信息
     * 
     * @param id 新业务新技术ID
     * @return 结果
     */
    @Override
    public int deletePmsSkillTechnologyById(Long id) {
        return pmsSkillTechnologyMapper.deletePmsSkillTechnologyById(id);
    }
}
