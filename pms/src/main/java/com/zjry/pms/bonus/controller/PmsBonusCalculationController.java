package com.zjry.pms.bonus.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Lists;
import com.zjry.common.utils.DateUtils;
import com.zjry.pms.bonus.domain.PmsBonusDto;
import com.zjry.pms.bonus.domain.PmsBonusPersonAudit;
import com.zjry.pms.bonus.service.IPmsBonusPersonAuditService;
import com.zjry.pms.common.Constants;
import com.zjry.pms.common.utils.UserUtils;
import com.zjry.pms.evaluate.domain.PmsEvaluateData;
import com.zjry.pms.evaluate.service.IPmsEvaluateDataService;
import io.swagger.annotations.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.enums.BusinessType;
import com.zjry.pms.bonus.domain.PmsBonusCalculation;
import com.zjry.pms.bonus.service.IPmsBonusCalculationService;
import com.zjry.common.core.page.TableDataInfo;

/**
 * 奖金核算Controller
 *
 * @author zjry
 * @date 2022-10-19
 */
@RestController
@RequestMapping("/bonus/calculation")
@Api(tags = {"【奖金核算】"})
public class PmsBonusCalculationController extends BaseController {

    @Autowired
    private IPmsBonusCalculationService pmsBonusCalculationService;
    @Autowired
    private IPmsEvaluateDataService pmsEvaluateDataService;
    @Autowired
    private IPmsBonusPersonAuditService pmsBonusPersonAuditService;

    /**
     * 查询奖金核算列表
     */
    @PreAuthorize("@ss.hasPermi('bonus:calculation:list')")
    @GetMapping("/list")
    @ApiOperation("查询奖金核算列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public TableDataInfo list(PmsBonusCalculation pmsBonusCalculation) {
        startPage();
        pmsBonusCalculation.setCompCode(UserUtils.getSysUser().getDeptId());
        List<PmsBonusCalculation> list = pmsBonusCalculationService.selectPmsBonusCalculationList(pmsBonusCalculation);
        return getDataTable(list);
    }

    /**
     * 归档（审核通过的数据方可归档，一次分配和二次分配都审核通过的数据方可归档）
     */
    @PreAuthorize("@ss.hasPermi('bonus:calculation:archive')")
    @Log(title = "奖金核算", businessType = BusinessType.UPDATE)
    @PostMapping("/archive/{id}")
    @ApiOperation("归档")
    public AjaxResult archive(@PathVariable("id") Long id) {
        PmsBonusCalculation data = pmsBonusCalculationService.selectPmsBonusCalculationById(id);
        if(data == null){
            return AjaxResult.error("数据不存在");
        }
        if(!Constants.CHECK_PASS.equals(data.getAuditState())){
            return AjaxResult.error("审核未完成的数据，不可归档");
        }
        /* 获取人员奖金审核通过数 */
        QueryWrapper<PmsBonusPersonAudit> qw = new QueryWrapper();
        qw.lambda().select(PmsBonusPersonAudit::getId)
                .eq(PmsBonusPersonAudit::getAuditState, Constants.CHECK_PASS)
                .eq(PmsBonusPersonAudit::getNowDate, data.getPeriod())
                .groupBy(PmsBonusPersonAudit::getDeptCode);
        int auditPassCount = pmsBonusPersonAuditService.count(qw);
        /* 获取部门数目 */
        QueryWrapper<PmsEvaluateData> wrapper_ = new QueryWrapper();
        wrapper_.lambda().select(PmsEvaluateData::getId).eq(PmsEvaluateData::getActDate,data.getPeriod())
                .eq(PmsEvaluateData::getCompCode, data.getCompCode())
                .eq(PmsEvaluateData::getDataType, Constants.EVA_CAL_BONUS).groupBy(PmsEvaluateData::getDeptCode);
        int deptBonusCount = pmsEvaluateDataService.count(wrapper_);
        if(auditPassCount == 0 || auditPassCount != deptBonusCount){
            return AjaxResult.error("二次分配未全部完成审核，不可归档");
        }

        UpdateWrapper<PmsBonusCalculation> wrapper = new UpdateWrapper();
        wrapper.lambda().set(PmsBonusCalculation::getArchiveState, Constants.COMMON_YES)
                .eq(PmsBonusCalculation::getId, id).eq(PmsBonusCalculation::getAuditState, Constants.CHECK_PASS);
        return toAjax(pmsBonusCalculationService.update(wrapper));
    }

