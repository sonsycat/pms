package com.zjry.pms.bonus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Lists;
import com.zjry.common.annotation.Log;
import com.zjry.common.core.controller.BaseController;
import com.zjry.common.core.domain.AjaxResult;
import com.zjry.common.core.page.TableDataInfo;
import com.zjry.common.enums.BusinessType;
import com.zjry.common.utils.DateUtils;
import com.zjry.pms.bonus.domain.PmsBonusCalculation;
import com.zjry.pms.bonus.domain.PmsBonusDto;
import com.zjry.pms.bonus.domain.PmsBonusPersonAudit;
import com.zjry.pms.bonus.service.IPmsBonusCalculationNewService;
import com.zjry.pms.bonus.service.IPmsBonusCalculationService;
import com.zjry.pms.bonus.service.IPmsBonusPersonAuditService;
import com.zjry.pms.common.Constants;
import com.zjry.pms.common.utils.UserUtils;
import com.zjry.pms.evaluate.domain.PmsEvaluateData;
import com.zjry.pms.evaluate.service.IPmsEvaluateDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;

/**
 * 奖金核算Controller
 *
 * @author zjry
 * @date 2022-10-19
 */
@RestController
@RequestMapping("/bonus/calculationNew")
@Api(tags = {"【奖金核算】"})
public class PmsBonusCalculationNewController extends BaseController {

    @Autowired
    private IPmsBonusCalculationNewService pmsBonusCalculationNewService;
    @Autowired
    private IPmsBonusCalculationService pmsBonusCalculationService;

    /**
     * 生成奖金核算
     */
    @PreAuthorize("@ss.hasPermi('bonus:calculationNew:add')")
    @Log(title = "奖金核算", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增奖金核算")
    public AjaxResult generate(@RequestBody PmsBonusCalculation pmsBonusCalculation) {
        String period = DateUtils.parseDateToStr(Constants.DATE_MONTH_FORMATTER, pmsBonusCalculation.getPeriod()==null?new Date():pmsBonusCalculation.getPeriod());
        QueryWrapper<PmsBonusCalculation> wrapper = new QueryWrapper<>();
        wrapper.lambda().select(PmsBonusCalculation::getId,PmsBonusCalculation::getPeriod,PmsBonusCalculation::getAuditState)
                .eq(PmsBonusCalculation::getPeriod, period);
        List<PmsBonusCalculation> list = pmsBonusCalculationNewService.list(wrapper);
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
        int i = pmsBonusCalculationNewService.insertPmsBonusCalculation(pmsBonusCalculation);
        if(i > 0){
            return AjaxResult.success("奖金数据已生成");
        }else{
            return AjaxResult.success("奖金数据已生成，数据为空");
        }
    }
    @PreAuthorize("@ss.hasPermi('bonus:calculationNew:detail')")
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
//            param.setDataType(Constants.EVA_CAL_BONUS);
            param.setCompCode(UserUtils.getSysUser().getDeptId());
            List<PmsEvaluateData> bonusList = pmsBonusCalculationNewService.selectBonusList(param);
            if(CollectionUtils.isNotEmpty(bonusList)){
                for(PmsEvaluateData data : bonusList){
                    if("2".equals(data.getDeptType())){
                        bonusAlone = bonusAlone.add(data.getGuideValueResult());
                    }if("1".equals(data.getDeptType())){
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
    @PreAuthorize("@ss.hasPermi('evaluate:data:list')")
    @GetMapping("/listAccountDeptBonus")
    @ApiOperation("查询指标数据值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public AjaxResult listAccountDeptBonus(PmsEvaluateData pmsEvaluateData){
        pmsEvaluateData.setCompCode(UserUtils.getSysUser().getDeptId());
        return AjaxResult.success(pmsBonusCalculationNewService.selectAccountDeptEvaluateDataList(pmsEvaluateData));
    }
    @PreAuthorize("@ss.hasPermi('evaluate:data:list')")
    @GetMapping("/getAccountDeptIndex")
    @ApiOperation("查询核算科室对应指标")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码", dataType = "int", paramType = "query", required = false),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = false),
    })
    public AjaxResult getAccountDeptIndex(PmsEvaluateData pmsEvaluateData){
        pmsEvaluateData.setCompCode(UserUtils.getSysUser().getDeptId());
        return AjaxResult.success(pmsBonusCalculationNewService.selectAccountDeptIndex(pmsEvaluateData));
    }
    @PreAuthorize("@ss.hasPermi('bonus:calculationNew:audit')")
    @Log(title = "审核奖金", businessType = BusinessType.UPDATE)
    @PostMapping("/bonusAudit")
    @ApiOperation("审核奖金")
    public AjaxResult bonusAudit(@RequestBody PmsBonusCalculation pmsBonusCalculation) {
        return pmsBonusCalculationNewService.bonusAudit(pmsBonusCalculation);
    }
    @PreAuthorize("@ss.hasPermi('bonus:calculationNew:audit')")
    @Log(title = "提交奖金", businessType = BusinessType.UPDATE)
    @PostMapping("/bonusCommit")
    @ApiOperation("提交奖金")
    public AjaxResult bonusCommit(@RequestBody PmsBonusCalculation pmsBonusCalculation) {
        return pmsBonusCalculationNewService.bonusCommit(pmsBonusCalculation);

    }
    @PreAuthorize("@ss.hasPermi('bonus:calculationNew:archive')")
    @Log(title = "奖金归档", businessType = BusinessType.UPDATE)
    @PostMapping("/archive/{id}")
    @ApiOperation("归档")
    public AjaxResult archive(@PathVariable("id") Long id) {
        return pmsBonusCalculationNewService.bonusArchive(id);
    }
}
