package com.zjry.pms.process.controller;

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
import com.zjry.common.core.domain.BaseEntity;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.pms.process.domain.HrProcessModel;
import com.zjry.pms.process.service.IHrProcessModelService;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 流程模块Controller
 *
 * @author yzh
 * @date 2021-07-26
 */
@RestController
@RequestMapping("/process/model")
@Api(tags={"【流程模块】"})
public class HrProcessModelController extends BaseController {
    @Autowired
    private IHrProcessModelService hrProcessModelService;

    /**
     * 查询流程模块列表
     */
    @PreAuthorize("@ss.hasPermi('process:model:list')")
    @GetMapping("/list")
    @ApiOperation("查询流程模块列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(HrProcessModel hrProcessModel) {
        startPage();
        List<HrProcessModel> list = hrProcessModelService.selectHrProcessModelList(hrProcessModel);
        return getDataTable(list);
    }

    /**
     * 导出流程模块列表
     */
    @PreAuthorize("@ss.hasPermi('process:model:export')")
    @Log(title = "流程模块", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出流程模块列表Excel")
    public AjaxResult export(HrProcessModel hrProcessModel) {
        List<HrProcessModel> list = hrProcessModelService.selectHrProcessModelList(hrProcessModel);
        ExcelUtil<HrProcessModel> util = new ExcelUtil<HrProcessModel>(HrProcessModel.class);
        return util.exportExcel(list, "流程模块数据");
    }

    /**
     * 获取流程模块详细信息
     */
    @PreAuthorize("@ss.hasPermi('process:model:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取流程模块详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(hrProcessModelService.selectHrProcessModelById(id));
    }

    /**
     * 新增流程模块
     */
    @PreAuthorize("@ss.hasPermi('process:model:add')")
    @Log(title = "流程模块", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增流程模块")
    public AjaxResult add(@RequestBody HrProcessModel hrProcessModel) {
    	hrProcessModel.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
    	hrProcessModel.preInsert();
        return toAjax(hrProcessModelService.insertHrProcessModel(hrProcessModel));
    }

    /**
     * 修改流程模块
     */
    @PreAuthorize("@ss.hasPermi('process:model:edit')")
    @Log(title = "流程模块", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改流程模块")
    public AjaxResult edit(@RequestBody HrProcessModel hrProcessModel) {
    	hrProcessModel.preUpdate();
        return toAjax(hrProcessModelService.updateHrProcessModel(hrProcessModel));
    }

    /**
     * 删除流程模块
     */
    @PreAuthorize("@ss.hasPermi('process:model:remove')")
    @Log(title = "流程模块", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除流程模块")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(hrProcessModelService.deleteHrProcessModelByIds(ids));
    }
}