    /**
     * 生成奖金核算
     */
    @PreAuthorize("@ss.hasPermi('bonus:calculation:add')")
    @Log(title = "奖金核算", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增奖金核算")
    public AjaxResult generate(@RequestBody PmsBonusCalculation pmsBonusCalculation) {
        String period = DateUtils.parseDateToStr(Constants.DATE_MONTH_FORMATTER, pmsBonusCalculation.getPeriod()==null?new Date():pmsBonusCalculation.getPeriod());
        QueryWrapper<PmsBonusCalculation> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(PmsBonusCalculation::getId,PmsBonusCalculation::getPeriod,PmsBonusCalculation::getAuditState)
                .eq(PmsBonusCalculation::getPeriod, period);
        List<PmsBonusCalculation> list = pmsBonusCalculationService.list(wrapper);
        if(CollectionUtils.isNotEmpty(list)){
            for(PmsBonusCalculation c : list){
                if(Constants.CHECK_DRAFT.equals(c.getAuditState())){
                    pmsBonusCalculation.setId(c.getId());
                }else if(Constants.CHECK_SUBMIT.equals(c.getAuditState())){
                    return AjaxResult.error("数据已生成，并已在审核流程中");
                }else if(Constants.CHECK_PASS.equals(c.getAuditState())){
                    return AjaxResult.error("数据已存在并已审核通过，不可重新生成");
                }
            }
        }
        int i = pmsBonusCalculationService.insertPmsBonusCalculation(pmsBonusCalculation);
        if(i > 0){
            return AjaxResult.success("奖金数据已生成");
        }else{
            return AjaxResult.success("奖金数据已生成，数据为空");
        }
    }

    /**
     * 获取奖金核算详细信息
     */
//    @PreAuthorize("@ss.hasPermi('bonus:calculation:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取奖金核算详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsBonusCalculationService.selectPmsBonusCalculationById(id));
    }

    /**
     * 获取奖金核算详细信息
     */
    @PreAuthorize("@ss.hasPermi('bonus:calculation:detail')")
    @Log(title = "获取奖金核算详细信息", businessType = BusinessType.UPDATE)
    @GetMapping(value = "/detail/{period}")
    @ApiOperation("获取奖金核算详细信息")
    public AjaxResult detail(@PathVariable("period") String period) {
        Date actDate = DateUtils.parseDate(period);

        String msg = "";
        BigDecimal bonusAlone = BigDecimal.ZERO;
        BigDecimal bonusAverage = BigDecimal.ZERO;

        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = actDate.toInstant().atZone(zoneId).toLocalDate();
        LocalDate lastDayOfMonth = localDate.with(TemporalAdjusters.lastDayOfMonth());
        ZonedDateTime zdt = lastDayOfMonth.atStartOfDay(zoneId);
        PmsBonusDto bonus = new PmsBonusDto();
        bonus.setPeriod(actDate);
        bonus.setDateBegin(actDate);
        bonus.setDateEnd(Date.from(zdt.toInstant()));
        QueryWrapper<PmsBonusCalculation> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(PmsBonusCalculation::getAuditState,PmsBonusCalculation::getName)
                .eq(PmsBonusCalculation::getCompCode, UserUtils.getSysUser().getDeptId())
                .eq(PmsBonusCalculation::getPeriod, period)
                .in(PmsBonusCalculation::getAuditState, Lists.newArrayList(Constants.CHECK_DRAFT,Constants.CHECK_SUBMIT,Constants.CHECK_PASS))
                .orderByDesc(PmsBonusCalculation::getAuditState).last("LIMIT 1");
        PmsBonusCalculation one = pmsBonusCalculationService.getOne(wrapper);
        if(null == one){
            msg = "数据未生成";
        }else{
            PmsEvaluateData param = new PmsEvaluateData();
            param.setActDate(actDate);
            param.setDataType(Constants.EVA_CAL_BONUS);
            param.setCompCode(UserUtils.getSysUser().getDeptId());
            List<PmsEvaluateData> bonusList = pmsEvaluateDataService.selectBonusList(param);
            if(CollectionUtils.isNotEmpty(bonusList)){
                for(PmsEvaluateData data : bonusList){
                    if(Constants.COMMON_YES.equals(data.getDeptType())){
                        bonusAlone = bonusAlone.add(data.getGuideValueResult());
                    }else{
                        bonusAverage = bonusAverage.add(data.getGuideValueResult());
                    }
                }
            }else{
                msg = "数据未生成";
            }
        }

        bonus.setBonusAlone(bonusAlone);
        bonus.setBonusAverage(bonusAverage);
        bonus.setBonusTotal(bonusAlone.add(bonusAverage));
        return AjaxResult.success(msg, bonus);
    }

    /**
     * 删除奖金核算
     */
    @PreAuthorize("@ss.hasPermi('bonus:calculation:remove')")
    @Log(title = "奖金核算", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除奖金核算")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsBonusCalculationService.deletePmsBonusCalculationByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('bonus:audit:commit')")
    @Log(title = "奖金提交", businessType = BusinessType.UPDATE)
    @PostMapping("/commit/audit")
    @ApiOperation("奖金审核提交")
    public AjaxResult commitAudit(@RequestBody PmsBonusCalculation pmsBonusCalculation) {
        return pmsBonusCalculationService.commitAudit(pmsBonusCalculation);
    }

    @PreAuthorize("@ss.hasPermi('bonus:audit:audit')")
    @Log(title = "奖金很合获取流程id", businessType = BusinessType.UPDATE)
    @PostMapping("/get/proId")
    @ApiOperation("奖金审核获取流程id")
    public AjaxResult getProId(@RequestBody PmsBonusCalculation pmsBonusCalculation) {
        return pmsBonusCalculationService.getProId(pmsBonusCalculation);
    }

    @Log(title = "奖金审核", businessType = BusinessType.UPDATE)
    @PostMapping("/audit")
    @ApiOperation("奖金审核")
    public AjaxResult audit(@RequestBody PmsBonusCalculation pmsBonusCalculation) {
        return pmsBonusCalculationService.audit(pmsBonusCalculation);
    }

}
