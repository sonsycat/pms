package com.zjry.pms.eva;

import com.zjry.pms.bonus.domain.PmsBonusPersonAudit;
import com.zjry.pms.bonus.service.IPmsBonusPersonAuditService;
import com.zjry.pms.common.utils.DateUtils;
import com.zjry.pms.evaluate.service.IPmsEvaluateSetDeptDetailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EvaTest {

    @Autowired
    private IPmsEvaluateSetDeptDetailService evaluateSetDeptDetailService;

    @Autowired
    private IPmsBonusPersonAuditService bonusPersonAuditService;

    @Test
    public void test() {
        evaluateSetDeptDetailService.calculateScorecardByMonth(DateUtils.parseDate("2022-10-01"));
    }

    @Test
    public void test2() {
        PmsBonusPersonAudit query = new PmsBonusPersonAudit();
        query.setDeptCode("100");
        query.setNowDate(DateUtils.parseDate("1993-04-01"));
        bonusPersonAuditService.updateData(query);
    }

}
