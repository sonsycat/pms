package com.zjry.pms.evaluate.domain;

import com.zjry.common.core.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 奖金指标对象(保存接收参数)
 *
 * @author zjry
 * @date 2022-11-08
 */
@Data
public class PmsEvaluateIndexBonusDto extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /* 科室类别(字典JXGL_JJ_KSLB) */
    private String deptRoomType;
    /* 单位编码 */
    private Long compCode;

    private List<PmsEvaluateIndexBonus> evaluateIndexBonusList;
}
