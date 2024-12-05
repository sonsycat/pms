package com.zjry.pms.basic.controller;

import com.zjry.common.core.controller.BaseController;
import com.zjry.pms.basic.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author mk
 * @title: AssetsBasicPublicInterface
 * @projectName zjry-spd
 * @date 2022/2/2114:10
 */

@RestController
@CrossOrigin
@RequestMapping("/api/publicInterface")
@Api(tags={"【对外接口】"})
public class AssetsBasicPublicInterface extends BaseController {
    @Autowired
    private ISpdSysUserService spdSysUserService;


    /**
     * 查询物资字典列表
     */
    @GetMapping("/querySystemValue")
    @ApiOperation("查系统参数列表")
    public String querySystemValue(String str) {
        return  spdSysUserService.querySystemValue(str);
    }

}
