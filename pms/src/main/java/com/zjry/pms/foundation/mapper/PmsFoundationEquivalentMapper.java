package com.zjry.pms.foundation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjry.pms.foundation.domain.PmsEquivalentDept;
import com.zjry.pms.foundation.domain.PmsEquivalentItem;
import com.zjry.pms.foundation.domain.PmsFundationEquivalent;
import com.zjry.pms.foundation.domain.PmsMatchDept;

import java.util.List;

public interface PmsFoundationEquivalentMapper extends BaseMapper<PmsFundationEquivalent> {
    List<PmsFundationEquivalent> selectPmsFundationEquivalentList(PmsFundationEquivalent pmsFundationEquivalent);

    List<PmsEquivalentDept> selectPmsEquivalentDeptList();

    List<PmsEquivalentItem> selectPmsEquivalentItemList(String keywords);

    PmsFundationEquivalent selectpPmsFundationEquivalentById(Long id);

    int updatePmsFundationEquivalent(PmsFundationEquivalent pmsFundationEquivalent);

    List<PmsMatchDept> selectPmsMatchDeptlist();

    List<PmsFundationEquivalent> selectPmsFundationEquivalentListByMatchDeptName(PmsFundationEquivalent pmsFundationEquivalent);
}
