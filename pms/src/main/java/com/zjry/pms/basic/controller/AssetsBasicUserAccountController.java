package com.zjry.pms.basic.controller;

import java.util.List;
import io.swagger.annotations.*;
import org.apache.commons.lang3.ObjectUtils;
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
import com.zjry.common.enums.BusinessType;
import com.zjry.pms.basic.domain.AssetsBasicUserAccount;
import com.zjry.pms.basic.service.IAssetsBasicUserAccountService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 人员账套对应关系Controller
 *
 * @author maozt
 * @date 2022-03-18
 */
@RestController
@RequestMapping("/basic/relationUserAccount")
@Api(tags={"【人员账套对应关系】"})
public class AssetsBasicUserAccountController extends BaseController {
    @Autowired
    private IAssetsBasicUserAccountService assetsBasicUserAccountService;

    /**
     * 查询人员账套对应关系列表
     */
    @PreAuthorize("@ss.hasPermi('basic:relationUserAccount:list')")
    @GetMapping("/list")
    @ApiOperation("查询人员账套对应关系列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(AssetsBasicUserAccount assetsBasicUserAccount) {
        startPage();
        List<AssetsBasicUserAccount> list = assetsBasicUserAccountService.selectAssetsBasicUserAccountList(assetsBasicUserAccount);
        return getDataTable(list);
    }

    /**
     * 导出人员账套对应关系列表
     */
    @PreAuthorize("@ss.hasPermi('basic:relationUserAccount:export')")
    @Log(title = "人员账套对应关系", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出人员账套对应关系列表Excel")
    public AjaxResult export(AssetsBasicUserAccount assetsBasicUserAccount) {
        List<AssetsBasicUserAccount> list = assetsBasicUserAccountService.selectAssetsBasicUserAccountList(assetsBasicUserAccount);
        ExcelUtil<AssetsBasicUserAccount> util = new ExcelUtil<AssetsBasicUserAccount>(AssetsBasicUserAccount.class);
        return util.exportExcel(list, "人员账套对应关系数据");
    }

    /**
     * 获取人员账套对应关系详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:relationUserAccount:query')")
    @GetMapping(value = "/{accountId}")
    @ApiOperation("获取人员账套对应关系详细信息")
    public AjaxResult getInfo(@PathVariable("accountId") Long accountId) {
        return AjaxResult.success(assetsBasicUserAccountService.selectAssetsBasicUserAccountById(accountId));
    }

    /**
     * 新增人员账套对应关系
     */
    @PreAuthorize("@ss.hasPermi('basic:relationUserAccount:add')")
    @Log(title = "人员账套对应关系", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增人员账套对应关系")
    public AjaxResult add(@RequestBody AssetsBasicUserAccount assetsBasicUserAccount) {
        Long userId = assetsBasicUserAccount.getUserId();
        if (ObjectUtils.isNotEmpty(userId)){
            assetsBasicUserAccountService.deleteAssetsBasicUserAccountByUserId(userId);
        }
        try {
            return toAjax(assetsBasicUserAccountService.insertAssetsBasicUserAccountPl(assetsBasicUserAccount));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 修改人员账套对应关系
     */
    @PreAuthorize("@ss.hasPermi('basic:relationUserAccount:edit')")
    @Log(title = "人员账套对应关系", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改人员账套对应关系")
    public AjaxResult edit(@RequestBody AssetsBasicUserAccount assetsBasicUserAccount) {
        return toAjax(assetsBasicUserAccountService.updateAssetsBasicUserAccount(assetsBasicUserAccount));
    }

    /**
     * 删除人员账套对应关系
     */
    @PreAuthorize("@ss.hasPermi('basic:relationUserAccount:remove')")
    @Log(title = "人员账套对应关系", businessType = BusinessType.DELETE)
    @DeleteMapping("/{accountIds}")
    @ApiOperation("删除人员账套对应关系")
    public AjaxResult remove(@PathVariable Long[] accountIds) {
        return toAjax(assetsBasicUserAccountService.deleteAssetsBasicUserAccountByIds(accountIds));
    }
}
