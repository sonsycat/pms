package com.zjry.pms.basic.controller;


import com.zjry.common.annotation.Log;
import com.zjry.common.constant.UserConstants;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.pms.basic.domain.basicData.BasicdataDeptRoom;
import com.zjry.pms.basic.domain.basicData.BasicdataDeptRoomVo;
import com.zjry.pms.basic.domain.basicData.BasicdataUser;
import com.zjry.pms.basic.service.IBasicdataDeptRoomService;
import com.zjry.pms.basic.utils.BasicDataUserUtils;
import com.zjry.pms.basic.utils.ReturnUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 科室Controller
 *
 * @author cyq
 * @date 2021-07-16
 */
@RestController
@RequestMapping("/basic/deptRoom")
@Api(tags={"【科室】"})
public class BasicdataDeptRoomController extends BaseController {
    @Autowired
    private IBasicdataDeptRoomService basicdataDeptRoomService;

    /**
     * 查询科室列表
     */
    @GetMapping("/list")
    @ApiOperation("查询科室列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list( BasicdataDeptRoom basicdataDeptRoom) {
        if(basicdataDeptRoom.getDeptId()==null){
            BasicdataUser user = BasicDataUserUtils.getUserWithRelations();
            basicdataDeptRoom.setDeptId(user.getDeptId());
        }
        startPage();
        List<BasicdataDeptRoom> list = basicdataDeptRoomService.selectBasicdataDeptRoomList(basicdataDeptRoom);
        return getDataTable(list);
    }

    /**
     * 导出科室列表
     */
    @Log(title = "科室", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出科室列表Excel")
    public AjaxResult export(BasicdataDeptRoom basicdataDeptRoom) {
        List<BasicdataDeptRoom> list = basicdataDeptRoomService.selectBasicdataDeptRoomList(basicdataDeptRoom);
        ExcelUtil<BasicdataDeptRoom> util = new ExcelUtil<BasicdataDeptRoom>(BasicdataDeptRoom.class);
        return util.exportExcel(list, "科室数据");
    }

    /**
     * 获取科室详细信息
     */
    @GetMapping(value = "/{deptRoomId}")
    @ApiOperation("获取科室详细信息")
    public AjaxResult getInfo(@PathVariable("deptRoomId") Long deptRoomId) {
        return AjaxResult.success(basicdataDeptRoomService.selectBasicdataDeptRoomById(deptRoomId));
    }

    /**
     * 新增科室
     */
    @Log(title = "科室", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增科室")
    public AjaxResult add(@RequestBody BasicdataDeptRoom basicdataDeptRoom) {
        if (UserConstants.NOT_UNIQUE.equals(basicdataDeptRoomService.checkDeptRoomNameUnique(basicdataDeptRoom))) {
            return AjaxResult.error("新增科室'" + basicdataDeptRoom.getDeptRoomName() + "'失败，科室名称已存在" );
        }
        int i = basicdataDeptRoomService.insertBasicdataDeptRoom(basicdataDeptRoom);
        return ReturnUtils.returnMsg(i,"新增科室");
    }

    /**
     * 修改科室
     */
    @Log(title = "科室", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改科室")
    public AjaxResult edit(@RequestBody BasicdataDeptRoom basicdataDeptRoom) {
        if (UserConstants.NOT_UNIQUE.equals(basicdataDeptRoomService.checkDeptRoomNameUnique(basicdataDeptRoom))) {
            return AjaxResult.error("修改科室'" + basicdataDeptRoom.getDeptRoomName() + "'失败，科室名称已存在" );
        }
        int i = basicdataDeptRoomService.updateBasicdataDeptRoom(basicdataDeptRoom);
        return ReturnUtils.returnMsg(i,"修改科室");
    }

    /**
     * 删除科室
     */
    @Log(title = "科室", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deptRoomId}")
    @ApiOperation("删除科室")
    public AjaxResult remove(@PathVariable Long deptRoomId) {
        if (basicdataDeptRoomService.hasChildByDeptRoomId(deptRoomId)) {
            return AjaxResult.error("存在下级科室,不允许删除" );
        }
        if (basicdataDeptRoomService.checkExistUser(deptRoomId)) {
            return AjaxResult.error("部门存在员工,不允许删除" );
        }
        int i = basicdataDeptRoomService.deleteBasicdataDeptRoomById(deptRoomId);
        return ReturnUtils.returnMsg(i,"删除科室");
    }

    /**
     * 获取科室下拉树列表
     */
    @GetMapping("/deptRoomTree" )
    @ApiOperation("获取科室下拉树列表" )
    public AjaxResult deptRoomTree(BasicdataDeptRoomVo basicdataDeptRoom) {
        List<BasicdataDeptRoomVo> depts = basicdataDeptRoomService.selectDeptRoomList(basicdataDeptRoom);
        return AjaxResult.success(basicdataDeptRoomService.buildDeptRoomTree(depts));
    }
    /**
     * 获取科室下列表 【通知公告】
     */
    @GetMapping("/deptRoomListForNotice" )
    @ApiOperation("获取科室下列表 【通知公告】" )
    public AjaxResult deptRoomListForNotice(BasicdataDeptRoom basicdataDeptRoom) {
        List<BasicdataDeptRoom> list = basicdataDeptRoomService.selectBasicdataDeptRoomListForNotice(basicdataDeptRoom);
        return AjaxResult.success(list);
    }
    /**
     * 导入科室
     *
     * @param file 文件信息
     * @return 结果
     */
    @Log(title = "科室管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('basic:deptRoom:import')")
    @PostMapping("/importData")
    @ApiOperation("导入科室数据")
    public AjaxResult importData(MultipartFile file) throws Exception {
        String message = basicdataDeptRoomService.importData(file);
        return AjaxResult.success(message);
    }

}
