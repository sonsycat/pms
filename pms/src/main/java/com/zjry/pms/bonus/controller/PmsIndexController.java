package com.zjry.pms.bonus.controller;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjry.pms.common.utils.UserUtils;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.zjry.pms.bonus.domain.PmsIndex;
import com.zjry.pms.bonus.service.IPmsIndexService;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 指标配置维护Controller
 *
 * @author zjry
 * @date 2022-10-09
 */
@RestController
@RequestMapping("/bonus/index")
@Api(tags={"【指标配置维护】"})
public class PmsIndexController extends BaseController {
    @Autowired
    private IPmsIndexService pmsIndexService;

    /**
     * 查询指标配置维护列表
     */
    @PreAuthorize("@ss.hasPermi('bonus:index:list')")
    @GetMapping("/list")
    @ApiOperation("查询指标配置维护列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsIndex pmsIndex) {
        startPage();
        pmsIndex.setCompCode(UserUtils.getSysUser().getDeptId());
        List<PmsIndex> list = pmsIndexService.selectPmsIndexList(pmsIndex);
        return getDataTable(list);
    }

    /**
     * 导出指标配置维护列表
     */
    @PreAuthorize("@ss.hasPermi('bonus:index:export')")
    @Log(title = "指标配置维护", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出指标配置维护列表Excel")
    public AjaxResult export(PmsIndex pmsIndex) {
        pmsIndex.setCompCode(UserUtils.getSysUser().getDeptId());
        List<PmsIndex> list = pmsIndexService.selectPmsIndexList(pmsIndex);
        ExcelUtil<PmsIndex> util = new ExcelUtil<PmsIndex>(PmsIndex.class);
        return util.exportExcel(list, "指标配置维护数据");
    }

    /**
     * 获取指标配置维护详细信息
     */
    @PreAuthorize("@ss.hasPermi('bonus:index:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取指标配置维护详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsIndexService.selectPmsIndexById(id));
    }

    /**
     * 新增指标配置维护
     */
    @PreAuthorize("@ss.hasPermi('bonus:index:add')")
    @Log(title = "指标配置维护", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增指标配置维护")
    public AjaxResult add(@Validated @RequestBody PmsIndex pmsIndex) {
        if(validDuplicateName(null, pmsIndex.getIndexName())){
            return AjaxResult.error("指标名称已存在");
        }
        return toAjax(pmsIndexService.insertPmsIndex(pmsIndex));
    }

    /**
     * 修改指标配置维护
     */
    @PreAuthorize("@ss.hasPermi('bonus:index:edit')")
    @Log(title = "指标配置维护", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改指标配置维护")
    public AjaxResult edit(@Validated @RequestBody PmsIndex pmsIndex) {
        if(validDuplicateName(pmsIndex.getId(), pmsIndex.getIndexName())){
            return AjaxResult.error("指标名称已存在");
        }
        return toAjax(pmsIndexService.updatePmsIndex(pmsIndex));
    }

    /**
     * 删除指标配置维护
     */
    @PreAuthorize("@ss.hasPermi('bonus:index:remove')")
    @Log(title = "指标配置维护", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除指标配置维护")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsIndexService.deletePmsIndexByIds(ids));
    }

    private boolean validDuplicateName(Long id, String name){
        QueryWrapper<PmsIndex> qw = new QueryWrapper<>();
        qw.select("ID", "INDEX_VALUE", "INDEX_NAME");
        qw.eq("INDEX_NAME", name);
        qw.eq("DEL_FLAG", PmsIndex.DEL_FLAG_NORMAL);
        qw.last("LIMIT 1");
        PmsIndex index = pmsIndexService.getOne(qw);
        if(index != null && id != null && index.getId().equals(id)){
            return false;
        }

        return null == index ? false : true;
    }
}
