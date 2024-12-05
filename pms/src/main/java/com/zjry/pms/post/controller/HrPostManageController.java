package com.zjry.pms.post.controller;

import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.StringUtils;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.pms.post.domain.HrPostManage;
import com.zjry.pms.post.domain.HrPostManageVo;
import com.zjry.pms.post.service.IHrPostManageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 岗位管理Controller
 *
 * @author mk
 * @date 2021-07-23
 */
@RestController
@RequestMapping("/manage/manage")
@Api(tags={"【岗位管理】"})
public class HrPostManageController extends BaseController {
    @Autowired
    private IHrPostManageService hrPostManageService;

    /**
     * 查询岗位管理列表
     */
    @PreAuthorize("@ss.hasPermi('manage:manage:list')")
    @GetMapping("/list")
    @ApiOperation("查询岗位管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(HrPostManage hrPostManage) {
        startPage();
        List<HrPostManage> list = hrPostManageService.selectHrPostManageList(hrPostManage);
        return getDataTable(list);
    }

    /**
     * 查询所有
     */
    @PreAuthorize("@ss.hasPermi('manage:manage:list')")
    @GetMapping("/postInfo")
    @ApiOperation("查询岗位所有")
    public  AjaxResult postInfo() {
        List<HrPostManageVo> data = hrPostManageService.queryAllPostInfo();
        if (StringUtils.isNull(data)) {
            data = new ArrayList();
        }

        return AjaxResult.success(data);
    }

    /**
     * 导出岗位管理列表
     */
    @PreAuthorize("@ss.hasPermi('manage:manage:export')")
    @Log(title = "岗位管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出岗位管理列表Excel")
    public AjaxResult export(HrPostManage hrPostManage) {
        List<HrPostManage> list = hrPostManageService.selectHrPostManageList(hrPostManage);
        ExcelUtil<HrPostManage> util = new ExcelUtil<HrPostManage>(HrPostManage.class);
        return util.exportExcel(list, "岗位管理数据");
    }

    /**
     * 获取岗位管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:manage:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取岗位管理详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(hrPostManageService.selectHrPostManageById(id));
    }

    /**
     * 新增岗位管理
     */
    @PreAuthorize("@ss.hasPermi('manage:manage:add')")
    @Log(title = "岗位管理", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增岗位管理")
    public AjaxResult add(@RequestBody HrPostManage hrPostManage) {
        return toAjax(hrPostManageService.insertHrPostManage(hrPostManage));
    }

    /**
     * 修改岗位管理
     */
    @PreAuthorize("@ss.hasPermi('manage:manage:edit')")
    @Log(title = "岗位管理", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改岗位管理")
    public AjaxResult edit(@RequestBody HrPostManage hrPostManage) {
        return toAjax(hrPostManageService.updateHrPostManage(hrPostManage));
    }

    /**
     * 删除岗位管理
     */
    @PreAuthorize("@ss.hasPermi('manage:manage:remove')")
    @Log(title = "岗位管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除岗位管理")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(hrPostManageService.deleteHrPostManageByIds(ids));
    }
}
