package com.zjry.pms.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/sso/yml" )
@Api(tags = {"【application.yml 配置文件信息】" })
public class SsoYmlController extends BaseController {

    @Autowired
    private SsoCas basicdataCas;

    /**
     * 查询application.yml配置文件中cas参数
     */
    @RequestMapping(value = "/cas" , method = RequestMethod.GET)
    @ApiOperation("查询配置文件中cas参数" )
    public AjaxResult cas() {
        return AjaxResult.success(basicdataCas);
    } 

}
