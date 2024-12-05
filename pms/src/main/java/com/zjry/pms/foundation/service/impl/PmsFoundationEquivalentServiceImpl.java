package com.zjry.pms.foundation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.pms.foundation.domain.PmsEquivalentDept;
import com.zjry.pms.foundation.domain.PmsEquivalentItem;
import com.zjry.pms.foundation.domain.PmsFundationEquivalent;
import com.zjry.pms.foundation.domain.PmsMatchDept;
import com.zjry.pms.foundation.mapper.PmsFoundationEquivalentMapper;
import com.zjry.pms.foundation.service.IPmsFoundationEquivalentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;


@Service
public class PmsFoundationEquivalentServiceImpl extends ServiceImpl<PmsFoundationEquivalentMapper, PmsFundationEquivalent> implements IPmsFoundationEquivalentService {
    @Autowired
    private PmsFoundationEquivalentMapper pmsFoundationEquivalentMapper;

    @Override
    public List<PmsFundationEquivalent> selectPmsEquivalentItemList(PmsFundationEquivalent pmsFundationEquivalent) {
        if(null == pmsFundationEquivalent.getEquivalentItemCode() && null == pmsFundationEquivalent.getEquivalentDeptCode()
                && null == pmsFundationEquivalent.getMatchDeptName()){
            return Collections.emptyList();
        } else if (null != pmsFundationEquivalent.getMatchDeptName()) {
            List<PmsFundationEquivalent> pmsFundationEquivalentList = pmsFoundationEquivalentMapper.selectPmsFundationEquivalentListByMatchDeptName(pmsFundationEquivalent);
            return pmsFundationEquivalentList;
        }
        {
            List<PmsFundationEquivalent> pmsFundationEquivalentList = pmsFoundationEquivalentMapper.selectPmsFundationEquivalentList(pmsFundationEquivalent);
            return pmsFundationEquivalentList;
        }

    }

    @Override
    public List<PmsEquivalentDept> selectPmsEquivalentDept() {

        return pmsFoundationEquivalentMapper.selectPmsEquivalentDeptList();
    }

    @Override
    public List<PmsEquivalentItem> selectPmsEquivalentItem(String keywords) {
        return pmsFoundationEquivalentMapper.selectPmsEquivalentItemList( keywords);
    }

    @Override
    public PmsFundationEquivalent selectpPmsFundationEquivalentById(Long id) {
        return pmsFoundationEquivalentMapper.selectpPmsFundationEquivalentById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePmsFundationEquivalent(PmsFundationEquivalent pmsFundationEquivalent) {
        pmsFundationEquivalent.preUpdate();
        return pmsFoundationEquivalentMapper.updatePmsFundationEquivalent(pmsFundationEquivalent);
    }

    @Override
    public List<PmsMatchDept> selectPmsMatchDeptlist() {
        return pmsFoundationEquivalentMapper.selectPmsMatchDeptlist();
    }
}
