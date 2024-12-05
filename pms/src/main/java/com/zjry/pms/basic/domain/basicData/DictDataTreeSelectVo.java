package com.zjry.pms.basic.domain.basicData;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 字典数据树
 *
 * @author lf
 */
@Data
public class DictDataTreeSelectVo extends BasicdataDictData implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean hasChildren;

    private List<DictDataTreeSelectVo> children;
}
