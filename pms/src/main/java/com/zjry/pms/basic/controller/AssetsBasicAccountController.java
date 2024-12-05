package com.zjry.pms.basic.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
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
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.pms.basic.domain.AssetsBasicAccount;
import com.zjry.pms.basic.domain.AssetsBasicAccountPeriod;
import com.zjry.pms.basic.service.IAssetsBasicAccountPeriodService;
import com.zjry.pms.basic.service.IAssetsBasicAccountService;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 账套管理Controller
 *
 * @author maozt
 * @date 2021-08-05
 */
@RestController
@RequestMapping("/basic/account")
@Api(tags={"【账套管理】"})
public class AssetsBasicAccountController extends BaseController {
    @Autowired
    private IAssetsBasicAccountService assetsBasicAccountService;
    @Autowired
    private IAssetsBasicAccountPeriodService assetsBasicAccountPeriodService;

    /**
     * 查询账套管理列表
     */
    @PreAuthorize("@ss.hasPermi('basic:account:list')")
    @GetMapping("/list")
    @ApiOperation("查询账套管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(AssetsBasicAccount assetsBasicAccount) {
        startPage();
        List<AssetsBasicAccount> list = assetsBasicAccountService.selectAssetsBasicAccountList(assetsBasicAccount);
        return getDataTable(list);
    }

    /**
     * 导出账套管理列表
     */
    @PreAuthorize("@ss.hasPermi('basic:account:export')")
    @Log(title = "账套管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出账套管理列表Excel")
    public AjaxResult export(AssetsBasicAccount assetsBasicAccount) {
        List<AssetsBasicAccount> list = assetsBasicAccountService.selectAssetsBasicAccountList(assetsBasicAccount);
        ExcelUtil<AssetsBasicAccount> util = new ExcelUtil<AssetsBasicAccount>(AssetsBasicAccount.class);
        return util.exportExcel(list, "账套管理数据");
    }

