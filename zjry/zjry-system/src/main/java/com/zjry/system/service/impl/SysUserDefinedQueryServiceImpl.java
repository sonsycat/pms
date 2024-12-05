package com.zjry.system.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjry.common.constant.DataBaseConstants;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.utils.DateUtils;
import com.zjry.common.utils.StringUtils;
import com.zjry.common.utils.database.DataBaseUtils;
import com.zjry.common.vo.database.FieldVo;
import com.zjry.system.domain.vo.QueryRow;
import com.zjry.system.domain.vo.UserDefinedQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zjry.system.mapper.SysUserDefinedQueryMapper;
import com.zjry.system.domain.SysUserDefinedQuery;
import com.zjry.system.service.ISysUserDefinedQueryService;

/**
 * 自定义查询Service业务层处理
 *
 * @author lf
 * @date 2021-07-01
 */
@Service
public class SysUserDefinedQueryServiceImpl extends ServiceImpl<SysUserDefinedQueryMapper, SysUserDefinedQuery> implements ISysUserDefinedQueryService {
    @Autowired
    private SysUserDefinedQueryMapper sysUserDefinedQueryMapper;
    @Autowired
    private DataBaseUtils dataBaseUtils;

    @Override
    public List<FieldVo> getUserDefinedQueryField(String tableName) throws Exception {
        return dataBaseUtils.getUserDefinedQueryField(tableName);
    }

