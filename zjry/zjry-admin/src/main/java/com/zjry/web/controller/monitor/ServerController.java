package com.zjry.web.controller.monitor;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.framework.web.domain.Server;

/**
 * 服务器监控
 *
 * @author zjry
 */
@RestController
@RequestMapping("/monitor/server" )
@Api(tags = {"【服务器监控】" })
public class ServerController {
//    @PreAuthorize("@ss.hasPermi('monitor:server:list')" )
    @GetMapping()
    @ApiOperation("获取服务器信息" )
    public AjaxResult getInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        return AjaxResult.success(server);
    }
}
