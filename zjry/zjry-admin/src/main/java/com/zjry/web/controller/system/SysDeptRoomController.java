package com.zjry.web.controller.system;

import java.util.Arrays;
import java.util.List;

import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.domain.model.LoginUser;
import com.zjry.common.enums.BusinessType;
import com.zjry.system.domain.SysDeptRoom;
import com.zjry.system.service.ISysDeptRoomService;
import com.zjry.common.utils.SecurityUtils;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 科室Controller
 *
 * @author lf
 * @date 2021-06-18
 */
@RestController
@RequestMapping("/system/room")
@Api(tags = {"【科室管理】"})
public class SysDeptRoomController extends BaseController {
    @Autowired
    private ISysDeptRoomService sysDeptRoomService;

    /**
     * 查询科室列表
     */
    @PreAuthorize("@ss.hasPermi('system:room:list')")
    @GetMapping("/list")
    @ApiOperation("查询科室列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(SysDeptRoom sysDeptRoom) {
        startPage();
//        LoginUser loginUser = SecurityUtils.getLoginUser();
//    	if(loginUser.getCurrDeptId() != null) {
//    		sysDeptRoom.setDeptId(loginUser.getCurrDeptId());
//    	} else {
//    		sysDeptRoom.setDeptId(loginUser.getUser().getDeptId());
//    	}
        List<SysDeptRoom> list = sysDeptRoomService.selectSysDeptRoomList(sysDeptRoom);
        return getDataTable(list);
    }

    /**
     * 导出科室列表
     */
    @PreAuthorize("@ss.hasPermi('system:room:export')")
    @Log(title = "科室", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出科室列表Excel")
    public AjaxResult export(SysDeptRoom sysDeptRoom) {
        List<SysDeptRoom> list = sysDeptRoomService.selectSysDeptRoomList(sysDeptRoom);
        ExcelUtil<SysDeptRoom> util = new ExcelUtil<SysDeptRoom>(SysDeptRoom.class);
        return util.exportExcel(list, "科室数据");
    }

    /**
     * 获取科室详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:room:query')")
    @GetMapping(value = "/{deptRoomId}")
    @ApiOperation("获取科室详细信息")
    public AjaxResult getInfo(@PathVariable("deptRoomId") Long deptRoomId) {
        return AjaxResult.success(sysDeptRoomService.getById(deptRoomId));
    }

    /**
     * 新增科室
     */
    @PreAuthorize("@ss.hasPermi('system:room:add')")
    @Log(title = "科室", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增科室")
    public AjaxResult add(@RequestBody SysDeptRoom sysDeptRoom) {
        return toAjax(sysDeptRoomService.save(sysDeptRoom));
    }

    /**
     * 修改科室
     */
    @PreAuthorize("@ss.hasPermi('system:room:edit')")
    @Log(title = "科室", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改科室")
    public AjaxResult edit(@RequestBody SysDeptRoom sysDeptRoom) {
        return toAjax(sysDeptRoomService.updateById(sysDeptRoom));
    }

    /**
     * 删除科室
     */
    @PreAuthorize("@ss.hasPermi('system:room:remove')")
    @Log(title = "科室", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deptRoomIds}")
    @ApiOperation("删除科室")
    public AjaxResult remove(@PathVariable Long[] deptRoomIds) {
        return toAjax(sysDeptRoomService.removeByIds(Arrays.asList(deptRoomIds)));
    }

    /**
     * 通过机构id查询科室数据
     */
    @GetMapping("/getDeptRoomListByDeptId")
    @ApiOperation("通过机构id查询科室数据")
    public AjaxResult getDeptRoomListByDeptId(Long deptId) {
        List<SysDeptRoom> sysDeptRoomList = sysDeptRoomService.getDeptRoomListByDeptId(deptId);
        return AjaxResult.success(sysDeptRoomList);
    }
}
