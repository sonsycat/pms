package com.zjry.pms.evaluate.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.poi.ExcelUtil;
import com.zjry.pms.common.Constants;
import com.zjry.pms.common.utils.UserUtils;
import com.zjry.pms.evaluate.domain.PmsEvaluateData;
import com.zjry.pms.evaluate.domain.PmsEvaluateIndex;
import com.zjry.pms.evaluate.service.IPmsEvaluateIndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLSyntaxErrorException;
import java.util.List;

/**
 * 指标管理Controller
 *
 * @author mk
 * @date 2022-07-26
 */
@RestController
@RequestMapping("/evaluate/index")
@Api(tags={"【指标管理】"})
public class PmsEvaluateIndexController extends BaseController {
    @Autowired
    private IPmsEvaluateIndexService pmsEvaluateIndexService;

    /**
     * 查询指标管理列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:index:list')")
    @GetMapping("/list")
    @ApiOperation("查询指标管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsEvaluateIndex pmsEvaluateIndex) {
        pmsEvaluateIndex.setIsBonus(Constants.IS_CHANGE_NO);
        pmsEvaluateIndex.setCompCode(UserUtils.getSysUser().getDeptId());
        startPage();
        List<PmsEvaluateIndex> list = pmsEvaluateIndexService.selectPmsEvaluateIndexList(pmsEvaluateIndex);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('evaluate:index:list')")
    @GetMapping("/bonusList")
    @ApiOperation("查询指标管理列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo bonusList(PmsEvaluateIndex pmsEvaluateIndex) {
        pmsEvaluateIndex.setIsBonus(Constants.IS_CHANGE_YES);
        pmsEvaluateIndex.setCompCode(UserUtils.getSysUser().getDeptId());
        startPage();
        List<PmsEvaluateIndex> list = pmsEvaluateIndexService.selectPmsEvaluateIndexList(pmsEvaluateIndex);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('evaluate:index:list')")
    @GetMapping("/bonusListAll")
    @ApiOperation("查询指标管理列表")
    public List bonusListAll(PmsEvaluateIndex pmsEvaluateIndex) {
        pmsEvaluateIndex.setIsBonus(Constants.IS_CHANGE_YES);
        return pmsEvaluateIndexService.selectPmsEvaluateIndexList(pmsEvaluateIndex);
    }

    /**
     * 导出指标管理列表
     */
    @PreAuthorize("@ss.hasPermi('evaluate:index:export')")
    @Log(title = "指标管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    @ApiOperation("导出指标管理列表Excel")
    public AjaxResult export(PmsEvaluateIndex pmsEvaluateIndex) {
        List<PmsEvaluateIndex> list = pmsEvaluateIndexService.selectPmsEvaluateIndexList(pmsEvaluateIndex);
        ExcelUtil<PmsEvaluateIndex> util = new ExcelUtil<PmsEvaluateIndex>(PmsEvaluateIndex.class);
        return util.exportExcel(list, "指标管理数据");
    }

    /**
     * 获取指标管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('evaluate:index:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取指标管理详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsEvaluateIndexService.selectPmsEvaluateIndexById(id));
    }

    /**
     * 新增指标管理
     */
    @PreAuthorize("@ss.hasPermi('evaluate:index:add')")
    @Log(title = "指标管理", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增指标管理")
    public AjaxResult add(@RequestBody PmsEvaluateIndex pmsEvaluateIndex) {
        PmsEvaluateIndex pms = new PmsEvaluateIndex();
        pms.setIName(pmsEvaluateIndex.getIndexName());
        pms.setCompCode(pmsEvaluateIndex.getCompCode());
        pms.setIsBonus(Constants.IS_CHANGE_NO);
        List<PmsEvaluateIndex> list = pmsEvaluateIndexService.selectPmsEvaluateIndexList(pms);
        if(list.size()>0){
            return error("指标名称重复");
        }
        pmsEvaluateIndex.setIsBonus(Constants.IS_CHANGE_NO);
        return toAjax(pmsEvaluateIndexService.insertPmsEvaluateIndex(pmsEvaluateIndex));
    }

    /**
     * 新增指标管理
     */
    @PreAuthorize("@ss.hasPermi('evaluate:index:add')")
    @Log(title = "指标管理", businessType = BusinessType.INSERT)
    @PostMapping("/addBonus")
    @ApiOperation("新增指标管理")
    public AjaxResult addBonus(@RequestBody PmsEvaluateIndex pmsEvaluateIndex) {
        PmsEvaluateIndex pms = new PmsEvaluateIndex();
        pms.setIName(pmsEvaluateIndex.getIndexName());
        pms.setCompCode(pmsEvaluateIndex.getCompCode());
        pms.setIsBonus(Constants.IS_CHANGE_YES);
        List<PmsEvaluateIndex> list = pmsEvaluateIndexService.selectPmsEvaluateIndexList(pms);
        if(list.size()>0){
            return error("指标名称重复");
        }
        pmsEvaluateIndex.setIsBonus(Constants.IS_CHANGE_YES);
        return toAjax(pmsEvaluateIndexService.insertPmsEvaluateIndex(pmsEvaluateIndex));
    }

    /**
     * 修改指标管理
     */
    @PreAuthorize("@ss.hasPermi('evaluate:index:edit')")
    @Log(title = "指标管理", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改指标管理")
    public AjaxResult edit(@RequestBody PmsEvaluateIndex pmsEvaluateIndex) {
        PmsEvaluateIndex index = pmsEvaluateIndexService.selectPmsEvaluateIndexById(pmsEvaluateIndex.getId());
        if(index == null){
            return AjaxResult.error("指标不存在");
        }
        if(StringUtils.isNotBlank(pmsEvaluateIndex.getFormulaCode()) && pmsEvaluateIndex.getFormulaCode().indexOf("#{"+index.getIndexCode()+"}") > -1){
            return AjaxResult.error("表达式中不能包含本身");
        }
        PmsEvaluateIndex pms = new PmsEvaluateIndex();
        pms.setId(pmsEvaluateIndex.getId());
        pms.setIName(pmsEvaluateIndex.getIndexName());
        pms.setCompCode(pmsEvaluateIndex.getCompCode());
        List<PmsEvaluateIndex> list = pmsEvaluateIndexService.selectPmsEvaluateIndexList(pms);
        if(list.size()>0){
            return error("指标名称重复");
        }
        return toAjax(pmsEvaluateIndexService.updatePmsEvaluateIndex(pmsEvaluateIndex));
    }

    /**
     * 删除指标管理
     */
    @PreAuthorize("@ss.hasPermi('evaluate:index:remove')")
    @Log(title = "指标管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除指标管理")
    public AjaxResult remove(@PathVariable Long[] ids) {
        PmsEvaluateIndex pmsEvaluateIndex = pmsEvaluateIndexService.selectPmsEvaluateIndexById(ids[0]);
        if(pmsEvaluateIndex == null){
            return AjaxResult.error("指标不存在！");
        }
        if(Constants.COMMON_YES.equals(pmsEvaluateIndex.getIsNecessary())){
            return AjaxResult.error("必须指标不允许删除！");
        }

        if(pmsEvaluateIndexService.checkIsUsedInSet(pmsEvaluateIndex.getIndexCode())){
            return AjaxResult.error("指标已使用，不允许删除！");
        }
        return toAjax(pmsEvaluateIndexService.deletePmsEvaluateIndexByIds(ids));
    }


    @GetMapping("/creatDataInfo")
    @ApiOperation("指标管理指标算法数据生成")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码" ,dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize",value = "每页数据量" , dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo creatDataInfo(PmsEvaluateIndex pmsEvaluateIndex) {
        List<PmsEvaluateIndex> list = null;
        try {
            startPage();
            list = pmsEvaluateIndexService.createDataInfo(pmsEvaluateIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getDataTable(list);
    }

    /**
     * 从数据源获取数据
     */
    @PreAuthorize("@ss.hasPermi('evaluate:index:remove')")
    @Log(title = "从数据源获取数据", businessType = BusinessType.INSERT)
    @PostMapping("/createDataFromSql")
    @ApiOperation("从数据源获取数据")
    public AjaxResult createDataFromSql(PmsEvaluateIndex pmsEvaluateIndex) {
        //数据校验
        if(StringUtils.isEmpty(pmsEvaluateIndex.getIndexSql()) || StringUtils.isEmpty(pmsEvaluateIndex.getDbCode())){
            return AjaxResult.error("指标算法SQL或数据源为空");
        }
        if(StringUtils.isBlank(pmsEvaluateIndex.getIsSection()) && Constants.COMMON_YES.equals(pmsEvaluateIndex.getIsSection())){
            if(pmsEvaluateIndex.getSectionStart() == null || pmsEvaluateIndex.getSectionEnd() == null){

            }return AjaxResult.error("选择为区间指标时，区间开始结束时间不可为空");
        }
        try {
            QueryWrapper<PmsEvaluateData> wrapper = new QueryWrapper<>();
            wrapper.lambda().select(PmsEvaluateData::getId);
            wrapper.lambda().eq(PmsEvaluateData::getGuideCode, pmsEvaluateIndex.getIndexCode());
            List<PmsEvaluateData> dataList = pmsEvaluateIndexService.generateDataFromSql(pmsEvaluateIndex);
            return AjaxResult.success("生成数据成功", dataList);
        } catch (Exception e) {
            logger.error("从数据源获取数据出错", e);
            if(e instanceof SQLSyntaxErrorException){
                return AjaxResult.error("SQL语法错误："+e.getMessage());
            }
            return AjaxResult.error("系统错误，请联系管理员");
        }
    }
}
