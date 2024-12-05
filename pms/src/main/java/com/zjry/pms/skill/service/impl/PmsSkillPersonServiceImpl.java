package com.zjry.pms.skill.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.pms.skill.mapper.PmsSkillPersonMapper;
import com.zjry.pms.skill.domain.PmsSkillPerson;
import com.zjry.pms.skill.service.IPmsSkillPersonService;

/**
 * 技术参与人Service业务层处理
 * 
 * @author maozt
 * @date 2022-08-01
 */
@Service
public class PmsSkillPersonServiceImpl extends ServiceImpl<PmsSkillPersonMapper, PmsSkillPerson> implements IPmsSkillPersonService {
    @Autowired
    private PmsSkillPersonMapper pmsSkillPersonMapper;

    /**
     * 查询技术参与人
     * 
     * @param id 技术参与人ID
     * @return 技术参与人
     */
    @Override
    public PmsSkillPerson selectPmsSkillPersonById(Long id) {
        return pmsSkillPersonMapper.selectPmsSkillPersonById(id);
    }

    /**
     * 查询技术参与人列表
     * 
     * @param pmsSkillPerson 技术参与人
     * @return 技术参与人
     */
    @Override
    public List<PmsSkillPerson> selectPmsSkillPersonList(PmsSkillPerson pmsSkillPerson) {
        return pmsSkillPersonMapper.selectPmsSkillPersonList(pmsSkillPerson);
    }

    /**
     * 新增技术参与人
     * 
     * @param pmsSkillPerson 技术参与人
     * @return 结果
     */
    @Override
    public int insertPmsSkillPerson(PmsSkillPerson pmsSkillPerson) {
    	pmsSkillPerson.preInsert();
        return pmsSkillPersonMapper.insertPmsSkillPerson(pmsSkillPerson);
    }

    /**
     * 新增技术参与人
     *
     * @param pmsSkillPerson 技术参与人
     * @return 结果
     */
    @Override
    public int insertPmsSkillPersonPl(PmsSkillPerson pmsSkillPerson) {
        int row = 0;
        List<PmsSkillPerson> personList = pmsSkillPerson.getPersonList();
        for (PmsSkillPerson skillPerson : personList) {
            skillPerson.preInsert();
            skillPerson.setCompCode(pmsSkillPerson.getCompCode());
            skillPerson.setTechnoCode(pmsSkillPerson.getTechnoCode());
            skillPerson.setTechnoType(pmsSkillPerson.getTechnoType());
            row += pmsSkillPersonMapper.insertPmsSkillPerson(skillPerson);
        }
        return row;
    }

    /**
     * 修改技术参与人
     * 
     * @param pmsSkillPerson 技术参与人
     * @return 结果
     */
    @Override
    public int updatePmsSkillPerson(PmsSkillPerson pmsSkillPerson) {
    	pmsSkillPerson.preUpdate();
        return pmsSkillPersonMapper.updatePmsSkillPerson(pmsSkillPerson);
    }

    /**
     * 批量删除技术参与人
     * 
     * @param ids 需要删除的技术参与人ID
     * @return 结果
     */
    @Override
    public int deletePmsSkillPersonByIds(Long[] ids) {
        return pmsSkillPersonMapper.deletePmsSkillPersonByIds(ids);
    }

    /**
     * 删除技术参与人信息
     * 
     * @param id 技术参与人ID
     * @return 结果
     */
    @Override
    public int deletePmsSkillPersonById(Long id) {
        return pmsSkillPersonMapper.deletePmsSkillPersonById(id);
    }

    /**
     * 删除技术参与人信息
     *
     * @param pmsSkillPerson 技术参与人
     * @return 结果
     */
    @Override
    public int deletePmsSkillPerson(PmsSkillPerson pmsSkillPerson) {
        return pmsSkillPersonMapper.deletePmsSkillPerson(pmsSkillPerson);
    }
}
