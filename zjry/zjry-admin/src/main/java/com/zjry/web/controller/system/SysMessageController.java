package com.zjry.web.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.system.domain.SysMessage;
import com.zjry.system.service.ISysMessageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 消息管理Controller
 *
 * @author yangtao
 * @date 2021-11-03
 */
@RestController
@RequestMapping("/system/message")
@Api(tags={"【消息管理】"})
public class SysMessageController extends BaseController {
    @Autowired
    private ISysMessageService sysMessageService;

    /**
     * 查询消息管理列表
     */
    @GetMapping("/list")
    @ApiOperation("查询消息管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(SysMessage sysMessage) {
        startPage();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if(loginUser != null) {
        	sysMessage.setReceiveUserId(loginUser.getUser().getUserId());
        }
        List<SysMessage> list = sysMessageService.selectSysMessageList(sysMessage);
        return getDataTable(list);
    }

    /**
     * 导出消息管理列表
     */
    @Log(title = "消息管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出消息管理列表Excel")
    public AjaxResult export(SysMessage sysMessage) {
        List<SysMessage> list = sysMessageService.selectSysMessageList(sysMessage);
        ExcelUtil<SysMessage> util = new ExcelUtil<SysMessage>(SysMessage.class);
        return util.exportExcel(list, "消息管理数据");
    }

    /**
     * 获取消息管理详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取消息管理详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sysMessageService.selectSysMessageById(id));
    }
    
    /**
     * 获取消息管理详细信息
     */
    @GetMapping(value = "/showDetails/{id}")
    @ApiOperation("显示消息详情")
    public AjaxResult showDetails(@PathVariable("id") Long id) {
        return AjaxResult.success(sysMessageService.showDetails(id));
    }

    /**
     * 新增消息管理
     */
    @Log(title = "消息管理", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增消息管理")
    public AjaxResult add(@RequestBody SysMessage sysMessage) {
        return toAjax(sysMessageService.insertSysMessage(sysMessage));
    }

    /**
     * 修改消息管理
     */
    @Log(title = "消息管理", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改消息管理")
    public AjaxResult edit(@RequestBody SysMessage sysMessage) {
        return toAjax(sysMessageService.updateSysMessage(sysMessage));
    }

    /**
     * 删除消息管理
     */
    @Log(title = "消息管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除消息管理")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sysMessageService.deleteSysMessageByIds(ids));
    }
    
    @GetMapping("/queryNotReadMsgNum")
    @ApiOperation("获取未读消息数量")
    public AjaxResult queryNotReadMsgNum() {
    	Long userId = SecurityUtils.getLoginUser().getUser().getUserId();
    	return AjaxResult.success(sysMessageService.queryNotReadMsgNum(userId));
    }
    
}
