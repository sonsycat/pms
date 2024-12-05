package com.zjry.pms.system.controller;

import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.domain.entity.SysDictData;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.pms.system.domain.PmsSystemStaff;
import com.zjry.pms.system.service.IPmsSystemStaffService;
import com.zjry.system.service.ISysDictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 奖金导入Controller
 *
 * @author zjry
 * @date 2022-10-19
 */
@RestController
@RequestMapping("/system/staff")
@Api(tags = {"【员工】"})
public class PmsSystemStaffController extends BaseController {

    @Autowired
    private IPmsSystemStaffService pmsSystemStaffService;
    @Autowired
    private ISysDictTypeService dictTypeService;


    @PreAuthorize("@ss.hasPermi('system:staff:list')")
    @GetMapping("/list")
    @ApiOperation("查询员工列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsSystemStaff pmsSystemStaff) {
        startPage();
        List<PmsSystemStaff> list = pmsSystemStaffService.selectPmsSystemStaffList(pmsSystemStaff);
        return getDataTable(list);
    }
    @GetMapping(value = "/{id}")
    @ApiOperation("获取员工详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsSystemStaffService.selectPmsSystemStaffById(id));
    }
    @PreAuthorize("@ss.hasPermi('system:staff:add')")
    @Log(title = "新增员工", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增员工")
    public AjaxResult add(@RequestBody PmsSystemStaff param) {
        return toAjax(pmsSystemStaffService.insertPmsSystemStaff(param));
    }
    @PreAuthorize("@ss.hasPermi('system:staff:edit')")
    @Log(title = "修改员工", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改员工")
    public AjaxResult edit(@RequestBody PmsSystemStaff pmsSystemStaff) {
        return toAjax(pmsSystemStaffService.updatePmsSystemStaff(pmsSystemStaff));
    }
    @PreAuthorize("@ss.hasPermi('system:staff:remove')")
    @Log(title = "删除员工数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除员工数据")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsSystemStaffService.deletePmsSystemStaffByIds(ids));
    }
    @PreAuthorize("@ss.hasPermi('bonus:enter:export')")
    @Log(title = "下载员工信息明细模板", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("下载员工信息明细模板")
    public AjaxResult export() {
        List<PmsSystemStaff> list = new ArrayList<>();
        ExcelUtil<PmsSystemStaff> util = new ExcelUtil<PmsSystemStaff>(PmsSystemStaff.class);
        return util.exportExcel(list, "员工信息明细");
    }
    @Log(title = "导出员工信息明细", businessType = BusinessType.EXPORT)
    @GetMapping("/exportDetail")
    @ApiOperation("导出员工信息明细列表Excel")
    public AjaxResult exportDetail(PmsSystemStaff pmsSystemStaff) {
        List<PmsSystemStaff> list = pmsSystemStaffService.selectPmsSystemStaffList(pmsSystemStaff);
        //处理性别和证件类别字典
        if(null != list && list.size() > 0){
            List<SysDictData> sexData = dictTypeService.selectDictDataByType("sys_user_sex");
            List<SysDictData> cardTypeData = dictTypeService.selectDictDataByType("CV02.01.101");
            for (PmsSystemStaff systemStaff : list) {
                List<SysDictData> sexList = sexData.stream().filter(data -> data.getDictValue().equals(systemStaff.getSex())).
                        collect(Collectors.toList());
                if(null != sexList && sexList.size() > 0){
                    systemStaff.setSex(sexList.get(0).getDictLabel());
                }
                List<SysDictData> cardTypeList = cardTypeData.stream().filter(data -> data.getDictValue().equals(systemStaff.getCardType())).
                        collect(Collectors.toList());
                if(null != cardTypeList && cardTypeList.size() > 0){
                    systemStaff.setCardType(cardTypeList.get(0).getDictLabel());
                }
            }

        }
        ExcelUtil<PmsSystemStaff> util = new ExcelUtil<PmsSystemStaff>(PmsSystemStaff.class);
        return util.exportExcel(list, "员工信息明细");
    }
    @PostMapping("/importData")
    @ApiOperation("导入奖金")
    @ResponseBody
    public AjaxResult importData(MultipartFile file) {
        ExcelUtil<PmsSystemStaff> util = new ExcelUtil<PmsSystemStaff>(PmsSystemStaff.class);
        List<PmsSystemStaff> pmsSystemStaffsFiles = null;
        try {
            pmsSystemStaffsFiles = util.importExcel(file.getInputStream());
            pmsSystemStaffsFiles.removeAll(Collections.singleton(null)); //移除所有的null元素
            if (null != pmsSystemStaffsFiles && pmsSystemStaffsFiles.size() == 0) {
                return error("导入失败:excle格式转换失败");
            } else {
                int i = pmsSystemStaffService.importData(pmsSystemStaffsFiles);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return error("导入失败：模板异常！");
        }
        return success();
    }
}
