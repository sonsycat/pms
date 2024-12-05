package com.zjry.common.vo.database;

/**
 * 通过表名查询字段返回vo  Options
 * 写在common里所有的地方都可以引用
 *
 * @author lf
 * @date 2021年06月29日 14:03
 */
public class FieldVo {
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 字段类型
     */
    private String fieldType;
    /**
     * 字段注释
     */
    private String fieldLabel;

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public FieldVo(String fieldName, String fieldType, String fieldLabel) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.fieldLabel = fieldLabel;
    }
}
