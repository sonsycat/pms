package com.zjry.common.utils.database;

import com.alibaba.druid.filter.config.ConfigTools;
import com.zjry.common.constant.HttpStatus;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.utils.StringUtils;
import com.zjry.common.vo.database.FieldVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库操作工具类
 *
 * @author lf
 * @date 2021年06月29日 11:21
 */
@Component
public class DataBaseUtils {

    private static final Logger log = LoggerFactory.getLogger(DataBaseUtils.class);

    @Value("${spring.datasource.driverClassName}")
    private String driver;

    @Value("${spring.datasource.druid.master.url}")
    private String url;

    @Value("${spring.datasource.druid.master.username}")
    private String username;

    @Value("${spring.datasource.druid.master.password}")
    private String password;

    @Value("${spring.datasource.druid.connectProperties}")
    private String connectProperties;

    /**
     * 获取数据库连接
     *
     * @return java.sql.Connection
     * @author lf
     * @date 2021/6/29 11:31
     */
    public Connection getConnection() throws Exception {
        Connection conn = null;
        Class.forName(driver);
        String publicKey = connectProperties.substring(connectProperties.indexOf("key") + 4);
        String decryptPassword = ConfigTools.decrypt(publicKey, password);
        conn = DriverManager.getConnection(url, username, decryptPassword);
        return conn;
    }

    /**
     * 关闭数据库连接
     *
     * @param conn
     * @author lf
     * @date 2021/6/29 11:33
     */
    public void closeConnection(Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    /**
     * 通过表名查询字段
     *
     * @param tableName 表名
     * @return List<FieldVo> FieldVo集合
     * @author lf
     * @date 2021/6/29 12:28
     */
    public List<FieldVo> getUserDefinedQueryField(String tableName) throws Exception {
        // fieldVoList返回结果集
        List<FieldVo> fieldVoList = new ArrayList<>(16);
        // 建立数据库连接
        Connection conn = getConnection();
        PreparedStatement pst = null;
        try {
            // 查询sql TODO 判断不同的数据库执行不同的查询语句
            String sql = "SELECT DISTINCT COLUMN_NAME as fieldName, DATA_TYPE as fieldType, COLUMN_COMMENT as fieldLabel " +
                    "FROM INFORMATION_SCHEMA.COLUMNS " +
                    "WHERE table_name = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, tableName);
            // 执行查询获取元数据结果集
            ResultSet rs = pst.executeQuery();
            // 拼装fieldVoList返回结果集
            while (rs.next()) {
                String fieldType = rs.getString("fieldType");
                String fieldName = rs.getString("fieldName");
                String fieldLabel = rs.getString("fieldLabel");
                fieldVoList.add(new FieldVo(fieldName, fieldType, fieldLabel));
            }
        } finally {
            // 关闭数据库连接
            if (pst != null) {
                pst.close();
                closeConnection(conn);
            }
        }
        return fieldVoList;
    }

    /**
     * 自定义查询获取数据sql
     *
     * @param querySqlStr 查询sql
     * @param totalSqlStr 查询总数sql
     * @author lf
     * @date 2021/6/30 16:14
     */
    public Map userDefinedQueryList(String querySqlStr, String totalSqlStr) throws Exception {
        Map map = new HashMap(2);
        // 建立数据库连接
        Connection conn = getConnection();
        PreparedStatement pst = null;
        try {
            // 查询sql
            pst = conn.prepareStatement(totalSqlStr);
            // 执行totalSqlStr查询获总数
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                map.put("total",rs.getInt(1));
            }
            pst = conn.prepareStatement(querySqlStr);
            // 执行querySqlStr查询获取元数据结果集
            rs = pst.executeQuery();
            List list = new ArrayList();
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            while (rs.next()) {
                Map rowData = new HashMap();
                for (int i = 1; i <= columnCount; i++) {
                    String humpStr = StringUtils.strToHump(md.getColumnName(i));
                    rowData.put(humpStr, rs.getObject(i));
                }
                list.add(rowData);
            }
            map.put("list",list);
            return map;
        } finally {
            // 关闭数据库连接
            if (pst != null) {
                pst.close();
                closeConnection(conn);
            }
        }
    }

}