    @Override
    public Map userDefinedQueryDataList(UserDefinedQueryVo userDefinedQueryVo) throws Exception {
        // 查询语句、查询总数语句
        String querySqlStr = "", totalSqlStr = "";
        String space = " ";
        // 1.拼装querySql=>select * from tableName where a='1' and b<2   totalSql=>select count(0) from tableName where a='1' and b<2
        StringBuffer querySql = new StringBuffer("select * from ");
        querySql.append(userDefinedQueryVo.getTableName());
        StringBuffer totalSql = new StringBuffer("select count(0) from ");
        totalSql.append(userDefinedQueryVo.getTableName());
        // 2过滤掉queryRows中fieldName/fieldConnect/fieldValue为空的数据
        List<QueryRow> queryRows = userDefinedQueryVo.getQueryRows().stream().filter(
                item -> (StringUtils.isNotEmpty(item.getFieldName()) &&
                        StringUtils.isNotEmpty(item.getFieldConnect()) &&
                        StringUtils.isNotEmpty(item.getFieldValue()))
        ).collect(Collectors.toList());
        StringBuffer centreSql = new StringBuffer();
        // 3.继续拼装querySql where条件后的语句
        if (queryRows.size() > 0) {
            centreSql.append(" where (");
            queryRows.forEach(item -> {
                String fieldValue = item.getFieldValue();
                String fieldConnect = item.getFieldConnect();
                String fieldType = item.getFieldType();
                if (fieldType.equals(DataBaseConstants.FieldEnum.VARCHAR.getCode()) ||
                        fieldType.equals(DataBaseConstants.FieldEnum.CHAR.getCode()) ||
                        fieldType.equals(DataBaseConstants.FieldEnum.DATETIME.getCode())) {
                    if (fieldConnect.equals(DataBaseConstants.FieldConnectEnum.LIKE.getCode())) {
                        fieldValue = "'%" + fieldValue + "%'";
                    } else if (fieldConnect.equals(DataBaseConstants.FieldConnectEnum.BEGIN_WITH.getCode())) {
                        fieldValue = "'" + fieldValue + "%'";
                    } else if (fieldConnect.equals(DataBaseConstants.FieldConnectEnum.END_WITH.getCode())) {
                        fieldValue = "'%" + fieldValue + "'";
                    } else {
                        fieldValue = "'" + fieldValue + "'";
                    }
                }
                // 根据不同的连接符拼装不同的sql
                if (fieldConnect.equals(DataBaseConstants.FieldConnectEnum.EQ.getCode())) {
                    centreSql.append(item.getFieldName()).append(DataBaseConstants.FieldConnectEnum.EQ.getValue())
                            .append(fieldValue).append(space).append(userDefinedQueryVo.getFilterCondition()).append(space);
                } else if (fieldConnect.equals(DataBaseConstants.FieldConnectEnum.NE.getCode())) {
                    centreSql.append(item.getFieldName()).append(DataBaseConstants.FieldConnectEnum.NE.getValue())
                            .append(fieldValue).append(space).append(userDefinedQueryVo.getFilterCondition()).append(space);
                } else if (fieldConnect.equals(DataBaseConstants.FieldConnectEnum.GT.getCode())) {
                    centreSql.append(item.getFieldName()).append(DataBaseConstants.FieldConnectEnum.GT.getValue())
                            .append(fieldValue).append(space).append(userDefinedQueryVo.getFilterCondition()).append(space);
                } else if (fieldConnect.equals(DataBaseConstants.FieldConnectEnum.LT.getCode())) {
                    centreSql.append(item.getFieldName()).append(DataBaseConstants.FieldConnectEnum.LT.getValue())
                            .append(fieldValue).append(space).append(userDefinedQueryVo.getFilterCondition()).append(space);
                } else if (fieldConnect.equals(DataBaseConstants.FieldConnectEnum.GE.getCode())) {
                    centreSql.append(item.getFieldName()).append(DataBaseConstants.FieldConnectEnum.GE.getValue())
                            .append(fieldValue).append(space).append(userDefinedQueryVo.getFilterCondition()).append(space);
                } else if (fieldConnect.equals(DataBaseConstants.FieldConnectEnum.LE.getCode())) {
                    centreSql.append(item.getFieldName()).append(DataBaseConstants.FieldConnectEnum.LE.getValue())
                            .append(fieldValue).append(space).append(userDefinedQueryVo.getFilterCondition()).append(space);
                } else if (fieldConnect.equals(DataBaseConstants.FieldConnectEnum.LIKE.getCode())) {
                    centreSql.append(item.getFieldName()).append(DataBaseConstants.FieldConnectEnum.LIKE.getValue())
                            .append(fieldValue).append(space).append(userDefinedQueryVo.getFilterCondition()).append(space);
                } else if (fieldConnect.equals(DataBaseConstants.FieldConnectEnum.BEGIN_WITH.getCode())) {
                    centreSql.append(item.getFieldName()).append(DataBaseConstants.FieldConnectEnum.BEGIN_WITH.getValue())
                            .append(fieldValue).append(space).append(userDefinedQueryVo.getFilterCondition()).append(space);
                } else if (fieldConnect.equals(DataBaseConstants.FieldConnectEnum.END_WITH.getCode())) {
                    centreSql.append(item.getFieldName()).append(DataBaseConstants.FieldConnectEnum.END_WITH.getValue())
                            .append(fieldValue).append(space).append(userDefinedQueryVo.getFilterCondition()).append(space);
                }
            });
            centreSql.append(")");
        }
        // 4.移除多余的" and "字段 添加del_flag=0
        if (centreSql.toString().endsWith(userDefinedQueryVo.getFilterCondition() + " )")) {
            String centreSqlStr = centreSql.substring(0, centreSql.length() - userDefinedQueryVo.getFilterCondition().length() - 3);
            querySql.append(centreSqlStr).append(")");
            if (userDefinedQueryVo.getCheckDelFlag()) {
                querySql.append(" and del_flag=0");
                totalSqlStr = totalSql.append(centreSqlStr).append(")").append(" and del_flag=0").toString();
            } else {
                totalSqlStr = totalSql.append(centreSqlStr).append(")").toString();
            }
        } else {
            if (userDefinedQueryVo.getCheckDelFlag()) {
                centreSql.append(" where del_flag=0");
                querySql.append(centreSql);
                totalSqlStr = totalSql.append(centreSql).toString();
            } else {
                totalSqlStr = totalSql.toString();
            }
        }
        // 5.querySqlStr添加分页 limit (pageNum-1)*pageSize,pageNum*pageSize-1
        querySql.append(" limit ")
                .append((userDefinedQueryVo.getPageNum() - 1) * userDefinedQueryVo.getPageSize())
                .append(",")
                .append(userDefinedQueryVo.getPageNum() * userDefinedQueryVo.getPageSize() - 1);
        querySqlStr = querySql.toString();
        // 6.查询数据
        return dataBaseUtils.userDefinedQueryList(querySqlStr, totalSqlStr);
    }

    /**
     * 查询自定义查询S列表
     *
     * @param sysUserDefinedQuery 查询对象
     * @return SysUserDefinedQuery
     */
    @Override
    public List<SysUserDefinedQuery> selectSysUserDefinedQueryList(SysUserDefinedQuery sysUserDefinedQuery) {
        LambdaQueryWrapper<SysUserDefinedQuery> queryWrapper = new LambdaQueryWrapper();
        queryWrapper
                .eq(StringUtils.isNotNull(sysUserDefinedQuery.getQueryIndex()), SysUserDefinedQuery::getQueryIndex, sysUserDefinedQuery.getQueryIndex())
                .orderByAsc(SysUserDefinedQuery::getCreateTime);
        return this.list(queryWrapper);
    }

}
