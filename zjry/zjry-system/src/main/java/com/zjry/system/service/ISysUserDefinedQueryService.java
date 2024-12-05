package com.zjry.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.vo.database.FieldVo;
import com.zjry.system.domain.SysUserDefinedQuery;
import com.zjry.system.domain.vo.UserDefinedQueryVo;

import java.util.List;
import java.util.Map;

/**
 * 自定义查询Service接口
 * 
 * @author lf
 * @date 2021-07-01
 */
public interface ISysUserDefinedQueryService extends IService<SysUserDefinedQuery> {

    List<SysUserDefinedQuery> selectSysUserDefinedQueryList(SysUserDefinedQuery sysUserDefinedQuery);

    List<FieldVo> getUserDefinedQueryField(String tableName) throws Exception;

    Map userDefinedQueryDataList(UserDefinedQueryVo userDefinedQueryVo) throws Exception;
}
