package com.zjry.common.constant;

/**
 * 数据库相关常量类
 *
 * @author lf
 * @date 2021年06月29日 14:33
 */
public class DataBaseConstants {
    public enum FieldEnum {
        VARCHAR("varchar", "字符类型"),
        INT("int", "数值类型"),
        BIGINT("bigint", "big数值类型"),
        DATETIME("datetime", "时间"),
        CHAR("char", "字符类型");

        private String code;
        private String msg;

        FieldEnum(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

    public enum FieldConnectEnum {
        EQ("eq", "=", "等于"),
        NE("ne", "!=", "不等于"),
        GT("gt", ">", "大于"),
        LT("lt", "<", "小于"),
        GE("ge", ">=", "大于等于"),
        LE("le", "<=", "小于等于"),
        LIKE("like", " like ", "包含"),
        BEGIN_WITH("begin_with", " like ", "以...开始"),
        END_WITH("end_with", " like ", "以...结尾");

        private String code;
        private String value;
        private String msg;

        FieldConnectEnum(String code, String value, String msg) {
            this.code = code;
            this.value = value;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public String getMsg() {
            return msg;
        }
    }

}
