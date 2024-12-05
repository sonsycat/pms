package com.zjry.system.domain.vo;

import java.util.List;

/**
 * 自定义查询请求vo
 *
 * @author lf
 * @date 2021年06月30日 14:36
 */
public class UserDefinedQueryVo {
    /**
     * 自定义查询表名
     */
    private String tableName;
    /**
     * 分页参数-第几页
     */
    private Long pageNum;
    /**
     * 分页参数-每页条数
     */
    private Long pageSize;
    /**
     * 是否添加del_flag=0作为查询条件
     */
    private Boolean checkDelFlag;
    /**
     * 字段与字段之间的连接符 or或者and
     */
    private String filterCondition;
    /**
     * 一行字段数据
     */
    private List<QueryRow> queryRows;

    public String getTableName() {
        return tableName;
    }

    public Long getPageNum() {
        return pageNum;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public Boolean getCheckDelFlag() {
        return checkDelFlag;
    }

    public String getFilterCondition() {
        return filterCondition;
    }

    public List<QueryRow> getQueryRows() {
        return queryRows;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public void setCheckDelFlag(Boolean checkDelFlag) {
        this.checkDelFlag = checkDelFlag;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    public void setQueryRows(List<QueryRow> queryRows) {
        this.queryRows = queryRows;
    }
}
