package com.zjry.web.controller.system;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.system.domain.SysNotice;
import com.zjry.system.service.ISysNoticeService;

/**
 * 公告 信息操作处理
 *
 * @author zjry
 */
@RestController
@RequestMapping("/system/notice" )
@Api(tags = {"【公告】" })
public class SysNoticeController extends BaseController {
    @Autowired
    private ISysNoticeService noticeService;

    /**
     * 获取通知公告列表
     */
    @PreAuthorize("@ss.hasPermi('system:notice:list')" )
    @GetMapping("/list" )
    @ApiOperation("获取通知公告列表" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum" , value = "当前页码" , dataType = "int" , paramType = "query" , required = false),
            @ApiImplicitParam(name = "pageSize" , value = "每页数据量" , dataType = "int" , paramType = "query" , required = false),
    })
    public TableDataInfo list(SysNotice notice) {
        startPage();
        List<SysNotice> list = noticeService.selectNoticeList(notice);
        return getDataTable(list);
    }

    /**
     * 根据通知公告编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:notice:query')" )
    @GetMapping(value = "/{noticeId}" )
    @ApiOperation("根据通知公告编号获取详细信息" )
    public AjaxResult getInfo(@PathVariable Long noticeId) {
        return AjaxResult.success(noticeService.selectNoticeById(noticeId));
    }

    /**
     * 新增通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:add')" )
    @Log(title = "通知公告" , businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增通知公告" )
    public AjaxResult add(@Validated @RequestBody SysNotice notice) {
        notice.setCreateBy(SecurityUtils.getUsername());
        return toAjax(noticeService.insertNotice(notice));
    }

    /**
     * 修改通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:edit')" )
    @Log(title = "通知公告" , businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改通知公告" )
    public AjaxResult edit(@Validated @RequestBody SysNotice notice) {
        notice.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(noticeService.updateNotice(notice));
    }

    /**
     * 删除通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:remove')" )
    @Log(title = "通知公告" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{noticeIds}" )
    @ApiOperation("删除通知公告" )
    public AjaxResult remove(@PathVariable Long[] noticeIds) {
        return toAjax(noticeService.deleteNoticeByIds(noticeIds));
    }
}
