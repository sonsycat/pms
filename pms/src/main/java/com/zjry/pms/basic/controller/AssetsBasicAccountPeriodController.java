package com.zjry.pms.basic.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.zjry.common.utils.StringUtils;
import com.zjry.pms.common.Constants;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
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
import com.zjry.pms.basic.domain.AssetsBasicAccountPeriod;
import com.zjry.pms.basic.service.IAssetsBasicAccountPeriodService;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 年度帐期间管理Controller
 *
 * @author maozt
 * @date 2021-08-06
 */
@RestController
@RequestMapping("/basic/period")
@Api(tags={"【年度帐期间管理】"})
public class AssetsBasicAccountPeriodController extends BaseController {
    @Autowired
    private IAssetsBasicAccountPeriodService assetsBasicAccountPeriodService;

    /**
     * 查询年度帐期间管理列表
     */
    @PreAuthorize("@ss.hasPermi('basic:period:list')")
    @GetMapping("/list")
    @ApiOperation("查询年度帐期间管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(AssetsBasicAccountPeriod assetsBasicAccountPeriod) throws ParseException {
        startPage();
        List<AssetsBasicAccountPeriod> list = assetsBasicAccountPeriodService.selectAssetsBasicAccountPeriodList(assetsBasicAccountPeriod);
        return getDataTable(list);
    }

    /**
     * 导出年度帐期间管理列表
     */
    @PreAuthorize("@ss.hasPermi('basic:period:export')")
    @Log(title = "年度帐期间管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出年度帐期间管理列表Excel")
    public AjaxResult export(AssetsBasicAccountPeriod assetsBasicAccountPeriod) {
        List<AssetsBasicAccountPeriod> list = assetsBasicAccountPeriodService.selectAssetsBasicAccountPeriodList(assetsBasicAccountPeriod);
        ExcelUtil<AssetsBasicAccountPeriod> util = new ExcelUtil<AssetsBasicAccountPeriod>(AssetsBasicAccountPeriod.class);
        return util.exportExcel(list, "年度帐期间管理数据");
    }

    /**
     * 获取年度帐期间管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('basic:period:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取年度帐期间管理详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(assetsBasicAccountPeriodService.selectAssetsBasicAccountPeriodById(id));
    }

    /**
     * 新增年度帐期间管理
     */
    @PreAuthorize("@ss.hasPermi('basic:period:add')")
    @Log(title = "年度帐期间管理", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增年度帐期间管理")
    public AjaxResult add(@RequestBody AssetsBasicAccountPeriod assetsBasicAccountPeriod) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        Date date = format.parse(assetsBasicAccountPeriod.getAccountYear().replace("Z", " UTC"));
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        assetsBasicAccountPeriod.setAccountYear(String.valueOf(cal.get(Calendar.YEAR)));
        if (assetsBasicAccountPeriodService.selectAssetsBasicAccountPeriodList(assetsBasicAccountPeriod).size() > 0) {
            return error("账套重复");
        }
        if (StringUtils.isNotEmpty(assetsBasicAccountPeriod.getNowPeriod())) {
            AssetsBasicAccountPeriod assetsBasicAccountPeriod1 = new AssetsBasicAccountPeriod();
            assetsBasicAccountPeriod1.setAccountCode(assetsBasicAccountPeriod.getAccountCode());
            assetsBasicAccountPeriod1.setIsPeriod("0");
            if (assetsBasicAccountPeriodService.selectAssetsBasicAccountPeriodList(assetsBasicAccountPeriod1).size() > 0) {
                return error("该账套已有当前期间");
            }
        }
        //修改数量
        int num = 0;

        if (Constants.ACCOUNT_TYPE_NORMAL.equals(assetsBasicAccountPeriod.getType())) {
            //自然月
            num += assetsBasicAccountPeriodService.insertAssetsBasicAccountPeriodNum(assetsBasicAccountPeriod);

        } else if (Constants.ACCOUNT_TYPE_FEEL.equals(assetsBasicAccountPeriod.getType())) {
            //自定义
            num += assetsBasicAccountPeriodService.insertAssetsBasicAccountPeriodNumFeel(assetsBasicAccountPeriod);
        } else {
            return error("类型为空");
        }
//        assetsBasicAccountPeriodService.insertAssetsBasicAccountPeriod(assetsBasicAccountPeriod);
        return toAjax(num);
    }

    /**
     * 修改年度帐期间管理
     */
    @PreAuthorize("@ss.hasPermi('basic:period:edit')")
    @Log(title = "年度帐期间管理", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改年度帐期间管理")
    public AjaxResult edit(@RequestBody AssetsBasicAccountPeriod assetsBasicAccountPeriod) {
        return toAjax(assetsBasicAccountPeriodService.updateAssetsBasicAccountPeriod(assetsBasicAccountPeriod));
    }

    /**
     * 删除年度帐期间管理
     */
    @PreAuthorize("@ss.hasPermi('basic:period:remove')")
    @Log(title = "年度帐期间管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除年度帐期间管理")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(assetsBasicAccountPeriodService.deleteAssetsBasicAccountPeriodByIds(ids));
    }

    /**
     * 删除年度帐期间管理
     */
    @PreAuthorize("@ss.hasPermi('basic:period:remove')")
    @Log(title = "年度帐期间管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/delAll")
    @ApiOperation("根据名称年度删除年度帐期间管理")
    public AjaxResult delAll(@RequestBody AssetsBasicAccountPeriod assetsBasicAccountPeriod) {
        if (ObjectUtils.isEmpty(assetsBasicAccountPeriod.getAccountYear())) {
            return error("年度不能为空");
        }
        return toAjax(assetsBasicAccountPeriodService.deleteAssetsBasicAccountPeriodByNameYear(assetsBasicAccountPeriod));
    }
}
