package com.zjry.pms.foundation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.pms.foundation.domain.PmsEquivalentDept;
import com.zjry.pms.foundation.domain.PmsEquivalentItem;
import com.zjry.pms.foundation.domain.PmsFundationEquivalent;
import com.zjry.pms.foundation.domain.PmsMatchDept;

import java.util.List;

/**
 * 分摊计算Service接口
 * 
 * @author yzh
 * @date 2022-05-17
 */
public interface IPmsFoundationEquivalentService extends IService<PmsFundationEquivalent> {

    List<PmsFundationEquivalent> selectPmsEquivalentItemList(PmsFundationEquivalent pmsFundationEquivalent);

    List<PmsEquivalentDept> selectPmsEquivalentDept();


    List<PmsEquivalentItem> selectPmsEquivalentItem(String keywords);

    PmsFundationEquivalent selectpPmsFundationEquivalentById(Long id);

    int updatePmsFundationEquivalent(PmsFundationEquivalent pmsFundationEquivalent);

    List<PmsMatchDept> selectPmsMatchDeptlist();


}