    /**
     * 获取账套管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:account:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取账套管理详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(assetsBasicAccountService.selectAssetsBasicAccountById(id));
    }

    /**
     * 新增账套管理
     */
    @PreAuthorize("@ss.hasPermi('basic:account:add')")
    @Log(title = "账套管理", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增账套管理")
    public AjaxResult add(@Validated @RequestBody AssetsBasicAccount assetsBasicAccount) throws ParseException {
        if(validDuplicateName(assetsBasicAccount)){
            return AjaxResult.error("账套名称已存在");
        }
        //年份处理
        if (!ObjectUtils.isEmpty(assetsBasicAccount.getStartYear())) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            Date date = format.parse(assetsBasicAccount.getStartYear().replace("Z", " UTC"));
            Calendar cal=Calendar.getInstance();
            cal.setTime(date);
            assetsBasicAccount.setStartYear(String.valueOf(cal.get(Calendar.YEAR)));
        }
        //月份处理
        if (!ObjectUtils.isEmpty(assetsBasicAccount.getStartMonth())) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            Date date = format.parse(assetsBasicAccount.getStartMonth().replace("Z", " UTC"));
            Calendar cal=Calendar.getInstance();
            cal.setTime(date);
            assetsBasicAccount.setStartMonth(String.valueOf(cal.get(Calendar.YEAR))+"-"+String.valueOf(cal.get(Calendar.MONTH) + 1));
        }
        return toAjax(assetsBasicAccountService.insertAssetsBasicAccount(assetsBasicAccount));
    }

    /**
     * 修改账套管理
     */
    @PreAuthorize("@ss.hasPermi('basic:account:edit')")
    @Log(title = "账套管理", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改账套管理")
    public AjaxResult edit(@Validated @RequestBody AssetsBasicAccount assetsBasicAccount) throws ParseException {
        if(validDuplicateName(assetsBasicAccount)){
            return AjaxResult.error("账套名称已存在");
        }
        //年份处理
        if (!ObjectUtils.isEmpty(assetsBasicAccount.getStartYear()) &&
                !assetsBasicAccount.getStartYear().equals(assetsBasicAccountService.selectAssetsBasicAccountById(assetsBasicAccount.getId()).getStartYear())) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            Date date = format.parse(assetsBasicAccount.getStartYear().replace("Z", " UTC"));
            Calendar cal=Calendar.getInstance();
            cal.setTime(date);
            assetsBasicAccount.setStartYear(String.valueOf(cal.get(Calendar.YEAR)));
        }
        //月份处理
        if (!ObjectUtils.isEmpty(assetsBasicAccount.getStartMonth()) &&
                !assetsBasicAccount.getStartMonth().equals(assetsBasicAccountService.selectAssetsBasicAccountById(assetsBasicAccount.getId()).getStartMonth())) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            Date date = format.parse(assetsBasicAccount.getStartMonth().replace("Z", " UTC"));
            Calendar cal=Calendar.getInstance();
            cal.setTime(date);
            assetsBasicAccount.setStartMonth(String.valueOf(cal.get(Calendar.YEAR))+"-"+String.valueOf(cal.get(Calendar.MONTH) + 1));
        }

        return toAjax(assetsBasicAccountService.updateAssetsBasicAccount(assetsBasicAccount));
    }

    /**
     * 删除账套管理
     */
    @PreAuthorize("@ss.hasPermi('basic:account:remove')")
    @Log(title = "账套管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除账套管理")
    public AjaxResult remove(@PathVariable Long[] ids) {
        for (Long id : ids) {
            AssetsBasicAccount assetsBasicAccount = assetsBasicAccountService.selectAssetsBasicAccountById(id);
            String accountCode = assetsBasicAccount.getAccountCode();
            if (assetsBasicAccountService.selectUserAccountRelationByAccountCode(accountCode) != 0) {
                return AjaxResult.error("账套已有用户绑定不能删除");
            }

            AssetsBasicAccountPeriod assetsBasicAccountPeriod = new AssetsBasicAccountPeriod();
            assetsBasicAccountPeriod.setAccountCode(accountCode);
            if (assetsBasicAccountPeriodService.selectAssetsBasicAccountPeriodList(assetsBasicAccountPeriod).size() != 0 ) {
                return AjaxResult.error("账套已经有账期不能删除");
            }

        }
        return toAjax(assetsBasicAccountService.deleteAssetsBasicAccountByIds(ids));
    }

    /**
     * 根据用户编码查询关联仓库管理列表
     * isUser 1 关联用户 2 非关联用户
     * userCode 关联用户编码
     */
    @PreAuthorize("@ss.hasPermi('basic:account:list')")
    @GetMapping("/listByUser")
    @ApiOperation("根据用户查询关联仓库管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo listByUser(AssetsBasicAccount assetsBasicAccount) {
        startPage();
        List<AssetsBasicAccount> list = assetsBasicAccountService.selectAssetsBasicAccountListByUserCode(assetsBasicAccount);
        return getDataTable(list);
    }

    /**
     * 名称重复判断
     * @param assetsBasicAccount
     * @return
     */
    private boolean validDuplicateName(AssetsBasicAccount assetsBasicAccount){
        if(StringUtils.isNotBlank(assetsBasicAccount.getAccountName())){
            assetsBasicAccount.setAccountName(assetsBasicAccount.getAccountName().trim());
        }
        QueryWrapper<AssetsBasicAccount> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(AssetsBasicAccount::getId,AssetsBasicAccount::getAccountName)
                .eq(AssetsBasicAccount::getAccountName, assetsBasicAccount.getAccountName());
        if(assetsBasicAccount.getId() != null){
            wrapper.lambda().ne(AssetsBasicAccount::getId, assetsBasicAccount.getId());
        }
        wrapper.last("LIMIT 1");
        AssetsBasicAccount one = assetsBasicAccountService.getOne(wrapper);
        if(one != null){
            return true;
        }
        return false;
    }
}
