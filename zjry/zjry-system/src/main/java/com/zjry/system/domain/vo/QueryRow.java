package com.zjry.system.domain.vo;

/**
 * 自定义查询请求单行数据
 *
 * @author lf
 * @date 2021年06月30日 14:39
 */
public class QueryRow {
    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 字段连接符 eq、ne等 要通过常量枚举转成数据库能识别的连接符
     */
    private String fieldConnect;
    /**
     * 字段值
     */
    private String fieldValue;
    /**
     * 字段类型
     */
    private String fieldType;

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldConnect() {
        return fieldConnect;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setFieldConnect(String fieldConnect) {
        this.fieldConnect = fieldConnect;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
}
