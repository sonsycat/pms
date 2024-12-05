package com.zjry.pms.basic.domain;

/**
 * @author mk
 * @title: BasicPublicVo
 * @projectName zjry-asset
 * @date 2021/10/2510:54
 */
public class BasicPublicVo {
    private static final long serialVersionUID = 1L;
    /**字典值*/
    private String name;
    /**字典标签*/
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
