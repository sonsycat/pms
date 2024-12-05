package com.zjry.web.controller.monitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zjry.common.annotation.Log;
import com.zjry.common.config.ZjRyConfig;
import com.zjry.common.constant.Constants;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.core.redis.RedisCache;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.StringUtils;
import com.zjry.system.domain.SysUserOnline;
import com.zjry.system.service.ISysUserOnlineService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 在线用户监控
 *
 * @author zjry
 */
@RestController
@RequestMapping("/monitor/online" )
@Api(tags = {"【在线用户监控】" })
public class SysUserOnlineController extends BaseController {
    @Autowired
    private ISysUserOnlineService userOnlineService;

    @Autowired
    private RedisCache redisCache;
    
    @Autowired
    private ZjRyConfig zjRyConfig;

    @PreAuthorize("@ss.hasPermi('monitor:online:list')" )
    @GetMapping("/list" )
    @ApiOperation("获取在线用户列表" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum" , value = "当前页码" , dataType = "int" , paramType = "query" , required = false),
            @ApiImplicitParam(name = "pageSize" , value = "每页数据量" , dataType = "int" , paramType = "query" , required = false),
    })
    public TableDataInfo list(String ipaddr, String userName) {
        Collection<String> keys = redisCache.keys(zjRyConfig.getName() + ":" + Constants.LOGIN_TOKEN_KEY + "*" );
        List<SysUserOnline> userOnlineList = new ArrayList<SysUserOnline>();
        for (String key : keys) {
            LoginUser user = redisCache.getCacheObject(key);
            if (StringUtils.isNotEmpty(ipaddr) && StringUtils.isNotEmpty(userName)) {
                if (StringUtils.equals(ipaddr, user.getIpaddr()) && StringUtils.equals(userName, user.getUsername())) {
                    userOnlineList.add(userOnlineService.selectOnlineByInfo(ipaddr, userName, user));
                }
            } else if (StringUtils.isNotEmpty(ipaddr)) {
                if (StringUtils.equals(ipaddr, user.getIpaddr())) {
                    userOnlineList.add(userOnlineService.selectOnlineByIpaddr(ipaddr, user));
                }
            } else if (StringUtils.isNotEmpty(userName) && StringUtils.isNotNull(user.getUser())) {
                if (StringUtils.equals(userName, user.getUsername())) {
                    userOnlineList.add(userOnlineService.selectOnlineByUserName(userName, user));
                }
            } else {
                userOnlineList.add(userOnlineService.loginUserToUserOnline(user));
            }
        }
        List<SysUserOnline> collect = userOnlineList.stream().sorted(Comparator.comparingLong(SysUserOnline::getLoginTime).reversed()).collect(Collectors.toList());
        collect.removeAll(Collections.singleton(null));
        return getDataTable(collect);
    }

    /**
     * 强退用户
     */
    @PreAuthorize("@ss.hasPermi('monitor:online:forceLogout')" )
    @Log(title = "在线用户" , businessType = BusinessType.FORCE)
    @DeleteMapping("/{tokenId}" )
    @ApiOperation("强退用户" )
    public AjaxResult forceLogout(@PathVariable String tokenId) {
        redisCache.deleteObject(zjRyConfig.getName() + ":" + Constants.LOGIN_TOKEN_KEY + tokenId);
        return AjaxResult.success();
    }
}
