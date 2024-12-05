package com.zjry.pms.basic.domain;

/**
 * @author mk
 * @title: AssetsBasicStoreVo
 * @projectName zjry-asset
 * @date 2021/8/1109:24
 */
public class AssetsBasicPublicVo {
    private static final long serialVersionUID = 1L;
    /**字典值*/
    private String dictValue;
    /**字典标签*/
    private String dictLabel;

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public String getDictLabel() {
        return dictLabel;
    }

    public void setDictLabel(String dictLabel) {
        this.dictLabel = dictLabel;
    }
}
