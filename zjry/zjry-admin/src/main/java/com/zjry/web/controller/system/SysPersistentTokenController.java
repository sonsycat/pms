package com.zjry.web.controller.system;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.system.domain.SysArea;
import com.zjry.system.domain.SysPersistentToken;
import com.zjry.system.service.ISysPersistentTokenService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 令牌管理控制器
 *
 * @Author: mingc
 * @DateTime: 2021/7/15
 */
@RestController
@RequestMapping("/monitor/persistent-token" )
@Api(tags = {"【令牌管理】" })
public class SysPersistentTokenController extends BaseController {

    @Autowired
    private ISysPersistentTokenService sysPersistentTokenService;

    /**
     * 查询令牌信息列表
     */
    @PreAuthorize("@ss.hasPermi('monitor:persistent:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysPersistentToken sysPersistentToken)
    {
        startPage();
        LambdaQueryWrapper<SysPersistentToken> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(SysPersistentToken::getTokenDate).eq(StrUtil.isNotEmpty(sysPersistentToken.getUsername()),SysPersistentToken::getUsername, sysPersistentToken.getUsername());
        List<SysPersistentToken> list = sysPersistentTokenService.list(lambdaQueryWrapper);
        return getDataTable(list);
    }

    /**
     * 删除令牌信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:persistent:remove')")
    @Log(title = "令牌信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(sysPersistentTokenService.removeByIds(Arrays.asList(ids)));
    }
}
