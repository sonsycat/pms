package com.zjry.advanceQuery.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zjry.advanceQuery.service.ISysAdvanceQueryService;
import com.zjry.advanceQuery.vo.AdvanceQueryVo;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.page.TableDataInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 高级查询Controller
 * @author yangtao
 * @date 2021-10-13
 */
@RestController
@RequestMapping("/advanceQuery")
@Api(tags = {"【高级查询】"})
public class SysAdvanceQueryController extends BaseController {
	
    @Autowired
    private ISysAdvanceQueryService sysAdvanceQueryService;

    @PostMapping("/list")
    @ApiOperation("高级查询入口-获取数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
        @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(@RequestBody AdvanceQueryVo advanceQueryVo) {
    	startPage();
        List<Map> list = sysAdvanceQueryService.list(advanceQueryVo);
        return getDataTable(list);
    }
    
}
