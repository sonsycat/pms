package com.zjry.web.controller.system;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.vo.database.FieldVo;
import com.zjry.system.domain.SysUserDefinedQuery;
import com.zjry.system.domain.vo.UserDefinedQueryVo;
import com.zjry.system.service.ISysUserDefinedQueryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 自定义查询Controller
 *
 * @author lf
 * @date 2021-07-01
 */
@RestController
@RequestMapping("/system/userDefinedQuery")
@Api(tags = {"【自定义查询】"})
public class SysUserDefinedQueryController extends BaseController {
    @Autowired
    private ISysUserDefinedQueryService sysUserDefinedQueryService;

    /**
     * 通过表名查询字段
     */
    @GetMapping("/getUserDefinedQueryField")
    @ApiOperation("通过表名查询字段")
    public AjaxResult getUserDefinedQueryField(String tableName) {
        List<FieldVo> fieldVoList = null;
        try {
            fieldVoList = sysUserDefinedQueryService.getUserDefinedQueryField(tableName);
            return AjaxResult.success(fieldVoList);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }

    }


    @PostMapping("/userDefinedQueryDataList")
    @ApiOperation("自定义查询获取业务数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo userDefinedQueryDataList(@RequestBody UserDefinedQueryVo userDefinedQueryVo) {
        Map map = null;
        try {
            map = sysUserDefinedQueryService.userDefinedQueryDataList(userDefinedQueryVo);
            List list = (List)map.get("list");
            TableDataInfo dataTable = getDataTable(list);
            dataTable.setTotal((Integer)map.get("total"));
            return dataTable;
        } catch (Exception e) {
            e.printStackTrace();
            TableDataInfo tableDataInfo = new TableDataInfo();
            tableDataInfo.setCode(500);
            tableDataInfo.setMsg("系统异常");
            return tableDataInfo;
        }

    }

    /**
     * 新增自定义查询
     */
    @PreAuthorize("@ss.hasPermi('system:userDefinedQuery:add')")
    @Log(title = "自定义查询", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增自定义查询")
    public AjaxResult add(@RequestBody SysUserDefinedQuery sysUserDefinedQuery) {
        return toAjax(sysUserDefinedQueryService.save(sysUserDefinedQuery));
    }

    /**
     * 查询自定义查询列表
     */
    @PreAuthorize("@ss.hasPermi('system:userDefinedQuery:list')")
    @GetMapping("/list")
    @ApiOperation("查询自定义查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public AjaxResult list(SysUserDefinedQuery sysUserDefinedQuery) {
        List<SysUserDefinedQuery> list = sysUserDefinedQueryService.selectSysUserDefinedQueryList(sysUserDefinedQuery);
        return AjaxResult.success(list);
    }

    /**
     * 删除自定义查询
     */
    @PreAuthorize("@ss.hasPermi('system:userDefinedQuery:remove')")
    @Log(title = "自定义查询", businessType = BusinessType.DELETE)
    @DeleteMapping("/{queryIds}")
    @ApiOperation("删除自定义查询")
    public AjaxResult remove(@PathVariable Long[] queryIds) {
        return toAjax(sysUserDefinedQueryService.removeByIds(Arrays.asList(queryIds)));
    }

}
